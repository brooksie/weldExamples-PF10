/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brooksbank.weldexamples.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author sjbro
 */
@Entity(name = "Drink")
@Table(name = "DRINK", uniqueConstraints = {@UniqueConstraint(columnNames = "NAME")})
@NamedQueries({
    @NamedQuery(name = "Drink.findAll", query = "SELECT u FROM Drink u"),
    @NamedQuery(name = "Drink.findById", query = "SELECT u FROM Drink u WHERE u.id = :id"),
    @NamedQuery(name = "Drink.findByName", query = "SELECT u FROM Drink u WHERE u.name = :name"),
    // ***** ADDED ***** ADDED ***** ADDED *****
    @NamedQuery(name = "Drink.getCountOfCondiments", query = "SELECT size(t.condiments) FROM Drink t WHERE t.id = :id")
})
@NamedEntityGraphs({
    @NamedEntityGraph( name = "drink.drinkcondiment.condiment",
        attributeNodes = {
            @NamedAttributeNode(value="name"),
            @NamedAttributeNode(value="condiments", subgraph="condiments-subpara")
        },
        subgraphs = {
            @NamedSubgraph( name="condiments-subpara",
                attributeNodes = {
                    @NamedAttributeNode(value="condiment")
                }
            )
        }
    )
})
public class Drink implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
//    private static final Logger LOG = Logger.getLogger(Drink.class.getName());

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;

    @NotNull
    @Size(max = 128, min = 1)
    @Basic(optional = false)
    @Column(name = "NAME", unique = true, nullable = false)
    private String name;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Basic(optional = false)
    @Column(name = "CREATED")
    private Date created = new Date();

    @OneToMany(mappedBy = "drink", cascade = CascadeType.ALL, fetch = FetchType.LAZY,
            orphanRemoval = true)
    private List<DrinkCondiment> condiments = new ArrayList<>();

    
    // Constructors
    public Drink() {
    }

    public Drink(String name) {
        this.name = name;
    }

    //Getters and setters 
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public List<DrinkCondiment> getCondiments() {
        return condiments;
    }

    public void setCondiments(List<DrinkCondiment> condiments) {
        this.condiments = condiments;
    }

    // additional functions
    public void addCondiment(@NotNull Condiment condiment) {
        DrinkCondiment drinkCondiment = new DrinkCondiment(this, condiment);
        condiments.add(drinkCondiment);
        condiment.getDrinks().add(drinkCondiment);
    }

    public void removeCondiment(@NotNull Condiment condiment) {
        // go through all the drinks associted with this condiment
        for ( DrinkCondiment dc : condiment.getDrinks()) {
            // find the one that points to this drink object
            if ( dc.getDrink().equals(this)) {
                // and remove it from the condiment list.
                condiment.getDrinks().remove(dc);
                // and at the same time, remove the condiment from this drink's list
                this.getCondiments().remove(dc);
                dc.setCondiment(null);
                dc.setDrink(null);
                break;   // duplicates are not allowed for same drink & condiment
            }
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.id);
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
        final Drink other = (Drink) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Drink{" + "id=" + id + ", name=" + name + ", created=" + created + '}';
    }

}
