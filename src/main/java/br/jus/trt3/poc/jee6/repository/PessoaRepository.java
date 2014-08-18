package br.jus.trt3.poc.jee6.repository;

import br.jus.trt3.poc.jee6.entity.Pessoa;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

/**
 * Define um repositório de objetos Pessoa
 * @author denisf
 */
@Repository(forEntity = Pessoa.class)
public interface PessoaRepository extends EntityRepository<Pessoa, Long> {
}
