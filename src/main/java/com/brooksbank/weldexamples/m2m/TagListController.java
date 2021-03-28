/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brooksbank.weldexamples.m2m;

import com.brooksbank.weldexamples.db.Tag;
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
public class TagListController implements Serializable {

    private Tag selectedTag;

//    @Inject
//    private TagService tagService;
    
//    @Inject
//    private Logger log;

    /**
     * Creates a new instance of ManyToManyController
     */
    public TagListController() {
    }

    @PostConstruct
    protected void init() {
    }
    

    public Tag getSelectedTag() {
        return selectedTag;
    }

    public void setSelectedTag(Tag selectedTag) {
        this.selectedTag = selectedTag;
    }
    
    

    public void addAction() {
        invokeDialog("Add", null);
    }

    public void editAction() {
        invokeDialog("Edit", selectedTag);
    }

    public void deleteAction() {
        invokeDialog("Delete", selectedTag);
    }

    public void viewAction() {
        invokeDialog("View", selectedTag);
    }

    private void invokeDialog(String mode, Tag tag) {
        Map<String, Object> options = new HashMap<>();
        options.put("resizable", false);
        options.put("modal", true);

        Map<String, List<String>> params = new HashMap<>();
        params.put("mode", Arrays.asList(mode));
        if (tag != null) {
            params.put("tagid", Arrays.asList(tag.getId().toString()));
        }
        PrimeFaces.current().dialog().openDynamic("tagDialog", options, params);
    }

//    public void onAddReturn(SelectEvent event) {
//        if (event != null && event.getObject() != null) {
//            if (event.getObject() instanceof Tag) {
//                selectedTag = (Tag) event.getObject();
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
//            if (event.getObject() instanceof Tag) {
//                selectedTag = (Tag) event.getObject();
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
