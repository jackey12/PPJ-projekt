package com.semestral.hirnsal.rest;

import com.semestral.hirnsal.SemestralJhApplication;
import com.semestral.hirnsal.client.*;

import com.semestral.hirnsal.db.repositories.BaseAutorRepository;
import com.semestral.hirnsal.db.repositories.BasePictureRepository;
import com.semestral.hirnsal.db.tables.AutorEntity;
import com.semestral.hirnsal.db.tables.PictureEntity;
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
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by jakub on 29.05.2016.
 */

@RestController
public class PictureController {
    //CRUD control

    @Autowired
    BasePictureRepository basePictureRepository;
    //PictureTagService pictureTagService;
    @Autowired
    BaseAutorRepository baseAutorRepository;

    @Value("${dataimg.path}")
    String dataImgPath;
    @Autowired
    ServletContext context;
    private static final Logger logger = LoggerFactory.getLogger(SemestralJhApplication.class);

    private final String filePrefix = "file://";


    @RequestMapping(value = ServerApi.UPLOAD_PATH, method = RequestMethod.POST)
    public
    @ResponseBody
    ImageStatus uploadImage(@PathVariable("name") String name,
                            @RequestParam("data") MultipartFile imageData,
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
                AutorEntity autorEntity = baseAutorRepository.findOne(iDautor);
                pictureEntity.setAutor(autorEntity);
            }

            basePictureRepository.save(pictureEntity);

            logger.debug("Created Picture with path ="+ pictureEntity.getPictureURL());
            state =new ImageStatus(ImageStatus.ImageState.UPLOADED);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return state;
    }


    @RequestMapping(value={ServerApi.PICTURE_GIVELIKEID_PATH}, method = RequestMethod.PUT)
    public ResponseEntity<Long> giveLikeToPicture(@PathVariable UUID id) {
        PictureEntity pictureEntity = basePictureRepository.findOne(id);
        logger.debug("Picture id -="+id);
        if (pictureEntity != null) {
            logger.debug("Picture id ="+id);
            Date date = new Date();
            pictureEntity.setLikesCount(pictureEntity.getLikesCount()+1);
            pictureEntity.setLastUpdate(date);
            basePictureRepository.save(pictureEntity);
            long count = pictureEntity.getLikesCount();
            logger.debug("Like Incremented to ="+count);
            return new ResponseEntity<>(count, HttpStatus.OK);
        } else {
            logger.warn("Picture ("+id+") was not found. Mapping="+ServerApi.PICTURE_GIVELIKEID_PATH);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value=ServerApi.PICTURE_GIVEDISLIKEID_PATH)
    public ResponseEntity<Long> giveDislikeToPicture(@PathVariable UUID id) {
        PictureEntity pictureEntity = basePictureRepository.findOne(id);
        if (pictureEntity != null) {
            logger.debug("Picture id ="+id);
            Date date = new Date();
            pictureEntity.setDislikesCount(pictureEntity.getDislikesCount()+1);
            pictureEntity.setLastUpdate(date);
            basePictureRepository.save(pictureEntity);
            long count = pictureEntity.getDislikesCount();
            logger.debug("Dislike Incremented to ="+count);
            return new ResponseEntity<>(count, HttpStatus.OK);

        } else {
            logger.warn("Picture ("+id+") was not found. Mapping="+ServerApi.PICTURE_GIVEDISLIKEID_PATH);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = ServerApi.PICTURE_GETBYAUTOR_PATH)
    public ResponseEntity<List<PictureEntity>> getPicturesByAuthor(@PathVariable UUID id) {
        return new ResponseEntity<>(basePictureRepository.findByAutorId(id), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = ServerApi.PICTURE_GETBYNAME_PATH)
    public ResponseEntity<List<PictureEntity>> getPicturesByName(@PathVariable String name) {
        return new ResponseEntity<>(basePictureRepository.findByName(name), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = ServerApi.PICTURE_GETBYTAG_PATH)
    public ResponseEntity<List<PictureEntity>> getPicturesByTag(@PathVariable String tag) {
        return new ResponseEntity<>(basePictureRepository.findByTagsTagText(tag), HttpStatus.OK);
    }


    @RequestMapping(value = ServerApi.PICTURE_PATH, method = RequestMethod.GET)
    public ResponseEntity<List<PictureEntity>> getPictures() {
        return new ResponseEntity<>(basePictureRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = ServerApi.PICTURE_ID_PATH, method = RequestMethod.GET)
    public ResponseEntity<PictureEntity> getPictureById(@PathVariable UUID id) {
        return new ResponseEntity<>(basePictureRepository.findOne(id), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = ServerApi.PICTURE_ID_PATH)
    public ResponseEntity<PictureEntity> deletePicture(@PathVariable UUID id) {
        PictureEntity picture = basePictureRepository.findOne(id);
        if(picture != null){
            basePictureRepository.delete(id);
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
            basePictureRepository.save(pictureEntity);
            return new ResponseEntity<>(pictureEntity, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = ServerApi.PICTURE_ID_PATH, method=RequestMethod.PUT)
    public ResponseEntity<PictureEntity> updateImage(@PathVariable UUID id, @RequestBody PictureEntity pictureEntity) {
        PictureEntity pictureUp = basePictureRepository.findOne(id);

        if (pictureEntity.getAutor() != null) {
            AutorEntity autor = baseAutorRepository.findOne(pictureEntity.getAutor().getId());
            if (autor != null) pictureUp.setAutor(autor);
        }

        if (pictureEntity.getName()!= null) {
            pictureUp.setName(pictureEntity.getName());
        }

        if (pictureEntity.getPictureURL()!= null) {
            pictureUp.setPictureURL(pictureEntity.getPictureURL());
        }

        if (pictureEntity.getTags()!= null) {
            pictureUp.setTags(pictureEntity.getTags());
        }

        pictureUp.setLikesCount(pictureEntity.getLikesCount());
        pictureUp.setDislikesCount(pictureEntity.getDislikesCount());

        pictureUp.setLastUpdate(new Date());
        basePictureRepository.save(pictureUp);
        return new ResponseEntity<>(pictureUp, HttpStatus.OK);
    }
}
