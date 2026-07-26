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

import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.patterns.model.GModuleUseInfo;
import ai.gebo.architecture.patterns.model.GModuleUseInfo.MInfoType;
import ai.gebo.architecture.patterns.model.GModuleUseInfo.ModuleType;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.llms.abstraction.layer.cluster.GAbstractClusteredModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.cluster.GLlmModelClusterCategory;
import ai.gebo.llms.abstraction.layer.model.GBaseImageModelConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseModelChoice;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableImageModel;
import ai.gebo.llms.abstraction.layer.services.IGImageModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGImageModelConfigurationSupportServiceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.IGImageModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;

/**
 * AI generated comments Implementation of the
 * IGChatModelRuntimeConfigurationDao interface. Manages runtime configurations
 * of chat models. Handles dynamic initialization of chat models upon
 * application context refresh.
 */
@Component
@Scope("singleton")
public class GImageModelRuntimeConfigurationDaoImpl
		extends GAbstractClusteredModelRuntimeConfigurationDao<IGConfigurableImageModel, GBaseImageModelConfig>
		implements IGImageModelRuntimeConfigurationDao {

	@Override
	protected GLlmModelClusterCategory getClusterCategory() {
		return GLlmModelClusterCategory.IMAGE;
	}

	// Logger instance for this class
	static Logger LOGGER = LoggerFactory.getLogger(GImageModelRuntimeConfigurationDaoImpl.class);
	// ObjectMapper instance for JSON processing
	static ObjectMapper mapper = new ObjectMapper();

	@Autowired
	IGImageModelConfigurationSupportServiceRepositoryPattern supportRepoPattern;

	@Autowired
	IGPersistentObjectManager persistentObjectManager;

	/**
	 * Default constructor initializing the base class with an empty list.
	 */
	public GImageModelRuntimeConfigurationDaoImpl() {
		super(new ArrayList(), null);
	}

	/**
	 * Find a chat model configuration by its unique code.
	 * 
	 * @param code The unique code identifying the chat model configuration.
	 * @return The chat model configuration associated with the provided code.
	 */
	@Override
	public IGConfigurableImageModel findByCode(String code) {

		return this.findByPredicate(x -> {
			return x.getCode() != null && code != null && x.getCode().equals(code);
		});
	}

	/**
	 * Initializes image models dynamically. Invoked once this DAO's own
	 * application context finishes refreshing.
	 */
	@Override
	protected void initializeRuntimeModels() {
		LOGGER.info("Begin initializing image models dinamically");
		try {
			// Retrieve all configurations extending GBaseChatModelConfig
			List<GBaseImageModelConfig> configs = persistentObjectManager
					.findAllExtendingType(GBaseImageModelConfig.class);
			for (GBaseImageModelConfig config : configs) {
				try {
					this.addRuntimeByConfig(config);
				} catch (Throwable e) {
					// A single model that cannot be allocated (revoked key, provider down, stale
					// configuration) must never keep the whole application from starting: report
					// it and carry on with the remaining models.
					LOGGER.error("Cannot initialize the image model with code=>" + config.getCode(), e);
				}
			}
		} catch (GeboPersistenceException e) {
			LOGGER.error("Cannot read the image models configuration", e);
		}

		LOGGER.info("End initializing image models dinamically");
	}

	/**
	 * Add a chat model to the static configurations list.
	 * 
	 * @param element The chat model to be added.
	 */
	@Override
	public void add(IGConfigurableImageModel element) {
		this.staticConfigs.add(element);
	}

	/**
	 * Add a chat model to runtime configurations based on the provided
	 * configuration. If the handler is not found, an exception is thrown.
	 * 
	 * @param config The configuration of the chat model.
	 * @throws LLMConfigException If the chat model configuration fails.
	 */
	@Override
	public void addRuntimeByConfig(GBaseImageModelConfig config) throws LLMConfigException {
		IGImageModelConfigurationSupportService handler = supportRepoPattern.findImplementation(x -> {
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
			} catch (JacksonException e) {
				// Log parsing exception if necessary
			}
			IGConfigurableImageModel imageModel = handler.create(config);
			LOGGER.info("Initialized chatModel successfully");
			this.staticConfigs.add(imageModel);
		}
	}

	/**
	 * Delete a chat model configuration by its unique code.
	 * 
	 * @param code The unique code identifying the chat model configuration to be
	 *             deleted.
	 * @throws LLMConfigException If the chat model configuration cannot be found.
	 */
	@Override
	public void deleteByCode(String code) throws LLMConfigException {
		IGConfigurableImageModel item = this.findByCode(code);
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
		for (IGConfigurableImageModel igConfigurableChatModel : staticConfigs) {
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