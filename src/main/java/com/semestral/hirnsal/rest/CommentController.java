package com.semestral.hirnsal.rest;

import com.semestral.hirnsal.SemestralJhApplication;
import com.semestral.hirnsal.client.ServerApi;
import com.semestral.hirnsal.db.tables.CommentEntity;
import com.semestral.hirnsal.service.CommentService;
import com.semestral.hirnsal.service.PictureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Created by jakub on 29.05.2016.
 */

@RestController
public class CommentController {
    //CRUD control

    private static final Logger logger = LoggerFactory.getLogger(SemestralJhApplication.class);
    CommentService commentService;
    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }
    PictureService pictureService;
    @Autowired
    public void setPictureService(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    //create
    @RequestMapping(value = ServerApi.COMMENT_PATH, method = RequestMethod.POST)
    public ResponseEntity<CommentEntity> addCommentMethod(@RequestBody CommentEntity commentEntity){
        if (pictureService.hasPicture(commentEntity.getCommentedPicture().getId())) {
            commentService.create(commentEntity);
            return new ResponseEntity<>(commentEntity, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //retrieve
    @RequestMapping(value = ServerApi.COMMENT_GETALL_PATH, method = RequestMethod.GET)
    public ResponseEntity<List<CommentEntity>> showComments() {
        List<CommentEntity> comments = commentService.getCurrent();
        return new ResponseEntity<>(comments,HttpStatus.OK);
    }


    //retrieve
    @RequestMapping(value = ServerApi.COMMENT_GETONEID_PATH, method = RequestMethod.GET)
    public ResponseEntity<CommentEntity> getComment(@PathVariable("id") UUID id){
        CommentEntity commentEntity = commentService.getComment(id);
        if ( commentEntity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else
            return new ResponseEntity<>(commentEntity,HttpStatus.OK);
    }

    //delete
    @RequestMapping(value = ServerApi.COMMENT_ID_PATH, method = RequestMethod.DELETE)
    public ResponseEntity<CommentEntity> deleteComment(@PathVariable("id") UUID id){
        CommentEntity commentEntity = commentService.getComment(id);
        if (commentEntity == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            commentService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value={ServerApi.COMMENT_GIVELIKEID_PATH}, method = RequestMethod.GET)
    public long giveLikeToComment(@PathVariable UUID id) {
        CommentEntity commentEntity = commentService.getComment(id);
        logger.debug("Picture id ="+id);
        if (commentEntity != null) {
            logger.debug("Comment id ="+id);
            long count = commentService.incrementLikes(commentEntity);
            logger.debug("Incremented to ="+count);
            return count;
        } else {
            logger.warn("Picture ("+id+") was not found. Mapping="+ServerApi.COMMENT_GIVELIKEID_PATH);
            return -1;
        }
    }

    @RequestMapping(method = RequestMethod.GET, value=ServerApi.COMMENT_GIVEDISLIKEID_PATH)
    public long giveDislikeToPicture(@PathVariable UUID id) {
        CommentEntity commentEntity = commentService.getComment(id);
        if (commentEntity != null) {
            return commentService.incrementDisLikes(commentEntity);
        } else {
            logger.warn("Picture ("+id+") was not found. Mapping="+ServerApi.COMMENT_GIVEDISLIKEID_PATH);
            return -1;
        }
    }
}
