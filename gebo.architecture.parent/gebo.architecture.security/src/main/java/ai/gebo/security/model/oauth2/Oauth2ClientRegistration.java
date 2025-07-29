/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
package ai.gebo.security.model.oauth2;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistration.Builder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import ai.gebo.secrets.model.GeboOauth2SecretContent;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Represents the OAuth2 client registration configuration.
 * 
 * Consists of a secret content part, and runtime configuration part.
 * 
 * AI generated comments
 */
@AllArgsConstructor
@Getter
public class Oauth2ClientRegistration {
	// Contains the secret content necessary for OAuth2 client registration.
	private final GeboOauth2SecretContent clientRegistration;

	// Contains the runtime configuration for OAuth2 client registration.
	private final Oauth2RuntimeConfiguration runtimeConfiguration;

	/**
	 * Converts an Oauth2ClientRegistration to a ClientRegistration object.
	 *
	 * @param registration The Oauth2ClientRegistration to convert.
	 * @return The corresponding ClientRegistration object, or null if the input is
	 *         null.
	 */
	public static ClientRegistration toClientRegistration(Oauth2ClientRegistration registration) {
		if (registration == null)
			return null;

		// Create a new ClientRegistration.Builder using the registration ID from
		// runtime configuration.
		Builder builder = ClientRegistration
				.withRegistrationId(registration.getRuntimeConfiguration().getRegistrationId());
		if (registration.getRuntimeConfiguration().getAuthGrantType() != null) {
			builder.authorizationGrantType(
					translateGrantType(registration.getRuntimeConfiguration().getAuthGrantType()));
		}
		if (registration.getRuntimeConfiguration().getClientAuthMethod() != null) {
			builder.clientAuthenticationMethod(
					translateAuthenticationMethod(registration.getRuntimeConfiguration().getClientAuthMethod()));
		}
		builder.clientId(registration.getClientRegistration().getClientId());
		builder.clientSecret(registration.getClientRegistration().getSecret());
		builder.registrationId(registration.getRuntimeConfiguration().getRegistrationId());
		builder.clientName(registration.getClientRegistration().getClientId());
		builder.authorizationUri(registration.getRuntimeConfiguration().getProviderConfig().getAuthorizationUri());
		builder.tokenUri(registration.getRuntimeConfiguration().getProviderConfig().getTokenUri());
		builder.userInfoUri(registration.getRuntimeConfiguration().getProviderConfig().getUserInfoUri());
		builder.issuerUri(registration.getRuntimeConfiguration().getProviderConfig().getIssuerUri());
		Map<String, Object> configurationMetaData = new HashMap<String, Object>();
		if (registration.getClientRegistration().getTenantId() != null) {
			configurationMetaData.put("tenant", registration.getClientRegistration().getTenantId());
		}
		builder.providerConfigurationMetadata(configurationMetaData);
		builder.redirectUri("{baseUrl}/login/oauth2/code/{registrationId}");
		// Build and return the ClientRegistration.
		return builder.build();
	}

	private static ClientAuthenticationMethod translateAuthenticationMethod(Oauth2ClientAuthMethod clientAuthMethod) {

		return ClientAuthenticationMethod.valueOf(clientAuthMethod.name().toLowerCase());
	}

	private static AuthorizationGrantType translateGrantType(Oauth2AuthorizationGrantType authGrantType) {
		switch (authGrantType) {
		case AUTHORIZATION_CODE:
			return AuthorizationGrantType.AUTHORIZATION_CODE;
		case CLIENT_CREDENTIALS:
			return AuthorizationGrantType.CLIENT_CREDENTIALS;
		case DEVICE_CODE:
			return AuthorizationGrantType.DEVICE_CODE;
		case JWT_BEARER:
			return AuthorizationGrantType.JWT_BEARER;
		case PASSWORD:
			return AuthorizationGrantType.PASSWORD;
		case REFRESH_TOKEN:
			return AuthorizationGrantType.REFRESH_TOKEN;
		case TOKEN_EXCHANGE:
			return AuthorizationGrantType.TOKEN_EXCHANGE;
		}
		return null;
	}
}