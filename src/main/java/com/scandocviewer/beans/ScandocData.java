package com.scandocviewer.beans;

import java.io.Serializable;
import java.util.Date;

public class ScandocData implements Serializable{	
	 
 	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String shipmentNo;
	private String archiveNo;
	private String masterbill;
	private String housebill;
	private String docsDataPK;
	private String docId;
	private String invoiceNo;
	private String department;
	private String docType;
	private String fileSize;
	private Date   dateScanned;
	private String branch;
	private String accessLevel;
	private String amsDocUrl;
	private int docDisplayOrder;
	private boolean selected = false;
	private String shipmentNoX;
	
	public String getShipmentNo() {
		return shipmentNo;
	}
	public void setShipmentNo(String shipmentNo) {
		this.shipmentNo = shipmentNo;
	}
	public String getArchiveNo() {
		return archiveNo;
	}
	public void setArchiveNo(String archiveNo) {
		this.archiveNo = archiveNo;
	}
	public String getMasterbill() {
		return masterbill;
	}
	public void setMasterbill(String masterbill) {
		this.masterbill = masterbill;
	}
	public String getHousebill() {
		return housebill;
	}
	public void setHousebill(String housebill) {
		this.housebill = housebill;
	}
	public String getDocsDataPK() {
		return docsDataPK;
	}
	public void setDocsDataPK(String docsDataPK) {
		this.docsDataPK = docsDataPK;
	}
 
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	 
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public Date getDateScanned() {
		return dateScanned;
	}
	public void setDateScanned(Date dateScanned) {
		this.dateScanned = dateScanned;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getAccessLevel() {
		return accessLevel;
	}
	public void setAccessLevel(String accessLevel) {
		this.accessLevel = accessLevel;
	} 
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public int getDocDisplayOrder() {
		return docDisplayOrder;
	}
	public void setDocDisplayOrder(int docDisplayOrder) {
		this.docDisplayOrder = docDisplayOrder;
	}
	public String getAmsDocUrl() {
		return amsDocUrl;
	}
	public void setAmsDocUrl(String amsDocUrl) {
		this.amsDocUrl = amsDocUrl;
	}
	public String getShipmentNoX() {
		return shipmentNoX;
	}
	public void setShipmentNoX(String shipmentNoX) {
		this.shipmentNoX = shipmentNoX;
	}
	

}
