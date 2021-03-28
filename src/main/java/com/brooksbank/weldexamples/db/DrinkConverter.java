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
@FacesConverter(value = "myDrinkConverter", managed = true)
public class DrinkConverter implements Converter<Drink> {

    @Inject
    private DrinkService drinkService;
    
    @Override
    public Drink getAsObject(FacesContext fc, UIComponent uic, String drinkName) {
        return (isNullOrBlank(drinkName) ? null : drinkService.findByName(drinkName));
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Drink t) {
        return (t == null) ? null : t.getName();
    }
    
    private static boolean isNullOrBlank(final String s) {
        return (s == null || s.isBlank());
    }

}
