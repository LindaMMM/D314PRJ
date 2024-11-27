/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.emiage.room.resources;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.emiage.room.configuration.RoomConfig;
import org.emiage.room.model.DTO.DTOLogin;
import org.emiage.room.model.DTO.DTOReturnLogin;
import org.emiage.room.security.entity.User;
import org.emiage.room.security.entity.UserJWT;
import org.emiage.room.security.repository.UserRepository;

/**
 *
 * @author linda
 */
@Path("auth")
@RequestScoped
public class AuthResource {
    @Inject
    UserRepository userRepository;

    @Inject
    RoomConfig roomConfig;
    
    @GET   
    public Response ping(){
        return Response
                .ok("ping Jakarta EE")
                .build();
    }
    
    @Path("login")
    @Produces("application/json")
    @POST
    public Response ping(DTOLogin user, @Context UriInfo uriInfo) {
        try {
            if (user != null) {
                String name = user.getLogin();
                String password = user.getPassword();
                if ((name != null) && (password != null)) {
                    // Recheche en base le login
                    User usersearch = userRepository.findByLoging(name);
                    // Vérification du login et mot de pass
                    if (usersearch != null
                            && usersearch.getPassword().equals(password)
                            && usersearch.getUsername().equals(name)) {

                        String token = UserJWT.createToken(usersearch, roomConfig);
                        
                        return Response
                                .ok()
                                .entity(new DTOReturnLogin(0, "User connected",token ))
                                .header("Authorization", "Bearer " + token)
                                .build();
                    } else {
                        return Response.status(Response.Status.UNAUTHORIZED).build();
                    }
                }
            }

        } catch (Exception er) {

        }
        return Response.status(Response.Status.UNAUTHORIZED).build();

    }


}
