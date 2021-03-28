/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brooksbank.weldexamples.example01;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author sjbro
 */
@Named
@RequestScoped
public class TranslateController {

    //private static final Logger LOG = Logger.getLogger(TranslateController.class.getName());

    /**
     * Creates a new instance of TranslateController
     */
    public TranslateController() {
    }

    @Inject
    TextTranslator textTranslator;
    // note: the warning "no-enabled-eligible-for-injection-beans-are-found" is spurious!

    private String inputText;
    private String translation;

    // JSF action method, perhaps
    public void translate() {
        //LOG.info("translating ... " + inputText);
        translation = textTranslator.translate(inputText);
        //LOG.info("got ... " + translation);
    }

    public String getInputText() {
        return inputText;
    }

    public void setInputText(String text) {
        this.inputText = text;
    }

    public String getTranslation() {
        return translation;
    }

}
