/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brooksbank.weldexamples.example08;

import java.io.Serializable;

/**
 *
 * @author sjbro
 */
public interface PaymentStrategy extends Serializable {
    public String getName();
}
