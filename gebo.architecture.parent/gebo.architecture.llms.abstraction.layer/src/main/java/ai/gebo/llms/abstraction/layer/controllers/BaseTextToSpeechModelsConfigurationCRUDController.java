/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.abstraction.layer.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseTextToSpeachModelChice;
import ai.gebo.llms.abstraction.layer.model.GBaseTextToSpeachModelConfig;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableTextToSpeechModel;
import ai.gebo.llms.abstraction.layer.services.IGTextToSpeechModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGTextToSpeechModelRuntimeConfigurationDao;
import ai.gebo.model.OperationStatus;
import lombok.AllArgsConstructor;

/**
 * Abstract base class for CRUD operations on text to speech model configurations.
 * Provides common functionality for inserting, updating, deleting, and finding
 * text to speech model configurations.
 *
 * @param <TextToSpeechModelConfigType> The type of text to speech model configuration.
 * @param <ModelChoice>                 The type of model choice.
 * 
 *                                      AI generated comments
 */
@AllArgsConstructor
public class BaseTextToSpeechModelsConfigurationCRUDController<TextToSpeechModelConfigType extends GBaseTextToSpeachModelConfig, ModelChoice extends GBaseTextToSpeachModelChice, Iface extends IGTextToSpeechModelConfigurationSupportService> {

	// Logger instance for logging operations and exceptions
	protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	// Persistent object manager for database operations

	protected final IGPersistentObjectManager persistentObjectManager;

	// DAO for text to speech model runtime configuration operations

	protected final IGTextToSpeechModelRuntimeConfigurationDao modelRuntimeConfigurationDao;

	// Type of the text to speech model configuration
	protected final Class<TextToSpeechModelConfigType> type;

	protected final Iface supportService;

	/**
	 * Inserts a new text to speech model configuration and handles runtime configuration.
	 *
	 * @param config The text to speech model configuration to insert.
	 * @return The operation status containing the inserted configuration or error.
	 */
	protected OperationStatus<TextToSpeechModelConfigType> insert(TextToSpeechModelConfigType config) {
		TextToSpeechModelConfigType out = null;
		LOGGER.info("Begin text to speech model configuration insert");
		try {
			out = persistentObjectManager.insert(config);
			handleDefaultModel(out);
		} catch (Throwable e) {
			LOGGER.error("Exception while inserting configuration ", e);
			return OperationStatus.<TextToSpeechModelConfigType>of(e);
		}
		try {
			this.modelRuntimeConfigurationDao.addRuntimeByConfig(config);
		} catch (Throwable e) {
			LOGGER.error("Exception while configuring new text to speech model ", e);
			try {
				persistentObjectManager.delete(config);
			} catch (Throwable th) {
				LOGGER.error("Cannot delete new text to speech model from mongo ", th);
			}
			return OperationStatus.<TextToSpeechModelConfigType>of(e);
		}
		LOGGER.info("End text to speech model configuration insert successfully");
		return OperationStatus.of(out);
	}

	/**
	 * Ensures that only one model is set as the default. If the given configuration
	 * is set as default, other models are updated accordingly.
	 *
	 * @param config The configuration that may be set as default.
	 * @throws GeboPersistenceException If a persistence error occurs.
	 */
	protected void handleDefaultModel(TextToSpeechModelConfigType config) throws GeboPersistenceException {
		if (config.getDefaultModel() != null && config.getDefaultModel()) {
			List<GBaseChatModelConfig> all = persistentObjectManager.findAllExtendingType(GBaseChatModelConfig.class);
			for (GBaseChatModelConfig gBaseChatModelConfig : all) {
				if (!(gBaseChatModelConfig.getClass().getName().equals(config.getClass().getName())
						&& gBaseChatModelConfig.getCode().equals(config.getCode()))) {
					if (gBaseChatModelConfig.getDefaultModel() != null && gBaseChatModelConfig.getDefaultModel()) {
						gBaseChatModelConfig.setDefaultModel(false);
						persistentObjectManager.update(gBaseChatModelConfig);
					}
				}
			}
		}
	}

	/**
	 * Updates an existing text to speech model configuration and reconfigures the runtime
	 * handler.
	 *
	 * @param config The text to speech model configuration to update.
	 * @return The operation status containing the updated configuration or error.
	 */
	protected OperationStatus<TextToSpeechModelConfigType> update(TextToSpeechModelConfigType config) {
		try {
			IGConfigurableTextToSpeechModel handler = this.modelRuntimeConfigurationDao.findByCode(config.getCode());
			handler.reconfigure(config);
		} catch (Throwable e) {
			LOGGER.error("Exception reconfiguring model", e);
			return OperationStatus.<TextToSpeechModelConfigType>of(e);
		}
		TextToSpeechModelConfigType out;
		try {
			out = this.persistentObjectManager.update(config);
			handleDefaultModel(out);
			return OperationStatus.of(out);
		} catch (GeboPersistenceException e) {
			LOGGER.error("Exception saving model", e);
			return OperationStatus.<TextToSpeechModelConfigType>of(e);
		}

	}

	/**
	 * Deletes a text to speech model configuration based on its type.
	 *
	 * @param type The type of the text to speech model configuration to delete.
	 * @return Operation status indicating success or with any errors encountered.
	 */
	protected OperationStatus<Boolean> delete(TextToSpeechModelConfigType type) {
		try {
			this.modelRuntimeConfigurationDao.deleteByCode(type.getCode());
			this.persistentObjectManager.delete(type);
		} catch (Throwable e) {
			LOGGER.error("Exception deleting model", e);
			return OperationStatus.<Boolean>of(e);
		}

		return OperationStatus.of(true);
	}

	/**
	 * Finds a text to speech model configuration by its code.
	 *
	 * @param code The code of the text to speech model configuration.
	 * @return The found text to speech model configuration.
	 * @throws GeboPersistenceException If a persistence error occurs.
	 */
	protected TextToSpeechModelConfigType findByCode(String code) throws GeboPersistenceException {
		return this.persistentObjectManager.findById(type, code);
	}

	/**
	 * Abstract method to obtain model choices based on a text to speech model configuration.
	 * Must be implemented by subclasses.
	 *
	 * @param type The text to speech model configuration.
	 * @return The operation status containing the list of model choices or error.
	 */
	protected OperationStatus<List<ModelChoice>> getModelChoices(TextToSpeechModelConfigType type) {
		return supportService.getModelChoices(type);
	}
}