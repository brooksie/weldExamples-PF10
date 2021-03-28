/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brooksbank.weldexamples.util;

/**
 *
 * @author sjbro
 */
public enum DialogMode {
    ADD ("Add"),
    EDIT ("Edit"),
    DELETE ("Delete"),
    VIEW ("View");
    
    private final String displayName;
    
    private DialogMode(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDispayName() {
        return displayName;
    }
    
}
