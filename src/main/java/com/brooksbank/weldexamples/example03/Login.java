/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brooksbank.weldexamples.example03;

import com.brooksbank.weldexamples.db.Users;
import com.brooksbank.weldexamples.db.UsersService;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author sjbro
 */
@Named
@SessionScoped
public class Login implements Serializable {

    private static final Logger LOG = Logger.getLogger(Login.class.getName());

    @Inject
    Credentials credentials;

    @Inject
    private UsersService usersService;
    
    @Inject
    private FacesContext facesContext;
    
    private Users user;

    // constructor!!!
    public Login() {
    }

    // public method!!!
    public void login() {

        user = usersService.authenticate(credentials.getUsername(), credentials.getPassword());
        if ( !isLoggedIn() ) {
            LOG.info("User does not exist and/or password not matched ");
            facesContext.addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "Invalid username and/or password", null));
        } else {
            LOG.log(Level.INFO, "Signed in ok - current user is {0}", getCurrentUser().getUsername());
            credentials.clearPassword();
        }
    }

    public void logout() {
        user = null;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    @Produces
    @Named
    @LoggedIn
    Users getCurrentUser() {
        return user;
    }

}
