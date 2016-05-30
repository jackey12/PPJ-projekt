package com.semestral.hirnsal.mvc;
import com.semestral.hirnsal.client.ServerApi;
import com.semestral.hirnsal.db.repositories.BaseCommentRepository;
import com.semestral.hirnsal.db.repositories.BasePictureRepository;
import com.semestral.hirnsal.db.tables.CommentTable;
import com.semestral.hirnsal.db.tables.PictureTable;
import com.semestral.hirnsal.service.CommentService;
import com.semestral.hirnsal.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Created by jakub on 25.05.2016.
 */
@Controller
public class DefaultController {

    PictureService pictureService;
    CommentService commentService;

    @Autowired
    public void setPictureService(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }


    @RequestMapping(value = {"/", "/home"})
    public String home(@RequestParam(required = false, defaultValue = "", value = "id") UUID id, Model model) {
        PictureTable actualPicture;
        if(id == null) {
            int count = pictureService.getCurrent().size();
            Random rand = new Random();
            int randomNum = 1 + rand.nextInt((count - 1) + 1);
            //actualPicture = basePictureRepository.findAll().get(randomNum);
            actualPicture = pictureService.getCurrent().get(randomNum);
            id = actualPicture.getId();
        }else{
            //actualPicture = basePictureRepository.findOne(id);
            actualPicture = pictureService.getPicture(id);
        }

        //PictureTable previousPicture = basePictureRepository.findFirstByIdLessThanOrderByIdDesc(id);
        PictureTable previousPicture = pictureService.getPreviousPicture(id);
        //PictureTable nextPicture = basePictureRepository.findFirstByIdGreaterThanOrderByIdAsc(id);
        PictureTable nextPicture = pictureService.getNextPicture(id);

        List<CommentTable> comments = commentService.getRelatedComments(actualPicture.getId());
        model.addAttribute("previousPicture", previousPicture);
        model.addAttribute("actualPicture", actualPicture);
        model.addAttribute("nextPicture", nextPicture);
        model.addAttribute("comments", comments);
        model.addAttribute("serverApi", ServerApi.class);

        return "home";
    }
}
