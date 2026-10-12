/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.system.ingestion;

/**
 * AI generated comments
 * 
 * Custom exception class specific to the Gebo ingestion subsystem.
 * This exception is thrown when errors occur during data ingestion processes.
 * It extends the standard Java Exception class to provide specialized error handling.
 */
public class GeboIngestionException extends Exception {

	/**
	 * Constructs a new GeboIngestionException with no specified detail message.
	 */
	public GeboIngestionException() {
		
	}

	/**
	 * Constructs a new GeboIngestionException with the specified detail message.
	 * 
	 * @param message The detail message explaining the reason for the exception
	 */
	public GeboIngestionException(String message) {
		super(message);
		
	}

	/**
	 * Constructs a new GeboIngestionException with the specified cause.
	 * 
	 * @param cause The cause of the exception (a throwable that caused this exception)
	 */
	public GeboIngestionException(Throwable cause) {
		super(cause);
		
	}

	/**
	 * Constructs a new GeboIngestionException with the specified detail message and cause.
	 * 
	 * @param message The detail message explaining the reason for the exception
	 * @param cause The cause of the exception (a throwable that caused this exception)
	 */
	public GeboIngestionException(String message, Throwable cause) {
		super(message, cause);
		
	}

	/**
	 * Constructs a new GeboIngestionException with the specified detail message,
	 * cause, suppression enabled or disabled, and writable stack trace enabled or disabled.
	 * 
	 * @param message The detail message explaining the reason for the exception
	 * @param cause The cause of the exception (a throwable that caused this exception)
	 * @param enableSuppression Whether suppression is enabled or disabled
	 * @param writableStackTrace Whether the stack trace should be writable
	 */
	public GeboIngestionException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

}