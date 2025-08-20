package ai.gebo.security.services.impl;

import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

import com.fasterxml.jackson.databind.ObjectMapper;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.crypting.services.IGeboCryptingService;
import ai.gebo.security.LocalJwtTokenProvider;
import ai.gebo.security.SecurityHeaderUtil;
import ai.gebo.security.model.SecurityHeaderData;
import ai.gebo.security.model.oauth2.Oauth2DeliveryData;
import ai.gebo.security.repository.Oauth2DeliveryDataRepository;
import ai.gebo.security.services.BackendOauth2LoginSPASupportException;
import ai.gebo.security.services.IGBackendOauth2LoginSPASupportService;
import ai.gebo.security.services.IGOAuth2AuthenticationSuccessHandler;
import ai.gebo.security.util.CookieUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GOAuth2AuthenticationSuccessHandler implements IGOAuth2AuthenticationSuccessHandler {
	private final static Logger LOGGER = LoggerFactory.getLogger(GOAuth2AuthenticationSuccessHandler.class);
	private final IGBackendOauth2LoginSPASupportService backendOauth2LoginSpaSupportService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin onAuthenticationSuccess(..)");
		}
		Optional<Cookie> cookieValue = CookieUtils.getCookie(request,
				IGBackendOauth2LoginSPASupportService.BACKEND_OAUTH2_COOKIE_NAME);
		if (cookieValue.isPresent()) {
			DefaultOidcUser principal = (DefaultOidcUser) authentication.getPrincipal();
			String loginAttemptCryptedId = cookieValue.get().getValue();
			String remoteAddress = request.getRemoteAddr();
			try {
				String nextUri = backendOauth2LoginSpaSupportService.oauth2LoginSuccess(loginAttemptCryptedId,
						remoteAddress, principal);
				response.sendRedirect(nextUri);
			} catch (BackendOauth2LoginSPASupportException | GeboCryptSecretException e) {
				throw new RuntimeException("Exception in IGBackendOauth2LoginSPASupportService", e);
			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End onAuthenticationSuccess(..)");
		}
	}

}