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
import java.util.function.Function;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelChoice;
import ai.gebo.llms.models.metainfos.ModelMetaInfo;

/**
 * Interface for enriching model choice metadata information.
 * This service provides methods to enrich metadata for chat and embedding models.
 * AI generated comments
 */
public interface IGModelChoiceMetaInfoEnricherService {

    /**
     * Enriches the metadata information for a single chat model choice.
     *
     * @param providerId The ID of the provider.
     * @param choice The chat model choice to enrich.
     * @param defaultMetainfoFactory A factory function to generate default metadata info for the model choice.
     * @param <ModelChoiceType> The type of model choice, extending GBaseChatModelChoice.
     */
    public <ModelChoiceType extends GBaseChatModelChoice> void enrichChatModelMetaInfos(String providerId,
            ModelChoiceType choice, Function<ModelChoiceType, ModelMetaInfo> defaultMetainfoFactory);

    /**
     * Enriches the metadata information for a list of chat model choices.
     *
     * @param providerId The ID of the provider.
     * @param choices A list of chat model choices to enrich.
     * @param defaultMetainfoFactory A factory function to generate default metadata info for each model choice.
     * @param <ModelChoiceType> The type of model choice, extending GBaseChatModelChoice.
     */
    public default <ModelChoiceType extends GBaseChatModelChoice> void enrichChatModelMetaInfos(String providerId,
            List<ModelChoiceType> choices, Function<ModelChoiceType, ModelMetaInfo> defaultMetainfoFactory) {
        // Iterate through each model choice and enrich its metadata information.
        for (ModelChoiceType modelChoiceType : choices) {
            enrichChatModelMetaInfos(providerId, modelChoiceType, defaultMetainfoFactory);
        }
    }

    /**
     * Enriches the metadata information for a single embedding model choice.
     *
     * @param providerId The ID of the provider.
     * @param choice The embedding model choice to enrich.
     * @param defaultMetainfoFactory A factory function to generate default metadata info for the model choice.
     * @param <ModelChoiceType> The type of model choice, extending GBaseEmbeddingModelChoice.
     */
    public <ModelChoiceType extends GBaseEmbeddingModelChoice> void enrichEmbeddingModelMetaInfos(String providerId,
            ModelChoiceType choice, Function<ModelChoiceType, ModelMetaInfo> defaultMetainfoFactory);

    /**
     * Enriches the metadata information for a list of embedding model choices.
     *
     * @param providerId The ID of the provider.
     * @param choices A list of embedding model choices to enrich.
     * @param defaultMetainfoFactory A factory function to generate default metadata info for each model choice.
     * @param <ModelChoiceType> The type of model choice, extending GBaseEmbeddingModelChoice.
     */
    public default <ModelChoiceType extends GBaseEmbeddingModelChoice> void enrichEmbeddingModelMetaInfos(
            String providerId, List<ModelChoiceType> choices,
            Function<ModelChoiceType, ModelMetaInfo> defaultMetainfoFactory) {
        // Iterate through each model choice and enrich its metadata information.
        for (ModelChoiceType modelChoiceType : choices) {
            enrichEmbeddingModelMetaInfos(providerId, modelChoiceType, defaultMetainfoFactory);
        }
    }

}