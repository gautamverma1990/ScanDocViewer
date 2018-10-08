
package com.scandocviewer.handler;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scandocviewer.beans.ScandocData;
import com.scandocviewer.beans.User;
import com.scandocviewer.exceptions.ScandocException;
import com.scandocviewer.services.UserService;
import com.scandocviewer.utils.ScandocUtils;
 
 
@RestController
public class UserHandler {
	
	@Autowired
	Environment env;
	
	@Autowired
	UserService userService;
	
 	@SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/user/authenticate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity authenticateUser(@RequestBody User user) { 
    	
        User userDetails;
		try {
			
			userDetails = userService.getUser(user.getUserId(), user.getPassword());
			
			if (userDetails == null) {
	            return new ResponseEntity(ScandocUtils.getJSONMsg(env.getProperty("errMsg.loginFailed")), HttpStatus.NOT_FOUND);
	        }
	        return new ResponseEntity<User>(userDetails, HttpStatus.OK);
		} catch (ScandocException e) {
            return new ResponseEntity(ScandocUtils.getJSONMsg(e.getMessage()), HttpStatus.NOT_FOUND);
		} 
        
      
	} 
 	
 	
 	@SuppressWarnings({ "rawtypes", "unchecked"})
    @RequestMapping(value = "/user/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity addUser(@RequestBody User user) {  
 		try {
 			
 			User userDetails = userService.getUserByID(user.getUserId());
 	    	
 	    	if( null != userDetails ){
 	            return new ResponseEntity(ScandocUtils.getJSONMsg(MessageFormat.format(env.getProperty("errMsg.userExists"), user.getUserId())) , HttpStatus.NOT_FOUND);
 	    	}else{  
 	    		userService.addUser(user); 
 	            return new ResponseEntity(ScandocUtils.getJSONSuccessMsg(MessageFormat.format(env.getProperty("successMsg.userAdded"), user.getUserId())) , HttpStatus.OK);
 	    	} 
 			
 		}catch (ScandocException e) {
            return new ResponseEntity(ScandocUtils.getJSONMsg(e.getMessage()), HttpStatus.NOT_FOUND);
		}  
        
	} 
 	
	@SuppressWarnings({ "rawtypes", "unchecked"})
    @RequestMapping(value = "/user/changePassword", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity changePassword(@RequestBody Map<String,String> userData) {  
  
    	 try {
			userService.changePassword(userData.get("usersPK"), userData.get("currPassword"), userData.get("newPassword"));
			
		} catch (ScandocException e) {
            return new ResponseEntity(ScandocUtils.getJSONMsg(e.getMessage()) , HttpStatus.NOT_FOUND); 
		}
    	 
         return new ResponseEntity(ScandocUtils.getJSONSuccessMsg(env.getProperty("successMsg.changePassword")) , HttpStatus.OK);
     	 
        
	} 
 	
 	
 	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/user/search/{searchType}/{searchValue}", method = RequestMethod.GET)
	 public ResponseEntity searchUsers(@PathVariable("searchType") String searchType, @PathVariable("searchValue") String searchValue) { 
		
 			try{
		        List<User> usersList = userService.searchUsers(searchType, searchValue);
		         
		        if (CollectionUtils.isEmpty(usersList)) { 
		            
		            return new ResponseEntity((ScandocUtils.getJSONMsg(env.getProperty("errMsg.usersSearchedNotFound"))),HttpStatus.NOT_FOUND);
		        } 
		        return new ResponseEntity<List<User>>(usersList, HttpStatus.OK);
	        
		 	}catch (ScandocException e) {
		        return new ResponseEntity(ScandocUtils.getJSONMsg(e.getMessage()), HttpStatus.NOT_FOUND);
			}  
	    }
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/user/showAll", method = RequestMethod.GET)
	 public ResponseEntity showAllUsers() { 
		
		  try{
		        List<User> usersList = userService.getAllUsers();
		         
		        if (CollectionUtils.isEmpty(usersList)) { 
		        	
		            return new ResponseEntity((ScandocUtils.getJSONMsg(env.getProperty("errMsg.usersNotFound"))),HttpStatus.NOT_FOUND);
		        }
		  
		        return new ResponseEntity<List<User>>(usersList, HttpStatus.OK);
			}catch (ScandocException e) {
		        return new ResponseEntity(ScandocUtils.getJSONMsg(e.getMessage()), HttpStatus.NOT_FOUND);
			}   
	    }
 	
	
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/user/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity deleteUser(@RequestBody User user) {  
    	try{
    		
    	  userService.deleteUser(user); 
          return new ResponseEntity(ScandocUtils.getJSONSuccessMsg(MessageFormat.format(env.getProperty("successMsg.userDeleteSuccess"), user.getUserId())) , HttpStatus.OK);
    	 
	    }catch (ScandocException e) {
	        return new ResponseEntity(ScandocUtils.getJSONMsg(e.getMessage()), HttpStatus.NOT_FOUND);
		}  
        
	} 
    
    
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/user/edit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity editUser(@RequestBody User user) {  
  
    	try{
	    	  userService.updateUser(user);
	          return new ResponseEntity(ScandocUtils.getJSONSuccessMsg(MessageFormat.format(env.getProperty("successMsg.userEditSuccess"), user.getUserId())) , HttpStatus.OK);
	          
	    }catch (ScandocException e) {
	        return new ResponseEntity(ScandocUtils.getJSONMsg(e.getMessage()), HttpStatus.NOT_FOUND);
		}  
	    	 
        
	} 
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/user/showAllEmails", method = RequestMethod.GET)
    public ResponseEntity showAuto_Email(){
    	 
		
		  try{
		        List<User> usersList = userService.Auto_Email();
		         
		        if (CollectionUtils.isEmpty(usersList)) { 
		        	
		            return new ResponseEntity((ScandocUtils.getJSONMsg(env.getProperty("errMsg.usersNotFound"))),HttpStatus.NOT_FOUND);
		        }
		  
		        return new ResponseEntity<List<User>>(usersList, HttpStatus.OK);
			}catch (ScandocException e) {
		        return new ResponseEntity(ScandocUtils.getJSONMsg(e.getMessage()), HttpStatus.NOT_FOUND);
			}   
	    
    }
 
 

}

 