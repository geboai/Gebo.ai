/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.serpapisearch.handler.model;

import lombok.Data;

/** Arguments the LLM fills when calling the SerpApi web-search tool. */
@Data
public class SerpapiSearchRequest {
	private String query = null;
	private Integer topN = null;
	/** Search engine SerpApi should scrape: "google", "bing", "duckduckgo". Optional. */
	private String engine = null;
	/** Country code (gl), e.g. "us", "it". Optional. */
	private String gl = null;
	/** Interface language (hl), e.g. "en", "it". Optional. */
	private String hl = null;
	/** Google time filter (tbs), e.g. "qdr:d", "qdr:w", "qdr:m", "qdr:y". Optional. */
	private String tbs = null;
}
