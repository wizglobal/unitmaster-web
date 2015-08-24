'use strict';

var StaffMngt= angular.module('StaffApp', ['ngRoute','angularUtils.directives.dirPagination','ngDialog'] ); 

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
     templateUrl: 'views/common/staffFeedbacks.html',   
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
StaffMngt.controller('Feedbackctrl', function ($scope,$window,staffFactory,ngDialog) {
      staffFactory.getFeedbacks()
                .success(function(data) {
		  $scope.feedbacks=data;
                  console.log("feedbacks");
                  console.log(data);
                  }) 
		.error(function(data) {
		  $scope.feedbacks=[];	
		  });
                  
           $scope.feedbackResponse=function(feedback){
                ngDialog.openConfirm({
                    template: 'staffFeedbacktmpl',
                    className: 'ngdialog-theme-default',
                    scope: $scope
                }).then(function (value) {
                    var det ={};
                    det.response=value.response;
                    det.feedbackid=feedback.id; 
                    det.responsedate=new Date();
                    
                     staffFactory.updateFeedback(det)
                           .success(function(data) {
                                 if (data.status !=2){                 
                                  alert(data.msg);
                                  }
                                  else {
                                      alert(data.Exception);

                                  }
                               }) 
                           .error(function(data) {
                                console.log(data);

                                });
                    
                }, function (reason) {
                    console.log('Modal promise rejected. Reason: ', reason);
                });
               
           }       
    
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


StaffMngt.controller('confirmMembersctrl', function ($scope,$window,staffFactory,ngDialog) {
    
    
          staffFactory.getUnconfirmedMembers()
              .success(function(data) {
			    	 $scope.members=data;
					}) 
				 .error(function(data) {
				   $scope.members=[];
					});
                                        
     $scope.viewDetails =function(member){
         console.log(member);
        $scope.memb=member;
                ngDialog.openConfirm({
                    template: 'templateId',
                    className: 'ngdialog-theme-default',
                    scope: $scope
                }).then(function (value) {
                     var det={};
                                          
                       staffFactory.confirmMember(member)
                           .success(function(data) {
                                 if (data.status !=2){                 
                                  alert(data.msg);
                                  }
                                  else {
                                      alert(data.Exception);

                                  }
                               }) 
                           .error(function(data) {
                                console.log(data);

                                });
                    
                    
                }, function (reason) {
                    console.log('Modal promise rejected. Reason: ', reason);
                });
     }                                  
});


StaffMngt.controller('registerCustomerctrl', function ($scope,$window,staffFactory,prompt) {
   
    
      staffFactory.getUnregisteredCustomers()
              .success(function(data) {
			    	 $scope.members=data;
                      
					}) 
				 .error(function(data) {
				   $scope.members=[];
					});
                                        
       $scope.registermember=function(member){
        var userdetails={}; 
          console.log(member);
              prompt( "Kindly Enter The Member Username ", member.memberNo ).then(
                    function( response ) {
                                userdetails.username=response ;
                                userdetails.category="customer" ;
                                userdetails.EMail=member.EMail ;
                                userdetails.refno=member.memberNo ;
                                userdetails.passwrd="test" ;
                                userdetails.number=member.memberNo ;
        
                        staffFactory.registerUsers(userdetails)
                           .success(function(data) {
                                 if (data.status !=2){                 
                                  alert(data.msg);
                                  }
                                  else {
                                      alert(data.Exception);

                                  }
                               }) 
                           .error(function(data) {
                                console.log(data);

                                });
                        
                    },
                    function() {
                        alert("You Have to Provide a Username");
                    }
                );
          
        
    }                                  
                                    
});
StaffMngt.controller('registerAgentctrl', function ($scope,$window,staffFactory,prompt) {
    
        staffFactory.getUnregisteredAgents()
              .success(function(data) {
			    	 $scope.agents=data;
                       
					}) 
				 .error(function(data) {
				   $scope.agents=[];
					});
                                        
                                        
    $scope.registerAgent=function(agent){
        var userdetails={};     
             prompt( "Kindly Enter The Agent Username ", agent.agentNo ).then(
                    function( response ) {
                                userdetails.username=response ;
                                userdetails.category="agent" ;
                                userdetails.EMail=agent.EMail ;
                                userdetails.refno=agent.agentNo ;
                                userdetails.passwrd="test" ;
                                userdetails.number=agent.agentNo ;
        
                        staffFactory.registerUsers(userdetails)
                           .success(function(data) {
                                 if (data.status !=2){                 
                                  alert(data.msg);
                                  }
                                  else {
                                      alert(data.Exception);

                                  }
                               }) 
                           .error(function(data) {
                                console.log(data);

                                });
                        
                    },
                    function() {
                        alert("You Have to Provide a Username");
                    }
                );     
    }                                   
    
});
StaffMngt.controller('registerStaffctrl', function ($scope,$window,staffFactory,prompt) {
    
         staffFactory.getUnregisteredStaff()
              .success(function(data) {
			    	 $scope.staffs=data;              
					}) 
				 .error(function(data) {
				   $scope.staffs=[];
					});
                                        
    $scope.registerStaff=function(staff){
        var userdetails={};   
        
            prompt( "Kindly Enter The Staff Username ", staff.userId ).then(
                    function( response ) {
                                userdetails.username=response ;
                                userdetails.category="staff" ;
                                userdetails.EMail=staff.EMail ;
                                userdetails.refno=staff.userId ;
                                userdetails.passwrd="test" ;
                                userdetails.number=staff.userId ;
        
                        staffFactory.registerUsers(userdetails)
                           .success(function(data) {
                                 if (data.status !=2){                 
                                  alert(data.msg);
                                  }
                                  else {
                                      alert(data.Exception);

                                  }
                               }) 
                           .error(function(data) {
                                console.log(data);

                                });
                        
                    },
                    function() {
                        alert("You Have to Provide a Username");
                    }
                );
          
        
    }                                    
    
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
             registerUsers:function (userdetails) {
		     return $http.post('/Web/rest/staff/RegisterUsers',userdetails);
               },
             confirmMember:function (details) {
                 console.log(details);
		     return $http.post('/Web/rest/staff/ConfirmMember',details);
               },
               
               updateFeedback:function (feedback) {
               
		     return $http.post('/Web/rest/staff/updateFeedback',feedback);
               },
             getFeedbacks:function () {
		     return $http.get('/Web/rest/staff/getFeedbacks',{ cache: true });
            },
            
            logout:function () {
		     return $http.get('/web/logout');
            },
	}
	return data;
}]);


StaffMngt.factory("prompt", function( $window, $q ) {
                // Define promise-based prompt() method.
                function prompt( message, defaultValue ) {
                    var defer = $q.defer();
                    // The native prompt will return null or a string.
                    var response = $window.prompt( message, defaultValue );
                    if ( response === null ) {
                        defer.reject();
                    } else {
                        defer.resolve( response );
                    }
                    return( defer.promise );
                }
                return( prompt );
            }
        );



