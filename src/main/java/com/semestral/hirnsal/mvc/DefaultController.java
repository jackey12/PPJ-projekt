package com.semestral.hirnsal.mvc;
import com.semestral.hirnsal.db.repositories.BaseCommentRepository;
import com.semestral.hirnsal.db.repositories.BasePictureRepository;
import com.semestral.hirnsal.db.tables.CommentTable;
import com.semestral.hirnsal.db.tables.PictureTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Random;

/**
 * Created by jakub on 25.05.2016.
 */
@Controller
public class DefaultController {

    @Autowired
    BasePictureRepository basePictureRepository;

    @Autowired
    BaseCommentRepository baseCommentRepositoryy;

    @RequestMapping("/")
    public String home(@RequestParam(required = false, defaultValue = "1", value = "id") int id, Model model) {
        PictureTable actualPicture;
        if(id == -1) {
            int count = basePictureRepository.findAll().size();
            Random rand = new Random();
            int randomNum = 1 + rand.nextInt((count - 1) + 1);
            id = randomNum;
            actualPicture = basePictureRepository.findAll().get(randomNum);
        }else{
            actualPicture = basePictureRepository.findAll().get(id);
        }

        PictureTable previousPicture = basePictureRepository.findFirstByIdLessThanOrderByIdDesc(id);
        PictureTable nextPicture = basePictureRepository.findFirstByIdGreaterThanOrderByIdAsc(id);

        List<CommentTable> comments = baseCommentRepositoryy.findByPicture(actualPicture);
        model.addAttribute("previousPicture", previousPicture);
        model.addAttribute("actualPicture", actualPicture);
        model.addAttribute("nextPicture", nextPicture);
        model.addAttribute("comments", comments);

        return "home";
    }
}
