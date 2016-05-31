package com.semestral.hirnsal.service;

import com.semestral.hirnsal.db.tables.PictureEntity;
import com.semestral.hirnsal.db.repositories.BasePictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

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


    public List<PictureEntity> getCurrent() {
        return basePictureRepository.findAll();
    }

    public void create (PictureEntity picture){
        basePictureRepository.save(picture);
    }

    public boolean hasPicture(UUID id) {

        if (id == null) {
            return false;
        }

        return basePictureRepository.findOne(id) != null;
    }

    public PictureEntity getPicture(String name) {

        if (name == null) {
            return null;
        }

        List<PictureEntity> pictures = basePictureRepository.findByName(name);

        if (pictures.size() == 0) {
            return null;
        }

        return pictures.get(0);
    }
    public PictureEntity getPicture(UUID id){
        return basePictureRepository.findOne(id);
    }
    public PictureEntity getPreviousPicture(UUID id){
        return basePictureRepository.findFirstByIdLessThanOrderByIdDesc(id);
    }
    public PictureEntity getNextPicture(UUID id){
       return basePictureRepository.findFirstByIdGreaterThanOrderByIdAsc(id);
    }

    public long incrementLikes(PictureEntity pic){
        Date date = new Date();
        pic.setLikesCount(pic.getLikesCount()+1);
        pic.setLastUpdate(date);
        saveOrUpdate(pic);
        return pic.getLikesCount();
    }

    public long incrementDisLikes(PictureEntity pic){
        Date date = new Date();
        pic.setDislikesCount(pic.getDislikesCount()+1);
        pic.setLastUpdate(date);
        saveOrUpdate(pic);
        return pic.getLikesCount();
    }

    public void saveOrUpdate(PictureEntity picture) {
        basePictureRepository.save(picture);
    }

    public void delete(UUID id) {
        basePictureRepository.delete(id);
    }
}
