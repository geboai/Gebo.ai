/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.security.services.impl.authmanagers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import ai.gebo.security.config.GeboSecurityConfig;
import ai.gebo.security.model.EditableUser;
import ai.gebo.security.model.GeboLoginPolicy;
import ai.gebo.security.model.Oauth2SyncUsersData;
import ai.gebo.security.model.oauth2.Oauth2ClientRegistration;
import ai.gebo.security.model.oauth2.Oauth2RuntimeConfiguration;
import ai.gebo.security.services.IGOauth2UserSyncService;
import ai.gebo.security.services.IGOauth2UserSyncServiceConditionedImplementationProvider;
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.IGSecurityAuditLoggerService.SecurityEvent;
import ai.gebo.security.services.IGUsersAdminService;
import ai.gebo.security.services.SecurityAuditTaxonomy;

/**
 * Provisions/syncs a user when a bearer token is accepted on the resource-server
 * path (which, unlike the interactive oauth2Login flow, never runs
 * {@code GOAuth2UserService}).
 *
 * <p>
 * This only acts under the {@link GeboLoginPolicy#TRUST_EVERY_OAUTH_IDENTITY}
 * policy - "every identity from a configured provider is admitted". Under the
 * invitation / self-registration policies an unknown token keeps being rejected,
 * exactly as before, since those flows provision at interactive login.
 * </p>
 *
 * <p>
 * It is invoked only from the {@code UsernameNotFoundException} branch of the
 * resource-server converters - i.e. a validated token whose user does not exist
 * locally yet - so provisioning is, by construction, unreachable on an
 * unauthenticated token. The actual create/sync is delegated to the same
 * {@link IGOauth2UserSyncServiceConditionedImplementationProvider} the login
 * flow uses, so it goes through {@link IGUsersAdminService} (seeding ACL alias
 * ids) and honours any per-provider handler. A TTL cache throttles this to at
 * most one attempt per token per window, so a token whose user cannot be created
 * does not drive a write on every request.
 * </p>
 *
 * <h2>Auditing</h2>
 * <p>
 * Every outcome of an attempt is audited as {@code oauth2IdentityProvision} - the
 * chain's decision, distinct from the {@code userAutoProvision} the store raises when
 * this goes on to ask it for a write: the identity was created, the attempt failed, or
 * it was refused (and why). This method is
 * <b>designed to swallow failures</b> - a provisioning error is reported to the caller
 * as "do not retry" so the request simply fails to authenticate as it would have
 * anyway - which means that, before this, a bearer-token identity that could never be
 * provisioned left nothing on the security log but a stream of 401s. The events here
 * are what tells those two situations apart.
 * </p>
 *
 * <p>
 * <b>The throttle is also the log's rate limit.</b> The events are emitted after the
 * TTL cache gate, so a token that keeps being presented produces one provisioning
 * event per window rather than one per request. The one branch deliberately left
 * unaudited is the policy gate: under a non-{@code TRUST_EVERY_OAUTH_IDENTITY} policy
 * this method declines on <i>every</i> request without ever consulting the cache, so
 * auditing there would emit an event per request; that refusal is a standing
 * configuration fact, and the request's own 401 already records it.
 * </p>
 */
public class GOauth2ResourceServerUserProvisioner {

	private static final Logger LOGGER = LoggerFactory.getLogger(GOauth2ResourceServerUserProvisioner.class);

	// Attribute keys, in preference order, that can carry the identity's username.
	private static final String[] USERNAME_CLAIMS = { "email", "preferred_username", "upn", "cognito:username", "sub" };

	/** {@code resourceType} of the events raised here: an external identity. */
	private static final String RESOURCE_TYPE_USER = "user";

	private final IGOauth2UserSyncServiceConditionedImplementationProvider syncProvider;
	private final IGUsersAdminService usersAdminService;
	private final GeboSecurityConfig securityConfig;
	private final Oauth2ResourceServerSyncTokenCache tokenCache;
	private final IGSecurityAuditLoggerService securityAuditLoggerService;

	public GOauth2ResourceServerUserProvisioner(
			IGOauth2UserSyncServiceConditionedImplementationProvider syncProvider,
			IGUsersAdminService usersAdminService, GeboSecurityConfig securityConfig,
			Oauth2ResourceServerSyncTokenCache tokenCache,
			IGSecurityAuditLoggerService securityAuditLoggerService) {
		this.syncProvider = syncProvider;
		this.usersAdminService = usersAdminService;
		this.securityConfig = securityConfig;
		this.tokenCache = tokenCache;
		this.securityAuditLoggerService = securityAuditLoggerService;
	}

	/**
	 * Fills in and emits a provisioning event.
	 *
	 * <p>
	 * Takes an already-created {@link SecurityEvent} so the caller-stack captured by
	 * {@code newSecurityEvent()} points at {@code provisionOnValidatedUnknownUser} -
	 * and therefore names which converter (JWT, opaque, reactive) reached it - rather
	 * than at this helper.
	 * </p>
	 */
	private void logProvisioningEvent(SecurityEvent event, String username, String outcome) {
		event.setEventType(SecurityAuditTaxonomy.EventType.USER_ADMINISTRATION);
		event.setCategory(SecurityAuditTaxonomy.Category.USER_ADMINISTRATION);
		event.setAction(SecurityAuditTaxonomy.Action.OAUTH2_IDENTITY_PROVISION);
		event.setResourceType(RESOURCE_TYPE_USER);
		event.setResourceId(username);
		event.setOutcome(outcome);
		securityAuditLoggerService.log(event);
	}

	/**
	 * Provisions the identity carried by a resource-server token that has ALREADY
	 * been validated and whose local user could not be loaded
	 * ({@code UsernameNotFoundException}).
	 *
	 * <p>
	 * Calling this only from the not-found branch of a converter makes the
	 * "must be authenticated first" guarantee self-evident from control flow: the
	 * exception is reachable only after the provider has decoded/introspected and
	 * validated the token, so this method can never run on an unauthenticated
	 * token, independent of Spring's converter-invocation ordering.
	 * </p>
	 *
	 * <p>
	 * Never throws: a provisioning failure is logged and reported as "do not retry"
	 * so the original not-found outcome stands (the request fails to authenticate,
	 * exactly as it would have without provisioning).
	 * </p>
	 *
	 * <p>
	 * Audited: see the class comment for which branches raise an event and why the
	 * policy gate does not.
	 * </p>
	 *
	 * @param runtimeConfig the provider configuration the token validated against
	 * @param token         the raw bearer token (used only as a throttle-cache key)
	 * @param attributes    the validated token claims / introspection attributes
	 * @return {@code true} if a provisioning attempt ran and the caller should retry
	 *         loading the user; {@code false} if provisioning was skipped or failed
	 *         and the not-found result should stand
	 */
	public boolean provisionOnValidatedUnknownUser(Oauth2RuntimeConfiguration runtimeConfig, String token,
			Map<String, Object> attributes) {
		if (runtimeConfig == null || attributes == null)
			return false;
		if (securityConfig.getLoginPolicy() != GeboLoginPolicy.TRUST_EVERY_OAUTH_IDENTITY)
			return false;

		// Throttle: at most one provisioning attempt per token per TTL window, whatever
		// the outcome, so a token whose user cannot be created does not drive a Mongo
		// write on every request. Evaluated before the username-claim check (which is a
		// deterministic property of the token, so nothing is lost by throttling it too)
		// so that everything below it - the writes AND the events describing them - is
		// bounded to once per token per window.
		if (tokenCache.contains(token))
			return false;
		tokenCache.put(token);

		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		event.getDetails().put("flow", "oauth2ResourceServer");
		event.getDetails().put("authProvider", String.valueOf(runtimeConfig.getProvider()));
		event.getDetails().put("loginPolicy", String.valueOf(securityConfig.getLoginPolicy()));

		String nameKey = pickUsernameClaim(attributes);
		if (nameKey == null) {
			LOGGER.debug("Resource-server token for provider {} carries no username claim; skipping provisioning",
					runtimeConfig.getProvider());
			// No username claim means there is no identity to name in resourceId - which is
			// exactly what makes this worth an event: a validated token from a configured
			// provider that Gebo cannot turn into a user is a provider/claims
			// misconfiguration, and it would otherwise only ever surface as a 401.
			event.getDetails().put("reason", "noUsernameClaim");
			event.getDetails().put("presentedClaims", new ArrayList<>(attributes.keySet()));
			logProvisioningEvent(event, null, SecurityAuditTaxonomy.Outcome.DENIED);
			return false;
		}

		String username = attributes.get(nameKey).toString();
		event.getDetails().put("usernameClaim", nameKey);
		try {
			OAuth2User oauth2User = new DefaultOAuth2User(List.of(), attributes, nameKey);
			Oauth2ClientRegistration config = new Oauth2ClientRegistration(null, runtimeConfig);
			EditableUser existing = usersAdminService.findUserByUsername(username);
			Oauth2SyncUsersData data = new Oauth2SyncUsersData(existing, oauth2User, config);
			event.getDetails().put("alreadyExisted", existing != null);

			IGOauth2UserSyncService handler = syncProvider.handlerOf(data);
			if (handler == null) {
				LOGGER.warn("No OAuth2 user sync handler resolved for provider {}", runtimeConfig.getProvider());
				event.getDetails().put("reason", "noSyncHandler");
				logProvisioningEvent(event, username, SecurityAuditTaxonomy.Outcome.FAILURE);
				return false;
			}
			// A custom handler writes without going through the audited
			// IGUsersAdminService path, so record which one ran.
			event.getDetails().put("syncHandler", handler.getClass().getName());
			handler.createOrSyncUser(data);
			logProvisioningEvent(event, username, SecurityAuditTaxonomy.Outcome.SUCCESS);
			return true;
		} catch (RuntimeException e) {
			// Do not break authentication on a sync failure: the caller keeps the original
			// not-found outcome and the request fails to authenticate as before.
			LOGGER.warn("Resource-server OAuth2 user provisioning failed for provider {}: {}",
					runtimeConfig.getProvider(), e.getMessage());
			// ... which is precisely why it is audited: swallowed here, the failure is
			// invisible to everything downstream.
			event.getDetails().put("error", e.getMessage());
			logProvisioningEvent(event, username, SecurityAuditTaxonomy.Outcome.FAILURE);
			return false;
		}
	}

	private static String pickUsernameClaim(Map<String, Object> attributes) {
		for (String claim : USERNAME_CLAIMS) {
			Object value = attributes.get(claim);
			if (value != null && !value.toString().trim().isEmpty())
				return claim;
		}
		return null;
	}
}
