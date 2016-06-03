package com.semestral.hirnsal.mvc;
import com.semestral.hirnsal.client.ServerApi;
import com.semestral.hirnsal.db.repositories.BaseCommentRepository;
import com.semestral.hirnsal.db.repositories.BasePictureRepository;
import com.semestral.hirnsal.db.tables.CommentEntity;
import com.semestral.hirnsal.db.tables.PictureEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Created by jakub on 25.05.2016.
 */
@Controller
public class DefaultController {

    @Autowired
    BasePictureRepository basePictureRepository;
    @Autowired
    BaseCommentRepository baseCommentRepository;


    @RequestMapping(value = {"/", "/home"})
    public String home(@RequestParam(required = false, defaultValue = "", value = "id") UUID id, Model model) {
        PictureEntity actualPicture;
        if(id == null) {
            int count = basePictureRepository.findAll().size();
            Random rand = new Random();
            int randomNum =  rand.nextInt(count - 1 );
            actualPicture = basePictureRepository.findAll().get(randomNum);
            id = actualPicture.getId();
        }else{
            actualPicture = basePictureRepository.findOne(id);
        }


        PictureEntity previousPicture = basePictureRepository.findFirstByIdLessThanOrderByIdDesc(id);
        PictureEntity nextPicture = basePictureRepository.findFirstByIdGreaterThanOrderByIdAsc(id);

        List<CommentEntity> comments = baseCommentRepository.findByPictureId(actualPicture.getId());
        model.addAttribute("previousPicture", previousPicture);
        model.addAttribute("actualPicture", actualPicture);
        model.addAttribute("nextPicture", nextPicture);
        model.addAttribute("comments", comments);
        model.addAttribute("serverApi", ServerApi.class);

        return "home";
    }


    @RequestMapping(ServerApi.HOME_COMMENT_GIVELIKE_PATH)
    public String giveLikeToCommentHome(@RequestParam(name = "id", defaultValue = "") UUID id) {
        if(id != null){
            CommentEntity comment = baseCommentRepository.findOne(id);
            if (comment != null) {
                comment.setLikesCount(comment.getLikesCount()+1);
                comment.setLastUpdate(new Date());
                baseCommentRepository.save(comment);
                id = comment.getCommentedPicture().getId();
            }
            return "redirect:/home?id="+id;
        }else{
            return "redirect:/home";
        }
    }

    @RequestMapping(ServerApi.HOME_COMMENT_GIVEDISLIKE_PATH)
    public String giveDisLikeToCommentHome(@RequestParam(name = "id", defaultValue = "") UUID id) {
        if(id != null){
            CommentEntity comment = baseCommentRepository.findOne(id);
            if (comment != null) {
                comment.setDislikesCount(comment.getDislikesCount()+1);
                comment.setLastUpdate(new Date());
                baseCommentRepository.save(comment);
                id = comment.getCommentedPicture().getId();
            }
            return "redirect:/home?id="+id;
        }else{
            return "redirect:/home";
        }
    }

    @RequestMapping(ServerApi.HOME_PICTURE_GIVELIKE_PATH)
    public String giveLikeToPictureHome(@RequestParam(name = "id", defaultValue = "") UUID id) {
        if(id != null){
            PictureEntity picture = basePictureRepository.findOne(id);
            if (picture != null) {
                Date date = new Date();
                picture.setLikesCount(picture.getLikesCount()+1);
                picture.setLastUpdate(date);
                basePictureRepository.save(picture);
            }
            return "redirect:/home?id="+id;
        }else{
            return "redirect:/home";
        }
    }

    @RequestMapping(ServerApi.HOME_PICTURE_GIVEDISLIKE_PATH)
    public String giveDisLikeToPictureHome(@RequestParam(name = "id", defaultValue = "") UUID id) {
        if(id != null){
            PictureEntity picture = basePictureRepository.findOne(id);
            if (picture != null) {
                Date date = new Date();
                picture.setDislikesCount(picture.getDislikesCount()+1);
                picture.setLastUpdate(date);
                basePictureRepository.save(picture);
            }
            return "redirect:/home?id="+id;
        }else{
            return "redirect:/home";
        }
    }
}
