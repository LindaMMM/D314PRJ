/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.emiage.room.model.repository;

import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.HeuristicMixedException;
import jakarta.transaction.HeuristicRollbackException;
import jakarta.transaction.NotSupportedException;
import jakarta.transaction.RollbackException;
import jakarta.transaction.SystemException;
import jakarta.transaction.UserTransaction;
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
    
    @Resource
    private UserTransaction utx;
    
    public Room create(Room room) throws SystemException {
        
        try {
            utx.begin();
            em.persist(room);
            utx.commit();
            logger.log(Level.INFO, "Creating room {0}", room.toString());
            return room;
        }
        catch (HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException | IllegalStateException | SecurityException e) {
            logger.log(Level.SEVERE, "Error Creating room {0}", e);
            utx.rollback();
        }
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

    public void delete(Long id) throws SystemException {
        logger.log(Level.INFO, "Deleting room by id {0}", id);
        try {
            utx.begin();
            var room = findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid room Id:" + id));
            em.remove(room);
            utx.commit();
        }
        catch (HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException | IllegalStateException | SecurityException e) {
            logger.log(Level.SEVERE, "Error Updating room {0}", e);
            utx.rollback();
         }
    }

    public Room update(Room room) throws SecurityException, NotSupportedException, SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
         try {
            utx.begin();
            logger.log(Level.INFO, "Updating room {0}", room.getName());
            room = em.merge(room);
            utx.commit();
         } 
         catch ( SecurityException e) {
            logger.log(Level.SEVERE, "Error Updating room {0}", e);
            utx.rollback();
         }
         return room;
    }
}
