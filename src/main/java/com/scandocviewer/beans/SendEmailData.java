package com.scandocviewer.beans;

import java.io.Serializable;
import java.util.List;

public class SendEmailData implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String emailTo;
	private String emailFrom;
	private String subject;
	private String message;
	private List<ScandocData> selectedScandocs;
	
	public String getEmailTo() {
		return emailTo;
	}
	public void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<ScandocData> getSelectedScandocs() {
		return selectedScandocs;
	}
	public void setSelectedScandocs(List<ScandocData> selectedScandocs) {
		this.selectedScandocs = selectedScandocs;
	}
	public String getEmailFrom() {
		return emailFrom;
	}
	public void setEmailFrom(String emailFrom) {
		this.emailFrom = emailFrom;
	}

}
