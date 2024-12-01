/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.emiage.room.security.repository;

import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.HeuristicMixedException;
import jakarta.transaction.HeuristicRollbackException;
import jakarta.transaction.NotSupportedException;
import jakarta.transaction.RollbackException;
import jakarta.transaction.SystemException;
import jakarta.transaction.UserTransaction;
import java.util.List;
import org.emiage.room.security.entity.User;

/**
 *
 * @author linda
 */
public class UserRepository {
    @PersistenceContext
    EntityManager em;
    
    @Resource
    private UserTransaction utx;

    public List getAllUsers() {
        return em.createNamedQuery("User.findAll", User.class).getResultList();
    }

    public User findByLoging(String name) {
       List<User> results =em.createQuery("SELECT t FROM User t where t.username = :value")
                        .setParameter("value", name).getResultList();
       if (!results.isEmpty())
       {
           return results.getFirst();
       }
       
       return null;
    }
    public User findById(Long id) {
        return em.find(User.class, id);
    }

    public User create(User user) throws NotSupportedException, SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
        utx.begin();
        em.persist(user);
        utx.commit();
        return user;
    }

    public void update(User user) {
        em.merge(user);
    }

    public void delete(User user) {
        if (!em.contains(user)) {
            user = em.merge(user);
        }

        em.remove(user);
    }
}
