/*
 * Copyright (c), Eclipse Foundation, Inc. and its licensors.
 *
 * All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v1.0, which is available at
 * https://www.eclipse.org/org/documents/edl-v10.php
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */
package org.emiage.room.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.ejb.EJBException;
import jakarta.ejb.Stateful;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.emiage.room.model.entity.Room;
import org.emiage.room.security.entity.Role;
import org.emiage.room.security.entity.User;

@Stateful
public class RequestBean {

    @PersistenceContext(unitName = "rooms_jpa")
    private EntityManager em;

    private static final Logger logger = Logger.getLogger("org.emiage.room.configuration.RequestBean");

    public void createRole(
            String name) {
        try {
            Role role = new Role();
            role.setName(name);
            logger.log(Level.INFO, "Created part {0}", new Object[]{role.toString()});
            em.persist(role); 
            logger.log(Level.INFO, "Persisted part {0}", new Object[]{role.toString()});

        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }

    public void createRoom(
            String name, int capacity, String comment, String usercrt) {
        try {
            Room room = new Room();
            room.setName(name);
            room.setCapacity(capacity);
            room.setComment(comment);
            room.setUserCreate(usercrt);
            room.setUserUpdate(usercrt);
            logger.log(Level.INFO, "Created part {0}", new Object[]{room.toString()});
            em.persist(room); 
            logger.log(Level.INFO, "Persisted part {0}", new Object[]{room.toString()});

        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }

    public void createUser(
            String name, String password, String role) {
        try {
            
            Role rl = em.createNamedQuery("Role.findbyName", Role.class).setParameter("value", role).getSingleResult();
            User user = new User();
            user.setUsername(name);
            user.setPassword(password);            
            user.getRoles().add(rl);
            logger.log(Level.INFO, "Created part {0}", new Object[]{user.toString()});
            em.persist(rl);
            em.persist(user); 
            logger.log(Level.INFO, "Persisted part {0}", new Object[]{user.toString()});

        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }
}