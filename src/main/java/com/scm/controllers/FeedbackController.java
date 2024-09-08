package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.entities.Feedback;
import com.scm.services.FeedbackService;

@Controller
@RequestMapping("/user")
public class FeedbackController {
	
	@Autowired
	private FeedbackService feedbackService;
	
    // Handle Feedback Form Submission (POST Request)
    @PostMapping("/feedback")
    public String submitFeedbackForm(@ModelAttribute("feedbackForm") Feedback feedback, Model model) {
        feedbackService.saveFeedback(feedback);
        model.addAttribute("message", "Feedback submitted successfully!");
        return "user/feedback";
    }
}

