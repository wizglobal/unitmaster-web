/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wizglobal.service;

import com.wizglobal.Controller.FeedbacksJpaController;
import com.wizglobal.Controller.exceptions.RollbackFailureException;
import com.wizglobal.entities.Feedbacks;
import com.wizglobal.helpers.tokendetails;
import com.wizglobal.security.LoginToken;
import java.util.List;

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
               
       return feedbacksJpaController.findMemberFeedback(tkn.getUsername(), tkn.getCategory());
    }
    
    public String CreateFeedback(Feedbacks feedback,String token) throws RollbackFailureException, Exception{
         feedbacksJpaController = new FeedbacksJpaController();
              loginToken= new LoginToken();     
               tkn=loginToken.parseJWT(token);
               return feedbacksJpaController.create(feedback);
    }
}
