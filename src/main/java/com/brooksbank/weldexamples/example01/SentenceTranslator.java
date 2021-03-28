/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brooksbank.weldexamples.example01;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;

/**
 *
 * @author sjbro
 */
@Stateless
public class SentenceTranslator implements Translator {

    //private static final Logger LOG = Logger.getLogger(SentenceTranslator.class.getName());
    
    @Override
    public String translate(String sentence) {
        //LOG.log (Level.INFO, "translate() called for ...  {0}", sentence);
        String translated =  toCamelCase(sentence);
        //LOG.log (Level.INFO, "translate() created ...  {0}", translated);
        return translated;
        
    }

    private static String toCamelCase(String s) {
        // split the string up based on repeated (one or more) white spaces
        String[] parts = s.split("[_\\s]+");
        StringBuilder camelCaseString = new StringBuilder();
        for (String part : parts) {
            if ( camelCaseString.length() > 0) {
                // add a space separator
                camelCaseString.append(" ");
            }
            camelCaseString.append(toProperCase(part));
        }
        return camelCaseString.toString();
    }

    private static String toProperCase(String s) {
        if (s.isBlank()) return "";
        int sLen = s.length();
        return s.substring(0, 1).toLowerCase()
                + ((sLen > 1)?s.substring(1).toUpperCase():"");
    }
}
