package com.semestral.hirnsal.db.repositories;

import com.semestral.hirnsal.db.tables.PictureTable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Created by jakub on 27.05.2016.
 */

@Repository
public interface BasePictureRepository  extends CrudRepository<PictureTable, UUID>{
    List<PictureTable> findAll();
    List<PictureTable> findByAutorId(UUID id);
    List<PictureTable> findByName(String name);
    //List<PictureTable> findByTag(String tag);

    PictureTable findFirstByIdLessThanOrderByIdDesc(UUID id);

    PictureTable findFirstByIdGreaterThanOrderByIdAsc(UUID id);

}
