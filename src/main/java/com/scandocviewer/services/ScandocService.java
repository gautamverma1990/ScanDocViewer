package com.scandocviewer.services;

import java.util.List;

import com.scandocviewer.beans.ScandocData;
import com.scandocviewer.beans.SendEmailData;
import com.scandocviewer.exceptions.ScandocException;

public interface ScandocService {

	List<ScandocData> getScandocData(String refType, String refValue, String userGroup, String accessLevel) throws ScandocException;

	boolean findShipments(String refType, String refValue) throws ScandocException;

	List<ScandocData> getAllScandocData();

	String updateDocumentType(String userId, ScandocData scandocData) throws ScandocException; 
 
	void sendEmail(SendEmailData emailData) throws ScandocException; 
 
	String mergePDFs(List<ScandocData> selectedData) throws ScandocException;

	void deleteDocuments(String userId, List<ScandocData> scandocDataList) throws ScandocException;

}
