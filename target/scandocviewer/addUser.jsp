 

<div class="col-md-4 col-md-offset-4" ng-controller="addUserController"> 
	
    <form name="addUserForm" ng-submit="addUser()" role="form">
    
    	 <div class="alert alert-danger" ng-show="showAddUSerError">
		    	<strong>Error:</strong> <span>{{addUSerErrorMsg}}</span>
		 </div> 
		  <div class="alert alert-success" ng-show="showAddUSersuccess">
		    	<strong>Success:</strong> <span>{{addUSerSuccessMsg}}</span>
		 </div> 
        <div class="form-group" ng-class="{ 'has-error': addUserForm.userId.$dirty && addUserForm.userId.$error.required }">
            <label for="userId">User Id</label>
            <input type="text" name="userId" id="userId" class="form-control" ng-model="userId" maxlength="10" required />
            <span ng-show="addUserForm.userId.$dirty && addUserForm.userId.$error.required" class="help-block">UserId is required</span>
        </div>
        <div class="form-group" ng-class="{ 'has-error': addUserForm.password.$dirty && addUserForm.password.$error.required }">
            <label for="password">Password</label>
            <input type="password" name="password" id="password" class="form-control" ng-model="password" maxlength="35" required />
            <span ng-show="addUserForm.password.$dirty && addUserForm.password.$error.required" class="help-block">Password is required</span>
        </div>
        <div class="form-group" ng-class="{ 'has-error': addUserForm.confPassword.$dirty && ( addUserForm.confPassword.$error.required || confPassword != password ) }">
            <label for="confPassword">Confirm Password</label>
            <input type="password" name="confPassword" id="confPassword" class="form-control" ng-model="confPassword" maxlength="35" required />
            <span ng-show="addUserForm.confPassword.$dirty && addUserForm.confPassword.$error.required" class="help-block">Please confirm password</span>
            <span ng-show="addUserForm.confPassword.$dirty &&  confPassword != password" class="help-block">Passwords don't match</span>
        </div>
        <div class="form-group" ng-class="{ 'has-error': addUserForm.firstName.$dirty && addUserForm.firstName.$error.required }">
            <label for="firstName">First Name</label>
            <input type="input" name="firstName" id="firstName" class="form-control" ng-model="firstName" maxlength="50"  required />
            <span ng-show="addUserForm.firstName.$dirty && addUserForm.firstName.$error.required" class="help-block">First Name is required</span>
        </div>
        <div class="form-group" ng-class="{ 'has-error': addUserForm.lastName.$dirty && addUserForm.lastName.$error.required }">
            <label for="lastName">Last Name</label>
            <input type="input" name="lastName" id="lastName" class="form-control" ng-model="lastName"  maxlength="50" required />
            <span ng-show="addUserForm.lastName.$dirty && addUserForm.lastName.$error.required" class="help-block">Last Name is required</span>
        </div> 
        
        <div class="form-group">
            <label for="company">Company</label>
            <input type="input" name="company" id="company" class="form-control" maxlength="100"   ng-model="company" />
         </div> 
         <div class="form-group" ng-class="{ 'has-error': addUserForm.email.$dirty && (addUserForm.email.$error.email || addUserForm.email.$error.required) }">
            <label for="email">Email</label>
            <input type="email" name="email" id="email" class="form-control"  maxlength="60"  ng-model="email" required/>
             <span ng-show="addUserForm.email.$dirty && addUserForm.email.$error.email"  class="help-block" >Please enter a valid email</span> 
             <span ng-show="addUserForm.email.$dirty && addUserForm.email.$error.required" class="help-block">Email is Required</span>
         </div> 
         
        <div class="form-group">
         	<label for="userGroup">User Group</label>
        	<select name="userGroup" id ="userGroup" class ="form-control" ng_model = "userGroup">
		 		<option value="U">User</option>
		 		<option value="S">Super User</option>
		 		<option value="A">Admin</option> 
			</select>
		</div>	
		
		
				
		<div class="form-group">
         	<label for="accessLevel">AccessLevel</label>
        	<select name="accessLevel" id ="accessLevel" class ="form-control" ng_model = "accessLevel">
		 		<option value="I">Internal</option>
		 		<option value="E">External</option>
 			</select>
		</div>	
		
		 <div class="form-group">
            <label for="notes">Notes</label>
            <textarea rows ="3" type="input" name="notes" id="notes" class="form-control" maxlength="200"   ng-model="notes" />
         </div> 

        
        <div class="form-actions">
            <button type="submit" ng-disabled="addUserForm.$invalid" class="btn btn-primary">Add</button>
            <button type="reset" class="btn btn-primary">Reset</button>                        
         <div>   
    </form>
    
    
</div>
 
  	<script>
	    $(document).ready(function () {
 	        $('.dropdown-toggle').dropdown();
	    });
	</script>