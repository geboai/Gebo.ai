/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Gebo.ai comment agent
 * 
 * Interface representing content with methods to manage token and byte counts,
 * and recalculate size based on child content.
 */
public interface IJsonClonable<CurrentClass> {
	static Logger LOGGER = LoggerFactory.getLogger(IJsonClonable.class);
	static ObjectMapper mapper = new ObjectMapper();

	

	default CurrentClass jsonClone() {
		return this.jsonClone((Class<CurrentClass>) getClass());
	}

	default CurrentClass jsonClone(Class<CurrentClass> type) {
		try {
			String data = mapper.writeValueAsString(this);
			CurrentClass result = mapper.readValue(data, type);
			return result;
		} catch (Throwable th) {
			LOGGER.error("Error in clone", th);
			throw new RuntimeException("Error in clone", th);
		}
	}

}