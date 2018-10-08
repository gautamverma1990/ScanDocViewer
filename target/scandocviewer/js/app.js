  'use strict';
    
     
   angular.module('scandocApp', ['ngRoute', 'ngCookies', 'scandocApp.controllers', 'scandocApp.services', 'ui.bootstrap'])
   .config(['$routeProvider', '$locationProvider', '$httpProvider' ,function($routeProvider, $locationProvider,$httpProvider) {
	   $httpProvider.defaults.headers.common['Cache-Control'] = 'no-cache';
	   $httpProvider.defaults.cache = false;

	   if (!$httpProvider.defaults.headers.get) {
	       $httpProvider.defaults.headers.get = {};
	   }
	   $httpProvider.defaults.headers.get['If-Modified-Since'] = '0';
	   
        $routeProvider.when('/home', {
        	title: 'Search Scanned Documents',
            controller: 'homeController',
            templateUrl: 'home.jsp' 
        })  
        
        $routeProvider.when('/home/:ref/:refValue', {
        	title: 'Search Scanned Documents',
            controller: 'homeController',
            templateUrl: 'home.jsp' 
        })
        
        $routeProvider.when('/login', {
        	title: 'Log into Scanned Documents',
            controller: 'LoginController',
            templateUrl: 'login.jsp' 
        })
        
        $routeProvider.when('/addUser', {
        	title: 'Add User',
            controller: 'addUserController',
            templateUrl: 'addUser.jsp' 
        })
        
         $routeProvider.when('/userManagement', {
        	title: 'Manage Users',
            controller: 'userManagementController',
            templateUrl: 'searchUsers.jsp' 
        }) 
        
        $routeProvider.when('/changePassword', {
        	title: 'Change Password',
            controller: 'changePasswordController',
            templateUrl: 'changePassword.jsp' 
        }) 

        $routeProvider.otherwise({ redirectTo: '/login' });
    }])
   .run(['$rootScope', '$location', '$cookies', '$http', 'utilService' ,
	   function($rootScope, $location, $cookies, $http, utilService) {
	   
       $rootScope.$on('$locationChangeStart', function (event, next, current) {
    	   
            
    	   $rootScope.globals = $cookies.getObject('globals') || {}; 
     	   
           var restrictedPage = $.inArray($location.path(), ['/login']) === -1;
           
           var loggedIn = $rootScope.globals.currentUser; 
           
           if (restrictedPage && !loggedIn) 
        	   $location.path('/login');  
            
           var restrictUsrManagement = (utilService.getUserGroup() != 'A'
        	   								&& $.inArray($location.path(), ['/addUser', '/userManagement']) != -1  )? true: false;   
           
           if(restrictUsrManagement) 
        	   $location.path('/home');
            
        	   
           
       });
	   
   	}]);

   