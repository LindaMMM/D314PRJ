/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.emiage.room.model.DTO;

import java.util.Date;

/**
 *
 * @author linda
 */
class DTOAvailableDate {
    private Date datestart;
    private Date dateEnd;
    private boolean available;
    
    public Date getDatestart(){
        return datestart;
    }
    
    public Date getdateEnd(){
        return dateEnd;
    }
    
    public boolean getavailable(){
        return available;
    }
    
    public void setDateStart(Date dtstart){
        this.datestart =  dtstart;
    }
    
    public void setdateEnd(Date dtEnd){
        this.dateEnd =  dtEnd;
    }
    
    public void setavailable(boolean available){
        this.available = available;
    }
}
