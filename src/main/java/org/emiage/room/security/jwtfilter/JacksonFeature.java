/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.emiage.room.security.jwtfilter;

import jakarta.ws.rs.core.Feature;
import jakarta.ws.rs.core.FeatureContext;
import org.glassfish.jersey.CommonProperties;

/**
 *
 * @author linda
 */
public class JacksonFeature implements Feature {

    @Override
    public boolean configure( final FeatureContext context ) {

        //String postfix = '.' + context.getConfiguration().getRuntimeType().name().toLowerCase();

        context.property( CommonProperties.MOXY_JSON_FEATURE_DISABLE_SERVER, true );

        //context.register( JsonParseExceptionMapper.class );
        //context.register( JsonMappingExceptionMapper.class );
        //context.register( JacksonJsonProvider.class, MessageBodyReader.class, MessageBodyWriter.class );

        return true;
    }
}
