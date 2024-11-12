/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.emiage.room.model.DTO;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author linda
 */


public class DTOAvailable {
    private Long idroom;
    private Set<DTOAvailableDate> lstdate = new HashSet<>();
    
    public Long getIdroom(){
        return idroom;
    }
    public Set<DTOAvailableDate> getlstDate(){
        return lstdate;
    }
    
    public void setIdroom(Long id){
        this.idroom=id;
    }
    public void setlstDate(Set<DTOAvailableDate> lst){
        this.lstdate = lst;
    }
    
    @Override
    public String toString(){
        return "DTOAvailable ["+idroom+"]";
    }
}
