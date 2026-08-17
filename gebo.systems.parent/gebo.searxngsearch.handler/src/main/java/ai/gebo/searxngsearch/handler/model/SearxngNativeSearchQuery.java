/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.searxngsearch.handler.model;

import java.util.List;

import ai.gebo.architecture.search.service.INativeQueryObject;
import lombok.Data;

/**
 * Native query the LLM fills for SearXNG, so it can pick SearXNG-specific
 * options in addition to the query strings.
 */
@Data
public class SearxngNativeSearchQuery implements INativeQueryObject {
	/** One or more web search queries to run. */
	private List<String> searchedTexts;
	/** Comma-separated SearXNG categories, e.g. "general", "news", "science". */
	private String categories;
	/** Recency filter: "day", "week", "month" or "year". */
	private String timeRange;
	/** Language code, e.g. "en", "it". */
	private String language;

	@Override
	public List<String> relevantKeywords() {
		return searchedTexts;
	}
}
