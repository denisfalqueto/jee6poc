package br.jus.trt3.poc.jee6.repository;

import br.jus.trt3.poc.jee6.entity.TipoTelefone;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

/**
 * Repository para tipos de telefone
 *
 * @author denisf
 */
@Repository(forEntity = TipoTelefone.class)
public interface TipoTelefoneRepository extends EntityRepository<TipoTelefone, Long> {

}
