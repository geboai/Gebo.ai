package ai.gebo.security.services.impl;

import java.io.IOException;
import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

import ai.gebo.security.services.IGOAuth2AuthenticationSuccessHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GOAuth2AuthenticationSuccessHandler implements IGOAuth2AuthenticationSuccessHandler {
	private final static Logger LOGGER = LoggerFactory.getLogger(GOAuth2AuthenticationSuccessHandler.class);

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin onAuthenticationSuccess(..)");
		}

		DefaultOidcUser principal = (DefaultOidcUser) authentication.getPrincipal();
		//Cookie cookieToken = new Cookie("TOKEN", principal.getIdToken().getTokenValue());
		//response.addCookie(cookieToken);
		String cookieName = "TOKEN";
		String jwt = principal.getIdToken().getTokenValue();
		ResponseCookie.ResponseCookieBuilder cb = ResponseCookie.from(cookieName, jwt).httpOnly(false).secure(true)
				.path("/").maxAge(Duration.ofMinutes(10)).sameSite("Lax");
		response.addHeader("Set-Cookie", cb.build().toString());
		response.sendRedirect("http://localhost:4200/ui/oauth2-land");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End onAuthenticationSuccess(..)");
		}
	}

}