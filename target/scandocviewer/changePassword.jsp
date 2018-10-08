<div class="col-md-4 col-md-offset-4" > 

<form name="changePwdForm" ng-submit="changePassword()" role="form">

 	<div class="alert alert-danger" ng-show="showchangePasswordError">
    	<strong>Error:</strong> <span>{{changePasswordErrorMsg}}</span>
	</div> 
	  <div class="alert alert-success" ng-show="showchangePasswordSuccess">
	    <strong>Success:</strong> <span>{{changePasswordSuccessMsg}}</span>
	 </div> 

	<div class="form-group" ng-class="{ 'has-error': changePwdForm.currPassword.$dirty && changePwdForm.currPassword.$error.required }">
	    <label for="password">Old Password</label>
	    <input type="password" name="currPassword" id="currPassword" class="form-control" ng-model="currPassword" required />
	    <span ng-show="changePwdForm.currPassword.$dirty && changePwdForm.currPassword.$error.required" class="help-block">Old Password is required</span>
	</div> 
	
	  <div class="form-group" ng-class="{ 'has-error': changePwdForm.password.$dirty && changePwdForm.password.$error.required }">
	           <label for="password">New Password</label>
	           <input type="password" name="password" id="password" class="form-control" ng-model="password" maxlength="35" required />
	           <span ng-show="changePwdForm.password.$dirty && changePwdForm.password.$error.required" class="help-block">New Password is required</span>
	  </div>
      <div class="form-group" ng-class="{ 'has-error': changePwdForm.confPassword.$dirty && ( changePwdForm.confPassword.$error.required || confPassword != password ) }">
          <label for="confPassword">Confirm Password</label>
          <input type="password" name="confPassword" id="confPassword" class="form-control" ng-model="confPassword" maxlength="35" required />
          <span ng-show="changePwdForm.confPassword.$dirty && changePwdForm.confPassword.$error.required" class="help-block">Please confirm password</span>
          <span ng-show="changePwdForm.confPassword.$dirty &&  confPassword != password" class="help-block">Passwords don't match</span>
      </div>
      
      <div class="form-actions">
           <button type="submit" ng-disabled="changePwdForm.$invalid" class="btn btn-primary">Change</button>
           <button type="reset" class="btn btn-primary">Reset</button>                        
      <div>   
	
</form>
</div>

 	<script>
	    $(document).ready(function () {
 	        $('.dropdown-toggle').dropdown();
	    });
	</script>