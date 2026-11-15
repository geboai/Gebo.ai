/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.security.services.impl;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

import ai.gebo.security.config.GeboSecurityConfig;
import ai.gebo.security.services.IGOauth2ConfigurationService;
import ai.gebo.security.services.IGOauth2UserSyncServiceConditionedImplementationProvider;
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.IGUsersAdminService;

/**
 * Resolves an interactive OAuth2 login that is not an OpenID Connect one into a
 * Gebo identity.
 *
 * <p>
 * The decision itself, which {@code ai.gebo.security.loginPolicy} governs, is made
 * by {@link GOauth2LoginPolicyEnforcer}, because the OpenID Connect counterpart
 * {@link GOidcUserService} has to make exactly the same one, and in practice makes
 * it far more often: every configuration saved through the administration form
 * logs in with the standard {@code openid} scopes.
 * </p>
 */
public class GOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

	private final GOauth2LoginPolicyEnforcer policy;
	private final OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();

	public GOAuth2UserService(IGOauth2ConfigurationService oauth2ConfigService, IGUsersAdminService userService,
			GeboSecurityConfig securityProperties,
			IGOauth2UserSyncServiceConditionedImplementationProvider oauth2SyncService,
			IGSecurityAuditLoggerService securityAuditLoggerService) {
		this.policy = new GOauth2LoginPolicyEnforcer(oauth2ConfigService, userService, securityProperties,
				oauth2SyncService, securityAuditLoggerService);
	}

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oauth2User = delegate.loadUser(userRequest);
		policy.enforce(userRequest.getClientRegistration().getRegistrationId(), oauth2User);
		return oauth2User;
	}
}
