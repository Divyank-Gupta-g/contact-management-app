package com.scm.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scm.entities.User;

@Repository
public interface UserRepo extends JpaRepository<User, String>{
	
	// custom finder methods
	Optional<User> findByEmail(String email);
	Optional<User> findByEmailAndPassword(String email, String password);
	
	Optional<User> findByEmailToken(String id);
}
