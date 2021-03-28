/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brooksbank.weldexamples.m2m;

import com.brooksbank.weldexamples.db.Condiment;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.primefaces.PrimeFaces;

/**
 *
 * @author sjbro
 */
@Named
@ViewScoped
public class CondimentListController implements Serializable {

    private Condiment selectedCondiment;

//    @Inject
//    private CondimentService condimentService;
    
//    @Inject
//    private Logger log;

    /**
     * Creates a new instance of ManyToManyController
     */
    public CondimentListController() {
    }

    @PostConstruct
    protected void init() {
    }
    

    public Condiment getSelectedCondiment() {
        return selectedCondiment;
    }

    public void setSelectedCondiment(Condiment selectedCondiment) {
        this.selectedCondiment = selectedCondiment;
    }
    
    

    public void addAction() {
        invokeDialog("Add", null);
    }

    public void editAction() {
        invokeDialog("Edit", selectedCondiment);
    }

    public void deleteAction() {
        invokeDialog("Delete", selectedCondiment);
    }

    public void viewAction() {
        invokeDialog("View", selectedCondiment);
    }

    private void invokeDialog(String mode, Condiment condiment) {
        Map<String, Object> options = new HashMap<>();
        options.put("resizable", false);
        options.put("modal", true);

        Map<String, List<String>> params = new HashMap<>();
        params.put("mode", Arrays.asList(mode));
        if (condiment != null) {
            params.put("condimentid", Arrays.asList(condiment.getId().toString()));
        }
        PrimeFaces.current().dialog().openDynamic("condimentDialog", options, params);
    }

//    public void onAddReturn(SelectEvent event) {
//        if (event != null && event.getObject() != null) {
//            if (event.getObject() instanceof Condiment) {
//                selectedCondiment = (Condiment) event.getObject();
//                // *******************
//                editAction();
//                // *******************
//            } else {
//                log.log(Level.INFO, "dialog returned object is of type {0}", 
//                        event.getObject().getClass().getName());
//            }
//        } else {
//            log.info("dialog returned nothing");
//        }
//    }

//    public void onEditReturn(SelectEvent event) {
//        if (event != null && event.getObject() != null) {
//            if (event.getObject() instanceof Condiment) {
//                selectedCondiment = (Condiment) event.getObject();
//                // *******************
//                //editAction();
//                // *******************
//            } else {
//                log.log(Level.INFO, "dialog returned object is of type {0}", 
//                        event.getObject().getClass().getName());
//            }
//        } else {
//            log.info("dialog returned nothing");
//        }
//    }
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
