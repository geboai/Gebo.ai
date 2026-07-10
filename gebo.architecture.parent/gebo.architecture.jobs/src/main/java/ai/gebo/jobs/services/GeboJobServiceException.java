/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.jobs.services;

/**
 * Exception class for handling specific errors related to Gebo Job Service operations.
 * This class extends the standard Java Exception class to provide specialized
 * exception handling for job service related failures.
 * 
 * AI generated comments
 */
public class GeboJobServiceException extends Exception {

	/**
	 * Constructs a new GeboJobServiceException with no message or cause.
	 */
	public GeboJobServiceException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructs a new GeboJobServiceException with the specified detail message.
	 * 
	 * @param message The detail message explaining the reason for the exception
	 */
	public GeboJobServiceException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructs a new GeboJobServiceException with the specified cause.
	 * 
	 * @param cause The underlying throwable that caused this exception
	 */
	public GeboJobServiceException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructs a new GeboJobServiceException with the specified detail message and cause.
	 * 
	 * @param message The detail message explaining the reason for the exception
	 * @param cause The underlying throwable that caused this exception
	 */
	public GeboJobServiceException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructs a new GeboJobServiceException with the specified detail message, cause,
	 * suppression and stack trace writability settings.
	 * 
	 * @param message The detail message explaining the reason for the exception
	 * @param cause The underlying throwable that caused this exception
	 * @param enableSuppression Whether suppression is enabled or disabled
	 * @param writableStackTrace Whether the stack trace should be writable
	 */
	public GeboJobServiceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}