/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.models.metainfos;

/**
 * Gebo.ai comment agent
 * 
 * Interface for accessing model metadata information.
 * Defines methods to find model metadata using model IDs
 * and provider IDs.
 */
public interface IGModelsLibraryDao {

    /**
     * Finds a model's metadata by its unique model ID.
     *
     * @param modelId The unique identifier of the model.
     * @return ModelMetaInfo object containing metadata about the model.
     */
    public ModelMetaInfo findByModelId(String modelId);

    /**
     * Finds a model's metadata by its provider ID and model ID.
     *
     * @param providerId The unique identifier of the provider.
     * @param modelId The unique identifier of the model.
     * @return ModelMetaInfo object containing metadata about the model.
     */
    public ModelMetaInfo findByProviderIdAndModelId(String providerId, String modelId);

    /**
     * The parameter rules covering a model of a provider: the first rule of the
     * installation's library covering it, else the first one of the bundled library.
     * Rules are matched by provider and model only, never by model id alone, since
     * one model id served by two providers takes different parameters.
     *
     * @param providerId the provider of the model, as its type declares it
     * @param modelId    the model id its requests name
     * @return the rules, or null when none covers the model
     */
    public default ModelParameterRules findParameterRules(String providerId, String modelId) {
        return null;
    }
}
