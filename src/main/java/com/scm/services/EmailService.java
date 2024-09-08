package com.scm.services;

import java.io.File;
import java.io.InputStream;

public interface EmailService {
	void sendEmail(String to, String subject, String body);
	
	void sendEmail(String[] to, String subject, String body);
	
	void sendEmailWithHTML(String to, String subject, String htmlContent);
	
	void sendEmailWithAttachment(String to, String subject, String body, File file);
	
	void sendEmailWithAttachment(String to, String subject, String body, InputStream is);
}
