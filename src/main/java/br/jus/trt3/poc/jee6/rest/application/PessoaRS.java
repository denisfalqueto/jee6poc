package br.jus.trt3.poc.jee6.rest.application;

import br.jus.trt3.poc.jee6.entity.Pessoa;
import br.jus.trt3.poc.jee6.repository.PessoaRepository;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import org.jboss.resteasy.spi.HttpResponse;
import org.jboss.resteasy.spi.NotFoundException;

/**
 *
 * @author alexadb
 */
@Path("/pessoa")
public class PessoaRS {

    @Inject
    private PessoaRepository pessoaRepository;

    @GET
    @Produces("application/json")
    public List<Pessoa> getPessoas(@QueryParam("nome") String nome, 
            @QueryParam("telefone") String telefone, @Context final HttpServletResponse response)
            throws NotFoundException {
        List<Pessoa> pessoas = pessoaRepository.findByNomeETelefone(nome, telefone);
        
        if (pessoas.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            try {
                response.flushBuffer();
            } catch(Exception e){}
        }
        return pessoas;
    }
    
    @GET
    @Produces("application/json")
    @Path("/id/{id}")
    public Pessoa getPessoa(@PathParam("id") Long id, @Context final HttpServletResponse response) throws NotFoundException{
        Pessoa pessoa = pessoaRepository.findBy(id);
        if (pessoa == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            try {
                response.flushBuffer();
            } catch(Exception e){}
        }
        return pessoa;
    }
}