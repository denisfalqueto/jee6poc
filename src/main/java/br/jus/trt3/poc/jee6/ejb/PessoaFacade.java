package br.jus.trt3.poc.jee6.ejb;

import br.jus.trt3.poc.jee6.entity.Pessoa;
import br.jus.trt3.poc.jee6.repository.PessoaRepository;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author denisf
 */
@Stateless
public class PessoaFacade {

    @Inject
    private PessoaRepository repository;

    public List<Pessoa> findByNomeETelefone(String filtroNome, String filtroTelefone) {
        return repository.findByNomeETelefone(filtroNome, filtroTelefone);
    }

    public void save(Pessoa pessoa) {
        repository.save(pessoa);
    }

    public void mergeAndRemove(Pessoa pessoa) {
        repository.remove(repository.merge(pessoa));
    }

    public List<Pessoa> findAll() {
        return repository.findAll();
    }

    public Pessoa findBy(Long id) {
        return repository.findBy(id);
    }
}
