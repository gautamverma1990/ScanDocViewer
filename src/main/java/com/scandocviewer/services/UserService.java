package com.scandocviewer.services;

import java.util.List;

import com.scandocviewer.beans.User;
import com.scandocviewer.exceptions.ScandocException;

public interface UserService {

	User getUser(String userName, String password) throws ScandocException;

	void addUser(User user) throws ScandocException;

	void updateUser(User user) throws ScandocException;

	void deleteUser(User user) throws ScandocException;

	User getUserByID(String userId) throws ScandocException;

	List<User> getAllUsers() throws ScandocException;

	List<User> searchUsers(String searchType, String searchValue) throws ScandocException;
	
	void changePassword(String usersPK, String currPassword, String newPassword) throws ScandocException;
	
	List<User> Auto_Email() throws ScandocException;

}
