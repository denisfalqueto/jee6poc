package br.jus.trt3.poc.jee6.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;

/**
 *
 * @author denisf
 */
@Entity
@XmlRootElement
@JsonAutoDetect(fieldVisibility=Visibility.NONE, getterVisibility=Visibility.PUBLIC_ONLY, isGetterVisibility=Visibility.NONE)
public class Pessoa implements Serializable {

    public enum Sexo {
        Masculino, Feminino, NaoInformado;
    }

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataNascimento;
    private Sexo sexo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pessoa")
    private Set<Telefone> telefones = new HashSet<Telefone>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public Set<Telefone> getTelefones() {
        return telefones;
    }

    public void setTelefones(Set<Telefone> telefones) {
        this.telefones = telefones;
    }
    
    @Transient
    public int getIdade() {
        return getIdadeEm(Calendar.getInstance());
    }
    
    @Transient
    public int getIdadeEm(Date data) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        return getIdadeEm(cal);
    }
    
    @Transient
    public int getIdadeEm(Calendar data) {
        Calendar calAniv = new GregorianCalendar();
        calAniv.setTime(dataNascimento);
        if (data.after(calAniv)) {
            int idade = data.get(Calendar.YEAR) - calAniv.get(Calendar.YEAR);
            if (data.get(Calendar.MONTH) < calAniv.get(Calendar.MONTH)) {
                idade--;
            } else if ((data.get(Calendar.MONTH) == calAniv.get(Calendar.MONTH)) &&
                    (data.get(Calendar.DAY_OF_MONTH) < calAniv.get(Calendar.DAY_OF_MONTH))) {
                idade--;
            }
            return idade;
        }
        return -1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pessoa)) {
            return false;
        }
        Pessoa other = (Pessoa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.jus.trt3.poc.jee6.entity.Pessoa[ id=" + id + " ]";
    }

}
