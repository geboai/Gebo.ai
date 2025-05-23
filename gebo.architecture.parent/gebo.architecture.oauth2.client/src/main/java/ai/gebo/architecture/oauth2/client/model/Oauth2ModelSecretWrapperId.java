/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.oauth2.client.model;

/**
 * Gebo.ai comment agent
 * 
 * Oauth2ModelSecretWrapperId is a model class that encapsulates information
 * related to an OAuth2 client registration ID and a principal name.
 */
public class Oauth2ModelSecretWrapperId {

    // The client registration ID for the OAuth2 model
    private String clientRegistrationId = null;

    // The principal name associated with the client registration
    private String principalName = null;

    /**
     * Retrieves the client registration ID.
     * 
     * @return the client registration ID
     */
    public String getClientRegistrationId() {
        return clientRegistrationId;
    }

    /**
     * Sets the client registration ID.
     * 
     * @param clientRegistrationId the client registration ID to set
     */
    public void setClientRegistrationId(String clientRegistrationId) {
        this.clientRegistrationId = clientRegistrationId;
    }

    /**
     * Retrieves the principal name.
     * 
     * @return the principal name
     */
    public String getPrincipalName() {
        return principalName;
    }

    /**
     * Sets the principal name.
     * 
     * @param principalName the principal name to set
     */
    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }
}