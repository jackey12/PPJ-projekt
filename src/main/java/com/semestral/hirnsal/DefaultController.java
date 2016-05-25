package com.semestral.hirnsal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
