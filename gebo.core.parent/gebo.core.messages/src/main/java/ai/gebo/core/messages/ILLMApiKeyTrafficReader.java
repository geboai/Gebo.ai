/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.core.messages;

import java.time.LocalDate;
import java.util.List;

/**
 * Reads the LLM traffic consolidated by day, per model type and API key: where the
 * usage is consolidated, it lets the provider deals compare the tokens consumed
 * with their traffic limits.
 */
public interface ILLMApiKeyTrafficReader {
	/**
	 * @param from        the first day, included
	 * @param toInclusive the last day, included
	 * @return the tokens consumed in the days, one row per model type and API key
	 */
	public List<LLMApiKeyTraffic> readTraffic(LocalDate from, LocalDate toInclusive);
}
