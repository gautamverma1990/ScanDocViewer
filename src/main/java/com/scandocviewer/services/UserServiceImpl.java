package com.scandocviewer.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.scandocviewer.beans.User;
import com.scandocviewer.dao.UserDao;
import com.scandocviewer.exceptions.ScanDocDataAccessException;
import com.scandocviewer.exceptions.ScandocException;
import com.scandocviewer.utils.ScandocUtils;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	Environment env;
	
	@Override
	public User getUser(String userId, String password) throws ScandocException{
		try{
			
			return userDao.getUser(userId, password);

		}catch (ScanDocDataAccessException e) {
			throw new ScandocException(env.getProperty("errMsg.database.opertion"));
		}
		
	}
	
	
	@Override
	public User getUserByID(String userId) throws ScandocException{
		try{
			return userDao.getUserById(userId);
		}catch (ScanDocDataAccessException e) {
			throw new ScandocException(env.getProperty("errMsg.database.opertion"));
		}
	}
	
	@Override
  	public void addUser(User user) throws ScandocException {  
		try{
	  		userDao.addUser(user);  			
		}catch (ScanDocDataAccessException e) {
			throw new ScandocException(env.getProperty("errMsg.database.opertion"));
		}
	 }
  	
	@Override
  	public void updateUser(User user) throws ScandocException {  
		try{
	  		userDao.updateUser(user);			
		}catch (ScanDocDataAccessException e) {
			throw new ScandocException(env.getProperty("errMsg.database.opertion"));
		}
	}
  	
	@Override
  	public void deleteUser(User user) throws ScandocException { 
		try{
	  		userDao.deleteUser(user); 
		}catch (ScanDocDataAccessException e) {
			throw new ScandocException(env.getProperty("errMsg.database.opertion"));
		}
	 }
	
	@Override
	public List<User> getAllUsers() throws ScandocException { 	
		try{
			return userDao.getAllUsers();  
		}catch (ScanDocDataAccessException e) {
			throw new ScandocException(env.getProperty("errMsg.database.opertion"));
		}
	 } 	
	
	@Override
	public List<User> searchUsers(String searchType, String searchValue) throws ScandocException {  
		try{
			return userDao.searchUsers(ScandocUtils.getUserWhereClause(searchType), searchValue);
			
		}catch (ScanDocDataAccessException e) {
			throw new ScandocException(env.getProperty("errMsg.database.opertion"));
		}
		
	} 
	
	@Override
	public void changePassword(String usersPK, String currPassword, String newPassword) throws ScandocException{
		try{
			
			String currPasswordinDB= userDao.getCurrentPassword(usersPK);
			
			if(currPasswordinDB.equals(currPassword)){
				
				userDao.changePassword(usersPK, newPassword);
				
			}else{
				throw new ScandocException(env.getProperty("errMsg.password.incorrect"));
			}
			
		}catch (ScanDocDataAccessException e) {
			throw new ScandocException(env.getProperty("errMsg.database.opertion"));
		}
		
	
		
	}
	
	public List<User> Auto_Email() throws ScandocException{
			 	
			try{
				return userDao.Auto_Email();  
			}catch (ScanDocDataAccessException e) {
				throw new ScandocException(env.getProperty("errMsg.database.opertion"));
			}
		 
	}

}
