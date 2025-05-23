/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.ragsystem.vectorstores.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.vectorstores.IGVectorStoreConfigurationProvider;
import ai.gebo.llms.abstraction.layer.vectorstores.model.VectorStoreRuntimeConfiguration;

/**
 * AI generated comments
 * 
 * Implementation of the IGVectorStoreConfigurationProvider interface.
 * This service provides vector store configuration by retrieving the active configuration
 * from the GeboVectorStoreConfigurationService and converting it to the standard format.
 */
@Service
public class GVectorStoreConfigurationProviderImpl implements IGVectorStoreConfigurationProvider {
	private final static Logger LOGGER = LoggerFactory.getLogger(GVectorStoreConfigurationProviderImpl.class);

	@Autowired
	GeboVectorStoreConfigurationService service;

	/**
	 * Retrieves the current vector store configuration.
	 * Fetches the active configuration from the service, converts it to the standard format,
	 * and logs information about the configured vector store product.
	 * 
	 * @return The current vector store runtime configuration
	 * @throws LLMConfigException If there is an error retrieving or processing the configuration
	 */
	@Override
	public VectorStoreRuntimeConfiguration get() throws LLMConfigException {
		// Get actual configuration and convert it to standard format
		VectorStoreRuntimeConfiguration configuration = service.mongo2Standart(service.getActualConfiguration());
		LOGGER.info("/***************************************************************************\n");
		LOGGER.info("*Providing Vector Store configuration for:"
				+ (configuration.getProduct() != null ? configuration.getProduct().name() : " UNKNOWN ") + "*\n");
		LOGGER.info("***************************************************************************/\n");
		return configuration;
	}

}