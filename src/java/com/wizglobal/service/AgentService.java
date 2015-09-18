/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wizglobal.service;

import com.wizglobal.Controller.AccountsJpaController;
import com.wizglobal.Controller.AgentsJpaController;
import com.wizglobal.Controller.TransAgentCommJpaController;
import com.wizglobal.Controller.TransAgentJpaController;
import com.wizglobal.Helpers.AgentAccountList;
import com.wizglobal.entities.Agents;
import com.wizglobal.entities.TransAgent;
import com.wizglobal.entities.TransAgentComm;
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
  TransAgentJpaController transAgentJpaController;
  TransAgentCommJpaController transAgentCommJpaController;
  AccountsJpaController Accctrl;
    LoginToken loginToken;
    tokendetails tkn;
  public Agents getAgent(String token){
          agentsJpaController= new AgentsJpaController();
          loginToken= new LoginToken();     
          tkn=loginToken.parseJWT(token);
      
     return  agentsJpaController.findAgentbyagentnumber(tkn.getMemberno());
  }
  
 public Agents getCustomerAgent(String token,String agentid){
       agentsJpaController= new AgentsJpaController();
          loginToken= new LoginToken();     
          tkn=loginToken.parseJWT(token);
      
     return  agentsJpaController.findAgentbyagentnumber(agentid);
 }
  public List<AgentAccountList>   getCustomerList(String token){
          Accctrl= new AccountsJpaController();
          loginToken= new LoginToken();     
         tkn=loginToken.parseJWT(token);   
          List < AgentAccountList> ki =new ArrayList<>();
          List<Object> li=Accctrl.findAgentCustomerList(tkn.getMemberno());     
            for (int lng=0;lng<li.size();lng++){      
              Object[] r =(Object[])li.toArray()[lng];          
                 AgentAccountList l = new AgentAccountList();
              try {
                    l.setAGENT_NO(r[0].toString());
                    l.setAGENT_NAME(r[1].toString());
                    l.setMEMBER_NO(r[2].toString());
                    l.setTITLE(r[3].toString());
                    l.setALLNAMES(r[4].toString());
                    l.setPOST_ADDRESS(r[5].toString());
                    l.setREG_DATE(r[6].toString());
                    l.setTEL_NO(r[7].toString());
                    l.setPHYS_ADDRESS(r[8].toString());
                    l.setTOWN(r[9].toString());
                    l.setSTREET(r[10].toString());
                    l.setGSM_NO(r[11].toString());
                    l.setE_MAIL(r[12].toString());
                    l.setID_NO(r[13].toString());
                    l.setPIN_NO(r[14].toString());                
              }catch (Exception ex){}
                 ki.add(l);
             
            }
         
         return  ki;
  }
  public List<TransAgent> agentTransactions(String token){
       transAgentJpaController= new TransAgentJpaController();
       loginToken= new LoginToken();     
       tkn=loginToken.parseJWT(token);
       return transAgentJpaController.findAgentTrxnByAgentcode(tkn.getRefno());
  }
  public List<TransAgentComm> agentDetailedTransactions(String token,String memberno){
       transAgentCommJpaController= new TransAgentCommJpaController();
       loginToken= new LoginToken();     
       tkn=loginToken.parseJWT(token);
       return transAgentCommJpaController.findAgentDetailedTransactions(memberno);
  }
  
}
