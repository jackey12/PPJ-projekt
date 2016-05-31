package com.semestral.hirnsal.mvc;
import com.semestral.hirnsal.client.ServerApi;
import com.semestral.hirnsal.db.tables.CommentEntity;
import com.semestral.hirnsal.db.tables.PictureEntity;
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
        PictureEntity actualPicture;
        if(id == null) {
            int count = pictureService.getCurrent().size();
            Random rand = new Random();
            int randomNum =  rand.nextInt(count - 1 );
            //actualPicture = basePictureRepository.findAll().get(randomNum);
            actualPicture = pictureService.getCurrent().get(randomNum);
            id = actualPicture.getId();
        }else{
            //actualPicture = basePictureRepository.findOne(id);
            actualPicture = pictureService.getPicture(id);
        }

        //PictureEntity previousPicture = basePictureRepository.findFirstByIdLessThanOrderByIdDesc(id);
        PictureEntity previousPicture = pictureService.getPreviousPicture(id);
        //PictureEntity nextPicture = basePictureRepository.findFirstByIdGreaterThanOrderByIdAsc(id);
        PictureEntity nextPicture = pictureService.getNextPicture(id);

        List<CommentEntity> comments = commentService.getRelatedComments(actualPicture.getId());
        model.addAttribute("previousPicture", previousPicture);
        model.addAttribute("actualPicture", actualPicture);
        model.addAttribute("nextPicture", nextPicture);
        model.addAttribute("comments", comments);
        model.addAttribute("serverApi", ServerApi.class);

        return "home";
    }


    @RequestMapping(ServerApi.HOME_COMMENT_GIVELIKE_PATH)
    public String giveLikeToComment(@RequestParam(name = "id", defaultValue = "") UUID id) {
        if(id != null){
            CommentEntity comment = commentService.getComment(id);
            if (comment != null) {
                commentService.incrementLikes(comment);
                id = comment.getCommentedPicture().getId();
            }
            return "redirect:/home?id="+id;
        }else{
            return "redirect:/home";
        }
    }

    @RequestMapping(ServerApi.HOME_COMMENT_GIVEDISLIKE_PATH)
    public String giveDisLikeToComment(@RequestParam(name = "id", defaultValue = "") UUID id) {
        if(id != null){
            CommentEntity comment = commentService.getComment(id);
            if (comment != null) {
                commentService.incrementDisLikes(comment);
                id = comment.getCommentedPicture().getId();
            }
            return "redirect:/home?id="+id;
        }else{
            return "redirect:/home";
        }
    }

    @RequestMapping(ServerApi.HOME_PICTURE_GIVELIKE_PATH)
    public String giveLikeToPicture(@RequestParam(name = "id", defaultValue = "") UUID id) {
        if(id != null){
            PictureEntity picture = pictureService.getPicture(id);
            if (picture != null) {
                pictureService.incrementLikes(picture);
            }
            return "redirect:/home?id="+id;
        }else{
            return "redirect:/home";
        }
    }

    @RequestMapping(ServerApi.HOME_PICTURE_GIVEDISLIKE_PATH)
    public String giveDisLikeToPicture(@RequestParam(name = "id", defaultValue = "") UUID id) {
        if(id != null){
            PictureEntity picture = pictureService.getPicture(id);
            if (picture != null) {
                pictureService.incrementDisLikes(picture);
            }
            return "redirect:/home?id="+id;
        }else{
            return "redirect:/home";
        }
    }
}
