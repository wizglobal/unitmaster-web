/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wizglobal.endpoint;

import com.wizglobal.ExceptionHandling.AppException;
import com.wizglobal.config.AppConstants;
import com.wizglobal.entities.Accounts;
import com.wizglobal.entities.Trans;
import com.wizglobal.service.AccountService;
import com.wizglobal.service.TransactionService;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;



@Path("/transaction")
public class TransactionOperation {
    
    TransactionService trxn;
    
    @GET
    @Path("account")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public String  getAccountTransactions(@QueryParam(value = "accountnumber") String accountnumber,@QueryParam(value = "securitycode") String securitycode) throws AppException{
     
     trxn = new  TransactionService();
      try {
             return trxn.listaccounttransactions(accountnumber,securitycode);
      }catch (Exception ex){
           throw new  AppException(Response.Status.CONFLICT.getStatusCode(), 500, 
					ex.toString(), AppConstants.BLOG_POST_URL);
      }
   }
    
    
    @POST
    @Path("/ConfirmTransaction")
    @Consumes(MediaType.APPLICATION_JSON)
    public String  ConfirmTransaction(Trans tran ,@Context HttpHeaders headers) throws AppException  {
        trxn = new  TransactionService();
        try {  
          String token =headers.getRequestHeader("token").get(0); 
         
            if ( tran.getTransId()!=null ){  
                return trxn.ConfirmTransaction(token, tran.getTransId());
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
    
}
