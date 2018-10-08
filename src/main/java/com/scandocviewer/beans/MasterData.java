package com.scandocviewer.beans;

import java.util.List;

public class MasterData implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<ScandocData> scandocData;
	private User user;
	
	public List<ScandocData> getScandocData() {
		return scandocData;
	}
	public void setScandocData(List<ScandocData> scandocData) {
		this.scandocData = scandocData;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

}
