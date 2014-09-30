package br.jus.trt3.poc.jee6.rest.application;

import br.jus.trt3.poc.jee6.entity.Pessoa;
import br.jus.trt3.poc.jee6.repository.PessoaRepository;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import org.jboss.resteasy.annotations.GZIP;
import org.jboss.resteasy.spi.validation.ValidateRequest;

/**
 *
 * @author alexadb
 */
@Produces(MediaType.APPLICATION_JSON+"; charset=UTF-8")
@Consumes(MediaType.APPLICATION_JSON+"; charset=UTF-8")
@Path("/pessoa")
@ValidateRequest
public class PessoaRS {

    @Inject
    private PessoaRepository pessoaRepository;

    @GET
    //@Produces(MediaType.APPLICATION_JSON+"; charset=UTF-8")
    @GZIP
    public List<Pessoa> getPessoas(@QueryParam("nome") String nome, 
            @QueryParam("telefone") String telefone) {
        List<Pessoa> pessoas = pessoaRepository.findByNomeETelefone(nome, telefone);
        
        if (pessoas.isEmpty()) {
            ResponseBuilder builder = Response.status(Status.NOT_FOUND);
            builder.entity("{ \"error\": \"Entidade não encontrada\" }");
            throw new WebApplicationException(builder.build());
        }
        return pessoas;
    }
    
    @GET
    @Path("/{id: \\d+}")
    //@Produces(MediaType.APPLICATION_JSON+"; charset=UTF-8")
    public Pessoa getPessoa(@PathParam("id") Long id){
        Pessoa pessoa = pessoaRepository.findBy(id);
        if (pessoa == null) {
            ResponseBuilder builder = Response.status(Status.NOT_FOUND);
            builder.entity("{ \"error\": \"Entidade não encontrada\" }");
            throw new WebApplicationException(builder.build());
        }
        return pessoa;
    }
    
    //@POST
    //public Pessoa 
}