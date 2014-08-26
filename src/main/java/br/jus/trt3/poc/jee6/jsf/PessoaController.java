package br.jus.trt3.poc.jee6.jsf;

import br.jus.trt3.poc.jee6.entity.Pessoa;
import br.jus.trt3.poc.jee6.entity.Telefone;
import br.jus.trt3.poc.jee6.jsf.util.JsfUtil;
import br.jus.trt3.poc.jee6.jsf.util.JsfUtil.PersistAction;
import br.jus.trt3.poc.jee6.repository.PessoaRepository;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import org.primefaces.event.SelectEvent;

@ManagedBean(name = "pessoaController")
@SessionScoped
public class PessoaController implements Serializable {

    @Inject
    private PessoaRepository pessoaRepository;
    private List<Pessoa> items = null;
    private Pessoa selected;
    private String filtroNome;
    private String filtroTelefone;
    private Set<Telefone> telefones = null;
    private Telefone telefoneSelected;

    public PessoaController() {
    }

    public Pessoa getSelected() {
        return selected;
    }

    public void setSelected(Pessoa selected) {
        this.selected = selected;
    }

    public Set<Telefone> getTelefones() {
        return telefones;
    }

    public void setTelefones(Set<Telefone> telefones) {
        this.telefones = telefones;
    }

    public Telefone getTelefoneSelected() {
        return telefoneSelected;
    }

    public void setTelefoneSelected(Telefone telefoneSelected) {
        this.telefoneSelected = telefoneSelected;
    }

    public String getFiltroNome() {
        return filtroNome;
    }

    public void setFiltroNome(String filtroNome) {
        this.filtroNome = filtroNome;
    }

    public String getFiltroTelefone() {
        return filtroTelefone;
    }

    public void setFiltroTelefone(String filtroTelefone) {
        this.filtroTelefone = filtroTelefone;
    }

    private PessoaRepository getRepository() {
        return pessoaRepository;
    }

    public Pessoa prepareCreate() {
        selected = new Pessoa();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PessoaCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PessoaUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("PessoaDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Pessoa> getItems() {
        if (items == null) {
            items = getRepository().findByNomeETelefone(filtroNome, filtroTelefone);
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            try {
                if (persistAction != PersistAction.DELETE) {
                    getRepository().save(selected);
                } else {
                    getRepository().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public List<Pessoa> getItemsAvailableSelectMany() {
        return getRepository().findAll();
    }

    public List<Pessoa> getItemsAvailableSelectOne() {
        return getRepository().findAll();
    }

    public Pessoa.Sexo[] getSexos() {
        return Pessoa.Sexo.values();
    }

    public void onPessoaSelected() {
        Set<Telefone> tels = null;
        if (getSelected() != null) {
            tels = getSelected().getTelefones();
        }
        setTelefones(tels);
    }

    public void onFiltroKeyUp() {
        this.items = null;
    }

    @FacesConverter(forClass = Pessoa.class)
    public static class PessoaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PessoaController controller = (PessoaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "pessoaController");
            return controller.getRepository().findBy(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Pessoa) {
                Pessoa o = (Pessoa) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Pessoa.class.getName()});
                return null;
            }
        }

    }

}
