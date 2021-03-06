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

/**
 *
 * @author sjbro
 */
@FacesConverter(value = "charArrayConverter")
public class CharArrayConverter implements Converter<char[]> {

    @Override
    public char[] getAsObject(FacesContext fc, UIComponent uic, String string) {
        return isNullOrEmpty(string) ? null : string.toCharArray();
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, char[] t) {
        return (t == null) ? null : String.valueOf(t);
    }
    
    private static boolean isNullOrEmpty(final String s) {
        return (s == null || s.isEmpty());
    }
  
}
