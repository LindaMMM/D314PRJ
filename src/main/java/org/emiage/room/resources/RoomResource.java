package org.emiage.room.resources;

import com.auth0.jwt.impl.HeaderClaimsHolder;
import com.auth0.jwt.interfaces.Header;
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
    public Room create(Room room) {
        logger.log(Level.INFO, "Creating room {0}", room.getName());
        try{
            /* Maj Du createur de la salle*/
            room.setUserCreate(securityctx.getUserPrincipal().getName());
            return roomRepository.create(room);
        }catch (PersistenceException ex){
            logger.log(Level.INFO, "Error creating room {0}", room.getName());
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }

    @DELETE
    @Path("{id}")
    @TokenAuthenticated
    public void delete(@PathParam("id") Long id) {
        logger.log(Level.INFO, "Deleting room by id {0}", id);
        try{
            roomRepository.delete(id);
        }catch (IllegalArgumentException e){
            logger.log(Level.INFO, "Error deleting room by id {0}", id);
            throw new WebApplicationException(Response.Status.NOT_FOUND);
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
            return roomRepository.create(room);
        }catch (PersistenceException ex){
            logger.log(Level.INFO, "Error updating room {0}", room.getName());
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
}
