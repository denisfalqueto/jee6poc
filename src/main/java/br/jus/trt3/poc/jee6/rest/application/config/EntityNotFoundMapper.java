package br.jus.trt3.poc.jee6.rest.application.config;

import javax.persistence.EntityNotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author alexadb
 */
@Provider
public class EntityNotFoundMapper 
    implements ExceptionMapper<EntityNotFoundException>
{
   @Override
   public Response toResponse(EntityNotFoundException exception)
   {
      return Response.status(Status.NOT_FOUND).type(MediaType.APPLICATION_JSON+"; charset=UTF-8").entity("{ \"error\": \"Entidade não encontrada\" }").build();
   }
}