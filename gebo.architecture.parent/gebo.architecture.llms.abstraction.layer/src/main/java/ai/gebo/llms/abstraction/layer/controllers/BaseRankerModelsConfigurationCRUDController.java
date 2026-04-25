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
import ai.gebo.llms.abstraction.layer.model.GBaseRankerModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseRankerModelConfig;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableRankerModel;
import ai.gebo.llms.abstraction.layer.services.IGRankerModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGRankerModelRuntimeConfigurationDao;
import ai.gebo.model.OperationStatus;
import lombok.AllArgsConstructor;

/**
 * Abstract base class for CRUD operations on ranker model configurations.
 * Provides common functionality for inserting, updating, deleting, and finding
 * chat model configurations.
 *
 * @param <RankerModelType> The type of chat model configuration.
 * @param <ModelChoice>     The type of model choice.
 * 
 *                          AI generated comments
 */

public class BaseRankerModelsConfigurationCRUDController<RankerModelType extends GBaseRankerModelConfig, ModelChoice extends GBaseRankerModelChoice>
		extends AbstractRankerModelsConfigurationCRUDController<RankerModelType, ModelChoice> {
	public BaseRankerModelsConfigurationCRUDController(IGPersistentObjectManager persistentObjectManager,
			IGRankerModelRuntimeConfigurationDao modelRuntimeConfigurationDao, Class<RankerModelType> type,
			IGRankerModelConfigurationSupportService<ModelChoice, RankerModelType> rankerModelConfigurationSupportService) {
		super(persistentObjectManager, modelRuntimeConfigurationDao, type);
		this.rankerModelConfigurationSupportService = rankerModelConfigurationSupportService;

	}
	protected final IGRankerModelConfigurationSupportService<ModelChoice, RankerModelType> rankerModelConfigurationSupportService;

	/**
	 * Abstract method to obtain model choices based on a chat model configuration.
	 * Must be implemented by subclasses.
	 *
	 * @param type The chat model configuration.
	 * @return The operation status containing the list of model choices or error.
	 */
	@Override
	protected OperationStatus<List<ModelChoice>> getModelChoices(RankerModelType config) {
		return rankerModelConfigurationSupportService.getModelChoices(config);
	}
}