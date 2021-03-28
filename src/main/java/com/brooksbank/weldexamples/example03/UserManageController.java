/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brooksbank.weldexamples.example03;

import com.brooksbank.weldexamples.db.Users2;
import com.brooksbank.weldexamples.db.Users2Service;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;

/**
 *
 * @author sjbro
 */
@Named
@ViewScoped
public class UserManageController implements Serializable {

    @Inject
    private Users2Service userService;

    private List<Users2> userList;

    private Users2 selectedUser;

    @Inject
    private Credentials credentials;

    @Inject
    private FacesContext facesContext;
    
    /**
     * Creates a new instance of UserManageController
     */
    public UserManageController() {
    }

    @PostConstruct
    private void updateUserList() {
        userList = userService.findAll();
    }

    public List<Users2> getUserList() {
        return userList;
    }

    public Users2 getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(Users2 selectedUser) {
        this.selectedUser = selectedUser;
        credentials.setUsername(selectedUser.getUsername());
        credentials.setPassword(null);
    }

    
    public void validateNewUsername(FacesContext context, UIComponent component, String value) {
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

    public void addAction() {

        boolean added = false;
         

        try {
            userService.createNewUser(credentials.getUsername(), credentials.getPassword());
            // success
            credentials.clearPassword();
            added = true;
            updateUserList();
            
        } catch (Exception ex) {
            //Logger.getLogger(UserManageController.class.getName()).log(Level.SEVERE, null, ex);
            facesContext.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "unable to create user", ex.getMessage()));
        }
        PrimeFaces.current().ajax().addCallbackParam("added", added);
        
    }
    
    public void editAction() {

        boolean edited = false;
        
        try {
            userService.updatePassword(selectedUser.getUsername(), credentials.getPassword());
            // success
            credentials.clearPassword();
            edited = true;
            updateUserList();
        } catch (Exception ex) {
            //Logger.getLogger(UserManageController.class.getName()).log(Level.SEVERE, null, ex);
            facesContext.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "unable to edit user", ex.getMessage()));
        }
        PrimeFaces.current().ajax().addCallbackParam("edited", edited);
    }
    
    public void deleteAction() {

        boolean deleted = false;
        
        try {
            userService.deleteUser(selectedUser.getUsername());
            // success
            deleted = true;
            updateUserList();
        } catch (Exception ex) {
            //Logger.getLogger(UserManageController.class.getName()).log(Level.SEVERE, null, ex);
            facesContext.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "unable to delete user", ex.getMessage()));
        }
        PrimeFaces.current().ajax().addCallbackParam("deleted", deleted);
    }
    
    private static boolean isNullOrBlank(String s) {
        return s == null || s.isBlank();
    }

}
