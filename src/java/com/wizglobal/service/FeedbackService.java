/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wizglobal.service;

import com.wizglobal.Controller.FeedbacksJpaController;
import com.wizglobal.Controller.exceptions.RollbackFailureException;
import com.wizglobal.entities.Feedbacks;
import com.wizglobal.entities.feedbackPojo;
import com.wizglobal.helpers.tokendetails;
import com.wizglobal.security.LoginToken;
import java.util.List;
import org.json.JSONObject;

/**
 *
 * @author nhif
 */
public class FeedbackService {
    FeedbacksJpaController feedbacksJpaController;
    tokendetails tkn;
    LoginToken loginToken;
    public List<Feedbacks> getCustomerFeedback (String token){
        feedbacksJpaController = new FeedbacksJpaController();
              loginToken= new LoginToken();     
               tkn=loginToken.parseJWT(token);
               System.out.println("Token User name "+tkn.getUsername());
               System.out.println("token category "+tkn.getCategory());
               
       return feedbacksJpaController.findMemberFeedback(tkn.getUsername(), tkn.getCategory());
    }
  
    
    public String CreateFeedback(feedbackPojo feedback,String token) throws RollbackFailureException, Exception{
         feedbacksJpaController = new FeedbacksJpaController();
         JSONObject obj = new JSONObject(); 
              loginToken= new LoginToken(); 
        try {       
               tkn=loginToken.parseJWT(token);
       
               int status= feedbacksJpaController.createFeedback(feedback,tkn.getRefno(),tkn.getCategory());
                          obj.put("status", status);
                          obj.put("msg", "Feedback Receieved ");
              }           
            catch (Exception ex){
                          obj.put("status", 2);
                          obj.put("msg", "Error Occured");
                          obj.put("Exception", ex.toString());
                }                  
               return obj.toString();
    }
}
