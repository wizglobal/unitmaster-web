'use strict';

var StaffMngt= angular.module('StaffApp', ['ngRoute','angularUtils.directives.dirPagination'] ); 

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
    .when('/changePassword', {
     templateUrl: 'views/common/changepassword.html',   
      controller: 'changepasswordctrl' 
        })       
        
    .when('/feedback', {
     templateUrl: 'views/common/feedback.html',   
      controller: 'Feedbackctrl' 
        })   
        
       .when('/about', {
     templateUrl: 'views/common/about.html',   
      controller: 'aboutctrl' 
        })       
        
    .when('/help', {
     templateUrl: 'views/common/help.html',   
      controller: 'helpctrl' 
        })  
        
     .when('/confirmMembers', {
     templateUrl: 'views/staff/confirmMembers.html',   
      controller: 'confirmMembersctrl' 
        })    
         .when('/registerCustomer', {
     templateUrl: 'views/staff/registration/registerCustomer.html',   
      controller: 'registerCustomerctrl' 
        })   
        
       .when('/registerAgent', {
     templateUrl: 'views/staff/registration/registerAgent.html',   
      controller: 'registerAgentctrl' 
        })       
        
    .when('/registerStaff', {
     templateUrl: 'views/staff/registration/registerStaff.html',   
      controller: 'registerStaffctrl' 
        }) 
      
      .when('/confirmNavs', {
     templateUrl: 'views/staff/confirmNav.html',   
      controller: 'confirmNavsctrl' 
        })   
       .when('/userRegistration', {
     templateUrl: 'views/staff/registerUser.html',   
      controller: 'userRegistrationctrl' 
        })  
             .when('/confirmTransactions', {
     templateUrl: 'views/staff/confirmTransactions.html',   
      controller: 'confirmTransactionsctrl' 
        })   
                   .when('/confirmInterests', {
     templateUrl: 'views/staff/confirmInterest.html',   
      controller: 'confirmInterestsctrl' 
        })  
       
    .otherwise({
         redirectTo: '/onlineCustomer'
      });

});

StaffMngt.controller('onlineCustomerctrl', function ($scope,$window,staffFactory) {
  
                                                 staffFactory.getOnlineCustomers()
						 .success(function(data) {
							$scope.customers=data;
                          
							 }) 
						 .error(function(data) {
							$scope.customers=[];	
							 });
});
StaffMngt.controller('profilectrl', function ($scope,$window,staffFactory) {
    
});
StaffMngt.controller('Feedbackctrl', function ($scope,$window) {
    
});
StaffMngt.controller('changepasswordctrl', function ($scope,$window) {
    
});
StaffMngt.controller('aboutctrl', function ($scope,$window) {
    
});
StaffMngt.controller('helpctrl', function ($scope,$window) {
    
});
StaffMngt.controller('userRegistrationctrl', function ($scope,$window) {
    
});

StaffMngt.controller('confirmInterestsctrl', function ($scope,$window,staffFactory) {
    staffFactory.getUnconfirmedInterest()
              .success(function(data) {
			    	 $scope.Interests =data;
					}) 
				 .error(function(data) {
				   $scope.Interests =[];
					});
});


StaffMngt.controller('confirmTransactionsctrl', function ($scope,$window,staffFactory) {
        staffFactory.getUnconfirmedTransactions()
              .success(function(data) {
			    	 $scope.transactions =data;
					}) 
				 .error(function(data) {
				   $scope.transactions =[];
					});
});


StaffMngt.controller('confirmNavsctrl', function ($scope,$window,staffFactory) {
    staffFactory.getUnconfirmedNavs()
              .success(function(data) {
			    	 $scope.members=data;
					}) 
				 .error(function(data) {
				   $scope.members=[];
					});
});


StaffMngt.controller('confirmMembersctrl', function ($scope,$window,staffFactory) {
    
    
          staffFactory.getUnconfirmedMembers()
              .success(function(data) {
			    	 $scope.members=data;
					}) 
				 .error(function(data) {
				   $scope.members=[];
					});
});


StaffMngt.controller('registerCustomerctrl', function ($scope,$window,staffFactory) {
   
    
      staffFactory.getUnregisteredCustomers()
              .success(function(data) {
			    	 $scope.members=data;
                      
					}) 
				 .error(function(data) {
				   $scope.members=[];
					});
                                    
});
StaffMngt.controller('registerAgentctrl', function ($scope,$window,staffFactory) {
    
        staffFactory.getUnregisteredAgents()
              .success(function(data) {
			    	 $scope.agents=data;
                       
					}) 
				 .error(function(data) {
				   $scope.agents=[];
					});
    
});
StaffMngt.controller('registerStaffctrl', function ($scope,$window,staffFactory) {
    
         staffFactory.getUnregisteredStaff()
              .success(function(data) {
			    	 $scope.staffs=data;
                        
					}) 
				 .error(function(data) {
				   $scope.staffs=[];
					});
    
});



StaffMngt.controller('Mainctrl', function ($scope,$window,staffFactory) {
       $scope.Logout=function(){
            staffFactory.logout()
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
StaffMngt.factory('staffFactory', ['$http',function($http) {
	var url='/web/Property';
        
	var data = {	 
            getOnlineCustomers:function () {
		     return $http.get('/Web/rest/staff/OnlineMembers',{ cache: true });
            },
          
             getStaffProfile:function () {
		     return $http.get('/Web/rest/staff/Profile',{ cache: true });
            },
             getUnregisteredCustomers:function () {
		     return $http.get('/Web/rest/staff/UnregisteredCustomers',{ cache: true });
            },
              getUnregisteredAgents:function () {
		     return $http.get('/Web/rest/staff/UnregisteredAgents',{ cache: true });
            },
             getUnconfirmedMembers:function () {
		     return $http.get('/Web/rest/staff/UnconfirmedMembers',{ cache: true });
            },     
              getUnconfirmedNavs:function () {
		     return $http.get('/Web/rest/staff/UnconfirmedNavs',{ cache: true });
            },
              getUnregisteredStaff:function () {
		     return $http.get('/Web/rest/staff/UnregisteredStaff',{ cache: true });
            },
              getUnconfirmedTransactions:function () {
		     return $http.get('/Web/rest/staff/UnconfirmedTransactions',{ cache: true });
            },
               getUnconfirmedInterest:function () {
		     return $http.get('/Web/rest/staff/UnconfirmedInterests',{ cache: true });
            },
            
                   
            logout:function () {
		     return $http.get('/web/logout');
            },
	}
	return data;
}]);


