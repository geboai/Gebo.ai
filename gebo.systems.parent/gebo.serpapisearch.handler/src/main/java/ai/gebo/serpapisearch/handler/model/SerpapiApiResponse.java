/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.serpapisearch.handler.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * Wire shape of a SerpApi search.json response (only the organic results we
 * consume; field name matches the JSON key). The Spring mapper ignores the rest.
 */
@Data
public class SerpapiApiResponse {
	private List<SerpapiOrganicResult> organic_results = new ArrayList<SerpapiOrganicResult>();

	@Data
	public static class SerpapiOrganicResult {
		private String title;
		private String link;
		private String snippet;
	}
}
