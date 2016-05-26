package com.semestral.hirnsal.mvc;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by jakub on 25.05.2016.
 */
@Controller
public class DefaultController {

    @RequestMapping("/")
    public String home() {
        return "home";
    }
}
