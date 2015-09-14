/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wizglobal.service;

import com.wizglobal.Controller.NavsJpaController;
import com.wizglobal.Controller.SecuritiesJpaController;
import com.wizglobal.Controller.TransJpaController;
import com.wizglobal.entities.Securities;
import com.wizglobal.entities.Trans;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
/**
 *
 * @author nhif
 */
public class TransactionService {
    TransJpaController transJpaController;
    NavsJpaController navsJpaController;
    public String listaccounttransactions(String accountnumber,String securitycode){
     JSONObject respObj = new JSONObject();
     TransactionService ts= new TransactionService();
      Double nav_amount;  
          Securities sec=ts.checkProducttype(securitycode);
             System.out.println("Fund Type is " +sec.getFundtype());
          
          Object[] navs = ts.querryNav(securitycode);
             if (navs[1].equals("")){
                 //handle when nav is null
                 nav_amount=0.0;
             }else {
                     String nav_date=navs[0] +"" ;
                     nav_amount  =(Double)navs[1] ;
             }
       
          //determine if Unitised or Runningbal
             
           
          
            if (sec.getFundtype().equals("Admin Fee")){
               if (nav_amount !=0.0){
                    
                    
                 //calculate unitized 
                    
                   respObj = ts.unitized(accountnumber, nav_amount);
                   respObj.put("statementtype","unitized");
                }
               else {
                  
                   
               }
            }
            if (sec.getFundtype().equals("Rate Fee")){
                 if (nav_amount !=0.0){
                    
                   
                 //calculate interest based  
                   respObj = ts.interestbased(accountnumber, nav_amount);
                   respObj.put("statementtype","interest"); 
                }
               else {
                 //calculate ingterest based
                     
               }
            }
          
          
     return respObj.toString();
    }
    
    private Securities checkProducttype(String securityCode){
        SecuritiesJpaController  securitiesJpaController = new SecuritiesJpaController();
        Securities sec=null ;
        try {
          sec = securitiesJpaController.securityDetails(securityCode);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return sec;
    }
    
    private Object[] querryNav(String securitycode){
        Object[] result=null; 
        navsJpaController = new NavsJpaController();
        try {
          result = (Object[])navsJpaController.querynav(securitycode);
                      
       }catch(Exception ex){
           ex.printStackTrace();
           result[0]="";
           result[1]="";
       }     
        return result;
    }
    
    private JSONObject interestbased(String accountnumber,Double nav_amount){
        
       Double Tpurchaseamount=0.00;
       Double Tinterestamount=0.00;
       Double Twithdrawalamount=0.00;
       Double  running_bal=0.00;
       Double Twithholding_tax=0.00;
       
       Double purchaseamount=0.00,interestamount=0.00,withdrawalamount=0.00;
        Double withholding_tax=0.00;
          transJpaController = new  TransJpaController();
        List<Trans> transactions=null;
        JSONObject obj;
        JSONArray arr = new JSONArray();
        JSONObject respObj = new JSONObject();
         JSONObject summary = new JSONObject();
        
             try {
                 transactions= transJpaController.ListaccountTransactions(accountnumber); 
                for (Trans tran : transactions) {
                        obj = new JSONObject();
                        
                       // System.out.println("TransType( " + tran.getTransType());
                       	if (tran.getTransType().equals("PURCHASE")){
                        
                                obj.put("tranid", tran.getTransId());
                                obj.put("trandate", tran.getTransDate());
                                obj.put("trantype", tran.getTransType());
                                
                                purchaseamount=tran.getAmount();
                                   obj.put("purchaseamount", purchaseamount);
                                   
                                   System.out.println("Purchase Amount " + purchaseamount);
                                interestamount=0.00;
                                   obj.put("interestamount",interestamount);
                                withdrawalamount=0.00;
                                  obj.put("withdrawalamount",withdrawalamount);
                                  
                                
                                withholding_tax=tran.getTaxamt();
                                  if (withholding_tax==null){
                                      withholding_tax=0.00; 
                                  }
                                 System.out.println("With tAX " + withholding_tax);
                                    System.out.println("Twithholding_tax" + withholding_tax);
                                    
                                  obj.put("withholding_tax",withholding_tax);
                                Tpurchaseamount=Tpurchaseamount + purchaseamount;
                                Twithholding_tax=Twithholding_tax + withholding_tax;
                            arr.put(obj);    
                        }
                        else if (tran.getTransType().equals("WITHDRAWAL")){
                             
                                obj.put("tranid", tran.getTransId());
                                obj.put("trandate", tran.getTransDate());
                                obj.put("trantype", tran.getTransType());
                                
                                 purchaseamount=0.0;
                                   obj.put("purchaseamount", purchaseamount);
                                interestamount=0.0;
                                   obj.put("interestamount",interestamount);
                                   withdrawalamount=tran.getAmount();
                                  obj.put("withdrawalamount",withdrawalamount);
                                  
                                   System.out.println("withdrawalamount " + withdrawalamount);
                                   
                                withholding_tax=tran.getTaxamt();
                                  obj.put("withholding_tax",withholding_tax);
                                  Twithdrawalamount=Twithdrawalamount+withdrawalamount;
                                  Twithholding_tax=Twithholding_tax+withholding_tax;
                                arr.put(obj);   
                        }
                        else if (tran.getTransType().equals("INTEREST")){
                        
                                obj.put("tranid", tran.getTransId());
                                obj.put("trandate", tran.getTransDate());
                                obj.put("trantype", tran.getTransType());
                                purchaseamount=0.0;
                                   obj.put("purchaseamount", purchaseamount);
                                    interestamount=tran.getAmount();
                                   obj.put("interestamount",interestamount);
                                   
                                   System.out.println("interestamount " + interestamount);
                                   withdrawalamount=0.0;
                                  obj.put("withdrawalamount",withdrawalamount);
                                   withholding_tax=tran.getTaxamt();
                                  obj.put("withholding_tax",withholding_tax);
                                
                                Tinterestamount=Tinterestamount+interestamount;
                                Twithholding_tax=Twithholding_tax+withholding_tax;
                        }
                        
                    running_bal=(Tpurchaseamount+Tinterestamount)-Twithdrawalamount;
                    obj.put("running_bal",running_bal);
                    arr.put(obj);
                }
                
                 respObj.put("transaction",arr);
                 summary.put("Tpurchaseamount", Tpurchaseamount);
                 summary.put("Twithdrawalamount" ,Twithdrawalamount);
                 summary.put("Tinterestamount",Tinterestamount);
                 summary.put("Trunning_bal",running_bal);
                 summary.put("Twithholding_tax",Twithholding_tax);
               respObj.put("summary",summary);
                
             }
             catch(Exception ex){
                     ex.printStackTrace();
                     }
        
       return respObj; 
    }
    
   private JSONObject unitized(String accountnumber,Double nav_amount){  
       
       System.out.println("Calaculating unitized ...");
        transJpaController = new  TransJpaController();
        List<Trans> transactions=null;
        JSONObject obj;
          JSONObject respObj = new JSONObject(); 
           JSONObject summ = new JSONObject();
           JSONArray arr = new JSONArray();
           Double totalpurchase,purchaseprice,purchasecost,saleprice,salesamount,purchasetotals=0.0;
           Double unitpurchased,unitssold;
           Double salestotals=0.0;
            Double balance_units=0.0;
            Double market_value;
           Double tpurchaseunit=0.0;
           Double Tsoldunits=0.0; 
           Double tpurchasecost=0.00;
          Double  TSalecost=0.00;
         try {
      transactions= transJpaController.ListaccountTransactions(accountnumber); 
       for (Trans tran : transactions) {
                  obj = new JSONObject();        
			if (tran.getTransType().equals("PURCHASE")){
                         
                                obj.put("tranid", tran.getTransId());
                                obj.put("trandate", tran.getTransDate());
                                obj.put("navamount", nav_amount);
                                obj.put("trantype", tran.getTransType());
                            unitpurchased=Double.parseDouble(tran.getNoofshares());
                              obj.put("unitspurchased", unitpurchased);
                            purchaseprice= tran.getPrice();
                              obj.put("purchaseprice", purchaseprice);
                            purchasecost=tran.getAmount();
                              obj.put("purchasecost", purchasecost);
                            unitssold=0.0;
                              obj.put("unitssold", unitssold);
                            saleprice=0.0;
                              obj.put("saleprice", saleprice);
                            salesamount=0.0;
                              obj.put("salesamount", salesamount);
                            tpurchasecost= tpurchasecost+ purchasecost;
                            
                            tpurchaseunit=tpurchaseunit + unitpurchased;
                            purchasetotals=purchasetotals+purchasecost;
                            
                               balance_units =tpurchaseunit + Tsoldunits;     
                               obj.put("balance_units", balance_units);
                               arr.put(obj);
                             
                        }else if (tran.getTransType().equals("WITHDRAWAL")){
                            obj = new JSONObject();  
                             obj.put("tranid", tran.getTransId());
                             obj.put("trandate", tran.getTransDate());
                             obj.put("navamount", nav_amount);
                             obj.put("trantype", tran.getTransType());
                             unitpurchased=0.0;
                              obj.put("unitspurchased", unitpurchased);
                            purchaseprice=0.0;
                              obj.put("purchaseprice", purchaseprice);
                            purchasecost=0.0;
                              obj.put("purchasecost", purchasecost);
                            unitssold=Double.parseDouble(tran.getNoofshares());
                              obj.put("unitssold", unitssold);
                          
                            saleprice=tran.getNav();
                                obj.put("saleprice", saleprice);
                            salesamount=tran.getAmount();
                                obj.put("salesamount", salesamount);
                                
                            Tsoldunits=Tsoldunits+unitssold;
                            salestotals= salestotals+salesamount;
                            TSalecost=TSalecost+salesamount;
                               balance_units =tpurchaseunit + Tsoldunits;     
                              obj.put("balance_units", balance_units); 
                             arr.put(obj);
                        }
                     
                     
                      
                      
                        
		}
      
    
                    
                     market_value =balance_units * nav_amount;   
            summ.put("market_value", market_value);
       //calculate market value
          summ.put("tpurchaseunit", tpurchaseunit);
            summ.put("Tsoldunits", Tsoldunits);
           summ.put("Tbalance_units", balance_units);
           summ.put("Tpurchasecost",tpurchasecost); 
           summ.put("TSalecost",TSalecost);
           summ.put("navamount", nav_amount);
             respObj.put("transaction",arr);
             respObj.put("summary", summ);
      
      
      
      
         }catch(Exception ex){
             ex.printStackTrace();
         }
       return respObj;
   }
}
