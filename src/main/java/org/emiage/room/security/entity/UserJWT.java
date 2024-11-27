/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.emiage.room.security.entity;

import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.inject.Inject;
import java.security.interfaces.RSAPublicKey;
import java.time.LocalDateTime;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.emiage.room.configuration.RoomConfig;

/**
 *
 * @author linda
 */
public class UserJWT {

    private static final Logger LOGGER = Logger.getLogger(UserJWT.class.getName());
    private static final String ISSUER = "wsroom";
    private static final String ROLES = "roles";
    private static final String IDUSER = "user_id";
    private static final String USERNAME = "user_name";
    private static final int DURATION = 300;

    private final String user;
    private final Set<String> roles;

    UserJWT(String user, Set<String> roles) {
        this.user = user;
        this.roles = roles;
    }

    public String getUser() {
        return user;
    }

    public Set<String> getRoles() {
        if (roles == null) {
            return Collections.emptySet();
        }
        return roles;
    }

    public static String createToken(User user, RoomConfig roomConfig) {
        //The JWT signature algorithm we will be using to sign the token
        final LocalDateTime expire = LocalDateTime.now(ZoneOffset.UTC).plusMinutes(DURATION);
        Set<String> roles = new HashSet<>();
        roles.add("ADMIN");
        
        Algorithm algorithm = Algorithm.HMAC256(roomConfig.getClientSecret());
        return JWT.create()
                .withClaim(IDUSER, user.getId())
                .withClaim(USERNAME, user.getUsername())
                .withJWTId(user.getUsername())
                .withIssuer(ISSUER)
                .withExpiresAt(Date.from(expire.atZone(ZoneOffset.UTC).toInstant()))
                .withClaim(ROLES, new ArrayList<>(roles))
                .sign(algorithm);
    }

    public static Optional<UserJWT> parse(String jwtText, RoomConfig roomConfig) {
        Algorithm algorithm = Algorithm.HMAC256(roomConfig.getClientSecret());
        try {
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(ISSUER).build();
            final DecodedJWT jwt = verifier.verify(jwtText);
            final Claim roles = jwt.getClaim(ROLES);
            final Claim id = jwt.getClaim(IDUSER);
            final Claim name = jwt.getClaim(USERNAME);
            
            LOGGER.log(Level.INFO, "User id {0}", id);
            LOGGER.log(Level.INFO, "Roles id {0}", roles);
            LOGGER.log(Level.INFO, "Roles id {0}", name);
            LOGGER.log(Level.INFO, "User Name {0}", jwt.getId());
            
            
            return Optional.of(new UserJWT( name.asString(),
                    roles.asList(String.class).stream().collect(Collectors.toUnmodifiableSet())));
        } catch (JWTVerificationException exp) {
            LOGGER.log(Level.WARNING, "There is an error to load the JWT token", exp);
            return Optional.empty();
        }

    }
}
