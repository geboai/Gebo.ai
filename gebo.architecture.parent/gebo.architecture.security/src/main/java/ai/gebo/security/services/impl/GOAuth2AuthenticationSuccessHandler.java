package ai.gebo.security.services.impl;

import java.io.IOException;
import java.time.Duration;

import javax.management.RuntimeErrorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

import com.fasterxml.jackson.databind.ObjectMapper;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.crypting.services.IGeboCryptingService;
import ai.gebo.security.LocalJwtTokenProvider;
import ai.gebo.security.SecurityHeaderUtil;
import ai.gebo.security.model.SecurityHeaderData;
import ai.gebo.security.services.IGOAuth2AuthenticationSuccessHandler;
import ai.gebo.security.util.CookieUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GOAuth2AuthenticationSuccessHandler implements IGOAuth2AuthenticationSuccessHandler {
	private static final String UI_OAUTH2_SUCCESS_FORWARD_PROPERTY = "ui_oauth2_success_forward";
	private final static String UI_FORWARD = System.getProperty(UI_OAUTH2_SUCCESS_FORWARD_PROPERTY) != null
			? System.getProperty(UI_OAUTH2_SUCCESS_FORWARD_PROPERTY)
			: "/ui/oauth2-land";
	private final static Logger LOGGER = LoggerFactory.getLogger(GOAuth2AuthenticationSuccessHandler.class);
	private final static ObjectMapper objectMapper = new ObjectMapper();
	private final LocalJwtTokenProvider tokenProvider;
	private final IGeboCryptingService cryptingService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin onAuthenticationSuccess(..)");
		}

		DefaultOidcUser principal = (DefaultOidcUser) authentication.getPrincipal();
		String cookieName = LocalJwtTokenProvider.TEMPORARY_OAUTH2_COOKIENAME;
		String jwt = tokenProvider.createToken(principal);
		SecurityHeaderData data = SecurityHeaderUtil.createSelfsignedJwtSecurityHeaderOauth2(jwt);
		String json = objectMapper.writeValueAsString(data);
		try {
			String crypted = cryptingService.crypt(json);
			// CookieUtils.addCookie(response, cookieName, crypted, 1200);

			ResponseCookie.ResponseCookieBuilder cb = ResponseCookie.from(cookieName, crypted).httpOnly(true)
					.secure(false).path("/").maxAge(Duration.ofMinutes(5)).sameSite("Lax");
			response.addHeader("Set-Cookie", cb.build().toString());

			response.sendRedirect(UI_FORWARD);

		} catch (GeboCryptSecretException e) {
			response.setStatus(500);
			throw new RuntimeException("Exception crypting", e);

		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End onAuthenticationSuccess(..)");
		}
	}

}