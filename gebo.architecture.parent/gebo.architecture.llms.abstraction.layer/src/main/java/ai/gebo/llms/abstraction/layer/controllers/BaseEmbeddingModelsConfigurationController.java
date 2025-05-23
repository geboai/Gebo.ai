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

import org.springframework.beans.factory.annotation.Autowired;

import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelConfig;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelConfigurationSupportService;
import ai.gebo.model.OperationStatus;

/**
 * Gebo.ai comment agent
 * 
 * Controller class for managing configurations of base embedding models.
 * This class extends the abstract CRUD controller for performing common operations.
 *
 * @param <EmbeddingModelConfigType> the type of the embedding model configuration
 * @param <ModelChoice> the type of model choice
 * @param <IfaceType> the type of interface providing configuration support
 */
public class BaseEmbeddingModelsConfigurationController<EmbeddingModelConfigType extends GBaseEmbeddingModelConfig, ModelChoice extends GBaseEmbeddingModelChoice, IfaceType extends IGEmbeddingModelConfigurationSupportService<ModelChoice, EmbeddingModelConfigType>>
		extends AbstractBaseEmbeddingModelsConfigurationCRUDController<EmbeddingModelConfigType, ModelChoice> {

	// Interface type providing configuration support for embedding models
	@Autowired
	IfaceType ifaceType;

	/**
	 * Constructor for the controller.
	 *
	 * @param type the class type of the embedding model configuration
	 */
	public BaseEmbeddingModelsConfigurationController(Class<EmbeddingModelConfigType> type) {
		super(type);
	}

	/**
	 * Retrieves available model choices based on the provided type.
	 *
	 * @param type the embedding model configuration type
	 * @return an operation status containing a list of model choices
	 */
	@Override
	protected OperationStatus<List<ModelChoice>> getModelChoices(EmbeddingModelConfigType type) {
		return this.ifaceType.getModelChoices(type);
	}
}