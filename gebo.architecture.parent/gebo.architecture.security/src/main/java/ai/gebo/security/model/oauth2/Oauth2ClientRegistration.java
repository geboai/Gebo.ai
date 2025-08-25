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
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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
	private final static List<String> STANDARD_AUTHENTICATION_SCOPES = List.of("openid", "profile", "email");
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
		
		builder.authorizationUri(handlerPlaceHolders(
				registration.getRuntimeConfiguration().getProviderConfig().getAuthorizationUri(), registration));
		builder.tokenUri(handlerPlaceHolders(registration.getRuntimeConfiguration().getProviderConfig().getTokenUri(),
				registration));
		builder.userInfoUri(handlerPlaceHolders(
				registration.getRuntimeConfiguration().getProviderConfig().getUserInfoUri(), registration));
		builder.issuerUri(handlerPlaceHolders(registration.getRuntimeConfiguration().getProviderConfig().getIssuerUri(),
				registration));
		builder.jwkSetUri(handlerPlaceHolders(registration.getRuntimeConfiguration().getProviderConfig().getJwkSetUri(),
				registration));
		List<String> scopes = registration.getRuntimeConfiguration().getClient() != null
				? registration.getRuntimeConfiguration().getClient().getScopes()
				: registration.getClientRegistration().getScopes();

		if (registration.getRuntimeConfiguration().getConfigurationType() != null && registration
				.getRuntimeConfiguration().getConfigurationType() == (Oauth2ConfigurationType.AUTHENTICATION)
				&& scopes == null) {
			scopes = STANDARD_AUTHENTICATION_SCOPES;
		}
		builder.scope(scopes);
		if (registration.getRuntimeConfiguration().getProviderConfig().getUserNameAttribute() != null)
			builder.userNameAttributeName(
					registration.getRuntimeConfiguration().getProviderConfig().getUserNameAttribute());
		Map<String, Object> configurationMetaData = new HashMap<String, Object>();
		if (registration.getClientRegistration().getCustomAttributes() != null) {
			configurationMetaData.putAll(registration.getClientRegistration().getCustomAttributes());
		}
		builder.providerConfigurationMetadata(configurationMetaData);
		builder.redirectUri("{baseUrl}/login/oauth2/code/{registrationId}");
		// Build and return the ClientRegistration.
		return builder.build();
	}

	private static String handlerPlaceHolders(String variable, Oauth2ClientRegistration registration) {
		String out = variable;
		if (out != null && registration.getClientRegistration() != null
				&& registration.getClientRegistration().getCustomAttributes() != null
				&& !registration.getClientRegistration().getCustomAttributes().isEmpty()) {
			Set<Entry<String, String>> names = registration.getClientRegistration().getCustomAttributes().entrySet();
			for (Entry<String, String> entry : names) {
				out = out.replace("{" + entry.getKey() + "}", entry.getValue());
			}
		}
		return out;
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