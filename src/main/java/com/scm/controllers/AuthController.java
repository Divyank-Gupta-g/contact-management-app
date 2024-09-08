package com.scm.controllers;

import java.awt.TrayIcon.MessageType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.entities.User;
import com.scm.helper.Message;
import com.scm.repositories.UserRepo;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private UserRepo userRepo;
	
	@GetMapping("/verify-email")
	public String verifyEmail(@RequestParam("token") String token, HttpSession session) {
		System.out.println("Verifying Email...");
		
		User user = userRepo.findByEmailToken(token).orElse(null);
		
		if(user != null) {
			if(user.getEmailToken().equals(token)) {
				user.setEmailVerified(true);
				user.setEnabled(true);
				userRepo.save(user);
				
				session.setAttribute("message", Message.builder().content("Email verified successfully...").type(com.scm.helper.MessageType.green).build());
				
				return "success_page";
			}
			session.setAttribute("message", Message.builder().content("Email not verified, Please try again...").type(com.scm.helper.MessageType.red).build());
			
			return "error_page";
		}
		session.setAttribute("message", Message.builder().content("Email not verified, Please try again...").type(com.scm.helper.MessageType.red).build());
		
		return "error_page";
	}
}
