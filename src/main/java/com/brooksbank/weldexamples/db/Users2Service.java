/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brooksbank.weldexamples.db;

import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author sjbro
 */
@Stateless
public class Users2Service extends AbstractFacade<Users2> {

    private static final Logger LOG = Logger.getLogger(Users2Service.class.getName());
    
    @Inject    
    @WeldDatabase
    private EntityManager em;
    
    @Inject
    private PasswordService passwordService;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public Users2Service() {
        super(Users2.class);
    }

    // ***** ADDED ***** ADDED ***** ADDED *****
    @Override
    public void create(Users2 entity) {
        throw new UnsupportedOperationException("Use service.createNewUser() instead");
        // super.create(entity); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void createNewUser(String username, char[] password) throws Exception {
        Users2 newUser = new Users2();
        newUser.setUsername(username);
        newUser.setPasswordhash(
                passwordService.getSaltedHash(password)
        );
        super.create(newUser);
    }
    
    public void updatePassword(String username, char[] password) throws Exception {
        Users2 user = findByUsername(username);
        if (user == null) {
            throw new RuntimeException("user does not exist");
        }
        user.setPasswordhash(
                passwordService.getSaltedHash(password)
        );
        edit(user);
    }
    
    public boolean authenticate(String username, char[] password) throws Exception {

        //LOG.info("  ** UsersService.authenticate() starting ...");
        Users2 user = findByUsername(username);
        
        if (user == null || isNullOrBlank(user.getPasswordhash())) {
            // password not set - it should always be set!
            LOG.info("user to be authenticated does not exist");
            return false;
        }
        
        return passwordService.check(password, user.getPasswordhash());
    }

    @Override
    public void remove(Users2 entity) {
        throw new UnsupportedOperationException("Use service.deleteUser() instead");
        //super.remove(entity); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void deleteUser(String username) {
        Users2 user = findByUsername(username);
        if (user == null) {
            throw new RuntimeException("user does not exist");
        }
        super.remove(user);
    }
    
    
    
    public Users2 findByUsername(String username) {
        Query q = em.createNamedQuery("Users2.findByUsername");
        q.setParameter("username", username);
        try {
            return (Users2) q.getSingleResult();
        } catch (NoResultException ex) {
            return null;    // user not found
        }   // and any other exception (e.g. not unique) will just be thrown
    }
    



    private static boolean isNullOrBlank(String s) {
        return s == null || s.isBlank();
    }
}
