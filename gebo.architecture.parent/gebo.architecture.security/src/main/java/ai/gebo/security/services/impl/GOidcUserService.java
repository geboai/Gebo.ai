/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.security.services.impl;

import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import ai.gebo.security.config.GeboSecurityConfig;
import ai.gebo.security.services.IGOauth2ConfigurationService;
import ai.gebo.security.services.IGOauth2UserSyncServiceConditionedImplementationProvider;
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.IGUsersAdminService;

/**
 * The same decision as {@link GOAuth2UserService}, for the logins that actually
 * happen.
 *
 * <p>
 * A configuration saved through the administration form carries no scopes of its
 * own, so an authentication configuration logs in with {@code openid profile
 * email}. That {@code openid} makes it an OpenID Connect login, which Spring hands
 * to its own user service unless the context offers one of this type. Nothing did,
 * so no policy was applied to a browser login at all: an identity the installation
 * had never heard of was carried through the login, a token was minted in its name,
 * and only the first call it then made was refused, which the interface reported as
 * an expired session.
 * </p>
 *
 * <p>
 * The identity itself is still fetched by Spring's own service, so what the rest of
 * the login receives is unchanged, down to the concrete type the success handler
 * expects.
 * </p>
 */
public class GOidcUserService implements OAuth2UserService<OidcUserRequest, OidcUser> {

	private final GOauth2LoginPolicyEnforcer policy;
	private final OAuth2UserService<OidcUserRequest, OidcUser> delegate = new OidcUserService();

	public GOidcUserService(IGOauth2ConfigurationService oauth2ConfigService, IGUsersAdminService userService,
			GeboSecurityConfig securityProperties,
			IGOauth2UserSyncServiceConditionedImplementationProvider oauth2SyncService,
			IGSecurityAuditLoggerService securityAuditLoggerService) {
		this.policy = new GOauth2LoginPolicyEnforcer(oauth2ConfigService, userService, securityProperties,
				oauth2SyncService, securityAuditLoggerService);
	}

	@Override
	public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
		OidcUser oidcUser = delegate.loadUser(userRequest);
		policy.enforce(userRequest.getClientRegistration().getRegistrationId(), oidcUser);
		return oidcUser;
	}
}
