/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brooksbank.weldexamples.db;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sjbro
 */
@Entity
@Table(name = "MESSAGELOG")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Messagelog.findAll", query = "SELECT m FROM Messagelog m"),
    @NamedQuery(name = "Messagelog.findByMessageid", query = "SELECT m FROM Messagelog m WHERE m.messageid = :messageid"),
    @NamedQuery(name = "Messagelog.findByMessage",   query = "SELECT m FROM Messagelog m WHERE m.message = :message"),
    @NamedQuery(name = "Messagelog.findByCreated",   query = "SELECT m FROM Messagelog m WHERE m.created = :created"),
    @NamedQuery(name = "Messagelog.findByCreatedby", query = "SELECT m FROM Messagelog m WHERE m.createdby = :createdby")
})
public class Messagelog implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "MESSAGEID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence" )
    @SequenceGenerator(sequenceName = "MessageLog_Seq", name = "sequence")
    private Long messageid;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "MESSAGE")
    private String message;
    
    @Column(name = "CREATED", updatable = false, insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    
    @Size(max = 128)
    @Column(name = "CREATEDBY", updatable = false, insertable = false)
    private String createdby;

    // Constructors
    public Messagelog() {
    }

    public Messagelog(Long messageid) {
        this.messageid = messageid;
    }

    public Messagelog(Long messageid, String message) {
        this.messageid = messageid;
        this.message = message;
    }

    // getters + setters
    public Long getMessageid() {
        return messageid;
    }

    public void setMessageid(Long messageid) {
        this.messageid = messageid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreated() {
        return created;
    }

//    public void setCreated(Date created) {
//        this.created = created;
//    }

    public String getCreatedby() {
        return createdby;
    }

//    public void setCreatedby(String createdby) {
//        this.createdby = createdby;
//    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.messageid);
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
        final Messagelog other = (Messagelog) obj;
        return Objects.equals(this.messageid, other.messageid);
    }
    
    @Override
    public String toString() {
        return "com.brooksbank.weldexamples.db.Messagelog[ codetypeid=" + messageid + " ]";
    }
    
}
