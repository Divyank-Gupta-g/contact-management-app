package com.scm.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scm.entities.User;
import com.scm.helper.AppConstants;
import com.scm.helper.Helper;
import com.scm.helper.ResourceNotFoundException;
import com.scm.repositories.UserRepo;
import com.scm.services.EmailService;
import com.scm.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private EmailService emailService;
	
	@Override
	public User saveUser(User user) {
		// generate userId
		String userId = UUID.randomUUID().toString();
		user.setUserId(userId);
		
		// encode password
//		user.setPassword(userId);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		// set user role
		user.setRoleList(List.of(AppConstants.ROLE_USER));
		
//		user.setProfilePic(userId);
		logger.info(user.getProviders().toString());
		
//		return userRepo.save(user);
		
		// to send verification link
		
		String emailToken = UUID.randomUUID().toString();
		
		user.setEmailToken(emailToken);
		
		User savedUser =  userRepo.save(user);
		
		String emailLink = Helper.getEmailVerificationLink(emailToken);
		
		emailService.sendEmail(savedUser.getEmail(), "Verify Your SCM account", emailLink);
		
		return savedUser;
	}

	@Override
	public Optional<User> getUserById(String userId) {
		return userRepo.findById(userId);
	}

	@Override
	public Optional<User> updateUser(User user) {
		User user2 = userRepo.findById(user.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found..."));
		user2.setName(user.getName());
		user2.setEmail(user.getEmail());
		user2.setPhoneNumber(user.getPhoneNumber());
		user2.setPassword(user.getPassword());
		user2.setAbout(user.getAbout());
		user2.setProfilePic(user.getProfilePic());
		user2.setEnabled(user.isEnabled());
		user2.setEmailVerified(user.isEmailVerified());
		user2.setPhoneVerified(user.isPhoneVerified());
		user2.setProviders(user.getProviders());
		user2.setProviderUserId(user.getProviderUserId());
		
		// save user in database
		User save = userRepo.save(user2);
		
		return Optional.ofNullable(save);
	}

	@Override
	public void deleteUser(String userId) {
		User user2 = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found..."));
		userRepo.delete(user2);
	}

	@Override
	public boolean isUserExist(String userId) {
		User user2 = userRepo.findById(userId).orElse(null);
		return (user2!=null ? true : false);
	}

	@Override
	public boolean isUserExistByEmail(String email) {
		User user2 = userRepo.findByEmail(email).orElse(null);
		return (user2!=null ? true : false);
	}

	@Override
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}

	@Override
	public User getUserByEmail(String email) {
		return userRepo.findByEmail(email).orElse(null);
	}
	
}
