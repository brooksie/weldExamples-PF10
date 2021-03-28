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

/**
 *
 * @author sjbro
 */
@Stateless
public class MessagelogService extends AbstractFacade<Messagelog> {

//    @PersistenceContext(unitName = "com.brooksbank_weldExamples_war_1.0PU")
//    private EntityManager em;
    @Inject
    @WeldDatabase
    private EntityManager em;
    
    @Inject
    @ChangedTable
    private Event<Messagelog> tableUpdatedEvent;

    @Inject
    Logger log;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MessagelogService() {
        super(Messagelog.class);
    }
    
    // **** Overrides *** Overrides *** Overrides *****

    @Override
    public void remove(Messagelog entity) {
        super.remove(entity); //To change body of generated methods, choose Tools | Templates.
        tableUpdatedEvent.fire(entity);
    }

    @Override
    public void edit(Messagelog entity) {
        super.edit(entity); //To change body of generated methods, choose Tools | Templates.
        tableUpdatedEvent.fire(entity);
    }

    @Override
    public void create(Messagelog entity) {
        super.create(entity); //To change body of generated methods, choose Tools | Templates.
        tableUpdatedEvent.fire(entity);
    }


}
