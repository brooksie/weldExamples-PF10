/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brooksbank.weldexamples.db;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
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
@Table(name = "ROLES")
@NamedQueries({
    @NamedQuery(name = "Roles.findAll", query = "SELECT u FROM Roles u"),
    @NamedQuery(name = "Roles.findById", query = "SELECT u FROM Roles u WHERE u.id = :id"),
    @NamedQuery(name = "Roles.findByRolename", query = "SELECT u FROM Roles u WHERE u.rolename = :rolename")
})
public class Roles implements Serializable{

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "ROLENAME")
    private String rolename;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "roleid", orphanRemoval = true)
    private Collection<Userroles> userrolesCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "roleid", orphanRemoval = true)
    private Collection<Roleprivileges> roleprivilegesCollection;

    public Roles() {
    }

    public Roles(Long id) {
        this.id = id;
    }

    public Roles(Long id, String rolename) {
        this.id = id;
        this.rolename = rolename;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }
    
    public Collection<Userroles> getUserrolesCollection() {
        return userrolesCollection;
    }

    public void setUserrolesCollection(Collection<Userroles> userrolesCollection) {
        this.userrolesCollection = userrolesCollection;
    }

    public Collection<Roleprivileges> getRoleprivilegesCollection() {
        return roleprivilegesCollection;
    }

    public void setRoleprivilegesCollection(Collection<Roleprivileges> roleprivilegesCollection) {
        this.roleprivilegesCollection = roleprivilegesCollection;
    }
    
    // ***** ADDED ***** ADDED ***** ADDED *****
    
    public Roles addUser( @NotNull Userroles userrole ) {
        if (userrolesCollection == null) {
            userrolesCollection = Arrays.asList(userrole);
        } else {
            userrolesCollection.add(userrole);
        }
        userrole.setRoleid(this);
        return this;
    }
    
    public Roles removeUser( @NotNull Userroles userrole ) {
        userrolesCollection.remove(userrole);
        userrole.setRoleid(null);
        return this;
    }
    
    public Roles addApppriv( @NotNull Roleprivileges rolepriv ) {
        if (roleprivilegesCollection == null) {
            roleprivilegesCollection = Arrays.asList(rolepriv);
        } else {
            roleprivilegesCollection.add(rolepriv);
        }
        rolepriv.setRoleid(this);
        return this;
    }
    
    public Roles removeApppriv( @NotNull Roleprivileges rolepriv ) {
        roleprivilegesCollection.remove(rolepriv);
        rolepriv.setRoleid(this);
        return this;
    }
    
    // END OF ***** ADDED ***** ADDED ***** ADDED *****


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Roles other = (Roles) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "com.brooksbank.weldexamples.db.Roles[ id=" + id + " ]";
    }
    
}
