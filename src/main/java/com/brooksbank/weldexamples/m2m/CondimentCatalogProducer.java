/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brooksbank.weldexamples.m2m;

import com.brooksbank.weldexamples.db.ChangedTable;
import com.brooksbank.weldexamples.db.Drink;
import com.brooksbank.weldexamples.db.Condiment;
import com.brooksbank.weldexamples.db.CondimentService;
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
@Named
@SessionScoped
public class CondimentCatalogProducer implements Serializable {
    
    @Inject
    private CondimentService condimentService;

    List<Condiment> catalog = null;
    
    @Produces
    @Named
    @RequestScoped
    List<Condiment> getCondimentCatalog() {
        if (catalog == null)  {
            updateCatalog();
        }
        return catalog;
    }
    
    private void updateCatalog() {
        //catalog = condimentService.findAll();
        //catalog = condimentService.findAllLeftJoinFetchDrinks();
        catalog = condimentService.findAllWithDrinks();
    }
    
    //public void refreshCatalog(@Observes(notifyObserver = Reception.IF_EXISTS, during = TransactionPhase.BEFORE_COMPLETION) @ChangedTable Condiment app) { 
    public void refreshCatalog(@Observes(notifyObserver = Reception.IF_EXISTS) @ChangedTable Condiment condiment) { 
        catalog = null;     // force a refresh next time the catalog is required
    }
    public void refreshCatalog(@Observes(notifyObserver = Reception.IF_EXISTS) @ChangedTable Drink drink) { 
        catalog = null;     // force a refresh next time the catalog is required
    }
    
}
