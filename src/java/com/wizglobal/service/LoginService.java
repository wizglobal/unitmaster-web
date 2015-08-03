/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wizglobal.service;

import com.wizglobal.Controller.MemberpassJpaController;
import com.wizglobal.config.AppConstants;
import com.wizglobal.entities.Memberpass;
import com.wizglobal.security.LoginToken;
import com.wizglobal.security.MD5Checksum;
import org.json.JSONObject;



/**
 *
 * @author nhif
 */

public class LoginService {
    
    
   MemberpassJpaController membercontroller;
   Memberpass memberpass;
   MD5Checksum checksum;
   LoginToken logintoken;
   
   
   public String Authenticate(String username,String password){
       String status =null;
       membercontroller = new MemberpassJpaController();
       checksum = new MD5Checksum();
        logintoken = new LoginToken();
          memberpass =  membercontroller.findMemberDetails(username);
      
             if (checksum.Checkhash("09d90c0c39cf41624aa62f142e82cecf", password)){
               System.out.println("The User Category is " + memberpass.getCategory());
                 JSONObject obj = new JSONObject();  
                   obj.put("username", username);
                   obj.put("category",memberpass.getCategory());
                   obj.put("memberno",memberpass.getMemberno());
                        // user Authenticated create token
                    String token =logintoken.createJWT(memberpass.getMemberno(),AppConstants.Token_issuer, obj.toString(), AppConstants.Token_ttl);
                    obj.put("token", token);
                 status=obj.toString();
             }
             else {status=null;}
     
     return status;  
   }
   
}
