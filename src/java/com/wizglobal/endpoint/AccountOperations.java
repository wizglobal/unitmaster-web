/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wizglobal.endpoint;

import com.wizglobal.ExceptionHandling.AppException;
import com.wizglobal.Helpers.CustomeragentsAccounts;
import com.wizglobal.config.AppConstants;
import com.wizglobal.entities.Accounts;
import com.wizglobal.service.AccountService;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/account")
public class AccountOperations {
AccountService acct;
    
    @GET
    @Path("accountid")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  public Accounts getAccount(@Context HttpHeaders headers) {
     String token =headers.getRequestHeader("token").get(0);
     acct = new  AccountService();
      
    return acct.getacct1(token);
  }

    @GET
    @Path("accoutlist")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public List<Accounts>  ListAccount() {
     
     acct = new  AccountService();
      
    return acct.listAccount1();
   }
    
    @GET
    @Path("member")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public List<Accounts>  MemberAccounts(@Context HttpHeaders headers) {
     String token =headers.getRequestHeader("token").get(0);
     acct = new  AccountService();
      
    return acct.getMemberAccounts(token);
   }
    @GET
    @Path("Agent")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public List<CustomeragentsAccounts>  getCustomerAgents(@Context HttpHeaders headers) {
     String token =headers.getRequestHeader("token").get(0);
     acct = new  AccountService();
      
    return acct.findCustomerAgents(token);
   }  
    

    

}
