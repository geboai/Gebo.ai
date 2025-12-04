/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.googlesearch.handler.model;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * AI generated comments
 * 
 * This class represents a simple web search request model that contains the
 * search query parameter and additional query parameters that can be included
 * in a web search request. It provides getters and setters for accessing and
 * modifying these parameters.
 */
@Data
public class SimpleWebSearchRequest {
	

	/**
	 * The main search query parameter. This field is marked as not null, indicating
	 * it's required.
	 */
	@NotNull
	private String searchTextQParam = null;

}