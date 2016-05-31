package com.semestral.hirnsal.service;

import com.semestral.hirnsal.db.tables.PictureEntity;
import com.semestral.hirnsal.db.repositories.BasePictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by jakub on 27.05.2016.
 */
@Service
@Transactional
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

    public List<PictureEntity> getPictureByName(String name) {

        List<PictureEntity> pictures = new ArrayList<>();

        if (name == null) {
            return pictures;
        }
        pictures = basePictureRepository.findByName(name);

        return pictures;
    }
    public PictureEntity getPicture(UUID id){
        return basePictureRepository.findOne(id);
    }

    public List<PictureEntity> getPictureByTag(String tag){
        return basePictureRepository.findByTagsTagText(tag);
    }

    public List<PictureEntity> getPictureByAutor(UUID id){
        return basePictureRepository.findByAutorId(id);
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
