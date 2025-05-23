/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.oauth2.client.services.impl;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.oauth2.client.model.ClientRegistrationSecretWrapper;
import ai.gebo.architecture.oauth2.client.model.config.GeboOauth2ClientConfig;
import ai.gebo.architecture.patterns.GAbstractRuntimeConfigurationDao;
import ai.gebo.architecture.patterns.IGDynamicConfigurationSource;

/**
 * Gebo.ai comment agent
 * 
 * A service class that provides runtime data access operations for
 * {@link ClientRegistrationSecretWrapper} objects.
 */
@Service
class GClientRegistrationSecretWrapperRuntimeDaoImpl
		extends GAbstractRuntimeConfigurationDao<ClientRegistrationSecretWrapper>
		implements IGClientRegistrationSecretWrapperRuntimeDao {

	/**
	 * Constructs a new instance of {@code GClientRegistrationSecretWrapperRuntimeDaoImpl}.
	 * 
	 * @param oauth2ClientConfig the OAuth2 client configuration for retrieving encrypted client registrations.
	 * @param dynamic the dynamic configuration source for {@code ClientRegistrationSecretWrapper} objects.
	 */
	public GClientRegistrationSecretWrapperRuntimeDaoImpl(GeboOauth2ClientConfig oauth2ClientConfig,
			IGDynamicConfigurationSource<ClientRegistrationSecretWrapper> dynamic) {
		// Initialize the superclass with the encrypted client registrations and the dynamic configuration source.
		super(oauth2ClientConfig != null ? oauth2ClientConfig.getCryptedClientRegistrations() : null, dynamic);
	}

	/**
	 * Finds a {@code ClientRegistrationSecretWrapper} by its client registration code.
	 * 
	 * @param code the client registration code.
	 * @return the {@code ClientRegistrationSecretWrapper} associated with the specified code, or {@code null} if not found.
	 */
	@Override
	public ClientRegistrationSecretWrapper findByCode(String code) {
		// Use a predicate to locate the ClientRegistrationSecretWrapper with the matching code.
		return findByPredicate(x -> {
			return x.getClientRegistrationId().equals(code);
		});
	}

}