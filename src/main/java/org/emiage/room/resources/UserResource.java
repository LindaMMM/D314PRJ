/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.emiage.room.resources;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import java.util.HashSet;
import java.util.Set;
import org.emiage.room.configuration.RoomConfig;
import org.emiage.room.model.DTO.DTOLogin;
import org.emiage.room.security.entity.User;
import org.emiage.room.security.entity.UserJWT;
import org.emiage.room.security.repository.UserRepository;

/**
 *
 * @author linda
 */
@Path("user")
@RequestScoped
public class UserResource {

    @Inject
    UserRepository userRepository;

    @Inject
    RoomConfig roomConfig;

    @GET
    public Response ping() {
        return Response
                .ok("ping UserResource EE")
                .build();
    }

    @Path("login")
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

                        return Response.ok("Is connected  " + name)
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

    @Path("list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getlist() {
        return Response.ok().entity(userRepository.getAllUsers()).build();
    }

}
