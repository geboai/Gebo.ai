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

import org.springframework.stereotype.Service;

import ai.gebo.architecture.oauth2.client.model.Oauth2ModelSecretWrapper;
import ai.gebo.architecture.oauth2.client.model.Oauth2ModelSecretWrapperId;
import ai.gebo.architecture.patterns.GAbstractRuntimeConfigurationDao;

/**
 * Gebo.ai comment agent
 * Service implementation of the Oauth2ModelSecretWrapper runtime DAO.
 * This class is responsible for handling operations related to Oauth2ModelSecretWrapper,
 * utilizing a dynamic data source.
 */
@Service
public class GOauth2ModelSecretWrapperRuntimeDaoImpl extends GAbstractRuntimeConfigurationDao<Oauth2ModelSecretWrapper>
		implements IGOauth2ModelSecretWrapperRuntimeDao {
	
	/**
	 * Constructor for GOauth2ModelSecretWrapperRuntimeDaoImpl.
	 * 
	 * @param dynamic The dynamic source used for managing Oauth2ModelSecretWrapper entities.
	 */
	public GOauth2ModelSecretWrapperRuntimeDaoImpl(Oauth2ModelSecretWrapperDynamicSource dynamic) {
		super(List.of(), dynamic);
	}

	/**
	 * Splits a given code into an Oauth2ModelSecretWrapperId object.
	 * 
	 * @param code The code to split.
	 * @return The Oauth2ModelSecretWrapperId representation of the code.
	 */
	static Oauth2ModelSecretWrapperId splitCode(String code) {
		return null;
	}

	/**
	 * Finds an Oauth2ModelSecretWrapper by its code.
	 * 
	 * @param code The code of the Oauth2ModelSecretWrapper to find.
	 * @return The Oauth2ModelSecretWrapper associated with the given code.
	 *         If not found in static configurations, it tries the dynamic source.
	 */
	@Override
	public Oauth2ModelSecretWrapper findByCode(String code) {
		Oauth2ModelSecretWrapperId id = splitCode(code);
		Optional<Oauth2ModelSecretWrapper> first = staticConfigs.stream().filter(x -> {
			return x.getId() != null && x.getId().getClientRegistrationId().equals(id.getClientRegistrationId())
					&& x.getId().getPrincipalName().equals(id.getPrincipalName());
		}).findFirst();
		if (first.isPresent())
			return first.get();

		return dynamic.findByCode(code);
	}

	/**
	 * Saves a given Oauth2ModelSecretWrapper entity.
	 * 
	 * @param wrapper The Oauth2ModelSecretWrapper entity to save.
	 */
	@Override
	public void save(Oauth2ModelSecretWrapper wrapper) {
		Oauth2ModelSecretWrapperDynamicSource handler = (Oauth2ModelSecretWrapperDynamicSource) dynamic;
		handler.save(wrapper);
	}

	/**
	 * Removes an Oauth2ModelSecretWrapper entity by its code.
	 * 
	 * @param code The code of the Oauth2ModelSecretWrapper to remove.
	 */
	@Override
	public void remove(String code) {
		Oauth2ModelSecretWrapperId id = splitCode(code);
		Oauth2ModelSecretWrapperDynamicSource handler = (Oauth2ModelSecretWrapperDynamicSource) dynamic;
		handler.delete(id);
	}

}