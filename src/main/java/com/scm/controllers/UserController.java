package com.scm.controllers;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.entities.Feedback;
import com.scm.entities.User;
import com.scm.helper.Helper;
import com.scm.services.FeedbackService;
import com.scm.services.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FeedbackService feedbackService;
	
	// dashboard
	@RequestMapping(value="/dashboard")
	public String userDashboard() {
		return "user/dashboard";
	}
	
	// Profile
	@RequestMapping(value="/profile")
	public String userProfile(Model model,  Authentication authentication) {
		
		return "user/profile";
	}
	
	@RequestMapping(value="/directMail")
	public String sendMail() {
		return "user/directMail";
	}
	
	// Feedback Form (GET Request)
    @GetMapping("/feedback")
    public String showFeedbackForm(Model model) {
        model.addAttribute("feedbackForm", new Feedback());
        return "user/feedback";
    }
	
	
	// add contact
	
	// view contact
	
	// edit contact
	
	//delete contact
	
	//search contact
}
