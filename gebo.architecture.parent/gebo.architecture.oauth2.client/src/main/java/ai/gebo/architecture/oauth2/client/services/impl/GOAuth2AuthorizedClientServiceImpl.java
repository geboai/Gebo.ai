/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.oauth2.client.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.stereotype.Service;

/**
 * Gebo.ai comment agent
 * 
 * This service implementation class provides methods to manage OAuth2AuthorizedClient instances.
 * It interacts with SecretOauth2DataHandler to load, save, and remove authorized clients
 * associated with OAuth2 authentication.
 */
@Service
public class GOAuth2AuthorizedClientServiceImpl implements OAuth2AuthorizedClientService {

	@Autowired
	SecretOauth2DataHandler handler; // Handles OAuth2 client operations

	/**
	 * Loads an authorized OAuth2 client using the provided client registration ID and principal name.
	 *
	 * @param clientRegistrationId the registration ID of the client
	 * @param principalName the name of the principal associated with the client
	 * @return the authorized OAuth2 client
	 */
	@Override
	public OAuth2AuthorizedClient loadAuthorizedClient(String clientRegistrationId, String principalName) {
		return handler.loadAuthorizedClient(clientRegistrationId, principalName);
	}

	/**
	 * Saves the authorized OAuth2 client based on the authentication principal.
	 *
	 * @param authorizedClient the authorized OAuth2 client to save
	 * @param principal the authentication principal
	 */
	@Override
	public void saveAuthorizedClient(OAuth2AuthorizedClient authorizedClient, Authentication principal) {
		handler.saveAuthorizedClient(authorizedClient, principal);
	}

	/**
	 * Removes an authorized OAuth2 client using the client registration ID and principal name.
	 *
	 * @param clientRegistrationId the registration ID of the client
	 * @param principalName the name of the principal associated with the client
	 */
	@Override
	public void removeAuthorizedClient(String clientRegistrationId, String principalName) {
		handler.removeAuthorizedClient(clientRegistrationId, principalName);
	}

}