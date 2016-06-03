package com.semestral.hirnsal.db.tables;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * Created by jakub on 27.05.2016.
 */

@Entity
@Table(name = "comments")
@Document(collection = "comments")
public class CommentEntity {

    @Id
    @org.springframework.data.annotation.Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    @Column(nullable = false)
    private String commentText;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "autor")
    @DBRef
    private AutorEntity autor;
    @Column(nullable = false)
    private Date createdAt;
    private Date lastUpdate;
    private int likesCount = 0;
    private int dislikesCount = 0;
    @ManyToOne(optional = false)
    @JsonIgnore
    private PictureEntity picture;

    public CommentEntity(UUID id, AutorEntity autor, String commentText, Date createdAt, Date lastUpdate, int likesCount, int dislikesCount, PictureEntity commentedPicture){
        this.id = id;
        this.autor = autor;
        this.commentText = commentText;
        this.createdAt = createdAt;
        this.lastUpdate = lastUpdate;
        this.likesCount = likesCount;
        this.dislikesCount = dislikesCount;
        this.picture = commentedPicture;
    }

    public CommentEntity(){}

    public CommentEntity(UUID id, String commentText, Date createdAt){
        this.id = id;
        this.commentText = commentText;
        this.createdAt = createdAt;
        this.lastUpdate = createdAt;
    }
    public CommentEntity(UUID id, String text, AutorEntity autorEntity, PictureEntity commentedPicture, Date createdAt){
        this.id = id;
        this.commentText = text;
        this.autor = autorEntity;
        this.picture = commentedPicture;
        this.createdAt = createdAt;
    }

    public AutorEntity getAutor(){
        return this.autor;
    }

    public void setAutor(AutorEntity autor) {
        this.autor = autor;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
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

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public int getDislikesCount() {
        return dislikesCount;
    }

    public void setDislikesCount(int dislikesCount) {
        this.dislikesCount = dislikesCount;
    }

    public PictureEntity getCommentedPicture() {
        return picture;
    }

    public void setCommentedPicture(PictureEntity commentedPicture) {
        this.picture = commentedPicture;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public PictureEntity getPicture() {
        return picture;
    }

    public void setPicture(PictureEntity picture) {
        this.picture = picture;
    }
}
