 
     
    var ctrl = angular.module('scandocApp.controllers', []);
     
    ctrl.controller('LoginController', ['$scope','AuthenticationService','$location', '$rootScope',
    	
    	function($scope, AuthenticationService, $location, $rootScope) { 
    	
    		
    		initLoginController();
    	  
    		$scope.login = function() {
    		  
    			$scope.dataLoading = true; 
     		  
    			AuthenticationService.login($scope.username, $scope.password, function (status, response) {  
            	  
             	  
    				if (status=='Success') {
    			    
    					hideErrorMessage();
                	  
    					AuthenticationService.saveCredentials(response);
                	  
    					$location.path('/home');
                	  
    				}else{
                 	  showErrorMessage(response.errorMessage);
    				}  
    		   });
              
	           function showErrorMessage(message){
	        	  $scope.showError = true;	
	         	  $scope.errorMessage =message;
	           }   
	           function hideErrorMessage(){
	         	  $scope.showError = false;	
	          	  $scope.errorMessage ='';
	           }  
              
           	 	$scope.dataLoading = false;

    		};
          
          
	        function initLoginController(){
	        	  
		    	  $scope.showError = false;	
		    	  $scope.errorMessage ="";
		    	  $rootScope.showAppMenu = false; 
		    	  $rootScope.showUserManagement = false; 
		    	  
		    	  AuthenticationService.ClearCredentials();
	        	  
	        }
          
    	}]);
    
    
    
    
    
    ctrl.controller('homeController', ['$scope','$routeParams', '$rootScope', '$window', 'SearchService', 'utilService', '$modal',
    	
    	function($scope,$routeParams, $rootScope, $window, SearchService, utilService, $modal) {  
		  	 
    		  initSearchScreen();
    		  //Assiging Value from URL to local variable
    		  var ref = $routeParams.ref;
    		  var refValue = $routeParams.refValue;

			  $scope.validateSearch= function(){ 
				  
				  if($scope.refValue == null || $scope.refValue.trim().length == 0){
 					  $scope.isSearchInvalid= true;
					  $scope.searchInvalidMsg= 'Reference Value can not be empty';				  
				  } else if( $scope.refType == 'stt' &&  
						  ( $window.isNaN($scope.refValue) ||  $scope.refValue.trim().length > 14 ) ){
 					  $scope.isSearchInvalid= true;
					  $scope.searchInvalidMsg= 'Please enter valid STT number';
				  }else if( $scope.refType == 'shipmentNo' &&  
						  (  $scope.refValue.trim().length > 10 ) ){
 					  $scope.isSearchInvalid= true;
					  $scope.searchInvalidMsg= 'Please enter valid shipment number';
				  }else{
 					  $scope.isSearchInvalid= false;
					  $scope.searchInvalidMsg= '';
				  } 
 						  
				  return  $scope.isSearchValid;  
					  
			  } 
			  
			  $scope.search = function(){  
 				  
				  if(!$scope.isSearchInvalid){
					  	 $scope.dataLoading = true;
						 
					  	 SearchService.search($scope.refType, $scope.refValue, utilService.getCurrentUser().userGroup, utilService.getCurrentUser().accessLevel, 
								 function (status, response) {   
					  		 		 $scope.dataLoading = false; 
 					  		 		 $('#selectAll').prop('checked',false);
 
									 if(status == 'Success'){
										 $scope.scandocDataList  = response;
										 $scope.searchErroMessage = null;
									 } else{
										 $scope.scandocDataList = null;
										 $scope.searchErroMessage = response.errorMessage;
									 }
					     			 
						 }); 
				  } 
			  } 
			  
			//Added By Dhirendra 
			// Passing the reference and its value in URL
			  if(ref != null && refValue != null){
    			  $scope.refType = ref;
    			  $scope.refValue = refValue;
    			  console.log($scope);
    			  $scope.validateSearch();
    			  $scope.search();
    		  }
			  
			  
			  $scope.showAll = function(){
				  
					 SearchService.showAll(function (status, response) {  
  						 
						 if(status == 'Success'){
							 $scope.scandocDataList  = response;
							 $scope.searchErroMessage = null;
						 } else{
							 $scope.scandocDataList = null;
							 $scope.searchErroMessage = response.errorMessage;
						 }
				     			 
					 }); 
			  }
			  
			  $scope.switchDocType = function(data){
				  
					 SearchService.switchDocType(utilService.getCurrentUser(), [data], function (status, response) {   
						 if(status == 'Success'){
							 console.info(response);
							 data.accessLevel  = data.accessLevel == "E" ? "I" : "E";
							 data.docType = response.successMessage;
 							 
 						 }  				     			 
					 }); 
			  }
			  
			  $scope.opeEmailDialog = function(){ 
 				  
				  var selectedData =   $scope.scandocDataList.filter(function(data){
															   	  		return data.selected
															   		 });
					
				  if(selectedData.length> 0){ 
					  
					  var modalInstance = $modal.open({
					        templateUrl: 'sendEmail.jsp',
					        controller: 'sendEmailCtrl',
					        resolve: {
					        	selectedData: function () {
					            return selectedData;
					          }, SearchService : function(){
					        	  return SearchService;
					          }, utilService : function(){
					        	  return utilService;
					          }
					        }
					   }); 
				  }
				  /*else{
					  
					    var modalInstance = $modal.open({
					        templateUrl: 'selectionAlert.jsp',
					        controller : 'selectionAlertCtrl'					        
					    });
				  }*/
				  

			  }
			  
			  $scope.printSelectedDocs = function(){
				  
				  var selectedData =   $scope.scandocDataList.filter(function(data){
															   	  		return data.selected
															   		 });
				  if(selectedData.length> 0){ 
					  $scope.printLoading = true;
					  SearchService.printMergedPdf( selectedData ,  function (status, response) { 
					      console.info(status);		
						  $scope.printLoading = false; 
					  }); 
				  }
 				   
			  }
			  
			  
			  $scope.deleteDocuments = function(){
				  var selectedData =   $scope.scandocDataList.filter(function(data){
															   	  		return data.selected
															   		 });
				  if(selectedData.length> 0){ 
					  
					  var modalInstance = $modal.open({
					        templateUrl: 'confirmDelDoc.jsp',
					        controller: 'ConfirmDeleteDocsCtrl',
					        resolve: {
					        	selectedData: function () {
					            return selectedData;
					          }
					        }
					      });
					    
					    
				    modalInstance.result.then(function (selectedData) {
 				        
				        SearchService.deleteDocuments( utilService.getCurrentUser(), selectedData , function (status, response) { 
				        	    
				        	$scope.search();							 
						 }); 
				        
 				      }, function () {
				        console.info('Modal dismissed at: ' + new Date());
				      });
					  
				  }/*else{
					  var modalInstance = $modal.open({
					        templateUrl: 'selectionAlert.jsp',
					        controller : 'selectionAlertCtrl'					        
					    });
				  }*/
				  
			  }
			  
			  $scope.selectAll= function(selectAll){ 
				  
				  var selectAllChecked = $(".checkboxAll").prop('checked');
				  
				  $(".checkbox").each(function(){
                       if($(this).prop('checked') != selectAllChecked)
							$(this).click();
				  });
			  }
			  
			 function initSearchScreen(){ 
				  $scope.isSearchInvalid = true;
				  $scope.refType = 'hbl';
				  utilService.showMenuBar();  
				  if(utilService.getUserGroup() == 'A' || utilService.getUserGroup() == 'S' )
					  $scope.showDelDocuments = true;   
			  }
 
    	}]);
    
    
    	ctrl.controller('addUserController', ['$scope','utilService', 'AddUserService',
    	
    	function($scope, utilService, AddUserService) { 
    		
    		initAddUsers();
    		
    		$scope.addUser = function(){  
				
    			var userObj = { userId: $scope.userId, password: $scope.password, firstName: $scope.firstName , lastName: $scope.lastName, 
    						    company: $scope.company, email : $scope.email, notes: $scope.notes, userGroup: $scope.userGroup, 
    						    accessLevel: $scope.accessLevel
    						  }
    			
  					  
			  AddUserService.addUser(userObj, function (status, response) {  
 					 
					 if(status == 'Success'){
 						 $scope.showAddUSerError = false;
						 $scope.showAddUSersuccess = true;
		         	     $scope.addUSerErrorMsg = null;
		         	     $scope.addUSerSuccessMsg = response.successMessage;
					 } else{
 						 $scope.showAddUSerError = true;
						 $scope.showAddUSersuccess = false;
		         	     $scope.addUSerErrorMsg = response.errorMessage;
		         	     $scope.addUSerSuccessMsg = null;

					 }
			     			 
				 }); 
				   
			  }
    		
    		
    		function initAddUsers(){ 

				  utilService.showMenuBar();  

				  $scope.userGroup = 'U';
				  $scope.accessLevel = 'I'; 
		    	  $scope.showAddUSerError = false;	
		    	  $scope.showAddUSersuccess = false;	 
 			  }
 
          
    	}]);
    	
    	
    	
 	 
    	
    	
    	ctrl.controller('userManagementController', ['$scope','UserManagementService', 'utilService','$modal',
        	
        	function($scope, UserManagementService, utilService, $modal) { 
    		
    			initUserManagement(); 
        		
    			$scope.searchUsers = function(){ 
    				
    				$scope.dataLoading = true;
         			
        			if($scope.getSearcedVal() &&  $scope.getSearcedVal().trim().length > 0 ){
        				
        				UserManagementService.search($scope.searchType, $scope.getSearcedVal(), function (status, response) {  	 
             				
        					$scope.dataLoading = false;
 	   							 
	   						 if(status == 'Success'){
	   							 $scope.usersDataList  = response;
	   							 $scope.searchErroMessage = null;
	   						 } else{
	   							 $scope.usersDataList = null;
	   							 $scope.searchErroMessage = response.errorMessage;
	   						 }
   				     			 
   					 	}); 
        				
        			}else{
        				UserManagementService.showAll(function (status, response) { 
        					
        					$scope.dataLoading = false; 
        					
        					 if(status == 'Success'){
	   							 $scope.usersDataList  = response;
	   							 $scope.searchErroMessage = null;
	   						 } else{
	   							 $scope.usersDataList = null;
	   							 $scope.searchErroMessage = response.errorMessage;
	   						 }
   				     			 
   					 	}); 
        			}
    				

     			} 
			  
    			$scope.showAllUsers = function(){ 
    				
    				$scope.dataLoading = true;

    				UserManagementService.showAll(function (status, response) {  	
    					
    					$scope.dataLoading = false; 

    					 if(status == 'Success'){
   							 $scope.usersDataList  = response;
   							 $scope.searchErroMessage = null;
   						 } else{
   							 $scope.usersDataList = null;
   							 $scope.searchErroMessage = response.errorMessage;
   						 }
				     			 
					 }); 
    			}
    			
    			 $scope.getSearcedVal = function(){
    				 if($scope.searchType == 'userGroup')
    					 return  $scope.userGroupList;
    				 else if($scope.searchType == 'accessLevel')
    					 return  $scope.accessLevelList;
    				 else 
    					 return $scope.searchValue;
    			 }
    			 
    			 
    			 
    			 $scope.editUser = function(selectedUser){
     				 
     				 $scope.selectedUser = selectedUser;
    				 
				    var modalInstance = $modal.open({
				        templateUrl: 'editUser.jsp',
				        controller: 'editUserCtrl',
				        resolve: {
				        	selectedUser: function () {
				            return $scope.selectedUser;
				          }, UserManagementService : function(){
				        	  return UserManagementService;
				          }
				        }
				      });
				    
				    
				    modalInstance.result.then(function (selectedUser) {
  				      }, function () {
 						 $scope.searchUsers();
				      });
    				 
    			 } 
    			 
    			 
    			 
    			 $scope.deleteUser = function(selectedUser){
      				 
    				 $scope.selectedUser = selectedUser;
    				 
				    var modalInstance = $modal.open({
				        templateUrl: 'confirmDelete.jsp',
				        controller: 'ConfirmDeleteCtrl',
				        resolve: {
				        	selectedUser: function () {
				            return $scope.selectedUser;
				          }
				        }
				      });
				    
				    
				    modalInstance.result.then(function (selectedUser) {
 				        
				        UserManagementService.deleteUser(selectedUser, function (status, response) {  					 
							 
	    					 if(status == 'Success'){
	   			         	     $scope.showUsrMngmntErrMsg = false;
	    						 $scope.searchUsers();
	   						 } else{
	   							 $scope.usrMngmntErrMsg = response.errorMessage;
	   			         	     $scope.showUsrMngmntErrMsg = true;
	   						 }
					     			 
						 }); 

				        
 				      }, function () {
				        console.info('Modal dismissed at: ' + new Date());
				      });
    				 
    			 } 
			  
			  
			  function initUserManagement(){
				  utilService.showMenuBar();  
				  
				  $scope.searchType = 'userId';  
				  $scope.userGroupList = 'U';  
				  $scope.accessLevelList = 'E';  
				  $scope.showUsrMngmntErrMsg = false;

			  }
 
              
        	}]); 
    	
    	
  	     
  		ctrl.controller('ConfirmDeleteCtrl', ['$scope','$modalInstance', 'selectedUser',
     	
         	function($scope, $modalInstance, selectedUser) { 
  			
   				
  				 $scope.selectedUser = selectedUser;
     			 $scope.selectedUserId = selectedUser.userId; 

     			  $scope.ok = function () {
     			    $modalInstance.close( $scope.selectedUser);
     			  };

     			  $scope.cancel = function () {
     			    $modalInstance.dismiss('cancel');
     			  };
  			 
  			}
  		]); 
  		
  		
   		
  		
  		ctrl.controller('editUserCtrl', ['$scope','$modalInstance', 'selectedUser', 'UserManagementService',
  	     	
         	function($scope, $modalInstance, selectedUser, UserManagementService) {  
 
  				 $scope.editUser = angular.copy(selectedUser);
 
     			  $scope.save = function () {
     				  console.info('save');
	     				  
				      UserManagementService.editUser($scope.editUser, function (status, response) { 
							 if(status == 'Success'){
 					         	  $scope.editUserErrorMsg = null;
					         	  $scope.editUSerSuccessMsg = response.successMessage;
					         	  $scope.showEditUSersuccess = true;
					         	  $scope.showEditUSerError = false;

							 } else{
 					         	  $scope.editUSerSuccessMsg = null;
					         	  $scope.editUserErrorMsg = response.errorMessage;
					         	  $scope.showEditUSerError = true;
					         	  $scope.showEditUSersuccess = false;
							 }
					     			 
						}); 
     			  };

     			  $scope.close = function () {
      				 $modalInstance.dismiss('cancel');
     			  };
  			 
  			}
  		]); 
  		
  		
   		//Edited by Gautam Verma
  		
  		ctrl.controller('sendEmailCtrl', ['$scope','$modalInstance', 'selectedData', 'UserManagementService','SearchService', 'utilService',
  	     	
         	function($scope, $modalInstance, selectedData, UserManagementService,SearchService, utilService) { 
  			UserManagementService.autoEmail(function (status, response) {
			 	
  				if(status == 'Success'){
			 		var choices = [];
			 		for(i=0;i<response.length;i++){
			 			choices[i] = response[i].email;
			 			
			 			
			 		}  						 
					 $scope.searchErroMessage = null;
				 } else{
					 $scope.usersDataList = null;
					 $scope.searchErroMessage = response.errorMessage;
				 }
				 var emailData=localStorage.getItem('emails', $scope.emailTo);
  				 var emailIds=emailData.split(" ");
  				// console.log('LocalStorage Data -----', emailIds);
  				// console.log('choices Data -----', choices);
  				 choices = choices.concat(emailIds);
  				// console.log('choices Data after push -----', choices);
			 	 choices = choices.filter(function (v, i, a) { return a.indexOf(v) === i});
	             $scope.items = choices;	 
	            // console.log('Emails',$scope.items);
	             $scope.text = '';
	             $scope.minlength = 1;
	             $scope.selected = {};
	            
  			});   
  			
    				
  				$scope.sendEmail = function () { 
  					
  					$scope.emailLoading = true;
  			  		
  			  		$scope.emailTo = $("#emailTo").val();//+emailId;
  			    	var z=localStorage.getItem('emails', $scope.emailTo);
  			    	//console.log(z);
  			  		if(z==null){
  			  		localStorage.setItem('emails', $scope.emailTo);
  			  		}else{
  			  		localStorage.setItem('emails', z+' '+$scope.emailTo);
  			  		}
  		
  			  		
  			  		
  				    SearchService.sendEmail(selectedData, $scope.emailTo, $scope.subject, $scope.message, utilService.getCurrentUser().email, function (status, response) { 
  	  					$scope.emailLoading = false;
						 if(status == 'Success'){
 		     				  $scope.emailErrorMsg = null;
				         	  $scope.emailSuccessMsg = response.successMessage;
				         	  $scope.showEmailSuccess = true;
				         	  $scope.showEmailError = false;
	
						 } else{
 		     				  $scope.emailSuccessMsg = null;
				         	  $scope.emailErrorMsg = response.errorMessage;
				         	  $scope.showEmailError = true;
				         	  $scope.showEmailSuccess = false;
						 }
				     			 
					}); 
	 			};
 
	 			$scope.close = function () {
 	 				$modalInstance.dismiss('cancel');
	 			};
  			 
  			}
  		]); 
  		
  		/*ctrl.controller('selectionAlertCtrl', ['$scope','$modalInstance',
  			function($scope, $modalInstance){
  			
  			$scope.ok = function () {
  				$modalInstance.dismiss();
 			};
  			
  		}]); */
      	
  		
  	  console.info (" creating ModalInstanceCtrl");
	     
		ctrl.controller('ConfirmDeleteDocsCtrl', ['$scope','$modalInstance', 'selectedData',
   	
       	function($scope, $modalInstance, selectedData) {  
   
   			  $scope.ok = function () {
   			    $modalInstance.close( selectedData);
   			  };

   			  $scope.cancel = function () {
   			    $modalInstance.dismiss('cancel');
   			  };
			 
			}
		]); 
		
		
		
		ctrl.controller('changePasswordController', ['$scope', 'AuthenticationService' , 'utilService',
	    	
	    	function($scope, AuthenticationService, utilService) { 
			
			  utilService.showMenuBar();  
				
			  $scope.changePassword = function () { 
 				  AuthenticationService.changePassword( utilService.getCurrentUser().usersPK , $scope.currPassword, $scope.password, function (status, response) { 
 					 if(status == 'Success'){
	     				  $scope.changePasswordErrorMsg = null;
			         	  $scope.changePasswordSuccessMsg = response.successMessage;
			         	  $scope.showchangePasswordSuccess = true;
			         	  $scope.showchangePasswordError = false;

					 } else{
	     				  $scope.changePasswordSuccessMsg = null;
			         	  $scope.changePasswordErrorMsg = response.errorMessage;
			         	  $scope.showchangePasswordError = true;
			         	  $scope.showchangePasswordSuccess = false;
					 }
				  }); 
 			  };
			
			}
		]);
		ctrl.directive('autocomplete', function($timeout) {
	  		  return {
	  		    controller: 'autocompleteController',
	  		    restrict: 'E',
	  		    replace: true,
	  		    scope: {
	  		      choices: '=',
	  		      enteredtext: '=',
	  		      minlength: '=',
	  		      selected: '='
	  		    },
	  		    templateUrl: 'emailAutocomplete.jsp',
	  		    /*template: `
		  		  <div class="autocomplete">
		  		  <input class="inputtext form-control" type="text" id="emailTo" ng-model="emailTo" placeholder="Please enter Email addresses " ng-keyup="filterItems()" /> 
			       
		  		  <div class="choices" ng-show="isVisible.suggestions">
		  		    <div class="choice" ng-repeat="choice in filteredChoices"
		  		      ng-click="selectItem(choice)">{{choice}}</div>
		  		  </div>
		  		</div>
	  		    `*/
	  		  }
	  		}).
	  		controller('autocompleteController', function($scope) {
	  		  
	  		  $scope.filteredChoices = ['a', 'b'];
	  		  $scope.isVisible = {
	  		    suggestions: false
	  		  };
	  		  
	  		  $scope.filterItems = function () {
	  		    if($scope.minlength <= $scope.emailTo.length) {
	  		      $scope.filteredChoices = querySearch($scope.emailTo);
	  		      $scope.isVisible.suggestions = $scope.filteredChoices.length > 0 ? true : false;
	  		    }
	  		    else {
	  		      $scope.isVisible.suggestions = false;
	  		    }
	  		  };
	  		  
	  		  
	  		  /**
	  		   * Takes one based index to save selected choice object
	  		   */
	  		  $scope.selectItem = function (choice) {
	  		    $scope.selected = choice;
	  		    $scope.emailTo = $scope.selected;
	  		    $scope.isVisible.suggestions = false;
	  		  };
	  		  
	  		  /**
	  		   * Search for states... use $timeout to simulate
	  		   * remote dataservice call.
	  		   */
	  		  function querySearch (query) {
	  		    // returns list of filtered items
	  		    return  query ? $scope.choices.filter( createFilterFor(query) ) : [];
	  		  }
	  		  
	  		  /**
	  		   * Create filter function for a query string
	  		   */
	  		  function createFilterFor(query) {
	  		    var lowercaseQuery = angular.lowercase(query);

	  		    return function filterFn(item) {
	  		      // Check if the given item matches for the given query
	  		      var label = angular.lowercase(item);
	  		      return (label.indexOf(lowercaseQuery) === 0);
	  		    };
	  		  }
	  		});
		