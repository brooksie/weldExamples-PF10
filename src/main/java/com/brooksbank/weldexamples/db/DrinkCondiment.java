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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author sjbro
 */
@Entity(name = "DrinkCondiment")
@Table(name = "DRINKCONDIMENT")

public class DrinkCondiment implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private DrinkCondimentPK pk;

    @MapsId("drinkId")      // maps to the drinkId attribute of the EmbeddedId
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DRINKID", referencedColumnName = "ID")
    private Drink drink;

    @MapsId("condimentId")      // maps to the condimentId attribute of the EmbeddedId
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONDIMENTID", referencedColumnName = "ID")
    private Condiment condiment;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Basic(optional = false)
    @Column(name = "CREATED", nullable = false)
    private Date created = new Date();

    // Constructors
    public DrinkCondiment() {
    }

    public DrinkCondiment(Drink drink, Condiment condiment) {
        this.pk = new DrinkCondimentPK(drink.getId(), condiment.getId());
        this.drink = drink;
        this.condiment = condiment;
    }

    //Getters and setters 
    public DrinkCondimentPK getPk() {
        return pk;
    }

    public void setPk(DrinkCondimentPK pk) {
        this.pk = pk;
    }

    public Drink getDrink() {
        return drink;
    }

    public void setDrink(Drink drink) {
        this.drink = drink;
    }

    public Condiment getCondiment() {
        return condiment;
    }

    public void setCondiment(Condiment condiment) {
        this.condiment = condiment;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.pk);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final DrinkCondiment other = (DrinkCondiment) obj;
        return Objects.equals(this.pk, other.pk);
    }

    @Override
    public String toString() {
        return "DrinkCondiment{" + "pk=" + pk + ", created=" + created + '}';
    }

}
