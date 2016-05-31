package com.semestral.hirnsal.db.repositories;

import com.semestral.hirnsal.db.tables.PictureTagEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.UUID;

/**
 * Created by jakub on 29.05.2016.
 */

@Repository
public interface BasePictureTagRepository extends CrudRepository<PictureTagEntity, UUID> {
    List<PictureTagEntity> findAll();
    List<PictureTagEntity> findByPictureId(UUID id);
}
