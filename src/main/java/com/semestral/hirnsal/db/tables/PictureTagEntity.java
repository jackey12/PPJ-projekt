package com.semestral.hirnsal.db.tables;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.UUID;

/**
 * Created by jakub on 28.05.2016.
 */

@Entity
@Table(name = "picturetags")
@Document(collection = "picturetags")
public class PictureTagEntity {

    @Id
    @org.springframework.data.annotation.Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    @Column(nullable = false)
    private String tagText;
    @ManyToOne
    @JoinColumn(name = "picture")
    @DBRef
    @JsonIgnore
    private PictureEntity picture;

    public PictureTagEntity(UUID id, String tagText, PictureEntity picture) {
        this.id = id;
        this.tagText = tagText;
        this.picture = picture;
    }

    public PictureTagEntity() {
    }

    public String getTagText() {
        return tagText;
    }

    public void setTagText(String tagText) {
        this.tagText = tagText;
    }

    public PictureEntity getPicture() {
        return picture;
    }

    public void setPicture(PictureEntity picture) {
        this.picture = picture;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
