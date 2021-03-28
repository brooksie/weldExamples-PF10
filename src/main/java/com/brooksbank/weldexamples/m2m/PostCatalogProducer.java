/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brooksbank.weldexamples.m2m;

import com.brooksbank.weldexamples.db.ChangedTable;
import com.brooksbank.weldexamples.db.Post;
import com.brooksbank.weldexamples.db.PostService;
import com.brooksbank.weldexamples.db.Tag;
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
public class PostCatalogProducer implements Serializable {
    
    @Inject
    private PostService postService;

    List<Post> catalog = null;
    
    @Produces
    @Named
    @RequestScoped
    List<Post> getPostCatalog() {
        if (catalog == null)  {
            updateCatalog();
        }
        return catalog;
    }
    
    private void updateCatalog() {
        catalog = postService.findAll();
    }
    
    //public void refreshCatalog(@Observes(notifyObserver = Reception.IF_EXISTS, during = TransactionPhase.BEFORE_COMPLETION) @ChangedTable Post app) { 
    public void refreshCatalog(@Observes(notifyObserver = Reception.IF_EXISTS) @ChangedTable Post post) { 
        catalog = null;  // force the catalog to be refreshed next time it is required
    }
    public void refreshCatalog(@Observes(notifyObserver = Reception.IF_EXISTS) @ChangedTable Tag tag) { 
        catalog = null;  // force the catalog to be refreshed next time it is required
    }
    
}
