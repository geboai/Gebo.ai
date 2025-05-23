/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.crypting.services;

/**
 * Gebo.ai comment agent
 *
 * This class represents a custom exception for cryptographic operations 
 * in the Gebo application. It extends the standard {@link Exception} class
 * to provide additional context or handle specific cases related to 
 * encryption and decryption failures.
 */
public class GeboCryptSecretException extends Exception {

    /**
     * Default constructor for GeboCryptSecretException.
     * It creates a new instance without a detail message or cause.
     */
    public GeboCryptSecretException() {
        // Default constructor does nothing extra
    }

    /**
     * Constructs a new GeboCryptSecretException with the specified detail message.
     *
     * @param message the detail message, provides more information about the exception cause.
     */
    public GeboCryptSecretException(String message) {
        super(message);
        // Pass the message to the superclass constructor
    }

    /**
     * Constructs a new GeboCryptSecretException with the specified cause.
     *
     * @param cause the cause of the exception, can be retrieved later by the getCause method.
     */
    public GeboCryptSecretException(Throwable cause) {
        super(cause);
        // Pass the cause to the superclass constructor
    }

    /**
     * Constructs a new GeboCryptSecretException with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause   the cause of the exception.
     */
    public GeboCryptSecretException(String message, Throwable cause) {
        super(message, cause);
        // Pass both message and cause to the superclass constructor
    }

    /**
     * Constructs a new GeboCryptSecretException with the specified detail message, cause,
     * whether or not suppression is enabled, and whether or not the stack trace should
     * be writable.
     *
     * @param message            the detail message.
     * @param cause              the cause of the exception.
     * @param enableSuppression  whether or not suppression is enabled or disabled.
     * @param writableStackTrace whether or not the stack trace should be writable.
     */
    public GeboCryptSecretException(String message, Throwable cause, boolean enableSuppression,
                                    boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        // Pass all parameters to the superclass constructor
    }

}