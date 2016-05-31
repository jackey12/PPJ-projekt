package com.semestral.hirnsal.service;

import com.semestral.hirnsal.db.repositories.BaseAutorRepository;
import com.semestral.hirnsal.db.tables.AutorEntity;
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

    public void create(AutorEntity autorEntity){
        baseAutorRepository.save(autorEntity);
    }

    public void delete(UUID id){
        baseAutorRepository.delete(id);
    }
    public AutorEntity getAutor(UUID id){
        return baseAutorRepository.findOne(id);
    }

}
