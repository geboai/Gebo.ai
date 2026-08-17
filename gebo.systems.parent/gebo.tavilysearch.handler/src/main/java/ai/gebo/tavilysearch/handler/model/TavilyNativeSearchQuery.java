/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.tavilysearch.handler.model;

import java.util.List;

import ai.gebo.architecture.search.service.INativeQueryObject;
import lombok.Data;

/**
 * Native query the LLM fills (via structured output) for Tavily, so it can pick
 * Tavily-specific options in addition to the query strings. Consumed by the
 * deep-search planner and the native searcher agent.
 */
@Data
public class TavilyNativeSearchQuery implements INativeQueryObject {
	/** One or more web search queries to run. */
	private List<String> searchedTexts;
	/** "basic" (fast) or "advanced" (deeper, higher quality). */
	private String searchDepth;
	/** "general" or "news". */
	private String topic;
	/** Recency filter: "day", "week", "month" or "year". */
	private String timeRange;

	@Override
	public List<String> relevantKeywords() {
		return searchedTexts;
	}
}
