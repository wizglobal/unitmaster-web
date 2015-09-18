
package com.wizglobal.endpoint;

import com.wizglobal.ExceptionHandling.AppException;
import com.wizglobal.Helpers.AgentAccountList;
import com.wizglobal.config.AppConstants;
import com.wizglobal.entities.Accounts;
import com.wizglobal.entities.Agents;
import com.wizglobal.entities.TransAgent;
import com.wizglobal.entities.TransAgentComm;
import com.wizglobal.service.AccountService;
import com.wizglobal.service.AgentService;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/agent")
public class AgentOperation {
    
    
     @GET
    @Path("customerList")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  public List<AgentAccountList>  getCustomerDetails(@Context HttpHeaders headers)  throws AppException{
     String token =headers.getRequestHeader("token").get(0);
     AgentService  agent = new  AgentService ();
      try {
      
         
        return  agent.getCustomerList(token);
          
      }catch(Exception ex){
          throw new  AppException(Response.Status.CONFLICT.getStatusCode(), 500, 
					ex.toString(), AppConstants.BLOG_POST_URL);
      }
    
  }
    @GET
    @Path("agentdetails")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  public Agents getAccount(@Context HttpHeaders headers)  throws AppException{
     String token =headers.getRequestHeader("token").get(0);
     AgentService  agent = new  AgentService ();
      try {
          return agent.getAgent(token);
      }catch(Exception ex){
          throw new  AppException(Response.Status.CONFLICT.getStatusCode(), 500, 
					ex.toString(), AppConstants.BLOG_POST_URL);
      }
    
  }
   @GET
    @Path("CustomerAgentDetails")
   
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  public Agents getCustomerAgent(@Context HttpHeaders headers,@DefaultValue("Undefined") @QueryParam("agentid") String agentid)  throws AppException{
     String token =headers.getRequestHeader("token").get(0);
     AgentService  agent = new  AgentService ();
      try {
          if (agentid.equals("Undefined")){
            throw new  AppException(Response.Status.NO_CONTENT.getStatusCode(), 501, 
					"Kindly pass the Agent id", AppConstants.BLOG_POST_URL);
          
          }
          else {
              System.out.println("Agent id is " + agentid);
          return agent.getCustomerAgent(token,agentid);
          }
      }catch(Exception ex){
          throw new  AppException(Response.Status.CONFLICT.getStatusCode(), 500, 
					ex.toString(), AppConstants.BLOG_POST_URL);
      }
    
  }
    @GET
    @Path("agenttransactions")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
   public List<TransAgent> getTransactions(@Context HttpHeaders headers)  throws AppException{
       String token =headers.getRequestHeader("token").get(0);
     
 
     AgentService  agent = new  AgentService ();
      try {
          return agent.agentTransactions(token);
      }catch(Exception ex){
          throw new  AppException(Response.Status.CONFLICT.getStatusCode(), 500, 
					ex.toString(), AppConstants.BLOG_POST_URL);
      }
   }
     @GET
    @Path("agentdetailedtransactions")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public List<TransAgentComm> getDetailedTransactions(@Context HttpHeaders headers,@DefaultValue("Undefined") @QueryParam("memberno") String memberno)  throws AppException{
         String token =headers.getRequestHeader("token").get(0);
     
 
     AgentService  agent = new  AgentService ();
      try {
          if (memberno.equals("Undefined")){
            throw new  AppException(Response.Status.NO_CONTENT.getStatusCode(), 501, 
					"Kindly pass the Member Number", AppConstants.BLOG_POST_URL);
          
          }else{
          
          return agent.agentDetailedTransactions(token,memberno);
          }
      }catch(Exception ex){
          throw new  AppException(Response.Status.CONFLICT.getStatusCode(), 500, 
					ex.toString(), AppConstants.BLOG_POST_URL);
      }
    }
    
}
