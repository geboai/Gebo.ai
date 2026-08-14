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
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.model.OperationStatus;
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.IGSecurityAuditLoggerService.SecurityEvent;
import ai.gebo.security.services.SecurityAuditTaxonomy;
import io.micrometer.observation.annotation.Observed;
import lombok.AllArgsConstructor;

/**
 * Abstract base class for CRUD operations on chat model configurations.
 * Provides common functionality for inserting, updating, deleting, and finding chat model configurations.
 *
 * @param <ChatModelConfigType> The type of chat model configuration.
 * @param <ModelChoice> The type of model choice.
 *
 * AI generated comments
 */
@AllArgsConstructor
@Observed(name = "gebo.llms.config.crud")
public abstract class AbstractBaseChatModelsConfigurationCRUDController<ChatModelConfigType extends GBaseChatModelConfig, ModelChoice extends GBaseChatModelChoice> {

	// Logger instance for logging operations and exceptions
	protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	// Persistent object manager for database operations
	protected final IGPersistentObjectManager persistentObjectManager;

	// DAO for chat model runtime configuration operations
	
	protected final IGChatModelRuntimeConfigurationDao modelRuntimeConfigurationDao;

	// Type of the chat model configuration
	protected final Class<ChatModelConfigType> type;

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
	 * Inserts a new chat model configuration and handles runtime configuration.
	 *
	 * @param config The chat model configuration to insert.
	 * @return The operation status containing the inserted configuration or error.
	 */
	protected OperationStatus<ChatModelConfigType> insert(ChatModelConfigType config) {
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		OperationStatus<ChatModelConfigType> status = insertInternal(config);
		logConfigEvent(event, SecurityAuditTaxonomy.Action.LLM_CONFIG_INSERT, config != null ? config.getCode() : null,
				status);
		return status;
	}

	private OperationStatus<ChatModelConfigType> insertInternal(ChatModelConfigType config) {
		ChatModelConfigType out = null;
		LOGGER.info("Begin chat model configuration insert");
		try {
			out = persistentObjectManager.insert(config);
			handleDefaultModel(out);
		} catch (Throwable e) {
			LOGGER.error("Exception while inserting configuration ", e);
			return OperationStatus.<ChatModelConfigType>of(e);
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
			return OperationStatus.<ChatModelConfigType>of(e);
		}
		LOGGER.info("End chat model configuration insert successfully");
		return OperationStatus.of(out);
	}

	/**
	 * Ensures that only one model is set as the default.
	 * If the given configuration is set as default, other models are updated accordingly.
	 *
	 * @param config The configuration that may be set as default.
	 * @throws GeboPersistenceException If a persistence error occurs.
	 */
	protected void handleDefaultModel(ChatModelConfigType config) throws GeboPersistenceException {
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
	 * Updates an existing chat model configuration and reconfigures the runtime handler.
	 *
	 * @param config The chat model configuration to update.
	 * @return The operation status containing the updated configuration or error.
	 */
	protected OperationStatus<ChatModelConfigType> update(ChatModelConfigType config) {
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		OperationStatus<ChatModelConfigType> status = updateInternal(config);
		logConfigEvent(event, SecurityAuditTaxonomy.Action.LLM_CONFIG_UPDATE, config != null ? config.getCode() : null,
				status);
		return status;
	}

	private OperationStatus<ChatModelConfigType> updateInternal(ChatModelConfigType config) {
		try {
			this.modelRuntimeConfigurationDao.reconfigureByConfigClustered(config);
		} catch (Throwable e) {
			LOGGER.error("Exception reconfiguring model", e);
			return OperationStatus.<ChatModelConfigType>of(e);
		}
		ChatModelConfigType out;
		try {
			out = this.persistentObjectManager.update(config);
			handleDefaultModel(out);
			return OperationStatus.of(out);
		} catch (GeboPersistenceException e) {
			LOGGER.error("Exception saving model", e);
			return OperationStatus.<ChatModelConfigType>of(e);
		}

	}

	/**
	 * Deletes a chat model configuration based on its type.
	 *
	 * @param type The type of the chat model configuration to delete.
	 * @return Operation status indicating success or with any errors encountered.
	 */
	protected OperationStatus<Boolean> delete(ChatModelConfigType type) {
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		OperationStatus<Boolean> status = deleteInternal(type);
		logConfigEvent(event, SecurityAuditTaxonomy.Action.LLM_CONFIG_DELETE, type != null ? type.getCode() : null,
				status);
		return status;
	}

	private OperationStatus<Boolean> deleteInternal(ChatModelConfigType type) {
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
	 * Finds a chat model configuration by its code.
	 *
	 * @param code The code of the chat model configuration.
	 * @return The found chat model configuration.
	 * @throws GeboPersistenceException If a persistence error occurs.
	 */
	protected ChatModelConfigType findByCode(String code) throws GeboPersistenceException {
		return this.persistentObjectManager.findById(type, code);
	}

	/**
	 * Abstract method to obtain model choices based on a chat model configuration.
	 * Must be implemented by subclasses.
	 *
	 * @param type The chat model configuration.
	 * @return The operation status containing the list of model choices or error.
	 */
	protected abstract OperationStatus<List<ModelChoice>> getModelChoices(ChatModelConfigType type);
}