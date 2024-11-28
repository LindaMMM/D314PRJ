/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.emiage.room.model.dto;

/**
 *
 * @author linda
 */
public class DTOLogin {
    
    private String login;
    private String password;
    
    public String getLogin(){
        return login;
    }
    public String getPassword(){
        return password;
    }
    
    public void setLogin(String login){
        this.login=login;
    }
    public void setPassword(String pwd){
        this.password=pwd;
    }
    
    @Override
    public String toString(){
        return "DTOLogin ["+login+"]";
    }
}
