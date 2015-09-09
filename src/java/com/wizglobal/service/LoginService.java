/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wizglobal.service;

import com.wizglobal.Controller.MemberpassJpaController;
import com.wizglobal.config.AppConstants;
import com.wizglobal.entities.Memberpass;
import com.wizglobal.helpers.pwdCredential;
import com.wizglobal.helpers.tokendetails;
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
       
       String status ="";
       membercontroller = new MemberpassJpaController();
       checksum = new MD5Checksum();
        logintoken = new LoginToken();
          memberpass =  membercontroller.findMemberDetails(username);
      
             if (checksum.Checkhash(memberpass.getPasswrd(), password)){
                 JSONObject obj = new JSONObject();  
                   obj.put("username", username);
                   obj.put("refnum", memberpass.getRefno());
                   obj.put("category",memberpass.getCategory());
                   obj.put("memberno",memberpass.getRefno());
                        // user Authenticated create token
                    String token =logintoken.createJWT(memberpass.getMemberno(),AppConstants.Token_issuer, obj.toString(), AppConstants.Token_ttl);
                    obj.put("token", token);
                 status=obj.toString();
             }
             else {status=null;}
     
     return status;  
   }
   public String ChangePassword (pwdCredential cred,String token){
       JSONObject obj = new JSONObject();
           logintoken= new LoginToken(); 
           checksum = new MD5Checksum();
         tokendetails tkn=logintoken.parseJWT(token);
         membercontroller = new MemberpassJpaController();
        
     try {       
            memberpass =  membercontroller.findMemberDetails(tkn.getUsername());
            
         
            if (checksum.Checkhash(memberpass.getPasswrd(), cred.getOldpwd())){
                
                String pwdHash = checksum.hashPassword (cred.getNewpwd());
               int Status  =membercontroller.UpdatePwd(pwdHash, tkn.getUsername());
                if (Status == 1){
                System.out.println("password Changed");
                          obj.put("status", Status);
                          obj.put("msg", "Password Changed  ");
                }else {
                          obj.put("status", 2);
                          obj.put("msg", "Error Updating Password  ");
                }
            }
            else {
                System.out.println("Password do not Match"); 
                         obj.put("status", 2);
                          obj.put("msg", "Error Occured");
                          obj.put("Exception", "Password do not Match");
            }
            
     }
     catch(Exception ex){
         System.out.println("Password Errror "); 
         ex.printStackTrace();
                          obj.put("status", 2);
                          obj.put("msg", "Error Occured");
                          obj.put("Exception", ex.toString());
     }
       return obj.toString();
   }
}
