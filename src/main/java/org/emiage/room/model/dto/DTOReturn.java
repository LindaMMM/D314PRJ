/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.emiage.room.model.dto;

import java.util.Optional;

/**
 *
 * @author linda
 */
public class DTOReturn {
    protected int code;
    protected String message;
    
    protected Object data = null;
    
    public DTOReturn(int code, String message){
        this.code = code;
        this.message = message;
    }
    
    public DTOReturn(int code, String message, Object object){
        this.code = code;
        this.message = message;
        this.data = object;
    }
    
    protected Object getdata(){
        return data;
    }
    
    protected void setdata(Object data){
        this.data = data;
    }
    
    public int getCode(){
        return code;
    }
    public  String getMessage(){
        return message;
    }
    public void setCode(int code){
        this.code=code;
    }
    public void setMessage(String msg){
        this.message = msg;
    }
    
}
