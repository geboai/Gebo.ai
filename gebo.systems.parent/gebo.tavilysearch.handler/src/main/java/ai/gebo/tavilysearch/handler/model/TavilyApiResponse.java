/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.tavilysearch.handler.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * Wire shape of a Tavily /search response (only the fields we consume; the
 * Spring-configured mapper ignores the rest).
 */
@Data
public class TavilyApiResponse {
	private String query;
	private String answer;
	private List<TavilyApiResult> results = new ArrayList<TavilyApiResult>();

	@Data
	public static class TavilyApiResult {
		private String title;
		private String url;
		private String content;
		private Double score;
	}
}
