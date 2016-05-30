package com.semestral.hirnsal.db.repositories;

import com.semestral.hirnsal.db.tables.PictureTagTable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.UUID;

/**
 * Created by jakub on 29.05.2016.
 */

@Repository
public interface BasePictureTagRepository extends CrudRepository<PictureTagTable, UUID> {
    List<PictureTagTable> findAll();
    List<PictureTagTable> findByPictureId(UUID id);
}
