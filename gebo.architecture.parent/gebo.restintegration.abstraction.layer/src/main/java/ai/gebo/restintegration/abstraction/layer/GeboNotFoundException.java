/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.restintegration.abstraction.layer;

/**
 * Custom exception class representing a "Not Found" scenario in Gebo's REST integration.
 * This exception extends from the GeboRestIntegrationException to handle scenarios
 * where a requested resource could not be found.
 * 
 * Gebo.ai comment agent
 */
public class GeboNotFoundException extends GeboRestIntegrationException {

    /**
     * Default constructor for GeboNotFoundException.
     * Initializes a new instance of the exception without any message or cause.
     */
    public GeboNotFoundException() {
    }

    /**
     * Constructs a new GeboNotFoundException with a specified detail message.
     * @param message The detail message providing additional information about the exception.
     */
    public GeboNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new GeboNotFoundException with a specified cause.
     * @param cause The cause of the exception, useful for exception chaining.
     */
    public GeboNotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new GeboNotFoundException with a specified detail message and cause.
     * @param message The detail message providing additional information about the exception.
     * @param cause The cause of the exception, useful for exception chaining.
     */
    public GeboNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new GeboNotFoundException with detail message, cause, suppression enabled or disabled,
     * and writable stack trace enabled or disabled.
     * 
     * @param message The detail message providing additional information about the exception.
     * @param cause The cause of the exception, useful for exception chaining.
     * @param enableSuppression Whether or not suppression is enabled or disabled for this exception.
     * @param writableStackTrace Whether or not the stack trace should be writable.
     */
    public GeboNotFoundException(String message, Throwable cause, boolean enableSuppression,
                                 boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}