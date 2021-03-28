/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brooksbank.weldexamples.messaging;

import javax.inject.Named;
import javax.faces.view.ViewScoped;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TextMessage;

/**
 *
 * @author sjbro
 */
@Named
@ViewScoped
public class Example15ReceiveController implements Serializable {

    @Inject
    Logger log;

    @Inject
    private JMSContext injectedContext;

    @Inject @InboundMessageQueue
    private Queue queue2;

    private String inboundMessage;

    /**
     * Creates a new instance of Example15_syncController
     */
    public Example15ReceiveController() {
    }

    @PostConstruct
    protected void init() {
    }

    public String getInboundMessage() {
        return inboundMessage;
    }

    public void receiveMessage() {
        this.inboundMessage = "";   // empty out the received message field

        try {
            JMSConsumer consumer = injectedContext.createConsumer(queue2);
            Message inboundMsg = consumer.receiveNoWait();
            if (inboundMsg == null) {
                log.log(Level.INFO, "nothing waiting on <{0}> at <{1}>", 
                        new Object[] {queue2.toString(), LocalDateTime.now()} );
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "no message waiting", ""));
            } else {
                if (!(inboundMsg instanceof TextMessage)) {
                    throw new RuntimeException("inbound message is of unexpected class (" + inboundMsg.getClass().getName() + ")");
                }
                this.inboundMessage = ((TextMessage) inboundMsg).getText();
                log.log(Level.INFO, "received {0}", inboundMessage);
            }
        } catch (JMSException ex) {
            Logger.getLogger(Example15ReceiveController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
