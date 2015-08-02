/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wizglobal.service;

import com.wizglobal.Controller.MemberpassJpaController;
import com.wizglobal.entities.Memberpass;
import java.util.List;


public class StaffService {
  MemberpassJpaController membercontroller;
  
    public List<Memberpass> onlineUsers(){
        membercontroller= new MemberpassJpaController();
     return   membercontroller.findMemberpassEntities();
        
    }
}
