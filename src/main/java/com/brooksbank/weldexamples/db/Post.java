/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brooksbank.weldexamples.db;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
@Entity(name = "Post")
@Table(name = "POST")
@NamedQueries({
    @NamedQuery(name = "Post.findAll", query = "SELECT u FROM Post u"),
    @NamedQuery(name = "Post.findById", query = "SELECT u FROM Post u WHERE u.id = :id"),
    @NamedQuery(name = "Post.findByTitle", query = "SELECT u FROM Post u WHERE u.title = :title")
})
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
 
    @NotNull
    @Size(max = 128, min = 1)
    @Basic(optional = false)
    @Column(name = "TITLE")
    private String title;

    // ***** Note the use of SET rather than LIST for this m-m relationship
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "posttag",
        joinColumns = @JoinColumn(name = "postid"),
        inverseJoinColumns = @JoinColumn(name = "tagid")
    )
    private Set<Tag> tags = new HashSet<>();

    // Constructors
    public Post() {}
 
    public Post(String title) {
        this.title = title;
    }
 
    //Getters and setters 

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    // additional functions
    
    public void addTag(Tag tag) {
        tags.add(tag);
        tag.getPosts().add(this);
    }
 
    public void removeTag(Tag tag) {
        tags.remove(tag);
        tag.getPosts().remove(this);
    }
 
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post)) return false;
        return id != null && id.equals(((Post) o).getId());
    }
 
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}