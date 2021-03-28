/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brooksbank.weldexamples.db;

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
public class PostService extends AbstractFacade<Post> {

    @Inject
    @WeldDatabase
    private EntityManager em;

    @Inject
    @ChangedTable
    private Event<Post> postTableUpdatedEvent;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    

    public PostService() {
        super(Post.class);
    }

    // **** Overrides *** Overrides *** Overrides *****
    @Override
    public void remove(Post entity) {
        super.remove(entity); //To change body of generated methods, choose Tools | Templates.
        postTableUpdatedEvent.fire(entity);
    }

    @Override
    public void edit(Post entity) {
        super.edit(entity); //To change body of generated methods, choose Tools | Templates.
        postTableUpdatedEvent.fire(entity);
    }

    @Override
    public void create(Post entity) {
        super.create(entity); //To change body of generated methods, choose Tools | Templates.
        postTableUpdatedEvent.fire(entity);
    }


    // **** Added **** Added **** Added ****

    public Post findByTitle(@NotNull String title) {
        Query q = em.createNamedQuery("Post.findByTitle");
        q.setParameter("title", title);
        return getSingleResult(q);
    }

    private Post getSingleResult(Query q) {
        try {
            Post res = (Post) q.getSingleResult();
            return res;
        } catch (NoResultException ex) {
            return null;    // just pass back null in this case
        } // any other exception will just be thrown upwards
    }

}
