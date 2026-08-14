package ai.gebo.security.services.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.IGSecurityAuditLoggerService.SecurityEvent;
import ai.gebo.security.services.SecurityAuditTaxonomy;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

/*****************************************************************************
 * Global failure handler for every OAuth2/SSO login attempt (bad state,
 * provider error, token exchange failure, or an exception raised deeper in
 * the chain e.g. GOAuth2UserService.loadUser). Wired as the .failureHandler(...)
 * sibling to GOAuth2AuthenticationSuccessHandler so it catches every OAuth2
 * login failure regardless of where in the filter chain it originated -
 * unlike instrumenting loadUser() alone, which would miss failures elsewhere
 * in the pipeline.
 */
@AllArgsConstructor
public class GOAuth2AuthenticationFailureHandler implements AuthenticationFailureHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(GOAuth2AuthenticationFailureHandler.class);
	private final IGSecurityAuditLoggerService securityAuditLoggerService;
	private final AuthenticationFailureHandler delegate = new SimpleUrlAuthenticationFailureHandler();

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		LOGGER.warn("OAuth2 login failure", exception);
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		event.setEventType(SecurityAuditTaxonomy.EventType.AUTHENTICATION);
		event.setCategory(SecurityAuditTaxonomy.Category.AUTHENTICATION);
		event.setAction(SecurityAuditTaxonomy.Action.AUTH_LOGIN_OAUTH2_FAILURE);
		event.setOutcome(SecurityAuditTaxonomy.Outcome.FAILURE);
		event.getDetails().put("exceptionType", exception.getClass().getName());
		event.getDetails().put("message", exception.getMessage());
		securityAuditLoggerService.log(event);
		delegate.onAuthenticationFailure(request, response, exception);
	}
}
