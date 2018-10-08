package com.scandocviewer.dao;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.scandocviewer.beans.ScandocData;
import com.scandocviewer.exceptions.ScanDocDataAccessException;
 
@Repository
public class ScandocDaoImpl implements ScandocDao{ 
	
	
	private static final Logger logger = Logger.getLogger(ScandocDaoImpl.class); 

	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<ScandocData> getScandocData(String searchString, String  whereClause, String userGroup, String accessLevel) throws ScanDocDataAccessException {
		
		try{
			StringBuffer searchQuery = new StringBuffer(DAOConstants.SCANDOC_SEARCH_QUERY);
			searchQuery.append(" and ").append(whereClause);
			
			if("U".equals(userGroup) && "E".equals(accessLevel)){
				searchQuery.append(" and doctype.AccessLevel = 'E' ");
			}
			
			searchQuery.append(" order by docdata.DocType");
			 
			List<ScandocData> ScandocDataList =  jdbcTemplate.query(searchQuery.toString(),
					 	 	new Object[] { searchString }, new BeanPropertyRowMapper(ScandocData.class));
			 
		    return ScandocDataList;
		}catch(Exception e){
			logger.error("ScandocDaoImpl.getScandocData(): Failed to get Scandoc Data :", e);
			throw new ScanDocDataAccessException(e.getMessage());
		}
		
	 }
	
	
 	@SuppressWarnings({ "unchecked", "rawtypes" })
 	@Override
	public List<ScandocData> getPreShipNoData(String searchString, String  whereClause, String userGroup, String accessLevel) throws ScanDocDataAccessException {
		
		try{
			StringBuffer searchQuery = new StringBuffer(DAOConstants.SCANDOC_PRESHIPNO_SEARCH_QUERY);
			searchQuery.append(" and ").append(whereClause);
			
			if("U".equals(userGroup) && "E".equals(accessLevel)){
				searchQuery.append(" and doctype.AccessLevel = 'E' ");
			}
			
			searchQuery.append(" order by docdata.DocType");
			 
			List<ScandocData> ScandocDataList =  jdbcTemplate.query(searchQuery.toString(),
					 	 	new Object[] { searchString }, new BeanPropertyRowMapper(ScandocData.class));
			 
		    return ScandocDataList;
		}catch(Exception e){
			logger.error("ScandocDaoImpl.getPreShipNoData(): Failed to get Scandoc Data :", e);
			throw new ScanDocDataAccessException(e.getMessage());
		}
		
	 }
	
	
	
	
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<ScandocData> getAllScandocData() {	
		 
		List<ScandocData> ScandocDataList =  jdbcTemplate.query(DAOConstants.SCANDOC_SEARCH_QUERY,
				 	 	 new BeanPropertyRowMapper(ScandocData.class));
		 
	    return ScandocDataList;
	 }
	
	@Override
	public boolean findShipments(String searchString, String  whereClause) throws ScanDocDataAccessException {
		
		try{
		
			StringBuffer searchQuery = new StringBuffer(DAOConstants.SHIPMENT_SEARCH_QUERY).append(whereClause);		
			
			Integer count = (Integer) jdbcTemplate.queryForObject(searchQuery.toString(),
			 	 	new Object[] { searchString}, Integer.class);
			
			return count > 0;
		
		}catch(Exception e){
			logger.error("ScandocDaoImpl.findShipments(): Failed with Exception :", e);
			throw new ScanDocDataAccessException(e.getMessage());
		}
		
	}
	
	
	@Override
	public void createChangeLog(String userId, String  change, ScandocData scandocData) throws ScanDocDataAccessException {
		
		try{
			
			
			jdbcTemplate.update(DAOConstants.CREATE_CHANGE_LOG,  new Object[] { 
																				scandocData.getShipmentNo(), 
																				scandocData.getArchiveNo(),
																				scandocData.getDepartment(), 
																				scandocData.getDocType(), 
																				scandocData.getDocId(),
																				change, userId} ); 
			
		}catch(Exception e){
			logger.error("ScandocDaoImpl.createChangeLog(): Failed with Exception :", e);
			throw new ScanDocDataAccessException(e.getMessage());
		}
		
		
		
	}
	
	@Override
	public void updateDocumentType(String userId, String docsDataPK, String docType) throws ScanDocDataAccessException{
		try{
			
			jdbcTemplate.update(DAOConstants.UPDATE_DOC_TYPE,  new Object[] { docType, userId, new Date(), docsDataPK } ); 

		}catch(Exception e){
			logger.error("ScandocDaoImpl.updateAccessLevel(): Failed with Exception :", e);
			throw new ScanDocDataAccessException(e.getMessage());
		}
		
	}
	
	@Override
	public void deleteDocuments(String docsDataPK, String docId) throws ScanDocDataAccessException{
		try{
			//Scandocs_Docs_Data Table
			jdbcTemplate.update(DAOConstants.UPDATE_DOC_DELETED,  docsDataPK ); 
			//Document_Transaction AUFOFAXDB
			String update_Doc_Trans = DAOConstants.UPDATE_DOC_TRANS_DELETED +Integer.parseInt(docId)+"')";
			jdbcTemplate.update(update_Doc_Trans); 
			
		}catch(Exception e){
			logger.error("ScandocDaoImpl.deleteDocuments(): Failed with Exception :", e);
			throw new ScanDocDataAccessException(e.getMessage());
		}
		
	
	}

}
