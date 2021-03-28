/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brooksbank.weldexamples.util;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 * A business method interceptor applies to invocations of methods of the bean
 * by clients of the bean.
 *
 * @author sjbro
 */
@Interceptor
@Debug           // binding the interceptor here. Now any method annotated @Debug will be intercepted by method
public class DebugInterceptor implements Serializable {

    @Inject
    private Logger log;
    
    @AroundInvoke
    public Object manageTransaction(InvocationContext ctx) throws Exception {
        log.log( Level.INFO, "Interceptor -> invoking {1} ({0})", 
                new Object[] {ctx.getMethod().getDeclaringClass().getName(), 
                    ctx.getMethod().getName()} );
        Object obj = ctx.proceed();
        log.log( Level.INFO, "Interceptor -> exited   {1} ({0})", 
                new Object[] {ctx.getMethod().getDeclaringClass().getName(), 
                    ctx.getMethod().getName()} );
        return obj;
    }

}
