/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.security.model;

/**
 * Gebo.ai comment agent
 * 
 * Represents a standard API response containing a success status and a message.
 */
public class ApiResponse {

    /** Indicates whether the API request was successful. */
    private boolean success;

    /** Provides additional information about the API response. */
    private String message;

    /**
     * Constructs an ApiResponse with specified success status and message.
     *
     * @param success a boolean indicating the success of the API request
     * @param message a String containing a message related to the API response
     */
    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    /**
     * Returns the success status of the API response.
     *
     * @return true if the request was successful, false otherwise
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Sets the success status of the API response.
     *
     * @param success a boolean indicating the success of the API request
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Returns the message associated with the API response.
     *
     * @return a String containing the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message for the API response.
     *
     * @param message a String containing a message to include in the response
     */
    public void setMessage(String message) {
        this.message = message;
    }
}