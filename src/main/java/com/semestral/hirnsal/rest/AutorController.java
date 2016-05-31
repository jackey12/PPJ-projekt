package com.semestral.hirnsal.rest;

import com.semestral.hirnsal.client.ServerApi;
import com.semestral.hirnsal.db.tables.AutorEntity;
import com.semestral.hirnsal.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

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
    public void createAuthor(@RequestBody AutorEntity autorEntity) {
        autorEntity.SetDate(new Date());
        autorService.create(autorEntity);
    }

}
