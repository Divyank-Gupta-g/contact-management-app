package com.scm.controllers;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.entities.Contacts;
import com.scm.entities.User;
import com.scm.forms.ContactForm;
import com.scm.forms.ContactSearchForm;
import com.scm.helper.AppConstants;
import com.scm.helper.Helper;
import com.scm.helper.Message;
import com.scm.helper.MessageType;
import com.scm.services.ContactService;
import com.scm.services.ImageService;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {
	
	private Logger logger = LoggerFactory.getLogger(ContactController.class);
	
	@Autowired
	private ContactService contactService;
	
	@Autowired
	private UserService userService;					// to get the user
	
	@Autowired
	private ImageService imageService;					// for uploading image
	
	// contact page handler
	@RequestMapping("/add")
	public String addContactView(Model model) {
		ContactForm contactForm = new ContactForm();
		model.addAttribute("contactForm", contactForm);
		return "user/add_contact";
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String saveContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult result, Authentication authentication, HttpSession session) {
		// process form data
		
		// validate data
		if(result.hasErrors()) {
			result.getAllErrors().forEach(error -> logger.info(error.toString()));
			session.setAttribute("message", Message.builder().content("Please correct the following errors...").type(MessageType.red).build());
			return "user/add_contact";
		}
		
		//get user details
		String username = Helper.getEmailOfLoggedInUser(authentication);
		
		User user = userService.getUserByEmail(username);
		
		// process contact image
		logger.info("File information: {}", contactForm.getContactImage().getOriginalFilename());
		
		// form ---> contact
		Contacts contact = new Contacts();
		contact.setName(contactForm.getName());
		contact.setEmail(contactForm.getEmail());
		contact.setFavourite(contactForm.isFavourite());
		contact.setPhoneNumber(contactForm.getPhoneNumber());
		contact.setAddress(contactForm.getAddress());
		contact.setDescription(contactForm.getDescription());
		contact.setUser(user);
		contact.setLinkedinLink(contactForm.getLinkedinLink());
		contact.setWebsiteLink(contactForm.getWebsiteLink());
		
		if(contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty()) {
			// generate file name
			String fileName = UUID.randomUUID().toString();
			// upload file
			String fileURL = imageService.uploadImage(contactForm.getContactImage(), fileName);
					
			contact.setPicture(fileURL);
			contact.setCloudinaryImagePublicId(fileName);
		}
		
		// save to database
		contactService.save(contact);
		
		// print data on console
		System.out.println(contactForm);
		
		// print message on the page on successfully adding the contact
		session.setAttribute("message", Message.builder().content("You have successfully added a new contact.").type(MessageType.green).build());
		
		return "redirect:/user/contacts/add";
	}
	
	// view contacts
	@RequestMapping
	public String viewContacts(@RequestParam(value="page", defaultValue="0") int page, 
			@RequestParam(value="size", defaultValue="3") int size, 
			@RequestParam(value="sortBy", defaultValue="name") String sortBy,
			@RequestParam(value="direction", defaultValue="asc") String direction, Model model, Authentication authentication) {
		// load all user contacts
		String userName = Helper.getEmailOfLoggedInUser(authentication);
		User user = userService.getUserByEmail(userName);
//		List<Contacts> contacts = contactService.getByUser(user);
		//for pagination
		Page<Contacts> pageContacts = contactService.getByUser(user, page, size, sortBy, direction);
		
		model.addAttribute("pageContacts", pageContacts);
		model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
		model.addAttribute("contactSearchForm", new ContactSearchForm());
		return "user/contacts";
	}
	
	// search handler
	@RequestMapping("/search")
	public String searchHandler(
			@ModelAttribute ContactSearchForm contactSearchForm, 
			@RequestParam(value="size", defaultValue=AppConstants.PAGE_SIZE+"") int size,
			@RequestParam(value="page", defaultValue="0") int page,
			@RequestParam(value="sortBy", defaultValue="name") String sortBy,
			@RequestParam(value="direction", defaultValue="asc") String direction,
			Model model, Authentication authentication
			) {
		logger.info("field {} key {}", contactSearchForm.getField(), contactSearchForm.getValue());
		
		var user = userService.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));
		
		Page<Contacts> pageContacts = null;
		if(contactSearchForm.getField().equalsIgnoreCase("name")) {
			pageContacts = contactService.searchByName(contactSearchForm.getValue(), size, page, sortBy, direction, user);
		}
		else if(contactSearchForm.getField().equalsIgnoreCase("email")) {
			pageContacts = contactService.searchByEmail(contactSearchForm.getValue(), size, page, sortBy, direction, user);
		}
		else if(contactSearchForm.getField().equalsIgnoreCase("phone")) {
			pageContacts = contactService.searchByPhoneNumber(contactSearchForm.getValue(), size, page, sortBy, direction, user);
		}
		
		logger.info("pageContact {}", pageContacts);
		
		model.addAttribute("contactSearchForm", contactSearchForm);
		
		model.addAttribute("pageContacts", pageContacts);
		
		model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
		
		return "user/search";
	}
	
	// update contact form view
	@GetMapping("/view/{contactId}")
	public String updateContactFormView(
			@PathVariable("contactId") String contactId, Model model) 
	{
		var contact = contactService.getById(contactId);
		
		ContactForm contactForm = new ContactForm();
		contactForm.setName(contact.getName());
		contactForm.setEmail(contact.getEmail());
		contactForm.setPhoneNumber(contact.getPhoneNumber());
		contactForm.setAddress(contact.getAddress());
		contactForm.setDescription(contact.getDescription());
		contactForm.setWebsiteLink(contact.getWebsiteLink());
		contactForm.setLinkedinLink(contact.getLinkedinLink());
		contactForm.setFavourite(contact.isFavourite());
		contactForm.setPicture(contact.getPicture());
		
		model.addAttribute("contactForm", contactForm);
		model.addAttribute("contactId", contactId);
		
		return "user/update_contact_view";
	}
	
	@RequestMapping(value="/update/{contactId}", method=RequestMethod.POST)
	public String updateContact(@PathVariable("contactId") String contactId, 
				@Valid @ModelAttribute ContactForm contactForm, 
				BindingResult bindingResult, 
				Model model) {
		// update the contact
		if(bindingResult.hasErrors()) {
			return "user/update_contact_view";
		}
		
		var con = contactService.getById(contactId);
		con.setContactId(contactId);
		con.setName(contactForm.getName());
		con.setEmail(contactForm.getEmail());
		con.setPhoneNumber(contactForm.getPhoneNumber());
		con.setAddress(contactForm.getAddress());
		con.setDescription(contactForm.getDescription());
		con.setWebsiteLink(contactForm.getWebsiteLink());
		con.setLinkedinLink(contactForm.getLinkedinLink());
		con.setFavourite(contactForm.isFavourite());
		
		
		// process image
		if(contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty()) {
			logger.info("File is not empty");
			String fileName = UUID.randomUUID().toString();
			String imageURL = imageService.uploadImage(contactForm.getContactImage(), fileName);
			con.setCloudinaryImagePublicId(fileName);
			con.setPicture(imageURL);
			contactForm.setPicture(imageURL);
		}
		else {
			logger.info("File is empty");
		}
		
		var updatedContact = contactService.update(con);
		logger.info("Updated Contact: {}", updatedContact);
		model.addAttribute("message", Message.builder().content("You have successfully updated the contact details.").type(MessageType.green).build());
		
		return "redirect:/user/contacts/view/" + contactId;
	}
}
