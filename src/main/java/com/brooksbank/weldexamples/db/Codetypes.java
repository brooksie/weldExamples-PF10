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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sjbro
 */
@Entity
@Table(name = "CODETYPES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Codetypes.findAll", query = "SELECT c FROM Codetypes c"),
    @NamedQuery(name = "Codetypes.findByCodetypeid", query = "SELECT c FROM Codetypes c WHERE c.codetypeid = :codetypeid"),
    @NamedQuery(name = "Codetypes.findByCodetype", query = "SELECT c FROM Codetypes c WHERE c.codetype = :codetype"),
    @NamedQuery(name = "Codetypes.findByDescription", query = "SELECT c FROM Codetypes c WHERE c.description = :description"),
    @NamedQuery(name = "Codetypes.findByExtra1name", query = "SELECT c FROM Codetypes c WHERE c.extra1name = :extra1name"),
    @NamedQuery(name = "Codetypes.findByExtra2name", query = "SELECT c FROM Codetypes c WHERE c.extra2name = :extra2name"),
    @NamedQuery(name = "Codetypes.findByExtra3name", query = "SELECT c FROM Codetypes c WHERE c.extra3name = :extra3name")})
public class Codetypes implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CODETYPEID")
    private Integer codetypeid;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "CODETYPE")
    private String codetype;
    
    @Size(max = 255)
    @Column(name = "DESCRIPTION")
    private String description;
    
    @Size(max = 255)
    @Column(name = "REGEX")
    private String regex;
    
    @Size(max = 255)
    @Column(name = "EXTRA1NAME")
    private String extra1name;
    
    @Size(max = 255)
    @Column(name = "EXTRA1REGEX")
    private String extra1regex;
    
    @Size(max = 255)
    @Column(name = "EXTRA2NAME")
    private String extra2name;
    
    @Size(max = 255)
    @Column(name = "EXTRA2REGEX")
    private String extra2regex;
    
    @Size(max = 255)
    @Column(name = "EXTRA3NAME")
    private String extra3name;
    
    @Size(max = 255)
    @Column(name = "EXTRA3REGEX")
    private String extra3regex;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codetypeid", orphanRemoval = true)
    private Collection<Codes> codesCollection;

    public Codetypes() {
    }

    public Codetypes(Integer codetypeid) {
        this.codetypeid = codetypeid;
    }

    public Codetypes(Integer codetypeid, String codetype) {
        this.codetypeid = codetypeid;
        this.codetype = codetype;
    }

    public Integer getCodetypeid() {
        return codetypeid;
    }

    public void setCodetypeid(Integer codetypeid) {
        this.codetypeid = codetypeid;
    }

    public String getCodetype() {
        return codetype;
    }

    public void setCodetype(String codetype) {
        this.codetype = codetype;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public String getExtra1name() {
        return extra1name;
    }

    public void setExtra1name(String extra1name) {
        this.extra1name = extra1name;
    }

    public String getExtra1regex() {
        return extra1regex;
    }

    public void setExtra1regex(String extra1regex) {
        this.extra1regex = extra1regex;
    }

    public String getExtra2name() {
        return extra2name;
    }

    public void setExtra2name(String extra2name) {
        this.extra2name = extra2name;
    }

    public String getExtra2regex() {
        return extra2regex;
    }

    public void setExtra2regex(String extra2regex) {
        this.extra2regex = extra2regex;
    }

    public String getExtra3name() {
        return extra3name;
    }

    public void setExtra3name(String extra3name) {
        this.extra3name = extra3name;
    }

    public String getExtra3regex() {
        return extra3regex;
    }

    public void setExtra3regex(String extra3regex) {
        this.extra3regex = extra3regex;
    }

    public Collection<Codes> getCodesCollection() {
        return codesCollection;
    }

    public void setCodesCollection(Collection<Codes> codesCollection) {
        this.codesCollection = codesCollection;
    }

    // ***** ADDED ***** ADDED ***** ADDED *****
    
    public Codetypes addCode( @NotNull Codes code ) {
        if (codesCollection == null) {
            codesCollection = Arrays.asList(code);
        } else {
            codesCollection.add(code);
        }
        code.setCodetypeid(this);
        return this;
    }
    
    public Codetypes removeCode( @NotNull Codes code ) {
        codesCollection.remove(code);
        code.setCodetypeid(null);
        return this;
    }
    
    // END OF ***** ADDED ***** ADDED ***** ADDED *****
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.codetypeid);
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
        final Codetypes other = (Codetypes) obj;
        return Objects.equals(this.codetypeid, other.codetypeid);
    }

    @Override
    public String toString() {
        return "com.brooksbank.weldexamples.db.Codetypes[ codetypeid=" + codetypeid + " ]";
    }
    
}
