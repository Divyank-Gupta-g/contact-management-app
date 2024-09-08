package com.scm.helper;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpSession;

@Component
public class SessionHelper {
	public static void removeMessage() {
		try {
			HttpSession session = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession();
			session.removeAttribute("message");
			System.out.println("Message removed...");
		}
		catch(Exception e) {
			System.out.println("Error in sessionHelper.... " );
			e.printStackTrace();
		}
	}
}
