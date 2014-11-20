package br.jus.trt3.poc.jee6.rest.application;

import com.jcabi.manifests.Manifests;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author alexadb
 */
@Produces(MediaType.APPLICATION_JSON+"; charset=UTF-8")
@Path("/info")
public class ManifestRS {

    @GET
    @Path("/{p: (Application-(Id|Version)|Commit-Id)}")
    public String getProperty(@PathParam("p") String property) {
        return Manifests.read(property);
    }
        
}