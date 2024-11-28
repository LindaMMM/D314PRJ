/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.emiage.room.resources;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.SystemException;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.MatrixParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.emiage.room.model.dto.DTOAvailable;
import org.emiage.room.model.dto.DTOAvailableDate;
import org.emiage.room.model.dto.DTOReturn;
import org.emiage.room.model.entity.Resa;
import org.emiage.room.model.service.ResaService;
import org.emiage.room.model.service.RoomException;
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
    ResaService resaService;
    
    @POST
    @TokenAuthenticated
    @Consumes("application/json")
    @Produces("application/json")
    public Response create(Resa resa){
        logger.log(Level.INFO, "Creating resa {0}", resa.getSubject());
        String message="la réservation est crée";
        try{
            
            resa = resaService.saveResa(resa, securityctx.getUserPrincipal().getName());
            return Response
                   .ok()
                   .entity(new DTOReturn(0, message, resa))
                   .build();
            
        }catch (PersistenceException ex){
            logger.log(Level.INFO, "Error creating resa {0}", resa.getSubject());
            message = ex.getMessage();
            
        } catch (RoomException | SystemException ex) { 
             Logger.getLogger(ResaResource.class.getName()).log(Level.SEVERE, null, ex);
             message = ex.getMessage();
         } 
         return Response
                   .ok()
                   .entity(new DTOReturn(-1, message))
                   .build();
    }

    @DELETE
    @Path("{id}")
    @TokenAuthenticated
    public Response delete(@PathParam("id") Long id) {
        logger.log(Level.INFO, "Deleting resa by id {0}", id);
        String message="la réservation est supprimée";
        try{
            if (resaService.deleteResa(id))
            return Response
                   .ok()
                   .entity(new DTOReturn(0, message))
                   .build();
        }catch (IllegalArgumentException e){
            logger.log(Level.INFO, "Error deleting resa by id {0}", id);
            message = e.getMessage();
        }
        return Response
                   .ok()
                   .entity(new DTOReturn(-1, "La supression de la réservation n'a pas pu être fait." + message))
                   .build();
    }


    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    @TokenAuthenticated
    public Response update(Resa resa){
        logger.log(Level.INFO, "Updating resa {0}", resa.getSubject());
        String message="la réservation est mise à jour";
        try{
            /* Maj de l'utilisateur de création*/
            return Response
                   .ok()
                   .entity(new DTOReturn(0, message, resaService.saveResa(resa, securityctx.getUserPrincipal().getName())))
                   .build();
            
        }catch (PersistenceException ex){
            logger.log(Level.INFO, "Error updating resa {0}", resa.getSubject());
            logger.log(Level.INFO, "Error updating resa {0}", ex.getStackTrace());
            message = ex.getMessage();
        } catch (RoomException | SystemException ex) {
             Logger.getLogger(ResaResource.class.getName()).log(Level.SEVERE, null, ex);
             message = ex.getMessage();
         }
         return Response
                   .ok()
                   .entity(new DTOReturn(-1, "La mise à jour de la réservation n'est pas possible." + message))
                   .build();
    }
    
    @GET
    @Path("{id}")
    @Produces("application/json")
    @TokenAuthenticated
    public Resa findResa(@PathParam("id") Long id) {
        logger.log(Level.INFO, "Getting Resa by id {0}", id);
        return resaService.findResaById(id);
    }
    
    @GET
    @Produces("application/json")
    @TokenAuthenticated
    public List<Resa> findAll() {
        logger.info("Getting all resa");
        return resaService.findAll();
    }
    
    @GET
    @Path("me")
    @Produces("application/json")
    @Consumes("application/json")
    @TokenAuthenticated
    public List<Resa> findMeResa() {
        logger.info("Getting all my resas");
        return resaService.getAllMyResa(securityctx.getUserPrincipal().getName());
    }
    
    @POST
    @Path("available")
    @Produces("application/json")
    @Consumes("application/json")
    @TokenAuthenticated
    public DTOAvailable searchAvailable(DTOAvailable dto ){
        logger.log(Level.INFO, "Recherche des disponibiltés d'une salle - {0}".formatted(dto.getIdroom()), dto.getIdroom());
        for (DTOAvailableDate i : dto.getLstDate()) {
             logger.log(Level.INFO, "Recherche des disponibiltés d'une salle{0}", i.toString());
        }
        return resaService.searchAvailable(dto);
        
    }
        
    @GET
    @Path("busyroom/{id}")
    @Consumes("application/json")
    @Produces("application/json")
    @TokenAuthenticated
    public List<Resa> searchBusyByRoomId(@PathParam("id") Long idroom){
        logger.log(Level.INFO, "Recherche des resa d'une salle", idroom);
        return resaService.findbyRoomId(idroom);
    }
}
