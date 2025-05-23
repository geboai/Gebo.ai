/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ai.gebo.architecture.patterns.GAbstractRuntimeConfigurationDao;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.llms.abstraction.layer.model.GBaseTranscriptModelConfig;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableTranscriptModel;
import ai.gebo.llms.abstraction.layer.services.IGTranscriptModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGTranscriptModelConfigurationSupportServiceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.IGTranscriptModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;

/**
 * AI generated comments
 *
 * Implementation of runtime configuration DAO for transcript models, responsible
 * for managing lifecycle and configuration of transcript models within the application.
 */
@Component
@Scope("singleton")
public class GTranscriptModelRuntimeConfigurationDaoimpl
		extends GAbstractRuntimeConfigurationDao<IGConfigurableTranscriptModel>
		implements IGTranscriptModelRuntimeConfigurationDao, ApplicationListener<ContextRefreshedEvent> {
	
	// Logger for logging runtime information and errors
	static Logger LOGGER = LoggerFactory.getLogger(GEmbeddingModelRuntimeConfigurationDaoImpl.class);
	
	// JSON object mapper for processing configurations
	static ObjectMapper mapper = new ObjectMapper();
	
	// Autowired repository pattern for supporting model configuration operations
	@Autowired
	IGTranscriptModelConfigurationSupportServiceRepositoryPattern supportRepoPattern;

	// Autowired manager for handling persistence of objects
	@Autowired
	IGPersistentObjectManager persistentObjectManager;

	/**
	 * Default constructor initializing the configuration with empty static models list.
	 */
	public GTranscriptModelRuntimeConfigurationDaoimpl() {
		super(new ArrayList<IGConfigurableTranscriptModel>(), null);
	}

	/**
	 * Adds a configurable transcript model to the static configuration list.
	 * @param element the model to be added
	 */
	@Override
	public void add(IGConfigurableTranscriptModel element) {
		this.staticConfigs.add(element);
	}

	/**
	 * Adds a new runtime configuration for a transcript model based on provided configuration.
	 * Throws LLMConfigException if the model type cannot be identified.
	 * 
	 * @param config the base configuration for the transcript model
	 * @throws LLMConfigException if model configuration is invalid or unsupported
	 */
	@Override
	public void addRuntimeByConfig(GBaseTranscriptModelConfig config) throws LLMConfigException {
		IGTranscriptModelConfigurationSupportService handler = supportRepoPattern.findImplementation(x -> {
			return x.getType().getCode().equals(config.getModelTypeCode());
		});
		if (handler == null) {
			LOGGER.error("Received in configuration an embedding model with type=>" + config.getModelTypeCode()
					+ " that is not found");
			throw new LLMConfigException("Cannot find embedding model with type=>" + config.getModelTypeCode());
		}
		try {
			LOGGER.info("Initializing embedding model with configuration:" + mapper.writeValueAsString(config));
		} catch (JsonProcessingException e) {
			// No action needed for this catch
		}
		IGConfigurableTranscriptModel model = handler.create(config);
		LOGGER.info("Initialized chatModel successfully");
		this.staticConfigs.add(model);
	}

	/**
	 * Deletes a configuration by code. Currently not implemented.
	 * 
	 * @param code the code of the configuration to delete
	 * @throws LLMConfigException 
	 */
	@Override
	public void deleteByCode(String code) throws LLMConfigException {
		// Method implementation pending
	}

	/**
	 * Finds a configurable transcript model by its code.
	 * 
	 * @param code the code identifying the transcript model
	 * @return the transcript model with the specified code, or null if not found
	 */
	@Override
	public IGConfigurableTranscriptModel findByCode(String code) {
		return findByPredicate(x -> x.getCode() != null && x.getCode().equals(code));
	}

	/**
	 * Handles ContextRefreshedEvent, initializing transcript models dynamically
	 * based on stored configurations.
	 * 
	 * @param event the application event signaling context refresh
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		LOGGER.info("Begin initalizing transcript  models dinamically");
		try {
			List<GBaseTranscriptModelConfig> configs = persistentObjectManager
					.findAllExtendingType(GBaseTranscriptModelConfig.class);
			for (GBaseTranscriptModelConfig config : configs) {
				addRuntimeByConfig(config);
			}
		} catch (GeboPersistenceException | LLMConfigException e) {
			String msg = "FATAL EMBEDDING MODELS INITIALIZATION EXCEPTION";
			LOGGER.error(msg, e);
			throw new RuntimeException(msg, e);
		}
		LOGGER.info("End initalizing transcript  models dinamically");
	}
}