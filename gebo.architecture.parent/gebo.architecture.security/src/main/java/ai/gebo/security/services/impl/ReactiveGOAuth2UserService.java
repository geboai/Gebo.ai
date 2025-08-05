package ai.gebo.security.services.impl;

import org.springframework.security.oauth2.client.userinfo.DefaultReactiveOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.ReactiveOAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

import ai.gebo.security.config.GeboAppSecurityProperties;
import ai.gebo.security.model.oauth2.GeboOauth2Exception;
import ai.gebo.security.model.oauth2.Oauth2ClientRegistration;
import ai.gebo.security.services.IGOauth2ConfigurationService;
import ai.gebo.security.services.IGUsersAdminService;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class ReactiveGOAuth2UserService implements ReactiveOAuth2UserService<OAuth2UserRequest, OAuth2User> {

	private final IGOauth2ConfigurationService oauth2ConfigService;
	private final IGUsersAdminService userService;
	private final GeboAppSecurityProperties securityProperties;

	@Override
	public Mono<OAuth2User> loadUser(final OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		Mono<OAuth2User> data = new DefaultReactiveOAuth2UserService().loadUser(userRequest);
		String registrationId = userRequest.getClientRegistration().getRegistrationId();

		return data.flatMap(oauth2User -> {
			Oauth2ClientRegistration config;
			try {
				config = oauth2ConfigService.findOauth2ClientRegistrationByRegistrationId(registrationId);
				if (config == null) {
					return Mono.error(new OAuth2AuthenticationException("OAuth2 configuration not found"));
				}
			} catch (GeboOauth2Exception e) {
				return Mono.error(new OAuth2AuthenticationException("OAuth2 configuration error"));
			}

			String email = oauth2User.getAttribute("email");
			if (email == null) {
				return Mono.error(new OAuth2AuthenticationException("Missing email attribute"));
			}

			switch (securityProperties.getLoginPolicy()) {
			case TRUST_EVERY_OAUTH_IDENTITY: {
				// Create user if not exists (can be async if needed)
				userService.createUserIfNotExists(email, oauth2User.getAttributes());
			}
				break;
			case USER_SELF_REGISTERS:
			case REQUIRE_INVITATION: {
				if (userService.findUserByUsername(email) == null) {
					return Mono.error(new OAuth2AuthenticationException("User not authorized"));
				}
			}
			}
			return Mono.just(oauth2User);
		});
	}
}
