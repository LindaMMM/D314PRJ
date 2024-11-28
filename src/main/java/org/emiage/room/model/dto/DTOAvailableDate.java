/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.emiage.room.model.dto;

import java.util.Date;

/**
 *
 * @author linda
 */
public class DTOAvailableDate {
    private Date datestart;
    private Date dateEnd;
    private boolean available;
    
    public Date getDateStart(){
        return datestart;
    }
    
    public Date getDateEnd(){
        return dateEnd;
    }
    
    public boolean getAvailable(){
        return available;
    }
    
    public void setDateStart(Date dtstart){
        this.datestart =  dtstart;
    }
    
    public void setDateEnd(Date dtEnd){
        this.dateEnd =  dtEnd;
    }
    
    public void setAvailable(boolean available){
        this.available = available;
    }
}
