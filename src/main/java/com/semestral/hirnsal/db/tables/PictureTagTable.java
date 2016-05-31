package com.semestral.hirnsal.db.tables;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class PictureTagTable {

    @Id
    @org.springframework.data.annotation.Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    @Column(nullable = false)
    private String tagText;
    @ManyToOne
    @JoinColumn(name = "picture")
    @DBRef
    @JsonBackReference
    private PictureTable picture;

    public PictureTagTable(UUID id, String tagText, PictureTable picture) {
        this.id = id;
        this.tagText = tagText;
        this.picture = picture;
    }

    public PictureTagTable() {
    }

    public String getTagText() {
        return tagText;
    }

    public void setTagText(String tagText) {
        this.tagText = tagText;
    }

    public PictureTable getPicture() {
        return picture;
    }

    public void setPicture(PictureTable picture) {
        this.picture = picture;
    }
}
