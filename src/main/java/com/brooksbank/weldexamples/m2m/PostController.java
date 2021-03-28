/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brooksbank.weldexamples.m2m;

import com.brooksbank.weldexamples.db.Post;
import com.brooksbank.weldexamples.db.PostService;
import com.brooksbank.weldexamples.db.Tag;
import com.brooksbank.weldexamples.db.TagService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
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
import org.primefaces.model.DualListModel;

/**
 *
 * @author sjbro
 */
@Named
@ViewScoped
public class PostController implements Serializable {

    @Inject
    private PostService service;

    @Inject
    private TagService tagService;

    @Inject
    private Logger log;

    @Named
    @Produces
    @ViewScoped
    private Post postBean = new Post();

    private String mode = "???";

    private DualListModel<Tag> tagPicklist;

    /**
     * Creates a new instance of PostController
     */
    public PostController() {
    }

    @PostConstruct
    public void init() {
        // first get the dlg parameters
        Map<String, String[]> paramMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterValuesMap();
        if (paramMap != null && !paramMap.isEmpty()) {

            String[] modeList = (String[]) paramMap.get("mode");
            mode = (modeList == null) ? null : modeList[0];

            String[] postidList = (String[]) paramMap.get("postid");
            if (postidList != null) {
                postBean = service.find(Long.parseLong(postidList[0]));
            }
        }

        List<Tag> source = new ArrayList<>();

        tagService.findAll().forEach(tag -> {
            source.add(tag);
        });

        List<Tag> target = new ArrayList<>();
        if (postBean != null) {
            postBean.getTags().forEach(tag -> {
                // remove tag from source list and (if successful) add to target list
                if (source.remove(tag)) {
                    target.add(tag);
                } else {
                    log.log(Level.WARNING,
                            "Existing tag ({0}) not found in full list of tags for post {1}. Existing "
                            + "tag has been ignored and not included in the target list",
                            new Object[]{tag, postBean});
                }
            });
        }

        tagPicklist = new DualListModel<>(source, target);
    }

    public String getMode() {
        return mode;
    }

    public DualListModel<Tag> getTagPicklist() {
        return tagPicklist;
    }

    public void setTagPicklist(DualListModel<Tag> tagPicklist) {
        this.tagPicklist = tagPicklist;
    }

    public void addPost() {
        //updateTags();
        service.create(postBean);
        updateTags();
        service.edit(postBean);
        PrimeFaces.current().dialog().closeDynamic(postBean);
    }

    private void updateTags() {
        Set<Tag> oldTagList = postBean.getTags();
        Set<Tag> newTagList = new HashSet<>(tagPicklist.getTarget());
        Set<Tag> deleteList = new HashSet<>();
        
        // First - go through the existing list and check if the old tags 
        // are still in the list. If not mark them for removal.
        for ( Tag tag : oldTagList ) {
            if ( !newTagList.contains(tag)) {
                deleteList.add(tag);
            }
        }
        // Now remove anything from the old set that needs to be removed.
        //(Note: this is done as a separate list to avoid ConcurrentModificationExceptions)
        for (Tag tag : deleteList) {
            oldTagList.remove(tag);
        }
        
        // Lastly - go through the new list and check if the new tag is already
        // in the old list. If not, add it.
        for (Tag tag : newTagList ) {
            oldTagList.add(tag);    // this will only add new elements
        }
        // and oldTagList now has all the new tags
    }
    
    public void editPost() {
        updateTags();
        service.edit(postBean);
        PrimeFaces.current().dialog().closeDynamic(postBean);
    }

    public void deletePost() {
        service.remove(postBean);
        PrimeFaces.current().dialog().closeDynamic(postBean);
    }

    public void validateTitle(FacesContext context, UIComponent component, String value) {
        if (isNullOrBlank(value)) {
            return;     // ignore null values
        }

        Post post = service.findByTitle(value);
        if (mode.equalsIgnoreCase("Add")) {
            if (post != null) {
                // post already exists
                throw new ValidatorException(
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "post already exists", "post already exists"));
            }
        } else if (mode.equalsIgnoreCase("Edit")) {
            if (post != null && !post.equals(postBean)) {
                // post already exists
                throw new ValidatorException(
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "post already exists", "post already exists"));
            }
        }
    }

    private static boolean isNullOrBlank(String s) {
        return s == null || s.isBlank();
    }

}
