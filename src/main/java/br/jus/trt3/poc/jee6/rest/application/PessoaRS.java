package br.jus.trt3.poc.jee6.rest.application;

import br.jus.trt3.poc.jee6.entity.Pessoa;
import br.jus.trt3.poc.jee6.entity.Telefone;
import br.jus.trt3.poc.jee6.repository.PessoaRepository;
import br.jus.trt3.poc.jee6.rest.application.config.LogMarker;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import org.apache.deltaspike.jpa.api.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.resteasy.annotations.GZIP;

/**
 *
 * @author alexadb
 */
@GZIP
@Transactional
@Produces(MediaType.APPLICATION_JSON+"; charset=UTF-8")
@Consumes(MediaType.APPLICATION_JSON+"; charset=UTF-8")
@Path("/pessoas")
public class PessoaRS {

    @Inject
    private PessoaRepository pessoaRepository;
    
    @Inject
    private EntityManager entityManager;
    
    private static Logger logger = LogManager.getLogger(PessoaRS.class);
    
    @GET
    public List<Pessoa> getPessoas(@QueryParam("nome") String nome, 
            @QueryParam("telefone") String telefone) {
        logger.entry(nome, telefone);
        List<Pessoa> pessoas = pessoaRepository.findByNomeETelefone(nome, telefone);
        
        if (pessoas.isEmpty()) {
            logger.debug(LogMarker.DB_QUERY, "Pessoa com nome '{}' não encontrada", nome);
            ResponseBuilder builder = Response.status(Status.NOT_FOUND);
            throw new WebApplicationException(builder.build());
        }
        logger.debug(LogMarker.DB_QUERY, "{} pessoas encontradas", pessoas.size());
        return logger.exit(pessoas);
    }
    
    @GET
    @Path("/{id: \\d+}")
    public Pessoa getPessoa(@PathParam("id") Long id){
        logger.entry(id);
        Pessoa pessoa = pessoaRepository.findBy(id);
        if (pessoa == null) {
            ResponseBuilder builder = Response.status(Status.NOT_FOUND);
            builder.entity("{ \"error\": \"Entidade não encontrada\" }");
            throw logger.throwing(new WebApplicationException(builder.build()));
        }
        return logger.exit(pessoa);
    }
    
    @POST
    public Response add(Pessoa pessoa) {
        logger.entry(pessoa);
        for (Telefone telefone : pessoa.getTelefones()) {
            telefone.setPessoa(pessoa);
        }
        pessoaRepository.save(pessoa);
        return logger.exit(Response.status(Status.CREATED).entity(pessoa.getId()).build());
    }
   
    @PUT
    public Pessoa update(Pessoa pessoa) {
        logger.entry(pessoa);
        Pessoa pessoaParaAtualizar = pessoaRepository.findBy(pessoa.getId());
        if (pessoaParaAtualizar != null) {
            pessoaRepository.save(pessoa);
            return logger.exit(pessoa);
        } else {
            throw logger.throwing(new WebApplicationException(Response.status(Status.NOT_FOUND).build()));
        }
    }
    
    @DELETE
    @Path("/{id: \\d+}")
    public Response delete(@PathParam("id") Long id) {
        logger.entry(id);
        Pessoa pessoa = pessoaRepository.findBy(id);
        if (pessoa != null) {
            entityManager.remove(entityManager.merge(pessoa));
            return logger.exit(Response.status(Status.ACCEPTED).build());
        } else { 
            return logger.exit(Response.status(Status.NOT_FOUND).build());
        }
    }
}
