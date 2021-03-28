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
public class TagService extends AbstractFacade<Tag>  {

    @Inject
    @WeldDatabase
    private EntityManager em;

    @Inject
    @ChangedTable
    private Event<Tag> tableUpdatedEvent;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @Inject
    Logger log;

    public TagService() {
        super(Tag.class);
    }

    // **** Overrides *** Overrides *** Overrides *****
    @Override
    public void remove(Tag entity) {
        super.remove(entity); //To change body of generated methods, choose Tools | Templates.
        tableUpdatedEvent.fire(entity);
    }

    @Override
    public void edit(Tag entity) {
        super.edit(entity); //To change body of generated methods, choose Tools | Templates.
        tableUpdatedEvent.fire(entity);
    }

    @Override
    public void create(Tag entity) {
        super.create(entity); //To change body of generated methods, choose Tools | Templates.
        tableUpdatedEvent.fire(entity);
    }


    // **** Added **** Added **** Added ****

    public Tag findByName(@NotNull String name) {
        Query q = em.createNamedQuery("Tag.findByName");
        q.setParameter("name", name);
        return getSingleResult(q);
    }

    private Tag getSingleResult(Query q) {
        try {
            Tag res = (Tag) q.getSingleResult();
            return res;
        } catch (NoResultException ex) {
            return null;    // just pass back null in this case
        } // any other exception will just be thrown upwards
    }
    
    public int getCountOfPosts(@NotNull Long id) {
        Query q = em.createNamedQuery("Tag.getCountOfPosts");
        q.setParameter("id", id);
        return (Integer) q.getSingleResult();
    }

}
