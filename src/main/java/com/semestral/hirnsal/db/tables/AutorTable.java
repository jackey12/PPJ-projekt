package com.semestral.hirnsal.db.tables;

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
public class AutorTable {
    @Id
    @org.springframework.data.annotation.Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false, name = "name")
    private String name;
    @Column(nullable = false, name = "RegistrationDate")
    private Date date;

    @OneToMany(mappedBy = "autor")
    private List<CommentTable> comments;

    @OneToMany(mappedBy = "autor")
    private List<PictureTable> pictures;

    public AutorTable(UUID id, String name, Date date) {
        this.id = id;
        this.name = name;
        this.date = date;

    }

    public AutorTable() {
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



}
