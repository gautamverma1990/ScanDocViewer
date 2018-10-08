<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html ng-app="scandocApp">
<head>
	 <meta http-equiv="X-UA-Compatible" content="IE=EDGE" />
     <title>Scanned Document Viewer</title>
     <link rel="shortcut icon" href="images/favicon.png" />
</head>
<body>
	<div class ="inlineblock logoDiv">
		<img src="images/logo.png" class = "navbar-left logoimg">
	</div> 
	<div class="container">	
     	<div class="jumbotron"> 
     	
     	 <nav class="navbar navbar-default" ng-show= "showAppMenu">
				  <div class="container-fluid">
				    <!-- Brand and toggle get grouped for better mobile display -->
				    <div class="navbar-header">	
				      <a class="navbar-brand" href="#!/home">ScanDoc Viewer</a>
				    </div>
				
				    <!-- Collect the nav links, forms, and other content for toggling -->
				    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				 	 <ul class="nav navbar-nav navbar-left">
				 	  	<li><a class= "bold" href="#!/home">Home</a></li>
				 	 </ul>
				       
				      <ul class="nav navbar-nav navbar-right">
				       
				        <li class="dropdown" >
				          <a href="#" class="dropdown-toggle bold" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"> 
				          	<i class="fa fa-user-circle fa-lg"></i> {{userFullName}}<span class="caret"></span>
				          </a>
				          <ul class="dropdown-menu">
				            <li ng-show= "showUserManagement"><a href="#!/addUser" >Add User</a></li>
				            <li ng-show= "showUserManagement"><a href="#!/userManagement" >View/Delete Users</a></li> 
				            <li ><a href="#!/changePassword" >Change Password</a></li>
				            
				          </ul>
				        </li>
				        
				         <li><a href="#!/login" class= "bold">Log Off</a></li>
				         
				      </ul>
				    </div><!-- /.navbar-collapse -->
				  </div><!-- /.container-fluid -->
			</nav>
     	
            <div >
                 <div ng-view>
                 
                 </div>
            </div>
        </div>
    </div> 

     
    <script src="js/lib/jquery-3.1.1.min.js"></script>
    <script src="js/lib/bootstrap.min.js"></script>
    <script src="js/lib/angular.min.js"></script>
    <script src="js/lib/angular-route.min.js"></script>
    <script src="js/lib/angular-cookies.min.js"></script>
    <script src="js/lib/ui-bootstrap-0.12.0.js"></script>
    <script src="js/lib/ui-bootstrap-tpls-0.12.0.js"></script>
     
    <link href="<c:url value='css/bootstrap-3.3.7.css' />" rel="stylesheet"></link>    
    <link href="<c:url value='css/app.css' />" rel="stylesheet"></link>    
    <link href="<c:url value='css/font-awesome.min.css' />" rel="stylesheet"></link>    
  
    <script src="<c:url value='js/app.js' />"></script>
    <script src="<c:url value='js/services.js' />"></script>
    <script src="<c:url value='js/controller.js' />"></script>
    
     
    <!--  <script src="app-services/authentication.service.js"></script>
    <script src="app-services/flash.service.js"></script> -->

    <!-- Real user service that uses an api -->
    <!-- <script src="app-services/user.service.js"></script> -->

    <!-- Fake user service for demo that uses local storage -->
    <!--  <script src="app-services/user.service.local-storage.js"></script>

    <script src="home/home.controller.js"></script>
    <script src="register/register.controller.js"></script>   -->
</body>
</html>