/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wizglobal.helpers;

/**
 *
 * @author nhif
 */
public class pwdCredential {
      private String oldpwd;
      private String newpwd;

    /**
     * @return the oldpwd
     */
    public String getOldpwd() {
        return oldpwd;
    }

    /**
     * @param oldpwd the oldpwd to set
     */
    public void setOldpwd(String oldpwd) {
        this.oldpwd = oldpwd;
    }

    /**
     * @return the newpwd
     */
    public String getNewpwd() {
        return newpwd;
    }

    /**
     * @param newpwd the newpwd to set
     */
    public void setNewpwd(String newpwd) {
        this.newpwd = newpwd;
    }
}
