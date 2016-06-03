package com.semestral.hirnsal.db.tables;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cascade;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by jakub on 27.05.2016.
 */

@Entity
@Table(name = "autor")
@Document(collection = "autor")
public class AutorEntity {
    @Id
    @org.springframework.data.annotation.Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, name = "RegistrationDate")
    private Date date;

    @OneToMany(mappedBy = "autor")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    @JsonIgnore
    private List<CommentEntity> comments;

    @OneToMany(mappedBy = "autor")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    @JsonIgnore
    private List<PictureEntity> pictures;

    public AutorEntity(UUID id, String name, Date date) {
        this.id = id;
        this.name = name;
        this.date = date;

    }

    public AutorEntity() {
    }

    public UUID getId(){
        return this.id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void SetDate(Date date){
        this.date = date;
    }

    public String toString() {
        return "Author (id = "+ id +", name = " +name+ ", regDate = "+date.toString()+")" ;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<CommentEntity> getComments() {
        return comments;
    }

    public void setComments(List<CommentEntity> comments) {
        this.comments = comments;
    }

    public List<PictureEntity> getPictures() {
        return pictures;
    }

    public void setPictures(List<PictureEntity> pictures) {
        this.pictures = pictures;
    }
}
