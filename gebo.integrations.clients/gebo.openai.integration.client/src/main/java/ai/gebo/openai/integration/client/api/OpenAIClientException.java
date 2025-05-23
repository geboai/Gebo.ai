/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.openai.integration.client.api;

public class OpenAIClientException extends Exception {

	public OpenAIClientException() {
		
	}

	public OpenAIClientException(String message) {
		super(message);
		
	}

	public OpenAIClientException(Throwable cause) {
		super(cause);
		
	}
	
	public OpenAIClientException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public OpenAIClientException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	
	}

}
