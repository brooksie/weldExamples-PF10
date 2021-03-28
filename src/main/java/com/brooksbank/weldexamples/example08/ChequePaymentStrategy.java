/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brooksbank.weldexamples.example08;

/**
 *
 * @author sjbro
 */
public class ChequePaymentStrategy implements PaymentStrategy {
    
    private static final String TYPE = "CHEQUE";

    @Override
    public String getName() {
        return TYPE;
    }
    
}
