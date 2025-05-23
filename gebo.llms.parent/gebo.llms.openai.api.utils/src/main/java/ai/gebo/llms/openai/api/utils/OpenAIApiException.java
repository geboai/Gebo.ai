/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.openai.api.utils;

/**
 * AI generated comments
 * Custom exception class for handling OpenAI API related errors.
 * This exception is thrown when issues occur while interacting with OpenAI's API,
 * such as authentication failures, rate limits, or invalid requests.
 */
public class OpenAIApiException extends Exception {

    /**
     * Constructs a new OpenAIApiException with no specified detail message.
     */
	public OpenAIApiException() {
		
	}

    /**
     * Constructs a new OpenAIApiException with the specified detail message.
     * 
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
	public OpenAIApiException(String message) {
		super(message);
		
	}

    /**
     * Constructs a new OpenAIApiException with the specified cause and a detail
     * message of (cause==null ? null : cause.toString()).
     * 
     * @param cause the cause (which is saved for later retrieval by the getCause() method)
     */
	public OpenAIApiException(Throwable cause) {
		super(cause);
		
	}

    /**
     * Constructs a new OpenAIApiException with the specified detail message and cause.
     * 
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     * @param cause the cause (which is saved for later retrieval by the getCause() method)
     */
	public OpenAIApiException(String message, Throwable cause) {
		super(message, cause);
		
	}

    /**
     * Constructs a new OpenAIApiException with the specified detail message,
     * cause, suppression enabled or disabled, and writable stack trace enabled or disabled.
     * 
     * @param message the detail message
     * @param cause the cause
     * @param enableSuppression whether suppression is enabled or disabled
     * @param writableStackTrace whether the stack trace should be writable
     */
	public OpenAIApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

}