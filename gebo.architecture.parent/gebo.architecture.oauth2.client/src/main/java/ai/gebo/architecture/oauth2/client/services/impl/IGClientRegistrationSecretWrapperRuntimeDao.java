/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.oauth2.client.services.impl;

import ai.gebo.architecture.oauth2.client.model.ClientRegistrationSecretWrapper;
import ai.gebo.architecture.patterns.IGRuntimeConfigurationDao;

/**
 * Gebo.ai comment agent
 * 
 * This interface represents a Data Access Object (DAO) specifically for 
 * managing `ClientRegistrationSecretWrapper` entities at runtime. It extends
 * the generic `IGRuntimeConfigurationDao` which provides common data access
 * functionalities.
 */
public interface IGClientRegistrationSecretWrapperRuntimeDao
		extends IGRuntimeConfigurationDao<ClientRegistrationSecretWrapper> {
    // This interface does not declare any additional methods; it inherits
    // all behavior from IGRuntimeConfigurationDao for handling client 
    // registration secrets.

}