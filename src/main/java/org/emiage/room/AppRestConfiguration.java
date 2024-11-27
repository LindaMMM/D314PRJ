package org.emiage.room;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import java.util.Set;
import org.emiage.room.resources.AuthResource;
import org.emiage.room.resources.ResaResource;
import org.emiage.room.resources.RoomResource;
import org.emiage.room.resources.UserResource;
import org.emiage.room.security.jwtfilter.BearerTokenFilter;
import org.emiage.room.security.jwtfilter.JacksonFeature;


/**
 * Configures Jakarta RESTful Web Services for the application.
 * @author Juneau
 */
@ApplicationPath("rest")
public class AppRestConfiguration extends Application {
    
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        resources.add(JacksonFeature.class);
        resources.add(BearerTokenFilter.class);
        resources.add(AuthResource.class);
        resources.add(RoomResource.class);
        resources.add(ResaResource.class);
        resources.add(UserResource.class);
        return resources;
    }
}
