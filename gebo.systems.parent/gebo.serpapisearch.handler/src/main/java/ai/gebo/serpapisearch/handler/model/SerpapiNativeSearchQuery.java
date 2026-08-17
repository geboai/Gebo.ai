/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.serpapisearch.handler.model;

import java.util.List;

import ai.gebo.architecture.search.service.INativeQueryObject;
import lombok.Data;

/**
 * Native query the LLM fills for SerpApi, so it can pick which engine to scrape
 * and Google-style options in addition to the query strings.
 */
@Data
public class SerpapiNativeSearchQuery implements INativeQueryObject {
	/** One or more web search queries to run. */
	private List<String> searchedTexts;
	/** Engine to scrape: "google" (default), "bing" or "duckduckgo". */
	private String engine;
	/** Country code (gl), e.g. "us", "it". */
	private String gl;
	/** Interface language (hl), e.g. "en", "it". */
	private String hl;
	/** Google recency filter (tbs): "qdr:d", "qdr:w", "qdr:m" or "qdr:y". */
	private String tbs;

	@Override
	public List<String> relevantKeywords() {
		return searchedTexts;
	}
}
