package com.scandocviewer.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.mail.Session;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.scandocviewer.beans.ScandocData;
import com.scandocviewer.beans.SendEmailData;
import com.scandocviewer.dao.ScandocDao;
import com.scandocviewer.exceptions.ScanDocDataAccessException;
import com.scandocviewer.exceptions.ScandocException;
import com.scandocviewer.utils.EmailUtils;
import com.scandocviewer.utils.ScandocUtils;

@Service
public class ScandocServiceImpl implements ScandocService{
 
	
	@Autowired
	ScandocDao scandocDao;
	
	@Autowired
	Environment env;
	
	@Autowired
	EmailUtils emailUtils;
	
	private static final Logger logger = Logger.getLogger(ScandocServiceImpl.class); 

	
	@Override
	public List<ScandocData> getScandocData(String refType, String refValue, String userGroup, String accessLevel) throws ScandocException{ 
		
		List<ScandocData> scandocDataList;
		try {
			
 			
			scandocDataList = scandocDao.getScandocData(refValue, ScandocUtils.getshpWhereClause(refType) , userGroup, accessLevel); 
			
			if(CollectionUtils.isEmpty(scandocDataList) &&
					!StringUtils.isEmpty(ScandocUtils.getPreShpWhereClause(refType))){
				scandocDataList = scandocDao.getPreShipNoData(refValue, ScandocUtils.getPreShpWhereClause(refType) , userGroup, accessLevel); 
			} 
			
			updateAmsUrl(scandocDataList);
			
		//	updateShipmentNoUI(scandocDataList);
			 
			return scandocDataList;
		} catch (ScanDocDataAccessException e) {
			throw new ScandocException(env.getProperty("errMsg.database.opertion"));
		}
		
		
	}
	
	@Override
	public List<ScandocData> getAllScandocData(){ 
		
		List<ScandocData> scandocDataList = scandocDao.getAllScandocData();
		
		updateAmsUrl(scandocDataList);
 
		return scandocDataList;
		
	}
	
	@Override
	public boolean findShipments(String refType, String refValue) throws ScandocException{
		try {
			
			 return  scandocDao.findShipments(refType, ScandocUtils.getshpWhereClause(refType));	
			
		} catch (ScanDocDataAccessException e) {
				throw new ScandocException(env.getProperty("errMsg.database.opertion"));
	    }
		
 		
	}
	
	

	
	@Override
	public String updateDocumentType(String userId, ScandocData scandocData) throws ScandocException{
		
		try { 
			String newDocType = ScandocUtils.getSwitchDocType(scandocData.getDocType());
			
			scandocDao.updateDocumentType(userId, scandocData.getDocsDataPK(), newDocType); 
			createChangeLog(userId, ScandocUtils.getSwitchMessage(scandocData.getDocType()), scandocData);
			
			return newDocType;
		
		} catch (ScanDocDataAccessException e) {
			throw new ScandocException(env.getProperty("errMsg.database.opertion"));
    }
		
	}
	
	@Override
	public void sendEmail(SendEmailData emailData) throws ScandocException{		
		String mergedFileName = null;
		
		try { 
			System.out.println(emailData);
			mergedFileName = mergePDFs(emailData.getSelectedScandocs()); 
		     
		    Session session = emailUtils.getSession();

		    if(null != session){
		    	 
		    	 emailUtils.sendEmailWithAttachment(session, emailData, mergedFileName.toString());
		    }else{
		    	System.out.println("else Condition");
				logger.error("ScandocServiceImpl.sendEmail(): Failed Connect to the email server"); 			
				throw new ScandocException(env.getProperty("errMsg.connect.emailServer"));
		    }
		    
		} catch (ScandocException e) { 
			throw new ScandocException(e.getMessage());
 		} finally {
			try {
				if(!StringUtils.isEmpty(mergedFileName))
					FileUtils.deleteDirectory((new File(mergedFileName)).getParentFile());
			} catch (IOException e) {
				e.printStackTrace();
			}
				
		}
	
	}
	
	@Override
	public void deleteDocuments(String userId, List<ScandocData> scandocDataList) throws ScandocException{
		
		try {			
			for(ScandocData scandocData: scandocDataList){
				scandocDao.deleteDocuments(scandocData.getDocsDataPK(),scandocData.getDocId()); 
				createChangeLog(userId, env.getProperty("msg.del.doc"), scandocData);
			}
		

 
		} catch (ScanDocDataAccessException e) {
			throw new ScandocException(env.getProperty("errMsg.database.opertion"));
		}	
	}
	
	
	@Override
	public String mergePDFs(List<ScandocData> selectedData) throws ScandocException {  
		
		try { 
			String tempDirectory = ScandocUtils.createTempDirectory(); 
			
			Collections.sort(selectedData, new Comparator<ScandocData>() {
			    @Override
			    public int compare(ScandocData o1, ScandocData o2) {
			        return o1.getDocDisplayOrder() - o2.getDocDisplayOrder();
			    }
			});
			
			PDFMergerUtility PDFmerger = new PDFMergerUtility(); 
			
			for(ScandocData scandocData : selectedData){
				
				URL amsUrl = new URL(scandocData.getAmsDocUrl());
			    URLConnection urlConn = amsUrl.openConnection();
			    if (null!=urlConn && "application/pdf".equals(urlConn.getContentType())) {
			    	
			    	InputStream inputStream = amsUrl.openStream();
					
					StringBuilder filename = new StringBuilder(tempDirectory)
													.append(File.separator)
													.append(scandocData.getDocId())
													.append("-")
													.append(scandocData.getShipmentNo())
													.append(".pdf");
					
					FileOutputStream outputStream = new FileOutputStream(new File(filename.toString())); 
				    
					IOUtils.copy(inputStream, outputStream); 
					
					outputStream.flush();
					outputStream.close();
					inputStream.close();
					
					PDFmerger.addSource(filename.toString());
			    } 
				
			} 
 
			 
			 StringBuilder mergedFileName = new StringBuilder(tempDirectory).append(File.separator).append(env.getProperty("merged.fileName"));
			 
			 PDFmerger.setDestinationFileName(mergedFileName.toString());
		        
		     PDFmerger.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
		     
			return mergedFileName.toString();
		}catch (Exception e) {
			
			logger.error("ScandocServiceImpl.mergePDFs(): Failed to Merge Pdf :", e); 			
			throw new ScandocException(env.getProperty("errMsg.merge.pdf"));
		}
	     
	} 
	
	
	private void createChangeLog(String userId, String change, ScandocData scandocData) throws ScandocException{ 
		
		 try {
			scandocDao.createChangeLog(userId, change, scandocData);
		} catch (ScanDocDataAccessException e) {
			throw new ScandocException(env.getProperty("errMsg.database.opertion"));
		}	
		
	}
	
 
	
	public void updateAmsUrl(List<ScandocData> scandocDataList){
		 
		for(ScandocData scandocData: scandocDataList){		
			
			String shipNo = StringUtils.isEmpty(scandocData.getShipmentNo()) 
									? scandocData.getArchiveNo() : scandocData.getShipmentNo();
									
			if(!StringUtils.isEmpty(shipNo))
				scandocData.setAmsDocUrl(getAmsUrl( shipNo, scandocData.getDocId()));
		} 
      
		
	}
	
	public void updateShipmentNoUI(List<ScandocData> scandocDataList){
		 
		for(ScandocData scandocData: scandocDataList){		
									
			if(!StringUtils.isEmpty(scandocData.getShipmentNo())){
				if(scandocData.getShipmentNo().substring(0, 1).equals("X")){
					scandocData.setShipmentNoX("");
				}else{
					scandocData.setShipmentNoX(scandocData.getShipmentNo());
				}
			}
		}      
		
	}
	
	public String getAmsUrl(String shipno, String docid){
		
		
		try {
		 StringBuilder filename = 
				 new StringBuilder(shipno).append("-").append(docid);
		 
		 List<BasicNameValuePair> queryParams = new ArrayList<BasicNameValuePair>();
		 
		 queryParams.add(new BasicNameValuePair("database", "pgserver")); 
		 queryParams.add(new BasicNameValuePair("table", "View_PGServerDocs")); 
		 queryParams.add(new BasicNameValuePair("field", "shipnoid")); 
		 queryParams.add(new BasicNameValuePair("data", filename.toString())); 
		 queryParams.add(new BasicNameValuePair("application", "WebApp")); 
		 queryParams.add(new BasicNameValuePair("user", "schenkerAppUser")); 
		 queryParams.add(new BasicNameValuePair("type", "sampleType")); 
		
		 URI uri = URIUtils.createURI("http", env.getProperty("ams.host"), -1, env.getProperty("ams.url.path"), URLEncodedUtils.format(queryParams, "UTF-8"), null);
		 
		 return uri.toString();
		 
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		 
		 
		
	}
 
	
	
	

}
