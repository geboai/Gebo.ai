/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.security.services.impl;

import java.util.function.Function;

import org.springframework.security.oauth2.client.oidc.authentication.OidcIdTokenValidator;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtValidators;

import ai.gebo.security.model.AuthProvider;
import ai.gebo.security.model.oauth2.GeboOauth2Exception;
import ai.gebo.security.model.oauth2.Oauth2ClientRegistration;
import ai.gebo.security.services.IGOauth2ConfigurationService;

/**
 * How the id token of an OpenID Connect login is checked.
 *
 * <p>
 * Every configuration is checked as Spring checks it when given nothing else,
 * except the Microsoft accounts one, whose people come from several tenants that
 * each sign under an issuer of their own: that one goes to
 * {@link GMicrosoftAccountsIdTokenValidator}, with the tenants its configuration
 * admits.
 * </p>
 *
 * <p>
 * Spring asks for a validator on every login, with the registration it fetched for
 * that login, so the configuration is read here each time and an edit applies to
 * the next login.
 * </p>
 */
public class GOidcIdTokenValidatorFactory implements Function<ClientRegistration, OAuth2TokenValidator<Jwt>> {

	private final IGOauth2ConfigurationService oauth2ConfigService;

	public GOidcIdTokenValidatorFactory(IGOauth2ConfigurationService oauth2ConfigService) {
		this.oauth2ConfigService = oauth2ConfigService;
	}

	@Override
	public OAuth2TokenValidator<Jwt> apply(ClientRegistration registration) {
		Oauth2ClientRegistration configuration;
		try {
			configuration = oauth2ConfigService.findOauth2ClientRegistrationByRegistrationId(
					registration.getRegistrationId());
		} catch (GeboOauth2Exception e) {
			throw new OAuth2AuthenticationException(new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
					"The configuration " + registration.getRegistrationId() + " cannot be read", null), e);
		}
		if (configuration != null && configuration.getRuntimeConfiguration() != null
				&& configuration.getRuntimeConfiguration().getProvider() == AuthProvider.microsoft) {
			return new GMicrosoftAccountsIdTokenValidator(registration,
					GMicrosoftAccountsIdTokenValidator.admittedTenants(configuration.getClientRegistration() != null
							? configuration.getClientRegistration().getCustomAttributes()
							: null));
		}
		return springDefault(registration);
	}

	/**
	 * What Spring checks an id token with when given no validator of its own: its
	 * times, then {@link OidcIdTokenValidator} against the registration.
	 *
	 * @param registration the registration logging in
	 * @return Spring's default id token validator for it
	 */
	public static OAuth2TokenValidator<Jwt> springDefault(ClientRegistration registration) {
		return JwtValidators.createDefaultWithValidators(new OidcIdTokenValidator(registration));
	}
}
