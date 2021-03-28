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
@Table(name = "APPPRIVILEGES")
@NamedQueries({
    @NamedQuery(name = "Appprivileges.findAll", query = "SELECT u FROM Appprivileges u"),
    @NamedQuery(name = "Appprivileges.findById", query = "SELECT u FROM Appprivileges u WHERE u.id = :id"),
    @NamedQuery(name = "Appprivileges.findByPrivname", query = "SELECT u FROM Appprivileges u WHERE u.privname = :privname")
})
public class Appprivileges implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "PRIVNAME")
    private String privname;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "privid", orphanRemoval = true)
    private Collection<Roleprivileges> roleprivilegesCollection;

    public Appprivileges() {
    }

    public Appprivileges(Long id) {
        this.id = id;
    }

    public Appprivileges(Long id, String privname) {
        this.id = id;
        this.privname = privname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrivname() {
        return privname;
    }

    public void setPrivname(String privname) {
        this.privname = privname;
    }

    // ***** ADDED ***** ADDED ***** ADDED *****
    
    public Appprivileges addRole( @NotNull Roleprivileges rolepriv ) {
        if (roleprivilegesCollection == null) {
            roleprivilegesCollection = Arrays.asList(rolepriv);
        } else {
            roleprivilegesCollection.add(rolepriv);
        }
        rolepriv.setPrivid(this);
        return this;
    }
    
    public Appprivileges removeRole( @NotNull Roleprivileges rolepriv ) {
        roleprivilegesCollection.remove(rolepriv);
        rolepriv.setPrivid(this);
        return this;
    }
    
    // END OF ***** ADDED ***** ADDED ***** ADDED *****

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.id);
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
        final Appprivileges other = (Appprivileges) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "com.brooksbank.weldexamples.db.Appprivileges[ id=" + id + " ]";
    }
    
}
