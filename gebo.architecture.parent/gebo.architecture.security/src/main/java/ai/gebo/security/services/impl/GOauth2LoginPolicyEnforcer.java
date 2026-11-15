/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.security.services.impl;

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
 * Where {@code ai.gebo.security.loginPolicy} decides whether an identity arriving
 * from a configured provider may become, or refresh, a user of this installation.
 *
 * <p>
 * This used to live inside the plain OAuth2 user service, and so only ran for a
 * login that was not an OpenID Connect one. Every configuration saved through the
 * administration form asks for the standard {@code openid profile email} scopes,
 * which makes every such login an OpenID Connect login, and those went to Spring's
 * own user service instead: the policy never ran, and an identity unknown here was
 * admitted as far as a token. The decision belongs to neither service in
 * particular, so it sits here and both call it.
 * </p>
 *
 * <h2>Auditing</h2>
 * <p>
 * The decision is a security event in its own right, separate from the login it
 * happens inside: the success and failure handlers record that an oauth2 login
 * succeeded or failed, which cannot say why, and carries no username when it was
 * refused. The events raised here name the identity, the provider, the policy in
 * force, and what made a refusal a refusal.
 * </p>
 *
 * <h2>Disabled accounts</h2>
 * <p>
 * An account an administrator disabled is refused here whatever the policy, with
 * the reason {@code userDisabled}. The password login and every token already
 * refuse it, through the user details service; this login did not, so a disabled
 * person signing in with their provider was handed a session token, and only the
 * requests that followed refused it, with an error rather than the login page's
 * refusal.
 * </p>
 */
@AllArgsConstructor
public class GOauth2LoginPolicyEnforcer {

	private final IGOauth2ConfigurationService oauth2ConfigService;
	private final IGUsersAdminService userService;
	private final GeboSecurityConfig securityProperties;
	private final IGOauth2UserSyncServiceConditionedImplementationProvider oauth2SyncService;
	private final IGSecurityAuditLoggerService securityAuditLoggerService;

	private static final String RESOURCE_TYPE_USER = "user";

	/**
	 * Fills in and emits a provisioning event.
	 *
	 * <p>
	 * It takes an event that is already made and never calls
	 * {@code newSecurityEvent()} itself, because that call captures the stack it is
	 * made from: made here, every event would name this one shared helper. Made where
	 * the decision starts, in {@link #enforce}, the captured stack names the enforcer
	 * and, right behind it, whichever user service was called, so an OpenID Connect
	 * login and a plain oauth2 one can still be told apart in the audit.
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

	/**
	 * Applies the policy to an identity that has just been authenticated by its
	 * provider.
	 *
	 * @param registrationId the configuration the login came through
	 * @param oauth2User     the identity the provider described
	 * @throws OAuth2AuthenticationException when the identity may not log in here
	 */
	public void enforce(String registrationId, OAuth2User oauth2User) {
		enforce(registrationId, oauth2User, "oauth2Login");
	}

	/**
	 * As {@link #enforce(String, OAuth2User)}, recording the login as the flow
	 * given, so the reactive login can still be told apart in the audit.
	 *
	 * @param registrationId the configuration the login came through
	 * @param oauth2User     the identity the provider described
	 * @param flow           the {@code flow} detail of the events raised
	 * @throws OAuth2AuthenticationException when the identity may not log in here
	 */
	public void enforce(String registrationId, OAuth2User oauth2User, String flow) {
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
		event.getDetails().put("flow", flow);
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
			refuseIfDisabled(event, user, email);
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
			// after the provider check: an account claimed through another provider is the
			// more telling reason to record, disabled or not
			refuseIfDisabled(event, user, email);
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
	}

	/**
	 * Refuses an existing account an administrator disabled, before it is refreshed
	 * from the provider.
	 */
	private void refuseIfDisabled(SecurityEvent event, EditableUser user, String email) {
		if (user != null && Boolean.TRUE.equals(user.getDisabled())) {
			event.getDetails().put("reason", "userDisabled");
			logProvisioningEvent(event, SecurityAuditTaxonomy.Action.OAUTH2_IDENTITY_SYNC, email,
					SecurityAuditTaxonomy.Outcome.DENIED);
			throw new OAuth2AuthenticationException("User disabled");
		}
	}
}
