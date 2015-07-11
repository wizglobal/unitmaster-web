var CustomerMngt= angular.module('CustomerApp', ['ngRoute'] ); 

	CustomerMngt.factory('authInterceptor', function ($rootScope, $q, $window) {
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
   CustomerMngt.config(function ($httpProvider) {
       $httpProvider.interceptors.push('authInterceptor');
    });

CustomerMngt.controller('Mainctrl', function ($scope,$window,customerFactory) {
     $scope.Logout=function(){
            customerFactory.logout()
              .success(function(data) {
			    	delete $window.sessionStorage.token;
					$window.location.href = "index.html";
					}) 
				 .error(function(data) {
				   delete $window.sessionStorage.token;
					$window.location.href = "index.html";
					});
                                    }
});
CustomerMngt.controller('accountctrl', function ($scope,$window,customerFactory) {
    
});
CustomerMngt.controller('agentdetailsctrl', function ($scope,customerFactory,$route) {
    
    $scope.agentcode=angular.fromJson(atob($route.current.params.agentcode));
    
                                            customerFactory.getAgentDetails($scope.agentcode)
						 .success(function(data) {
							$scope.agentdetails=data;
                                                CustomerDetails.save(data);
							 }) 
						 .error(function(data) {
							details=[];	
							 });
});

CustomerMngt.controller('profilectrl', function ($scope,$window,customerFactory,CustomerDetails,$location) {

                 
                                                            customerFactory.getCustomerDetails()
						 .success(function(data) {
							$scope.memberdetails=data;
                                                CustomerDetails.save(data);
							 }) 
						 .error(function(data) {
							details=[];	
							 });
        
                                   customerFactory.getCustomerAccounts()
						 .success(function(data) {
							$scope.memberaccounts=data;
                                                console.log(data);
							 }) 
						 .error(function(data) {
							accounts=[];	
							 });
                                                         
                               customerFactory.getCustomerBeneficiaries()
						 .success(function(data) {
							$scope.memberbeneficiaries=data;
                                                console.log(data);
							 }) 
						 .error(function(data) {
							accounts=[];	
							 });      
                                                         
         $scope.ViewAdvisor=function(advisorCode){
             	var det = angular.toJson(advisorCode);
                   det=btoa(det);
             $location.path('/agentdetails/'+det);
         }                                                
                 
                 
});
CustomerMngt.config(function($routeProvider,$locationProvider)	{

$locationProvider.hashPrefix("!");

  $routeProvider
	 
 .when('/profile', {
     templateUrl: 'views/customer/profile.html',   
      controller: 'profilectrl' 
        })	   
  .when('/account', {
     templateUrl: 'views/customer/account.html',   
      controller: 'accountctrl' 
        }) 
    .when('/agentdetails/:agentcode', {
     templateUrl: 'views/customer/agentdetails.html',   
      controller: 'agentdetailsctrl' 
        })      
    .otherwise({
         redirectTo: '/profile'
      });

});
CustomerMngt.factory('customerFactory', ['$http',function($http) {
	var url='/web/Property';
        
	var data = {	 
            getCustomerDetails:function () {
		     return $http.get('/Web/rest/member/memberdetails',{ cache: true });
            },
          
             getCustomerAccounts:function () {
		     return $http.get('/Web/rest/member/Accounts',{ cache: true });
            },
            getCustomerBeneficiaries:function () {
		     return $http.get('/Web/rest/beneficiary/memberBeneficiary',{ cache: true });
            },
            getAgentDetails:function (agentnumber) {
		     return $http.get('/Web/rest/agent/agentdetails/'+agentnumber);
            },
            
            
          
            logout:function () {
		     return $http.get('/web/logout');
            },
	}
	return data;
}]);


CustomerMngt.service('CustomerDetails', function () {

    var data={} ;
    var properties=[] ;
    this.save = function (userDetails) {
		   data=userDetails;

    }


    this.get = function () {  
         return data;
       }

       this.saveProperties = function (prop) {
		   properties=prop;

    }


    this.getProperties = function () {  
         return properties;
       }
    
  
});


