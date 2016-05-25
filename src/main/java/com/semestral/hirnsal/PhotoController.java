package com.semestral.hirnsal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PhotoController {

	@RequestMapping("/photo")
	public String greeting(@RequestParam(value="id", required=false, defaultValue="1") String id, Model model) {
			model.addAttribute("id", id);
			return "photo";
	}
	
}