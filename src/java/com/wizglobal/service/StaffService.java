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
     public String RegisterUser(String token,Userdetails userdetails) {
         StaffService ss = new StaffService();
         String rt="";
             
            loginToken= new LoginToken();     
                tkn=loginToken.parseJWT(token);
                
            if (userdetails.getCategory().equals("agent")){
              rt= ss.RegisterAgent(userdetails);
            }else if (userdetails.getCategory().equals("staff")){
              rt= ss.RegisterStaff(userdetails);
            }else if (userdetails.getCategory().equals("customer")){
              rt= ss.RegisterCustomer(userdetails);
            }
            
            return rt;
     }  
     
     private String RegisterCustomer(Userdetails userdetails){
           JSONObject obj = new JSONObject();  
        Memberpass mp = new Memberpass();
          StaffService ss = new StaffService();
           
           try {
                 mp.setCategory(userdetails.getCategory());
                 mp.setEMail(userdetails.getEMail());
                 mp.setRefno(userdetails.getRefno());
                 mp.setUsername(userdetails.getUsername());
                 mp.setPasswrd(userdetails.getPasswrd());
                
                 int status =ss.CreateUser(mp);
                 
                 obj.put("status", status);
                 obj.put("msg", "Member Created");
               
           }catch (Exception ex){
               obj.put("status", 2);
               obj.put("msg", "Error Occurred");
               obj.put("Exception", "The Username Exists ");
               ex.printStackTrace();
               
               
           }
        
        return obj.toString();
     }
     
     private String RegisterStaff(Userdetails userdetails){
           JSONObject obj = new JSONObject();  
        Memberpass mp = new Memberpass();
          StaffService ss = new StaffService();
           
           try {
                 mp.setCategory(userdetails.getCategory());
                 mp.setEMail(userdetails.getEMail());
                 mp.setRefno(userdetails.getRefno());
                 mp.setUsername(userdetails.getUsername());
                 mp.setPasswrd(userdetails.getPasswrd());
                
                 int status =ss.CreateUser(mp);
                  if (status==1){
                     usersetupJpaController = new UsersetupJpaController();
                      int k= usersetupJpaController.updateStaff(userdetails.getRefno());
                        if (k==1){
                            obj.put("status", status);
                            obj.put("msg", "Staff Created");
                        }else {
                                obj.put("status", 2);
                                obj.put("msg", "Bussiness Error contact Admin");
                                obj.put("Exception", "Error Updating Staff Details ");
                        }
                  }else {
                        obj.put("status", 2);
                        obj.put("msg", "Bussiness Error contact Admin");
                        obj.put("Exception", "Error Creating Staff Details ");
                  }  
                
               
           }catch (Exception ex){
               obj.put("status", 2);
               obj.put("msg", "Error Occurred");
               obj.put("Exception", "The Username Exists ");
               ex.printStackTrace();
               
               
           }
        
        return obj.toString();
     }
    private String RegisterAgent(Userdetails userdetails){
        JSONObject obj = new JSONObject();  
        Memberpass mp = new Memberpass();
          StaffService ss = new StaffService();
           
           try {
                 mp.setCategory(userdetails.getCategory());
                 mp.setEMail(userdetails.getEMail());
                 mp.setRefno(userdetails.getRefno());
                 mp.setUsername(userdetails.getUsername());
                 mp.setPasswrd(userdetails.getPasswrd());
                
                 int status =ss.CreateUser(mp);
                 if (status==1){
                      agentsJpaController = new AgentsJpaController();
                    int t= agentsJpaController.updateAgent(userdetails.getRefno());
                      if (t==1){
                          obj.put("status", status);
                          obj.put("msg", "Agent Created");
                      }else {
                        obj.put("status", 2);
                        obj.put("msg", "Bussiness Error contact Admin");
                        obj.put("Exception", "Error on Updating Agent details");
                      }
                 }else {
                        obj.put("status", 2);
                        obj.put("msg", "Bussiness Error contact Admin");
                        obj.put("Exception", "Error Creating the Agent Details");
                 }
                 
               
           }catch (Exception ex){
               obj.put("status", 2);
               obj.put("msg", "Error Occurred");
               obj.put("Exception", "The Username Exists ");
               ex.printStackTrace();
               
               
           }
        
        return obj.toString();
    }
    
    private int CreateUser (Memberpass memberpass) throws usernameExistsException{
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
