package com.semestral.hirnsal.service;

import com.semestral.hirnsal.db.repositories.BasePictureTagRepository;
import com.semestral.hirnsal.db.tables.PictureTable;
import com.semestral.hirnsal.db.repositories.BasePictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
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
        return basePictureRepository.findAll();
    }

    public void create (PictureTable picture){
        basePictureRepository.save(picture);
    }

    public boolean hasPicture(UUID id) {

        if (id == null) {
            return false;
        }

        return basePictureRepository.findOne(id) != null;
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
    public PictureTable getPicture(UUID id){
        return basePictureRepository.findOne(id);
    }
    public PictureTable getPreviousPicture(UUID id){
        return basePictureRepository.findFirstByIdLessThanOrderByIdDesc(id);
    }
    public PictureTable getNextPicture(UUID id){
       return basePictureRepository.findFirstByIdGreaterThanOrderByIdAsc(id);
    }

    public long incrementLikes(PictureTable pic){
        pic.setLikesCount(pic.getLikesCount()+1);
        saveOrUpdate(pic);
        return pic.getLikesCount();
    }

    public long incrementDisLikes(PictureTable pic){
        pic.setDislikesCount(pic.getDislikesCount()+1);
        saveOrUpdate(pic);
        return pic.getLikesCount();
    }

    public void saveOrUpdate(PictureTable picture) {
        basePictureRepository.save(picture);
    }

    public void delete(UUID id) {
        basePictureRepository.delete(id);
    }
}
