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
@FacesConverter(value = "myCondimentConverter", managed = true)
public class CondimentConverter implements Converter<Condiment> {

    @Inject
    private CondimentService condimentService;
    
    @Override
    public Condiment getAsObject(FacesContext fc, UIComponent uic, String condimentName) {
        return (isNullOrBlank(condimentName) ? null : condimentService.findByName(condimentName));
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Condiment t) {
        return (t == null) ? null : t.getName();
    }
    
    private static boolean isNullOrBlank(final String s) {
        return (s == null || s.isBlank());
    }

}
