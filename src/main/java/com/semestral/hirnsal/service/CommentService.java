package com.semestral.hirnsal.service;

import com.semestral.hirnsal.db.repositories.BaseCommentRepository;
import com.semestral.hirnsal.db.tables.CommentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by jakub on 29.05.2016.
 */

@Service
public class CommentService {

    private final BaseCommentRepository baseCommentRepository;

    @Autowired
    public CommentService(BaseCommentRepository baseCommentRepository){
        this.baseCommentRepository = baseCommentRepository;
    }


    public List<CommentEntity> getCurrent() {
        return StreamSupport.stream(baseCommentRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public List<CommentEntity> getRelatedComments(UUID idpic){
        return baseCommentRepository.findByPictureId(idpic);
    }

    public void create(CommentEntity commentEntity){
        baseCommentRepository.save(commentEntity);
    }

    public CommentEntity getComment(UUID id){
        return baseCommentRepository.findOne(id);
    }

    public void delete(UUID id){
        baseCommentRepository.delete(id);
    }


    public long incrementLikes(CommentEntity comment){
        comment.setLikesCount(comment.getLikesCount()+1);
        comment.setLastUpdate(new Date());
        saveOrUpdate(comment);
        return comment.getLikesCount();
    }

    public long incrementDisLikes(CommentEntity comment){
        comment.setDislikesCount(comment.getDislikesCount()+1);
        comment.setLastUpdate(new Date());
        saveOrUpdate(comment);
        return comment.getLikesCount();
    }

    public void saveOrUpdate(CommentEntity comment) {
        baseCommentRepository.save(comment);
    }
}
