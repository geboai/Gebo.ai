/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.oauth2.client.model;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import ai.gebo.architecture.oauth2.client.model.clientregistration.GClientRegistration;

/**
 * Gebo.ai comment agent
 * 
 * This class holds a client registration instance. It serves as a container for
 * an OAuth2 client registration, specifically for instances of 
 * {@link GClientRegistration}.
 */
public class ClientRegistrationHolder /* extends PolimorphicJsonType */ {
    
    // Instance variable to store a client registration of type GClientRegistration
    private GClientRegistration clientRegistration = null;

    /**
     * Default constructor for ClientRegistrationHolder. Initializes the holder
     * without setting a client registration.
     */
    public ClientRegistrationHolder() {

    }

    /**
     * Retrieves the stored client registration instance.
     * 
     * @return the client registration
     */
    public GClientRegistration getClientRegistration() {
        return clientRegistration;
    }

    /**
     * Sets the client registration instance to the provided registration.
     * 
     * @param clientRegistration the client registration to set
     */
    public void setClientRegistration(GClientRegistration clientRegistration) {
        this.clientRegistration = clientRegistration;
    }

}