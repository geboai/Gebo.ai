/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.contenthandling.interfaces;

/**
 * Gebo.ai comment agent
 *
 * This class represents a custom exception used in the Gebo content handling system.
 * It extends from the base Exception class to provide specific error handling
 * functionality when dealing with system-related exceptions within the content
 * handling architecture.
 */
public class GeboContentHandlerSystemException extends Exception {

    /**
     * Default no-argument constructor for the exception.
     * Initializes a new instance of GeboContentHandlerSystemException without any detail message or cause.
     */
    public GeboContentHandlerSystemException() {

    }

    /**
     * Constructs a new GeboContentHandlerSystemException with the specified detail message.
     *
     * @param message The detail message explaining the reason for the exception.
     */
    public GeboContentHandlerSystemException(String message) {
        super(message);

    }

    /**
     * Constructs a new GeboContentHandlerSystemException with the specified cause.
     *
     * @param cause The cause (which is saved for later retrieval by the {@link #getCause()} method).
     */
    public GeboContentHandlerSystemException(Throwable cause) {
        super(cause);

    }

    /**
     * Constructs a new GeboContentHandlerSystemException with the specified detail message and cause.
     *
     * @param message The detail message explaining the reason for the exception.
     * @param cause   The cause (which is saved for later retrieval by the {@link #getCause()} method).
     */
    public GeboContentHandlerSystemException(String message, Throwable cause) {
        super(message, cause);

    }

    /**
     * Constructs a new GeboContentHandlerSystemException with the specified detail message,
     * cause, suppression enabled or disabled, and writable stack trace enabled or disabled.
     *
     * @param message            The detail message explaining the reason for the exception.
     * @param cause              The cause (which is saved for later retrieval by the {@link #getCause()} method).
     * @param enableSuppression  Whether or not suppression is enabled or disabled.
     * @param writableStackTrace Whether or not the stack trace should be writable.
     */
    public GeboContentHandlerSystemException(String message, Throwable cause, boolean enableSuppression,
                                             boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);

    }

}