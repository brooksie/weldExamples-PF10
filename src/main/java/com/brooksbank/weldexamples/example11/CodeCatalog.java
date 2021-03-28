/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brooksbank.weldexamples.example11;

import com.brooksbank.weldexamples.db.ChangedTable;
import com.brooksbank.weldexamples.db.Codes;
import com.brooksbank.weldexamples.db.CodesService;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

/**
 *
 * @author sjbro
 */
@SessionScoped
public class CodeCatalog implements Serializable {
    
    private static final String ALL_TYPES = "*";
    
    @Inject
    Logger log;
    
    @Inject
    private CodesService service;

    List<Codes> catalog = Collections.EMPTY_LIST;
    private String restrictedCodetype = "";

    private String previousCodetype = "";  
    
    @Produces
    @Named
    @RequestScoped
    List<Codes> getCodesList() {
        if (!previousCodetype.equals(restrictedCodetype))  {
            updateCatalog();
        }
        previousCodetype = restrictedCodetype;
        return catalog;
    }
    
    private void updateCatalog() {
        if ( isNullOrBlank(restrictedCodetype) || ALL_TYPES.equals(restrictedCodetype) ) {
            catalog = service.findAll();
        } else {
            catalog = service.findAllByCodetype(restrictedCodetype);
        }
    }
    
    //public void refreshCatalog(@Observes(notifyObserver = Reception.IF_EXISTS, 
    //         during = TransactionPhase.BEFORE_COMPLETION) @ChangedTable Codes ct) { 
    public void refreshCatalog(@Observes(notifyObserver = Reception.IF_EXISTS) @ChangedTable Codes ct) { 
        updateCatalog();
    }
    
    
    public String getRestrictedCodetype() {
        return restrictedCodetype;
    }

    public void setRestrictedCodetype(@NotNull String restrictedCodetype) {
        this.restrictedCodetype = restrictedCodetype;
    }

    private boolean isNullOrBlank(String s) {
        return s == null || s.isBlank();
    }

}
