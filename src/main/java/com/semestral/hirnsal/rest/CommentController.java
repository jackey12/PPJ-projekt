package com.semestral.hirnsal.rest;

import com.semestral.hirnsal.SemestralJhApplication;
import com.semestral.hirnsal.client.ServerApi;
import com.semestral.hirnsal.db.tables.CommentTable;
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
    public ResponseEntity<CommentTable> addCommentMethod(@RequestBody CommentTable commentTable){
        if (pictureService.hasPicture(commentTable.getCommentedPicture().getId())) {
            commentService.create(commentTable);
            return new ResponseEntity<>(commentTable, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //retrieve
    @RequestMapping(value = ServerApi.COMMENT_GETALL_PATH, method = RequestMethod.GET)
    public ResponseEntity<List<CommentTable>> showComments() {
        List<CommentTable> comments = commentService.getCurrent();
        return new ResponseEntity<>(comments,HttpStatus.OK);
    }


    //retrieve
    @RequestMapping(value = ServerApi.COMMENT_GETONEID_PATH, method = RequestMethod.GET)
    public ResponseEntity<CommentTable> getComment(@PathVariable("id") UUID id){
        CommentTable commentTable = commentService.getComment(id);
        if ( commentTable == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else
            return new ResponseEntity<>(commentTable,HttpStatus.OK);
    }

    //delete
    @RequestMapping(value = ServerApi.COMMENT_ID_PATH, method = RequestMethod.DELETE)
    public ResponseEntity<CommentTable> deleteComment(@PathVariable("id") UUID id){
        CommentTable commentTable = commentService.getComment(id);
        if (commentTable == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            commentService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value={ServerApi.COMMENT_GIVELIKEID_PATH}, method = RequestMethod.GET)
    public long giveLikeToComment(@PathVariable UUID id) {
        CommentTable commentTable  = commentService.getComment(id);
        logger.debug("Picture id ="+id);
        if (commentTable != null) {
            logger.debug("Comment id ="+id);
            long count = commentService.incrementLikes(commentTable);
            logger.debug("Incremented to ="+count);
            return count;
        } else {
            logger.warn("Picture ("+id+") was not found. Mapping="+ServerApi.COMMENT_GIVELIKEID_PATH);
            return -1;
        }
    }

    @RequestMapping(method = RequestMethod.GET, value=ServerApi.COMMENT_GIVEDISLIKEID_PATH)
    public long giveDislikeToPicture(@PathVariable UUID id) {
        CommentTable commentTable = commentService.getComment(id);
        if (commentTable != null) {
            return commentService.incrementDisLikes(commentTable);
        } else {
            logger.warn("Picture ("+id+") was not found. Mapping="+ServerApi.COMMENT_GIVEDISLIKEID_PATH);
            return -1;
        }
    }
}
