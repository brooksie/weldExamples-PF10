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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "CODES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Codes.findAll", query = "SELECT c FROM Codes c"),
    @NamedQuery(name = "Codes.findByCodeid", query = "SELECT c FROM Codes c WHERE c.codeid = :codeid"),
    @NamedQuery(name = "Codes.findByCode", query = "SELECT c FROM Codes c WHERE c.code = :code"),
    @NamedQuery(name = "Codes.findByDescription", query = "SELECT c FROM Codes c WHERE c.description = :description"),
    @NamedQuery(name = "Codes.findBySortorder", query = "SELECT c FROM Codes c WHERE c.sortorder = :sortorder"),
    @NamedQuery(name = "Codes.findByExtra1", query = "SELECT c FROM Codes c WHERE c.extra1 = :extra1"),
    @NamedQuery(name = "Codes.findByExtra2", query = "SELECT c FROM Codes c WHERE c.extra2 = :extra2"),
    @NamedQuery(name = "Codes.findByExtra3", query = "SELECT c FROM Codes c WHERE c.extra3 = :extra3"),
    @NamedQuery(name = "Codes.findByValidfrom", query = "SELECT c FROM Codes c WHERE c.validfrom = :validfrom"),
    @NamedQuery(name = "Codes.findByValidto", query = "SELECT c FROM Codes c WHERE c.validto = :validto"),
// *** ADDED ***
    @NamedQuery(name = "Codes.findAllByCodetype", query = "SELECT c FROM Codes c JOIN c.codetypeid t "
            + " WHERE t.codetype = :codetype "
            + " ORDER BY c.sortorder" ),
    @NamedQuery(name = "Codes.findCurrentByCodetype", query = "SELECT c FROM Codes c JOIN c.codetypeid t "
            + " WHERE t.codetype = :codetype "
            + " AND c.validfrom <= CURRENT_DATE "
            + " AND c.validto > CURRENT_DATE "
            + " ORDER BY c.sortorder" ),
    @NamedQuery(name = "Codes.findCurrentByCodetypeAndExtra1", query = "SELECT c FROM Codes c JOIN c.codetypeid t "
            + " WHERE t.codetype = :codetype "
            + " AND c.extra1 = :extra1 "
            + " AND c.validfrom <= CURRENT_DATE "
            + " AND c.validto > CURRENT_DATE "
            + " ORDER BY c.sortorder" ),
    @NamedQuery(name = "Codes.findCurrentByCodetypeAndExtra2", query = "SELECT c FROM Codes c JOIN c.codetypeid t "
            + " WHERE t.codetype = :codetype "
            + " AND c.extra2 = :extra2 "
            + " AND c.validfrom <= CURRENT_DATE "
            + " AND c.validto > CURRENT_DATE "
            + " ORDER BY c.sortorder" ),
    @NamedQuery(name = "Codes.findCurrentByCodetypeAndExtra3", query = "SELECT c FROM Codes c JOIN c.codetypeid t "
            + " WHERE t.codetype = :codetype "
            + " AND c.extra3 = :extra3 "
            + " AND c.validfrom <= CURRENT_DATE "
            + " AND c.validto > CURRENT_DATE "
            + " ORDER BY c.sortorder" ),
    @NamedQuery(name = "Codes.findByCodetypeAndCode", query = "SELECT c FROM Codes c JOIN c.codetypeid t "
            + " WHERE t.codetype = :codetype "
            + " AND c.code = :code ")

})
public class Codes implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CODEID")
    private Integer codeid;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "CODE")
    private String code;
    
    @Size(max = 255)
    @Column(name = "DESCRIPTION")
    private String description;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "SORTORDER")
    private int sortorder;
    
    @Size(max = 8)
    @Column(name = "EXTRA1")
    private String extra1;
    
    @Size(max = 8)
    @Column(name = "EXTRA2")
    private String extra2;
    
    @Size(max = 8)
    @Column(name = "EXTRA3")
    private String extra3;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "VALIDFROM")
    @Temporal(TemporalType.DATE)
    private Date validfrom;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "VALIDTO")
    @Temporal(TemporalType.DATE)
    private Date validto;
    
    @JoinColumn(name = "CODETYPEID", referencedColumnName = "CODETYPEID")
    @ManyToOne(optional = false)
    private Codetypes codetypeid;

    public Codes() {
    }

    public Codes(Integer codeid) {
        this.codeid = codeid;
    }

    public Codes(Integer codeid, String code, int sortorder, Date validfrom, Date validto) {
        this.codeid = codeid;
        this.code = code;
        this.sortorder = sortorder;
        this.validfrom = validfrom;
        this.validto = validto;
    }

    public Integer getCodeid() {
        return codeid;
    }

    public void setCodeid(Integer codeid) {
        this.codeid = codeid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSortorder() {
        return sortorder;
    }

    public void setSortorder(int sortorder) {
        this.sortorder = sortorder;
    }

    public String getExtra1() {
        return extra1;
    }

    public void setExtra1(String extra1) {
        this.extra1 = extra1;
    }

    public String getExtra2() {
        return extra2;
    }

    public void setExtra2(String extra2) {
        this.extra2 = extra2;
    }

    public String getExtra3() {
        return extra3;
    }

    public void setExtra3(String extra3) {
        this.extra3 = extra3;
    }

    public Date getValidfrom() {
        return validfrom;
    }

    public void setValidfrom(Date validfrom) {
        this.validfrom = validfrom;
    }

    public Date getValidto() {
        return validto;
    }

    public void setValidto(Date validto) {
        this.validto = validto;
    }

    public Codetypes getCodetypeid() {
        return codetypeid;
    }

    public void setCodetypeid(Codetypes codetypeid) {
        this.codetypeid = codetypeid;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.codeid);
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
        final Codes other = (Codes) obj;
        return Objects.equals(this.codeid, other.codeid);
    }

    @Override
    public String toString() {
        return "com.brooksbank.weldexamples.db.Codes[ codeid=" + codeid + " ]";
    }
    
}
