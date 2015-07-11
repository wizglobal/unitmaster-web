/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wizglobal.endpoint;

import com.wizglobal.ExceptionHandling.AppException;
import com.wizglobal.config.AppConstants;
import com.wizglobal.entities.Beneficiaries;
import com.wizglobal.entities.Members;
import com.wizglobal.service.BeneficiaryService;
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
@Path("/beneficiary")
public class beneficiaryOperation { 
    BeneficiaryService beneficiaryService;
    
    @GET
    @Path("memberBeneficiary")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  public List<Beneficiaries> getMemberbeneficiary(@Context HttpHeaders headers) throws AppException  {
     
     beneficiaryService = new  BeneficiaryService();
    
String token =headers.getRequestHeader("token").get(0);



   
     try {
       return beneficiaryService.getmemberBeneficiaries(token);
     }
     catch (Exception ex){
         throw new  AppException(Response.Status.CONFLICT.getStatusCode(), 500, 
					ex.toString(), AppConstants.BLOG_POST_URL);
     }
   
  }
}
