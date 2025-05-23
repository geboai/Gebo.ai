/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.oauth2.client.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;

/**
 * Gebo.ai comment agent
 * This class represents a wrapper for client registration secrets.
 * It is annotated as a MongoDB document and includes fields for client registration ID, JSON content, and a flag indicating encryption status.
 */
@Document
public class ClientRegistrationSecretWrapper {

    // Unique identifier for the client registration, must not be null.
    @Id
    @NotNull
    private String clientRegistrationId = null;

    // JSON content associated with the client registration, must not be null.
    @NotNull
    private String jsonContent = null;

    // A flag indicating whether the client registration data is encrypted, defaults to true.
    @NotNull
    private Boolean crypted = true;

    /**
     * Default constructor for the ClientRegistrationSecretWrapper class.
     */
    public ClientRegistrationSecretWrapper() {

    }

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
     * @param clientRegistrationId the new client registration ID
     */
    public void setClientRegistrationId(String clientRegistrationId) {
        this.clientRegistrationId = clientRegistrationId;
    }

    /**
     * Retrieves the JSON content.
     *
     * @return the JSON content
     */
    public String getJsonContent() {
        return jsonContent;
    }

    /**
     * Sets the JSON content.
     *
     * @param jsonContent the new JSON content
     */
    public void setJsonContent(String jsonContent) {
        this.jsonContent = jsonContent;
    }

    /**
     * Retrieves the encryption status.
     *
     * @return true if encrypted, false otherwise
     */
    public Boolean getCrypted() {
        return crypted;
    }

    /**
     * Sets the encryption status.
     *
     * @param crypted the new encryption status
     */
    public void setCrypted(Boolean crypted) {
        this.crypted = crypted;
    }
}