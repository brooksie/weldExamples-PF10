/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brooksbank.weldexamples.example11;

import com.brooksbank.weldexamples.db.ChangedTable;
import com.brooksbank.weldexamples.db.Codetypes;
import com.brooksbank.weldexamples.db.CodetypesService;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

/**
 *
 * @author sjbro
 */
@SessionScoped
public class CodetypesCatalog implements Serializable {
    
    @Inject
    private CodetypesService service;

    List<Codetypes> catalog = null;
    
    @Produces
    @Named
    @RequestScoped
    List<Codetypes> getCodetypesList() {
        if (catalog == null)  {
            updateCatalog();
        }
        return catalog;
    }
    
    private void updateCatalog() {
        catalog = service.findAll();
    }
    
    //public void refreshCatalog(@Observes(notifyObserver = Reception.IF_EXISTS, during = TransactionPhase.BEFORE_COMPLETION) @ChangedTable Codetypes ct) { 
    public void refreshCatalog(@Observes(notifyObserver = Reception.IF_EXISTS) @ChangedTable Codetypes ct) { 
        updateCatalog();
    }
    
}
