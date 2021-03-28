/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.brooksbank.weldexamples.example09;

import javax.inject.Named;
import java.io.Serializable;
import java.text.MessageFormat;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
    
/**
 *
 * @author sjbro
 */
@Named
@SessionScoped
public class MyLoggerBean implements Serializable {

    private StringBuilder builder;
    
    /** Creates a new instance of LogBean */
    public MyLoggerBean() {
    }
    
    @PostConstruct
    void init() {
        builder = new StringBuilder();
    }
    
    public void add(String s) {
        builder.append(s).append('\n');
    }
    
    public void add(String pattern, Object ... arguments) {
        // this variant is equivalent to 
        // add(MessageFormat.format(pattern, Object ... arguments);
        MessageFormat fmt = new MessageFormat(pattern);
        StringBuffer buff = fmt.format(arguments, new StringBuffer(), null);
        this.add(buff.toString());
    }
    
    public void clearLog() {
        builder.delete(0, builder.length());
    }

    public String getLog() {
        return builder.toString();
    }

}
