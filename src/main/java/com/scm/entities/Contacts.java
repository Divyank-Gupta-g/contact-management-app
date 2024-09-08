package com.scm.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contacts {
	@Id
	private String contactId;
	private String name;
	private String email;
	private String phoneNumber;
	private String address;
	private String picture;
	@Column(length=10000)
	private String description;
	private boolean favourite = false;
	private String instagramLink;
	private String facebookLink;
	private String websiteLink;
	private String linkedinLink;
	private String cloudinaryImagePublicId;
	
//	private List<String> socialLinks = new ArrayList<>();
	
	@ManyToOne
	@JsonIgnore							// to ignore recursion while retrieving contact data 
	private User user;
	
	@OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private List<SocialLinks> links = new ArrayList<>();
}
