package com.facebook.controllers.albumsAndPictures;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.facebook.POJO.User;

@Controller
@RequestMapping("/album")
public class Albums {
	@RequestMapping( method=RequestMethod.GET)
	public String mainController(Model model, HttpServletRequest request){
			User currentUser = (User) request.getSession().getAttribute("currentUser");
		return "/album";
	}
}
