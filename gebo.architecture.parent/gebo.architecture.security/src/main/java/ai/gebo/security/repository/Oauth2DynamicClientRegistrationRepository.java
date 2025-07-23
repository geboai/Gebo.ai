/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
package ai.gebo.security.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

import ai.gebo.security.model.oauth2.GeboOauth2Exception;
import ai.gebo.security.model.oauth2.Oauth2ClientConfig;
import ai.gebo.security.model.oauth2.Oauth2RuntimeConfiguration;
import ai.gebo.security.services.IGOauth2ConfigurationService;
import lombok.AllArgsConstructor;

/**
 * AI generated comments This class acts as a dynamic client registration
 * repository for OAuth2 clients. It implements the ClientRegistrationRepository
 * interface provided by Spring Security. It is annotated with @Service to
 * indicate that it's a service component in the Spring context.
 */
@AllArgsConstructor
public class Oauth2DynamicClientRegistrationRepository implements IOauth2DynamicClientRegistrationRepository {

	// Logger instance to log actions or events pertaining to this class.
	private final static Logger LOGGER = LoggerFactory.getLogger(Oauth2DynamicClientRegistrationRepository.class);

	// Injects an implementation of the IGOauth2ConfigurationService to manage
	// OAuth2 client configuration.

	final IGOauth2ConfigurationService oauth2ConfigurationService;

	/**
	 * Finds and returns a ClientRegistration by its registration ID.
	 * 
	 * @param registrationId the registration ID of the client to find.
	 * @return the ClientRegistration if found.
	 * @throws RuntimeException if there is an issue accessing the registration.
	 */
	@Override
	public ClientRegistration findByRegistrationId(String registrationId) {

		try {
			// Uses the injected service to find the client registration.
			return oauth2ConfigurationService.findClientRegistrationByRegistrationId(registrationId);
		} catch (GeboOauth2Exception e) {
			// Logs an error and throws a runtime exception if an error occurs.
			String message = "exception accessing client registration => " + registrationId;
			LOGGER.error(message, e);
			throw new RuntimeException(message, e);
		}
	}

	@Override
	public Oauth2ClientConfig findOauth2ClientConfigById(String registrationId) {
		ClientRegistration data = this.findByRegistrationId(registrationId);
		Oauth2ClientConfig outData = new Oauth2ClientConfig();
		outData.setClientId(data.getClientId());
		outData.setIssuer(data.getProviderDetails().getIssuerUri());
		outData.setRegistrationId(registrationId);
		outData.setDescription(data.getClientName());
		return outData;
	}

}