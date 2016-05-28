package com.semestral.hirnsal.db.repositories;

import com.semestral.hirnsal.db.tables.PictureTable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jakub on 27.05.2016.
 */
@Repository
public interface BasePictureRepository  extends CrudRepository<PictureTable, Integer>{
    List<PictureTable> findAll();
    List<PictureTable> findByAutorId(int id);
    List<PictureTable> findByName(String name);
    //List<PictureTable> findByTag(String tag);

    PictureTable findFirstByIdLessThanOrderByIdDesc(int id);

    PictureTable findFirstByIdGreaterThanOrderByIdAsc(int id);

}
