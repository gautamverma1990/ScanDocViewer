package com.scandocviewer.handler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scandocviewer.beans.MasterData;
import com.scandocviewer.beans.ScandocData;
import com.scandocviewer.beans.SendEmailData;
import com.scandocviewer.exceptions.ScandocException;
import com.scandocviewer.services.ScandocService;
import com.scandocviewer.utils.ScandocUtils;

@RestController
public class ScandocHandler {
	
	@Autowired
	Environment env;
	
	@Autowired
	ScandocService scandocService;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/search/{refType}/{refValue}/{userGroup}/{accessLevel}", method = RequestMethod.GET)
	public ResponseEntity search(@PathVariable("refType") String refType, 
								 @PathVariable("refValue") String refValue, 
								 @PathVariable("userGroup") String userGroup, 
								 @PathVariable("accessLevel") String accessLevel) { 
		try{
		        List<ScandocData> ScandocDataList = scandocService.getScandocData(refType, refValue, userGroup, accessLevel);
		         
		        if (CollectionUtils.isEmpty(ScandocDataList)) { 
 		            
		            String errormesage = (!"schenkerInvoice".equals(refType) && scandocService.findShipments(refType, refValue))? 
		            						env.getProperty("errMsg.noShipments") :
		            						env.getProperty("errMsg.noDocuments"); 		            
		            
		            return new ResponseEntity(ScandocUtils.getJSONMsg( MessageFormat.format(errormesage, refType, refValue) ),HttpStatus.NOT_FOUND);
		        }
		  
		        return new ResponseEntity<List<ScandocData>>(ScandocDataList, HttpStatus.OK);
		        
		}catch (ScandocException e) {
	        return new ResponseEntity(ScandocUtils.getJSONMsg(e.getMessage()), HttpStatus.NOT_FOUND);
		}  
	} 
 
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
    @RequestMapping(value = "/updateDocumentType", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity updateDocumentType(@RequestBody MasterData data) { 
		
		try{
   
	    	String newDocType= scandocService.updateDocumentType( data.getUser().getUserId(), data.getScandocData().get(0));
	    	
	        return new ResponseEntity( ScandocUtils.getJSONSuccessMsg (newDocType), HttpStatus.OK);
	        
	        
		}catch (ScandocException e) {
	        return new ResponseEntity(ScandocUtils.getJSONMsg(e.getMessage()), HttpStatus.NOT_FOUND);
		}  

        
	} 
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
    @RequestMapping(value = "/sendEmail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity sendEmail(@RequestBody SendEmailData emailData) {  
	 
    	try {
			scandocService.sendEmail(emailData);
	        return new ResponseEntity( ScandocUtils.getJSONSuccessMsg (env.getProperty("successMsg.email.sent")), HttpStatus.OK);
	        
		} catch (ScandocException e) {
            return new ResponseEntity( ScandocUtils.getJSONMsg (e.getMessage()),HttpStatus.NOT_FOUND);
		} 
        
	} 
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/printMergedPdf", method = RequestMethod.POST)
	public ResponseEntity<byte[]> printMergedPdf(@RequestBody SendEmailData emailData) {

		try {
			
		    HttpHeaders headers = new HttpHeaders();	
		    headers.setContentType(MediaType.parseMediaType("application/pdf"));
		    
		    String mergedPdf   = scandocService.mergePDFs(emailData.getSelectedScandocs()); 
		    Path path = Paths.get(mergedPdf);		    
		    byte[] contents  = Files.readAllBytes( path );  
 	
 		    ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(contents, headers, HttpStatus.OK);
 		    
 		   FileUtils.deleteDirectory(new File(mergedPdf).getParentFile());
 		    
		    return response;
		    
		} catch (IOException e) { 
            return new ResponseEntity( ScandocUtils.getJSONMsg (e.getMessage()),HttpStatus.NOT_FOUND);
		}catch (ScandocException e) {
            return new ResponseEntity(ScandocUtils.getJSONMsg(e.getMessage()), HttpStatus.NOT_FOUND);
		}  
		
 	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
    @RequestMapping(value = "/deleteDocuments", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity deleteDocuments(@RequestBody MasterData data) {  
		try{
			scandocService.deleteDocuments(data.getUser().getUserId(), data.getScandocData());
	        return new ResponseEntity( ScandocUtils.getJSONSuccessMsg (env.getProperty("successMsg.document.deleted")), HttpStatus.OK); 
	        
		}catch (ScandocException e) {
	        return new ResponseEntity(ScandocUtils.getJSONMsg(e.getMessage()), HttpStatus.NOT_FOUND);
		}  
        
	} 
	
	

}
