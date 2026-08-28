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
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.IGSecurityAuditLoggerService.SecurityEvent;
import ai.gebo.security.services.SecurityAuditTaxonomy;
import io.micrometer.observation.annotation.Observed;
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
@Observed(name = "gebo.llms.config.crud")
public abstract class AbstractTextToSpeechModelsConfigurationCRUDController<TextToSpeechModelConfigType extends GBaseTextToSpeachModelConfig, ModelChoice extends GBaseTextToSpeachModelChice> {

	protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	protected final IGPersistentObjectManager persistentObjectManager;

	protected final IGTextToSpeechModelRuntimeConfigurationDao modelRuntimeConfigurationDao;

	protected final Class<TextToSpeechModelConfigType> type;

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

	protected OperationStatus<TextToSpeechModelConfigType> insert(TextToSpeechModelConfigType config) {
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		OperationStatus<TextToSpeechModelConfigType> status = insertInternal(config);
		logConfigEvent(event, SecurityAuditTaxonomy.Action.LLM_CONFIG_INSERT, config != null ? config.getCode() : null,
				status);
		return status;
	}

	private OperationStatus<TextToSpeechModelConfigType> insertInternal(TextToSpeechModelConfigType config) {
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
			this.modelRuntimeConfigurationDao.addRuntimeByConfigClustered(config);
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
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		OperationStatus<TextToSpeechModelConfigType> status = updateInternal(config);
		logConfigEvent(event, SecurityAuditTaxonomy.Action.LLM_CONFIG_UPDATE, config != null ? config.getCode() : null,
				status);
		return status;
	}

	private OperationStatus<TextToSpeechModelConfigType> updateInternal(TextToSpeechModelConfigType config) {
		try {
			this.modelRuntimeConfigurationDao.reconfigureByConfigClustered(config);
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
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		OperationStatus<Boolean> status = deleteInternal(type);
		logConfigEvent(event, SecurityAuditTaxonomy.Action.LLM_CONFIG_DELETE, type != null ? type.getCode() : null,
				status);
		return status;
	}

	private OperationStatus<Boolean> deleteInternal(TextToSpeechModelConfigType type) {
		try {
			this.modelRuntimeConfigurationDao.deleteByCodeClustered(type.getCode());
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
