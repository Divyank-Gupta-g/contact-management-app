package com.scm.services;

import org.springframework.web.multipart.MultipartFile;

public interface UserImageService {
	
	String uploadImage(MultipartFile profileImage, String fileName);
	
	String getURLFromPublicId(String publicId);
}
