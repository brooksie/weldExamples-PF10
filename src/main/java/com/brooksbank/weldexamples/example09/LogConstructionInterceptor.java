/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brooksbank.weldexamples.example09;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 * A lifecycle callback interceptor applies to invocations of lifecycle
 * callbacks by the container.
 *
 * @AroundConstruct, @PostConstruct, @PreDestroy
 * @author sjbro
 */
@Interceptor
@LogConstruction
public class LogConstructionInterceptor implements Serializable {

    private static final Logger LOG = Logger.getLogger(LogConstructionInterceptor.class.getName());

    @Inject
    MyLoggerBean logBean;
    
    @PostConstruct
    public void injectDependencies(InvocationContext ctx) {
        String objectFullName = ctx.getTarget().getClass().getName();
        String shortName = objectFullName.substring(0, objectFullName.indexOf("$"));
        logBean.add( "ready for manipulation: {0} ", shortName );
        try {
            ctx.proceed();
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }
    
    @PreDestroy
    public void destoryBean(InvocationContext ctx) {
        String objectFullName = ctx.getTarget().getClass().getName();
        String shortName = objectFullName.substring(0, objectFullName.indexOf("$"));
        logBean.add( "destroying: {0} ", shortName );
        try {
            ctx.proceed();
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }
    
    
}
