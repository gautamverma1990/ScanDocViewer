 
	
 	var services = angular.module('scandocApp.services',[]);
	 
	services.factory('AuthenticationService', function($http, $rootScope, $cookies) {

 		    var service = {};
		
		    service.login = function (userId, password, callback) { 
 		    	
		    	var userObj = {userId : userId, password : password};	 
		    	
		    	$http.post('/Scandocviewer/user/authenticate', userObj)
		    	.then(
		    			/* On Success*/
		    			function (response) { 
 		    				return callback('Success', response.data);
		    			},
		    			
		    			/* On Error*/
		    			function (response) { 
 		    				callback('Failure', response.data);
		    			}
		    	); 
		    	
		    };		
		    
		    service.changePassword = function (usersPK, currPassword, newPassword, callback) { 
	 		    	
			    	var userObj = {usersPK : usersPK, currPassword : currPassword, newPassword : newPassword};	 
			    	
			    	$http.post('/Scandocviewer/user/changePassword', userObj)
			    	.then(
			    			function (response) { return callback('Success', response.data); }, 
			    			
			    			function (response) { return callback('Failure', response.data); }
			    	); 
			    	
			    };	
		    
		    service.saveCredentials = function (userData ) {
		    	
		    	 $rootScope.globals = {
	                 currentUser: {
	                	 userId: userData.userId,
	                	 userData: userData
	                 }
		         }; 
 		    	
		    	// store user details in globals cookie that keeps user logged in for 1 day (or until they logout)
		            
		    	 	var cookieExp = new Date();
		            cookieExp.setDate(cookieExp.getDate() + 1);
		            
		            $cookies.putObject('globals', $rootScope.globals, { expires: cookieExp });
		    	
		    };	
		    
		    service.ClearCredentials = function() {
	            $rootScope.globals = {};
	            $cookies.remove('globals');
	            $http.defaults.headers.common.Authorization = 'Basic';
	        }
		  
		    return service;
	});
	
	
	
	services.factory('SearchService', function($http, $window) {

		    var service = {};
	
	    service.search = function (refType, refValue, userGroup, accessLevel, callback) { 
 	    	
	    	var searchURI= "/Scandocviewer/search/"+refType+"/"+refValue+"/"+userGroup+"/"+accessLevel; 
 	    	
	    	$http.get(searchURI)
	    	.then(
 	    			function (response) { 
 	    				return callback('Success', response.data);
	    			}, 
 	    			function (response) { 
		    				callback('Failure', response.data);
	    			}
	    	); 
	    	
	    };	
	    
	    service.showAll = function (callback) { 
 	    	
	    	$http.get("/Scandocviewer/showAll")
	    	.then(
 	    			function (response) { return callback('Success', response.data); },	    			
 	    			function (response) { return callback('Failure', response.data); }
	    	); 
	    	
	    };	
	    
	    service.switchDocType = function (currentUser, scanDocData, callback) { 
 	    	
	    	var data = {	user : currentUser,
	    					scandocData : scanDocData  };
	    	
	    	$http.post("/Scandocviewer/updateDocumentType", data)
	    	.then(
 	    			function (response) { return callback('Success', response.data); }, 
 	    			function (response) { return callback('Failure', response.data); }
	    	); 


	    };
	    
	    service.sendEmail = function(selectedData, emailTo, subject, message, email, callback){
	    	
	    	var emailData = { emailTo : emailTo, 
	    					  subject : subject,
	    					  message : message,
	    					  emailFrom: email,
	    					  selectedScandocs : selectedData
	    					}
	    	
	    	$http.post("/Scandocviewer/sendEmail", emailData)
	    	.then(
 	    			function (response) { return callback('Success', response.data); }, 
 	    			function (response) { return callback('Failure', response.data); }
	    	);
 	    	
	    };
	    
	    
	    service.printMergedPdf = function(selectedData, callback){
	    	
	    	var emailData = { selectedScandocs : selectedData } 
 	    	
	    	$http.post("/Scandocviewer/printMergedPdf", emailData, {responseType: 'arraybuffer'})
	    	.then(
 	    			function (response) {  
   	    		        
 	    				var mergedfile = new Blob([response.data], { type: 'application/pdf' });
 	    				
 	    				if( $window.navigator &&  $window.navigator.msSaveOrOpenBlob){
 		  	    		    $window.navigator.msSaveOrOpenBlob(mergedfile, "PrintSelectedDocs.pdf");
 	    				}else{
 		  	    		    var mergedfileURL = URL.createObjectURL(mergedfile);
 		                    $window.open(mergedfileURL);  
 	    				}   	    		    
	  	    		 
 	    				return callback('Success'); 
  	    			}, 
 	    			function (response) { 
 	    				return callback('Failure', response.data); 
 	    			}
	    	); 
 	    	
	    };
	    
	    
	    service.deleteDocuments = function(currentUser, scanDocData, callback){
	    	
	    	var data = {	user : currentUser, scandocData : scanDocData  };
	     
	    	$http.post("/Scandocviewer/deleteDocuments", data)
	    	.then(
 	    			function (response) { return callback('Success', response.data); }, 
 	    			function (response) { return callback('Failure', response.data); }
	    	);
	    };
	    
	  
	    return service;
	});
	
	
	
	
	services.factory('AddUserService', function($http) {

	    var service = {};

	    service.addUser = function ( userObj, callback) { 
 	    	
	    	$http.post("/Scandocviewer/user/add", userObj)
	    	.then(
 	    			function (response) { 
 	    				return callback('Success', response.data);
	    			},
	    			
 	    			function (response) { 
		    				callback('Failure', response.data);
	    			}
	    	); 
	    	
	    };	 
 
  
	    return service;
	});
	
	
	services.factory('UserManagementService', function($http) {
		 var service = {};
			
		 service.search = function (searchType, searchValue, callback) { 
 	    	
	    	var searchURI= "/Scandocviewer/user/search/"+searchType+"/"+searchValue 
 	    	
	    	$http.get(searchURI)
	    	.then(
 	    			function (response) { 
 	    				return callback('Success', response.data);
	    			}, 
 	    			function (response) { 
		    				callback('Failure', response.data);
	    			}
	    	); 
		    	
		 };	
		    
		 service.showAll = function (callback) {  
		    	
		    	$http.get("/Scandocviewer/user/showAll")
		    	.then(
 		    			function (response) { return callback('Success', response.data); },
		    			
 		    			function (response) { return callback('Failure', response.data); }
		    	); 
		    	
		    };	
		    service.autoEmail=function(callback){  
		    	
		    	$http.get("/Scandocviewer/user/showAllEmails")
		    	.then(
 		    			function (response) { return callback('Success', response.data); },
		    			
 		    			function (response) { return callback('Failure', response.data); }
		    	); 
		    	
		    
		    	
		    };
		    
		    
		 service.deleteUser = function (user, callback) {  
  		    	
		    	$http.post("/Scandocviewer/user/delete", user)
		    	.then(
 		    			function (response) { return callback('Success', response.data); },
		    			
 		    			function (response) { return callback('Failure', response.data); }
		    	); 
		    	
		    };	
		    
		    
		 service.editUser = function (user, callback) {   
			    	$http.post("/Scandocviewer/user/edit", user)
			    	.then(
 			    			function (response) { return callback('Success', response.data); }, 
 			    			function (response) { return callback('Failure', response.data); }
			    	); 
			    	
			    };	
		  
		 return service;
		});
	 
	
 
	services.factory('utilService', function($http, $rootScope, $cookies) {
		
		 var service = {};
		 
		 service.getUserGroup = function () {  
			 
			 $rootScope.globals = $cookies.getObject('globals') || {};			 
			 
			 var currentUser = $rootScope.globals.currentUser; 
 			 
			 return currentUser.userData.userGroup ;
		  };
		  
		  
		 service.getCurrentUser = function () {  
				 
				 $rootScope.globals = $cookies.getObject('globals') || {};			 
				 
				 var currentUser = $rootScope.globals.currentUser; 
 				 
				 return currentUser.userData ;
		  };
		  
		  service.showMenuBar = function () {  
				 
			  $rootScope.showAppMenu = true;  
			  
			  $rootScope.globals = $cookies.getObject('globals') || {};			 
			  
			  var currentUser = $rootScope.globals.currentUser.userData;  
			  
			  if(currentUser.userGroup == 'A')
				  $rootScope.showUserManagement = true;
			  
			  
			  $rootScope.userFullName = currentUser.firstName + ' '+ currentUser.lastName;

		  };
		  
		  return service;
	});
	
	
 