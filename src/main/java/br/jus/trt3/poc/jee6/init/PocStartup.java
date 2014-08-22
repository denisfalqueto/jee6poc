package br.jus.trt3.poc.jee6.init;

import br.jus.trt3.poc.jee6.entity.Pessoa;
import br.jus.trt3.poc.jee6.entity.Telefone;
import br.jus.trt3.poc.jee6.entity.TipoTelefone;
import br.jus.trt3.poc.jee6.repository.PessoaRepository;
import br.jus.trt3.poc.jee6.repository.TipoTelefoneRepository;
import java.util.Date;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import org.apache.deltaspike.core.api.projectstage.ProjectStage;

/**
 * Uma instância deste objeto deverá existir apenas para executar um método no
 * início da carga da aplicação. Pode ser usado para carregar instâncias de
 * objetos.
 *
 * @author denisf
 */
@Singleton
@Startup
public class PocStartup {

    @Inject
    private ProjectStage projectStage;

    @Inject
    private PessoaRepository pessoaRepository;

    @Inject
    private TipoTelefoneRepository tipoTelefoneRepository;

    @PostConstruct
    public void execute() {
        // Se estivermos em desenvolvimento...
        if (ProjectStage.Development.equals(projectStage)) {

            // Criar tipos de telefones
            TipoTelefone comercial = new TipoTelefone();
            comercial.setDescricao("Comercial");
            tipoTelefoneRepository.save(comercial);

            TipoTelefone residencial = new TipoTelefone();
            residencial.setDescricao("Residencial");
            tipoTelefoneRepository.save(residencial);

            TipoTelefone celular = new TipoTelefone();
            celular.setDescricao("Celular");
            tipoTelefoneRepository.save(celular);

            TipoTelefone[] tipos = new TipoTelefone[]{
                comercial, residencial, celular
            };

            // Criar pessoas aleatoriamente
            Random r = new Random();
            int maxPessoas = r.nextInt(200);
            for (int i = 0; i < maxPessoas; i++) {
                Pessoa pessoa = new Pessoa();
                pessoa.setNome(String.format("Nome %d", i + 1));
                pessoa.setSexo(r.nextBoolean() ? Pessoa.Sexo.Masculino : Pessoa.Sexo.Feminino);
                pessoa.setDataNascimento(new Date());

                int maxTelefones = r.nextInt(10);
                for (int j = 0; j < maxTelefones; j++) {
                    Telefone tel = new Telefone();
                    tel.setPessoa(pessoa);
                    tel.setTipo(tipos[r.nextInt(tipos.length)]);
                    StringBuilder sbNumero = new StringBuilder();
                    for (int k = 0; k < 8; k++) {
                        sbNumero.append(r.nextInt(10));
                    }
                    tel.setNumero(sbNumero.toString());
                    pessoa.getTelefones().add(tel);
                }

                pessoaRepository.save(pessoa);
            }
        }
    }
}
