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
import ai.gebo.llms.abstraction.layer.model.GBaseTranscriptModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseTranscriptModelConfig;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableTranscriptModel;
import ai.gebo.llms.abstraction.layer.services.IGTranscriptModelRuntimeConfigurationDao;
import ai.gebo.model.OperationStatus;
import io.micrometer.observation.annotation.Observed;
import lombok.AllArgsConstructor;

/**
 * AI generated comments
 *
 * Abstract base class providing CRUD operations for transcript model
 * configurations. Unlike {@link BaseTranscriptModelsConfigurationCRUDController}
 * this variant does not bind to a single support service: subclasses resolve the
 * proper provider dynamically (e.g. via a repository pattern), making it suitable
 * for modules that host multiple OpenAI-compatible providers.
 *
 * @param <TranscriptModelConfigType> The type of transcript model configuration.
 * @param <ModelChoice>                The type of model choice.
 */
@AllArgsConstructor
@Observed(name = "gebo.llms.config.crud")
public abstract class AbstractTranscriptModelsConfigurationCRUDController<TranscriptModelConfigType extends GBaseTranscriptModelConfig, ModelChoice extends GBaseTranscriptModelChoice> {

	protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	protected final IGPersistentObjectManager persistentObjectManager;

	protected final IGTranscriptModelRuntimeConfigurationDao modelRuntimeConfigurationDao;

	protected final Class<TranscriptModelConfigType> type;

	protected OperationStatus<TranscriptModelConfigType> insert(TranscriptModelConfigType config) {
		TranscriptModelConfigType out = null;
		LOGGER.info("Begin transcript model configuration insert");
		try {
			out = persistentObjectManager.insert(config);
			handleDefaultModel(out);
		} catch (Throwable e) {
			LOGGER.error("Exception while inserting configuration ", e);
			return OperationStatus.<TranscriptModelConfigType>of(e);
		}
		try {
			this.modelRuntimeConfigurationDao.addRuntimeByConfigClustered(config);
		} catch (Throwable e) {
			LOGGER.error("Exception while configuring new transcript model ", e);
			try {
				persistentObjectManager.delete(config);
			} catch (Throwable th) {
				LOGGER.error("Cannot delete new transcript model from mongo ", th);
			}
			return OperationStatus.<TranscriptModelConfigType>of(e);
		}
		LOGGER.info("End transcript model configuration insert successfully");
		return OperationStatus.of(out);
	}

	protected void handleDefaultModel(TranscriptModelConfigType config) throws GeboPersistenceException {
		if (config.getDefaultModel() != null && config.getDefaultModel()) {
			List<GBaseTranscriptModelConfig> all = persistentObjectManager
					.findAllExtendingType(GBaseTranscriptModelConfig.class);
			for (GBaseTranscriptModelConfig gBaseTranscriptModelConfig : all) {
				if (!(gBaseTranscriptModelConfig.getClass().getName().equals(config.getClass().getName())
						&& gBaseTranscriptModelConfig.getCode().equals(config.getCode()))) {
					if (gBaseTranscriptModelConfig.getDefaultModel() != null
							&& gBaseTranscriptModelConfig.getDefaultModel()) {
						gBaseTranscriptModelConfig.setDefaultModel(false);
						persistentObjectManager.update(gBaseTranscriptModelConfig);
					}
				}
			}
		}
	}

	protected OperationStatus<TranscriptModelConfigType> update(TranscriptModelConfigType config) {
		try {
			this.modelRuntimeConfigurationDao.reconfigureByConfigClustered(config);
		} catch (Throwable e) {
			LOGGER.error("Exception reconfiguring model", e);
			return OperationStatus.<TranscriptModelConfigType>of(e);
		}
		TranscriptModelConfigType out;
		try {
			out = this.persistentObjectManager.update(config);
			handleDefaultModel(out);
			return OperationStatus.of(out);
		} catch (GeboPersistenceException e) {
			LOGGER.error("Exception saving model", e);
			return OperationStatus.<TranscriptModelConfigType>of(e);
		}

	}

	protected OperationStatus<Boolean> delete(TranscriptModelConfigType type) {
		try {
			this.modelRuntimeConfigurationDao.deleteByCodeClustered(type.getCode());
			this.persistentObjectManager.delete(type);
		} catch (Throwable e) {
			LOGGER.error("Exception deleting model", e);
			return OperationStatus.<Boolean>of(e);
		}

		return OperationStatus.of(true);
	}

	protected TranscriptModelConfigType findByCode(String code) throws GeboPersistenceException {
		return this.persistentObjectManager.findById(type, code);
	}

	protected abstract OperationStatus<List<ModelChoice>> getModelChoices(TranscriptModelConfigType config);
}
