// created after authentication

package com.scm.helper;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {
	public static String getEmailOfLoggedInUser(Authentication authentication) {
//		AuthenticationPrincipal principal = (AuthenticationPrincipal)authentication.getPrincipal();
		// manually login
		if(authentication instanceof OAuth2AuthenticationToken) {
			
			var oAuth2AuthenticationToken = (OAuth2AuthenticationToken)authentication;
			var clientId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
			
			var oauth2User = (OAuth2User)authentication.getPrincipal();
			String username = "";
		
		// sign in with google
			if(clientId.equalsIgnoreCase("google")) {
				System.out.println("Google");
				username = oauth2User.getAttribute("email").toString();
			}
		
		// sign in with github
			else if(clientId.equalsIgnoreCase("github")) {
				System.out.println("Github");
				username = oauth2User.getAttribute("email")!=null ? 
						oauth2User.getAttribute("email").toString() : oauth2User.getAttribute("login").toString()+"@gmail.com";
			}
			return username;
			
		}
		else {
			System.out.println("local database");
			return authentication.getName();
		}
	}
	
	public static String getEmailVerificationLink(String emailToken) {
		String link = "http://localhost:8080/auth/verify-email?token=" + emailToken;
		return link;
	}
}
