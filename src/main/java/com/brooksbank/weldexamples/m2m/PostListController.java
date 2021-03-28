/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brooksbank.weldexamples.m2m;

import com.brooksbank.weldexamples.db.Post;
import com.brooksbank.weldexamples.db.PostService;
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
public class PostListController implements Serializable {

    private Post selectedPost;

//    @Inject
//    private PostService postService;
    
    @Inject
    private Logger log;

    /**
     * Creates a new instance of ManyToManyController
     */
    public PostListController() {
    }

    @PostConstruct
    protected void init() {
    }
    

    public Post getSelectedPost() {
        return selectedPost;
    }

    public void setSelectedPost(Post selectedPost) {
        this.selectedPost = selectedPost;
    }
    
    

    public void addAction() {
        invokeDialog("Add", null);
    }

    public void editAction() {
        invokeDialog("Edit", selectedPost);
    }

    public void deleteAction() {
        invokeDialog("Delete", selectedPost);
    }

    public void viewAction() {
        invokeDialog("View", selectedPost);
    }

    private void invokeDialog(String mode, Post post) {
        Map<String, Object> options = new HashMap<>();
        options.put("resizable", false);
        options.put("modal", true);

        Map<String, List<String>> params = new HashMap<>();
        params.put("mode", Arrays.asList(mode));
        if (post != null) {
            params.put("postid", Arrays.asList(post.getId().toString()));
        }
        PrimeFaces.current().dialog().openDynamic("postDialog", options, params);
    }

    public void onAddReturn(SelectEvent event) {
        if (event != null && event.getObject() != null) {
            if (event.getObject() instanceof Post) {
                selectedPost = (Post) event.getObject();
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
            if (event.getObject() instanceof Post) {
                selectedPost = (Post) event.getObject();
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
