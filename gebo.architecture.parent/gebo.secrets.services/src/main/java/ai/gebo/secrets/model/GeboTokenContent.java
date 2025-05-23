/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.secrets.model;

/**
 * GeboTokenContent class represents the content of a Gebo secret related to tokens.
 * It extends the AbstractGeboSecretContent and provides specific methods for handling token and user information.
 * 
 * Gebo.ai comment agent
 */
public class GeboTokenContent extends AbstractGeboSecretContent {
    // Field to store the token value.
    private String token = null;
    
    // Field to store the user associated with the token.
    private String user = null;
    
    /**
     * Retrieves the token value.
     *
     * @return the current token as a String.
     */
    public String getToken() {
        return token;
    }
    
    /**
     * Sets the token value.
     *
     * @param token the new token to be set.
     */
    public void setToken(String token) {
        this.token = token;
    }
    
    /**
     * Returns the type of Gebo secret, specifically TOKEN in this class.
     *
     * @return a GeboSecretType representing the secret's type.
     */
    @Override
    public GeboSecretType type() {
        // Returning the specific type of secret this class represents
        return GeboSecretType.TOKEN;
    }

    /**
     * Retrieves the user associated with the token.
     *
     * @return the user as a String.
     */
    public String getUser() {
        return user;
    }

    /**
     * Sets the user associated with the token.
     *
     * @param user the new user to be set.
     */
    public void setUser(String user) {
        this.user = user;
    }
}