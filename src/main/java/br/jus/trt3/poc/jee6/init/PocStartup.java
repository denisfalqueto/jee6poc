package br.jus.trt3.poc.jee6.init;

import br.jus.trt3.poc.jee6.entity.Pessoa;
import br.jus.trt3.poc.jee6.entity.Telefone;
import br.jus.trt3.poc.jee6.entity.TipoTelefone;
import br.jus.trt3.poc.jee6.repository.PessoaRepository;
import br.jus.trt3.poc.jee6.repository.TipoTelefoneRepository;
import java.util.Calendar;
import java.util.GregorianCalendar;
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

            // Alguns nomes comuns
            String[] nomesPropriosMasc = new String[]{"Lucas", "Enzo", "Guilherme", "Gabriel",
                "Gustavo", "João", "Arthur", "Mateus", "Rafael", "Adão", "Eduardo", "Bruno"};
            String[] nomesPropriosFem = new String[]{"Isabela", "Sofia", "Maria", "Ana", "Julia",
                "Beatriz", "Laura", "Camila", "Amanda", "Leticia", "Manuela", "Luana", "Bruna"};
            String[] sobrenomes = new String[]{"Araral", "Barra", "Campos", "Cardoso", "Dias", "Gonzaga",
                "Meier", "Melo", "Silva", "Silveira", "da Silva", "Fernandes", "Fraga", "Fonseca"};

            // Criar pessoas aleatoriamente
            Random r = new Random();
            Calendar hoje = new GregorianCalendar();
            
            int maxPessoas = r.nextInt(200);
            for (int i = 0; i < maxPessoas; i++) {
                Pessoa pessoa = new Pessoa();
                pessoa.setSexo(r.nextBoolean() ? Pessoa.Sexo.Masculino : Pessoa.Sexo.Feminino);

                // Definir um nome aleatoriamente
                StringBuilder nome = new StringBuilder();
                if (pessoa.getSexo().equals(Pessoa.Sexo.Masculino)) {
                    nome.append(nomesPropriosMasc[r.nextInt(nomesPropriosMasc.length)]);
                    // Uma chance em 10 de ter um nome composto :)
                    if (r.nextInt(10) == 0) {
                        nome.append(" ").append(nomesPropriosMasc[r.nextInt(nomesPropriosMasc.length)]);
                    }
                } else {
                    nome.append(nomesPropriosFem[r.nextInt(nomesPropriosFem.length)]);
                    // Uma chance em 10 de ter um nome composto :)
                    if (r.nextInt(10) == 0) {
                        nome.append(" ").append(nomesPropriosFem[r.nextInt(nomesPropriosFem.length)]);
                    }
                }
                // Sobrenome
                nome.append(" ").append(sobrenomes[r.nextInt(sobrenomes.length)]);
                
                // Até 80% de chances de ter um segundo sobrenome
                if (r.nextInt(10) < 8) {
                    nome.append(" ").append(sobrenomes[r.nextInt(sobrenomes.length)]);
                }
                pessoa.setNome(nome.toString());
                
                // Gerar uma data de aniversário aleatória, com até 90 anos de idade.
                Calendar aniv = (Calendar) hoje.clone();
                aniv.add(Calendar.DATE, -1 * r.nextInt(365 * 90));
                pessoa.setDataNascimento(aniv.getTime());

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
