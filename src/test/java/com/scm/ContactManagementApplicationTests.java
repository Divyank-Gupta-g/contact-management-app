package com.scm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.scm.services.EmailService;

@SpringBootTest
class ContactManagementApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Autowired
	private EmailService service;
	
	@Test
	void sendEmailTest() {
		service.sendEmail("divyankgupta0001@gmail.com", "Testing email service.", "SCM project working on email service.");
	}
	
	@Test
	void sendEmailWithHtmlTest() {
		String html = "" + "<h1 style='color:blue'>This mail is sent via SCM.</h1>" + "";
		service.sendEmailWithHTML("divyankgupta0001@gmail.com", "Testing Email with HTML.", html);
	}
	
	@Test
	void sendEmailWithFile() {
		service.sendEmailWithAttachment("divyankgupta0001@gmail.com", "Testing email with attachment", "Please find attachment", new File("C:\\Users\\DELL\\Documents\\workspace-spring-tool-suite-4-4.23.1.RELEASE\\Contact_Management\\src\\main\\resources\\static\\images\\Users.png"));
	}

	@Test
	void sendEmailWithFileStream() {
		File file = new File("C:\\Users\\DELL\\Documents\\workspace-spring-tool-suite-4-4.23.1.RELEASE\\Contact_Management\\src\\main\\resources\\static\\images\\Users.png");
		try {
			InputStream is = new FileInputStream(file);
			service.sendEmailWithAttachment("divyankgupta0001@gmail.com", "Testing email with attachment", "Please find attachment", is);
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
}
