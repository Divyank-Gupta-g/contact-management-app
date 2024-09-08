package com.scm.forms;

import org.springframework.web.multipart.MultipartFile;

import com.scm.validators.ValidFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserForm {
	@NotBlank(message = "User name is mandatory.")
	@Size(min= 3, message = "Minimum 3 characters required.")
	private String name;
	
	@Email(message = "Invalid Email address.")
	private String email;
	
	@Size(min = 10, max = 12, message = "Enter a valid mobile number.")
	private String phoneNumber;
	
	@NotBlank(message = "Password is mandatory.")
	@Size(min= 6, max = 12, message = "Password length should be from 6 to 12 characters.")
	private String password;
	
	@NotBlank(message = "About is mandatory.")
	private String about;
	
	// creating custom annotation to validate file (size, resolution)
//	@ValidFile(message = "Invalid File")
	private MultipartFile profileImage;
		
	// to show picture in update form
//	private String profilePic;
}
