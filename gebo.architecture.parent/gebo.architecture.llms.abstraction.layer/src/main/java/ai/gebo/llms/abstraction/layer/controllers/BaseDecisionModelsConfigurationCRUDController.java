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

import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.llms.abstraction.layer.model.GBaseDecisionModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseDecisionModelConfig;
import ai.gebo.llms.abstraction.layer.services.IGDecisionModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGDecisionModelRuntimeConfigurationDao;
import ai.gebo.model.OperationStatus;
import ai.gebo.security.services.IGSecurityAuditLoggerService;

/**
 * The CRUD controller base of a provider's decision models, asking the provider's
 * support service for the models it offers.
 */
public class BaseDecisionModelsConfigurationCRUDController<DecisionModelType extends GBaseDecisionModelConfig, ModelChoice extends GBaseDecisionModelChoice>
		extends AbstractDecisionModelsConfigurationCRUDController<DecisionModelType, ModelChoice> {

	protected final IGDecisionModelConfigurationSupportService<ModelChoice, DecisionModelType> decisionModelConfigurationSupportService;

	public BaseDecisionModelsConfigurationCRUDController(IGPersistentObjectManager persistentObjectManager,
			IGDecisionModelRuntimeConfigurationDao modelRuntimeConfigurationDao, Class<DecisionModelType> type,
			IGDecisionModelConfigurationSupportService<ModelChoice, DecisionModelType> decisionModelConfigurationSupportService,
			IGSecurityAuditLoggerService securityAuditLoggerService) {
		super(persistentObjectManager, modelRuntimeConfigurationDao, type, securityAuditLoggerService);
		this.decisionModelConfigurationSupportService = decisionModelConfigurationSupportService;
	}

	@Override
	protected OperationStatus<List<ModelChoice>> getModelChoices(DecisionModelType config) {
		return decisionModelConfigurationSupportService.getModelChoices(config);
	}
}
