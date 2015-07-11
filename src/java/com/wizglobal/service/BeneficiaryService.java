/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wizglobal.service;

import com.wizglobal.Controller.BeneficiariesJpaController;
import com.wizglobal.entities.Beneficiaries;
import com.wizglobal.helpers.tokendetails;
import com.wizglobal.security.LoginToken;
import java.util.List;

/**
 *
 * @author nhif
 */
public class BeneficiaryService {
    BeneficiariesJpaController beneficiaryCtrl;
    LoginToken loginToken;
    tokendetails tkn;
    public List<Beneficiaries> getmemberBeneficiaries(String token){
        beneficiaryCtrl = new BeneficiariesJpaController();
        loginToken= new LoginToken();     
        tkn=loginToken.parseJWT(token);
        
        
        return  beneficiaryCtrl.findBeneficiariesByMemberNo(tkn.getUsername());
    } 
}
