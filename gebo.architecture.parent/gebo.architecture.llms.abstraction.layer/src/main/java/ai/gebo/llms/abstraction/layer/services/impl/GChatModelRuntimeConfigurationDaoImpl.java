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

import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.patterns.GAbstractRuntimeConfigurationDao;
import ai.gebo.architecture.patterns.model.GModuleUseInfo;
import ai.gebo.architecture.patterns.model.GModuleUseInfo.MInfoType;
import ai.gebo.architecture.patterns.model.GModuleUseInfo.ModuleType;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseModelChoice;
import ai.gebo.llms.abstraction.layer.services.IGChatModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGChatModelConfigurationSupportServiceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;

/**
 * AI generated comments
 * Implementation of the IGChatModelRuntimeConfigurationDao interface.
 * Manages runtime configurations of chat models.
 * Handles dynamic initialization of chat models upon application context refresh.
 */
@Component
@Scope("singleton")
public class GChatModelRuntimeConfigurationDaoImpl extends GAbstractRuntimeConfigurationDao<IGConfigurableChatModel>
		implements IGChatModelRuntimeConfigurationDao, ApplicationListener<ContextRefreshedEvent> {
	
	// Logger instance for this class
	static Logger LOGGER = LoggerFactory.getLogger(GChatModelRuntimeConfigurationDaoImpl.class);
	// ObjectMapper instance for JSON processing
	static ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	IGChatModelConfigurationSupportServiceRepositoryPattern supportRepoPattern;
	
	@Autowired
	IGPersistentObjectManager persistentObjectManager;

	/**
	 * Default constructor initializing the base class with an empty list.
	 */
	public GChatModelRuntimeConfigurationDaoImpl() {
		super(new ArrayList(), null);
	}

	/**
	 * Find a chat model configuration by its unique code.
	 * 
	 * @param code The unique code identifying the chat model configuration.
	 * @return The chat model configuration associated with the provided code.
	 */
	@Override
	public IGConfigurableChatModel findByCode(String code) {

		return this.findByPredicate(x -> {
			return x.getCode() != null && code != null && x.getCode().equals(code);
		});
	}

	/**
	 * Reacts to the ContextRefreshedEvent by initializing chat models dynamically.
	 * 
	 * @param event The context refreshed event from the Spring framework.
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		LOGGER.info("Begin initializing chat models dinamically");
		try {
			// Retrieve all configurations extending GBaseChatModelConfig
			List<GBaseChatModelConfig> configs = persistentObjectManager
					.findAllExtendingType(GBaseChatModelConfig.class);
			for (GBaseChatModelConfig config : configs) {
				this.addRuntimeByConfig(config);
			}
		} catch (GeboPersistenceException | LLMConfigException e) {
			String msg = "FATAL CHAT MODELS INITIALIZATION EXCEPTION";
			LOGGER.error(msg, e);
			throw new RuntimeException(msg, e);
		}

		LOGGER.info("End initializing chat models dinamically");
	}

	/**
	 * Add a chat model to the static configurations list.
	 * 
	 * @param element The chat model to be added.
	 */
	@Override
	public void add(IGConfigurableChatModel element) {
		this.staticConfigs.add(element);
	}

	/**
	 * Add a chat model to runtime configurations based on the provided configuration.
	 * If the handler is not found, an exception is thrown.
	 * 
	 * @param config The configuration of the chat model.
	 * @throws LLMConfigException If the chat model configuration fails.
	 */
	@Override
	public void addRuntimeByConfig(GBaseChatModelConfig config) throws LLMConfigException {
		IGChatModelConfigurationSupportService handler = supportRepoPattern.findImplementation(x -> {
			return x.getType().getCode().equals(config.getModelTypeCode());
		});
		if (handler == null) {
			LOGGER.error("Received in configuration a chat model with type=>" + config.getModelTypeCode()
					+ " that is not found");
			throw new LLMConfigException("Cannot find handler for code=>" + config.getModelTypeCode());
		} else {
			try {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Initializing chatModel with configuration:" + mapper.writeValueAsString(config));
				}
			} catch (JsonProcessingException e) {
				// Log parsing exception if necessary
			}
			IGConfigurableChatModel chatModel = handler.create(config);
			LOGGER.info("Initialized chatModel successfully");
			this.staticConfigs.add(chatModel);
		}
	}

	/**
	 * Delete a chat model configuration by its unique code.
	 * 
	 * @param code The unique code identifying the chat model configuration to be deleted.
	 * @throws LLMConfigException If the chat model configuration cannot be found.
	 */
	@Override
	public void deleteByCode(String code) throws LLMConfigException {
		IGConfigurableChatModel item = this.findByCode(code);
		staticConfigs.remove(item);
		item.delete();
	}

	/**
	 * Retrieve usage information of modules.
	 * 
	 * @return A list of module usage information.
	 */
	@Override
	public List<GModuleUseInfo> getModuleUseInfo() {

		List<GModuleUseInfo> use = new ArrayList<GModuleUseInfo>();
		for (IGConfigurableChatModel igConfigurableChatModel : staticConfigs) {
			GModuleUseInfo useItem = new GModuleUseInfo();
			useItem.setModuleId(GStandardModulesConstraints.CORE_MODULE);
			useItem.setHandlerId(igConfigurableChatModel.getType().getCode());
			GBaseModelChoice choosedModel = (igConfigurableChatModel.getConfig()).getChoosedModel();
			String specsCode = choosedModel != null ? choosedModel.getCode() : null;
			useItem.setSpecsCode(specsCode);
			useItem.setUsed(true);
			useItem.setInfoType(MInfoType.SETUP);
			useItem.setModuleType(ModuleType.LLMS);
			use.add(useItem);
		}
		return use;
	}

}