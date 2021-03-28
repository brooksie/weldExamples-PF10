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
@Table(name = "USERROLES")
@NamedQueries({
    @NamedQuery(name = "Userroles.findAll", query = "SELECT u FROM Userroles u"),
    @NamedQuery(name = "Userroles.findById", query = "SELECT u FROM Userroles u WHERE u.id = :id"),
    @NamedQuery(name = "Userroles.findByRoleconstraint", query = "SELECT u FROM Userroles u WHERE u.roleconstraint = :roleconstraint")
})
public class Userroles implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    
    @Size(max = 255)
    @Column(name = "ROLECONSTRAINT")
    private String roleconstraint;
    
    
    @JoinColumn(name = "ROLEID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Roles roleid;

    @JoinColumn(name = "USERID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Users2 userid;

    public Userroles() {
    }

    public Userroles(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleconstraint() {
        return roleconstraint;
    }

    public void setRoleconstraint(String roleconstraint) {
        this.roleconstraint = roleconstraint;
    }

    public Users2 getUserid() {
        return userid;
    }

    public void setUserid(Users2 userid) {
        this.userid = userid;
    }

    public Roles getRoleid() {
        return roleid;
    }

    public void setRoleid(Roles roleid) {
        this.roleid = roleid;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.id);
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
        final Userroles other = (Userroles) obj;
        return Objects.equals(this.id, other.id);
    }
    
    @Override
    public String toString() {
        return "com.brooksbank.weldexamples.db.Userroles[ id=" + id + " ]";
    }
    
}
