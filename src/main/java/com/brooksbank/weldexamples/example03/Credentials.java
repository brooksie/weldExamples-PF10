/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brooksbank.weldexamples.example03;

import java.util.Arrays;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author sjbro
 */
@Named
@RequestScoped
public class Credentials {

    @NotNull
    @Size(min = 3, max = 25)
    private String username;

    @NotNull
    @Size(min = 6, max = 20)
    private char[] password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }
    
    public void clearPassword() {
        Arrays.fill(password, ' ');
    }

}
