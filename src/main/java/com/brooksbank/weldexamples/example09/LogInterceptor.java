/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brooksbank.weldexamples.example09;

import java.io.Serializable;
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
@Log            // binding the interceptor here. Now any method annotated @Log will be intercepted by method
public class LogInterceptor implements Serializable {

    @Inject
    MyLoggerBean logBean;
    
    @AroundInvoke
    public Object manageTransaction(InvocationContext ctx) throws Exception {
        logBean.add("Interceptor -> invoking {1} ({0})", 
                ctx.getMethod().getDeclaringClass().getName(), ctx.getMethod().getName() );
        Object obj = ctx.proceed();
        logBean.add("Interceptor -> returned from {1} ({0})", 
                ctx.getMethod().getDeclaringClass().getName(), ctx.getMethod().getName());
        return obj;
    }

}
