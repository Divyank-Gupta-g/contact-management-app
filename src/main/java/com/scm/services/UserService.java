package com.scm.services;

import java.util.List;
import java.util.Optional;

import com.scm.entities.User;

public interface UserService {
	User saveUser(User user);
	Optional<User> getUserById(String userId);
	Optional<User> updateUser(User user);
	void deleteUser(String userId);
	boolean isUserExist(String userId);
	boolean isUserExistByEmail(String email);
	
	User getUserByEmail(String email);
	
	List<User> getAllUsers();
	
	
}
