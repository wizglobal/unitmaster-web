/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wizglobal.service;

import com.wizglobal.Controller.AgentsJpaController;
import com.wizglobal.Controller.IRatesJpaController;
import com.wizglobal.Controller.MemberpassJpaController;
import com.wizglobal.Controller.MembersJpaController;
import com.wizglobal.Controller.NavsJpaController;
import com.wizglobal.Controller.TransJpaController;
import com.wizglobal.Controller.UsersetupJpaController;
import com.wizglobal.ExceptionHandling.usernameExistsException;
import com.wizglobal.entities.Agents;
import com.wizglobal.entities.IRates;
import com.wizglobal.entities.Memberpass;
import com.wizglobal.entities.Members;
import com.wizglobal.entities.Navs;
import com.wizglobal.entities.Trans;
import com.wizglobal.entities.Usersetup;
import com.wizglobal.entities.Userdetails;
import com.wizglobal.helpers.tokendetails;
import com.wizglobal.security.LoginToken;
import java.util.List;
import org.json.JSONObject;


public class StaffService {
  MemberpassJpaController membercontroller;
  MembersJpaController  membersJpaController ;
  AgentsJpaController agentsJpaController;
  UsersetupJpaController usersetupJpaController;
  NavsJpaController navsJpaController;
  TransJpaController     transJpaController;
  IRatesJpaController iRatesJpaController;
 
      LoginToken loginToken;
    tokendetails tkn;
    public List<Memberpass> onlineUsers(String token){
        membercontroller= new MemberpassJpaController();
         loginToken= new LoginToken();     
                tkn=loginToken.parseJWT(token); 
     return   membercontroller.findMemberpassEntities();
        
    }
    public List<Agents> findUnregisteredAgents(String token) {
        agentsJpaController = new AgentsJpaController();
          loginToken= new LoginToken();     
                tkn=loginToken.parseJWT(token); 
        return agentsJpaController.findUnregisteredAgents();
    }
    public List<Usersetup> findUnregisteredStaff(String token) {
        usersetupJpaController = new UsersetupJpaController();
          loginToken= new LoginToken();     
                tkn=loginToken.parseJWT(token); 
        return usersetupJpaController.findUnregisteredStaff();
    }
    
     public List<Members> findUnconfirmedMembers(String token) {
        membersJpaController = new MembersJpaController ();
          loginToken= new LoginToken();     
                tkn=loginToken.parseJWT(token); 
        return membersJpaController.findUnconfirmedMembers(tkn.getUsername());
    }
     
    public List<Navs> findUnconfirmedNav (String token){
        
        navsJpaController = new NavsJpaController();
          loginToken= new LoginToken();     
                tkn=loginToken.parseJWT(token); 
                
        return navsJpaController.findUnconfirmedNav(tkn.getUsername());
     }
    
    public List<Trans> findUnconfirmedTransactions (String token){
        transJpaController = new TransJpaController();
          loginToken= new LoginToken();     
                tkn=loginToken.parseJWT(token); 
        return transJpaController.ListunconfirmedTransactions(tkn.getUsername());
    }
        public List<IRates> findUnconfirmedInterests(String token){
        iRatesJpaController = new IRatesJpaController();
          loginToken= new LoginToken();     
                tkn=loginToken.parseJWT(token); 
        return iRatesJpaController.ListUnconfirmedRates(tkn.getUsername());
    }
    public String RegisterAgent(Userdetails userdetails){
        JSONObject obj = new JSONObject();  
        Memberpass mp = new Memberpass();
          StaffService ss = new StaffService();
           
           try {
                 mp.setCategory("agent");
                 mp.setEMail(userdetails.getEMail());
                 mp.setRefno(userdetails.getRefno());
                 mp.setUsername(userdetails.getUsername());
                 mp.setPasswrd(userdetails.getPasswrd());
                
                 int status =ss.RegisterUser(mp);
                 
                 obj.put("status", status);
                 obj.put("msg", "Agent Created");
               
           }catch (Exception ex){
               obj.put("status", 2);
               obj.put("msg", "Error Occurred");
               obj.put("Exception", "The Username Exists ");
               ex.printStackTrace();
               
               
           }
        
        return obj.toString();
    }
    
    private int RegisterUser (Memberpass memberpass) throws usernameExistsException{
        membercontroller = new MemberpassJpaController();
        try {
            membercontroller.create(memberpass);
            return 1;
        }catch (Exception ex){
            ex.printStackTrace();
            throw new usernameExistsException(" Username Already Exists");
        }
        
    }
}
