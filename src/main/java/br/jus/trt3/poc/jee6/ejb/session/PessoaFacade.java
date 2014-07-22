/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.jus.trt3.poc.jee6.ejb.session;

import br.jus.trt3.poc.jee6.entity.Pessoa;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author denisf
 */
@Stateless
public class PessoaFacade extends AbstractFacade<Pessoa> {
    @PersistenceContext(unitName = "br.jus.trt.bibliotecas_jee6poc_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PessoaFacade() {
        super(Pessoa.class);
    }
    
}
