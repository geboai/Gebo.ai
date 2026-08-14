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
import org.springframework.beans.factory.annotation.Autowired;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelConfig;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.model.OperationStatus;
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.IGSecurityAuditLoggerService.SecurityEvent;
import ai.gebo.security.services.SecurityAuditTaxonomy;
import io.micrometer.observation.annotation.Observed;
import lombok.AllArgsConstructor;

/**
 * AI generated comments
 *
 * Abstract class providing CRUD operations for embedding model configurations.
 * Supports insertion, updating, deletion, and retrieval of embedding model
 * configurations and their runtime configurations.
 *
 * @param <EmbeddingModelConfigType> The type of the embedding model
 *                                   configuration.
 * @param <ModelChoice>              The type of the model choice.
 */
@AllArgsConstructor
@Observed(name = "gebo.llms.config.crud")
public abstract class AbstractBaseEmbeddingModelsConfigurationCRUDController<EmbeddingModelConfigType extends GBaseEmbeddingModelConfig, ModelChoice extends GBaseEmbeddingModelChoice> {
	protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	protected final IGPersistentObjectManager persistentObjectManager;

	protected final IGEmbeddingModelRuntimeConfigurationDao modelRuntimeConfigurationDao;

	protected final Class<EmbeddingModelConfigType> type;

	protected final IGSecurityAuditLoggerService securityAuditLoggerService;

	// Takes an already-created SecurityEvent (never calls newSecurityEvent()
	// itself) so newSecurityEvent()'s caller-stack capture points at insert/
	// update/delete - the real API entry point - not at this shared helper.
	private void logConfigEvent(SecurityEvent event, String action, String resourceId, OperationStatus<?> status) {
		event.setEventType(SecurityAuditTaxonomy.EventType.LLM_CONFIGURATION);
		event.setCategory(SecurityAuditTaxonomy.Category.LLM_CONFIGURATION);
		event.setAction(action);
		event.setResourceId(resourceId);
		event.setOutcome(status.isHasErrorMessages() ? SecurityAuditTaxonomy.Outcome.FAILURE
				: SecurityAuditTaxonomy.Outcome.SUCCESS);
		securityAuditLoggerService.log(event);
	}

	/**
	 * Inserts a new embedding model configuration. Handles default model setting
	 * and adds runtime configuration.
	 *
	 * @param config The configuration to insert.
	 * @return OperationStatus indicating the result of the insertion.
	 */
	protected OperationStatus<EmbeddingModelConfigType> insert(EmbeddingModelConfigType config) {
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		OperationStatus<EmbeddingModelConfigType> status = insertInternal(config);
		logConfigEvent(event, SecurityAuditTaxonomy.Action.LLM_CONFIG_INSERT, config != null ? config.getCode() : null,
				status);
		return status;
	}

	private OperationStatus<EmbeddingModelConfigType> insertInternal(EmbeddingModelConfigType config) {
		EmbeddingModelConfigType out = null;
		LOGGER.info("Begin chat model configuration insert");
		try {
			out = persistentObjectManager.insert(config);
			handleDefaultModel(out);
		} catch (Throwable e) {
			LOGGER.error("Exception while inserting configuration ", e);
			return OperationStatus.<EmbeddingModelConfigType>of(e);
		}
		try {
			this.modelRuntimeConfigurationDao.addRuntimeByConfigClustered(config);
		} catch (Throwable e) {
			LOGGER.error("Exception while configuring new chat model ", e);
			try {
				persistentObjectManager.delete(config);
			} catch (Throwable th) {
				LOGGER.error("Cannot delete new chat model from mongo ", th);
			}
			return OperationStatus.<EmbeddingModelConfigType>of(e);
		}
		LOGGER.info("End chat model configuration insert successfully");
		return OperationStatus.of(out);
	}

	/**
	 * Updates an existing embedding model configuration with new settings.
	 *
	 * @param config The updated configuration.
	 * @return OperationStatus indicating the result of the update.
	 */
	protected OperationStatus<EmbeddingModelConfigType> update(EmbeddingModelConfigType config) {
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		OperationStatus<EmbeddingModelConfigType> status = updateInternal(config);
		logConfigEvent(event, SecurityAuditTaxonomy.Action.LLM_CONFIG_UPDATE, config != null ? config.getCode() : null,
				status);
		return status;
	}

	private OperationStatus<EmbeddingModelConfigType> updateInternal(EmbeddingModelConfigType config) {
		try {
			this.modelRuntimeConfigurationDao.reconfigureByConfigClustered(config);
		} catch (Throwable e) {
			LOGGER.error("Exception reconfiguring model", e);
			return OperationStatus.<EmbeddingModelConfigType>of(e);
		}
		EmbeddingModelConfigType out;
		try {
			out = this.persistentObjectManager.update(config);
			handleDefaultModel(out);
			return OperationStatus.of(out);
		} catch (GeboPersistenceException e) {
			LOGGER.error("Exception saving model", e);
			return OperationStatus.<EmbeddingModelConfigType>of(e);
		}
	}

	/**
	 * Deletes an embedding model configuration.
	 *
	 * @param type The configuration type to delete.
	 * @return OperationStatus indicating the result of the deletion.
	 */
	protected OperationStatus<Boolean> delete(EmbeddingModelConfigType type) {
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		OperationStatus<Boolean> status = deleteInternal(type);
		logConfigEvent(event, SecurityAuditTaxonomy.Action.LLM_CONFIG_DELETE, type != null ? type.getCode() : null,
				status);
		return status;
	}

	private OperationStatus<Boolean> deleteInternal(EmbeddingModelConfigType type) {
		try {
			this.modelRuntimeConfigurationDao.deleteByCodeClustered(type.getCode());
			this.persistentObjectManager.delete(type);
		} catch (Throwable e) {
			LOGGER.error("Exception deleting model", e);
			return OperationStatus.<Boolean>of(e);
		}
		return OperationStatus.of(true);
	}

	/**
	 * Handles the assignment of default model configuration. If a new default model
	 * is set, updates other model configurations to not be default.
	 *
	 * @param config The configuration potentially marked as default.
	 * @throws GeboPersistenceException If there are persistence errors.
	 */
	protected void handleDefaultModel(EmbeddingModelConfigType config) throws GeboPersistenceException {
		if (config.getDefaultModel() != null && config.getDefaultModel()) {
			List<GBaseEmbeddingModelConfig> all = persistentObjectManager
					.findAllExtendingType(GBaseEmbeddingModelConfig.class);
			for (GBaseEmbeddingModelConfig gBaseChatModelConfig : all) {
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
	 * Finds an embedding model configuration by its code.
	 *
	 * @param code The code to search for.
	 * @return The corresponding EmbeddingModelConfigType.
	 * @throws GeboPersistenceException If the configuration cannot be found.
	 */
	protected EmbeddingModelConfigType findByCode(String code) throws GeboPersistenceException {
		return this.persistentObjectManager.findById(type, code);
	}

	/**
	 * Abstract method to get model choices based on the given type. Must be
	 * implemented by subclasses.
	 *
	 * @param type The configuration type to get model choices for.
	 * @return OperationStatus containing a list of ModelChoice.
	 */
	protected abstract OperationStatus<List<ModelChoice>> getModelChoices(EmbeddingModelConfigType type);
}