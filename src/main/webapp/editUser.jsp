  <div class="modal-header">
  	<h4 class ="inlineblock" > Edit user: {{editUser.userId}} </h4> <h4 class ="pull-right"> {{editUser.firstName}}, {{editUser.lastName}} </h4>
  </div> 
  <div class="modal-body">
  
 		<div class="alert alert-danger" ng-show="showEditUSerError">
	    	<strong>Error:</strong> <span>{{editUserErrorMsg}}</span>
		 </div> 
		  <div class="alert alert-success" ng-show="showEditUSersuccess">
		    	<strong>Success:</strong> <span>{{editUSerSuccessMsg}}</span>
		 </div> 
  	   
	   <div class="form-group">
	      <label for="company">Company</label>
	      <input type="input" name="company" id="company" class="form-control" maxlength="100"   ng-model="editUser.company" />
	   </div> 
	   
	   <div class="form-group" ng-form name="editUserForm" ng-class="{ 'has-error': editUserForm.email.$dirty && editUserForm.email.$error.email }">
            <label for="email">Email</label>
            <input type="email" name="email" id="email" class="form-control"  maxlength="60"  ng-model="editUser.email" required/>
             <span ng-show="editUserForm.email.$dirty && editUserForm.email.$error.email"  class="help-block" >Please enter a valid email</span> 
       </div> 
	   
	   <div class="form-group">
         	<label for="userGroup">User Group</label>
        	<select name="editUser.userGroup" id ="editUser.userGroup" class ="form-control" ng_model = "editUser.userGroup">
		 		<option value="U">User</option>
		 		<option value="S">Super User</option>
		 		<option value="A">Admin</option> 
			</select>
		</div> 
				
		<div class="form-group">
         	<label for="accessLevel">AccessLevel</label>
        	<select name="editUser.accessLevel" id ="editUser.accessLevel" class ="form-control" ng_model = "editUser.accessLevel">
		 		<option value="I">Internal</option>
		 		<option value="E">External</option>
 			</select>
		</div>	
		
		 <div class="form-group">
            <label for="notes">Notes</label>
            <textarea rows ="3" type="input" name="editUser.notes" id="editUser.notes" class="form-control" maxlength="200"   ng-model="editUser.notes" />
         </div> 
  	   
  </div> 
  <div class="modal-footer">
    <button class="btn btn-primary" ng-click="save()">Save</button>
    <button class="btn btn-warning" ng-click="close()">Close</button>
  </div>