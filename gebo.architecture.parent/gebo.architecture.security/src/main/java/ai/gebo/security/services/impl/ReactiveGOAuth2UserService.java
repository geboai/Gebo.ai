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
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.IGSecurityAuditLoggerService.SecurityEvent;
import ai.gebo.security.services.IGUsersAdminService;
import ai.gebo.security.services.SecurityAuditTaxonomy;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * The reactive twin of {@link GOAuth2UserService}, and audited the same way and for
 * the same reasons - see that class for why the provisioning decision gets events of
 * its own rather than relying on the login's success/failure events.
 *
 * <p>
 * One difference in shape: refusals here return {@code Mono.error(...)} instead of
 * throwing, so the event is emitted before the error is returned rather than in a
 * catch block.
 * </p>
 *
 * Gebo.ai comment agent
 */
@AllArgsConstructor
public class ReactiveGOAuth2UserService implements ReactiveOAuth2UserService<OAuth2UserRequest, OAuth2User> {

	private final IGOauth2ConfigurationService oauth2ConfigService;
	private final IGUsersAdminService userService;
	private final GeboSecurityConfig securityProperties;
	private final IGOauth2UserSyncServiceConditionedImplementationProvider oauth2SyncService;
	private final IGSecurityAuditLoggerService securityAuditLoggerService;

	/** {@code resourceType} of the events raised here: an external identity. */
	private static final String RESOURCE_TYPE_USER = "user";

	/** See {@code GOAuth2UserService}'s helper of the same name. */
	private void logProvisioningEvent(SecurityEvent event, String action, String username, String outcome) {
		event.setEventType(SecurityAuditTaxonomy.EventType.USER_ADMINISTRATION);
		event.setCategory(SecurityAuditTaxonomy.Category.USER_ADMINISTRATION);
		event.setAction(action);
		event.setResourceType(RESOURCE_TYPE_USER);
		event.setResourceId(username);
		event.setOutcome(outcome);
		securityAuditLoggerService.log(event);
	}

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

			SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
			event.getDetails().put("flow", "oauth2LoginReactive");
			event.getDetails().put("registrationId", registrationId);
			event.getDetails().put("loginPolicy", String.valueOf(securityProperties.getLoginPolicy()));
			event.getDetails().put("authProvider", String.valueOf(authProvider));
			event.getDetails().put("alreadyExisted", user != null);
			event.getDetails().put("syncHandler", service != null ? service.getClass().getName() : null);
			String action = user == null ? SecurityAuditTaxonomy.Action.OAUTH2_IDENTITY_PROVISION
					: SecurityAuditTaxonomy.Action.OAUTH2_IDENTITY_SYNC;
			switch (securityProperties.getLoginPolicy()) {
			case TRUST_EVERY_OAUTH_IDENTITY: {
				// Create user if not exists (can be async if needed)
				try {
					service.createOrSyncUser(_data);
				} catch (RuntimeException e) {
					event.getDetails().put("error", e.getMessage());
					logProvisioningEvent(event, action, email, SecurityAuditTaxonomy.Outcome.FAILURE);
					throw e;
				}
				logProvisioningEvent(event, action, email, SecurityAuditTaxonomy.Outcome.SUCCESS);
			}
				break;
			case USER_SELF_REGISTERS:
			case REQUIRE_INVITATION: {

				if (user == null || user.getAuthProvider() != authProvider) {
					event.getDetails().put("reason", user == null ? "unknownIdentity" : "authProviderMismatch");
					if (user != null)
						event.getDetails().put("registeredAuthProvider", String.valueOf(user.getAuthProvider()));
					logProvisioningEvent(event, SecurityAuditTaxonomy.Action.OAUTH2_IDENTITY_PROVISION, email,
							SecurityAuditTaxonomy.Outcome.DENIED);
					return Mono.error(new OAuth2AuthenticationException("User not authorized"));
				}
				try {
					service.syncUser(_data);
				} catch (RuntimeException e) {
					event.getDetails().put("error", e.getMessage());
					logProvisioningEvent(event, SecurityAuditTaxonomy.Action.OAUTH2_IDENTITY_SYNC, email,
							SecurityAuditTaxonomy.Outcome.FAILURE);
					throw e;
				}
				logProvisioningEvent(event, SecurityAuditTaxonomy.Action.OAUTH2_IDENTITY_SYNC, email,
						SecurityAuditTaxonomy.Outcome.SUCCESS);
			}
			}
			return Mono.just(oauth2User);
		});
	}
}
