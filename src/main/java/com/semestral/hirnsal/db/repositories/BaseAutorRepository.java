package com.semestral.hirnsal.db.repositories;

import com.semestral.hirnsal.db.tables.AutorEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Created by jakub on 27.05.2016.
 */
@Repository
public interface BaseAutorRepository extends CrudRepository<AutorEntity, UUID> {
    public List<AutorEntity> findAll();
    public List<AutorEntity>findByName(String name);
}
