/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.patterns.GAbstractRuntimeConfigurationDao;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.services.IGChatModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGChatModelConfigurationSupportServiceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.model.GChatProfileConfiguration;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatProfileChatModel;
import ai.gebo.llms.chat.abstraction.layer.services.IGRuntimeChatProfileChatModelDao;
import ai.gebo.model.base.GObjectRef;

@Service
/**
 * Implementation of the IGRuntimeChatProfileChatModelDao interface that deals with 
 * the runtime configurations of chat profiles.
 * 
 * AI generated comments
 */
public class GRuntimeChatProfileChatModelDaoImpl extends GAbstractRuntimeConfigurationDao<IGChatProfileChatModel>
		implements IGRuntimeChatProfileChatModelDao {
    
    // Dependency injection of repository pattern for chat models
	@Autowired
	IGChatModelConfigurationSupportServiceRepositoryPattern chatModelsRepositoryPattern;
    
    // Dependency injection of runtime configuration DAO for chat models
	@Autowired
	IGChatModelRuntimeConfigurationDao chatModelsDao;
    
    // Dependency injection of persistent object manager
	@Autowired
	IGPersistentObjectManager persistentObjectManager;

    /**
     * Constructor that initializes the superclass with an empty list and null configuration.
     */
	public GRuntimeChatProfileChatModelDaoImpl() {
		super(new ArrayList(), null);
	}

    /**
     * Finds an IGChatProfileChatModel by its code.
     * 
     * @param code The code of the chat profile to find.
     * @return The found IGChatProfileChatModel or null if not found.
     */
	@Override
	public IGChatProfileChatModel findByCode(String code) {
		return findByPredicate(x -> {
			return code != null && x.getChatProfile() != null && x.getChatProfile().getCode().equals(code);
		});
	}

    /**
     * Gets or creates a chat model from a given configuration.
     * 
     * @param configuration The configuration to create the chat model from.
     * @return The created or retrieved IGChatProfileChatModel.
     * @throws LLMConfigException If there is an issue with the configuration.
     */
	@Override
	public IGChatProfileChatModel getChatModel(GChatProfileConfiguration configuration)
			throws LLMConfigException {
		if (configuration.getCode() != null) {
			IGChatProfileChatModel model = findByCode(configuration.getCode());
		}
		return this.createAndAdd(configuration);
	}

    /**
     * Creates and adds a new chat profile chat model.
     * 
     * @param configuration The configuration used to create the chat model.
     * @return The newly created IGChatProfileChatModel.
     * @throws LLMConfigException If there is a configuration issue or persistence error.
     */
	private IGChatProfileChatModel createAndAdd(GChatProfileConfiguration configuration) throws LLMConfigException {
		GObjectRef<GBaseChatModelConfig> modelConfiguration = configuration.getChatModelReference();
		if (modelConfiguration == null) {
			IGConfigurableChatModel defaultHandler = chatModelsDao.defaultHandler();
			if (defaultHandler != null) {
				modelConfiguration = GObjectRef.of((GBaseChatModelConfig) defaultHandler.getConfig());
			}
		}
		if (modelConfiguration == null)
			throw new LLMConfigException("No default LLM configured for the system");
        
		try {
            // Attempt to find and configure a new chat model
			GBaseChatModelConfig newConfig = persistentObjectManager.findByReference(modelConfiguration,
					GBaseChatModelConfig.class);
			newConfig.setEnabledFunctions(configuration.getEnabledFunctions());
			IGChatModelConfigurationSupportService handler = chatModelsRepositoryPattern
					.findByCode(newConfig.getModelTypeCode());
			GChatProfileChatModel chatProfileChatModel = new GChatProfileChatModel(configuration,
					handler.create(newConfig));
			this.staticConfigs.add(chatProfileChatModel);
			return chatProfileChatModel;
		} catch (GeboPersistenceException e) {
            // Throw an exception if there is a persistence problem
			throw new LLMConfigException("Problem using mongo layer", e);
		} finally {
            // Finally block left intentionally empty
		}
	}

}