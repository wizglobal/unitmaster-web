/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wizglobal.service;

import com.wizglobal.Controller.AccountsJpaController;
import com.wizglobal.Controller.AgentsJpaController;
import com.wizglobal.Helpers.AgentAccountList;
import com.wizglobal.entities.Agents;
import com.wizglobal.helpers.tokendetails;
import com.wizglobal.security.LoginToken;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nhif
 */
public class AgentService {
  AgentsJpaController agentsJpaController  ;
  AccountsJpaController Accctrl;
    LoginToken loginToken;
    tokendetails tkn;
  public Agents getAgent(String token){
        agentsJpaController= new AgentsJpaController();
          loginToken= new LoginToken();     
         tkn=loginToken.parseJWT(token);
      
     return  agentsJpaController.findAgentbyagentnumber(tkn.getMemberno());
  }
  public List<AgentAccountList>   getCustomerList(String token){
        Accctrl= new AccountsJpaController();
          loginToken= new LoginToken();     
         tkn=loginToken.parseJWT(token);
        
         System.out.println("The Agent member Number is " +tkn.getMemberno());
         
          List li =new ArrayList<>();
         li=Accctrl.findAgentCustomerList(tkn.getMemberno());
        
     return  li;
  }
}
