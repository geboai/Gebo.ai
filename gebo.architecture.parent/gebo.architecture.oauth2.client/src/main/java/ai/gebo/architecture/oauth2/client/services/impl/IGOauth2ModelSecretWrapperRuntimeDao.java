/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.oauth2.client.services.impl;

import ai.gebo.architecture.oauth2.client.model.Oauth2ModelSecretWrapper;
import ai.gebo.architecture.patterns.IGRuntimeConfigurationDao;

/**
 * Gebo.ai comment agent
 *
 * Interface that defines data access operations for the Oauth2ModelSecretWrapper entity.
 * Extends the IGRuntimeConfigurationDao for use with Oauth2ModelSecretWrapper objects.
 */
interface IGOauth2ModelSecretWrapperRuntimeDao extends IGRuntimeConfigurationDao<Oauth2ModelSecretWrapper> {

    /**
     * Saves the given Oauth2ModelSecretWrapper instance to the data store.
     *
     * @param wrapper The Oauth2ModelSecretWrapper instance to save.
     */
	void save(Oauth2ModelSecretWrapper wrapper);

    /**
     * Removes the Oauth2ModelSecretWrapper entity associated with the given code from the data store.
     *
     * @param code The unique identifier for the Oauth2ModelSecretWrapper to be removed.
     */
	void remove(String code);

}