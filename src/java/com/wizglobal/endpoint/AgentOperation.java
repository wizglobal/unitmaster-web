
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
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
     
     System.out.println(token);
     AgentService  agent = new  AgentService ();
      try {
          return agent.getAgent(token);
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
    public List<TransAgentComm> getDetailedTransactions(@Context HttpHeaders headers)  throws AppException{
         String token =headers.getRequestHeader("token").get(0);
     
 
     AgentService  agent = new  AgentService ();
      try {
          return agent.agentDetailedTransactions(token);
      }catch(Exception ex){
          throw new  AppException(Response.Status.CONFLICT.getStatusCode(), 500, 
					ex.toString(), AppConstants.BLOG_POST_URL);
      }
    }
    
}
