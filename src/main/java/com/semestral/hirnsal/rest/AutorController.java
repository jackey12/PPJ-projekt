package com.semestral.hirnsal.rest;

import com.semestral.hirnsal.client.ServerApi;
import com.semestral.hirnsal.db.repositories.BaseAutorRepository;
import com.semestral.hirnsal.db.tables.AutorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by jakub on 30.05.2016.
 */

@RestController
public class AutorController {
    //CRUD control

    @Autowired
    BaseAutorRepository baseAutorRepository;

    @RequestMapping(method = RequestMethod.POST, value = ServerApi.AUTOR_PATH)
    public ResponseEntity<AutorEntity> createAutor(@RequestBody AutorEntity autorEntity) {
        autorEntity.SetDate(new Date());
        baseAutorRepository.save(autorEntity);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @RequestMapping(method = RequestMethod.DELETE, value = ServerApi.AUTOR_ID_PATH)
    public ResponseEntity<AutorEntity> deleteAutor(@PathVariable UUID id) {
        AutorEntity autorEntity = baseAutorRepository.findOne(id);
        if (autorEntity == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            baseAutorRepository.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = ServerApi.AUTOR_PATH)
    public ResponseEntity<List<AutorEntity>> getAllAutors() {
        return new ResponseEntity<>(baseAutorRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = ServerApi.AUTOR_ID_PATH)
    public ResponseEntity<AutorEntity>  getAutor(@PathVariable UUID id) {
        return new ResponseEntity<>(baseAutorRepository.findOne(id), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = ServerApi.AUTOR_GETBYNAME_PATH)
    public ResponseEntity<List<AutorEntity>> getAutorByName(@PathVariable String name) {
        return new ResponseEntity<>(baseAutorRepository.findByName(name), HttpStatus.OK);
    }

    @RequestMapping(value = ServerApi.AUTOR_ID_PATH, method=RequestMethod.PUT)
    public ResponseEntity<AutorEntity> updateAuthor(@PathVariable UUID id, @RequestBody AutorEntity autor) {
        AutorEntity autorUp = baseAutorRepository.findOne(id);
        autorUp.setName(autor.getName());
        baseAutorRepository.save(autorUp);
        return new ResponseEntity<>(autorUp, HttpStatus.OK);
    }


}
