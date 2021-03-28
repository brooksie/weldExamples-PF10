/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brooksbank.weldexamples.m2m;

import com.brooksbank.weldexamples.db.Drink;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author sjbro
 */
@Named
@ViewScoped
public class DrinkListController implements Serializable {

    private Drink selectedDrink;

    @Inject
    private Logger log;

    /**
     * Creates a new instance of ManyToManyController
     */
    public DrinkListController() {
    }

    @PostConstruct
    protected void init() {
    }
    

    public Drink getSelectedDrink() {
        return selectedDrink;
    }

    public void setSelectedDrink(Drink selectedDrink) {
        this.selectedDrink = selectedDrink;
    }
    
    

    public void addAction() {
        invokeDialog("Add", null);
    }

    public void editAction() {
        invokeDialog("Edit", selectedDrink);
    }

    public void deleteAction() {
        invokeDialog("Delete", selectedDrink);
    }

    public void viewAction() {
        invokeDialog("View", selectedDrink);
    }

    private void invokeDialog(String mode, Drink drink) {
        Map<String, Object> options = new HashMap<>();
        options.put("resizable", false);
        options.put("modal", true);

        Map<String, List<String>> params = new HashMap<>();
        params.put("mode", Arrays.asList(mode));
        if (drink != null) {
            params.put("drinkid", Arrays.asList(drink.getId().toString()));
        }
        PrimeFaces.current().dialog().openDynamic("drinkDialog", options, params);
    }

    public void onAddReturn(SelectEvent event) {
        if (event != null && event.getObject() != null) {
            if (event.getObject() instanceof Drink) {
                selectedDrink = (Drink) event.getObject();
                // *******************
                editAction();
                // *******************
            } else {
                log.log(Level.INFO, "dialog returned object is of type {0}", 
                        event.getObject().getClass().getName());
            }
        } else {
            log.info("dialog returned nothing");
        }
    }

    public void onEditReturn(SelectEvent event) {
        if (event != null && event.getObject() != null) {
            if (event.getObject() instanceof Drink) {
                selectedDrink = (Drink) event.getObject();
                // *******************
                //editAction();
                // *******************
            } else {
                log.log(Level.INFO, "dialog returned object is of type {0}", 
                        event.getObject().getClass().getName());
            }
        } else {
            log.info("dialog returned nothing");
        }
    }
//
//    
//    public void onDeleteReturn(SelectEvent event) {
//        log.log(Level.INFO, "{0} onDeleteReturn(event) invoked: {1}", 
//                new Object[]{fixedId, (event == null || event.getObject() == null 
//                        || !(event.getObject() instanceof Codetypes))? "null" : ((Codetypes)event.getObject()).getCodetype()});
//        setList();
//    }
//
//    public void refreshAction() {
//        log.log(Level.INFO, "{0} refreshAction() invoked", fixedId);
//        setList();
//    }

}
