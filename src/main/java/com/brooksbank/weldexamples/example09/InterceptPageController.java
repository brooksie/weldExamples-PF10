/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.brooksbank.weldexamples.example09;

import javax.inject.Named;
import javax.faces.view.ViewScoped;
    
import java.io.Serializable;
import javax.inject.Inject;
    

/**
 *
 * @author sjbro
 */
@Named
@ViewScoped
@LogConstruction
public class InterceptPageController implements Serializable {

    @Inject
    DummyService1 service1;
    @Inject
    DummyService2 service2;
    @Inject
    MyLoggerBean logBean;

    /** Creates a new instance of InterceptorController */
    public InterceptPageController() {
    }
    
    @Log
    public void doIt() {
        logBean.add("doIt()");
        service1.doSomething();
        service1.doSomethingElse("hello1");
        service2.doSomething();
        service2.doSomethingElse("Hello2");
    }
    
    public void clear() {
        logBean.clearLog();
    }
    
}
