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
 * 
 * This class represents an exception thrown when invalid access is attempted
 * within the Gebo REST integration layer. It extends GeboRestIntegrationException
 * to provide specific exception handling related to invalid access scenarios.
 */
public class GeboInvalidAccessException extends GeboRestIntegrationException {

    /**
     * Default constructor for GeboInvalidAccessException.
     * Initializes a new instance of the exception without a message or cause.
     */
    public GeboInvalidAccessException() {

    }

    /**
     * Constructs a new GeboInvalidAccessException with a specified detail message.
     * 
     * @param message the detail message explaining the reason for the exception.
     */
    public GeboInvalidAccessException(String message) {
        super(message);

    }

    /**
     * Constructs a new GeboInvalidAccessException with a specified cause.
     * 
     * @param cause the underlying cause of the exception.
     */
    public GeboInvalidAccessException(Throwable cause) {
        super(cause);

    }

    /**
     * Constructs a new GeboInvalidAccessException with a specified detail message
     * and cause.
     * 
     * @param message the detail message explaining the reason for the exception.
     * @param cause the underlying cause of the exception.
     */
    public GeboInvalidAccessException(String message, Throwable cause) {
        super(message, cause);

    }

    /**
     * Constructs a new GeboInvalidAccessException with a specified detail message,
     * cause, suppression enabled or disabled, and writable stack trace enabled or
     * disabled.
     * 
     * @param message the detail message explaining the reason for the exception.
     * @param cause the underlying cause of the exception.
     * @param enableSuppression whether or not suppression is enabled or disabled.
     * @param writableStackTrace whether or not the stack trace should be writable.
     */
    public GeboInvalidAccessException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);

    }

}