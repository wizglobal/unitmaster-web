/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wizglobal.service;

import com.wizglobal.Controller.AgentsJpaController;
import com.wizglobal.entities.Agents;
import com.wizglobal.helpers.tokendetails;
import com.wizglobal.security.LoginToken;

/**
 *
 * @author nhif
 */
public class AgentService {
  AgentsJpaController agentsJpaController  ;
    LoginToken loginToken;
    tokendetails tkn;
  public Agents getAgent(String agentnumber,String token){
        agentsJpaController= new AgentsJpaController();
          loginToken= new LoginToken();     
       //  tkn=loginToken.parseJWT(token);
      
     return  agentsJpaController.findAgentbyagentnumber(agentnumber);
  }
}
