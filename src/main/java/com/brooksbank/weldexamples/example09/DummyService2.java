/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.brooksbank.weldexamples.example09;

import javax.inject.Named;
import javax.enterprise.context.Dependent;
import java.io.Serializable;
import javax.annotation.PostConstruct;

/**
 *
 * @author sjbro
 */
@Named
@Dependent
public class DummyService2 implements Serializable {

    private int xx;
    
    /** Creates a new instance of DummyService2 */
    public DummyService2() {
    }

    @PostConstruct
    private void init() {
        // do something
        xx = (int) (Math.random() * 1000 - 0.5);

    }
    
    public String doSomething() {
        // something should happen here
        return Integer.toString(xx);
    }
    
    @Log        // just log this method being invoked
    public String doSomethingElse( String str) {
        // something should happen here
        return str;
    }
    
}
