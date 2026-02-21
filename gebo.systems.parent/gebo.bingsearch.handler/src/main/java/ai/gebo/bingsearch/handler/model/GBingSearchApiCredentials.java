/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.bingsearch.handler.model;

import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.model.base.GBaseObject;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * AI generated comments
 * 
 * This class represents Bing Search API credentials for authentication.
 * It extends GBaseObject and is stored as a document in MongoDB.
 * Contains the necessary information to authenticate and use Google's Custom Search API.
 */
@Document
@Data
public class GBingSearchApiCredentials extends GBaseObject {
	/**
	 * The API key/secret code required for authentication with Google Search API.
	 * Must not be null.
	 */
	@NotNull
	private String secretCode = null;
	

}