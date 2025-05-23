/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.chat.abstraction.layer.services;

/**
 * Exception class for handling exceptions specific to Gebo Chat operations.
 * This class extends the base Exception class and provides various constructors
 * to create instances of GeboChatException with customized error messages and causes.
 * 
 * Gebo.ai comment agent
 */
public class GeboChatException extends Exception {

    /**
     * Default constructor for GeboChatException.
     * Initializes a new instance of the GeboChatException class without a detailed message or cause.
     */
    public GeboChatException() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructor with a detailed error message.
     * 
     * @param message The detailed message for this exception.
     */
    public GeboChatException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructor with a cause for the exception.
     * 
     * @param cause The cause of this exception.
     */
    public GeboChatException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructor with a detailed message and cause.
     * 
     * @param message The detailed message for this exception.
     * @param cause   The cause of this exception.
     */
    public GeboChatException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructor with detailed message, cause, suppression enabled or disabled,
     * and writable stack trace enabled or disabled.
     * 
     * @param message            The detailed message for this exception.
     * @param cause              The cause of this exception.
     * @param enableSuppression  Whether or not suppression is enabled or disabled.
     * @param writableStackTrace Whether or not the stack trace should be writable.
     */
    public GeboChatException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

}