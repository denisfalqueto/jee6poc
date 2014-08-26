package br.jus.trt3.poc.jee6.repository;

import br.jus.trt3.poc.jee6.entity.Pessoa;
import java.util.List;
import javax.persistence.TypedQuery;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;

/**
 * Define um repositório de objetos Pessoa
 *
 * @author denisf
 */
@Repository(forEntity = Pessoa.class)
public abstract class PessoaRepository extends AbstractEntityRepository<Pessoa, Long> {

    public List<Pessoa> findByNomeETelefone(String nome, String numero) {
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

        TypedQuery<Pessoa> query = typedQuery(strQuery.toString());

        if (nome != null && !nome.trim().isEmpty()) {
            query.setParameter("nome", "%" + nome + "%");
        }
        if (numero != null && !numero.trim().isEmpty()) {
            query.setParameter("numero", numero + "%");
        }

        return query.getResultList();
    }
}
