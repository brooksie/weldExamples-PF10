/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brooksbank.weldexamples.db;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

/**
 *
 * @author sjbro
 */
@FacesConverter(value = "myTagConverter", managed = true)
public class TagConverter implements Converter<Tag> {

    @Inject
    private TagService tagService;
    
    @Override
    public Tag getAsObject(FacesContext fc, UIComponent uic, String tagName) {
        return (isNullOrBlank(tagName) ? null : tagService.findByName(tagName));
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Tag t) {
        return (t == null) ? null : t.getName();
    }
    
    private static boolean isNullOrBlank(final String s) {
        return (s == null || s.isBlank());
    }

  
}
