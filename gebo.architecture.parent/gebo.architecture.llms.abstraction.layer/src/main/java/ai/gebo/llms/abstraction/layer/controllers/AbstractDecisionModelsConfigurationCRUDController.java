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
import ai.gebo.llms.abstraction.layer.model.GBaseDecisionModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseDecisionModelConfig;
import ai.gebo.llms.abstraction.layer.services.IGDecisionModelRuntimeConfigurationDao;
import ai.gebo.model.OperationStatus;
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.IGSecurityAuditLoggerService.SecurityEvent;
import ai.gebo.security.services.SecurityAuditTaxonomy;
import io.micrometer.observation.annotation.Observed;
import lombok.AllArgsConstructor;

/**
 * The CRUD of a provider's decision model configurations: persists the configuration,
 * keeps the running model in step with it (added, reconfigured, deleted, cluster wide),
 * keeps one default decision model, and writes a security audit event per change.
 *
 * @param <DecisionModelType> the provider's configuration type
 * @param <ModelChoice>       the provider's model choice type
 */
@AllArgsConstructor
@Observed(name = "gebo.llms.config.crud")
public abstract class AbstractDecisionModelsConfigurationCRUDController<DecisionModelType extends GBaseDecisionModelConfig, ModelChoice extends GBaseDecisionModelChoice> {

	protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	protected final IGPersistentObjectManager persistentObjectManager;
	protected final IGDecisionModelRuntimeConfigurationDao modelRuntimeConfigurationDao;
	protected final Class<DecisionModelType> type;
	protected final IGSecurityAuditLoggerService securityAuditLoggerService;

	// Takes an already-created SecurityEvent (never calls newSecurityEvent() itself) so
	// newSecurityEvent()'s caller-stack capture points at insert/update/delete - the
	// real API entry point - not at this shared helper.
	private void logConfigEvent(SecurityEvent event, String action, String resourceId, OperationStatus<?> status) {
		event.setEventType(SecurityAuditTaxonomy.EventType.LLM_CONFIGURATION);
		event.setCategory(SecurityAuditTaxonomy.Category.LLM_CONFIGURATION);
		event.setAction(action);
		event.setResourceId(resourceId);
		event.setOutcome(status.isHasErrorMessages() ? SecurityAuditTaxonomy.Outcome.FAILURE
				: SecurityAuditTaxonomy.Outcome.SUCCESS);
		securityAuditLoggerService.log(event);
	}

	protected OperationStatus<DecisionModelType> insert(DecisionModelType config) {
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		OperationStatus<DecisionModelType> status = insertInternal(config);
		logConfigEvent(event, SecurityAuditTaxonomy.Action.LLM_CONFIG_INSERT, config != null ? config.getCode() : null,
				status);
		return status;
	}

	private OperationStatus<DecisionModelType> insertInternal(DecisionModelType config) {
		DecisionModelType out = null;
		LOGGER.info("Begin decision model configuration insert");
		try {
			out = persistentObjectManager.insert(config);
			handleDefaultModel(out);
		} catch (Throwable e) {
			LOGGER.error("Exception while inserting configuration ", e);
			return OperationStatus.<DecisionModelType>of(e);
		}
		try {
			this.modelRuntimeConfigurationDao.addRuntimeByConfigClustered(config);
		} catch (Throwable e) {
			LOGGER.error("Exception while configuring new decision model ", e);
			try {
				persistentObjectManager.delete(config);
			} catch (Throwable th) {
				LOGGER.error("Cannot delete new decision model from mongo ", th);
			}
			return OperationStatus.<DecisionModelType>of(e);
		}
		LOGGER.info("End decision model configuration insert successfully");
		return OperationStatus.of(out);
	}

	protected void handleDefaultModel(DecisionModelType config) throws GeboPersistenceException {
		if (config.getDefaultModel() != null && config.getDefaultModel()) {
			List<GBaseDecisionModelConfig> all = persistentObjectManager
					.findAllExtendingType(GBaseDecisionModelConfig.class);
			for (GBaseDecisionModelConfig other : all) {
				if (!(other.getClass().getName().equals(config.getClass().getName())
						&& other.getCode().equals(config.getCode()))) {
					if (other.getDefaultModel() != null && other.getDefaultModel()) {
						other.setDefaultModel(false);
						persistentObjectManager.update(other);
					}
				}
			}
		}
	}

	protected OperationStatus<DecisionModelType> update(DecisionModelType config) {
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		OperationStatus<DecisionModelType> status = updateInternal(config);
		logConfigEvent(event, SecurityAuditTaxonomy.Action.LLM_CONFIG_UPDATE, config != null ? config.getCode() : null,
				status);
		return status;
	}

	private OperationStatus<DecisionModelType> updateInternal(DecisionModelType config) {
		try {
			this.modelRuntimeConfigurationDao.reconfigureByConfigClustered(config);
		} catch (Throwable e) {
			LOGGER.error("Exception reconfiguring model", e);
			return OperationStatus.<DecisionModelType>of(e);
		}
		DecisionModelType out;
		try {
			out = this.persistentObjectManager.update(config);
			handleDefaultModel(out);
			return OperationStatus.of(out);
		} catch (GeboPersistenceException e) {
			LOGGER.error("Exception saving model", e);
			return OperationStatus.<DecisionModelType>of(e);
		}
	}

	protected OperationStatus<Boolean> delete(DecisionModelType config) {
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		OperationStatus<Boolean> status = deleteInternal(config);
		logConfigEvent(event, SecurityAuditTaxonomy.Action.LLM_CONFIG_DELETE, config != null ? config.getCode() : null,
				status);
		return status;
	}

	private OperationStatus<Boolean> deleteInternal(DecisionModelType config) {
		try {
			this.modelRuntimeConfigurationDao.deleteByCodeClustered(config.getCode());
			this.persistentObjectManager.delete(config);
		} catch (Throwable e) {
			LOGGER.error("Exception deleting model", e);
			return OperationStatus.<Boolean>of(e);
		}
		return OperationStatus.of(true);
	}

	protected DecisionModelType findByCode(String code) throws GeboPersistenceException {
		return this.persistentObjectManager.findById(type, code);
	}

	protected abstract OperationStatus<List<ModelChoice>> getModelChoices(DecisionModelType config);
}
