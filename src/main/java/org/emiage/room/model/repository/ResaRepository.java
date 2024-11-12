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
import org.emiage.room.model.entity.Resa;

/**
 *
 * @author linda
 */
public class ResaRepository {

    private static final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    @PersistenceContext
    private EntityManager em;

    public Resa create(Resa resa) {
        logger.log(Level.INFO, "Creating Resa {0}", resa.getSubject());
        em.persist(resa);
        return resa;
    }

    public List<Resa> findAll() {
        logger.info("Getting all resa");
        return em.createQuery("SELECT r FROM Resa r", Resa.class).getResultList();
    }

    public Optional<Resa> findById(Long id) {
        logger.log(Level.INFO, "Getting resa by id {0}", id);
        return Optional.ofNullable(em.find(Resa.class, id));
    }

    public void delete(Long id) {
        logger.log(Level.INFO, "Deleting resa by id {0}", id);
        var resa = findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid resa Id:" + id));
        em.remove(resa);
    }

    public Resa update(Resa resa) {
        logger.log(Level.INFO, "Updating resa {0}", resa.getSubject());
        return em.merge(resa);
    }

}
