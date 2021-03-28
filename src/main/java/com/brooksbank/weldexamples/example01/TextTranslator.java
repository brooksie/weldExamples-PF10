/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brooksbank.weldexamples.example01;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

/**
 *
 * @author sjbro
 */
public class TextTranslator {

    //private static final Logger LOG = Logger.getLogger(TextTranslator.class.getName());

    private final SentenceParser sentenceParser;
    private final Translator sentenceTranslator;

    @Inject         // this makes this a CDI bean!!!
    TextTranslator(SentenceParser sentenceParser, Translator sentenceTranslator) {
        //LOG.info("TextTranslator object created");
        this.sentenceParser = sentenceParser;
        this.sentenceTranslator = sentenceTranslator;
    }

    public String translate(String text) {
        //LOG.log(Level.INFO, "TextTranslator.translate() called for string ... {0}", text);
        StringBuilder sb = new StringBuilder();
        
        List<String> sentences = sentenceParser.parse(text);
        //LOG.log(Level.INFO, "TextTranslator.translate() received {0} strings from parse()", 
        //        sentences.isEmpty()?"<empty>" : sentences.size());
        
        for (String sentence : sentences) {
            if (!sentence.isBlank()) {
                String translation = sentenceTranslator.translate(sentence);
                sb.append(translation);
                sb.append(".||");
            }
        }

        //LOG.log(Level.INFO, "TextTranslator.translate() returns ... {0}", sb.toString());
        return sb.toString();
    }
    
}
