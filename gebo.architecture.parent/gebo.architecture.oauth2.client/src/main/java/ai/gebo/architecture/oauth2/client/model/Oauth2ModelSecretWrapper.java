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
 * This class represents a wrapper for an OAuth2 Model Secret.
 * It is intended to be stored in a MongoDB collection.
 */
@Document
public class Oauth2ModelSecretWrapper {

    @Id
    @NotNull
    private Oauth2ModelSecretWrapperId id = null;

    @NotNull
    private String jsonContent = null;

    @NotNull
    private Boolean crypted = true;

    /**
     * Returns the ID of the OAuth2 Model Secret Wrapper.
     * 
     * @return id - the ID of the secret wrapper.
     */
    public Oauth2ModelSecretWrapperId getId() {
        return id;
    }

    /**
     * Sets the ID for the OAuth2 Model Secret Wrapper.
     * 
     * @param id - the ID to set for the secret wrapper.
     */
    public void setId(Oauth2ModelSecretWrapperId id) {
        this.id = id;
    }

    /**
     * Returns the JSON content stored in the secret wrapper.
     * 
     * @return jsonContent - the JSON content as String.
     */
    public String getJsonContent() {
        return jsonContent;
    }

    /**
     * Sets the JSON content for the secret wrapper.
     * 
     * @param jsonContent - the JSON content to set.
     */
    public void setJsonContent(String jsonContent) {
        this.jsonContent = jsonContent;
    }

    /**
     * Returns whether the JSON content is encrypted or not.
     * 
     * @return crypted - true if the content is encrypted, false otherwise.
     */
    public Boolean getCrypted() {
        return crypted;
    }

    /**
     * Sets the encryption status for the JSON content.
     * 
     * @param crypted - the encryption status to set.
     */
    public void setCrypted(Boolean crypted) {
        this.crypted = crypted;
    }
}