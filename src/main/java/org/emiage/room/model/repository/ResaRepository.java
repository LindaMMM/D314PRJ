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
import jakarta.ws.rs.PathParam;
import java.lang.invoke.MethodHandles;
import java.util.Date;
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
    
    @Resource
    private UserTransaction utx;
    

    public Resa create(Resa resa) throws SystemException, SystemException {
        
        logger.log(Level.INFO, "Creating Resa {0}", resa.toString());
         try {
            
            utx.begin();
            em.persist(resa);
            utx.commit();
            logger.log(Level.INFO, "Creating Resa {0}", resa.toString());
        }
        catch (HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException | IllegalStateException | SecurityException e) {
            logger.log(Level.SEVERE, "Error Creating Resa {0}", e); 
            utx.rollback();
             
        }
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

     public boolean deletebyroom(Long id) {
        logger.log(Level.INFO, "Deleting resa by idRoom {0}", id);
        try {
            utx.begin();
            em.createQuery("DELETE FROM Resa r where r.id_room = :id", Resa.class).setParameter("id", id).executeUpdate();
            utx.commit();
            return true;
        }
        catch (RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException | SystemException | NotSupportedException ex) {
          Logger.getLogger(ResaRepository.class.getName()).log(Level.SEVERE, null, ex);
          try {
              utx.rollback();
          } catch (IllegalStateException | SecurityException | SystemException ex1) {
              Logger.getLogger(ResaRepository.class.getName()).log(Level.SEVERE, null, ex1);
          }
        
        }
      return true;  
     }
     
    public boolean delete(Long id) {
        logger.log(Level.INFO, "Deleting resa by id {0}", id);
        var resa = findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid resa Id:" + id));
        try {
            utx.begin();
             em.createQuery("DELETE FROM Resa r where r.id_resa = :id", Resa.class).setParameter("id", id).executeUpdate();
            utx.commit();
            return true;
        } catch (RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException | SystemException | NotSupportedException ex) {
            Logger.getLogger(ResaRepository.class.getName()).log(Level.SEVERE, null, ex);
            try {
                utx.rollback();
            } catch (IllegalStateException | SecurityException | SystemException ex1) {
                Logger.getLogger(ResaRepository.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return false;
        
    }

    public Resa update(Resa resa) {
        logger.log(Level.INFO, "Updating resa {0}", resa.getSubject());
        try {
            utx.begin();
            resa = em.merge(resa);
            utx.commit();
        } catch (RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException | SystemException | NotSupportedException ex) {
            Logger.getLogger(ResaRepository.class.getName()).log(Level.SEVERE, null, ex);
            try {
                utx.rollback();
            } catch (IllegalStateException | SecurityException | SystemException ex1) {
                Logger.getLogger(ResaRepository.class.getName()).log(Level.SEVERE, null, ex1);
            }
            return null;
        }
        return resa;
    }
    
    public List<Resa> findbyuser(String name){
        logger.info("Getting all resa");
        return em.createQuery("SELECT r FROM Resa r, User u where u.id_user=r.id_user and u.username=:valname  ", Resa.class)
                .setParameter("valname", name)
                .getResultList();
    }
    
    public List<Resa> findbyRoomId(Long idroom, Date dtstart){
        logger.info("Getting all resa");
        return em.createQuery("SELECT r FROM Resa r where r.id_room = :id and r.dateStart>:dtstart ", Resa.class)
                .setParameter("id", idroom)
                .setParameter("dtstart", dtstart)
                .getResultList();
    }
    
    public boolean availableRoom(Resa resaOrigin){
       logger.info("Recherche de la disponibilité d'une salle");
       
       List<Resa> lst = this.availableRoomList(resaOrigin.getIdRoom(), resaOrigin.getDateStart(), resaOrigin.getDateEnd());
       
       if(lst.isEmpty())
           return true;
       
       if((lst.contains(resaOrigin)) && (lst.size() == 1 ))
       {
           return true;
       }
       return false;
    }
    
    public boolean availableRoom(Long idroom, Date dtstart, Date dtend ){
        List<Resa> lst = this.availableRoomList(idroom, dtstart, dtend);
       
        if(lst.isEmpty())
           return true; 
       
       return false;
    }
    
    private List<Resa> availableRoomList(Long idroom, Date dtstart, Date dtend ) {
         List<Resa> lst = em.createQuery("SELECT r FROM Resa r where r.id_room=:id_room and (r.dateCreate between :dtstart and :dtend or r.dateEnd between :dtstart and :dtend )", Resa.class)
                .setParameter("id_room", idroom)
                .setParameter("dtstart", dtstart)
                .setParameter("dtend", dtend)
                .getResultList();
         return lst;
    }
    
  
}
