package com.semestral.hirnsal.service;

import com.semestral.hirnsal.db.repositories.BaseAutorRepository;
import com.semestral.hirnsal.db.tables.AutorTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by jakub on 29.05.2016.
 */
@Service
public class AutorService {

    private final BaseAutorRepository baseAutorRepository;

    @Autowired
    public AutorService(BaseAutorRepository baseAutorRepository){
        this.baseAutorRepository = baseAutorRepository;
    }

    public void create(AutorTable autorTable){
        baseAutorRepository.save(autorTable);
    }

    public void delete(UUID id){
        baseAutorRepository.delete(id);
    }
    public AutorTable getAutor(UUID id){
        return baseAutorRepository.findOne(id);
    }

}
