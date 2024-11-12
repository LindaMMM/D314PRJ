/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.emiage.room.model.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.emiage.room.model.entity.Room;


/**
 *
 * @author linda
 */
public class RoomRepository {
    private static final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    @PersistenceContext
    private EntityManager em;

    public Room create(Room room) {
        logger.log(Level.INFO, "Creating room {0}", room.getName());
        em.persist(room);
        return room;
    }

    public List<Room> findAll() {
        logger.info("Getting all Room");
        return em.createQuery("SELECT r FROM Room r", Room.class).getResultList();
    }

    public Optional<Room> findById(Long id) {
        logger.log(Level.INFO, "Getting room by id {0}", id);
        return Optional.ofNullable(em.find(Room.class, id));
    }

    public void delete(Long id) {
        logger.log(Level.INFO, "Deleting room by id {0}", id);
        var room = findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid room Id:" + id));
        em.remove(room);
    }

    public Room update(Room room) {
        logger.log(Level.INFO, "Updating room {0}", room.getName());
        return em.merge(room);
    }
}
