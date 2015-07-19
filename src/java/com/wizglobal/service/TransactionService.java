/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wizglobal.service;

import com.wizglobal.Controller.TransJpaController;
import com.wizglobal.entities.Trans;
import java.util.List;
/**
 *
 * @author nhif
 */
public class TransactionService {
    TransJpaController transJpaController;
    
    public List<Trans> listaccounttransactions(String accountnumber){
        
        transJpaController = new  TransJpaController();
        System.out.println("Account Number " + accountnumber);
       return transJpaController.ListaccountTransactions(accountnumber);
    }
}
