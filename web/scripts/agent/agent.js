var AgentMngt= angular.module('AgentApp', ['ngRoute','angularUtils.directives.dirPagination','ngDialog'] ); 


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
         $scope.Logout=function(){
            agentFactory.logout()
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
AgentMngt.controller('homectrl', function ($scope,$window,profile,customerList,agentSrv) {
    $scope.profile=profile.data;
    $scope.customerList=customerList.data
    agentSrv.setCustomerDetails(customerList.data);
    
    
    
});
AgentMngt.controller('viewCustomerctrl', function ($scope,$window,agentFactory) {
    
});
AgentMngt.controller('detailedstatementctrl', function ($scope,$window,agentFactory) {
    
           agentFactory.getDetailedTransactions()
                         .success(function(data) {
                        
			    	$scope.transactions=data;
                           console.log(data);
					}) 
				 .error(function(data) {
                                  
				  $scope.transactions=[];
					});
    
});
AgentMngt.controller('statementctrl', function ($scope,$window,agentFactory) {
   
                     agentFactory.getTransactions()
                         .success(function(data) {
                        
			    	$scope.transactions=data;
                           
					}) 
				 .error(function(data) {
                                  
				  $scope.transactions=[];
					});
                                    
    
});
AgentMngt.controller('feedbackctrl', function ($scope,$window,agentFactory) {
    
});
AgentMngt.controller('profilectrl', function ($scope,$window,agentFactory) {
                         agentFactory.getAgentDetails()
                         .success(function(data) {
                        
			    	$scope.profile=data;
                           console.log(data);
					}) 
				 .error(function(data) {
                                  
				  $scope.profile=[];
					});
    
});
AgentMngt.controller('Feedbackctrl', function ($scope,$window,agentFactory) {
    
});
AgentMngt.controller('changepasswordctrl', function ($scope,$window,agentFactory,ngDialog) {
     $scope.SubmitPwd=function(){
        var changepwd={};
          changepwd.oldpwd=$scope.pwd.currentpassword;
          changepwd.newpwd=$scope.pwd.newpassword;
        customerFactory.changePwd(changepwd)
           .success(function(data) {
                if (data.status !=2){ 
                 ngDialog.open({
                    template: '<p>Password Changed  </p>',
                    plain: true
                                });
                            }
                  else {
                        ngDialog.open({
                            template: '<p>Error Updating Password </p>',
                            plain: true
                                });
                  }          
           })
          .error(function(data) {
              ngDialog.open({
                    template: '<p>Error on Changing Password,kindly Retry later </p>',
                    plain: true
                                });
           });  
    }
    
});

AgentMngt.controller('aboutctrl', function ($scope,$window) {
    
});
AgentMngt.controller('helpctrl', function ($scope,$window) {
    
});
AgentMngt.controller('sendfeedbackctrl', function ($scope,$window) {
    
});


AgentMngt.factory('agentFactory', ['$http',function($http) {
 var data = {
        getAgentDetails:function () {
		     return $http.get('/Web/rest/agent/agentdetails',{ cache: true });
            },
         customerList :function () {
		     return $http.get('/Web/rest/agent/customerList',{ cache: true });
            },  
         logout:function () {
		     return $http.get('/web/logout');
            }, 
           getTransactions:function () {
		     return $http.get('/Web/rest/agent/agenttransactions',{ cache: true });
            }, 
          getDetailedTransactions:function () {
		     return $http.get('/Web/rest/agent/agentdetailedtransactions',{ cache: true });
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
    .when('/changePassword', {
     templateUrl: 'views/common/changepassword.html',   
      controller: 'changepasswordctrl' 
        })       
      .when('/about', {
     templateUrl: 'views/common/about.html',   
      controller: 'aboutctrl' 
        })       
        
    .when('/help', {
     templateUrl: 'views/common/help.html',   
      controller: 'helpctrl' 
        })       
    .when('/feedback', {
     templateUrl: 'views/common/feedback.html',   
      controller: 'Feedbackctrl' 
        })
     .when('/Sendfeedback', {
     templateUrl: 'views/common/sendFeedback.html',   
      controller: 'sendfeedbackctrl' 
        })     
        
    .otherwise({
         redirectTo: '/home'
      });

});
            