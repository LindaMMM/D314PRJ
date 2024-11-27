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
import org.emiage.room.model.DTO.DTOReturn;
import org.emiage.room.model.DTO.DTOReturnLogin;
import org.emiage.room.model.entity.Room;
import org.emiage.room.model.repository.RoomRepository;
import org.emiage.room.security.jwtfilter.TokenAuthenticated;
import static org.hibernate.annotations.common.util.impl.LoggerFactory.logger;

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
    
    /*@GET   
    @TokenAuthenticated
    public Response ping(){
        return Response
                .ok("ping Jakarta EE")
                .build();
    }*/
    
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
        try{
            room.setUserCreate(securityctx.getUserPrincipal().getName());
            room.setUserUpdate(securityctx.getUserPrincipal().getName());
            roomRepository.create(room);
            return Response
                   .ok()
                   .entity(new DTOReturn(0, "Salle creer"))
                   .build();
        }catch (PersistenceException ex){
            logger.log(Level.INFO, "Error creating room {0}", room.getName());
            logger.log(Level.SEVERE, "Exception {0}", ex.toString());
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }

    @DELETE
    @Path("{id}")
    @TokenAuthenticated
    public Response delete(@PathParam("id") Long id){
        logger.log(Level.INFO, "Deleting room by id {0}", id);
        try{
            roomRepository.delete(id);
            return Response
                   .ok()
                   .entity(new DTOReturn(0, "Salle supprimer"))
                   .build();
        }catch (IllegalArgumentException e){
            logger.log(Level.INFO, "Error deleting room by id {0}", id);
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        catch (SystemException e){
            logger.log(Level.INFO, "Error deleting room by id {0}", id);
            return Response
                   .ok()
                   .entity(new DTOReturn(-1, "Erreur {0}".formatted(e.getMessage())))
                   .build();
        }
    }


    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    @TokenAuthenticated
    public Room update(Room room) {
        logger.log(Level.INFO, "Updating room {0}", room.getName());
        try{
            /* Maj de l'utilisateur de création*/
            room.setUserUpdate(securityctx.getUserPrincipal().getName());
            return roomRepository.update(room);
        }catch (PersistenceException ex){
            logger.log(Level.INFO, "Error updating room {0}", room.getName());
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        } catch (SecurityException ex) {
            Logger.getLogger(RoomResource.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotSupportedException ex) {
            Logger.getLogger(RoomResource.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SystemException ex) {
            Logger.getLogger(RoomResource.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RollbackException ex) {
            Logger.getLogger(RoomResource.class.getName()).log(Level.SEVERE, null, ex);
        } catch (HeuristicMixedException ex) {
            Logger.getLogger(RoomResource.class.getName()).log(Level.SEVERE, null, ex);
        } catch (HeuristicRollbackException ex) {
            Logger.getLogger(RoomResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
