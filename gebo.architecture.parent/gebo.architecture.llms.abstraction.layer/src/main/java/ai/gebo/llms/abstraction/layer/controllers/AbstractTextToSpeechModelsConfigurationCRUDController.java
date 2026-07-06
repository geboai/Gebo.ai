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
import ai.gebo.llms.abstraction.layer.model.GBaseTextToSpeachModelChice;
import ai.gebo.llms.abstraction.layer.model.GBaseTextToSpeachModelConfig;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableTextToSpeechModel;
import ai.gebo.llms.abstraction.layer.services.IGTextToSpeechModelRuntimeConfigurationDao;
import ai.gebo.model.OperationStatus;
import lombok.AllArgsConstructor;

/**
 * AI generated comments
 *
 * Abstract base class providing CRUD operations for text to speech model
 * configurations. Unlike {@link BaseTextToSpeechModelsConfigurationCRUDController}
 * this variant does not bind to a single support service: subclasses resolve the
 * proper provider dynamically (e.g. via a repository pattern), making it suitable
 * for modules that host multiple OpenAI-compatible providers.
 *
 * @param <TextToSpeechModelConfigType> The type of text to speech model
 *                                      configuration.
 * @param <ModelChoice>                  The type of model choice.
 */
@AllArgsConstructor
public abstract class AbstractTextToSpeechModelsConfigurationCRUDController<TextToSpeechModelConfigType extends GBaseTextToSpeachModelConfig, ModelChoice extends GBaseTextToSpeachModelChice> {

	protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	protected final IGPersistentObjectManager persistentObjectManager;

	protected final IGTextToSpeechModelRuntimeConfigurationDao modelRuntimeConfigurationDao;

	protected final Class<TextToSpeechModelConfigType> type;

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

	protected void handleDefaultModel(TextToSpeechModelConfigType config) throws GeboPersistenceException {
		if (config.getDefaultModel() != null && config.getDefaultModel()) {
			List<GBaseTextToSpeachModelConfig> all = persistentObjectManager
					.findAllExtendingType(GBaseTextToSpeachModelConfig.class);
			for (GBaseTextToSpeachModelConfig gBaseTextToSpeachModelConfig : all) {
				if (!(gBaseTextToSpeachModelConfig.getClass().getName().equals(config.getClass().getName())
						&& gBaseTextToSpeachModelConfig.getCode().equals(config.getCode()))) {
					if (gBaseTextToSpeachModelConfig.getDefaultModel() != null
							&& gBaseTextToSpeachModelConfig.getDefaultModel()) {
						gBaseTextToSpeachModelConfig.setDefaultModel(false);
						persistentObjectManager.update(gBaseTextToSpeachModelConfig);
					}
				}
			}
		}
	}

	protected OperationStatus<TextToSpeechModelConfigType> update(TextToSpeechModelConfigType config) {
		try {
			IGConfigurableTextToSpeechModel handler = this.modelRuntimeConfigurationDao
					.findByCode(config.getCode());
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

	protected TextToSpeechModelConfigType findByCode(String code) throws GeboPersistenceException {
		return this.persistentObjectManager.findById(type, code);
	}

	protected abstract OperationStatus<List<ModelChoice>> getModelChoices(TextToSpeechModelConfigType config);
}
