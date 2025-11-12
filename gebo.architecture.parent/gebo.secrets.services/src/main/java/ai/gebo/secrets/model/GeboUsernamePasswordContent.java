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
 * Represents a username and password secret content.
 * This class extends AbstractGeboSecretContent and provides methods
 * to get and set username and password values.
 */
public class GeboUsernamePasswordContent extends AbstractGeboSecretContent {
    
    // Fields to store username and password
	@NotNull
    private String username = null;
	@NotNull
	private String password = null;

    /**
     * Gets the username.
     *
     * @return a String representing the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username a String containing the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password.
     *
     * @return a String representing the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password a String containing the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the secret type for this content.
     * 
     * @return GeboSecretType.USERNAME_PASSWORD indicating the type of secret
     */
    @Override
    public GeboSecretType type() {
        // Return the specific type of secret content
        return GeboSecretType.USERNAME_PASSWORD;
    }
}