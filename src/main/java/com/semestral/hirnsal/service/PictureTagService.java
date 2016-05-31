package com.semestral.hirnsal.service;

import com.semestral.hirnsal.db.repositories.BasePictureTagRepository;
import com.semestral.hirnsal.db.tables.PictureTagEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jakub on 29.05.2016.
 */

@Service
public class PictureTagService {

    private final BasePictureTagRepository basePictureTagRepository;

    @Autowired
    public PictureTagService( BasePictureTagRepository basePictureTagRepository){
        this.basePictureTagRepository = basePictureTagRepository;
    }

    public void createTag (PictureTagEntity picture){
        basePictureTagRepository.save(picture);
    }

    public void creteTags (List<PictureTagEntity> tags){
        for (PictureTagEntity tag:tags) {
            basePictureTagRepository.save(tag);
        }
    }


}
