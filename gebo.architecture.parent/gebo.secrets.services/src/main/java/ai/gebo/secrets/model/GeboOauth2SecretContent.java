/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.secrets.model;

import jakarta.validation.constraints.NotNull;

/**
 * Gebo.ai comment agent
 * 
 * This class represents secret content related to OAuth2 credentials.
 * It extends AbstractGeboSecretContent and provides specific fields 
 * for tenant ID, client ID, and the secret.
 */
public class GeboOauth2SecretContent extends AbstractGeboSecretContent {
    
    // Identifier for the tenant, must not be null.
    @NotNull
    private String tenantId = null;
    
    // Identifier for the client, must not be null.
    @NotNull
    private String clientId = null;
    
    // Secret key associated with OAuth2 credentials, must not be null.
    @NotNull
    private String secret = null;

    /**
     * Default constructor.
     */
    public GeboOauth2SecretContent() {
        
    }

    /**
     * Returns the type of this secret.
     * 
     * @return GeboSecretType.OAUTH2_STANDARD indicating it's an OAuth2 standard type.
     */
    @Override
    public GeboSecretType type() {   
        return GeboSecretType.OAUTH2_STANDARD;
    }

    /**
     * Retrieves the tenant ID.
     * 
     * @return tenantId as a String.
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * Sets the tenant ID.
     * 
     * @param tenantId the tenant ID to set.
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * Retrieves the client ID.
     * 
     * @return clientId as a String.
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * Sets the client ID.
     * 
     * @param clientId the client ID to set.
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**
     * Retrieves the secret.
     * 
     * @return secret as a String.
     */
    public String getSecret() {
        return secret;
    }

    /**
     * Sets the secret.
     * 
     * @param secretId the secret to set.
     */
    public void setSecret(String secretId) {
        this.secret = secretId;
    }
}