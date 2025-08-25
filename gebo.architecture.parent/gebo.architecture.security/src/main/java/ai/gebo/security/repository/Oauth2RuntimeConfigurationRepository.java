/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
package ai.gebo.security.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import ai.gebo.security.model.AuthProvider;
import ai.gebo.security.model.oauth2.Oauth2ConfigurationType;
import ai.gebo.security.model.oauth2.Oauth2RuntimeConfiguration;
import jakarta.validation.constraints.NotNull;

/**
 * AI generated comments
 * Repository interface for `Oauth2RuntimeConfiguration` entities.
 * Extends the MongoRepository to provide CRUD operations on the MongoDB database.
 */
public interface Oauth2RuntimeConfigurationRepository extends MongoRepository<Oauth2RuntimeConfiguration, String> {

    /**
     * Finds all configurations associated with the specified authentication provider.
     *
     * @param provider the authentication provider, must not be null.
     * @return a list of Oauth2RuntimeConfiguration entities that match the specified provider.
     */
    public List<Oauth2RuntimeConfiguration> findByProvider(@NotNull AuthProvider provider);

    /**
     * Finds configurations by authentication provider and configuration type.
     * This method will return all `Oauth2RuntimeConfiguration` objects that
     * contain the specified configuration type and are associated with the given provider.
     *
     * @param provider the authentication provider object.
     * @param type the configuration type to search for.
     * @return a list of Oauth2RuntimeConfiguration entities that match the specified provider and include the configuration type.
     */
    public List<Oauth2RuntimeConfiguration> findByProviderAndConfigurationType(AuthProvider provider,
            Oauth2ConfigurationType type);

	public List<Oauth2RuntimeConfiguration> findByConfigurationType(Oauth2ConfigurationType authentication);

}