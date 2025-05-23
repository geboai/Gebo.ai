/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.chat.client.rest.controllers;

import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * AI generated comments
 * 
 * Utility class for handling streaming data operations, particularly focused on
 * serializing objects to JSON strings for streaming purposes.
 */
public class StreamUtil {
	/** Logger instance for this class */
	static Logger LOGGER = LoggerFactory.getLogger(StreamUtil.class);
	
	/** Object mapper for JSON serialization */
	static private ObjectMapper mapper = new ObjectMapper();
	
	/**
	 * Function that converts any object to its JSON string representation.
	 * Replaces newlines with spaces to ensure proper streaming format.
	 * Returns an empty string if serialization fails.
	 */
	static Function<Object, String> mappingFunction = x -> {
		try {
			String mappedValue = mapper.writeValueAsString(x);
			// Replace newlines with spaces for proper streaming
			mappedValue = mappedValue.replace("\n", " ");
			return mappedValue;
		} catch (Throwable e) {
			LOGGER.error("Error in streaming objects", e);
			return "";
		}
	};

}