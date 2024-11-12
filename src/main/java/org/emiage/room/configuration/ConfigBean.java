/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.emiage.room.configuration;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

/**
 *
 * @author linda
 */
@Singleton
@Startup
public class ConfigBean {

    @EJB
    private RequestBean request;

    @PostConstruct
    public void createData() {
        
        request.createRole("ADMIN");
        request.createRole("USER");
        
        request.createUser("usr1", "password", "ADMIN" );
        request.createUser("usr2", "password", "USER" );

        request.createRoom("SALLE1",10,"Vidéo projecteur","usr1" );
        request.createRoom("SALLE2",15,"Vidéo projecteur, vision","usr1" );
    }

    @PreDestroy
    public void deleteData() {

    }
}
