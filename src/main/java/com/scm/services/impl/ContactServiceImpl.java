package com.scm.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.scm.entities.Contacts;
import com.scm.entities.User;
import com.scm.helper.ResourceNotFoundException;
import com.scm.repositories.ContactRepo;
import com.scm.services.ContactService;

@Service
public class ContactServiceImpl implements ContactService {

	@Autowired
	private ContactRepo contactRepo;
	
	@Override
	public Contacts save(Contacts contact) {
		String contactId = UUID.randomUUID().toString();
		contact.setContactId(contactId);
		return contactRepo.save(contact);
	}

	@Override
	public Contacts update(Contacts contact) {
		var oldContact = contactRepo.findById(contact.getContactId()).orElseThrow(() -> new ResourceNotFoundException("Contact Not Found..."));
		oldContact.setName(contact.getName());
		oldContact.setEmail(contact.getEmail());
		oldContact.setPhoneNumber(contact.getPhoneNumber());
		oldContact.setAddress(contact.getAddress());
		oldContact.setDescription(contact.getDescription());
		oldContact.setFavourite(contact.isFavourite());
		oldContact.setLinkedinLink(contact.getLinkedinLink());
		oldContact.setWebsiteLink(contact.getWebsiteLink());
		oldContact.setPicture(contact.getPicture());
		oldContact.setCloudinaryImagePublicId(contact.getCloudinaryImagePublicId());
		
		return contactRepo.save(oldContact);
	}

	@Override
	public List<Contacts> getAll() {
		return contactRepo.findAll();
	}

	@Override
	public Contacts getById(String contactId) {
		return contactRepo.findById(contactId).orElseThrow(() -> new ResourceNotFoundException("Contact Not Found with id: " + contactId));
	}

	@Override
	public void delete(String contactId) {
		var del = contactRepo.findById(contactId).orElseThrow(() -> new ResourceNotFoundException("Contact Not Found with id: " + contactId));
		contactRepo.delete(del);
		
	}

	@Override
	public List<Contacts> getByUserId(String userId) {
		return contactRepo.findByUserId(userId);
	}

//	@Override
//	public List<Contacts> getByUser(User user) {
//		return contactRepo.findByUser(user);
//	}
	//for pagination
	@Override
	public Page<Contacts> getByUser(User user, int page, int size, String sortBy, String direction) {
		Sort sort = direction.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		var pageable = PageRequest.of(page, size, sort);
		return contactRepo.findByUser(user, pageable);
	}

	@Override
	public Page<Contacts> searchByName(String nameKeyword, int size, int page, String sortBy, String order, User user) {
		Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		var pageable = PageRequest.of(page, size, sort);
		return contactRepo.findByUserAndNameContaining(user, nameKeyword, pageable);
	}

	@Override
	public Page<Contacts> searchByEmail(String emailKeyword, int size, int page, String sortBy, String order, User user) {
		Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		var pageable = PageRequest.of(page, size, sort);
		return contactRepo.findByUserAndEmailContaining(user, emailKeyword, pageable);
	}
 
	@Override
	public Page<Contacts> searchByPhoneNumber(String phoneNumberKeyword, int size, int page, String sortBy,
			String order, User user) {
		Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		var pageable = PageRequest.of(page, size, sort);
		return contactRepo.findByUserAndPhoneNumberContaining(user, phoneNumberKeyword, pageable);
	}

	@Override
	public void deleteContact(String contactId) {
		contactRepo.deleteById(contactId);
	}

	
}
