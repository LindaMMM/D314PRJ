/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.emiage.room.resources;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.emiage.room.model.entity.Resa;
import org.emiage.room.model.repository.ResaRepository;
import org.emiage.room.security.jwtfilter.TokenAuthenticated;

/**
 *
 * @author linda
 */
@Path("resa")
@RequestScoped
public class ResaResource {
     private final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    @Inject 
    SecurityContext securityctx;
    
    @Inject
    private ResaRepository resaRepository;
    
    // @TokenAuthenticated
    
    @GET
    @Path("{id}")
    @Produces("application/json")
    public Resa findResa(@PathParam("id") Long id) {
        logger.log(Level.INFO, "Getting Resa by id {0}", id);
        return resaRepository.findById(id)
            .orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
    }
    
    @GET
    @Produces("application/json")
    public List<Resa> findAll() {
        logger.info("Getting all resa");
        return resaRepository.findAll();
    }
    
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Resa create(Resa resa) {
        logger.log(Level.INFO, "Creating resa {0}", resa.getSubject());
        try{
            return resaRepository.create(resa);
        }catch (PersistenceException ex){
            logger.log(Level.INFO, "Error creating resa {0}", resa.getSubject());
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Long id) {
        logger.log(Level.INFO, "Deleting resa by id {0}", id);
        try{
            resaRepository.delete(id);
        }catch (IllegalArgumentException e){
            logger.log(Level.INFO, "Error deleting resa by id {0}", id);
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }


    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Resa update(Resa resa) {
        logger.log(Level.INFO, "Updating resa {0}", resa.getSubject());
        try{
            return resaRepository.create(resa);
        }catch (PersistenceException ex){
            logger.log(Level.INFO, "Error updating resa {0}", resa.getSubject());
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
}
