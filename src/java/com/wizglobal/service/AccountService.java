/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wizglobal.service;

import com.wizglobal.Controller.AccountsJpaController;
import com.wizglobal.Helpers.CustomeragentsAccounts;
import com.wizglobal.Service.exceptions.RollbackFailureException;
import com.wizglobal.entities.Accounts;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
public class AccountService {
   
    AccountsJpaController acctCtrl;
    CustomeragentsAccounts ca;
    private ArrayList<CustomeragentsAccounts> CAAccounts;
    
    
    public String  listAccount(){
      
        JSONObject obj = new JSONObject();
        acctCtrl = new  AccountsJpaController();
        
                List<Accounts> AList=acctCtrl.findAccountsEntities();
            obj.put("Accountlist", AList);    
    return obj.toString();            
    }
    
       public List<Accounts>  listAccount1(){
      
          acctCtrl = new  AccountsJpaController();
          return acctCtrl.findAccountsEntities();            
    }
       public List<Accounts>  getMemberAccounts(String memberno){
      
          acctCtrl = new  AccountsJpaController();
          return acctCtrl.findAccountsbyMembernumber(memberno);
    }
       
    
    public String getacct(String acctnum){
        Accounts acct =null;
        acctCtrl = new  AccountsJpaController();
       JSONObject obj = new JSONObject();  
        acct=acctCtrl.findAccounts(acctnum);
          obj.put("Acctnum",acct.getAccountNo());
        return obj.toString();
        
    }
    
    public Accounts getacct1(String acctnum){
        acctCtrl = new  AccountsJpaController();
        return acctCtrl.findAccounts(acctnum);
        
    }
    
    public List<CustomeragentsAccounts> findCustomerAgents(String membernumber){
        acctCtrl = new  AccountsJpaController();
        
        // ca = new  CustomeragentsAccounts() ;
       //  ca.setACCOUNT_NO(membernumber);
        return acctCtrl.findCustomerAgents(membernumber);
        
    }
    
    public String createAcct(Accounts acct){
        acctCtrl = new  AccountsJpaController();
        try {
            acctCtrl.create(acct);
        } catch (Exception ex) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return "";
    }
    
    public String updateAcct(Accounts acct){
        acctCtrl = new  AccountsJpaController();
        
        try {
            acctCtrl.edit(acct);
        } catch (Exception ex) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
}
