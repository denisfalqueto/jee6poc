package br.jus.trt3.poc.jee6.ejb;

import br.jus.trt3.poc.jee6.entity.Pessoa;
import br.jus.trt3.poc.jee6.repository.PessoaRepository;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Vamos ver se esse troço funciona com EJB
 * @author denisf
 */
@Stateless
public class PessoaFacade {
    
    @Inject
    private PessoaRepository pessoaRepository;
    
    public void save(Pessoa p) {
        pessoaRepository.save(p);
    }
    
    public void mergeAndRemove(Pessoa p) {
        if (!pessoaRepository.contains(p)) {
            p = pessoaRepository.merge(p);
        }
        pessoaRepository.remove(p);
    }
    
    public List<Pessoa> findByNomeETelefone(String filtroNome, String filtroTelefone) {
        return pessoaRepository.findByNomeETelefone(filtroNome, filtroTelefone);
    }
    
    public List<Pessoa> findAll() {
        return pessoaRepository.findAll();
    }
    
    public Pessoa findBy(Long pk) {
        return pessoaRepository.findBy(pk);
    }
    
}
