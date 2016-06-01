package com.semestral.hirnsal.rest;

import com.semestral.hirnsal.SemestralJhApplication;
import com.semestral.hirnsal.client.*;

import com.semestral.hirnsal.db.tables.AutorEntity;
import com.semestral.hirnsal.db.tables.PictureEntity;
import com.semestral.hirnsal.db.tables.PictureTagEntity;
import com.semestral.hirnsal.service.AutorService;
import com.semestral.hirnsal.service.PictureService;
import com.semestral.hirnsal.service.PictureTagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
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
            PictureEntity pictureEntity = new PictureEntity(UUID.randomUUID(), name, this.filePrefix + fullPathName ,date);

            Path target = Paths.get(this.dataImgPath).resolve(imageData.getOriginalFilename());
            state =new ImageStatus(ImageStatus.ImageState.PROCESSING);
            Files.copy(imageData.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
            logger.debug("Picture uploaded to path ="+target);



            if(iDautor != null){
                AutorEntity autorEntity = autorService.getAutor(iDautor);
                pictureEntity.setAutor(autorEntity);
            }
            List<PictureTagEntity> tagList = new ArrayList<>();
            if(tags != null){
                for (String tag: tags) {
                    tagList.add(new PictureTagEntity(UUID.randomUUID(), tag, pictureEntity));
                }
                pictureEntity.setTags(tagList);
            }
            pictureService.saveOrUpdate(pictureEntity);
            if(tags != null){
                pictureTagService.creteTags(tagList);
            }
            logger.debug("Created Picture with path ="+ pictureEntity.getPictureURL());
            state =new ImageStatus(ImageStatus.ImageState.UPLOADED);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return state;
    }


    @RequestMapping(value={ServerApi.PICTURE_GIVELIKEID_PATH}, method = RequestMethod.PUT)
    public ResponseEntity<Long> giveLikeToPicture(@PathVariable UUID id) {
        PictureEntity pictureEntity = pictureService.getPicture(id);
        logger.debug("Picture id -="+id);
        if (pictureEntity != null) {
            logger.debug("Picture id ="+id);
            long count = pictureService.incrementLikes(pictureEntity);
            logger.debug("Like Incremented to ="+count);
            return new ResponseEntity<>(count, HttpStatus.OK);
        } else {
            logger.warn("Picture ("+id+") was not found. Mapping="+ServerApi.PICTURE_GIVELIKEID_PATH);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value=ServerApi.PICTURE_GIVEDISLIKEID_PATH)
    public ResponseEntity<Long> giveDislikeToPicture(@PathVariable UUID id) {
        PictureEntity pictureEntity = pictureService.getPicture(id);
        if (pictureEntity != null) {
            logger.debug("Picture id ="+id);
            long count = pictureService.incrementDisLikes(pictureEntity);
            logger.debug("Dislike Incremented to ="+count);
            return new ResponseEntity<>(count, HttpStatus.OK);

        } else {
            logger.warn("Picture ("+id+") was not found. Mapping="+ServerApi.PICTURE_GIVEDISLIKEID_PATH);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = ServerApi.PICTURE_GETBYAUTOR_PATH)
    public ResponseEntity<List<PictureEntity>> getPicturesByAuthor(@PathVariable UUID id) {
        return new ResponseEntity<>(pictureService.getPictureByAutor(id), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = ServerApi.PICTURE_GETBYNAME_PATH)
    public ResponseEntity<List<PictureEntity>> getPicturesByName(@PathVariable String name) {
        return new ResponseEntity<>(pictureService.getPictureByName(name), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = ServerApi.PICTURE_GETBYTAG_PATH)
    public ResponseEntity<List<PictureEntity>> getPicturesByTag(@PathVariable String tag) {
        return new ResponseEntity<>(pictureService.getPictureByTag(tag), HttpStatus.OK);
    }


    @RequestMapping(value = ServerApi.PICTURE_PATH, method = RequestMethod.GET)
    public ResponseEntity<List<PictureEntity>> getPictures() {
        return new ResponseEntity<>(pictureService.getCurrent(), HttpStatus.OK);
    }

    @RequestMapping(value = ServerApi.PICTURE_GETONEID_PATH, method = RequestMethod.GET)
    public ResponseEntity<PictureEntity> getPictureById(@PathVariable UUID id) {
        return new ResponseEntity<>(pictureService.getPicture(id), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = ServerApi.PICTURE_ID_PATH)
    public ResponseEntity<PictureEntity> deletePicture(@PathVariable UUID id) {
        PictureEntity picture = pictureService.getPicture(id);
        if(picture != null){
            pictureService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //create
    @RequestMapping(value = ServerApi.PICTURE_PATH, method = RequestMethod.POST)
    public ResponseEntity<PictureEntity> createPicture(@RequestBody PictureEntity pictureEntity){
        if (pictureEntity != null) {
            Date date = new Date();
            pictureEntity.setCreatedAt(date);
            pictureEntity.setLastUpdate(date);
            pictureService.create(pictureEntity);
            return new ResponseEntity<>(pictureEntity, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = ServerApi.PICTURE_ID_PATH, method=RequestMethod.PUT)
    public ResponseEntity<PictureEntity> updateImage(@PathVariable UUID id, @RequestBody PictureEntity pictureEntity) {
        PictureEntity pictureUp = pictureService.getPicture(id);

        if (pictureEntity.getAutor() != null) {
            AutorEntity autor = autorService.getAutor(pictureEntity.getAutor().getId());
            if (autor != null) pictureUp.setAutor(autor);
        }

        if (!pictureEntity.getName().isEmpty()) {
            pictureUp.setName(pictureEntity.getName());
        }

        if (!pictureEntity.getPictureURL().isEmpty()) {
            pictureUp.setPictureURL(pictureEntity.getPictureURL());
        }
        if (!pictureEntity.getTags().isEmpty()) {
            pictureUp.setTags(pictureEntity.getTags());
        }


        pictureUp.setLikesCount(pictureEntity.getLikesCount());
        pictureUp.setDislikesCount(pictureEntity.getDislikesCount());

        pictureUp.setLastUpdate(new Date());
        pictureService.saveOrUpdate(pictureUp);
        return new ResponseEntity<>(pictureUp, HttpStatus.OK);
    }
}
