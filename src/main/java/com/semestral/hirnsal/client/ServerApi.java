package com.semestral.hirnsal.client;


import com.semestral.hirnsal.db.tables.AutorEntity;
import com.semestral.hirnsal.db.tables.CommentEntity;
import com.semestral.hirnsal.db.tables.PictureEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import retrofit.http.*;
import retrofit.mime.TypedFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

/**
 * Created by jakub on 29.05.2016.
 */
public interface ServerApi {
    public static final String UPLOAD_PATH = "/upload/{name}";

    public static final String COMMENT_PATH = "/comment";
    public static final String COMMENT_GETALL_PATH = "/comment";
    public static final String COMMENT_ID_PATH = "/comment/{id}";
    public static final String COMMENT_GIVELIKEID_PATH = "/comment/givelike/{id}";
    public static final String COMMENT_GIVEDISLIKEID_PATH = "/comment/givedislike/{id}";

    public static final String PICTURE_PATH = "/picture";
    public static final String PICTURE_GIVELIKEID_PATH = "/picture/givelike/{id}";
    public static final String PICTURE_GIVEDISLIKEID_PATH = "/picture/givedislike/{id}";
    public static final String PICTURE_ID_PATH = "/picture/{id}";
    public static final String PICTURE_GETONEID_PATH = "/picture/getone/{id}";
    public static final String PICTURE_GETBYAUTOR_PATH = "/picture/getbyautor/{id}";
    public static final String PICTURE_GETBYNAME_PATH = "/picture/getbyname/{name}";
    public static final String PICTURE_GETBYTAG_PATH = "/picture/getbytag/{tag}";

    public static final String AUTOR_PATH = "/autor";
    public static final String AUTOR_ID_PATH = "/autor/{id}";
    public static final String AUTOR_GETBYNAME_PATH = "/autor/getbyname/{name}";

    public static final String HOME_PICTURE_GIVELIKE_PATH = "/picture/givelike";
    public static final String HOME_PICTURE_GIVEDISLIKE_PATH = "/picture/givedislike";
    public static final String HOME_COMMENT_GIVELIKE_PATH = "/comment/givelike";
    public static final String HOME_COMMENT_GIVEDISLIKE_PATH = "/comment/givedislike";


    @Multipart
    @POST(UPLOAD_PATH)
    public ImageStatus uploadImage(@Path("name") String name, @Part("data") TypedFile imageData);

    @PUT(PICTURE_GIVELIKEID_PATH)
    public ResponseEntity<Long> giveLikeToPicture(@Path("id") UUID id);

    @PUT(PICTURE_GIVEDISLIKEID_PATH)
    public ResponseEntity<Long> giveDislikeToPicture(@Path("id") UUID id);

    @GET(PICTURE_GETBYAUTOR_PATH)
    public ResponseEntity<List<PictureEntity>> getPicturesByAuthor(@Path("id") UUID id);

    @GET(PICTURE_GETBYNAME_PATH)
    public ResponseEntity<List<PictureEntity>> getPicturesByName(@Path("name") String name);

    @GET(PICTURE_GETBYTAG_PATH)
    public ResponseEntity<List<PictureEntity>> getPicturesByTag(@Path("tag") String name);

    @GET(PICTURE_PATH)
    public ResponseEntity<List<PictureEntity>> getPictures();

    @GET(PICTURE_GETONEID_PATH)
    public ResponseEntity<PictureEntity> getPictureById();

    @DELETE(PICTURE_ID_PATH)
    public ResponseEntity<PictureEntity> deletePicture();

    @POST(PICTURE_PATH)
    public ResponseEntity<PictureEntity> createPicture(@Body PictureEntity pictureEntity);

    @PUT(PICTURE_ID_PATH)
    public ResponseEntity<PictureEntity> updateImage(@Path("id") UUID id, @Body PictureEntity pictureEntity);



    @POST(COMMENT_PATH)
    public ResponseEntity<CommentEntity> addCommentMethod(@Body CommentEntity commentEntity);

    @GET(COMMENT_GETALL_PATH)
    public ResponseEntity<List<CommentEntity>> showComments();

    @DELETE(COMMENT_ID_PATH)
    public ResponseEntity<CommentEntity> deleteComment(@Path("id") UUID id);

    @PUT(COMMENT_GIVELIKEID_PATH)
    public ResponseEntity<Long> giveLikeToComment(@Path("id") UUID id);

    @PUT(COMMENT_GIVEDISLIKEID_PATH)
    public ResponseEntity<Long> giveDislikeToPComment(@Path("id") UUID id);

    @GET(COMMENT_ID_PATH)
    public ResponseEntity<CommentEntity> getCommentByID(@Path("id") UUID id);

    @PUT(COMMENT_ID_PATH)
    public ResponseEntity<CommentEntity> updateCommentMethod(@Path("id") UUID id, @Body CommentEntity commentEntity);


    @POST(AUTOR_PATH)
    public ResponseEntity<AutorEntity> createAutor(@Body AutorEntity autorEntity);

    @DELETE(AUTOR_ID_PATH)
    public ResponseEntity<AutorEntity> deleteAutor(@Path("id") UUID id);

    @GET(AUTOR_PATH)
    public ResponseEntity<List<AutorEntity>> getAllAutors();

    @GET(AUTOR_ID_PATH)
    public ResponseEntity<AutorEntity>  getAutor(@Path("id") UUID id);

    @GET(AUTOR_GETBYNAME_PATH)
    public ResponseEntity<List<AutorEntity>> getAutorByName(@Path("name")String name);

    @PUT(AUTOR_ID_PATH)
    public ResponseEntity<AutorEntity> updateAuthor(@Path("id") UUID id, @Body AutorEntity autor);
}
