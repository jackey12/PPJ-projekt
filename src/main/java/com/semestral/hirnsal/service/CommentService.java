package com.semestral.hirnsal.service;

import com.semestral.hirnsal.db.repositories.BaseCommentRepository;
import com.semestral.hirnsal.db.tables.CommentTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    public List<CommentTable> getCurrent() {
        return StreamSupport.stream(baseCommentRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public List<CommentTable> getRelatedComments(UUID idpic){
        return baseCommentRepository.findByPictureId(idpic);
    }

    public void create(CommentTable commentTable){
        baseCommentRepository.save(commentTable);
    }

    public CommentTable getComment(UUID id){
        return baseCommentRepository.findOne(id);
    }

    public void delete(UUID id){
        baseCommentRepository.delete(id);
    }

}
