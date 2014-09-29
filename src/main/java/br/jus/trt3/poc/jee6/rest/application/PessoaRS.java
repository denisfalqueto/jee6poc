package br.jus.trt3.poc.jee6.rest.application;

import br.jus.trt3.poc.jee6.entity.Pessoa;
import br.jus.trt3.poc.jee6.repository.PessoaRepository;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

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
    public List<Pessoa> getPessoas(@QueryParam("nome") String nome, @QueryParam("telefone") String telefone) {
        List<Pessoa> pessoas = pessoaRepository.findByNomeETelefone(nome, telefone);

        return pessoas;
    }
}