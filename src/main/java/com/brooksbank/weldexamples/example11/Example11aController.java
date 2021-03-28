/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brooksbank.weldexamples.example11;

import com.brooksbank.weldexamples.db.Codetypes;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;

/**
 *
 * @author sjbro
 */
@Named
@ViewScoped
public class Example11aController implements Serializable {

//    @Inject
//    private CodetypesService service;
    
    @Inject
    Logger log;
    
    @Inject
    List<Codetypes> codetypeList;
    
    private List<Codetypes> filteredCodetypeList;
    
    private Codetypes selectedCodetype;
    private final int fixedId;

    /**
     * Creates a new instance of Example11aController
     * Set the fixedID using a randomly generated integer
     */
    public Example11aController() {
        fixedId = (int) (Math.random()*1000000 - 0.5);  // 0 - 999,999
    }
    
    @PostConstruct
    public void init() {
        log.log(Level.INFO, "{0} postConstruct for Example11aController", fixedId);
//        setList();
    }

//    public void setList() {
//        //log.log(Level.INFO, "{0} setList() invoked", fixedId);
//        //codetypeList = service.findAll();
//    }
    
    public List<Codetypes> getCodetypeList() {
        return codetypeList;
    }

    public List<Codetypes> getFilteredCodetypeList() {
        return filteredCodetypeList;
    }

    public void setFilteredCodetypeList(List<Codetypes> filteredCodetypeList) {
        this.filteredCodetypeList = filteredCodetypeList;
    }

    public Codetypes getSelectedCodetype() {
        return selectedCodetype;
    }

    public void setSelectedCodetype(Codetypes selectedCodetype) {
        this.selectedCodetype = selectedCodetype;
    }

    
//    public void afterTableUpdate( @Observes(notifyObserver = Reception.IF_EXISTS) @ChangedTable Codetypes codetype) {
//    //public void afterTableUpdate( @Observes @ChangedTable Codetypes codetype) {
//        log.log(Level.INFO, "{0} afterTableUpdate() fired", fixedId);
//        setList();
//    }
    

    public void addAction() {
        invokeDialog("Add", null);
    }
    
    public void editAction() {
        invokeDialog("Edit", selectedCodetype);
    }
    
    public void deleteAction() {
        invokeDialog("Delete", selectedCodetype);
    }

    public void viewAction() {
        invokeDialog("View", selectedCodetype);
    }
    
    private void invokeDialog(String mode, Codetypes ct) {
        Map<String,Object> options = new HashMap<>();
        options.put("resizable", false);
        options.put("modal", true);
        
        Map<String,List<String>> params = new HashMap<>();
        params.put("mode", Arrays.asList(mode) );
        if ( ct != null) {
            params.put("codetype", Arrays.asList(ct.getCodetype()) );
        }
        PrimeFaces.current().dialog().openDynamic("example11a_dlg", options, params);
    }


//    public void onAddReturn(SelectEvent event) {
//        // nothing to do
//        log.log(Level.INFO, "{0} onAddReturn(event) invoked: {1}", 
//                new Object[]{fixedId, (event == null || event.getObject() == null 
//                        || !(event.getObject() instanceof Codetypes))? "null" : ((Codetypes)event.getObject()).getCodetype()});
//        setList();
//    }
//
//    public void onEditReturn(SelectEvent event) {
//        log.log(Level.INFO, "{0} onEditReturn(event) invoked: {1}", 
//                new Object[]{fixedId, (event == null || event.getObject() == null 
//                        || !(event.getObject() instanceof Codetypes))? "null" : ((Codetypes)event.getObject()).getCodetype()});
//        setList();
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
