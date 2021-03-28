/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brooksbank.weldexamples.db;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;


/**
 *
 * @author sjbro
 */
@Entity
@Table(name = "ROLEPRIVILEGES")
@NamedQueries({
    @NamedQuery(name = "Roleprivileges.findAll", query = "SELECT r FROM Roleprivileges r"),
    @NamedQuery(name = "Roleprivileges.findById", query = "SELECT r FROM Roleprivileges r WHERE r.id = :id"),
    @NamedQuery(name = "Roleprivileges.findByPrivconstraint", query = "SELECT r FROM Roleprivileges r WHERE r.privconstraint = :privconstraint")})
public class Roleprivileges implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    
    @Size(max = 255)
    @Column(name = "PRIVCONSTRAINT")
    private String privconstraint;
    
    @JoinColumn(name = "PRIVID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Appprivileges privid;
    
    @JoinColumn(name = "ROLEID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Roles roleid;

    public Roleprivileges() {
    }

    public Roleprivileges(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrivconstraint() {
        return privconstraint;
    }

    public void setPrivconstraint(String privconstraint) {
        this.privconstraint = privconstraint;
    }

    public Appprivileges getPrivid() {
        return privid;
    }

    public void setPrivid(Appprivileges privid) {
        this.privid = privid;
    }

    public Roles getRoleid() {
        return roleid;
    }

    public void setRoleid(Roles roleid) {
        this.roleid = roleid;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + Objects.hashCode(this.id);
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
        final Roleprivileges other = (Roleprivileges) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "com.brooksbank.weldexamples.db.Roleprivileges{" + "id=" + id + '}';
    }

    


}
