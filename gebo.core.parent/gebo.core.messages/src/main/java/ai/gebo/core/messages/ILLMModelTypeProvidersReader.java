/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.core.messages;

import java.util.Map;

/**
 * Reads the real provider of every model type known where the LLM modules run: it
 * lets the usage consolidation, which does not depend on them, attribute to their
 * provider the usage recorded before the provider was.
 */
public interface ILLMModelTypeProvidersReader {
	/**
	 * @return the provider id of each model type code, e.g. "chatgpt-OpenAI" to
	 *         "openai"; the types declaring no provider are left out
	 */
	public Map<String, String> providersOfModelTypes();
}
