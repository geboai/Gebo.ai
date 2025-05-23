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
 * This exception is thrown when an unmanageable redirection is encountered
 * in the REST integration layer. It extends the GeboRestIntegrationException
 * to handle specific cases of redirection that cannot be dealt with 
 * automatically.
 */
public class GeboUnmanageableRedirectionException extends GeboRestIntegrationException {

    /**
     * Constructs a new GeboUnmanageableRedirectionException with no
     * detail message or cause.
     */
    public GeboUnmanageableRedirectionException() {
        
    }

    /**
     * Constructs a new GeboUnmanageableRedirectionException with the
     * specified detail message.
     *
     * @param message the detail message
     */
    public GeboUnmanageableRedirectionException(String message) {
        super(message);
        
    }

    /**
     * Constructs a new GeboUnmanageableRedirectionException with the
     * specified cause.
     *
     * @param cause the cause of the exception
     */
    public GeboUnmanageableRedirectionException(Throwable cause) {
        super(cause);
        
    }

    /**
     * Constructs a new GeboUnmanageableRedirectionException with the
     * specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause of the exception
     */
    public GeboUnmanageableRedirectionException(String message, Throwable cause) {
        super(message, cause);
        
    }

    /**
     * Constructs a new GeboUnmanageableRedirectionException with the
     * specified detail message, cause, suppression enabled or disabled,
     * and writable stack trace enabled or disabled.
     *
     * @param message            the detail message
     * @param cause              the cause of the exception
     * @param enableSuppression  whether or not suppression is enabled
     * @param writableStackTrace whether or not the stack trace should be writable
     */
    public GeboUnmanageableRedirectionException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        
    }

}