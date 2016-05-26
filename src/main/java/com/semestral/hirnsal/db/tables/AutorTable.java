package com.semestral.hirnsal.db.tables;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by jakub on 27.05.2016.
 */

@Entity
@Table(name = "Autor")
public class AutorTable {

    private int id;
    private String name;
    private Date date;

    public AutorTable(String name, Date date) {
        this.name = name;
        this.date = date;

    }


}
