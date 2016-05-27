package com.semestral.hirnsal.service;

import com.semestral.hirnsal.db.tables.PictureTable;
import com.semestral.hirnsal.db.repositories.BasePictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by jakub on 27.05.2016.
 */
@Service
public class PictureService {

    private final BasePictureRepository basePictureRepository;

    @Autowired
    public PictureService(BasePictureRepository basePictureRepository){
        this.basePictureRepository = basePictureRepository;
    }

    public List<PictureTable> getCurrent() {
        return StreamSupport.stream(basePictureRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public void create (PictureTable picture){
        basePictureRepository.save(picture);
    }

    public boolean hasPicture(String name) {

        if (name== null) {
            return false;
        }

        return basePictureRepository.findByName(name).size() != 0;
    }

    public PictureTable getPicture(String name) {

        if (name == null) {
            return null;
        }

        List<PictureTable> pictures = basePictureRepository.findByName(name);

        if (pictures.size() == 0) {
            return null;
        }

        return pictures.get(0);
    }

    public void saveOrUpdate(PictureTable picture) {
        basePictureRepository.save(picture);
    }

    public void delete(int id) {
        basePictureRepository.delete(id);
    }
}