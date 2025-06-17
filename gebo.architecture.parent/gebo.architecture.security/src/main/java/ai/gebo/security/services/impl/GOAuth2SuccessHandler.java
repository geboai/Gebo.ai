package ai.gebo.security.services.impl;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import ai.gebo.security.services.IGOauth2InitializationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GOAuth2SuccessHandler implements AuthenticationSuccessHandler {

	private final OAuth2AuthorizedClientService clientService;
	private final IGOauth2InitializationService initializationService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		OAuth2AuthenticationToken oauth2Token = ((OAuth2AuthenticationToken) authentication);
		String registrationId = oauth2Token.getAuthorizedClientRegistrationId();
		String principalName = authentication.getName();
		OAuth2AuthorizedClient client = clientService.loadAuthorizedClient(registrationId, principalName);
		String accessToken = client.getAccessToken().getTokenValue();
		StringBuffer baseUrl = request.getRequestURL();
		String loginId = request.getParameter("login_id");
		initializationService.updateAuthenticated(loginId,principalName,accessToken);
		String redirectUrl = "/ui/oauth2-land?login_id=" + loginId;
		response.sendRedirect(redirectUrl);
	}
}