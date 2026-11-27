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

    /**
     * What a model of a provider accepts as input by the library's rules: the input
     * modalities of the first rule covering it that tells them, the installation's
     * library first. It is what the library knows of a model that has no entry of its
     * own, a dated variant ({@code gpt-5.2-2025-12-11}) or a whole family.
     *
     * @param providerId the provider of the model, as its type declares it
     * @param modelId    the model id its requests name
     * @return the modalities, or null when no rule tells them
     */
    public default java.util.List<ai.gebo.llms.abstraction.layer.model.GModelInputModality> findInputModalities(
            String providerId, String modelId) {
        return null;
    }

    /**
     * The api of its provider a model is called through by the library's rules: the api
     * of the first rule covering it that tells one, the installation's library first.
     * It is for the providers serving their models through several apis, a model that
     * takes one only being named by a rule ({@code gpt-5.5-pro*}), its dated variants
     * with it.
     *
     * @param providerId the provider of the model, as its type declares it
     * @param modelId    the model id its requests name
     * @return the api as the provider module spells it, or null when no rule tells it
     */
    public default String findApi(String providerId, String modelId) {
        return null;
    }

    /**
     * The range of the temperature a model of a provider takes by the library's rules:
     * the range of the first rule covering it that tells one, the installation's library
     * first; a rule naming every model of the provider ({@code *}), last in its file,
     * gives the provider's range.
     *
     * @param providerId the provider of the model, as its type declares it
     * @param modelId    the model id its requests name
     * @return the range, or null when no rule tells it
     */
    public default ModelParameterRules.ValueRange findTemperatureRange(String providerId, String modelId) {
        return null;
    }

    /** The range of the top_p a model of a provider takes, as {@link #findTemperatureRange}. */
    public default ModelParameterRules.ValueRange findTopPRange(String providerId, String modelId) {
        return null;
    }

    /**
     * Whether the library knows a model as a chat model: it has an entry of its own
     * saying so, or a rule telling what it accepts as input. The lists of chat models
     * that recognise the other kinds by markers in their ids ("audio", "image") keep
     * such a model: a chat model that listens or draws carries those markers too.
     */
    public default boolean knowsAsChatModel(String providerId, String modelId) {
        if (providerId == null || modelId == null) {
            return false;
        }
        final ModelMetaInfo entry = findByProviderIdAndModelId(providerId, modelId);
        if (entry != null && Boolean.TRUE.equals(entry.getChatModel())) {
            return true;
        }
        final java.util.List<ai.gebo.llms.abstraction.layer.model.GModelInputModality> inputs = findInputModalities(
                providerId, modelId);
        return inputs != null && !inputs.isEmpty();
    }
}
