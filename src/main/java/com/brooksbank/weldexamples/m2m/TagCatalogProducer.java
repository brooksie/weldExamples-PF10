/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brooksbank.weldexamples.m2m;

import com.brooksbank.weldexamples.db.ChangedTable;
import com.brooksbank.weldexamples.db.Post;
import com.brooksbank.weldexamples.db.Tag;
import com.brooksbank.weldexamples.db.TagService;
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
public class TagCatalogProducer implements Serializable {
    
    @Inject
    private TagService tagService;

    List<Tag> catalog = null;
    
    @Produces
    @Named
    @RequestScoped
    List<Tag> getTagCatalog() {
        if (catalog == null)  {
            updateCatalog();
        }
        return catalog;
    }
    
    private void updateCatalog() {
        catalog = tagService.findAll();
    }
    
    //public void refreshCatalog(@Observes(notifyObserver = Reception.IF_EXISTS, during = TransactionPhase.BEFORE_COMPLETION) @ChangedTable Tag app) { 
    public void refreshCatalog(@Observes(notifyObserver = Reception.IF_EXISTS) @ChangedTable Tag tag) { 
        catalog = null;     // force a refresh next time the catalog is required
    }
    public void refreshCatalog(@Observes(notifyObserver = Reception.IF_EXISTS) @ChangedTable Post post) { 
        catalog = null;     // force a refresh next time the catalog is required
    }
    
}
