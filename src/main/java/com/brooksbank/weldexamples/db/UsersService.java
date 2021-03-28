/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brooksbank.weldexamples.db;

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
public class UsersService extends AbstractFacade<Users> {

    //private static final Logger LOG = Logger.getLogger(UsersService.class.getName());

    @Inject 
    @WeldDatabase
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsersService() {
        super(Users.class);
    }
    
    // ***** ADDED ***** ADDED ***** ADDED *****
    public Users authenticate(String username, char[] password) {
        //LOG.info("  ** UsersService.authenticate() starting ...");
        Query q = em.createNamedQuery("Users.authenticate");
        q.setParameter("username", username);
        q.setParameter("password", hashedPassword(password));
        try {
            return (Users) q.getSingleResult();
        } catch (NoResultException ex) {
            return null;    // user not found
        }
        // any other exception will just be thrown as a runtime exception
    }
    
    private String hashedPassword(char[] password) {
        // assumes database contains String rather than Char[]
        // so just convert into a String
        return String.valueOf(password);
    }
    
}
