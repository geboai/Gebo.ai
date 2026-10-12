/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.system.ingestion.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.patterns.GAbstractRuntimeConfigurationDao;
import ai.gebo.system.ingestion.IGIngestionHandlerConfigDao;
import ai.gebo.system.ingestion.config.GeboContentReadingConfig;
import ai.gebo.system.ingestion.model.IngestionHandlerConfig;

/**
 * AI generated comments
 * 
 * Implementation of the IGIngestionHandlerConfigDao interface that provides
 * data access operations for IngestionHandlerConfig objects.
 * This service extends the abstract runtime configuration DAO to handle
 * ingestion handler configurations.
 */
@Service
public class GIngestionHandlerConfigDaoImpl extends GAbstractRuntimeConfigurationDao<IngestionHandlerConfig> implements IGIngestionHandlerConfigDao{

	/**
	 * Constructor that initializes the DAO with handler configurations.
	 * 
	 * @param config The GeboContentReadingConfig containing handler configurations
	 */
	public GIngestionHandlerConfigDaoImpl(@Autowired GeboContentReadingConfig config) {
		super(config.getHandlers(), null);
	}

	/**
	 * Finds an IngestionHandlerConfig by its code identifier.
	 * 
	 * @param code The unique identifier code to search for
	 * @return The matching IngestionHandlerConfig or null if not found
	 */
	@Override
	public IngestionHandlerConfig findByCode(String code) {
		// Use the predicate to find the config with the matching ID
		return this.findByPredicate(x -> {
			return x.getId() != null && x.getId().equals(code);
		});
	}
}