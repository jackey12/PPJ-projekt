package com.semestral.hirnsal.db.repositories;

import com.semestral.hirnsal.db.tables.CommentTable;
import com.semestral.hirnsal.db.tables.PictureTable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

/**
 * Created by jakub on 27.05.2016.
 */
@Repository
public interface BaseCommentRepository extends CrudRepository<CommentTable, UUID> {
    List<CommentTable> findAll();
    List<CommentTable> findByPictureId(UUID id);
}
