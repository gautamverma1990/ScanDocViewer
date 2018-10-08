 
		  

   	 	<form name="searchForm" ng-submit="search()" role="form"> 

			 <div class="col-xs-5 col-sm-2">
			 	 <p class="pull-right">Reference Type </p>
	        </div>
				<div class="col-xs-5 col-sm-2">
	            	<select name="refType" id ="refType" class ="form-control mb-15" ng_model = "refType">
				 		<option value="hbl">HAWB/HBL</option>
				 		<option value="mbl">MAWB/MBL</option>
				 		<option value="stt">STT</option>
						<option value="shipmentNo">Shipment Number</option>
						<option value="archiveNumber">Archive Number</option>						
						<option value="schenkerInvoice">Schenker Invoice</option>
					</select>
	        	</div> 
	         <div class="col-xs-5 col-sm-2">
			 	<p class="pull-right">Reference Value</p>
	        </div>
			 <div class="col-xs-5 col-sm-2">
	           <input type="text" id ="refValue" name="refValue"  class ="form-control mb-15" ng_model = "refValue" ng-minlength="0"> 
	           <span ng-if="isSearchInvalid"  class="help-block searchError">{{searchInvalidMsg}}</span> 
	        </div>
	        <div class="col-xs-3 col-sm-1">
	           <button type="submit" id ="search" class="btn btn-primary" ng-click="validateSearch();" >Search</button>
	           <img  ng-if="dataLoading" src="data:image/gif;base64,R0lGODlhEAAQAPIAAP///wAAAMLCwkJCQgAAAGJiYoKCgpKSkiH/C05FVFNDQVBFMi4wAwEAAAAh/hpDcmVhdGVkIHdpdGggYWpheGxvYWQuaW5mbwAh+QQJCgAAACwAAAAAEAAQAAADMwi63P4wyklrE2MIOggZnAdOmGYJRbExwroUmcG2LmDEwnHQLVsYOd2mBzkYDAdKa+dIAAAh+QQJCgAAACwAAAAAEAAQAAADNAi63P5OjCEgG4QMu7DmikRxQlFUYDEZIGBMRVsaqHwctXXf7WEYB4Ag1xjihkMZsiUkKhIAIfkECQoAAAAsAAAAABAAEAAAAzYIujIjK8pByJDMlFYvBoVjHA70GU7xSUJhmKtwHPAKzLO9HMaoKwJZ7Rf8AYPDDzKpZBqfvwQAIfkECQoAAAAsAAAAABAAEAAAAzMIumIlK8oyhpHsnFZfhYumCYUhDAQxRIdhHBGqRoKw0R8DYlJd8z0fMDgsGo/IpHI5TAAAIfkECQoAAAAsAAAAABAAEAAAAzIIunInK0rnZBTwGPNMgQwmdsNgXGJUlIWEuR5oWUIpz8pAEAMe6TwfwyYsGo/IpFKSAAAh+QQJCgAAACwAAAAAEAAQAAADMwi6IMKQORfjdOe82p4wGccc4CEuQradylesojEMBgsUc2G7sDX3lQGBMLAJibufbSlKAAAh+QQJCgAAACwAAAAAEAAQAAADMgi63P7wCRHZnFVdmgHu2nFwlWCI3WGc3TSWhUFGxTAUkGCbtgENBMJAEJsxgMLWzpEAACH5BAkKAAAALAAAAAAQABAAAAMyCLrc/jDKSatlQtScKdceCAjDII7HcQ4EMTCpyrCuUBjCYRgHVtqlAiB1YhiCnlsRkAAAOwAAAAAAAAAAAA==" />	           
	        </div> 
        
        </form>
        
        <hr class="liner">
        
        <table class="table table-hover">
		    <thead>
		      <tr>
		        <th><input id="selectAll" type="checkbox" class="checkboxAll"  ng-show="scandocDataList" ng-click="selectAll(this);"></th>
		        <th>Shipment Number</th>
		        <th>Archive Number</th>
		        <th>MBL</th>
		        <th>Department</th>
		        <th>Document Type</th>
		        <th>Document Number</th>
		        <th>Date Scanned</th>
		        <th>File Size</th>
		        <th>Access Level</th>
		        <th ng-show = "showDelDocuments">Change Doc Type</th>
		        <th>Hyper link to document</th>
		      </tr>
		    </thead>
		    <tbody>
 		      <tr  ng-repeat="data in scandocDataList">
 		      	<td><input id="select" type="checkbox" class="checkbox" ng-model="data.selected"></input></td> 		      			        
		        <td>{{data.shipmentNo}}</td>
		      	<td>{{data.archiveNo}}</td>
		      	<td>{{data.masterbill}}</td>
		      	<td>{{data.department}}</td> 
		        <td>{{data.docType}}</td> 
		        <td>{{data.invoiceNo}}</td> 
 			    <td>{{data.dateScanned | date :  "dd-MMM-yyyy"}}</td> 
 			    <td>{{data.fileSize}} KB</td> 
		      	<td ng-switch="data.accessLevel" >
		      		<span ng-switch-when="E">External</span>
      				<span ng-switch-when="I">Internal</span> 
		      	</td> 
		      	<td ng-switch="data.docType" ng-show = "showDelDocuments">
		      		<span 
		      		ng-switch-when="Commercial Invoice||Supplier Invoice||Outgoing Invoice/Credit Note External||Outgoing Invoice/Credit Note Internal" 
		      		ng-switch-when-separator="||">
		      			<button class="btn btn-primary" ng-click="switchDocType(data);">Switch</button>
					</span>
      				<span ng-switch-default>Not Applicable</span> 
		      	</td>
		        <td class = "text-center">
		        	<a  ng-href="{{data.amsDocUrl}}" target="_blank">
		        		<i class="fa fa-file-pdf-o fa-lg" aria-hidden="true"></i>
		        	</a>
 		        </td> 
		      </tr>
		      <tr ng-if = "!scandocDataList && (searchErroMessage != null)">
		      <td colspan="14">
		      		<div class="alert alert-danger">
					    <strong>Error:</strong> <span>{{searchErroMessage}}</span>
					</div>
		      </td>
		      </tr>
		    </tbody>
	  	</table>
 
 	<!--  <div ng-show = "scandocDataList">
 		<input id="mergePdf" type="checkbox" class="checkbox" checked="true"><p class ="inlineblock combinepdf" >Combine PDFs</p></input>
 	</div>  -->
 	<div ng-show = "scandocDataList">
 		<button type="button" id ="email" class="btn btn-primary" ng-click="opeEmailDialog();">Email</button>
 		<button type="button" id ="print" class="btn btn-primary" ng-click="printSelectedDocs();">Print</button>
		<img  ng-if="printLoading" src="data:image/gif;base64,R0lGODlhEAAQAPIAAP///wAAAMLCwkJCQgAAAGJiYoKCgpKSkiH/C05FVFNDQVBFMi4wAwEAAAAh/hpDcmVhdGVkIHdpdGggYWpheGxvYWQuaW5mbwAh+QQJCgAAACwAAAAAEAAQAAADMwi63P4wyklrE2MIOggZnAdOmGYJRbExwroUmcG2LmDEwnHQLVsYOd2mBzkYDAdKa+dIAAAh+QQJCgAAACwAAAAAEAAQAAADNAi63P5OjCEgG4QMu7DmikRxQlFUYDEZIGBMRVsaqHwctXXf7WEYB4Ag1xjihkMZsiUkKhIAIfkECQoAAAAsAAAAABAAEAAAAzYIujIjK8pByJDMlFYvBoVjHA70GU7xSUJhmKtwHPAKzLO9HMaoKwJZ7Rf8AYPDDzKpZBqfvwQAIfkECQoAAAAsAAAAABAAEAAAAzMIumIlK8oyhpHsnFZfhYumCYUhDAQxRIdhHBGqRoKw0R8DYlJd8z0fMDgsGo/IpHI5TAAAIfkECQoAAAAsAAAAABAAEAAAAzIIunInK0rnZBTwGPNMgQwmdsNgXGJUlIWEuR5oWUIpz8pAEAMe6TwfwyYsGo/IpFKSAAAh+QQJCgAAACwAAAAAEAAQAAADMwi6IMKQORfjdOe82p4wGccc4CEuQradylesojEMBgsUc2G7sDX3lQGBMLAJibufbSlKAAAh+QQJCgAAACwAAAAAEAAQAAADMgi63P7wCRHZnFVdmgHu2nFwlWCI3WGc3TSWhUFGxTAUkGCbtgENBMJAEJsxgMLWzpEAACH5BAkKAAAALAAAAAAQABAAAAMyCLrc/jDKSatlQtScKdceCAjDII7HcQ4EMTCpyrCuUBjCYRgHVtqlAiB1YhiCnlsRkAAAOwAAAAAAAAAAAA==" />
 		<button type="button" id ="delete" class="btn btn-danger" ng-show = "showDelDocuments" ng-click="deleteDocuments();">Delete Documents</button>
 	</div>
 		
 	<script>
	    $(document).ready(function () {
 	        $('.dropdown-toggle').dropdown();
	    });
	</script>
	
	<script type="text/ng-template" id="selectionAlert.jsp">
           <div class="modal-header">
               <h4>Please select a record first.</h4>
           </div> 
           <div class="modal-footer">
               <button class="btn btn-primary" ng-click="ok()">OK</button>
            </div>
   	</script>
   	
   	<script type="text/ng-template" id="confirmDelDoc.jsp">
           <div class="modal-header">
               <h4> Are you sure you want to delete the documents ?</h4>
           </div> 
           <div class="modal-footer">
               <button class="btn btn-primary" ng-click="ok()">OK</button>
               <button class="btn btn-warning" ng-click="cancel()">Cancel</button>
           </div>
   	</script>