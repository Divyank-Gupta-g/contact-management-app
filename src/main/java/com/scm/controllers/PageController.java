package com.scm.controllers;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.entities.User;
import com.scm.forms.UserForm;
import com.scm.helper.Message;
import com.scm.helper.MessageType;
import com.scm.services.UserImageService;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class PageController {
	
	private final Logger logger = LoggerFactory.getLogger(PageController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserImageService imageService;					// for uploading image
	
	@GetMapping("/")
	public String index() {
		return "redirect:/home";
	}
	
	@RequestMapping("/home")
	public String home(Model m) {
		System.out.println("Home page");
		
		m.addAttribute("name", "Contact Manager");
		m.addAttribute("service", "Manage your contacts...");
		return "home";
	}
	
	//about
	
	@GetMapping("/about")
	public String aboutPage() {
		return "about";
	}
	
	//services
	
	@GetMapping("/services")
	public String servicePage() {
		return "services";
	}

	// Contact
	

	//logIn
	
	@GetMapping("/login")
	public String login() {
		return new String("login");
	}

	//services
	
	@GetMapping("/register")
	public String register(Model model) {
		UserForm userForm = new UserForm();
//		userForm.setName("Ram");
//		userForm.setAbout("Write something about yourself...");
		model.addAttribute("userForm", userForm);
		return "register";
	}
	
	// Registration
	@RequestMapping(value="/Registration", method=RequestMethod.POST)
	public String processRegister(@Valid @ModelAttribute UserForm userForm, BindingResult rBindingResult , HttpSession session) {
		// fetch data
		System.out.println(userForm);
		
		//validate data
		if(rBindingResult.hasErrors()) {
			return "register";
		}
		
		// save data
//		User user = User.builder()
//				.name(userForm.getName())
//				.email(userForm.getEmail())
//				.phoneNumber(userForm.getPhoneNumber())
//				.password(userForm.getPassword())
//				.about(userForm.getAbout())
//				.profilePic("src\\main\\resources\\static\\images\\userProfilePic.png")
//				.build();
		
		logger.info("file information: {}", userForm.getProfileImage().getOriginalFilename());
		
		User user = new User();
		user.setName(userForm.getName());
		user.setEmail(userForm.getEmail());
		user.setPhoneNumber(userForm.getPhoneNumber());
		user.setPassword(userForm.getPassword());
		user.setAbout(userForm.getAbout());

		if(userForm.getProfileImage() != null && !userForm.getProfileImage().isEmpty()) {
			// generate file name
			String fileName = UUID.randomUUID().toString();
			// upload file
			String fileURL = imageService.uploadImage(userForm.getProfileImage(), fileName);
					
			user.setProfilePic(fileURL);
			user.setCloudinaryImagePublicId(fileName);
		}
		
		// to enable or disable user
		user.setEnabled(false);
		
		
		User savedUser = userService.saveUser(user);
		
		System.out.println("user saved...");
		
		// message
		Message message = Message.builder().content("Registration Successful...").type(MessageType.green).build();
		session.setAttribute("message", message);
		
		// redirect to login page
		return "redirect:/register";
	}
}
