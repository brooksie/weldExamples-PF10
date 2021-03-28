/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brooksbank.weldexamples.messaging;

import javax.inject.Named;
import javax.faces.view.ViewScoped;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TextMessage;


/**
 *
 * @author sjbro
 */
@Named
@ViewScoped
public class Example15SyncController implements Serializable {

    @Inject
    Logger log;

    @Inject
    private JMSContext injectedContext;

    @Inject @OutboundMessageQueue
    private Queue queueExample;

    private String outboundMessage = "Hello, world!";
    private String inboundMessage;

    /**
     * Creates a new instance of Example15_syncController
     */
    public Example15SyncController() {
    }

    @PostConstruct
    protected void init() {
    }

    public String getOutboundMessage() {
        return outboundMessage;
    }

    public void setOutboundMessage(String outboundMessage) {
        this.outboundMessage = outboundMessage;
    }

    public String getInboundMessage() {
        return inboundMessage;
    }

    public void sendMessage() {
        this.inboundMessage = "";   // empty out the received message field
        try {
            JMSProducer producer = injectedContext.createProducer();
            
            Destination tempDest = injectedContext.createTemporaryQueue();
            
            TextMessage xmlMsg = injectedContext.createTextMessage(this.outboundMessage);
            xmlMsg.setJMSReplyTo(tempDest);
            producer.send(this.queueExample, xmlMsg);
            //updateDatabase(dbMessage, msgHdr);
            log.log(Level.INFO, "Message sent");
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Message sent"));
            
            JMSConsumer consumer = injectedContext.createConsumer(tempDest);
            
            Message inboundMsg = consumer.receive(5000);    // wait up to 5 seconds
            if (inboundMsg != null) {
                if (!(inboundMsg instanceof TextMessage)) {
                    throw new RuntimeException("inbound message is of unexpected class (" + inboundMsg.getClass().getName() + ")");
                }
                this.inboundMessage = ((TextMessage) inboundMsg).getText();
                log.log(Level.INFO, "received {0}", inboundMessage);
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Message received"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,"no message received",""));
            }
        } catch (JMSException ex) {
            Logger.getLogger(Example15SyncController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
