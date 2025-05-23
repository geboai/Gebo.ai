/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.model;

import ai.gebo.model.annotations.GObjectReference;
import ai.gebo.model.base.GBaseObject;

/**
 * AI generated comments
 * Represents a base configuration for a model, storing essential information such as model type, API secret, and more.
 *
 * @param <ModelChoiceType> The type of model choice extending GBaseModelChoice that this configuration can handle.
 */
public class GBaseModelConfig<ModelChoiceType extends GBaseModelChoice> extends GBaseObject {
    /**
     * The code representing the model type. Annotated to reference GModelType.
     */
    @GObjectReference(referencedType = GModelType.class)
    protected String modelTypeCode = null;

    /**
     * Indicates whether this configuration is for the default model.
     */
    protected Boolean defaultModel = null;

    /**
     * Secret code used for API authentication.
     */
    protected String apiSecretCode = null;

    /**
     * The specific model choice selected, represented by a type extending GBaseModelChoice.
     */
    protected ModelChoiceType choosedModel = null;

    /**
     * Base URL for connecting to the model's API.
     */
    protected String baseUrl = null;

    /**
     * Default constructor for GBaseModelConfig.
     */
    public GBaseModelConfig() {

    }

    /**
     * Gets the model type code.
     *
     * @return The code representing the model type.
     */
    public String getModelTypeCode() {
        return modelTypeCode;
    }

    /**
     * Sets the model type code.
     *
     * @param modelTypeCode The code representing the model type to set.
     */
    public void setModelTypeCode(String modelTypeCode) {
        this.modelTypeCode = modelTypeCode;
    }

    /**
     * Gets the API secret code.
     *
     * @return The API secret code.
     */
    public String getApiSecretCode() {
        return apiSecretCode;
    }

    /**
     * Sets the API secret code.
     *
     * @param apiSecretCode The API secret code to set.
     */
    public void setApiSecretCode(String apiSecretCode) {
        this.apiSecretCode = apiSecretCode;
    }

    /**
     * Gets the chosen model.
     *
     * @return The currently chosen model type.
     */
    public ModelChoiceType getChoosedModel() {
        return choosedModel;
    }

    /**
     * Sets the chosen model.
     *
     * @param choosedModel The model choice to set.
     */
    public void setChoosedModel(ModelChoiceType choosedModel) {
        this.choosedModel = choosedModel;
    }

    /**
     * Gets whether the model configuration is set as default.
     *
     * @return True if it is the default model; otherwise, false.
     */
    public Boolean getDefaultModel() {
        return defaultModel;
    }

    /**
     * Sets whether the model configuration should be set as default.
     *
     * @param defaultModel True to set as default; otherwise, false.
     */
    public void setDefaultModel(Boolean defaultModel) {
        this.defaultModel = defaultModel;
    }

    /**
     * Gets the base URL for the model's API.
     *
     * @return The base URL.
     */
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * Sets the base URL for the model's API.
     *
     * @param baseUrl The URL to set as the base URL.
     */
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

}
