package com.semestral.hirnsal.rest;

import com.semestral.hirnsal.SemestralJhApplication;
import com.semestral.hirnsal.client.ServerApi;
import com.semestral.hirnsal.db.repositories.BaseCommentRepository;
import com.semestral.hirnsal.db.repositories.BasePictureRepository;
import com.semestral.hirnsal.db.tables.CommentEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by jakub on 29.05.2016.
 */

@RestController
public class CommentController {
    //CRUD control

    private static final Logger logger = LoggerFactory.getLogger(SemestralJhApplication.class);
    @Autowired
    BasePictureRepository basePictureRepository;
    @Autowired
    BaseCommentRepository baseCommentRepository;

    //create
    @RequestMapping(value = ServerApi.COMMENT_PATH, method = RequestMethod.POST)
    public ResponseEntity<CommentEntity> addCommentMethod(@RequestBody CommentEntity commentEntity){
        if ((basePictureRepository.findOne(commentEntity.getCommentedPicture().getId())) != null) {
            baseCommentRepository.save(commentEntity);
            return new ResponseEntity<>(commentEntity, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = ServerApi.COMMENT_ID_PATH, method = RequestMethod.PUT)
    public ResponseEntity<CommentEntity> updateCommentMethod(@PathVariable UUID id, @RequestBody CommentEntity commentEntity) {
        CommentEntity commentUp = baseCommentRepository.findOne(id);
        commentUp.setCommentText(commentEntity.getCommentText());
        commentUp.setLastUpdate(new Date());
        baseCommentRepository.save(commentUp);
        return new ResponseEntity<>(commentUp, HttpStatus.OK);
    }

    //retrieve
    @RequestMapping(value = ServerApi.COMMENT_PATH, method = RequestMethod.GET)
    public ResponseEntity<List<CommentEntity>> showComments() {
        List<CommentEntity> comments = baseCommentRepository.findAll();
        return new ResponseEntity<>(comments,HttpStatus.OK);
    }


    //delete
    @RequestMapping(value = ServerApi.COMMENT_ID_PATH, method = RequestMethod.DELETE)
    public ResponseEntity<CommentEntity> deleteComment(@PathVariable("id") UUID id){
        CommentEntity commentEntity = baseCommentRepository.findOne(id);
        if (commentEntity == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            baseCommentRepository.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value={ServerApi.COMMENT_GIVELIKEID_PATH}, method = RequestMethod.PUT)
    public ResponseEntity<Long> giveLikeToComment(@PathVariable UUID id) {
        CommentEntity commentEntity = baseCommentRepository.findOne(id);
        logger.debug("Picture id ="+id);
        if (commentEntity != null) {
            logger.debug("Comment id ="+id);
            commentEntity.setLikesCount(commentEntity.getLikesCount()+1);
            commentEntity.setLastUpdate(new Date());
            baseCommentRepository.save(commentEntity);
            long count = commentEntity.getLikesCount();
            logger.debug("Incremented to ="+count);
            return new ResponseEntity<>(count, HttpStatus.OK);
        } else {
            logger.warn("Picture ("+id+") was not found. Mapping="+ServerApi.COMMENT_GIVELIKEID_PATH);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value=ServerApi.COMMENT_GIVEDISLIKEID_PATH)
    public ResponseEntity<Long> giveDislikeToComment(@PathVariable UUID id) {
        CommentEntity commentEntity = baseCommentRepository.findOne(id);
        if (commentEntity != null) {
            commentEntity.setDislikesCount(commentEntity.getDislikesCount()+1);
            commentEntity.setLastUpdate(new Date());
            baseCommentRepository.save(commentEntity);
            long count =  commentEntity.getLikesCount();
            return new ResponseEntity<>(count, HttpStatus.OK);
        } else {
            logger.warn("Picture ("+id+") was not found. Mapping="+ServerApi.COMMENT_GIVEDISLIKEID_PATH);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //retrieve
    @RequestMapping(value = ServerApi.COMMENT_ID_PATH, method = RequestMethod.GET)
    public ResponseEntity<CommentEntity> getCommentByID(@PathVariable("id") UUID id){
        CommentEntity commentEntity = baseCommentRepository.findOne(id);
        if (commentEntity == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
        return new ResponseEntity<>(commentEntity, HttpStatus.OK);
    }
}
