/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wizglobal.service;

import com.wizglobal.Controller.NavsJpaController;
import com.wizglobal.Controller.TransJpaController;
import com.wizglobal.entities.Trans;
import java.util.List;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
/**
 *
 * @author nhif
 */
public class TransactionService {
    TransJpaController transJpaController;
    NavsJpaController navsJpaController;
    public String listaccounttransactions(String accountnumber,String securitycode){
      
        transJpaController = new  TransJpaController();
        navsJpaController = new NavsJpaController();
          JSONObject obj = new JSONObject(); 
          
         List<Trans> transactions=null;
         Double totalpurchase,purchaseprice,purchasecost,saleprice,salesamount,purchasetotals=0.0;
         Double unitpurchased,unitssold;
        Double salestotals=0.0;
        Double balance_units=0.0;
        Double market_value;
       Double tpurchaseunit=0.0;
       Double Tsoldunits=0.0; 
      
       try {
       
      ObjectMapper mapper = new ObjectMapper();
     
      transactions= transJpaController.ListaccountTransactions(accountnumber);
        String tr=mapper.writeValueAsString(transactions);
    
         obj.put("Transactions", tr);
      
         
       for (Trans tran : transactions) {
                 
                      
			if (tran.getTransType().equals("PURCHASE")){
                            unitpurchased=Double.parseDouble(tran.getNoofshares());
                            System.out.println("unitpurchased " + unitpurchased);
                            purchaseprice= tran.getPrice();
                            purchasecost=tran.getAmount();
                            unitssold=0.0;
                            saleprice=0.0;
                            salesamount=0.0;
                            tpurchaseunit=tpurchaseunit + unitpurchased;
                            purchasetotals=purchasetotals+purchasecost;
                        }else if (tran.getTransType().equals("WITHDRAWAL")){
                            unitpurchased=0.0;
                            purchaseprice=0.0;
                            purchasecost=0.0;
                            unitssold=Double.parseDouble(tran.getNoofshares());
                            System.out.println("unitssold" + unitssold);
                            saleprice=tran.getNav();
                            salesamount=tran.getAmount();
                            Tsoldunits=Tsoldunits+unitssold;
                            salestotals= salestotals+salesamount;
                        }
                        
                    
		}
       balance_units =tpurchaseunit-  Tsoldunits;  
 
       //calculate market value
           obj.put("tpurchaseunit", tpurchaseunit);
            obj.put("Tsoldunits", Tsoldunits);
            obj.put("balance_units", balance_units);
          
       try {
         Object[] result = (Object[])navsJpaController.querynav(securitycode);
            String nav_date=result[0] +"" ;
            Double nav_amount=(Double)result[1] ;
            market_value =balance_units * nav_amount;   
            obj.put("market_value", market_value);
       }catch(Exception ex){
            obj.put("market_value", "N/A");
       }
           
           
               
       }catch(Exception ex){
           ex.printStackTrace();
           obj.put("market_value", "N/A");
       }
       //return transactions;
       return obj.toString();
    }
}
