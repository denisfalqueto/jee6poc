package br.jus.trt3.poc.jee6.producer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 * Producer de Entitymanager, necessário para o módulo Data do DeltaSpike.
 *
 * @author denisf
 */
@ApplicationScoped
public class EntityManagerProducer {
    
    @PersistenceUnit
    private EntityManagerFactory emf;

    @Produces
    @Default
    @RequestScoped
    public EntityManager produces() {
        return emf.createEntityManager();
    }

    public void close(@Disposes @Default EntityManager em) {
        if (em.isOpen()) {
            em.close();
        }
    }
}
