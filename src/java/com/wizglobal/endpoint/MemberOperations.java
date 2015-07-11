/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wizglobal.endpoint;

import com.wizglobal.ExceptionHandling.AppException;
import com.wizglobal.Helpers.customerAccounts;
import com.wizglobal.config.AppConstants;
import com.wizglobal.entities.Accounts;
import com.wizglobal.entities.Members;
import com.wizglobal.service.AccountService;
import com.wizglobal.service.MemberService;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author nhif
 */
@Path("/member")
public class MemberOperations {
    
    MemberService memService;
    @GET
    @Path("memberdetails")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  public Members getMemberdetails(@Context HttpHeaders headers) throws AppException  {
     
     memService = new  MemberService();
     Members mem=new Members();
     try {
     String token =headers.getRequestHeader("token").get(0);    
     mem= memService.getMemberdetails(token);
     }
     catch (Exception ex){
         throw new  AppException(Response.Status.CONFLICT.getStatusCode(), 500, 
					ex.toString(), AppConstants.BLOG_POST_URL);
     }
    return mem;
  }
  
    @GET
    @Path("Accounts")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
   public List<customerAccounts> getAccount(@Context HttpHeaders headers) throws AppException  {
       memService = new  MemberService();
     List<customerAccounts> cacc  ;
     try {
       String token =headers.getRequestHeader("token").get(0);      
     cacc= memService.getMemberAccounts(token);
     }
     catch (Exception ex){
         System.out.println(ex);
         throw new  AppException(Response.Status.CONFLICT.getStatusCode(), 500, 
					ex.toString(), AppConstants.BLOG_POST_URL);
     }
    return cacc;
   }
}
