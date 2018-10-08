package com.scandocviewer.beans;

import java.io.Serializable;
import java.util.Date; 

 public class User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5210758128776874760L;
	
	private int usersPK;
	private String userId;
	private String password;
	private String firstName;
	private String lastName;
	private String company;
	private String email;
	private String notes;
	private String userGroup;
	private String accessLevel;
	private String activeUser;
	private String createdBy;
	private Date createdDate;
	private String updatedBy;
	private Date updatedDate;
	
 
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	} 
 
	public String getActiveUser() {
		return activeUser;
	}
	public void setActiveUser(String activeUser) {
		this.activeUser = activeUser;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getUserGroup() {
		return userGroup;
	}
	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
	}
	public String getAccessLevel() {
		return accessLevel;
	}
	public void setAccessLevel(String accessLevel) {
		this.accessLevel = accessLevel;
	}
	public int getUsersPK() {
		return usersPK;
	}
	public void setUsersPK(int usersPK) {
		this.usersPK = usersPK;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
 
	 
	

}
