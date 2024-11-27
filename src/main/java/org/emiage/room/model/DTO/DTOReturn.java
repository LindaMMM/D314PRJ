/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.emiage.room.model.DTO;

/**
 *
 * @author linda
 */
public class DTOReturn {
    protected int code;
    protected String message;
    
    public DTOReturn(int code, String message){
        this.code = code;
        this.message = message;
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
