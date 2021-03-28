/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brooksbank.weldexamples.example09;

import javax.inject.Named;
import javax.enterprise.context.Dependent;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 *
 * @author sjbro
 */
@Named
@Dependent
@Log                // use logging service at TYPE level (meaning all method calls)
@LogConstruction    // also log the construction of the bean
public class DummyService1 implements Serializable {

    private static final Logger LOG = Logger.getLogger(DummyService1.class.getName());

    
    
    private int id;

    @PostConstruct
    void init() {
        // do something
        id = (int) (Math.random() * 1000 - 0.5);
        LOG.log(Level.INFO, "Constructing {0}: {1}", 
                new Object[]{id, DummyService1.class.getName()});
    }

    public void doSomething() {
        // something should happen here
    }

    public String doSomethingElse(String str) {
        // something should happen here
        return Integer.toBinaryString(id) + ": " + str;
    }

    // there seems to be some issue about when PreDestroy gets called
    // At the moment I don't see any evidence of it being called, and 
    // I'm not sure why.
    @PreDestroy
    void destroy() {
        LOG.log(Level.INFO, "Destroying {0}: {1}", 
                new Object[]{id, DummyService1.class.getName()});
        id = -1;
    }

}
