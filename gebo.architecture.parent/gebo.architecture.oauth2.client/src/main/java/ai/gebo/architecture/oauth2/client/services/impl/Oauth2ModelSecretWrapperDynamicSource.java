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

import ai.gebo.architecture.oauth2.client.model.Oauth2ModelSecretWrapper;
import ai.gebo.architecture.oauth2.client.model.Oauth2ModelSecretWrapperId;
import ai.gebo.architecture.patterns.IGDynamicConfigurationSource;

/**
 * Service class implementing the IGDynamicConfigurationSource interface 
 * for Oauth2ModelSecretWrapper objects.
 * Gebo.ai comment agent
 */
@Service
class Oauth2ModelSecretWrapperDynamicSource implements IGDynamicConfigurationSource<Oauth2ModelSecretWrapper> {

    // Repository for accessing Oauth2ModelSecretWrapper data.
	@Autowired
	Oauth2ModelSecretWrapperRepository repo;

	/**
	 * Retrieves all configuration entries.
	 *
	 * @return a list of Oauth2ModelSecretWrapper objects.
	 */
	@Override
	public List<Oauth2ModelSecretWrapper> getConfigurations() {
        // Fetches all configurations from the repository.
		return repo.findAll();
	}

	/**
	 * Finds a specific Oauth2ModelSecretWrapper by its code.
	 *
	 * @param code the code identifying the Oauth2ModelSecretWrapper.
	 * @return the Oauth2ModelSecretWrapper object if found, otherwise null.
	 */
	@Override
	public Oauth2ModelSecretWrapper findByCode(String code) {
		// Splits the code to create an Oauth2ModelSecretWrapperId.
		Oauth2ModelSecretWrapperId id = GOauth2ModelSecretWrapperRuntimeDaoImpl.splitCode(code);
		// Attempts to find the entity in the repository by its ID.
		Optional<Oauth2ModelSecretWrapper> optional = repo.findById(id);
		return optional.isPresent() ? optional.get() : null;
	}

	/**
	 * Saves a given Oauth2ModelSecretWrapper to the repository.
	 *
	 * @param wrapper the Oauth2ModelSecretWrapper to save.
	 */
	public void save(Oauth2ModelSecretWrapper wrapper) {
		repo.save(wrapper);
	}

	/**
	 * Deletes a specific Oauth2ModelSecretWrapper by its ID.
	 *
	 * @param id the ID of the Oauth2ModelSecretWrapper to delete.
	 */
	public void delete(Oauth2ModelSecretWrapperId id) {
		repo.deleteById(id);
	}
}