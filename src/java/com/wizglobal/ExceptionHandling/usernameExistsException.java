/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wizglobal.ExceptionHandling;

/**
 *
 * @author nhif
 */
public class usernameExistsException extends Exception  {
     public usernameExistsException(String message, Throwable cause) {
        super(message, cause);
    }
    public usernameExistsException(String message) {
        super(message);
    }
}
