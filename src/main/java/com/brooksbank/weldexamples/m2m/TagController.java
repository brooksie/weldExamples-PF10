/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brooksbank.weldexamples.m2m;

import com.brooksbank.weldexamples.db.Tag;
import com.brooksbank.weldexamples.db.TagService;
import java.io.Serializable;
import java.util.Map;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
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
public class TagController implements Serializable {

    @Inject
    private TagService service;

    @Inject
    private Logger log;
    
    @Inject 
    FacesContext facesContext;

    @Named
    @Produces
    @ViewScoped
    private Tag tagBean = new Tag();

    private String mode = "???";
    private boolean deleteActionDisabled = false;

    /**
     * Creates a new instance of TagController
     */
    public TagController() {
    }

    @PostConstruct
    public void init() {
        // first get the dlg parameters
        Map<String, String[]> paramMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterValuesMap();
        if (paramMap != null && !paramMap.isEmpty()) {

            String[] modeList = (String[]) paramMap.get("mode");
            mode = (modeList == null) ? null : modeList[0];

            String[] tagidList = (String[]) paramMap.get("tagid");
            if (tagidList != null) {
                tagBean = service.find(Long.parseLong(tagidList[0]));
            }
        }
        
        if (tagBean != null && "Delete".equals(mode)) {
            int numPosts = service.getCountOfPosts(tagBean.getId());
            if (numPosts != 0) {
                deleteActionDisabled = true;
                facesContext.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "Delete not possible",
                                "Tag has " + numPosts + " posts defined"
                        ));
            }
        }


    }

    public String getMode() {
        return mode;
    }

    public boolean isDeleteActionDisabled() {
        return deleteActionDisabled;
    }
    
    

    public void addTag() {
        service.create(tagBean);
        PrimeFaces.current().dialog().closeDynamic(tagBean);
    }

    public void editTag() {
        updatePosts();
        service.edit(tagBean);
        PrimeFaces.current().dialog().closeDynamic(tagBean);
    }

    private void updatePosts() {
        
        
        
//        Set<Tag> oldTagList = tagBean.getTags();
//        Set<Tag> newTagList = new HashSet<>(tagPicklist.getTarget());
//        Set<Tag> deleteList = new HashSet<>();
//        
//        // First - go through the existing list and check if the old tags 
//        // are still in the list. If not mark them for removal.
//        for ( Tag tag : oldTagList ) {
//            if ( !newTagList.contains(tag)) {
//                deleteList.add(tag);
//            }
//        }
//        // Now remove anything from the old set that needs to be removed.
//        //(Note: this is done as a separate list to avoid ConcurrentModificationExceptions)
//        for (Tag tag : deleteList) {
//            oldTagList.remove(tag);
//        }
//        
//        // Lastly - go through the new list and check if the new tag is already
//        // in the old list. If not, add it.
//        for (Tag tag : newTagList ) {
//            oldTagList.add(tag);    // this will only add new elements
//        }
//        // and oldTagList now has all the new tags
    }
    
    public void deleteTag() {
        // no need to worry abount posts as delete is not allowed when any posts exists for the tag
        service.remove(tagBean);
        PrimeFaces.current().dialog().closeDynamic(tagBean);
    }

    public void validateName(FacesContext context, UIComponent component, String value) {
        if (isNullOrBlank(value)) {
            return;     // ignore null values
        }

        Tag tag = service.findByName(value);
        if (mode.equalsIgnoreCase("Add")) {
            if (tag != null) {
                // tag already exists
                throw new ValidatorException(
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "tag already exists", "tag already exists"));
            }
        } else if (mode.equalsIgnoreCase("Edit")) {
            if (tag != null && !tag.equals(tagBean)) {
                // tag already exists
                throw new ValidatorException(
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "tag already exists", "tag already exists"));
            }
        }
    }

    private static boolean isNullOrBlank(String s) {
        return s == null || s.isBlank();
    }

}
