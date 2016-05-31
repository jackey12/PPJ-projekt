package com.semestral.hirnsal.db.tables;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class CommentTable {

    @Id
    @org.springframework.data.annotation.Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    @Column(nullable = false)
    private String commentText;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "autor")
    @DBRef
    private AutorTable autor;
    @Column(nullable = false)
    private Date createdAt;
    private Date lastUpdate;
    private int likesCount = 0;
    private int dislikesCount = 0;
    @ManyToOne(optional = false)
    @JsonBackReference
    private PictureTable picture;

    public CommentTable(UUID id, AutorTable autor, String commentText, Date createdAt, Date lastUpdate, int likesCount, int dislikesCount, PictureTable commentedPicture){
        this.id = id;
        this.autor = autor;
        this.commentText = commentText;
        this.createdAt = createdAt;
        this.lastUpdate = lastUpdate;
        this.likesCount = likesCount;
        this.dislikesCount = dislikesCount;
        this.picture = commentedPicture;
    }

    public CommentTable(){}

    public CommentTable(UUID id, String commentText, Date createdAt){
        this.id = id;
        this.commentText = commentText;
        this.createdAt = createdAt;
        this.lastUpdate = createdAt;
    }
    public CommentTable(UUID id, String text, AutorTable autorTable, PictureTable commentedPicture, Date createdAt){
        this.id = id;
        this.commentText = text;
        this.autor = autorTable;
        this.picture = commentedPicture;
        this.createdAt = createdAt;
    }

    public AutorTable getAutor(){
        return this.autor;
    }

    public void setAutor(AutorTable autor) {
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

    public PictureTable getCommentedPicture() {
        return picture;
    }

    public void setCommentedPicture(PictureTable commentedPicture) {
        this.picture = commentedPicture;
    }

    public UUID getId() {
        return id;
    }
}
