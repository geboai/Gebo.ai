package ai.gebo.security.services.impl;

import org.springframework.security.oauth2.client.userinfo.DefaultReactiveOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.ReactiveOAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

import ai.gebo.security.config.GeboSecurityConfig;
import ai.gebo.security.model.AuthProvider;
import ai.gebo.security.model.EditableUser;
import ai.gebo.security.model.Oauth2SyncUsersData;
import ai.gebo.security.model.oauth2.GeboOauth2Exception;
import ai.gebo.security.model.oauth2.Oauth2ClientRegistration;
import ai.gebo.security.services.IGOauth2ConfigurationService;
import ai.gebo.security.services.IGOauth2UserSyncService;
import ai.gebo.security.services.IGOauth2UserSyncServiceConditionedImplementationProvider;
import ai.gebo.security.services.IGUsersAdminService;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class ReactiveGOAuth2UserService implements ReactiveOAuth2UserService<OAuth2UserRequest, OAuth2User> {

	private final IGOauth2ConfigurationService oauth2ConfigService;
	private final IGUsersAdminService userService;
	private final GeboSecurityConfig securityProperties;
	private final IGOauth2UserSyncServiceConditionedImplementationProvider oauth2SyncService;

	@Override
	public Mono<OAuth2User> loadUser(final OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		Mono<OAuth2User> data = new DefaultReactiveOAuth2UserService().loadUser(userRequest);
		String registrationId = userRequest.getClientRegistration().getRegistrationId();

		return data.flatMap(oauth2User -> {
			Oauth2ClientRegistration config;
			AuthProvider authProvider = null;
			try {
				config = oauth2ConfigService.findOauth2ClientRegistrationByRegistrationId(registrationId);
				if (config == null) {
					return Mono.error(new OAuth2AuthenticationException("OAuth2 configuration not found"));
				}
				authProvider = config.getRuntimeConfiguration().getProvider();
			} catch (GeboOauth2Exception e) {
				return Mono.error(new OAuth2AuthenticationException("OAuth2 configuration error"));
			}

			String email = oauth2User.getAttribute("email");
			if (email == null) {
				return Mono.error(new OAuth2AuthenticationException("Missing email attribute"));
			}
			EditableUser user = userService.findUserByUsername(email);
			Oauth2SyncUsersData _data = new Oauth2SyncUsersData(user, oauth2User, config);
			IGOauth2UserSyncService service = oauth2SyncService.handlerOf(_data);
			switch (securityProperties.getLoginPolicy()) {
			case TRUST_EVERY_OAUTH_IDENTITY: {
				// Create user if not exists (can be async if needed)
				service.createOrSyncUser(_data);
			}
				break;
			case USER_SELF_REGISTERS:
			case REQUIRE_INVITATION: {

				if (user == null || user.getAuthProvider() != authProvider) {
					return Mono.error(new OAuth2AuthenticationException("User not authorized"));
				}
				service.syncUser(_data);
			}
			}
			return Mono.just(oauth2User);
		});
	}
}
