package org.emiage.room.resources;

import com.auth0.jwt.impl.HeaderClaimsHolder;
import com.auth0.jwt.interfaces.Header;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.HeuristicMixedException;
import jakarta.transaction.HeuristicRollbackException;
import jakarta.transaction.NotSupportedException;
import jakarta.transaction.RollbackException;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.emiage.room.model.dto.DTOReturn;
import org.emiage.room.model.dto.DTOReturnLogin;
import org.emiage.room.model.entity.Room;
import org.emiage.room.model.repository.RoomRepository;
import org.emiage.room.model.service.ResaService;
import org.emiage.room.security.jwtfilter.TokenAuthenticated;


/**
 *
 * @author 
 */
@Path("room")
@RequestScoped
public class RoomResource {
    
    private static final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
   
    @Inject 
    SecurityContext securityctx;
    
    @Inject
    private RoomRepository roomRepository;
    
    @Inject 
    private ResaService resasrv;
    
    @GET
    @Path("{id}")
    @Produces("application/json")
    @TokenAuthenticated
    public Room findRoom(@PathParam("id") Long id) {
        logger.log(Level.INFO, "Getting Room by id {0}", id);
        return roomRepository.findById(id)
            .orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
    }
    
    @GET
    @TokenAuthenticated
    @Produces("application/json")
    public List<Room> findAll() {
        logger.info("Getting all room");
        return roomRepository.findAll();
    }
    
    @POST
    @TokenAuthenticated
    @Consumes("application/json")
    @Produces("application/json")
    public Response create(Room room) throws SystemException {
        logger.log(Level.INFO, "Creating room {0}", room.getName());
        String message ="la salle de réunion est crée";
        try{
            room.setUserCreate(securityctx.getUserPrincipal().getName());
            room.setUserUpdate(securityctx.getUserPrincipal().getName());
            room = roomRepository.create(room);
            return Response
                   .ok()
                   .entity(new DTOReturn(0, message , room))
                   .build();
        }catch (PersistenceException ex){
            logger.log(Level.INFO, "Error creating room {0}", room.getName());
            logger.log(Level.SEVERE, "Exception {0}", ex.toString());
            message =  ex.getMessage();
        }
        return Response
                   .ok()
                   .entity(new DTOReturn(-1, "La création a échouée  :" + message ))
                   .build();
    }

    @DELETE
    @Path("{id}")
    @TokenAuthenticated
    public Response delete(@PathParam("id") Long id){
        logger.log(Level.INFO, "Deleting room by id {0}", id);
        String message ="la salle de réunion est supprimée";
        try{
            if (resasrv.findbyRoomId(id).isEmpty())
            {
                resasrv.deletefromRoom(id);
                roomRepository.delete(id);
                return Response
                   .ok()
                   .entity(new DTOReturn(0, message))
                   .build();
            }
            else
            {
                return Response
                   .ok()
                   .entity(new DTOReturn(-2, "La salle possède des reservations à supprimer."))
                   .build();
            }
        }catch (IllegalArgumentException e){
            logger.log(Level.INFO, "Error deleting room by id {0}", id);
            message= e.getMessage();
        }
        catch (SystemException e){
            logger.log(Level.INFO, "Error deleting room by id {0}", id);
            message= e.getMessage();
        }
        return Response
                   .ok()
                   .entity(new DTOReturn(-1, "Erreur" + message))
                   .build();
    }


    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    @TokenAuthenticated
    public Response update(Room room) {
        logger.log(Level.INFO, "Updating room {0}", room.getName());
        String message ="la salle de réunion est modifiée";
        try{
            /* Maj de l'utilisateur de création*/
            room.setUserUpdate(securityctx.getUserPrincipal().getName());
            room = roomRepository.update(room);
            return Response
                   .ok()
                   .entity(new DTOReturn(0, message, room))
                   .build();
        }catch (PersistenceException ex){
            logger.log(Level.INFO, "Error updating room {0}", room.getName());
            message = ex.getMessage();
            
        } catch (SecurityException | NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException ex) {
            Logger.getLogger(RoomResource.class.getName()).log(Level.SEVERE, null, ex);
            message = ex.getMessage();
        }
        return Response
                   .ok()
                   .entity(new DTOReturn(-1,"La salle n'a pas être modifié" + message))
                   .build();
    }
}
