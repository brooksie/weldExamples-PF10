/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.brooksbank.weldexamples.example08;

import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
    

/**
 *
 * @author sjbro
 */
@Named
@ViewScoped
public class PaymentSelectionController implements Serializable {

    private static final Logger LOG = Logger.getLogger(PaymentSelectionController.class.getName());

//    @Inject @Preferred 
//    PaymentStrategy paymentStrategy;
//    
//    @Inject @Preferred2 
//    PaymentStrategy paymentStrategy2;
    
    @Inject 
    PaymentPreferenceProducer producer;
    @Inject 
    PaymentPreferenceProducer_v2 producer2;
    
    private PaymentStrategyType selectedPaymentStrategyType;
    private final PaymentStrategyType[] listOfTypes = PaymentStrategyType.values();
    
    /** Creates a new instance of PaymentSelectionController */
    public PaymentSelectionController() {
    }

    public PaymentStrategyType[] getListOfTypes() {
        return listOfTypes;
    }
    
    public PaymentStrategyType getSelectedPaymentStrategyType() {
        return selectedPaymentStrategyType;
    }

    public void setSelectedPaymentStrategyType(PaymentStrategyType selectedPaymentStrategyType) {
        this.selectedPaymentStrategyType = selectedPaymentStrategyType;
    }
    
    public void paymentStrategyTypeChanged() {
        LOG.log(Level.INFO, "changed payment strategy is {0}", selectedPaymentStrategyType);
        if ( selectedPaymentStrategyType != null ) {
            producer.setPaymentStrategyType(selectedPaymentStrategyType);
            producer2.setPaymentStrategyType(selectedPaymentStrategyType);
        }
    }

}
