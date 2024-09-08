package com.scm.services.impl;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.scm.helper.AppConstants;
import com.scm.services.UserImageService;

@Service
public class UserImageServiceImpl implements UserImageService {
	
	private Cloudinary cloudinary;
	
	public UserImageServiceImpl(Cloudinary cloudinary) {
		this.cloudinary = cloudinary;
	}

	@Override
	public String uploadImage(MultipartFile profileImage, String fileName) {
		
		
		// code to upload image
		try {
			byte[] data = new byte[profileImage.getInputStream().available()];
			profileImage.getInputStream().read(data);
			cloudinary.uploader().upload(data, ObjectUtils.asMap(
					"public_id", fileName
					));
			return this.getURLFromPublicId(fileName);
		} 
		catch (IOException e) { 
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public String getURLFromPublicId(String publicId) {
		return cloudinary.url().transformation(
				new Transformation<>()
				.width(AppConstants.CONTACT_IMAGE_WIDTH)
				.height(AppConstants.CONTACT_IMAGE_HEIGHT)
				.crop(AppConstants.CONTACT_IMAGE_CROP)
				).generate(publicId);
	}

}
