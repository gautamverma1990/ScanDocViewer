package com.scandocviewer.dao;

import java.util.List;

import com.scandocviewer.beans.User;
import com.scandocviewer.exceptions.ScanDocDataAccessException;

public interface UserDao {

	User getUser(String userName, String password) throws ScanDocDataAccessException;

	void addUser(User user) throws ScanDocDataAccessException;

	void updateUser(User user) throws ScanDocDataAccessException;

	void deleteUser(User user) throws ScanDocDataAccessException;

	User getUserById(String userId) throws ScanDocDataAccessException;

	List<User> getAllUsers() throws ScanDocDataAccessException;

	List<User> searchUsers(String whereClause, String searchValue) throws ScanDocDataAccessException;

 
	String getCurrentPassword(String UsersPK) throws ScanDocDataAccessException;

	void changePassword(String UsersPK, String newPassword) throws ScanDocDataAccessException;
	
	List<User> Auto_Email() throws ScanDocDataAccessException;

}
