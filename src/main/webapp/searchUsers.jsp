<div>
   	 	<form name="userSearchForm" ng-submit="searchUsers()" role="form"> 

			 <div class="col-xs-5 col-sm-2">
			 	 <p class="pull-right">Search Type </p>
	        </div>
			<div class="col-xs-5 col-sm-2">
            	<select name="searchType" id ="searchType" class ="form-control mb-15" ng_model = "searchType">
			 		<option value="userId">UserId</option>
			 		<option value="firstName">First Name</option>
			 		<option value="lastName">Last Name</option>
					<option value="company">Company</option>
					<option value="userGroup">User Group</option>						
					<option value="accessLevel">Access Level</option>
				</select>
	         </div> 
	         <div class="col-xs-5 col-sm-2">
			 	<p class="pull-right">Search Value</p>
	        </div>
			 <div class="col-xs-5 col-sm-2"  ng-show="searchType == 'userId' || searchType == 'firstName' || searchType == 'lastName' || searchType == 'company'">
	           <input type="text" id ="searchValue" name="searchValue"  class ="form-control mb-15" ng_model = "searchValue"> 
 	        </div>
	        <div class="col-xs-5 col-sm-2" ng-show="searchType == 'userGroup'">
	             <select name="userGroupList" id ="userGroupList" class ="form-control mb-15" ng_model = "userGroupList">
			 		<option value="A">Admin</option>
			 		<option value="S">Super User</option>
			 		<option value="U">User</option> 
				</select>
 	        </div>
	       <div class="col-xs-5 col-sm-2" ng-show="searchType == 'accessLevel'">
	             <select name="accessLevelList" id ="accessLevelList" class ="form-control mb-15" ng_model = "accessLevelList">
			 		<option value="E">External</option>
			 		<option value="I">Internal</option>
 				</select>
 	        </div>
	        <div class="col-xs-3 col-sm-1">
	           <button type="submit" id ="search" class="btn btn-primary ">Search</button>
	        </div>
	        <div class="col-xs-3 col-sm-1">
	           <button type="button" id ="showALl" class="btn btn-primary" ng-click="showAllUsers();">Show All</button>
	           <img  ng-if="dataLoading" src="data:image/gif;base64,R0lGODlhEAAQAPIAAP///wAAAMLCwkJCQgAAAGJiYoKCgpKSkiH/C05FVFNDQVBFMi4wAwEAAAAh/hpDcmVhdGVkIHdpdGggYWpheGxvYWQuaW5mbwAh+QQJCgAAACwAAAAAEAAQAAADMwi63P4wyklrE2MIOggZnAdOmGYJRbExwroUmcG2LmDEwnHQLVsYOd2mBzkYDAdKa+dIAAAh+QQJCgAAACwAAAAAEAAQAAADNAi63P5OjCEgG4QMu7DmikRxQlFUYDEZIGBMRVsaqHwctXXf7WEYB4Ag1xjihkMZsiUkKhIAIfkECQoAAAAsAAAAABAAEAAAAzYIujIjK8pByJDMlFYvBoVjHA70GU7xSUJhmKtwHPAKzLO9HMaoKwJZ7Rf8AYPDDzKpZBqfvwQAIfkECQoAAAAsAAAAABAAEAAAAzMIumIlK8oyhpHsnFZfhYumCYUhDAQxRIdhHBGqRoKw0R8DYlJd8z0fMDgsGo/IpHI5TAAAIfkECQoAAAAsAAAAABAAEAAAAzIIunInK0rnZBTwGPNMgQwmdsNgXGJUlIWEuR5oWUIpz8pAEAMe6TwfwyYsGo/IpFKSAAAh+QQJCgAAACwAAAAAEAAQAAADMwi6IMKQORfjdOe82p4wGccc4CEuQradylesojEMBgsUc2G7sDX3lQGBMLAJibufbSlKAAAh+QQJCgAAACwAAAAAEAAQAAADMgi63P7wCRHZnFVdmgHu2nFwlWCI3WGc3TSWhUFGxTAUkGCbtgENBMJAEJsxgMLWzpEAACH5BAkKAAAALAAAAAAQABAAAAMyCLrc/jDKSatlQtScKdceCAjDII7HcQ4EMTCpyrCuUBjCYRgHVtqlAiB1YhiCnlsRkAAAOwAAAAAAAAAAAA==" />	           

	        </div>
        
        </form>
        
        <hr class="liner">
        
         <div class="alert alert-danger" ng-show="showUsrMngmntErrMsg">
		    	<strong>Error:</strong> <span>{{usrMngmntErrMsg}}</span>
		 </div>  
        
        <table class="table table-hover">
		    <thead>
		      <tr>
		      	<th></th>
		      	<th></th> 
		        <th>User ID</th>
		        <th>First Name</th>
		        <th>Last Name</th>
		        <th>Company</th>
		        <th>CreatedBy</th>
		        <th>UserGroup</th>
		        <th>Access Level</th> 
		      </tr>
		    </thead>
		    <tbody>
 		      <tr  ng-repeat="data in usersDataList">		  
 		      	<td>
		      	  <button type="button" class="btn btn-info btn-sm pull-right" ng-click="editUser(data);">
		        	<i class="fa fa-pencil-square-o" aria-hidden="true"></i> Edit
		       	  </button> 
 		      	</td>     
 		      	<td>
		      	  <button type="button" class="btn btn-danger btn-sm" ng-click="deleteUser(data);">
		        	<i class="fa fa-trash-o" aria-hidden="true"></i> Delete
		       	  </button> 
 		      	</td>    
		        <td>{{data.userId}}</td>
		      	<td>{{data.firstName}}</td>
		      	<td>{{data.lastName}}</td>
		      	<td>{{data.company}}</td> 
		        <td>{{data.createdBy}}</td> 
		        <td ng-switch="data.userGroup" >
		      		<span ng-switch-when="A">Admin</span>
      				<span ng-switch-when="S">Super User</span> 
      				<span ng-switch-when="U">User</span> 
		      	</td> 
		      	<td ng-switch="data.accessLevel" >
		      		<span ng-switch-when="I">Internal</span>
      				<span ng-switch-when="E">External</span> 
 		      	</td>  
 		        <td></td> 
		      </tr>
		      <tr ng-if = "!usersDataList && (searchErroMessage != null)">
		      <td colspan="10">
		      		<div class="alert alert-danger">
					    <strong>Error:</strong> <span>{{searchErroMessage}}</span>
					</div>
		      </td>
		      </tr>
		    </tbody>
	  	</table> 
	  	
  	  	
	  	<script type="text/ng-template" id="confirmDelete.jsp">
           <div class="modal-header">
               <h4> Are you sure you want to delete the user: {{selectedUserId}} ?</h4>
           </div> 
           <div class="modal-footer">
               <button class="btn btn-primary" ng-click="ok()">OK</button>
               <button class="btn btn-warning" ng-click="cancel()">Cancel</button>
           </div>
   	 	</script>
	  	
</div>  	
        
 	<script>
	    $(document).ready(function () {
 	        $('.dropdown-toggle').dropdown();
	    });
	</script>        
        