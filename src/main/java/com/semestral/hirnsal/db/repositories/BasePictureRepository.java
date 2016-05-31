package com.semestral.hirnsal.db.repositories;

import com.semestral.hirnsal.db.tables.PictureEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Created by jakub on 27.05.2016.
 */

@Repository
public interface BasePictureRepository  extends CrudRepository<PictureEntity, UUID>{
    List<PictureEntity> findAll();
    List<PictureEntity> findByAutorId(UUID id);
    List<PictureEntity> findByName(String name);
    List<PictureEntity> findByTagsTagText(String tag);

    PictureEntity findFirstByIdLessThanOrderByIdDesc(UUID id);

    PictureEntity findFirstByIdGreaterThanOrderByIdAsc(UUID id);

}
