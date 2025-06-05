package ai.gebo.security.services.impl;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

import ai.gebo.security.config.GeboAppSecurityProperties;
import ai.gebo.security.model.oauth2.GeboOauth2Exception;
import ai.gebo.security.model.oauth2.Oauth2ClientRegistration;
import ai.gebo.security.services.IGOauth2ConfigurationService;
import ai.gebo.security.services.IGUsersAdminService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

	private final IGOauth2ConfigurationService oauth2ConfigService;
	private final IGUsersAdminService userService; // servizio che gestisce la tua logica utenti
	private final GeboAppSecurityProperties securityProperties;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oauth2User = new DefaultOAuth2UserService().loadUser(userRequest);

		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		Oauth2ClientRegistration config = null;
		try {
			config = oauth2ConfigService.findOauth2ClientRegistrationByRegistrationId(registrationId);
			if (config == null)
				throw new OAuth2AuthenticationException("oauth2 configuration not found");
		} catch (GeboOauth2Exception e) {
			throw new OAuth2AuthenticationException("oauth2 configuration not found");
		}

		String email = oauth2User.getAttribute("email"); // o "preferred_username", dipende dal provider
		if (email == null)
			throw new OAuth2AuthenticationException("Missing email attribute");

		switch (securityProperties.getLoginPolicy()) {
		case TRUST_ANY_IDENTITY -> {
			userService.createUserIfNotExists(email, oauth2User.getAttributes());
		}
		case REQUIRE_REGISTERED_USER -> {
			if (userService.findUserByUsername(email) == null) {
				throw new OAuth2AuthenticationException("User not authorized");
			}
		}
		case REQUIRE_INVITATION -> {
			if (userService.findUserByUsername(email) == null) {
				throw new OAuth2AuthenticationException("User not authorized");
			}
		}
		}

		return oauth2User;
	}
}
