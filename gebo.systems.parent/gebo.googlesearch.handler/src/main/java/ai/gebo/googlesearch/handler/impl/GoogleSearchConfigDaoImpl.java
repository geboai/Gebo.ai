/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.googlesearch.handler.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.patterns.IGRuntimeConfigurationDao;
import ai.gebo.googlesearch.handler.model.GGoogleSearchApiCredentials;
import ai.gebo.googlesearch.handler.model.GoogleSearchConfig;
import ai.gebo.googlesearch.handler.repository.GGoogleSearchApiCredentialsRepository;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;

/**
 * AI generated comments
 * Implementation of the IGRuntimeConfigurationDao interface for Google Search configurations.
 * This service handles the retrieval of Google Search API credentials and converts them
 * into usable configuration objects.
 */
@Service
public class GoogleSearchConfigDaoImpl implements IGRuntimeConfigurationDao<GoogleSearchConfig> {
	/** Logger for this class */
	static Logger LOGGER = LoggerFactory.getLogger(GoogleSearchConfigDaoImpl.class);
	
	/** Repository for accessing Google Search API credentials */
	@Autowired
	GGoogleSearchApiCredentialsRepository repository;
	
	/** Service for accessing secrets such as API keys */
	@Autowired
	IGeboSecretsAccessService secretService;

	/**
	 * Default constructor for GoogleSearchConfigDaoImpl
	 */
	public GoogleSearchConfigDaoImpl() {

	}

	/**
	 * Retrieves all Google Search configurations from the repository.
	 * For each credential entry, it fetches the associated secret content
	 * and builds a GoogleSearchConfig object.
	 * 
	 * @return List of GoogleSearchConfig objects with engine IDs and API keys
	 */
	@Override
	public List<GoogleSearchConfig> getConfigurations() {
		List<GGoogleSearchApiCredentials> list = repository.findAll();

		List<GoogleSearchConfig> configs = new ArrayList<GoogleSearchConfig>();
		try {
			for (GGoogleSearchApiCredentials x : list) {
				String engineId = x.getCustomSearchEngineId();
				AbstractGeboSecretContent data = secretService.getSecretContentById(x.getSecretCode());
				GoogleSearchConfig config = new GoogleSearchConfig();
				config.setCustomSearchEngineId(engineId);
				config.setApiKey(((GeboTokenContent) data).getToken());
				config.setEnabled(true);
				configs.add(config);
			}
		} catch (Throwable th) {
			LOGGER.error("Error in reading google search config", th);
		}
		return configs;
	}

	/**
	 * Finds a GoogleSearchConfig by its code.
	 * Currently not implemented, always returns null.
	 * 
	 * @param code The code to search for
	 * @return Always returns null in the current implementation
	 */
	@Override
	public GoogleSearchConfig findByCode(String code) {

		return null;
	}

}