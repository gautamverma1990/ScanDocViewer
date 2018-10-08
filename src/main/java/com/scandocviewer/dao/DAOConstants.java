package com.scandocviewer.dao;

public class DAOConstants {
	
	static String SCANDOC_SEARCH_QUERY =  
			"SELECT distinct shp.ShipmentNo, shp.archiveNo, shp.Masterbill, " +
			"docdata.DocsDataPK, docdata.DocId, "+ 
			"isnull(docdata.InvoiceNo, 'Not Available') InvoiceNo, " +
			"docdata.department, docdata.docType, " + 
			"docdata.FileSize ,  docdata.DateScanned ,  docdata.Branch, doctype.DocDisplayOrder, " + 
			"doctype.AccessLevel " + 
			"FROM Shipment shp " + 
			"inner join Scandocs_Docs_Data docdata " + 
			"on shp.ShipmentNo = docdata.ShipmentNo   OR shp.ArchiveNo = docdata.ShipmentNo " + 
			"inner join Scandocs_Doc_Type doctype " + 
			"on docdata.Department = doctype.Department and docdata.DocType = doctype.DocType " +
			"Left outer join Tango_Invoices invoice "+
			"on docdata.ShipmentNo = invoice.ShipmentNo "+
			"where docdata.DocumentDeleted = 'N'";
	
	
	static String SCANDOC_PRESHIPNO_SEARCH_QUERY =  
			"select distinct CASE WHEN  LEFT(docdata.ShipmentNo,1)='X'  THEN ''	ELSE  docdata.ShipmentNo END 'ShipmentNoX',docdata.ShipmentNo,"+ 
			"docdata.DocsDataPK, docdata.DocId, "+
			"isnull(docdata.InvoiceNo, 'Not Available') InvoiceNo,  "+ 
			"docdata.department, docdata.docType,   "+
			"docdata.FileSize ,  docdata.DateScanned ,  docdata.Branch, doctype.DocDisplayOrder,  "+  
			"doctype.AccessLevel   from 	 "+
			"Scandocs_Docs_Data_Preshipno preship  "+
			"inner join Scandocs_Docs_Data docdata "+
			"on preship.docid = docdata.DocId "+
			"inner join Scandocs_Doc_Type doctype  "+ 
			"on docdata.Department = doctype.Department and docdata.DocType = doctype.DocType  "+ 		
			"where docdata.DocumentDeleted = 'N'  ";

	static String SHIPMENT_SEARCH_QUERY =  "SELECT count(1) FROM Shipment shp where ";
	
	static String CREATE_CHANGE_LOG = "insert into Scandocs_Changes_Log "
	+ "(ShipmentNumber, ArchiveNumber, Department, DocType, DocNumber, Change, CreatedBy) "
	+ " values (?,?,?,?,?,?,?)"; 
	
	static String UPDATE_DOC_TYPE = "Update Scandocs_Docs_Data set DocumentTypeChanged = 'Y', DocType = ? , UpdatedBy =?, UpdatedDate= ? where DocsDataPK = ? ";
	
	static String UPDATE_DOC_DELETED = "Update Scandocs_Docs_Data set DocumentDeleted = 'Y' where DocsDataPK = ?  ";
	
	static String UPDATE_DOC_TRANS_DELETED = "DELETE OPENQUERY (AUKOFAXTEST, 'Select * from DBSCapture.dbo.Document_Transaction where Scandocs_docid = ";
	
	static String GET_USER_BYID = "SELECT * FROM Scandocs_Users where UserID = ?";
	static String AUTHENTICATE_USER = "SELECT count(*) FROM Scandocs_Users where UserID = ? and Password = ?";
	static String CREATE_USER = "insert into Scandocs_Users (UserID, Password, FirstName, LastName, Company, Email, Notes, UserGroup, AccessLevel, ActiveUser, CreatedBy) "
							  + " values (?,?,?,?,?,?,?,?,?,?,?)"; 
	static String EDIT_USER = "update Scandocs_Users set Company = ?, Notes = ?, UserGroup = ?, AccessLevel = ?, email = ?, UpdatedBy = ?, UpdatedDate = ?  where UsersPK = ? "; 
	static String DELETE_USER = "delete from Scandocs_Users where UsersPK = ?";
	
	static String SEARCH_USERS = "SELECT  UsersPK ,UserID  ,FirstName ,LastName ,Company ,Email ,notes, CreatedBy , "
										+ "	UserGroup , AccessLevel FROM Scandocs_Users where ActiveUser ='Y'";
	
	static String GET_CUR_PWD = "SELECT Password from Scandocs_Users where UsersPK = ?";
	static String UPADTE_PASSWORD = "Update Scandocs_Users set password = ? where UsersPK = ?";
	static String Auto_Email = "SELECT Email FROM Scandocs_Users";



}
