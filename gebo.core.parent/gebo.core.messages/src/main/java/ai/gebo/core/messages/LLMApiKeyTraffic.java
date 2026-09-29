/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.core.messages;

/**
 * The tokens the LLM calls of a period consumed through one API key of one model
 * type, read by {@link ILLMApiKeyTrafficReader}.
 *
 * @param modelTypeCode the model type code the usage was recorded under
 * @param apiSecretCode the API secret code the calls went through, the pseudo key
 *                      "__no-api-key__" for a model without one, null for usage
 *                      recorded before the key was
 * @param totalToken    the tokens consumed, input and output
 */
public record LLMApiKeyTraffic(String modelTypeCode, String apiSecretCode, long totalToken) {
}
