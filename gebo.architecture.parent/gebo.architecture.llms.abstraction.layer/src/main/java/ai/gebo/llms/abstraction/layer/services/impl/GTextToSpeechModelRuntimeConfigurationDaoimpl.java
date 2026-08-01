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
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.llms.abstraction.layer.cluster.GAbstractClusteredModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.cluster.GLlmModelClusterCategory;
import ai.gebo.llms.abstraction.layer.model.GBaseTextToSpeachModelConfig;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableTextToSpeechModel;
import ai.gebo.llms.abstraction.layer.services.IGTextToSpeechModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGTextToSpeechModelConfigurationSupportServiceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.IGTextToSpeechModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;

@Component
@Scope("singleton")
public class GTextToSpeechModelRuntimeConfigurationDaoimpl
		extends GAbstractClusteredModelRuntimeConfigurationDao<IGConfigurableTextToSpeechModel, GBaseTextToSpeachModelConfig>
		implements IGTextToSpeechModelRuntimeConfigurationDao {

	@Override
	protected GLlmModelClusterCategory getClusterCategory() {
		return GLlmModelClusterCategory.TEXT_TO_SPEECH;
	}

	/** 
     * Logger for this class
     * AI generated comments
     */
	static Logger LOGGER = LoggerFactory.getLogger(GTextToSpeechModelRuntimeConfigurationDaoimpl.class);
	
	/** 
     * ObjectMapper to handle JSON processing
     */
	static ObjectMapper mapper = new ObjectMapper();
	
	/**
     * Repository pattern for finding model configuration support services
     */
	@Autowired
	IGTextToSpeechModelConfigurationSupportServiceRepositoryPattern supportRepoPattern;

	/**
     * Manager for persistent objects
     */
	@Autowired
	IGPersistentObjectManager persistentObjectManager;

	/** 
     * Constructor initializing with empty list and null
     */
	public GTextToSpeechModelRuntimeConfigurationDaoimpl() {
		super(new ArrayList<IGConfigurableTextToSpeechModel>(), null);

	}

	/** 
     * Adds a configuration model to the static configurations
     * @param element The model to be added
     */
	@Override
	public void add(IGConfigurableTextToSpeechModel element) {
		this.staticConfigs.add(element);
	}

	/** 
     * Adds a runtime model by configuration
     * @param config Configuration for the text-to-speech model
     * @throws LLMConfigException If a proper model cannot be found or initialized
     */
	@Override
	public void addRuntimeByConfig(GBaseTextToSpeachModelConfig config) throws LLMConfigException {
		IGTextToSpeechModelConfigurationSupportService handler = supportRepoPattern.findImplementation(x -> {
			return x.getType().getCode().equals(config.getModelTypeCode());
		});
		if (handler == null) {
			LOGGER.error("Received in configuration a text to speech model with type=>" + config.getModelTypeCode()
					+ " that is not found");
			throw new LLMConfigException("Cannot find text to speech model with type=>" + config.getModelTypeCode());
		}
		try {
			LOGGER.info("Initializing text to speech model with configuration:" + mapper.writeValueAsString(config));
		} catch (JacksonException e) {
			// Handle JSON processing exception here
		}
		IGConfigurableTextToSpeechModel ttsModel = handler.create(config);
		LOGGER.info("Initialized chatModel successfully");
		this.staticConfigs.add(ttsModel);
	}

	/** 
     * Delete a model by its code
     * @param code The code of the model to be deleted
     * @throws LLMConfigException If an error occurs during deletion
     */
	@Override
	public void deleteByCode(String code) throws LLMConfigException {
		// Implementation needed for deleting by code
	}

	/** 
     * Finds a model by its code
     * @param code The code of the model to be found
     * @return Returns the model if found, null otherwise
     */
	@Override
	public IGConfigurableTextToSpeechModel findByCode(String code) {
		return findByPredicate(x -> x.getCode() != null && x.getCode().equals(code));
	}

	/**
     * Initializes text-to-speech models dynamically. Invoked once this DAO's
     * own application context finishes refreshing.
     */
	@Override
	protected void initializeRuntimeModels() {
		LOGGER.info("Begin initalizing text to speech  models dinamically");
		try {
			List<GBaseTextToSpeachModelConfig> configs = persistentObjectManager
					.findAllExtendingType(GBaseTextToSpeachModelConfig.class);
			for (GBaseTextToSpeachModelConfig config : configs) {
				try {
					addRuntimeByConfig(config);
				} catch (Throwable e) {
					// A single model that cannot be allocated (revoked key, provider down, stale
					// configuration) must never keep the whole application from starting: report
					// it and carry on with the remaining models.
					LOGGER.error("Cannot initialize the text to speech model with code=>" + config.getCode(), e);
				}
			}
		} catch (GeboPersistenceException e) {
			LOGGER.error("Cannot read the text to speech models configuration", e);
		}
		LOGGER.info("End initalizing  text to speech models dinamically");
	}

}