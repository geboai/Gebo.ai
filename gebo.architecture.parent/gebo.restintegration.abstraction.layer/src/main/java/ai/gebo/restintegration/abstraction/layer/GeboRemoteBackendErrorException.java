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
 * This class represents an exception that is thrown when there is an error 
 * in the remote backend integration. It extends from GeboRestIntegrationException, 
 * making it a specific type of exception for backend errors.
 */
public class GeboRemoteBackendErrorException extends GeboRestIntegrationException {

    /**
     * Default constructor that creates a new instance of GeboRemoteBackendErrorException with no detail message.
     */
    public GeboRemoteBackendErrorException() {
        
    }

    /**
     * Constructs a new exception with the specified detail message.
     * 
     * @param message the detail message which provides more information about the exception.
     */
    public GeboRemoteBackendErrorException(String message) {
        super(message);
        
    }

    /**
     * Constructs a new exception with the specified cause.
     * 
     * @param cause the underlying reason for this exception being thrown.
     */
    public GeboRemoteBackendErrorException(Throwable cause) {
        super(cause);
        
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     * 
     * @param message the detail message that provides more information about the exception.
     * @param cause the underlying reason for this exception being thrown.
     */
    public GeboRemoteBackendErrorException(String message, Throwable cause) {
        super(message, cause);
        
    }

    /**
     * Constructs a new exception with the specified detail message, cause, 
     * suppression enabled or disabled, and writable stack trace enabled or disabled.
     * 
     * @param message the detail message that provides more information about the exception.
     * @param cause the underlying reason for this exception being thrown.
     * @param enableSuppression whether or not suppression is enabled or disabled.
     * @param writableStackTrace whether or not the stack trace should be writable.
     */
    public GeboRemoteBackendErrorException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        
    }

}