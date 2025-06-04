/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
package ai.gebo.security.model.oauth2;

/**
 * AI generated comments
 *
 * This class represents a custom exception for OAuth2 operations in the Gebo system.
 * It extends the standard Java Exception class and provides various constructors
 * for creating exceptions with specific messages and causes.
 */
public class GeboOauth2Exception extends Exception {

    /**
     * Default constructor for GeboOauth2Exception.
     * Initializes a new instance of the exception without a message or cause.
     */
    public GeboOauth2Exception() {

    }

    /**
     * Constructs a new GeboOauth2Exception with the specified detail message.
     *
     * @param message The detail message, saved for later retrieval by the getMessage() method.
     */
    public GeboOauth2Exception(String message) {
        super(message);

    }

    /**
     * Constructs a new GeboOauth2Exception with the specified cause.
     *
     * @param cause The cause (which is saved for later retrieval by the getCause() method).
     */
    public GeboOauth2Exception(Throwable cause) {
        super(cause);

    }

    /**
     * Constructs a new GeboOauth2Exception with the specified detail message and cause.
     *
     * @param message The detail message.
     * @param cause   The cause of the exception.
     */
    public GeboOauth2Exception(String message, Throwable cause) {
        super(message, cause);

    }

    /**
     * Constructs a new GeboOauth2Exception with the specified detail message,
     * cause, suppression enabled or disabled, and writable stack trace enabled or disabled.
     *
     * @param message            The detail message.
     * @param cause              The cause of the exception.
     * @param enableSuppression  Whether or not suppression is enabled or disabled.
     * @param writableStackTrace Whether or not the stack trace should be writable.
     */
    public GeboOauth2Exception(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);

    }
}