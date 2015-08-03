var AgentMngt= angular.module('AgentApp', ['ngRoute'] ); 


	AgentMngt.factory('authInterceptor', function ($rootScope, $q, $window) {
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
   AgentMngt.config(function ($httpProvider) {
       $httpProvider.interceptors.push('authInterceptor');
    });

AgentMngt.controller('Mainctrl', function ($scope,$window,agentFactory) {

});
AgentMngt.controller('profilectrl', function ($scope,$window,profile) {
    $scope.profile=profile.data;
});
AgentMngt.controller('customerListctrl', function ($scope,$window,agentFactory) {
    
});


AgentMngt.factory('agentFactory', ['$http',function($http) {
 var data = {
        getAgentDetails:function (agentnumber) {
		     return $http.get('/Web/rest/agent/agentdetails');
            },
            }
	return data;
}]);
AgentMngt.service('agentDetails', function () {

    var data={} ;
    this.set = function (agentDetails) {
		   data=agentDetails;

    }


    this.get = function () {  
         return data;
       } 
  
});

AgentMngt.config(function($routeProvider,$locationProvider)	{

$locationProvider.hashPrefix("!");

  $routeProvider
   .when('/profile', {
     templateUrl: 'views/agent/profile.html',   
      controller: 'profilectrl' ,
      resolve: {		
            profile: function(agentFactory) {
				return agentFactory.getAgentDetails();
			},
	       }
        })
    .when('/customerList', {
     templateUrl: 'views/agent/customers.html',   
      controller: 'customerListctrl' 
        })           
    .otherwise({
         redirectTo: '/profile'
      });

});
            