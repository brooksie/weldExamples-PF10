/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brooksbank.weldexamples.db;

import java.util.Objects;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author sjbro
 */
@Embeddable
public class DrinkCondimentPK implements java.io.Serializable {
 
    private Long drinkId;
    private Long condimentId;
    

    public DrinkCondimentPK() {}
 
    public DrinkCondimentPK( @NotNull Long drinkId, @NotNull Long condimentId) {
        this.drinkId = drinkId;
        this.condimentId = condimentId;
    }

    public Long getDrinkId() {
        return drinkId;
    }

    public void setDrinkId(Long drinkId) {
        this.drinkId = drinkId;
    }

    public Long getCondimentId() {
        return condimentId;
    }

    public void setCondimentId(Long condimentId) {
        this.condimentId = condimentId;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.drinkId);
        hash = 97 * hash + Objects.hashCode(this.condimentId);
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
        final DrinkCondimentPK other = (DrinkCondimentPK) obj;
        return Objects.equals(this.drinkId, other.drinkId)
                && Objects.equals(this.condimentId, other.condimentId);
    }

    @Override
    public String toString() {
        return "DrinkCondimentPK{" + "drinkId=" + drinkId + ", condimentId=" + condimentId + '}';
    }
}
