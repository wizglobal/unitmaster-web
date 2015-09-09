/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wizglobal.endpoint;

import com.wizglobal.ExceptionHandling.AppException;
import com.wizglobal.config.AppConstants;
import com.wizglobal.helpers.credentials;
import com.wizglobal.helpers.pwdCredential;
import com.wizglobal.service.LoginService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONObject;


@Path("/authentication")
public class LoginOperation {
      LoginService    logservice ;
    
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public String dologin(credentials  userCredentials) throws AppException  {
        String rtn = "";
           logservice = new LoginService ();
       
     try {      
           String tk=logservice.Authenticate(userCredentials.getUsername(), userCredentials.getPassword());
           if (tk!=null){
           
            System.out.println(tk);
            rtn=   tk;       
           }else {
               throw new  AppException(Response.Status.FORBIDDEN.getStatusCode(), 401, 
					"Invalid Credential Passed", AppConstants.BLOG_POST_URL);
      
        }
     }catch (Exception exp){
         System.err.print(exp);
         throw new  AppException(Response.Status.FORBIDDEN.getStatusCode(), 500, 
					exp.toString(), AppConstants.BLOG_POST_URL);
     }     
           
        return rtn;
    }
    @GET
    @Path("/logout")
    public String dologout() throws AppException  {
        
        return "success";
    }
    
    @POST
    @Path("/changepassword")
    @Consumes(MediaType.APPLICATION_JSON)
    public String changeCredential(pwdCredential  cred,@Context HttpHeaders headers) throws AppException  {
       String token =headers.getRequestHeader("token").get(0);
        String resp="";
        
        try {
            logservice = new LoginService ();
            
System.out.println( "token " + token);

        resp=logservice.ChangePassword(cred,token);
         }catch (Exception exp){
         System.err.print(exp);
         throw new  AppException(Response.Status.FORBIDDEN.getStatusCode(), 500, 
					exp.toString(), AppConstants.BLOG_POST_URL);
     }  
        return resp;
    }
    
}
