/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.emiage.room.resources;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.HeuristicMixedException;
import jakarta.transaction.HeuristicRollbackException;
import jakarta.transaction.NotSupportedException;
import jakarta.transaction.RollbackException;
import jakarta.transaction.SystemException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import java.lang.invoke.MethodHandles;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.emiage.room.model.dto.DTOReturn;
import org.emiage.room.model.entity.Resa;
import org.emiage.room.model.service.RoomException;
import org.emiage.room.security.entity.User;
import org.emiage.room.security.jwtfilter.TokenAuthenticated;
import org.emiage.room.security.repository.UserRepository;

/**
 *
 * @author linda
 */
@Path("user")
@RequestScoped
public class UserResource {
    
    private static final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    
    @Inject
    UserRepository userRepository;

    @Inject 
    SecurityContext securityctx;
      
    @GET
    @Produces("application/json")
    @TokenAuthenticated
    public User getuser() {
        logger.log(Level.INFO, "Getting User by name {0}", securityctx.getUserPrincipal().getName());
        try{
            return userRepository.findByLoging(securityctx.getUserPrincipal().getName());
          }catch (PersistenceException ex){
            logger.log(Level.INFO, "Getting User by name {0}", securityctx.getUserPrincipal().getName());
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
     @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response create(User use){
        logger.log(Level.INFO, "Creating User {0}", use.getUsername());
        String message="la réservation est crée";
        try{
            use = userRepository.create(use);
            return Response
                   .ok()
                   .entity(new DTOReturn(0, message, use))
                   .build();
            
        }catch (PersistenceException ex){
            logger.log(Level.INFO, "Error creating resa {0}", use.getUsername());
            message = ex.getMessage();
        }
        catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException ex) {
                Logger.getLogger(UserResource.class.getName()).log(Level.SEVERE, null, ex);
                message = ex.getMessage();
        } 
         return Response
                   .ok()
                   .entity(new DTOReturn(-1, message))
                   .build();
    }

    @Path("list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getlist() {
        return Response.ok().entity(userRepository.getAllUsers()).build();
    }

}
