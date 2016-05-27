package com.semestral.hirnsal.db.tables;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jakub on 27.05.2016.
 */

@Entity
@Table(name = "Picture")
@Document(collection = "Picture")
public class PictureTable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false)
    private String pictureURL;
    @Column(nullable = false)
    private String name;
    @ManyToOne
    @JoinColumn(name = "Autor")
    @DBRef
    private AutorTable autor;
    @Column(nullable = false)
    private Date createdAt;
    private Date lastUpdate;
    private long likesCount = 0;
    private long dislikesCount = 0;
    @ElementCollection
    @Column(name = "tags", length = 16)
    private Set<String> tags = new HashSet<>();

    public PictureTable(String pictureURL, String name, AutorTable autor, Date createdAt, Date lastUpdate, long likesCount, long dislikesCount, Set<String> tags) {
        this.pictureURL = pictureURL;
        this.name = name;
        this.autor = autor;
        this.createdAt = createdAt;
        this.lastUpdate = lastUpdate;
        this.likesCount = likesCount;
        this.dislikesCount = dislikesCount;
        this.tags = tags;
    }

    public PictureTable(String name, String pictureURL, Date createdAt) {
        this.name = name;
        this.pictureURL = pictureURL;
        this.createdAt = createdAt;
    }

    public PictureTable() {}

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AutorTable getAutor() {
        return autor;
    }

    public void setAutor(AutorTable autor) {
        this.autor = autor;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public long getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(long likesCount) {
        this.likesCount = likesCount;
    }

    public long getDislikesCount() {
        return dislikesCount;
    }

    public void setDislikesCount(long dislikesCount) {
        this.dislikesCount = dislikesCount;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public int getId() {
        return id;
    }
}
