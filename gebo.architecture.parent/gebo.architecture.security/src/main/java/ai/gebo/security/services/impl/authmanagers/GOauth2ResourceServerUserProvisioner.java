/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.security.services.impl.authmanagers;

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
import ai.gebo.security.services.IGUsersAdminService;

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
 */
public class GOauth2ResourceServerUserProvisioner {

	private static final Logger LOGGER = LoggerFactory.getLogger(GOauth2ResourceServerUserProvisioner.class);

	// Attribute keys, in preference order, that can carry the identity's username.
	private static final String[] USERNAME_CLAIMS = { "email", "preferred_username", "upn", "cognito:username", "sub" };

	private final IGOauth2UserSyncServiceConditionedImplementationProvider syncProvider;
	private final IGUsersAdminService usersAdminService;
	private final GeboSecurityConfig securityConfig;
	private final Oauth2ResourceServerSyncTokenCache tokenCache;

	public GOauth2ResourceServerUserProvisioner(
			IGOauth2UserSyncServiceConditionedImplementationProvider syncProvider,
			IGUsersAdminService usersAdminService, GeboSecurityConfig securityConfig,
			Oauth2ResourceServerSyncTokenCache tokenCache) {
		this.syncProvider = syncProvider;
		this.usersAdminService = usersAdminService;
		this.securityConfig = securityConfig;
		this.tokenCache = tokenCache;
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

		String nameKey = pickUsernameClaim(attributes);
		if (nameKey == null) {
			LOGGER.debug("Resource-server token for provider {} carries no username claim; skipping provisioning",
					runtimeConfig.getProvider());
			return false;
		}
		// Throttle: at most one provisioning attempt per token per TTL window, whatever
		// the outcome, so a token whose user cannot be created does not drive a Mongo
		// write on every request.
		if (tokenCache.contains(token))
			return false;
		tokenCache.put(token);

		try {
			OAuth2User oauth2User = new DefaultOAuth2User(List.of(), attributes, nameKey);
			Oauth2ClientRegistration config = new Oauth2ClientRegistration(null, runtimeConfig);
			String username = attributes.get(nameKey).toString();
			EditableUser existing = usersAdminService.findUserByUsername(username);
			Oauth2SyncUsersData data = new Oauth2SyncUsersData(existing, oauth2User, config);

			IGOauth2UserSyncService handler = syncProvider.handlerOf(data);
			if (handler == null) {
				LOGGER.warn("No OAuth2 user sync handler resolved for provider {}", runtimeConfig.getProvider());
				return false;
			}
			handler.createOrSyncUser(data);
			return true;
		} catch (RuntimeException e) {
			// Do not break authentication on a sync failure: the caller keeps the original
			// not-found outcome and the request fails to authenticate as before.
			LOGGER.warn("Resource-server OAuth2 user provisioning failed for provider {}: {}",
					runtimeConfig.getProvider(), e.getMessage());
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
