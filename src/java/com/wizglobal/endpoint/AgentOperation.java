
package com.wizglobal.endpoint;

import com.wizglobal.ExceptionHandling.AppException;
import com.wizglobal.config.AppConstants;
import com.wizglobal.entities.Accounts;
import com.wizglobal.entities.Agents;
import com.wizglobal.service.AccountService;
import com.wizglobal.service.AgentService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/agent")
public class AgentOperation {
    
    
     @GET
    @Path("agentdetails/{agentnumber}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  public Agents getAccount(@Context HttpHeaders headers,@PathParam("agentnumber") String agentnumber)  throws AppException{
     String token =headers.getRequestHeader("token").get(0);
     AgentService  agent = new  AgentService ();
      try {
          return agent.getAgent(agentnumber, token);
      }catch(Exception ex){
          throw new  AppException(Response.Status.CONFLICT.getStatusCode(), 500, 
					ex.toString(), AppConstants.BLOG_POST_URL);
      }
    
  }

    
}
