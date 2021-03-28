/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brooksbank.weldexamples.db;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author sjbro
 */
@Entity(name = "Tag")
@Table(name = "TAG")
@NamedQueries({
    @NamedQuery(name = "Tag.findAll", query = "SELECT t FROM Tag t"),
    @NamedQuery(name = "Tag.findById", query = "SELECT t FROM Tag t WHERE t.id = :id"),
    @NamedQuery(name = "Tag.findByName", query = "SELECT t FROM Tag t WHERE t.name = :name")
    // ***** ADDED ***** ADDED ***** ADDED *****
    ,@NamedQuery(name = "Tag.getCountOfPosts", query = "SELECT size(t.posts) FROM Tag t WHERE t.id = :id")
})
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Size(max = 32, min = 1)
    @Basic(optional = false)
    @Column(name = "NAME")
//    @NaturalId        -- Hibernate-specific annotation
    private String name;
 
    // ***** Note the use of SET rather than LIST for this m-m relationship
    @ManyToMany(mappedBy = "tags", fetch = FetchType.EAGER)
    private Set<Post> posts = new HashSet<>();
 
    public Tag() {}
 
    public Tag(String name) {
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

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) { 
        this.posts = posts;
    }

    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag otherTag = (Tag) o;
        return Objects.equals(name, otherTag.name);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Tag{" + "id=" + id + ", name=" + name + '}';
    }
    
} 

