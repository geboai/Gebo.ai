/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.oidc.authentication.OidcIdTokenDecoderFactory;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.JwtDecoderFactory;

import ai.gebo.security.services.IGOauth2ConfigurationService;
import ai.gebo.security.services.IGOauth2UserSyncServiceConditionedImplementationProvider;
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.IGUsersAdminService;
import ai.gebo.security.services.impl.GOidcIdTokenValidatorFactory;
import ai.gebo.security.services.impl.GOidcUserService;

/**
 * Puts Gebo's own OpenID Connect user service in front of Spring's, and Gebo's own
 * check of the id token.
 *
 * <p>
 * When an {@code oauth2Login} does not name one, Spring looks for a single bean of
 * each of these exact types in the context and falls back to its own only if it
 * finds none. That is how the login policy, and the tenant check of the Microsoft
 * accounts configuration, reach the OpenID Connect flow without the filter chain
 * having to be touched, and it is the flow every configuration saved through the
 * administration form uses, since they all log in with the standard {@code openid}
 * scopes.
 * </p>
 *
 * <p>
 * The lookup takes a bean only if it is unique, so there must be exactly one of
 * each: a second would send Spring silently back to its own, and the policy or the
 * check would stop being applied, with nothing failing to say so.
 * </p>
 */
@Configuration
public class GeboOidcLoginConfig {

	@Bean
	public OAuth2UserService<OidcUserRequest, OidcUser> geboOidcUserService(
			IGOauth2ConfigurationService oauth2ConfigurationService, IGUsersAdminService userAdminService,
			GeboSecurityConfig securityProperties,
			IGOauth2UserSyncServiceConditionedImplementationProvider oauth2UserSyncProvider,
			IGSecurityAuditLoggerService securityAuditLoggerService) {
		return new GOidcUserService(oauth2ConfigurationService, userAdminService, securityProperties,
				oauth2UserSyncProvider, securityAuditLoggerService);
	}

	/**
	 * Spring's own id token decoding, with the checks that follow it chosen per
	 * configuration by {@link GOidcIdTokenValidatorFactory}. Spring uses it for the
	 * login and for refreshed tokens alike.
	 */
	@Bean
	public JwtDecoderFactory<ClientRegistration> geboOidcIdTokenDecoderFactory(
			IGOauth2ConfigurationService oauth2ConfigurationService) {
		OidcIdTokenDecoderFactory factory = new OidcIdTokenDecoderFactory();
		factory.setJwtValidatorFactory(new GOidcIdTokenValidatorFactory(oauth2ConfigurationService));
		return factory;
	}
}
