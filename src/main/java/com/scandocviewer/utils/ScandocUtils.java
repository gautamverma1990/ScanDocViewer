package com.scandocviewer.utils;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ScandocUtils { 


	static  Map<String,String>   shpWhereClauseMap ;  
	static  Map<String,String>   preShpWhereClauseMap ;  
	static  Map<String,String>   userWhereClauseMap ;  
	static  Map<String,String>   switchDocTypeMap ;  
	static  Map<String,String>   switchMessageeMap ;  

	
	public static Map<String,String> getshpWhereClauseMap(){
	
		shpWhereClauseMap = new HashMap<>();
			
		shpWhereClauseMap.put("hbl", "shp.Housebill = ?");
		shpWhereClauseMap.put("mbl", "shp.Masterbill = ?");
		shpWhereClauseMap.put("stt", "shp.STT = ?");
		shpWhereClauseMap.put("shipmentNo", "shp.ShipmentNo = ?");
		shpWhereClauseMap.put("archiveNumber", "shp.ArchiveNo = ?");
		shpWhereClauseMap.put("schenkerInvoice", "invoice.InvoiceNo = ?");
		
		return shpWhereClauseMap;
 
	} 
	
	
	public static Map<String,String> getSwitchDocTypeMap(){
		
		switchDocTypeMap = new HashMap<>();
			
		switchDocTypeMap.put("Commercial Invoice", "Supplier Invoice");
		switchDocTypeMap.put("Supplier Invoice", "Commercial Invoice");
		switchDocTypeMap.put("Outgoing Invoice/Credit Note External", "Outgoing Invoice/Credit Note Internal");
		switchDocTypeMap.put("Outgoing Invoice/Credit Note Internal", "Outgoing Invoice/Credit Note External");
 		
		return switchDocTypeMap;
 
	}
	
public static Map<String,String> getSwitchMessageMap(){
		
		switchMessageeMap = new HashMap<>();
			
		switchMessageeMap.put("Commercial Invoice", "Document Type is change from Commercial Invoice to  Supplier Invoice");
		switchMessageeMap.put("Supplier Invoice", "Document Type is change from Supplier Invoice to  Commercial Invoice");
		switchMessageeMap.put("Outgoing Invoice/Credit Note External", "Document Type is change from Outgoing Invoice/Credit Note External to  Outgoing Invoice/Credit Note Internal");
		switchMessageeMap.put("Outgoing Invoice/Credit Note Internal", "Document Type is change from Outgoing Invoice/Credit Note Internal to  Outgoing Invoice/Credit Note External");
 		
		return switchMessageeMap;
 
	}
	
	
	
	public static Map<String,String> getUserWhereClauseMap(){
		
		userWhereClauseMap = new HashMap<>();
			
		userWhereClauseMap.put("userId", "UserID like ?");
		userWhereClauseMap.put("firstName", "FirstName like ?");
		userWhereClauseMap.put("lastNmae", "LastName like ?");
		userWhereClauseMap.put("company", "Company like ?");
		userWhereClauseMap.put("userGroup", "UserGroup like ?");
		userWhereClauseMap.put("accessLevel", "AccessLevel like ?");
		
		return userWhereClauseMap;
 
	}
	
	
	public static Map<String,String> getPreShpWhereClauseMap(){
		
		preShpWhereClauseMap = new HashMap<>();
			
		preShpWhereClauseMap.put("hbl", "preship.Housebill = ?");
 		preShpWhereClauseMap.put("stt", "preship.STT = ?");
		preShpWhereClauseMap.put("shipmentNo", "preship.ShipmentNo = ?");
 		
		return preShpWhereClauseMap;
 
	} 
	
	
	public static String getUserWhereClause(String refType){		
		
		if( null ==  userWhereClauseMap){
			userWhereClauseMap = ScandocUtils.getUserWhereClauseMap();
		}
		
		return userWhereClauseMap.get(refType);
	}
	
	public static String getshpWhereClause(String refType){		
		
		if( null ==  shpWhereClauseMap){
			shpWhereClauseMap = ScandocUtils.getshpWhereClauseMap();
		}
		
		return shpWhereClauseMap.get(refType);
	}
	
	public static String getPreShpWhereClause(String refType){		
		
		if( null ==  preShpWhereClauseMap){
			preShpWhereClauseMap = ScandocUtils.getPreShpWhereClauseMap();
		}
		
		return preShpWhereClauseMap.get(refType);
	}
	
	public static String getSwitchDocType(String docType){
		
		if( null ==  switchDocTypeMap){
			switchDocTypeMap = ScandocUtils.getSwitchDocTypeMap();
		}
		
		return switchDocTypeMap.get(docType);
	}
	
	public static String getSwitchMessage(String docType){
		
		if( null ==  switchMessageeMap){
			switchMessageeMap = ScandocUtils.getSwitchMessageMap();
		}
		
		return switchMessageeMap.get(docType);
	}
	
	
	public static String getJSONMsg (String ErrorMsg){
		return new StringBuffer("{\"errorMessage\":\"").append(ErrorMsg).append("\"}").toString();
 	}
	
	public static String getJSONSuccessMsg (String ErrorMsg){
		return new StringBuffer("{\"successMessage\":\"").append(ErrorMsg).append("\"}").toString();
 	}
	
	public static String createTempDirectory(){
 		DateFormat  dateFormat  = new SimpleDateFormat("yyyyMMdd-HHmmssSSS");
		String folderName =  dateFormat.format(new Date());
		
		String systemTempDir = System.getProperty("java.io.tmpdir"); 
 		
		StringBuilder folderPath = new StringBuilder(systemTempDir).append(File.separator).append(folderName);

 		File folder =new File(folderPath.toString());
 		folder.mkdirs();
 		
 		return folder.getPath();

	}

}
