/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wizglobal.endpoint;

import com.wizglobal.ExceptionHandling.AppException;
import com.wizglobal.Helpers.customerAccounts;
import com.wizglobal.config.AppConstants;
import com.wizglobal.entities.Memberpass;
import com.wizglobal.service.StaffService;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/staff")
public class StaffOperations {
    
    StaffService Staffservice;
             @GET
             @Path("OnlineMembers")
             @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
             public List<Memberpass> getOnlineMembers(@Context HttpHeaders headers) throws AppException  {
                 Staffservice = new StaffService();
               try {  
               return  Staffservice.onlineUsers();
               } catch (Exception ex){
         throw new  AppException(Response.Status.CONFLICT.getStatusCode(), 500, 
					ex.toString(), AppConstants.BLOG_POST_URL);
     }
             }
             
             @GET
             @Path("Profile")
             @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
             public String getProfile(@Context HttpHeaders headers) throws AppException  {
                 try {
                 return "";
                 } catch (Exception ex){
         throw new  AppException(Response.Status.CONFLICT.getStatusCode(), 500, 
					ex.toString(), AppConstants.BLOG_POST_URL);
     }
             }
}
