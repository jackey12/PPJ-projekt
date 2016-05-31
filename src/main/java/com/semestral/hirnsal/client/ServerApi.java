package com.semestral.hirnsal.client;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import retrofit.http.*;

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
    public ImageStatus uploadImage(@PathVariable("name") String name,
                            @RequestParam("data") MultipartFile imageData,
                            @RequestParam(value = "tags", required = false)List<String> tags,
                            @RequestParam(value = "iDautor", required = false)UUID iDautor,
                            HttpServletResponse response);

    @GET(PICTURE_GIVELIKEID_PATH)
    public long giveLikeToPicture(@Path("id") UUID id);

    @GET(PICTURE_GIVEDISLIKEID_PATH)
    public long giveDislikeToPicture(@Path("id") UUID id);
}
