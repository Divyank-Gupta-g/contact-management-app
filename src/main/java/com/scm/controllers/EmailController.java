package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.helper.Message;
import com.scm.helper.MessageType;
import com.scm.services.EmailService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class EmailController {
    
    @Autowired
    private EmailService emailService;
    
    @PostMapping("/directMail")
    public String sendEmail(@RequestParam("to") String to, 
                            @RequestParam("subject") String subject, 
                            @RequestParam("body") String body, 
                            HttpSession session) {
        emailService.sendEmail(to, subject, body);
        
        // print message on the page on successfully sending the message
     		session.setAttribute("message", Message.builder().content("Email sent successfully...").type(MessageType.green).build());
        
        return "user/directMail";
    }
}
