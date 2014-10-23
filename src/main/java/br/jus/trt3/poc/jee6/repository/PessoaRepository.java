package br.jus.trt3.poc.jee6.repository;

import br.jus.trt3.poc.jee6.entity.Pessoa;
import br.jus.trt3.poc.jee6.entity.Pessoa_;
import br.jus.trt3.poc.jee6.entity.Telefone;
import br.jus.trt3.poc.jee6.entity.Telefone_;
import java.util.List;
import javax.inject.Inject;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.Criteria;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;
import org.jboss.logging.Logger;

/**
 * Define um repositório de objetos Pessoa
 *
 * @author denisf
 */
@Repository(forEntity = Pessoa.class)
public abstract class PessoaRepository extends AbstractEntityRepository<Pessoa, Long> implements CriteriaSupport<Pessoa> {

    @Inject
    private Logger log;

    public List<Pessoa> findByNomeETelefone(String nome, String numero) {
        log.trace("Entrou em findByNomeETelefone");
        Criteria<Pessoa, Pessoa> crit = criteria().
                distinct().
                fetch(Pessoa_.telefones);

        if (nome != null && !nome.trim().isEmpty()) {
            log.debug("Filtrando por nome");
            crit.like(Pessoa_.nome, "%" + nome + "%");
        }
        if (numero != null && !numero.trim().isEmpty()) {
            log.debug("Filtrando por número");
            crit.join(Pessoa_.telefones,
                    where(Telefone.class).
                    like(Telefone_.numero, "%" + numero + "%"));
        }

        log.debug("Retornar os resultados");
        return crit.orderAsc(Pessoa_.id).getResultList();
    }

}
