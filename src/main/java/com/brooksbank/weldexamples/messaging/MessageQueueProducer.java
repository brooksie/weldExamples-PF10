/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.brooksbank.weldexamples.messaging;

import javax.enterprise.context.ApplicationScoped;
import java.io.Serializable;
import javax.annotation.Resource;
import javax.enterprise.inject.Produces;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;
    

/**
 *
 * @author sjbro
 */
@ApplicationScoped
public class MessageQueueProducer implements Serializable {

    @Produces
    @OutboundMessageQueue
    @Resource(lookup = "java:/jms/queue/SJB-JMSQueue")
    private Queue outboundMessageQueue;

    @Produces
    @InboundMessageQueue
    @Resource(lookup = "java:/jms/queue/SJB-JMSQueue2")
    private Queue inboundMessageQueue;

//    @Produces
//    public JMSProducer producer (JMSContext context) {
//        return context.createProducer();
//    }
//    
//    @Produces
//    public JMSConsumer consumer (JMSContext context, 
//            @InboundMessageQueue Queue inboundMsgQueue) {
//        return context.createConsumer(inboundMsgQueue);
//    }
    
}
