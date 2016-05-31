package com.semestral.hirnsal.db.repositories;

import com.semestral.hirnsal.db.tables.CommentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

/**
 * Created by jakub on 27.05.2016.
 */
@Repository
public interface BaseCommentRepository extends CrudRepository<CommentEntity, UUID> {
    List<CommentEntity> findAll();
    List<CommentEntity> findByPictureId(UUID id);
}
