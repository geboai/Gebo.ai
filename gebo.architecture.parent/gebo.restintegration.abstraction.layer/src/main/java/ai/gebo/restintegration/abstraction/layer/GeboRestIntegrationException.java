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
 * Gebo.ai comment agent
 * Custom exception class for handling exceptions specific to Gebo REST Integration.
 */
public class GeboRestIntegrationException extends Exception {

    /**
     * Default constructor for GeboRestIntegrationException.
     */
    public GeboRestIntegrationException() {
        
    }

    /**
     * Constructor that accepts a custom error message.
     *
     * @param message the detail message for this exception.
     */
    public GeboRestIntegrationException(String message) {
        super(message);
        
    }

    /**
     * Constructor that accepts a throwable cause for the exception.
     *
     * @param cause the cause of the exception.
     */
    public GeboRestIntegrationException(Throwable cause) {
        super(cause);
        
    }

    /**
     * Constructor that accepts both a custom error message and a throwable cause.
     *
     * @param message the detail message for this exception.
     * @param cause the cause of the exception.
     */
    public GeboRestIntegrationException(String message, Throwable cause) {
        super(message, cause);
        
    }

    /**
     * Advanced constructor for creating an exception with message, cause, suppression enabled or disabled, 
     * and writable stack trace enabled or disabled.
     *
     * @param message the detail message.
     * @param cause the cause of the exception.
     * @param enableSuppression whether or not suppression is enabled or disabled.
     * @param writableStackTrace whether or not the stack trace should be writable.
     */
    public GeboRestIntegrationException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        
    }

}