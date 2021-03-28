/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brooksbank.weldexamples.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceUtil;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;

/**
 *
 * @author sjbro
 */
@Stateless
public class DrinkService extends AbstractFacade<Drink> {

    @Inject
    @WeldDatabase
    private EntityManager em;

    @Inject
    @ChangedTable
    private Event<Drink> drinkTableUpdatedEvent;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DrinkService() {
        super(Drink.class);
    }

    // **** Overrides *** Overrides *** Overrides *****
    @Override
    public void remove(Drink entity) {
        super.remove(entity); //To change body of generated methods, choose Tools | Templates.
        drinkTableUpdatedEvent.fire(entity);
    }

    @Override
    public void edit(Drink entity) {
        super.edit(entity); //To change body of generated methods, choose Tools | Templates.
        drinkTableUpdatedEvent.fire(entity);
    }

    @Override
    public void create(Drink entity) {
        super.create(entity); //To change body of generated methods, choose Tools | Templates.
        drinkTableUpdatedEvent.fire(entity);
    }

    // **** Added **** Added **** Added ****
    public Drink findByName(@NotNull String name) {
        Query q = em.createNamedQuery("Drink.findByName");
        q.setParameter("name", name);
        return getSingleResult(q);
    }

    public Drink findByIdEG(@NotNull Long id) {
        Query q = em.createNamedQuery("Drink.findById");
        q.setParameter("id", id);
        q.setHint("javax.persistence.fetchgraph", getEGd_dc_c());
        return getSingleResult(q);
    }

    private Drink getSingleResult(Query q) {
        try {
            Drink res = (Drink) q.getSingleResult();
            return res;
        } catch (NoResultException ex) {
            return null;    // just pass back null in this case
        } // any other exception will just be thrown upwards
    }

    public Drink findWithEG(Long id) {
        // find a specific Drink by ID using EntityGraph
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.fetchgraph", getEGd_dc_c());
        Drink drink = em.find(Drink.class, id, properties);
//        // ****
//        testEG(drink);
//        // ****
        return drink;
    }

    public List<Drink> findAllWithEG() {
        // find a specific Drink by ID using EntityGraph
        Query q = em.createNamedQuery("Drink.findAll");
        q.setHint("javax.persistence.fetchgraph", getEGd_dc_c());
        return q.getResultList();
    }
    
    private EntityGraph getEGd_dc_c() {
        return em.getEntityGraph("drink.drinkcondiment.condiment");

    }

//    private void testEG(Drink drink) {
//        PersistenceUtil pu = em.getEntityManagerFactory().getPersistenceUnitUtil();
//        System.out.println("           drink loaded: " + pu.isLoaded(drink));
//        System.out.println("      drink.name loaded: " + pu.isLoaded(drink, "name"));
//        System.out.println("   drink.created loaded: " + pu.isLoaded(drink, "created"));
//        System.out.println("drink.condiments loaded: " + pu.isLoaded(drink, "condiments"));
//        System.out.println(drink);
//        if ( pu.isLoaded(drink, "condiments")) {
//            DrinkCondiment dc = drink.getCondiments().get(0);
//            System.out.println(dc);
//            System.out.println("drink.condiments.condiment loaded: " + pu.isLoaded(dc, "condiment"));
//            if (pu.isLoaded(dc, "condiment")) {
//                Condiment c = dc.getCondiment();
//                System.out.println(c);
//            }
//        }
//    }
}
