/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brooksbank.weldexamples.example08;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

/**
 *
 * @author sjbro
 */
@SessionScoped
public class PaymentPreferenceProducer implements Serializable {

    private static final Logger LOG = Logger.getLogger(PaymentPreferenceProducer.class.getName());
    
    private int id;
    private PaymentStrategyType paymentStrategyType = PaymentStrategyType.NONE;
    
    @PostConstruct
    void init() {
        id = (int) (Math.random() * 1000000 - 0.5);
    }

    // The dependent scope means the producer method will be called every time 
    // the container injects this field or any other field that resolves 
    // to the same producer method. Thus, there could be multiple instances 
    // of the PaymentStrategy object for each user session. 
    // Strictly the @Dependent qualifier is unnecessary
    @Named(value = "preferredPaymentStrategy")
    @Produces @Preferred
    public PaymentStrategy getPaymentStrategy() {
        LOG.log(Level.INFO, "{0}: producing @Preferred PaymentStrategy for {1}", 
                new Object[] {id, paymentStrategyType});
        switch (paymentStrategyType) {
            case CREDIT_CARD: return new CreditCardPaymentStrategy();
            case CHEQUE: return new ChequePaymentStrategy();
            case PAYPAL: return new PayPalPaymentStrategy();
            case NONE: return new NonePaymentStrategy();
        }
        return null;    // will cause a runtime exception!
    }
    
    void close(@Disposes @Preferred PaymentStrategy strategy) {
        LOG.log(Level.INFO, "{0}: disposing @Preferred PaymentStrategy", id);
        //connection.close();
    }

    public PaymentStrategyType getPaymentStrategyType() {
        return paymentStrategyType;
    }

    public void setPaymentStrategyType(PaymentStrategyType paymentStrategyType) {
        this.paymentStrategyType = paymentStrategyType;
    }

}
