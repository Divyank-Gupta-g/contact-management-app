package com.scm.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ContactUsForm {
	@NotBlank(message = "Please enter the name of contact.")
	private String name;
	
	@NotBlank(message = "Please enter the email id of contact")
	@Email(message = "Please enter a valid email address.")
	private String email;
	
	@NotBlank(message = "Please enter the contact number.")
	@Pattern(regexp = "^[0-9]{10}$", message = "Contact number should contain 10 digits only.")
	private String phoneNumber;
	
	@NotBlank(message = "Please enter the address of contact.")
	private String address;
	
	private String type;
	
	private String description;
}
