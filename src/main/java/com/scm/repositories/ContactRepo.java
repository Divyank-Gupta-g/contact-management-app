package com.scm.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scm.entities.Contacts;
import com.scm.entities.User;
import java.util.List;


@Repository
public interface ContactRepo extends JpaRepository<Contacts, String>{
	
	// find contacts by user
	// use any one method
	// custom finder method
//	List<Contacts> findByUser(User user);
	// for pagination
	Page<Contacts> findByUser(User user, Pageable pageable);
	
	// custom query method
	@Query("SELECT c FROM Contacts c Where c.user.id = :userId")
	List<Contacts> findByUserId(@Param("userId") String userId);
	
	
	// search
	Page<Contacts> findByUserAndNameContaining(User user, String nameKeyword, Pageable pageable);
	Page<Contacts> findByUserAndEmailContaining(User user, String emailKeyword, Pageable pageable);
	Page<Contacts> findByUserAndPhoneNumberContaining(User user, String phoneNumberKeyword, Pageable pageable);
}
