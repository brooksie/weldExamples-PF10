/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brooksbank.weldexamples.db;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author sjbro
 */
@Entity
@Table(name = "USERS2")
@NamedQueries({
    @NamedQuery(name = "Users2.findAll", query = "SELECT u FROM Users2 u"),
    @NamedQuery(name = "Users2.findById", query = "SELECT u FROM Users2 u WHERE u.id = :id"),
    @NamedQuery(name = "Users2.findByUsername", query = "SELECT u FROM Users2 u WHERE u.username = :username")
})

public class Users2 implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "USERNAME")
    private String username;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "PASSWORDHASH")
    private String passwordhash;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userid", orphanRemoval = true)
    private Collection<Userroles> userrolesCollection;


    public Users2() {
    }

    public Users2(Long id) {
        this.id = id;
    }

    public Users2(Long id, String username, String passwordhash) {
        this.id = id;
        this.username = username;
        this.passwordhash = passwordhash;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordhash() {
        return passwordhash;
    }

    public void setPasswordhash(String passwordhash) {
        this.passwordhash = passwordhash;
    }

    public Collection<Userroles> getUserrolesCollection() {
        return userrolesCollection;
    }

    public void setUserrolesCollection(Collection<Userroles> userrolesCollection) {
        this.userrolesCollection = userrolesCollection;
    }
    
    // ***** ADDED ***** ADDED ***** ADDED *****
    
    public Users2 addRole( @NotNull Userroles userrole ) {
        if (userrolesCollection == null) {
            userrolesCollection = Arrays.asList(userrole);
        } else {
            userrolesCollection.add(userrole);
        }
        userrole.setUserid(this);
        return this;
    }
    
    public Users2 removeRole( @NotNull Userroles userrole ) {
        userrolesCollection.remove(userrole);
        userrole.setUserid(null);
        return this;
    }
    
    // END OF ***** ADDED ***** ADDED ***** ADDED *****
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users2)) {
            return false;
        }
        Users2 other = (Users2) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.brooksbank.weldexamples.db.Users2[ id=" + id + " ]";
    }
    
}
