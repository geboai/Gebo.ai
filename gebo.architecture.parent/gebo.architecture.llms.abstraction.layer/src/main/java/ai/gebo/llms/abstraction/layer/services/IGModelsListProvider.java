/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.services;

import java.util.List;

import ai.gebo.llms.abstraction.layer.model.GBaseModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseModelConfig;
import ai.gebo.model.OperationStatus;

/**
 * Gebo.ai comment agent
 * 
 * An interface for providing a list of model choices from a given provider.
 * This interface defines methods to retrieve the identifier of the model
 * provider and to fetch model choices based on specific configurations and
 * parameters.
 */
public interface IGModelsListProvider {
    
    /**
     * Retrieves the unique identifier for the model provider.
     * 
     * @return A string that represents the identifier of this provider.
     */
    public String getId();

    /**
     * Retrieves a list of model choices from the provider based on the provided
     * configuration and API key.
     * 
     * @param <ModelChoice> The type parameter that extends GBaseModelChoice, representing the choice of the model.
     * @param <ModelConfig> The type parameter that extends GBaseModelConfig, representing the configuration of the model.
     * @param providerId    The identifier for the provider from which to fetch the models.
     * @param config        The configuration details specific to the model choice, encapsulated in ModelConfig.
     * @param clearApiKey   The API key to authenticate and authorize the request to the provider.
     * @param choiceType    The class type of ModelChoice, used for type safety and validation.
     * 
     * @return An OperationStatus object containing a list of ModelChoice objects, which indicates the status of the operation.
     */
    public <ModelChoice extends GBaseModelChoice, ModelConfig extends GBaseModelConfig<ModelChoice>> OperationStatus<List<ModelChoice>> geModels(
            String providerId, ModelConfig config, String clearApiKey, Class<ModelChoice> choiceType);

}