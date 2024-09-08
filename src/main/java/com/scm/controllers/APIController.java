package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scm.entities.Contacts;
import com.scm.services.ContactService;

@RestController
@RequestMapping("/api")
public class APIController {
	// get contact detail of user
	@Autowired
	private ContactService contactService;
	
	@GetMapping("/contacts/{contactId}")
	public Contacts getContact(@PathVariable String contactId) {
		return contactService.getById(contactId);
	}
	
	@DeleteMapping("/contacts/{contactId}")
    public ResponseEntity<Void> deleteContact(@PathVariable String contactId) {
        contactService.deleteContact(contactId);
        return ResponseEntity.noContent().build();
    }
}

