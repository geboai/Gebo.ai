/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.security.model;

import jakarta.validation.constraints.NotBlank;

/**
 * Gebo.ai comment agent
 *
 * Represents a login request containing the necessary details
 * for user authentication. This class is used to capture the
 * login information such as username and password.
 */
public class LoginRequest {
    
    @jakarta.validation.constraints.NotBlank
    private String username;  // Stores the username for authentication

    @NotBlank
    private String password;  // Stores the password for authentication

    /**
     * Retrieves the username.
     * 
     * @return the username provided for login
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username for this login request.
     * 
     * @param email the username to be set
     */
    public void setUsername(String email) {
        this.username = email;
    }

    /**
     * Retrieves the password.
     * 
     * @return the password provided for login
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password for this login request.
     * 
     * @param password the password to be set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}