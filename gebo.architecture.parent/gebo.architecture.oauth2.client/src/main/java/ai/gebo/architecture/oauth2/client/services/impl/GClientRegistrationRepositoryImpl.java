/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.oauth2.client.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Service;

/**
 * Gebo.ai comment agent
 * 
 * A service implementation for managing OAuth2 client registrations. 
 * This class provides a mechanism to retrieve OAuth2 client details based on a registration ID.
 */
@Service
public class GClientRegistrationRepositoryImpl implements ClientRegistrationRepository {

    // Handler for processing OAuth2 client secrets and related data.
    @Autowired
    SecretOauth2DataHandler handler;

    /**
     * Finds a client registration by its registration ID.
     * 
     * @param registrationId a unique identifier for the client registration
     * @return the ClientRegistration object associated with the specified registrationId
     */
    @Override
    public ClientRegistration findByRegistrationId(String registrationId) {
        // Delegate the call to the handler to retrieve the actual client registration details.
        return handler.findByRegistrationId(registrationId);
    }
}