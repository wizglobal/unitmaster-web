/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wizglobal.service;

import com.wizglobal.Controller.MembersJpaController;
import com.wizglobal.Helpers.customerAccounts;
import com.wizglobal.entities.Members;
import com.wizglobal.helpers.tokendetails;
import com.wizglobal.security.LoginToken;
import java.util.List;


public class MemberService {
    
    MembersJpaController membercontroller;
    LoginToken loginToken;
    tokendetails tkn;
    
    public Members getMemberdetails(String token){
       membercontroller = new MembersJpaController();
       
         loginToken= new LoginToken();     
         tkn=loginToken.parseJWT(token);
       
       return membercontroller.memberDetails(tkn.getUsername());
    }
    public List<customerAccounts>  getMemberAccounts(String token){
       membercontroller = new MembersJpaController();
       customerAccounts ca = new customerAccounts();
                loginToken= new LoginToken();     
                tkn=loginToken.parseJWT(token);       
       return membercontroller.findCustomerAccounts(tkn.getUsername());
    }
}
