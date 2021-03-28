/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brooksbank.weldexamples.example01;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author sjbro
 */
public class SentenceParser {
    
    public List<String> parse(String text) { 
        
        //separate into sentance. Look for a full stop followed by zero or more white space
        String[] splitStrings = text.split("[\\s]*\\.[\\.\\s]*");
        
        return Arrays.asList(splitStrings);
    }
    
}
