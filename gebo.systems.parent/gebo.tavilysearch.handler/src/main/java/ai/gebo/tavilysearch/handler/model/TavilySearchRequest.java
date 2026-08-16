/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.tavilysearch.handler.model;

import lombok.Data;

/** Arguments the LLM fills when calling the Tavily web-search tool. */
@Data
public class TavilySearchRequest {
	private String query = null;
	private String language = null;
	private Integer topN = null;
	/** "basic" (fast) or "advanced" (deeper). Optional. */
	private String searchDepth = null;
	/** "general" or "news". Optional. */
	private String topic = null;
	/** Recency filter: "day", "week", "month" or "year". Optional. */
	private String timeRange = null;
}
