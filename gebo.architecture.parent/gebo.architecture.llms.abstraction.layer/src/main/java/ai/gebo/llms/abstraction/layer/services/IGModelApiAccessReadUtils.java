/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.services;

import ai.gebo.llms.abstraction.layer.model.GBaseModelConfig;

/**
 * Gebo.ai comment agent
 * Interface for accessing read utilities specific to GModel API.
 */
public interface IGModelApiAccessReadUtils {

    /**
     * Nested static class to store API key information.
     */
    public static class ApiKeyInfo {
        private final String user, apiKey;

        /**
         * Constructor to initialize ApiKeyInfo with user and apiKey.
         * 
         * @param user the username associated with the API key
         * @param apiKey the API key for accessing the service
         */
        public ApiKeyInfo(String user, String apiKey) {
            this.user = user;
            this.apiKey = apiKey;
        }

        /**
         * Retrieves the username.
         * 
         * @return the username associated with the API key
         */
        public String getUser() {
            return user;
        }

        /**
         * Retrieves the API key.
         * 
         * @return the API key
         */
        public String getApiKey() {
            return apiKey;
        }
    }

    /**
     * Obtains ApiKeyInfo for a given model configuration.
     * 
     * @param modelConfig the configuration object for the model
     * @return ApiKeyInfo containing user and API key information
     * @throws LLMConfigException if there is an error retrieving the API key information
     */
    public ApiKeyInfo getApiKeyInfo(GBaseModelConfig modelConfig) throws LLMConfigException;  
}