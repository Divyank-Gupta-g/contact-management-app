package com.scm.validators;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileValidator implements ConstraintValidator<ValidFile, MultipartFile> {
	
	private static final long MAX_FILE_SIZE = 1024*1024*5;						// 5MB
	private static final long MAX_FILE_HEIGHT = 600;
	private static final long MAX_FILE_WIDTH = 600;

	@Override
	public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
		if(file == null || file.isEmpty()){
//			context.disableDefaultConstraintViolation();
//			context.buildConstraintViolationWithTemplate("File can not be empty.").addConstraintViolation();
			return true;
		}
		
		// size
		if(file.getSize() > MAX_FILE_SIZE) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("File size should be less than or equal to 5MB.").addConstraintViolation();
			return false;
		}
		
		// resolution
		try {
			BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
			if((bufferedImage.getHeight() > MAX_FILE_HEIGHT) || (bufferedImage.getWidth() > MAX_FILE_WIDTH)) {
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate("Max allowed File resulution: 600*400").addConstraintViolation();
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return true;
	}
}
