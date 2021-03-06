/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wizglobal.endpoint;

import com.wizglobal.ExceptionHandling.AppException;
import com.wizglobal.Helpers.customerAccounts;
import com.wizglobal.config.AppConstants;
import com.wizglobal.entities.Agents;
import com.wizglobal.entities.Feedbacks;
import com.wizglobal.entities.IRates;
import com.wizglobal.entities.Memberpass;
import com.wizglobal.entities.Members;
import com.wizglobal.entities.Membersbankdetails;
import com.wizglobal.entities.Navs;
import com.wizglobal.entities.Trans;
import com.wizglobal.entities.Userdetails;
import com.wizglobal.entities.Usersetup;
import com.wizglobal.entities.feedbackPojo;
import com.wizglobal.helpers.credentials;
import com.wizglobal.helpers.memberConfirmDetails;
import com.wizglobal.service.FeedbackService;
import com.wizglobal.service.LoginService;
import com.wizglobal.service.MemberService;
import com.wizglobal.service.StaffService;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/staff")
public class StaffOperations {
     MemberService memService;
    StaffService Staffservice;
             @GET
             @Path("OnlineMembers")
             @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
             public List<Memberpass> getOnlineMembers(@Context HttpHeaders headers) throws AppException  {
                 Staffservice = new StaffService();
               try {  
                   String token =headers.getRequestHeader("token").get(0);  
               return  Staffservice.onlineUsers(token);
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
             
               @GET
    @Path("UnregisteredCustomers")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
   public List<Members> getUnregisteredCustomers(@Context HttpHeaders headers) throws AppException  {
       memService = new  MemberService();
     try {
       String token =headers.getRequestHeader("token").get(0);      
     return memService.getUnregisteredCustomers(token);
     }
     catch (Exception ex){
         System.out.println(ex);
         throw new  AppException(Response.Status.CONFLICT.getStatusCode(), 500, 
					ex.toString(), AppConstants.BLOG_POST_URL);
     }
    
   }
   
    @GET
    @Path("UnregisteredAgents")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
   public List<Agents> getUnregisteredAgents(@Context HttpHeaders headers) throws AppException  {
      Staffservice = new StaffService();
     try {
       String token =headers.getRequestHeader("token").get(0);      
     return Staffservice.findUnregisteredAgents(token);
     }
     catch (Exception ex){
         System.out.println(ex);
         throw new  AppException(Response.Status.CONFLICT.getStatusCode(), 500, 
					ex.toString(), AppConstants.BLOG_POST_URL);
     }
    
   }
    @GET
    @Path("UnregisteredStaff")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
   public List<Usersetup> getUnregisteredStaff(@Context HttpHeaders headers) throws AppException  {
      Staffservice = new StaffService();
     try {
       String token =headers.getRequestHeader("token").get(0);      
     return Staffservice.findUnregisteredStaff(token);
     }
     catch (Exception ex){
         System.out.println(ex);
         throw new  AppException(Response.Status.CONFLICT.getStatusCode(), 500, 
					ex.toString(), AppConstants.BLOG_POST_URL);
     }
    
   }
   
   
    @GET
    @Path("UnconfirmedMembers")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
   public List<Members> UnconfirmedStaff(@Context HttpHeaders headers) throws AppException  {
      Staffservice = new StaffService();
     try {
       String token =headers.getRequestHeader("token").get(0);      
     return Staffservice.findUnconfirmedMembers(token);
     }
     catch (Exception ex){
         System.out.println(ex);
         throw new  AppException(Response.Status.CONFLICT.getStatusCode(), 500, 
					ex.toString(), AppConstants.BLOG_POST_URL);
     }
    
   }
   
   @GET
    @Path("UnconfirmedNavs")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
   public List<Navs> UnconfirmedNavs(@Context HttpHeaders headers) throws AppException  {
      Staffservice = new StaffService();
     try {
       String token =headers.getRequestHeader("token").get(0);      
     return Staffservice.findUnconfirmedNav(token);
     }
     catch (Exception ex){
         System.out.println(ex);
         throw new  AppException(Response.Status.CONFLICT.getStatusCode(), 500, 
					ex.toString(), AppConstants.BLOG_POST_URL);
     }
    
   }
   
    @GET
    @Path("UnconfirmedTransactions")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
   public List<Trans> UnconfirmedTransactions(@Context HttpHeaders headers) throws AppException  {
      Staffservice = new StaffService();
     try {
       String token =headers.getRequestHeader("token").get(0);      
     return Staffservice.findUnconfirmedTransactions(token);
     }
     catch (Exception ex){
         System.out.println(ex);
         throw new  AppException(Response.Status.CONFLICT.getStatusCode(), 500, 
					ex.toString(), AppConstants.BLOG_POST_URL);
     }
    
   }
   
    @GET
    @Path("UnconfirmedInterests")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
   public List<IRates> UnconfirmedInterests(@Context HttpHeaders headers) throws AppException  {
      Staffservice = new StaffService();
     try {
       String token =headers.getRequestHeader("token").get(0);      
     return Staffservice.findUnconfirmedInterests(token);
     }
     catch (Exception ex){
         System.out.println(ex);
         throw new  AppException(Response.Status.CONFLICT.getStatusCode(), 500, 
					ex.toString(), AppConstants.BLOG_POST_URL);
     }
    
   }
    @GET
    @Path("/getFeedbacks")
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Feedbacks>  getfeedbacks(@Context HttpHeaders headers) throws AppException  {
       Staffservice = new StaffService();
       
     try {  
          String token =headers.getRequestHeader("token").get(0); 
          
                return Staffservice.getAllFeedbacks(token);
          
      
        
     }catch (Exception exp){
         System.err.print(exp);
         throw new  AppException(Response.Status.FORBIDDEN.getStatusCode(), 500, 
					exp.toString(), AppConstants.BLOG_POST_URL);
     }     
           
     
    }
   
    @POST
    @Path("/RegisterUsers")
    @Consumes(MediaType.APPLICATION_JSON)
    public String  registerAgent(Userdetails userdetails ,@Context HttpHeaders headers) throws AppException  {
       Staffservice = new StaffService();
       
     try {  
          String token =headers.getRequestHeader("token").get(0); 
            if ( userdetails.getUsername() !=null || userdetails.getRefno() !=null || userdetails.getEMail() !=null){  
                return Staffservice.RegisterUser(token,userdetails);
           }
           else {
               throw new  AppException(Response.Status.BAD_REQUEST.getStatusCode(), 501, 
					" Kindly pass the Userdetails ", AppConstants.BLOG_POST_URL);
      
        }
     }catch (Exception exp){
         System.err.print(exp);
         throw new  AppException(Response.Status.FORBIDDEN.getStatusCode(), 500, 
					exp.toString(), AppConstants.BLOG_POST_URL);
     }     
           
     
    }
    
     @POST
    @Path("/ConfirmMember")
    @Consumes(MediaType.APPLICATION_JSON)
    public String  confirmMember(Members MemberConfirmDetails ,@Context HttpHeaders headers) throws AppException  {
       Staffservice = new StaffService();
        try {  
          String token =headers.getRequestHeader("token").get(0); 
          System.out.println("Member number" +MemberConfirmDetails.getMemberNo());
            if ( MemberConfirmDetails.getMemberNo() !=null ){  
                return Staffservice.ConfirmMember(token, MemberConfirmDetails.getMemberNo());
           }
           else {
               throw new  AppException(Response.Status.BAD_REQUEST.getStatusCode(), 501, 
					" Kindly pass the Member Number ", AppConstants.BLOG_POST_URL);
      
        }
     }catch (Exception exp){
         exp.printStackTrace();
         throw new  AppException(Response.Status.FORBIDDEN.getStatusCode(), 500, 
					exp.toString(), AppConstants.BLOG_POST_URL);
     }
        
    }    
    @POST
    @Path("/updateFeedback")
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateFeedback(@Context HttpHeaders headers,feedbackPojo  feedbacks) throws AppException  {
          Staffservice = new StaffService();
        
       
         try {
             String token =headers.getRequestHeader("token").get(0);
          
          return   Staffservice.updateFeedback(token, feedbacks);
         }  
         catch(Exception ex){
          throw new  AppException(Response.Status.CONFLICT.getStatusCode(), 500, 
					ex.toString(), AppConstants.BLOG_POST_URL);
      }
    
  }
    @POST
    @Path("/updateNav")
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateNav(@Context HttpHeaders headers,Navs  nav) throws AppException  {
          Staffservice = new StaffService();
        
       
         try {
             String token =headers.getRequestHeader("token").get(0);
          
          return   Staffservice.ConfirmNav(token, nav.getNavId());
         }  
         catch(Exception ex){
          throw new  AppException(Response.Status.CONFLICT.getStatusCode(), 500, 
					ex.toString(), AppConstants.BLOG_POST_URL);
      }
    
  }
    
    @POST
    @Path("/updateIrate")
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateIrate(@Context HttpHeaders headers,IRates  irates) throws AppException  {
          Staffservice = new StaffService();
        
       
         try {
             String token =headers.getRequestHeader("token").get(0);
          
          return   Staffservice.ConfirmIrates(token, irates.getRateId());
         }  
         catch(Exception ex){
          throw new  AppException(Response.Status.CONFLICT.getStatusCode(), 500, 
					ex.toString(), AppConstants.BLOG_POST_URL);
      }
    
  }
   
}
