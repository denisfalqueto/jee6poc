package br.jus.trt3.poc.jee6.rest.application.config;

import javax.ws.rs.core.Context;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.HttpResponse;
import org.jboss.resteasy.spi.interception.PostProcessInterceptor;

/**
 *
 * @author alexadb
 */
 public class Interceptor implements PostProcessInterceptor {
    @Context HttpResponse response;

    @Override
    public void postProcess(ServerResponse response) {
        if(this.response.getStatus() != 0){
            response.setStatus(this.response.getStatus());
        }
    }
}