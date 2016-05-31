package com.semestral.hirnsal.db.tables;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Cascade;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.*;

/**
 * Created by jakub on 27.05.2016.
 */

@Entity
@Table(name = "picture")
@Document(collection = "picture")
public class PictureEntity {

    @Id
    @org.springframework.data.annotation.Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    @Column(nullable = false)
    private String pictureURL;
    @Column(nullable = false)
    private String name;
    @ManyToOne
    @JoinColumn(name = "autor")
    @JsonBackReference
    @DBRef
    private AutorEntity autor;
    @Column(nullable = false)
    private Date createdAt;
    private Date lastUpdate;
    private long likesCount = 0;
    private long dislikesCount = 0;
    @OneToMany(mappedBy = "picture", cascade = CascadeType.ALL, orphanRemoval = true, fetch=FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    @JsonManagedReference
    private List<PictureTagEntity> tags;

    public PictureEntity(UUID id, String pictureURL, String name, AutorEntity autor, Date createdAt, Date lastUpdate, long likesCount, long dislikesCount, List<PictureTagEntity> tags) {
        this.id = id;
        this.pictureURL = pictureURL;
        this.name = name;
        this.autor = autor;
        this.createdAt = createdAt;
        this.lastUpdate = lastUpdate;
        this.likesCount = likesCount;
        this.dislikesCount = dislikesCount;
        this.tags = tags;
    }

    public PictureEntity(UUID id, String name, String pictureURL, Date createdAt) {
        this.id = id;
        this.name = name;
        this.pictureURL = pictureURL;
        this.createdAt = createdAt;
        this.lastUpdate = createdAt;
    }
    public PictureEntity(UUID id, String name, String pictureURL){
        this.id = id;
        this.name = name;
        this.pictureURL = pictureURL;
    }

    public PictureEntity() {}

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

    public AutorEntity getAutor() {
        return autor;
    }

    public void setAutor(AutorEntity autor) {
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

    public List<PictureTagEntity> getTags() {
        return tags;
    }

    public void setTags(List<PictureTagEntity> tags) {
        this.tags = tags;
    }

    public UUID getId() {
        return id;
    }

    public List<String> getAllTagsText(){
        List<String> tags = new ArrayList<>();
        for(PictureTagEntity tag :this.tags){
            tags.add(tag.getTagText());
        }
        return tags;
    }
}
