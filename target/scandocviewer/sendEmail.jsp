
<form name="sendEmailForm" ng-submit="sendEmail()" role="form">

<div class="modal-header">
  	<h4 class ="inlineblock" > Send Email </h4>
  </div> 
  <div class="modal-body"> 
  	  
 		<div class="alert alert-danger" ng-show="showEmailError">
    	 <strong>Error:</strong> <span>{{emailErrorMsg}}</span>
		</div> 
		  <div class="alert alert-success" ng-show="showEmailSuccess">
		    	<strong>Success:</strong> <span>{{emailSuccessMsg}}</span>
		</div> 
  	   
	   <div class="form-group">
	      <label for="company">Email To</label>
	      <autocomplete choices="items" emailTo="text"
      minlength="1" selected="selected"></autocomplete>
      
      <!-- Commented by Gautam Verma -->
	     <!-- <input type="input" name="emailTo" id="emailTo" class="form-control" ng-model="emailTo" placeholder="Please enter Email addresses separated by comma (,)" 
	      		ng-pattern= "/^[\W]*([\w+\-.%]+@[\w\-.]+\.[A-Za-z]{2,}[\W]*,{1}[\W]*)*([\w+\-.%]+@[\w\-.]+\.[A-Za-z]{2,})[\W]*$/"		/>-->
	      		
	      <!-- <span ng-show="sendEmailForm.emailTo.$dirty && sendEmailForm.emailTo.$invalid"  class="emailError" >Please enter valid email addresses</span>  -->
	      
	   </div>  
	   
	   <div class="form-group">
            <label for="notes">SUBJECT</label>
	      <input type="input" name="subject" id="subject" class="form-control" ng-model="subject" required />
	      <span ng-show="sendEmailForm.subject.$dirty && sendEmailForm.subject.$error.required" class="emailError">Subject is required</span>

       </div> 
       
       <div class="form-group">
            <label for="notes">MESSAGE</label>
            <textarea rows ="8" type="input" name="message" id="message" class="form-control"   ng-model="message" />
       </div> 
  	  

  	   
  </div> 
  <div class="modal-footer">
    <button class="btn btn-primary"  type="submit" ng-disabled="sendEmailForm.$invalid" >SendEmail</button>
    <img  ng-if="emailLoading" src="data:image/gif;base64,R0lGODlhEAAQAPIAAP///wAAAMLCwkJCQgAAAGJiYoKCgpKSkiH/C05FVFNDQVBFMi4wAwEAAAAh/hpDcmVhdGVkIHdpdGggYWpheGxvYWQuaW5mbwAh+QQJCgAAACwAAAAAEAAQAAADMwi63P4wyklrE2MIOggZnAdOmGYJRbExwroUmcG2LmDEwnHQLVsYOd2mBzkYDAdKa+dIAAAh+QQJCgAAACwAAAAAEAAQAAADNAi63P5OjCEgG4QMu7DmikRxQlFUYDEZIGBMRVsaqHwctXXf7WEYB4Ag1xjihkMZsiUkKhIAIfkECQoAAAAsAAAAABAAEAAAAzYIujIjK8pByJDMlFYvBoVjHA70GU7xSUJhmKtwHPAKzLO9HMaoKwJZ7Rf8AYPDDzKpZBqfvwQAIfkECQoAAAAsAAAAABAAEAAAAzMIumIlK8oyhpHsnFZfhYumCYUhDAQxRIdhHBGqRoKw0R8DYlJd8z0fMDgsGo/IpHI5TAAAIfkECQoAAAAsAAAAABAAEAAAAzIIunInK0rnZBTwGPNMgQwmdsNgXGJUlIWEuR5oWUIpz8pAEAMe6TwfwyYsGo/IpFKSAAAh+QQJCgAAACwAAAAAEAAQAAADMwi6IMKQORfjdOe82p4wGccc4CEuQradylesojEMBgsUc2G7sDX3lQGBMLAJibufbSlKAAAh+QQJCgAAACwAAAAAEAAQAAADMgi63P7wCRHZnFVdmgHu2nFwlWCI3WGc3TSWhUFGxTAUkGCbtgENBMJAEJsxgMLWzpEAACH5BAkKAAAALAAAAAAQABAAAAMyCLrc/jDKSatlQtScKdceCAjDII7HcQ4EMTCpyrCuUBjCYRgHVtqlAiB1YhiCnlsRkAAAOwAAAAAAAAAAAA==" />
    <button class="btn btn-warning" type="button" ng-click="close()">Close</button>
  </div>
  
</form>  
<style>
.wrapper {
  margin: auto;
  max-width: 400px;
  z-index:9999999;
}
.autocomplete, pre {
  width: calc(100% - 20px);
  z-index:9999999;
}

.inputtext {
  border: 1px solid grey;
  margin: 0;
  padding: 5px;
  width: 100%;
  z-index:9999999;
}

.choices .choice {
  border: 1px solid #ccc;
  cursor: pointer;
  padding: 5px;
  width: 100%;
  z-index:9999999;
}
</style>