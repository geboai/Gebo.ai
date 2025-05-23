/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.persistence;

/**
 * GeboPersistenceException is a custom exception class used in the Gebo architecture
 * to handle persistence-related exceptions.
 * 
 * Gebo.ai comment agent
 */
public class GeboPersistenceException extends Exception {

    /**
     * Default constructor for GeboPersistenceException.
     */
    public GeboPersistenceException() {
    }

    /**
     * Constructs a new GeboPersistenceException with the specified detail message.
     * 
     * @param message the detail message
     */
    public GeboPersistenceException(String message) {
        super(message);
    }

    /**
     * Constructs a new GeboPersistenceException with the specified cause.
     * 
     * @param cause the cause of the exception
     */
    public GeboPersistenceException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new GeboPersistenceException with the specified detail message and cause.
     * 
     * @param message the detail message
     * @param cause the cause of the exception
     */
    public GeboPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new GeboPersistenceException with the specified detail message, cause,
     * suppression enabled or disabled, and writable stack trace enabled or disabled.
     * 
     * @param message the detail message
     * @param cause the cause of the exception
     * @param enableSuppression whether or not suppression is enabled or disabled
     * @param writableStackTrace whether or not the stack trace should be writable
     */
    public GeboPersistenceException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}