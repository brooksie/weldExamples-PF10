/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brooksbank.weldexamples.example11;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author sjbro
 */
@Named
@RequestScoped
public class Example11Controller {

    @Inject
    private FacesContext context;

    /**
     * Creates a new instance of Example11Controller
     */
    public Example11Controller() {
    }
    

    public void doAction(String s) {
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, s, null));
    }

}
