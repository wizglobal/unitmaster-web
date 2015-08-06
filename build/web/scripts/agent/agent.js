var AgentMngt= angular.module('AgentApp', ['ngRoute','angularUtils.directives.dirPagination'] ); 


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
AgentMngt.controller('homectrl', function ($scope,$window,profile,customerList,agentSrv) {
    $scope.profile=profile.data;
    $scope.customerList=customerList.data
    agentSrv.setCustomerDetails(customerList.data);
    
    
    
});
AgentMngt.controller('viewCustomerctrl', function ($scope,$window,agentFactory) {
    
});
AgentMngt.controller('detailedstatementctrl', function ($scope,$window,agentFactory) {
    
});
AgentMngt.controller('statementctrl', function ($scope,$window,agentFactory) {
    
});
AgentMngt.controller('feedbackctrl', function ($scope,$window,agentFactory) {
    
});
AgentMngt.controller('profilectrl', function ($scope,$window,agentFactory) {
    
});
AgentMngt.controller('customerFeedbackctrl', function ($scope,$window,agentFactory) {
    
});


AgentMngt.factory('agentFactory', ['$http',function($http) {
 var data = {
        getAgentDetails:function () {
		     return $http.get('/Web/rest/agent/agentdetails',{ cache: true });
            },
         customerList :function () {
		     return $http.get('/Web/rest/agent/customerList',{ cache: true });
            },  
            
            }
	return data;
}]);
AgentMngt.service('agentSrv', function () {

    var data={} ;
    var CustomerData=[];
    this.set = function (agentDetails) {
		   data=agentDetails;

    }


    this.get = function () {  
         return data;
       } 
     this.setCustomerDetails=function(customerDetails){
        CustomerData= customerDetails;
     } 
     this.getCustomerDetails=function(){
         return CustomerData;
     }
  
});

AgentMngt.config(function($routeProvider,$locationProvider)	{

$locationProvider.hashPrefix("!");

  $routeProvider
   .when('/home', {
     templateUrl: 'views/agent/home.html',   
      controller: 'homectrl' ,
      resolve: {		
            profile: function(agentFactory) {
				return agentFactory.getAgentDetails();
			},
            customerList: function(agentFactory) {
				return agentFactory.customerList();
			}            
	       }
        })
    .when('/Viewcustomer/:memberno', {
     templateUrl: 'views/agent/customers.html',   
      controller: 'viewCustomerctrl' 
        })
    .when('/profile', {
     templateUrl: 'views/agent/profile.html',   
      controller: 'profilectrl' 
        })
    .when('/DetailsStatement', {
     templateUrl: 'views/agent/DetailedStatement.html',   
      controller: 'detailedstatementctrl' 
        })     
    .when('/statement', {
     templateUrl: 'views/agent/Statement.html',   
      controller: 'statementctrl' 
        })
   .when('/customerFeedback', {
     templateUrl: 'views/agent/customerFeedback.html',   
      controller: 'customerFeedbackctrl' 
        })
           
        
    .when('/feedback', {
     templateUrl: 'views/agent/feedBack.html',   
      controller: 'feedbackctrl' 
        })     
    .otherwise({
         redirectTo: '/home'
      });

});
            