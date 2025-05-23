/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.oauth2.client.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.oauth2.client.model.ClientRegistrationSecretWrapper;
import ai.gebo.architecture.patterns.IGDynamicConfigurationSource;

/**
 * Gebo.ai comment agent
 *
 * A service implementation that sources dynamic configurations for 
 * {@code ClientRegistrationSecretWrapper} objects.
 */
@Service
class ClientRegistrationSecretWrapperDynamicSource
		implements IGDynamicConfigurationSource<ClientRegistrationSecretWrapper> {

	// Injects the repository that manages ClientRegistrationSecretWrapper entities.
	@Autowired
	ClientRegistrationSecretWrapperRepository repo;

	/**
	 * Retrieves all configurations of {@code ClientRegistrationSecretWrapper} from the repository.
	 *
	 * @return a list of ClientRegistrationSecretWrapper objects.
	 */
	@Override
	public List<ClientRegistrationSecretWrapper> getConfigurations() {
		return repo.findAll();
	}

	/**
	 * Finds a specific {@code ClientRegistrationSecretWrapper} by its unique code.
	 *
	 * @param code the unique identifier for a ClientRegistrationSecretWrapper.
	 * @return the ClientRegistrationSecretWrapper if found, otherwise returns null.
	 */
	@Override
	public ClientRegistrationSecretWrapper findByCode(String code) {
		Optional<ClientRegistrationSecretWrapper> opt = repo.findById(code);
		if (opt.isPresent())
			return opt.get();
		else
			return null;
	}
}