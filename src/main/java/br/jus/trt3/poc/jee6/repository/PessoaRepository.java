package br.jus.trt3.poc.jee6.repository;

import br.jus.trt3.poc.jee6.entity.Pessoa;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.TypedQuery;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.jboss.logging.Logger;

/**
 * Define um repositório de objetos Pessoa
 *
 * @author denisf
 */
@Repository(forEntity = Pessoa.class)
public abstract class PessoaRepository extends AbstractEntityRepository<Pessoa, Long> {
    
    @Inject
    private Logger log;

    public List<Pessoa> findByNomeETelefone(String nome, String numero) {
        log.trace("entrou em findBynomeETelefone");
        log.tracef("nome = %s", nome);
        log.tracef("numero = %s", numero);
        
        log.debug("Construindo a consulta");
        StringBuilder strQuery = new StringBuilder();
        String conector = " where ";
        strQuery.append("from Pessoa p");

        if (nome != null && !nome.trim().isEmpty()) {
            strQuery.append(conector).append("lower(p.nome) like lower(:nome)");
            conector = " and ";
        }
        
        if (numero != null && !numero.isEmpty()) {
            strQuery.append(conector).append("exists ( "
                    + "select 1 " 
                    + "  from Telefone t " 
                    + "  where t.pessoa = p " 
                    + "    and t.numero like :numero) ");
            conector = " and ";
        }

        log.debug("Criar a typedQuery");
        TypedQuery<Pessoa> query = typedQuery(strQuery.toString());

        log.debug("Passar os parãmetros");
        if (nome != null && !nome.trim().isEmpty()) {
            query.setParameter("nome", "%" + nome + "%");
        }
        if (numero != null && !numero.trim().isEmpty()) {
            query.setParameter("numero", numero + "%");
        }

        log.debug("Executar a consulta");
        return query.getResultList();
    }
}
