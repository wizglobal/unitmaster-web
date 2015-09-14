var CustomerMngt= angular.module('CustomerApp', ['ngRoute','angularUtils.directives.dirPagination','ngDialog'] ); 

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

CustomerMngt.controller('Feedbackctrl', function ($scope,$window,customerFactory,ngDialog) {
    customerFactory.getFeedbacks()
    .success(function(data) {
		  $scope.feedbacks=data;
                       
                  }) 
		.error(function(data) {
		  $scope.feedbacks=[];	
		  });
                  
      $scope.showfeedback =function(feedback){
             $scope.resp=feedback;
                ngDialog.openConfirm({
                    template: 'feedbackResptmpl',
                    className: 'ngdialog-theme-default',
                    scope: $scope
                }).then(function (value) {
                    
                  }, function (reason) {
                    
                });
      }             
});
CustomerMngt.controller('changepasswordctrl', function ($scope,$window,customerFactory,ngDialog) {
    
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
                            template: '<div> <p>Error Chaging Your  Password </p><p>Invalid Current Password</p></div>',
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

CustomerMngt.controller('aboutctrl', function ($scope,$window) {
    
});
CustomerMngt.controller('helpctrl', function ($scope,$window) {
    
});
CustomerMngt.controller('unitizedctrl', function ($scope,$window,statement) {
      $scope.accttransaction=angular.fromJson(statement.getStatement().transaction);
       $scope.transummary=statement.getStatement().summary;
         console.log($scope.accttransaction);
});
CustomerMngt.controller('interestBasedctrl', function ($scope,$window,statement) {
         $scope.accttransaction=angular.fromJson(statement.getStatement().transaction);
         $scope.transummary=statement.getStatement().summary;
         console.log("Transa Summary ");
         console.log($scope.transummary);
        
    
});
CustomerMngt.controller('sendfeedbackctrl', function ($scope,$window,CustomerDetails,customerFactory,ngDialog) {
  //  console.log("Member details ");
   //  console.log(CustomerDetails.get());
    $scope.SubmitFeedback=function(feedback){
        customerFactory.postFeedback(feedback)
                .success(function(data) {
		          if (data.status !==2){                 
                                  ngDialog.open({
                                    template: '<p>Feedback Received </p>',
                                    plain: true
                                });
                                 $scope.feedback="";
                                  }
                                  else {
                                    //  alert(data.Exception);
                                     ngDialog.open({
                                    template: '<p>An Error Occurred Posting Your Feedback</p><p>Kindly Retry later</p>',
                                    plain: true
                                });
                                $scope.feedback="";

                                  }
                  }) 
		.error(function(data) {
		         //  alert(data)	;
                                   ngDialog.open({
                                    template: '<p>An Error Occurred Posting Your Feedback</p><p>Kindly Retry later</p>',
                                    plain: true
                                });
		  });
    }
    
});

CustomerMngt.controller('accountstatementctrl', function ($scope,$window,customerFactory,$route,statement,$location) {
     $scope.accountnumber=angular.fromJson(atob($route.current.params.accountnumber));
   
           customerFactory.getAccountTransaction($scope.accountnumber.accno,$scope.accountnumber.securitycode)
						 .success(function(data) {
                                                          console.log("Transaction data");
                                                           console.log(data);
                                                           statement.saveStatement(data);
                                                           if (data.statementtype=="interest"){
                                                                 $location.path('/interestBased');
                                                           }else if (data.statementtype=="unitized"){
                                                                 $location.path('/unitized');
                                                           }
                                                           
                                                      
							 }) 
						 .error(function(data) {
							details=[];	
							 });
                                                         
        $scope.generatepdf=function(){
            alert("Generating ...");
        }                                                 
});

CustomerMngt.controller('agentdetailsctrl', function ($scope,customerFactory,$route,CustomerDetails) {
    
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
         
         
         $scope.ViewAccount=function(accountnumber,SECURITY_CODE){
     
             var act={};
                    act.accno=accountnumber;
                    act.securitycode=SECURITY_CODE;
             	var det = angular.toJson(act);
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
     .when('/interestBased', {
     templateUrl: 'views/customer/interestBased.html',   
      controller: 'interestBasedctrl' 
        })   
     .when('/unitized', {
     templateUrl: 'views/customer/unitized.html',   
      controller: 'unitizedctrl' 
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
		     return $http.get('/Web/rest/agent/CustomerAgentDetails?agentid='+agentnumber);
            },
            getAccountTransaction:function (accountnumber,securitycode) {
		     return $http.get('/Web/rest/transaction/account?accountnumber='+accountnumber +" &securitycode=" +securitycode);
            },
           getCustomerBankDetails:function () {
		     return $http.get('/Web/rest/member/Bankdetails',{ cache: true });
            },
            getFeedbacks:function () {
		     return $http.get('/Web/rest/feedback/memberfeedback',{ cache: true });
            },
             postFeedback:function (feedback) {
               //  console.log(feedback);
		     return $http.post('/Web/rest/feedback/createFeedback',feedback);
               },
               changePwd:function (credentials) {
                 
		     return $http.post('/Web/rest/authentication/changepassword',credentials);
               },
          
            logout:function () {
		     return $http.get('/web/logout');
            },
	}
	return data;
}]);
CustomerMngt.service('statement', function () {
    var statement;
        this.saveStatement=function (stmt){
           statement=stmt;
        }
        this.getStatement=function(){
            return statement;
        }
});
CustomerMngt.directive('pwCheck', function() {
        return {
            require: 'ngModel',
            link: function (scope, elem, attrs, ctrl) {
                var firstPassword = '#' + attrs.pwCheck;
                $(elem).add(firstPassword).on('keyup', function () {
                    scope.$apply(function () {
                        var v = elem.val()===$(firstPassword).val();
                        ctrl.$setValidity('pwcheck', v);
                    });
                });
            }
        }
    });

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


