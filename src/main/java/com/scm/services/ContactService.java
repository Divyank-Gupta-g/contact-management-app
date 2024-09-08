package com.scm.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.scm.entities.Contacts;
import com.scm.entities.User;

public interface ContactService {
	// save contact
	Contacts save(Contacts contact);
	
	// update contact
	Contacts update(Contacts contact);
	
	// get contacts
	List<Contacts> getAll();
	
	// get contact by id
	Contacts getById(String contactId);
	
	// delete contact
	void delete(String contactId);
	
	// search contact
	Page<Contacts> searchByName(String nameKeyword, int size, int page, String sortBy, String order, User user);
	Page<Contacts> searchByEmail(String emailKeyword, int size, int page, String sortBy, String order, User user);
	Page<Contacts> searchByPhoneNumber(String phoneNumberKeyword, int size, int page, String sortBy, String order, User user);
	
	// get contacts by userId
	List<Contacts> getByUserId(String userId);
	
	// get all contacts of user
//	List<Contacts> getByUser(User user);
	// for pagination
	Page<Contacts> getByUser(User user, int page, int size, String sortField, String sortDirection);

	void deleteContact(String contactId);
}
