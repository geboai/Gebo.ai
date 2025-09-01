/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.fastsetup.llms.model;

import jakarta.validation.constraints.NotNull;

/**
 * AI generated comments
 *
 * The FastLLMSSetupData class represents the setup data required for configuring a connection
 * to a Language Model Service (LLMS). It encapsulates the necessary information including
 * user, provider, and API key, which are all essential for authentication and interaction
 * with the LLMS.
 */
public class FastLLMSSetupData {

    // Annotated with @NotNull to ensure that the user field is never null
    @NotNull
    private String user = null;

    // Annotated with @NotNull to ensure that the provider field is never null
    @NotNull
    private String provider = null;

    // Annotated with @NotNull to ensure that the apiKey field is never null
    @NotNull
    private String apiKey = null;

    /**
     * Default constructor for creating an instance of FastLLMSSetupData.
     * Initializes the fields with null values and requires setting them via
     * their respective setters.
     */
    public FastLLMSSetupData() {
    }

    /**
     * Retrieves the provider string that specifies which language model service
     * the setup data refers to.
     *
     * @return the provider for the LLMS
     */
    public String getProvider() {
        return provider;
    }

    /**
     * Sets the provider information. This specifies which language model
     * service the setup will use.
     *
     * @param provider the provider for the LLMS
     */
    public void setProvider(String provider) {
        this.provider = provider;
    }

    /**
     * Retrieves the API key necessary for authenticating with the language
     * model service provider.
     *
     * @return the API key for the LLMS
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * Sets the API key for the language model service provider. This key is critical
     * for authentication and must be provided.
     *
     * @param apiKey the API key for the LLMS
     */
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * Retrieves the user identifier necessary for setting up the language
     * model service.
     *
     * @return the user for the LLMS setup
     */
    public String getUser() {
        return user;
    }

    /**
     * Sets the user identifier for whom the setup is performed. This is essential
     * for logging and management purposes.
     *
     * @param user the user identifier for the LLMS setup
     */
    public void setUser(String user) {
        this.user = user;
    }
}