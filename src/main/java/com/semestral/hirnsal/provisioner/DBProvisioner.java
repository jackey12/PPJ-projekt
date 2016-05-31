package com.semestral.hirnsal.provisioner;

import com.semestral.hirnsal.db.repositories.BaseAutorRepository;
import com.semestral.hirnsal.db.repositories.BaseCommentRepository;
import com.semestral.hirnsal.db.repositories.BasePictureRepository;
import com.semestral.hirnsal.db.repositories.BasePictureTagRepository;
import com.semestral.hirnsal.db.tables.AutorEntity;
import com.semestral.hirnsal.db.tables.CommentEntity;
import com.semestral.hirnsal.db.tables.PictureEntity;
import com.semestral.hirnsal.db.tables.PictureTagEntity;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by jakub on 31.05.2016.
 */

@Component
@Transactional
public class DBProvisioner implements InitializingBean{
    @Autowired
    private BaseAutorRepository baseAutorRepository;

    @Autowired
    private BasePictureRepository basePictureRepository;

    @Autowired
    private BasePictureTagRepository basePictureTagRepository;

    @Autowired
    private BaseCommentRepository baseCommentRepository;

    @Override
    public void afterPropertiesSet() throws Exception {
        provisionAutorCollectionIfEmpty();
        provisionPictureCollectionIfEmpty();
        provisionPictureTagCollectionIfEmpty();
        provisionCommentCollectionIfEmpty();
    }

    private boolean provisionAutorCollectionIfEmpty() throws IOException {
        boolean isEmpty = baseAutorRepository.count() == 0;
        if (isEmpty) {

            try (BufferedReader read = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/provision/Autor.txt")))) {
                List<AutorEntity> els = read.lines().map(s -> s.split("\\s"))
                        .map(a -> new AutorEntity(UUID.fromString(a[0]), a[1], new Date())).collect(Collectors.toList());
                baseAutorRepository.save(els);
            }
        }
        return isEmpty;
    }

    private boolean provisionPictureCollectionIfEmpty() throws IOException {
        boolean isEmpty = basePictureRepository.count() == 0;
        if (isEmpty) {

            try (BufferedReader read = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/provision/pictures.txt")))) {
                List<PictureEntity> els = read.lines().map(s -> s.split("\\s"))
                        .map(a -> new PictureEntity(UUID.fromString(a[0]), a[1], a[2], new Date())).collect(Collectors.toList());
                basePictureRepository.save(els);
            }
        }
        return isEmpty;
    }

    private boolean provisionPictureTagCollectionIfEmpty() throws IOException {
        boolean isEmpty = basePictureTagRepository.count() == 0;
        if (isEmpty) {

            try (BufferedReader read = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/provision/picturetags.txt")))) {
                List<PictureTagEntity> els = read.lines().map(s -> s.split("\\s"))
                        .map(a -> new PictureTagEntity(UUID.fromString(a[0]), a[1], basePictureRepository.findOne(UUID.fromString(a[2])))).collect(Collectors.toList());
                basePictureTagRepository.save(els);
            }
        }
        return isEmpty;
    }

    private boolean provisionCommentCollectionIfEmpty() throws IOException {
        boolean isEmpty = baseCommentRepository.count() == 0;
        if (isEmpty) {

            try (BufferedReader read = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/provision/comments.txt")))) {
                List<CommentEntity> els = read.lines().map(s -> s.split("\\s"))
                        .map(a -> new CommentEntity(UUID.fromString(a[0]), a[1], baseAutorRepository.findOne(UUID.fromString(a[2])), basePictureRepository.findOne(UUID.fromString(a[3])), new Date())).collect(Collectors.toList());
                baseCommentRepository.save(els);
            }
        }
        return isEmpty;
    }

}
