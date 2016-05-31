package com.semestral.hirnsal.rest;

import com.semestral.hirnsal.client.ServerApi;
import com.semestral.hirnsal.db.tables.AutorEntity;
import com.semestral.hirnsal.service.AutorService;
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

    AutorService autorService;
    @Autowired
    public void setAutorServicee(AutorService autorService) {
        this.autorService = autorService;
    }

    @RequestMapping(method = RequestMethod.POST, value = ServerApi.AUTOR_PATH)
    public ResponseEntity<AutorEntity> createAutor(@RequestBody AutorEntity autorEntity) {
        autorEntity.SetDate(new Date());
        autorService.create(autorEntity);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @RequestMapping(method = RequestMethod.DELETE, value = ServerApi.AUTOR_ID_PATH)
    public ResponseEntity<AutorEntity> deleteAutor(@PathVariable UUID id) {
        AutorEntity autorEntity = autorService.getAutor(id);
        if (autorEntity == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            autorService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = ServerApi.AUTOR_PATH)
    public ResponseEntity<List<AutorEntity>> getAllAutors() {
        return new ResponseEntity<>(autorService.getAllAutors(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = ServerApi.AUTOR_ID_PATH)
    public ResponseEntity<AutorEntity>  getAutor(@PathVariable UUID id) {
        return new ResponseEntity<>(autorService.getAutor(id), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = ServerApi.AUTOR_GETBYNAME_PATH)
    public ResponseEntity<List<AutorEntity>> getAutorByName(@PathVariable String name) {
        autorService.getAutorsByName(name);
        return new ResponseEntity<>(autorService.getAutorsByName(name), HttpStatus.OK);
    }


}
