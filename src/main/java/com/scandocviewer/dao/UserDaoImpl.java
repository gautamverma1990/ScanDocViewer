package com.scandocviewer.dao;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.scandocviewer.beans.User;
import com.scandocviewer.exceptions.ScanDocDataAccessException;

@Repository
public class UserDaoImpl implements UserDao{ 
								
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private static final Logger logger = Logger.getLogger(UserDaoImpl.class);  
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public User getUserById(String userId) {
		 
		try{
			 User user = (User) jdbcTemplate.queryForObject(DAOConstants.GET_USER_BYID, 
					 new Object[] { userId }, new BeanPropertyRowMapper(User.class));
		 
		        return user;
		}catch(EmptyResultDataAccessException  e){
			return null;
		}
		
	 }
	
	
	@Override
 	public User getUser(String userName, String password) throws ScanDocDataAccessException { 
		
		try{
			
			if(authenticateUser(userName, password)){
				return getUserById(userName);
			}
			
			return null; 
			
		}catch(Exception e){
			logger.error("UserDaoImpl.getUser(): Failed with Exception :", e);
			throw new ScanDocDataAccessException(e.getMessage());
		}
		
	
	 }
	
	
	private boolean authenticateUser(String userName, String password) {   
		
			Integer count = (Integer) jdbcTemplate.queryForObject(DAOConstants.AUTHENTICATE_USER, 
					new Object[] { userName,  password}, Integer.class); 
			
		    return count>0; 
	 }

	
	
	@Override
  	public void addUser(User user) throws ScanDocDataAccessException { 
		
		try{
			jdbcTemplate.update(DAOConstants.CREATE_USER,  new Object[] { user.getUserId(), user.getPassword(), user.getFirstName(), user.getLastName(), 
						 user.getCompany(), user.getEmail(), user.getNotes(), user.getUserGroup(), user.getAccessLevel(), "Y", "Scandocs" }); 
			
		}catch(Exception e){
			logger.error("UserDaoImpl.addUser(): Failed with Exception :", e);
			throw new ScanDocDataAccessException(e.getMessage());
		}
		
  	 
	 }
  	
	@Override
  	public void updateUser(User user) throws ScanDocDataAccessException { 
		
		try{
			
	  		jdbcTemplate.update(DAOConstants.EDIT_USER,  new Object[] { user.getCompany(), user.getNotes(), user.getUserGroup(), user.getAccessLevel(), user.getEmail(), "Scandocs", new Date(), user.getUsersPK()});
			
		}catch(Exception e){
			logger.error("UserDaoImpl.updateUser(): Failed with Exception :", e);
			throw new ScanDocDataAccessException(e.getMessage());
		}
		
		
	 
	 }
  	
	@Override
  	public void deleteUser(User user) throws ScanDocDataAccessException { 
		
		try{
	  		jdbcTemplate.update(DAOConstants.DELETE_USER,  new Object[] {user.getUsersPK()});
			
		}catch(Exception e){
			logger.error("UserDaoImpl.deleteUser(): Failed with Exception :", e);
			throw new ScanDocDataAccessException(e.getMessage());
		}
		
		
	 
	 }
	
	@Override
   	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<User> getAllUsers() throws ScanDocDataAccessException { 
		
		try{ 
			
			List<User> userList =  jdbcTemplate.query(DAOConstants.SEARCH_USERS,  new BeanPropertyRowMapper(User.class));

			return userList; 
			
		}catch(Exception e){
			logger.error("UserDaoImpl.getAllUsers(): Failed with Exception :", e);
			throw new ScanDocDataAccessException(e.getMessage());
		}

	 
	 }
	
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
 	public String getCurrentPassword(String UsersPK) throws ScanDocDataAccessException { 
		
		try{
			
			String currentPassword =  jdbcTemplate.queryForObject(DAOConstants.GET_CUR_PWD,  new Object[] {UsersPK}, String.class);

			return currentPassword; 
			
		}catch(Exception e){
			logger.error("UserDaoImpl.getCurrentPassword(): Failed with Exception :", e);
			throw new ScanDocDataAccessException(e.getMessage());
		}
		
	
	 
	 }
	
	@Override
  	public void changePassword(String UsersPK, String newPassword) throws ScanDocDataAccessException { 
		
		try{
			
	  		jdbcTemplate.update(DAOConstants.UPADTE_PASSWORD,  new Object[] { newPassword, UsersPK}); 
			
		}catch(Exception e){
			logger.error("UserDaoImpl.changePassword(): Failed with Exception :", e);
			throw new ScanDocDataAccessException(e.getMessage());
		}
		
	 
	 }
   	
   	@Override
   	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<User> searchUsers(String whereClause, String searchValue) throws ScanDocDataAccessException { 
   		
   		try{
   			
   			StringBuffer searchQuery = new StringBuffer(DAOConstants.SEARCH_USERS);
   			searchQuery.append(" and ").append(whereClause);	 
   	 		
   			List<User> userList =  jdbcTemplate.query(searchQuery.toString(),  new Object[] {  "%"+searchValue+"%" }, new BeanPropertyRowMapper(User.class));

   			return userList; 
			
		}catch(Exception e){
			logger.error("UserDaoImpl.searchUsers(): Failed with Exception :", e);
			throw new ScanDocDataAccessException(e.getMessage());
		}
		
   		
	 
	 }
   	@Override
   	@SuppressWarnings({ "rawtypes", "unchecked" })
	public
   	List<User> Auto_Email() throws ScanDocDataAccessException {
 		
		try{ 
			
			List<User> userList =  jdbcTemplate.query(DAOConstants.Auto_Email,  new BeanPropertyRowMapper(User.class));

			return userList; 
			
		}catch(Exception e){
			logger.error("UserDaoImpl.Auto_Email(): Failed with Exception :", e);
			throw new ScanDocDataAccessException(e.getMessage());
		}

	  
   		
   	}
   
}
