package com.semestral.hirnsal.client;


import com.semestral.hirnsal.db.tables.AutorEntity;
import com.semestral.hirnsal.db.tables.CommentEntity;
import com.semestral.hirnsal.db.tables.PictureEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

/**
 * Created by jakub on 29.05.2016.
 */
public interface ServerApi {
    public static final String UPLOAD_PATH = "/upload/{name}";

    public static final String COMMENT_PATH = "/comment";
    public static final String COMMENT_ID_PATH = "/comment/{id}";
    public static final String COMMENT_GIVELIKEID_PATH = "/comment/like/{id}";
    public static final String COMMENT_GIVEDISLIKEID_PATH = "/comment/dislike/{id}";

    public static final String PICTURE_PATH = "/picture";
    public static final String PICTURE_GIVELIKEID_PATH = "/picture/like/{id}";
    public static final String PICTURE_GIVEDISLIKEID_PATH = "/picture/dislike/{id}";
    public static final String PICTURE_ID_PATH = "/picture/{id}";
    public static final String PICTURE_GETBYAUTOR_PATH = "/picture/autor/{id}";
    public static final String PICTURE_GETBYNAME_PATH = "/picture/name/{name}";
    public static final String PICTURE_GETBYTAG_PATH = "/picture/tag/{tag}";

    public static final String AUTOR_PATH = "/autor";
    public static final String AUTOR_ID_PATH = "/autor/{id}";
    public static final String AUTOR_GETBYNAME_PATH = "/autor/name/{name}";

    public static final String HOME_PICTURE_GIVELIKE_PATH = "/picture/like";
    public static final String HOME_PICTURE_GIVEDISLIKE_PATH = "/picture/dislike";
    public static final String HOME_COMMENT_GIVELIKE_PATH = "/comment/like";
    public static final String HOME_COMMENT_GIVEDISLIKE_PATH = "/comment/dislike";


}
