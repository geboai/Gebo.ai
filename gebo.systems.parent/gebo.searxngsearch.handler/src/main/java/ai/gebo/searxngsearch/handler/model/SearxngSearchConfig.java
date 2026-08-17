/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.searxngsearch.handler.model;

import lombok.Data;

/**
 * Setup DTO for a SearXNG instance. baseUrl is required; apiKey is optional (a
 * bearer token for instances protected behind auth).
 */
@Data
public class SearxngSearchConfig {
	private String baseUrl = null;
	private String apiKey = null;
	private Boolean enabled = false;
}
