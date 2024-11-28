/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.emiage.room.model.dto;

/**
 *
 * @author linda
 */
public class DTOReturnLogin extends DTOReturn {
    protected String token;
    
    public  String getToken(){
        return token;
    }
    
    public void setToken(String msg){
        this.token = msg;
    }
    
    public DTOReturnLogin(int code, String message, String token){
        super(code, message);
        this.token = token;
    }
}
