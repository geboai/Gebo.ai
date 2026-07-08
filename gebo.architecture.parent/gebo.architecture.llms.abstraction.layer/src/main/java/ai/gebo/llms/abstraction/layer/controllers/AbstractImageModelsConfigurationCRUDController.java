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
import ai.gebo.llms.abstraction.layer.model.GBaseImageModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseImageModelConfig;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableImageModel;
import ai.gebo.llms.abstraction.layer.services.IGImageModelRuntimeConfigurationDao;
import ai.gebo.model.OperationStatus;
import lombok.AllArgsConstructor;

/**
 * AI generated comments
 *
 * Abstract base class providing CRUD operations for image model configurations.
 * Unlike {@link BaseImageModelsConfigurationCRUDController} this variant does not
 * bind to a single support service: subclasses resolve the proper provider
 * dynamically (e.g. via a repository pattern), making it suitable for modules
 * that host multiple OpenAI-compatible providers.
 *
 * @param <ImageModelConfigType> The type of image model configuration.
 * @param <ModelChoice>          The type of model choice.
 */
@AllArgsConstructor
public abstract class AbstractImageModelsConfigurationCRUDController<ImageModelConfigType extends GBaseImageModelConfig, ModelChoice extends GBaseImageModelChoice> {

	protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	protected final IGPersistentObjectManager persistentObjectManager;

	protected final IGImageModelRuntimeConfigurationDao modelRuntimeConfigurationDao;

	protected final Class<ImageModelConfigType> type;

	protected OperationStatus<ImageModelConfigType> insert(ImageModelConfigType config) {
		ImageModelConfigType out = null;
		LOGGER.info("Begin image model configuration insert");
		try {
			out = persistentObjectManager.insert(config);
			handleDefaultModel(out);
		} catch (Throwable e) {
			LOGGER.error("Exception while inserting configuration ", e);
			return OperationStatus.<ImageModelConfigType>of(e);
		}
		try {
			this.modelRuntimeConfigurationDao.addRuntimeByConfigClustered(config);
		} catch (Throwable e) {
			LOGGER.error("Exception while configuring new image model ", e);
			try {
				persistentObjectManager.delete(config);
			} catch (Throwable th) {
				LOGGER.error("Cannot delete new image model from mongo ", th);
			}
			return OperationStatus.<ImageModelConfigType>of(e);
		}
		LOGGER.info("End image model configuration insert successfully");
		return OperationStatus.of(out);
	}

	protected void handleDefaultModel(ImageModelConfigType config) throws GeboPersistenceException {
		if (config.getDefaultModel() != null && config.getDefaultModel()) {
			List<GBaseImageModelConfig> all = persistentObjectManager
					.findAllExtendingType(GBaseImageModelConfig.class);
			for (GBaseImageModelConfig gBaseImageModelConfig : all) {
				if (!(gBaseImageModelConfig.getClass().getName().equals(config.getClass().getName())
						&& gBaseImageModelConfig.getCode().equals(config.getCode()))) {
					if (gBaseImageModelConfig.getDefaultModel() != null
							&& gBaseImageModelConfig.getDefaultModel()) {
						gBaseImageModelConfig.setDefaultModel(false);
						persistentObjectManager.update(gBaseImageModelConfig);
					}
				}
			}
		}
	}

	protected OperationStatus<ImageModelConfigType> update(ImageModelConfigType config) {
		try {
			this.modelRuntimeConfigurationDao.reconfigureByConfigClustered(config);
		} catch (Throwable e) {
			LOGGER.error("Exception reconfiguring model", e);
			return OperationStatus.<ImageModelConfigType>of(e);
		}
		ImageModelConfigType out;
		try {
			out = this.persistentObjectManager.update(config);
			handleDefaultModel(out);
			return OperationStatus.of(out);
		} catch (GeboPersistenceException e) {
			LOGGER.error("Exception saving model", e);
			return OperationStatus.<ImageModelConfigType>of(e);
		}

	}

	protected OperationStatus<Boolean> delete(ImageModelConfigType type) {
		try {
			this.modelRuntimeConfigurationDao.deleteByCodeClustered(type.getCode());
			this.persistentObjectManager.delete(type);
		} catch (Throwable e) {
			LOGGER.error("Exception deleting model", e);
			return OperationStatus.<Boolean>of(e);
		}

		return OperationStatus.of(true);
	}

	protected ImageModelConfigType findByCode(String code) throws GeboPersistenceException {
		return this.persistentObjectManager.findById(type, code);
	}

	protected abstract OperationStatus<List<ModelChoice>> getModelChoices(ImageModelConfigType config);
}
