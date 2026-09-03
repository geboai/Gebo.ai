package ai.gebo.security.services.impl;

import java.util.Map;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
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

/**
 * Resolves the interactive OAuth2/OIDC login (the {@code oauth2Login} redirect flow)
 * into a Gebo identity, and is where {@code ai.gebo.security.loginPolicy} decides
 * whether an external identity may become - or update - a local user.
 *
 * <h2>Auditing</h2>
 * <p>
 * That decision is a security event in its own right, distinct from the login it
 * happens inside: {@code GOAuth2AuthenticationSuccessHandler} /
 * {@code GOAuth2AuthenticationFailureHandler} record "an OAuth2 login succeeded or
 * failed", which cannot say <b>why</b> - a login refused here because the identity is
 * unknown under {@code REQUIRE_INVITATION} is indistinguishable, in those events, from
 * a bad token exchange, and carries no username at all. So this class emits its own
 * {@code oauth2IdentityProvision} / {@code oauth2IdentitySync} events, with the
 * identity, the provider, the policy in force, and the reason a refusal was a refusal.
 * </p>
 *
 * <p>
 * It also names the resolved {@code IGOauth2UserSyncService} handler: a deployment that
 * plugs in its own handler bypasses {@code IGUsersAdminService.createUserIfNotExists}
 * entirely, so without this event the write it performs would have no provisioning
 * trail at all.
 * </p>
 *
 * Gebo.ai comment agent
 */
@AllArgsConstructor
public class GOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

	private final IGOauth2ConfigurationService oauth2ConfigService;
	private final IGUsersAdminService userService; // servizio che gestisce la tua logica utenti
	private final GeboSecurityConfig securityProperties;
	private final IGOauth2UserSyncServiceConditionedImplementationProvider oauth2SyncService;
	private final IGSecurityAuditLoggerService securityAuditLoggerService;

	/** {@code resourceType} of the events raised here: an external identity. */
	private static final String RESOURCE_TYPE_USER = "user";

	/**
	 * Fills in and emits a provisioning event.
	 *
	 * <p>
	 * Takes an already-created {@link SecurityEvent} so the caller-stack
	 * {@code newSecurityEvent()} captured points at {@code loadUser}, not at this
	 * helper - the rule every other instrumented class here follows.
	 * </p>
	 */
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
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		Map<String, Object> params = userRequest.getAdditionalParameters();
		OAuth2User oauth2User = new DefaultOAuth2UserService().loadUser(userRequest);

		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		Oauth2ClientRegistration config = null;
		AuthProvider authProvider = null;
		try {
			config = oauth2ConfigService.findOauth2ClientRegistrationByRegistrationId(registrationId);
			if (config == null)
				throw new OAuth2AuthenticationException("oauth2 configuration not found");
			authProvider = config.getRuntimeConfiguration().getProvider();
		} catch (GeboOauth2Exception e) {
			throw new OAuth2AuthenticationException("oauth2 configuration not found");
		}

		String email = oauth2User.getAttribute("email"); // o "preferred_username", dipende dal provider
		if (email == null)
			throw new OAuth2AuthenticationException("Missing email attribute");
		EditableUser user = userService.findUserByUsername(email);
		Oauth2SyncUsersData data = new Oauth2SyncUsersData(user, oauth2User, config);
		IGOauth2UserSyncService service = oauth2SyncService.handlerOf(data);

		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		event.getDetails().put("flow", "oauth2Login");
		event.getDetails().put("registrationId", registrationId);
		event.getDetails().put("loginPolicy", String.valueOf(securityProperties.getLoginPolicy()));
		event.getDetails().put("authProvider", String.valueOf(authProvider));
		event.getDetails().put("alreadyExisted", user != null);
		// Which handler would do the write: a custom IGOauth2UserSyncService does not go
		// through the audited IGUsersAdminService path, so this is the only place the
		// identity of the writer is recorded.
		event.getDetails().put("syncHandler", service != null ? service.getClass().getName() : null);
		// "This identity became a user" and "this identity was refreshed" are different
		// events to a SIEM - the first is an account appearing out of a trusted provider.
		String action = user == null ? SecurityAuditTaxonomy.Action.OAUTH2_IDENTITY_PROVISION
				: SecurityAuditTaxonomy.Action.OAUTH2_IDENTITY_SYNC;
		switch (securityProperties.getLoginPolicy()) {
		case TRUST_EVERY_OAUTH_IDENTITY: {
			try {
				service.createOrSyncUser(data);
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
				// The refusal, with its reason and its username. The failure handler further
				// down the chain will log the login failure, but it sees neither: an identity
				// turned away because it was never invited, and one turned away because it is
				// registered against a DIFFERENT provider (someone presenting an account of
				// the same name from another IdP) look identical there.
				event.getDetails().put("reason", user == null ? "unknownIdentity" : "authProviderMismatch");
				if (user != null)
					event.getDetails().put("registeredAuthProvider", String.valueOf(user.getAuthProvider()));
				logProvisioningEvent(event, SecurityAuditTaxonomy.Action.OAUTH2_IDENTITY_PROVISION, email,
						SecurityAuditTaxonomy.Outcome.DENIED);
				throw new OAuth2AuthenticationException("User not authorized");
			}
			try {
				service.syncUser(data);
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

		return oauth2User;
	}
}
