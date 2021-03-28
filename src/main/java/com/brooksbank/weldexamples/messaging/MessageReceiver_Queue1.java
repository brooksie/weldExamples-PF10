/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brooksbank.weldexamples.messaging;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.TextMessage;

/**
 *
 * @author sjbro
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/queue/SJB-JMSQueue"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class MessageReceiver_Queue1 implements MessageListener {

    @Inject
    private Logger log;

    @Inject
    private JMSContext injectedContext;
    
    @Inject @InboundMessageQueue
    private Queue queue2;

    @Override
    public void onMessage(Message msg) {

        try {

            if (!(msg instanceof TextMessage)) {
                String errMsg = MessageFormat.format(
                        "onMessage: message class ({0}) not recognised",
                        ((msg == null) ? "<null>" : msg.getClass().getName()));
                throw new RuntimeException(errMsg);
            }

            TextMessage txtMsg = (TextMessage) msg;
            try {
                log.log(Level.INFO, "OnMessage() received: {0}", txtMsg.getText());
            } catch (JMSException ex) {
                log.log(Level.SEVERE, null, ex);
            }

            Destination newDest = msg.getJMSReplyTo();
            if (newDest == null) {
                newDest = queue2;
            }
            
            String newStr = "Received:\"" + txtMsg.getText() + "\" on "
                    + LocalDateTime.now().toString() + ". Thank-you.";
            TextMessage newMsg = injectedContext.createTextMessage(newStr);
            newMsg.setJMSCorrelationID(msg.getJMSCorrelationID());
            JMSProducer producer = injectedContext.createProducer();
            producer.send(newDest, newMsg);

            log.log(Level.INFO, "OnMessage() sent: <{0}> to <{1}>", 
                    new Object[] {newMsg.getText(), newDest.toString() } );

        } catch (JMSException ex) {
            Logger.getLogger(MessageReceiver_Queue1.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
