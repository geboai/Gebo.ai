/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.llms.abstraction.layer.model.GBaseModelConfig;
import ai.gebo.llms.abstraction.layer.services.IGModelApiAccessReadUtils;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboSecretType;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;

/**
 * Gebo.ai comment agent
 * 
 * Implementation of the {@link IGModelApiAccessReadUtils} interface.
 * This service retrieves API key information from secret management.
 */
@Service
public class GModelApiAccessReadUtilsimpl implements IGModelApiAccessReadUtils {

    // Service for accessing secret management functionalities
    @Autowired
    IGeboSecretsAccessService secretService;

    /**
     * Default constructor for GModelApiAccessReadUtilsimpl.
     */
    public GModelApiAccessReadUtilsimpl() {

    }

    /**
     * Retrieves API key information from secret management based on model configuration.
     * 
     * @param modelConfig the configuration containing the API secret code
     * @return ApiKeyInfo containing user and API key information, or null if not found
     * @throws LLMConfigException if there is an issue accessing the secret management layer
     */
    @Override
    public ApiKeyInfo getApiKeyInfo(GBaseModelConfig modelConfig) throws LLMConfigException {
        // Retrieve the secret code from the model configuration
        String secretCode = modelConfig.getApiSecretCode();
        String user = null, apiKey = null;

        // Proceed only if secret code is not null
        if (secretCode != null) {
            AbstractGeboSecretContent secret;
            try {
                // Attempt to retrieve secret content using the secret code
                secret = secretService.getSecretContentById(secretCode);

                // Check if the secret content is of type TOKEN
                if (secret.type() == GeboSecretType.TOKEN) {
                    GeboTokenContent token = (GeboTokenContent) secret;
                    apiKey = token.getToken();  // Retrieve the token as API key
                    user = token.getUser();     // Retrieve the user associated with the token
                    return new ApiKeyInfo(user, apiKey);
                } else {
                    // Throw exception if the secret is not of type TOKEN
                    throw new LLMConfigException(
                            "Large language models API can work only with an api key of type TOKEN");
                }
            } catch (GeboCryptSecretException e) {
                // Wrap and throw a configuration exception if an underlying exception occurs
                throw new LLMConfigException("Exception accessing underlying secret management layer", e);
            }
        }
        return null;  // Return null if no valid secret code is present
    }

}