package br.jus.trt3.poc.jee6.repository;

import br.jus.trt3.poc.jee6.entity.Pessoa;
import java.util.List;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

/**
 * Define um repositório de objetos Pessoa
 *
 * @author denisf
 */
@Repository(forEntity = Pessoa.class)
public interface PessoaRepository extends EntityRepository<Pessoa, Long> {

    @Query("from Pessoa p "
            + "where exists ( "
            + "  select 1 "
            + "  from Telefone t "
            + "  where t.pessoa = p "
            + "    and t.numero like ?1"
            + ")")
    public List<Pessoa> findByNumeroTelefone(String numero);
}
