/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.services;

/**
 * Gebo.ai comment agent
 * 
 * Custom exception class for handling configuration errors related to the LLM (Language Learning Model) services.
 */
public class LLMConfigException extends Exception {

    /**
     * Default constructor for LLMConfigException.
     * Initializes a new instance of the exception without any message or cause.
     */
    public LLMConfigException() {
        
    }

    /**
     * Constructor for LLMConfigException with a specific error message.
     * 
     * @param message A string representing the detail message of the exception.
     */
    public LLMConfigException(String message) {
        super(message);
        
    }

    /**
     * Constructor for LLMConfigException with a specific cause.
     * 
     * @param cause A Throwable that represents the cause of the exception.
     */
    public LLMConfigException(Throwable cause) {
        super(cause);
        
    }

    /**
     * Constructor for LLMConfigException with a specific error message and cause.
     * 
     * @param message A string representing the detail message of the exception.
     * @param cause A Throwable that represents the cause of the exception.
     */
    public LLMConfigException(String message, Throwable cause) {
        super(message, cause);
        
    }

    /**
     * Constructor for LLMConfigException with error message, cause, 
     * suppression enabled or disabled, and writable stack trace enabled or disabled.
     *
     * @param message A string representing the detail message of the exception.
     * @param cause A Throwable that represents the cause of the exception.
     * @param enableSuppression boolean indicating if suppression should be enabled or not.
     * @param writableStackTrace boolean indicating if the stack trace should be writable.
     */
    public LLMConfigException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        
    }

}