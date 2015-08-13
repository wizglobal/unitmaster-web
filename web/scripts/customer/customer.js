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


CustomerMngt.controller('profilectrl', function ($scope,$window, CustomerDetails, bankdetails) {
        
    $scope.bankdetails=bankdetails.data[0];
    $scope.profile=CustomerDetails.get();
    
    
});

CustomerMngt.controller('Feedbackctrl', function ($scope,$window) {
    
});
CustomerMngt.controller('changepasswordctrl', function ($scope,$window) {
    
});

CustomerMngt.controller('aboutctrl', function ($scope,$window) {
    
});
CustomerMngt.controller('helpctrl', function ($scope,$window) {
    
});
CustomerMngt.controller('sendfeedbackctrl', function ($scope,$window) {
    
});

CustomerMngt.controller('accountstatementctrl', function ($scope,$window,customerFactory,$route) {
     $scope.accountnumber=angular.fromJson(atob($route.current.params.accountnumber));
    
           customerFactory.getAccountTransaction($scope.accountnumber)
						 .success(function(data) {
							$scope.transactions=data;
                          
							 }) 
						 .error(function(data) {
							details=[];	
							 });
                                                         
        $scope.generatepdf=function(){
            alert("Generating ...");
        }                                                 
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

CustomerMngt.controller('homectrl', function ($scope,$window,customerFactory,CustomerDetails,$location) {

                 
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
                                              
							 }) 
						 .error(function(data) {
							accounts=[];	
							 });
                                                         
                               customerFactory.getCustomerBeneficiaries()
						 .success(function(data) {
							$scope.memberbeneficiaries=data;
							 }) 
						 .error(function(data) {
							accounts=[];	
							 });      
                                                         
         $scope.ViewAdvisor=function(advisorCode){
             	var det = angular.toJson(advisorCode);
                   det=btoa(det);
             $location.path('/agentdetails/'+det);
         }   
         
         
         $scope.ViewAccount=function(accountnumber){
             	var det = angular.toJson(accountnumber);
                   det=btoa(det);
             $location.path('/accountstatement/'+det);
         }   
                 
                 
});
CustomerMngt.config(function($routeProvider,$locationProvider)	{

$locationProvider.hashPrefix("!");

  $routeProvider
   .when('/home', {
     templateUrl: 'views/customer/home.html',   
      controller: 'homectrl' 
        })                     
     
 .when('/profile', {
     templateUrl: 'views/customer/profile.html',   
      controller: 'profilectrl',
      resolve: {		
            bankdetails: function(customerFactory) {
				return customerFactory.getCustomerBankDetails();
			}            
	       }
        })
        	   
  .when('/account', {
     templateUrl: 'views/customer/account.html',   
      controller: 'accountctrl' 
        }) 
     .when('/changePassword', {
     templateUrl: 'views/common/changepassword.html',   
      controller: 'changepasswordctrl' 
        })       
        
    .when('/feedback', {
     templateUrl: 'views/common/feedback.html',   
      controller: 'Feedbackctrl' 
        })           
    .when('/agentdetails/:agentcode', {
     templateUrl: 'views/customer/agentdetails.html',   
      controller: 'agentdetailsctrl' 
        }) 
        
     .when('/accountstatement/:accountnumber', {
     templateUrl: 'views/customer/accountstatement.html',   
      controller: 'accountstatementctrl' 
        })
     .when('/about', {
     templateUrl: 'views/common/about.html',   
      controller: 'aboutctrl' 
        })       
        
    .when('/help', {
     templateUrl: 'views/common/help.html',   
      controller: 'helpctrl' 
        })       
    .when('/Sendfeedback', {
     templateUrl: 'views/common/sendFeedback.html',   
      controller: 'sendfeedbackctrl' 
        })    
    .otherwise({
         redirectTo: '/home'
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
            getAccountTransaction:function (accountnumber) {
		     return $http.get('/Web/rest/transaction/account/'+accountnumber);
            },
           getCustomerBankDetails:function () {
		     return $http.get('/Web/rest/member/Bankdetails',{ cache: true });
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


