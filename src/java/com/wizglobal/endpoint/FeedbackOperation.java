/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wizglobal.endpoint;

import com.wizglobal.ExceptionHandling.AppException;
import com.wizglobal.config.AppConstants;
import com.wizglobal.entities.Feedbacks;
import com.wizglobal.helpers.credentials;
import com.wizglobal.service.FeedbackService;
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

@Path("/feedback")
public class FeedbackOperation {
   FeedbackService  feedbackService ; 
    @GET
    @Path("memberfeedback")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
     public List<Feedbacks> getCustomerFeedback(@Context HttpHeaders headers) throws AppException{
         String token =headers.getRequestHeader("token").get(0);
         feedbackService = new FeedbackService();
         try {
          return   feedbackService.getCustomerFeedback(token);
         }  
         catch(Exception ex){
          throw new  AppException(Response.Status.CONFLICT.getStatusCode(), 500, 
					ex.toString(), AppConstants.BLOG_POST_URL);
      }
     }
       @POST
    @Path("/createFeedback")
    @Consumes(MediaType.APPLICATION_JSON)
    public String createFeedback(@Context HttpHeaders headers,Feedbacks  feedbacks) throws AppException  {
         String token =headers.getRequestHeader("token").get(0);
         feedbackService = new FeedbackService();
         try {
          return   feedbackService.CreateFeedback(feedbacks, token);
         }  
         catch(Exception ex){
          throw new  AppException(Response.Status.CONFLICT.getStatusCode(), 500, 
					ex.toString(), AppConstants.BLOG_POST_URL);
      }
    } 
}
