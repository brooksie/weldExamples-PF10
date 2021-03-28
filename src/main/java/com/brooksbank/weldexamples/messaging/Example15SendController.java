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
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.jms.TextMessage;


/**
 *
 * @author sjbro
 */
@Named
@ViewScoped
public class Example15SendController implements Serializable {

    @Inject
    Logger log;

    @Inject
    private JMSContext injectedContext;

    @Inject @OutboundMessageQueue
    private Queue queue1;

    private String outboundMessage = "Hello, world!";
    private String confirmationMessage;

    /**
     * Creates a new instance of Example15_syncController
     */
    public Example15SendController() {
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

    public String getConfirmationMessage() {
        return confirmationMessage;
    }

    public void sendMessage() {
        this.confirmationMessage = "";   // empty out the received message field
        try {
            JMSProducer producer = injectedContext.createProducer();
            TextMessage xmlMsg = injectedContext.createTextMessage(this.outboundMessage);
            producer.send(this.queue1, xmlMsg);
            //updateDatabase(dbMessage, msgHdr);
            this.confirmationMessage = "Message sent (" + xmlMsg.getText() + ")";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(confirmationMessage));
            
        } catch (JMSException ex) {
            Logger.getLogger(Example15SendController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
