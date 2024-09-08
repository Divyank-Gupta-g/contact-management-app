package com.scm.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.entities.ContactUs;
import com.scm.forms.ContactUsForm;
import com.scm.helper.Message;
import com.scm.helper.MessageType;
import com.scm.services.ContactUsService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("")
public class ContactUsController {
	
	@Autowired
	private ContactUsService contactUsService;
	
	private Logger logger = LoggerFactory.getLogger(ContactUsController.class);
	
	@GetMapping("/contact")
	public String contactView(Model model) {
		ContactUsForm contactUsForm = new ContactUsForm();
		model.addAttribute("contactUsForm", contactUsForm);
		return "contact";
	}
	
	@RequestMapping(value="/contact", method=RequestMethod.POST)
	public String saveForm(@ModelAttribute ContactUsForm contactUsForm, HttpSession session) {
		ContactUs contactUs = new ContactUs();
		
		contactUs.setName(contactUsForm.getName());
		contactUs.setEmail(contactUsForm.getEmail());
		contactUs.setPhoneNumber(contactUsForm.getPhoneNumber());
		contactUs.setAddress(contactUsForm.getAddress());
		contactUs.setType(contactUsForm.getType());
		contactUs.setDescription(contactUsForm.getDescription());
		
		contactUsService.save(contactUs);
		
		System.out.println(contactUsForm);
		
		session.setAttribute("message", Message.builder().content("Thank You...").type(MessageType.green).build());
		
		return "contact";
	}
}
