package com.semestral.hirnsal.db.tables;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;

/**
 * Created by jakub on 28.05.2016.
 */

@Entity
@Table(name = "picturetags")
@Document(collection = "picturetags")
public class PictureTagTable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false)
    private String tagText;
    @ManyToOne
    @JoinColumn(name = "picture")
    @DBRef
    private PictureTable picture;

    public PictureTagTable(String tagText, PictureTable picture) {
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
