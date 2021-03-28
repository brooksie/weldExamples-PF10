/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brooksbank.weldexamples.db;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author sjbro
 */
@ApplicationScoped
public class WeldDatabaseProducer {

    @Produces
    @WeldDatabase
    @PersistenceContext(unitName = "com.brooksbank_weldExamples_war_1.0PU")
//    @PersistenceContext
//    static EntityManager userDatabase;
    private EntityManager entityManager;

}
