package br.jus.trt3.poc.jee6.ws.soap;

import br.jus.trt3.poc.jee6.entity.Pessoa;
import br.jus.trt3.poc.jee6.repository.PessoaRepository;
import java.util.List;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * Exemplo de serviço SOAP, via JAX-WS
 *
 * @author denisf
 */
@WebService(serviceName = "PessoaWS")
public class PessoaWS {

    @Inject
    private PessoaRepository pessoaRepository;

    /**
     * Retorna uma lista de pessoas que possuam uma parte do nome e o início do
     * telefone fornecidos.
     *
     * @param nome Parte do nome usado como filtro
     * @param telefone Início do telefone usado como filtro
     * @return Lista de pessoas que satisfazem as condições
     */
    @WebMethod()
    public List<Pessoa> findByNomeOuTelefone(String nome, String telefone) {
        return pessoaRepository.findByNomeETelefone(nome, telefone);
    }

}
