/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brooksbank.weldexamples.m2m;

import com.brooksbank.weldexamples.db.ChangedTable;
import com.brooksbank.weldexamples.db.Drink;
import com.brooksbank.weldexamples.db.DrinkService;
import com.brooksbank.weldexamples.db.Condiment;
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
public class DrinkCatalogProducer implements Serializable {
    
    @Inject
    private DrinkService drinkService;

    List<Drink> catalog = null;
    
    @Produces
    @Named
    @RequestScoped
    List<Drink> getDrinkCatalog() {
        if (catalog == null)  {
            updateCatalog();
        }
        return catalog;
    }
    
    private void updateCatalog() {
        //catalog = drinkService.findAll();
        //catalog = drinkService.findAllLJF();
        catalog = drinkService.findAllWithEG();
    }
    
    //public void refreshCatalog(@Observes(notifyObserver = Reception.IF_EXISTS, during = TransactionPhase.BEFORE_COMPLETION) @ChangedTable Drink app) { 
    public void refreshCatalog(@Observes(notifyObserver = Reception.IF_EXISTS) @ChangedTable Drink drink) { 
        catalog = null;  // force the catalog to be refreshed next time it is required
    }
    public void refreshCatalog(@Observes(notifyObserver = Reception.IF_EXISTS) @ChangedTable Condiment condiment) { 
        catalog = null;  // force the catalog to be refreshed next time it is required
    }
    
}
