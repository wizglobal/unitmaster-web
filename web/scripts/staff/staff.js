'use strict';

var StaffMngt= angular.module('StaffApp', ['ngRoute'] ); 

	StaffMngt.factory('authInterceptor', function ($rootScope, $q, $window) {
		  return {
			request: function (config) {
				
			  config.headers = config.headers || {};
			  if ($window.sessionStorage.token) {
				config.headers.token=  $window.sessionStorage.token;
			  }
			   else{
				   // no token in Store
                    $window.location.href = "Errror.html";
			  }
			  return config;
			},
			response: function (response) {
			  if (response.status === 401) {
				// handle the case where the user is not authenticated
				$window.location.href = "Errror.html";
			  }
			  
			  return response || $q.when(response);
			}
		  };
		});
   StaffMngt.config(function ($httpProvider) {
       $httpProvider.interceptors.push('authInterceptor');
    });
 StaffMngt.config(function($routeProvider,$locationProvider)	{   
$locationProvider.hashPrefix("!");

  $routeProvider
   .when('/onlineCustomer', {
     templateUrl: 'views/staff/onlineCustomer.html',   
      controller: 'onlineCustomerctrl' 
        })
    .when('/profile', {
     templateUrl: 'views/staff/profile.html',   
      controller: 'profilectrl' 
        })     
                                 
    .otherwise({
         redirectTo: '/onlineCustomer'
      });

});

StaffMngt.controller('onlineCustomerctrl', function ($scope,$window,staffFactory) {
  
                                                 staffFactory.getOnlineCustomers()
						 .success(function(data) {
							$scope.customers=data;
                                                console.log(data);
                          
							 }) 
						 .error(function(data) {
							$scope.customers=[];	
							 });
});
StaffMngt.controller('profilectrl', function ($scope,$window,staffFactory) {
    
});
StaffMngt.controller('Mainctrl', function ($scope,$window,staffFactory) {
});
StaffMngt.factory('staffFactory', ['$http',function($http) {
	var url='/web/Property';
        
	var data = {	 
            getOnlineCustomers:function () {
		     return $http.get('/Web/rest/staff/OnlineMembers',{ cache: true });
            },
          
             getStaffProfile:function () {
		     return $http.get('/Web/rest/staff/Profile',{ cache: true });
            },
                   
            logout:function () {
		     return $http.get('/web/logout');
            },
	}
	return data;
}]);


