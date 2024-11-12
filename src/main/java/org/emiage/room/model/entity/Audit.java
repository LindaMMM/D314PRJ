/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.emiage.room.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author linda
 */
@MappedSuperclass
public abstract class Audit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "DAT_CRT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreate;

    @Column(name = "USER_CREATE")
    private String userCreate;

    @Column(name = "DAT_UPD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpdate;

    @Column(name = "USER_UPDATE")
    private String userUpdate;

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Date getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Date dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public String getUserCreate() {
        return userCreate;
    }

    public void setUserCreate(String userCreate) {
        this.userCreate = userCreate;
    }

    public String getUserUpdate() {
        return userUpdate;
    }

    public void setUserUpdate(String userUpdate) {
        this.userUpdate = userUpdate;
    }

    @PrePersist
    private void onCreate() {
        dateCreate = new Date();
    }

    @PreUpdate
    private void onUpdate() {
        dateUpdate = new Date();
    }
    
    @Override
    public String toString() {
        
        return "\n Create by " + userCreate + "[ " + dateCreate + "]\n Update by " + userUpdate + "[ " + dateUpdate + "]";
    }
}
