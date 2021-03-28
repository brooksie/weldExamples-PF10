/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brooksbank.weldexamples.db;

import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;

/**
 *
 * @author sjbro
 */
@Stateless
public class CodetypesService extends AbstractFacade<Codetypes> {

    private static final int DEFAULT_ITEM_LIMIT = 25;

    @Inject
    @WeldDatabase
    private EntityManager em;

    @Inject
    @ChangedTable
    private Event<Codetypes> tableUpdatedEvent;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @Inject
    Logger log;

    public CodetypesService() {
        super(Codetypes.class);
    }

    // **** Overrides *** Overrides *** Overrides *****
    @Override
    public void remove(Codetypes entity) {
        super.remove(entity); //To change body of generated methods, choose Tools | Templates.
        tableUpdatedEvent.fire(entity);
    }

    @Override
    public void edit(Codetypes entity) {
        super.edit(entity); //To change body of generated methods, choose Tools | Templates.
        tableUpdatedEvent.fire(entity);
    }

    @Override
    public void create(Codetypes entity) {
        super.create(entity); //To change body of generated methods, choose Tools | Templates.
        tableUpdatedEvent.fire(entity);
    }


    // **** Added **** Added **** Added ****

    public Codetypes findByCodetype(@NotNull String codetype) {
        Query q = em.createNamedQuery("Codetypes.findByCodetype");
        q.setParameter("codetype", codetype);
        return getSingleResult(q);
    }

    private Codetypes getSingleResult(Query q) {
        try {
            Codetypes res = (Codetypes) q.getSingleResult();
            return res;
        } catch (NoResultException ex) {
            return null;    // just pass back null in this case
        } // any other exception will just be thrown upwards
    }

}
