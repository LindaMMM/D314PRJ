/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.emiage.room.security.jwtfilter;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.Method;

import org.emiage.room.configuration.RoomConfig;
import org.emiage.room.security.entity.UserJWT;

import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.core.CacheControl;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.core.UriInfo;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author linda
 */
@Provider
@TokenAuthenticated
public class BearerTokenFilter implements ContainerRequestFilter {

    @Context
    ResourceInfo info;

    @Inject
    RoomConfig roomConfig;
    
    @Context
    UriInfo uriInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        // String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        try {
            
            Method method = info.getResourceMethod();
            if (method != null) {
                // Recherche de l'annotation de permission
                TokenAuthenticated tokenContext = method.getAnnotation(TokenAuthenticated.class);
                if (tokenContext == null)
                    // La route n'a pas besoin d'être authentifier
                    return;
                
                // Lecture de l'entête
                String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
                if (authorizationHeader == null){
                    throw new Exception("no token");
                }
                String token = authorizationHeader.substring("Bearer".length()).trim();
                
                var userJWT = UserJWT.parse(token, roomConfig);
                if (userJWT.isPresent()) {
                     final String subject = userJWT.get().getUser();
                     final SecurityContext securityContext = requestContext.getSecurityContext();
            requestContext.setSecurityContext(new SecurityContext() {
                        @Override
                        public Principal getUserPrincipal() {
                            return new Principal() {
                                @Override
                                public String getName() {
                                    return subject;
                                }
                            };
                        }

                        @Override
                        public boolean isUserInRole(String role) {
                            Set<String> roles = new HashSet<>();
                            roles.add("ADMIN");
                            return roles.contains(role);
                        }

                        @Override
                        public boolean isSecure() {
                            return true;
                        }

                        @Override
                        public String getAuthenticationScheme() {
                            return "Token-Beare";
                        }
                    });
                } else {
                    throw new Exception("no token");
                }
             
        }
        } catch (Exception e) {
            e.printStackTrace();
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }

    }

}
