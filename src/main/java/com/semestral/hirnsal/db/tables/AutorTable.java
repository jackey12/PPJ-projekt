package com.semestral.hirnsal.db.tables;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by jakub on 27.05.2016.
 */

@Entity
@Table(name = "autor")
@Document(collection = "autor")
public class AutorTable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false, name = "name")
    private String name;
    @Column(nullable = false, name = "RegistrationDate")
    private Date date;

    @OneToMany(mappedBy = "autor")
    private List<CommentTable> comments;

    @OneToMany(mappedBy = "autor")
    private List<PictureTable> pictures;

    public AutorTable(String name, Date date) {
        this.name = name;
        this.date = date;

    }

    public AutorTable() {
    }



    public int getId(){
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
