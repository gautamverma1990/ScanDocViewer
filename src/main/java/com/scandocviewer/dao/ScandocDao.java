package com.scandocviewer.dao;

import java.util.List;

import com.scandocviewer.beans.ScandocData;
import com.scandocviewer.exceptions.ScanDocDataAccessException;
 
public interface ScandocDao {

	List<ScandocData> getAllScandocData();

	List<ScandocData> getScandocData(String searchString, String whereClause, String userGroup, String accessLevel) throws ScanDocDataAccessException;

	boolean findShipments(String searchString, String whereClause) throws ScanDocDataAccessException;

	void createChangeLog(String userId, String change, ScandocData scandocData) throws ScanDocDataAccessException;

	void updateDocumentType(String userId, String docsDataPK, String docType) throws ScanDocDataAccessException; 
 
	void deleteDocuments(String docsDataPK, String docId) throws ScanDocDataAccessException;

	List<ScandocData> getPreShipNoData(String searchString, String whereClause, String userGroup, String accessLevel) throws ScanDocDataAccessException;

}
