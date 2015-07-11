'use strict';

var LoginMngt= angular.module('WizglobalAppLogin', [] ); 

 LoginMngt.controller('loginCtrl', function ($scope,$http,$window) {
	 $scope.noFullyConfigured=false;
    $scope.showSpinner=false;
       $scope.Userlogin=function(){
            $scope.showSpinner=true;
                $http.post('/Web/rest/authentication/login',$scope.user)
				 		 .success(function(data) {
								     $scope.invalidcredential=false;
									 $window.sessionStorage.token = data.token;
                                                                        if (data.category==="customer"){
								            $window.location.href="/Web/customer.html";
                                                                         }else if (data.category==="staff"){$window.location.href="/Web/staff.html";}
                                                                         else{$window.location.href="/Web/agent.html";}
									   
							 }) 
						 .error(function(data) {
							   $scope.invalidcredential=true;
							    $scope.showSpinner=false;
							    delete $window.sessionStorage.token;
                                                            
							 });	
      };


      $scope.forgotPassword=function(){
             $http.post('web/sendmail',$scope.user)
				 .success(function(data) {
					 })
				.error(function(data) {
						  })
	  
	  };

	   });

