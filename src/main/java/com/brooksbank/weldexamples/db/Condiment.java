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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author sjbro
 */
@Entity(name = "Condiment")
@Table(name = "CONDIMENT")
@NamedQueries({
    @NamedQuery(name = "Condiment.findAll", query = "SELECT t FROM Condiment t"),
    @NamedQuery(name = "Condiment.findById", query = "SELECT t FROM Condiment t WHERE t.id = :id"),
    @NamedQuery(name = "Condiment.findByName", query = "SELECT t FROM Condiment t WHERE t.name = :name"),
    // ***** ADDED ***** ADDED ***** ADDED *****
    @NamedQuery(name = "Condiment.getCountOfDrinks", query = "SELECT size(t.drinks) FROM Condiment t WHERE t.id = :id"),
    @NamedQuery(name = "Condiment.findByIdWithDrinks", 
            query = "SELECT c FROM Condiment c LEFT JOIN FETCH c.drinks dc LEFT JOIN FETCH dc.drink "
                    + "WHERE c.id = :id")
})
@NamedEntityGraphs({
    @NamedEntityGraph( name = "condiment.drinkcondiment.drink",
        attributeNodes = {
            @NamedAttributeNode(value="name"),
            @NamedAttributeNode(value="drinks", subgraph="drinks-subpara")
        },
        subgraphs = {
            @NamedSubgraph( name="drinks-subpara",
                attributeNodes = {
                    @NamedAttributeNode(value="drink")
                }
            )
        }
    )
})
public class Condiment implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;

    @NotNull
    @Size(max = 32, min = 1)
    @Basic(optional = false)
    @Column(name = "NAME", unique = true, nullable = false)
//    @NaturalId        -- Hibernate-specific annotation
    private String name;
 
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Basic(optional = false)
    @Column(name = "CREATED")
    private Date created = new Date();
 
    @OneToMany(mappedBy = "condiment", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DrinkCondiment> drinks = new ArrayList<>();


    public Condiment() {}
 
    public Condiment(String name) {
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

    // the NaturalId generally shouln't be changed
    public void setName(String name) {
        this.name = name;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
    
    public List<DrinkCondiment> getDrinks() {
        return drinks;
    }

    public void setDrinks(List<DrinkCondiment> drinks) {
        this.drinks = drinks;
    }

    // note: NAME is the @NaturalID, meaning not null and unique and doesn't change
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        //if (obj == null || getClass() != obj.getClass()) return false;
        if (obj == null || !(obj instanceof Condiment)) return false;
        final Condiment other = (Condiment) obj;
        return Objects.equals(this.name, other.name);
    }

    @Override
    public String toString() {
        return "Condiment{" + "id=" + id + ", name=" + name + ", created=" + created + '}';
    }
} 
