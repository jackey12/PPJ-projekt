package com.semestral.hirnsal.rest;

import com.semestral.hirnsal.SemestralJhApplication;
import com.semestral.hirnsal.client.*;

import com.semestral.hirnsal.db.tables.AutorTable;
import com.semestral.hirnsal.db.tables.PictureTable;
import com.semestral.hirnsal.db.tables.PictureTagTable;
import com.semestral.hirnsal.service.AutorService;
import com.semestral.hirnsal.service.CommentService;
import com.semestral.hirnsal.service.PictureService;
import com.semestral.hirnsal.service.PictureTagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.tools.JavaFileManager;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by jakub on 29.05.2016.
 */

@RestController
public class PictureController {
    //CRUD control

    PictureService pictureService;
    PictureTagService pictureTagService;
    AutorService autorService;
    @Value("${dataimg.path}")
    String dataImgPath;
    @Autowired
    ServletContext context;
    private static final Logger logger = LoggerFactory.getLogger(SemestralJhApplication.class);

    private final String filePrefix = "file://";
    @Autowired
    public void setPictureService(PictureService pictureService) {
        this.pictureService = pictureService;
    }
    @Autowired
    public void setAutorService(AutorService autorService) {
        this.autorService = autorService;
    }
    @Autowired
    public void sePictureTagService(PictureTagService pictureTagService) {
        this.pictureTagService = pictureTagService;
    }


    @RequestMapping(value = ServerApi.UPLOAD_PATH, method = RequestMethod.POST)
    public
    @ResponseBody
    ImageStatus uploadImage(@PathVariable("name") String name,
                            @RequestParam("data") MultipartFile imageData,
                            @RequestParam(value = "tags", required = false)List<String> tags,
                            @RequestParam(value = "iDautor", required = false)UUID iDautor,
                            HttpServletResponse response) {

        ImageStatus state = new ImageStatus(ImageStatus.ImageState.READY);

        try {
            if(! new File( this.dataImgPath).exists())
            {
                new File( this.dataImgPath).mkdir();
            }
            String fullPathName = this.dataImgPath + "/"+imageData.getOriginalFilename();
            Date date = new Date();
            PictureTable pictureTable = new PictureTable(UUID.randomUUID(), name, this.filePrefix + fullPathName ,date);


            Path target = Paths.get(this.dataImgPath).resolve(imageData.getOriginalFilename());
            Files.copy(imageData.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

            state =new ImageStatus(ImageStatus.ImageState.PROCESSING);

            if(iDautor != null){
                AutorTable autorTable = autorService.getAutor(iDautor);
                pictureTable.setAutor(autorTable);
            }
            List<PictureTagTable> tagList = new ArrayList<>();
            if(tags != null){
                for (String tag: tags) {
                    tagList.add(new PictureTagTable(UUID.randomUUID(), tag, pictureTable));
                }
                pictureTable.setTags(tagList);
            }
            pictureService.saveOrUpdate(pictureTable);
            if(tags != null){
                pictureTagService.creteTags(tagList);
            }




        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return state;
    }


    @RequestMapping(value={ServerApi.PICTURE_GIVELIKEID_PATH}, method = RequestMethod.GET)
    public long giveLikeToPicture(@PathVariable UUID id) {
        PictureTable pictureTable = pictureService.getPicture(id);
        logger.debug("Picture id -="+id);
        if (pictureTable != null) {
            logger.debug("Picture id ="+id);
            long count = pictureService.incrementLikes(pictureTable);
            logger.debug("Incremented to ="+count);
            return count;
        } else {
            logger.warn("Picture ("+id+") was not found. Mapping="+ServerApi.PICTURE_GIVELIKEID_PATH);
            return -1;
        }
    }

    @RequestMapping(method = RequestMethod.GET, value=ServerApi.PICTURE_GIVEDISLIKEID_PATH)
    public long giveDislikeToPicture(@PathVariable UUID id) {
        PictureTable pictureTable = pictureService.getPicture(id);
        if (pictureTable != null) {
            return pictureService.incrementDisLikes(pictureTable);
        } else {
            logger.warn("Picture ("+id+") was not found. Mapping="+ServerApi.PICTURE_GIVEDISLIKEID_PATH);
            return -1;
        }
    }


    @RequestMapping(value = ServerApi.PICTURE_PATH, method = RequestMethod.GET)
    public List<PictureTable> getPictures() {
        return pictureService.getCurrent();
    }

    @RequestMapping(value = ServerApi.PICTURE_GETONEID_PATH, method = RequestMethod.GET)
    public PictureTable getPictureById(@PathVariable UUID id) {
        return pictureService.getPicture(id);
    }
}
