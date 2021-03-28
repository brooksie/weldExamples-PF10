/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brooksbank.weldexamples.db;

import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;

/**
 *
 * @author sjbro
 */
@Stateless
public class CondimentService extends AbstractFacade<Condiment>  {

    @Inject
    @WeldDatabase
    private EntityManager em;

    @Inject
    @ChangedTable
    private Event<Condiment> tableUpdatedEvent;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @Inject
    Logger log;

    public CondimentService() {
        super(Condiment.class);
    }

    // **** Overrides *** Overrides *** Overrides *****
    @Override
    public void remove(Condiment entity) {
        super.remove(entity); //To change body of generated methods, choose Tools | Templates.
        tableUpdatedEvent.fire(entity);
    }

    @Override
    public void edit(Condiment entity) {
        super.edit(entity); //To change body of generated methods, choose Tools | Templates.
        tableUpdatedEvent.fire(entity);
    }

    @Override
    public void create(Condiment entity) {
        super.create(entity); //To change body of generated methods, choose Tools | Templates.
        tableUpdatedEvent.fire(entity);
    }


    // **** Added **** Added **** Added ****

    public Condiment findByName(@NotNull String name) {
        Query q = em.createNamedQuery("Condiment.findByName");
        q.setParameter("name", name);
        return getSingleResult(q);
    }

    public Condiment findByIdWithDrinks(@NotNull Long id) {
        // NOTE: these two cases are equivalent. 
        // Either use the JOIN FETCH or use the entity graph. 
        // Both return the same results.
        
//        Query q = em.createNamedQuery("Condiment.findById");
//        q.setParameter("id", id);
//        q.setHint("javax.persistence.fetchgraph", getEGc_dc_d());
//        Condiment c = getSingleResult(q);

        Query q = em.createNamedQuery("Condiment.findByIdWithDrinks");
        q.setParameter("id", id);
        return getSingleResult(q);
    }

    private Condiment getSingleResult(Query q) {
        try {
            Condiment res = (Condiment) q.getSingleResult();
            return res;
        } catch (NoResultException ex) {
            return null;    // just pass back null in this case
        } // any other exception will just be thrown upwards
    }
    
    public int getCountOfDrinks(@NotNull Long id) {
        Query q = em.createNamedQuery("Condiment.getCountOfDrinks");
        q.setParameter("id", id);
        return (Integer) q.getSingleResult();
    }
    
    
//    public Condiment findWithDrinks(Long id) {
//        // find a specific Drink by ID using EntityGraph
//        Map<String, Object> properties = new HashMap<>();
//        properties.put("javax.persistence.fetchgraph", getEGc_dc_d());
//        Condiment condiment = em.find(Condiment.class, id, properties);
//        return condiment;
//    }
    
    public List<Condiment> findAllWithDrinks() {
        // find all condiments, but also load all associted drinks (NB. this is an expensive operation!
        Query q = em.createNamedQuery("Condiment.findAll");
        q.setHint("javax.persistence.fetchgraph", getEGc_dc_d());
        return q.getResultList();
    }
    
    private EntityGraph getEGc_dc_d() {
        return em.getEntityGraph("condiment.drinkcondiment.drink");
    }

}
