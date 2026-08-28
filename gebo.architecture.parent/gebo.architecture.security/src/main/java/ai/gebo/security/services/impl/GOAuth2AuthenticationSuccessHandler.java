package ai.gebo.security.services.impl;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.security.services.BackendOauth2LoginSPASupportException;
import ai.gebo.security.services.IGBackendOauth2LoginSPASupportService;
import ai.gebo.security.services.IGOAuth2AuthenticationSuccessHandler;
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.IGSecurityAuditLoggerService.SecurityEvent;
import ai.gebo.security.services.SecurityAuditTaxonomy;
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
	private final IGSecurityAuditLoggerService securityAuditLoggerService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin onAuthenticationSuccess(..)");
		}
		// Request is unauthenticated as far as MDC is concerned when this fires, so
		// record the authenticated principal explicitly instead of relying on the
		// MDC-sourced userId.
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		event.setEventType(SecurityAuditTaxonomy.EventType.AUTHENTICATION);
		event.setCategory(SecurityAuditTaxonomy.Category.AUTHENTICATION);
		event.setAction(SecurityAuditTaxonomy.Action.AUTH_LOGIN_OAUTH2);
		event.setResourceId(authentication != null ? authentication.getName() : null);
		try {
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
			event.setOutcome(SecurityAuditTaxonomy.Outcome.SUCCESS);
			securityAuditLoggerService.log(event);
		} catch (RuntimeException e) {
			event.setOutcome(SecurityAuditTaxonomy.Outcome.FAILURE);
			securityAuditLoggerService.log(event);
			throw e;
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End onAuthenticationSuccess(..)");
		}
	}

}