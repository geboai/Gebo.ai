/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.secrets.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * AI generated comments
 * Represents a secret in the Gebo system, which is stored in a MongoDB collection.
 * This class is annotated to indicate that it is a document stored in the database.
 */
@Document
public class GeboSecret {

    // Unique identifier for this secret
    @Id
    private String code = null;
    
    // The code for the context in which this secret is used
    private String contextCode = null;
    
    // Type of the secret, specified by a GeboSecretType enum
    private GeboSecretType secretType = null;

    // Description of the secret
    private String description = null;

    // URI that represents the type of the secret
    private String secretTypeUri = null;

    // The content of the secret
    private String secretContent = null;

    // Date when the secret was created
    private Date creationDate = null, expirationDate = null; // Expiration date of the secret

    /**
     * Gets the unique code of the secret.
     * @return the code of the secret
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the unique code for the secret.
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gets the description of the secret.
     * @return the description of the secret
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description for the secret.
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the content of the secret.
     * @return the content of the secret
     */
    public String getSecretContent() {
        return secretContent;
    }

    /**
     * Sets the content of the secret.
     * @param secretContent the content to set
     */
    public void setSecretContent(String secretContent) {
        this.secretContent = secretContent;
    }

    /**
     * Gets the URI representing the secret type.
     * @return the secret type URI
     */
    public String getSecretTypeUri() {
        return secretTypeUri;
    }

    /**
     * Sets the URI for the secret type.
     * @param secretTypeUri the URI to set
     */
    public void setSecretTypeUri(String secretTypeUri) {
        this.secretTypeUri = secretTypeUri;
    }

    /**
     * Gets the creation date of the secret.
     * @return the creation date
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Sets the creation date of the secret.
     * @param creationDate the date to set
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Gets the expiration date of the secret.
     * @return the expiration date
     */
    public Date getExpirationDate() {
        return expirationDate;
    }

    /**
     * Sets the expiration date of the secret.
     * @param expirationDate the date to set
     */
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    /**
     * Gets the type of the secret.
     * @return the secret type
     */
    public GeboSecretType getSecretType() {
        return secretType;
    }

    /**
     * Sets the type of the secret.
     * @param secretType the secret type to set
     */
    public void setSecretType(GeboSecretType secretType) {
        this.secretType = secretType;
    }

    /**
     * Gets the context code of the secret.
     * @return the context code
     */
    public String getContextCode() {
        return contextCode;
    }

    /**
     * Sets the context code for the secret.
     * @param contextCode the context code to set
     */
    public void setContextCode(String contextCode) {
        this.contextCode = contextCode;
    }
}