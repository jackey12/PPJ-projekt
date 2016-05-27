package com.semestral.hirnsal.db.repositories;

import com.semestral.hirnsal.db.tables.AutorTable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jakub on 27.05.2016.
 */
@Repository
public interface BaseAutorRepository extends CrudRepository<AutorTable, Integer> {
    public List<AutorTable> findAll();
}
