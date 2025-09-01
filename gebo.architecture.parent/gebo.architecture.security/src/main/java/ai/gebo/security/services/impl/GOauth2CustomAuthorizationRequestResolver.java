package ai.gebo.security.services.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

import jakarta.servlet.http.HttpServletRequest;

public class GOauth2CustomAuthorizationRequestResolver implements OAuth2AuthorizationRequestResolver {

	private final OAuth2AuthorizationRequestResolver defaultResolver;

	public GOauth2CustomAuthorizationRequestResolver(ClientRegistrationRepository repo) {
		this.defaultResolver = new DefaultOAuth2AuthorizationRequestResolver(repo, "/oauth2/authorization");
	}

	@Override
	public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {
		OAuth2AuthorizationRequest req = defaultResolver.resolve(request);
		return customizeAuthorizationRequest(request, req);
	}

	@Override
	public OAuth2AuthorizationRequest resolve(HttpServletRequest request, String clientRegistrationId) {
		OAuth2AuthorizationRequest req = defaultResolver.resolve(request, clientRegistrationId);
		return customizeAuthorizationRequest(request, req);
	}

	private OAuth2AuthorizationRequest customizeAuthorizationRequest(HttpServletRequest request,
			OAuth2AuthorizationRequest req) {
		if (req == null)
			return null;
		String loginId = request.getParameter("login_id");

		Map<String, Object> extra = new HashMap(req.getAdditionalParameters());
		if (loginId != null) {
			extra.put("login_id", loginId);
		}
		return OAuth2AuthorizationRequest.from(req).additionalParameters(extra).build();
	}
}