package com.semestral.hirnsal.rest;

import com.semestral.hirnsal.db.tables.CommentTable;
import com.semestral.hirnsal.service.CommentService;
import com.semestral.hirnsal.service.PictureService;
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
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public ResponseEntity<CommentTable> addCommentMethod(@RequestBody CommentTable commentTable){
        if (pictureService.hasPicture(commentTable.getCommentedPicture().getId())) {
            commentService.create(commentTable);
            return new ResponseEntity<>(commentTable, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //retrieve
    @RequestMapping(value = "/comment", method = RequestMethod.GET)
    public ResponseEntity<List<CommentTable>> showOffers() {
        List<CommentTable> comments = commentService.getCurrent();
        return new ResponseEntity<>(comments,HttpStatus.OK);
    }


    //retrieve
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public ResponseEntity<CommentTable> getComment(@PathVariable("id") UUID id){
        CommentTable commentTable = commentService.getComment(id);
        if ( commentTable == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else
            return new ResponseEntity<>(commentTable,HttpStatus.OK);
    }

    //delete
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<CommentTable> deleteComment(@PathVariable("id") UUID id){
        CommentTable commentTable = commentService.getComment(id);
        if (commentTable == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            commentService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
