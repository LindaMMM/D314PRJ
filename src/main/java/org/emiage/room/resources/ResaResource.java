/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.emiage.room.resources;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.SystemException;
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
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.emiage.room.model.DTO.DTOReturn;
import org.emiage.room.model.entity.Resa;
import org.emiage.room.model.entity.Room;
import org.emiage.room.model.repository.ResaRepository;
import org.emiage.room.model.repository.RoomRepository;
import org.emiage.room.security.entity.User;
import org.emiage.room.security.jwtfilter.TokenAuthenticated;
import org.emiage.room.security.repository.UserRepository;

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
    
    @Inject
    private UserRepository userRepository;
    
    @Inject
    private RoomRepository roomRepository;
    
    
    @GET
    @Path("myresa")
    @Produces("application/json")
    @TokenAuthenticated
    public List<Resa> findMeResa() {
        logger.info("Getting all my resas");
        return resaRepository.findbyuser(securityctx.getUserPrincipal().getName());
    }
    
    
    @GET
    @Path("{id}")
    @Produces("application/json")
    @TokenAuthenticated
    public Resa findResa(@PathParam("id") Long id) {
        logger.log(Level.INFO, "Getting Resa by id {0}", id);
        return resaRepository.findById(id)
            .orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
    }
    
    @GET
    @Produces("application/json")
    @TokenAuthenticated
    public List<Resa> findAll() {
        logger.info("Getting all resa");
        return resaRepository.findAll();
    }
    
    @POST
    @TokenAuthenticated
    @Consumes("application/json")
    @Produces("application/json")
    public Response create(Resa resa){
        logger.log(Level.INFO, "Creating resa {0}", resa.getSubject());
        try{
            /* Maj de l'utilisateur de création*/
            resa.setUserCreate(securityctx.getUserPrincipal().getName());
            resa.setUserUpdate(securityctx.getUserPrincipal().getName());
            // Recherche du user
            User user = userRepository.findByLoging(securityctx.getUserPrincipal().getName());
            // Recherche de la salle
            var room = roomRepository.findById(resa.getIdRoom());
            if (room.isPresent())
            {
                resa.setIdUser(user.getId());
                logger.log(Level.INFO, "Ready resa {0}", resa.toString());
                resaRepository.create(resa);
                return Response
                   .ok()
                   .entity(new DTOReturn(0, "Resrvation crée"))
                   .build();
            }
            return Response
                   .ok()
                   .entity(new DTOReturn(-2, "La salle n'est pas connue"))
                   .build();
            
        }catch (PersistenceException ex){
            logger.log(Level.INFO, "Error creating resa {0}", resa.getSubject());
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        } catch (SystemException ex) {
             Logger.getLogger(ResaResource.class.getName()).log(Level.SEVERE, null, ex);
         }
        
        return Response
                   .ok()
                   .entity(new DTOReturn(-1, "Erreur dans la création de la réservation"))
                   .build();
    }

    @DELETE
    @Path("{id}")
    @TokenAuthenticated
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
    @TokenAuthenticated
    public Resa update(Resa resa) throws SystemException {
        logger.log(Level.INFO, "Updating resa {0}", resa.getSubject());
        try{
            /* Maj de l'utilisateur de création*/
            resa.setUserUpdate(securityctx.getUserPrincipal().getName());
            return resaRepository.create(resa);
        }catch (PersistenceException ex){
            logger.log(Level.INFO, "Error updating resa {0}", resa.getSubject());
            logger.log(Level.INFO, "Error updating resa {0}", ex.getStackTrace());
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
}
