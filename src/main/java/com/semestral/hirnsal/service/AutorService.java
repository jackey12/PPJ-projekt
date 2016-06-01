package com.semestral.hirnsal.service;

import com.semestral.hirnsal.db.repositories.BaseAutorRepository;
import com.semestral.hirnsal.db.tables.AutorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Created by jakub on 29.05.2016.
 */
@Service
@Transactional
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
    public List<AutorEntity> getAllAutors(){
        return baseAutorRepository.findAll();
    }

    public List<AutorEntity> getAutorsByName(String name){
        return baseAutorRepository.findByName(name);
    }

    public void save(AutorEntity autorEntity){
        baseAutorRepository.save(autorEntity);
    }


}
