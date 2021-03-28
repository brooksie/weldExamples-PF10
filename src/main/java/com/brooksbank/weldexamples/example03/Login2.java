/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brooksbank.weldexamples.example03;

import com.brooksbank.weldexamples.db.Users2;
import com.brooksbank.weldexamples.db.Users2Service;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;

/**
 *
 * @author sjbro
 */
@Named
@SessionScoped
public class Login2 implements Serializable {

    private static final Logger LOG = Logger.getLogger(Login2.class.getName());

    @Inject
    Credentials credentials;

    @Inject
    private Users2Service userService;
    
    @Inject
    private FacesContext facesContext;
    
    private String loggedInUsername = null;

    // constructor!!!
    public Login2() {
    }

    // public method!!!
    public void login() throws Exception {

        // user = users2Service.authenticate(credentials.getUsername(), credentials.getPassword());
        boolean success = userService.authenticate(credentials.getUsername(), credentials.getPassword());
        if ( !success ) {
            logout();   // make sure user is cleared
            LOG.info("User does not exist and/or password not matched ");
            facesContext.addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "Invalid username and/or password", null));
        } else {
            credentials.clearPassword();
            loggedInUsername = credentials.getUsername();
            LOG.log(Level.INFO, "Signed in ok - current user is {0}", getCurrentUser2());
        }
    }

    public void logout() {
        loggedInUsername = null;
    }

    public boolean isLoggedIn() {
        return loggedInUsername != null;
    }

    @Produces
    @Named
    @LoggedIn2
    String getCurrentUser2() {
        return loggedInUsername;
    }

    
    public void validateUsername(FacesContext context, UIComponent component, String value) {
        if (isNullOrBlank(value)) {
            return;     // ignore null values
        }

        Users2 user = userService.findByUsername(value);
        if (user != null) {
            // username already exists
            throw new ValidatorException(
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "username already exists", null));
        }
    }
    
    private static boolean isNullOrBlank(String s) {
        return s == null || s.isBlank();
    }
}
