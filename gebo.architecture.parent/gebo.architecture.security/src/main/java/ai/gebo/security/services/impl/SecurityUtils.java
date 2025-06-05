package ai.gebo.security.services.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class SecurityUtils {
	public static String getCurrentUserEmail() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null)
			throw new RuntimeException("Not authenticated");

		if (auth instanceof OAuth2AuthenticationToken oauth2Auth) {
			OAuth2User user = (OAuth2User) oauth2Auth.getPrincipal();
			return user.getAttribute("email");
		} else {
			return auth.getName();
		}
	}
}
