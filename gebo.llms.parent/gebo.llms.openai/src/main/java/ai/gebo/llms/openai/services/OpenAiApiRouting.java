/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.openai.services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Which OpenAI api a chat model is called through. OpenAI's reasoning guide
 * (developers.openai.com/api/docs/guides/reasoning, 2026-10-11) says "Chat Completions
 * does not support function calling with GPT-6 Astra or GPT-6.1 Sol", and the GPT-6 Sol
 * and Luna pages "Chat Completions supports function calling only with reasoning_effort
 * set to none": the models of version 6 and later go through the Responses api (Spring
 * AI's {@code OpenAiResponsesChatModel}), the others keep Spring AI's Chat Completions
 * model.
 */
public final class OpenAiApiRouting {
	/** The first version of the models on the Responses api. */
	static final double FIRST_RESPONSES_VERSION = 6.0;
	private static final Pattern VERSION = Pattern.compile("\\d+(?:\\.\\d+)*");

	private OpenAiApiRouting() {
	}

	/**
	 * Whether a model goes through the Responses API: the first run of digits and dots
	 * of its id read as a number ({@code gpt-6.1-sol} 6.1, {@code gpt-5.5} 5.5,
	 * {@code gpt-4o} 4, {@code o3} 3) is 6 or more.
	 */
	public static boolean usesResponsesApi(String modelId) {
		final Double version = versionOf(modelId);
		return version != null && version >= FIRST_RESPONSES_VERSION;
	}

	/** The version a model id carries, its first digits and dots; null when it has none. */
	static Double versionOf(String modelId) {
		if (modelId == null)
			return null;
		final Matcher matcher = VERSION.matcher(modelId);
		if (!matcher.find())
			return null;
		final String[] parts = matcher.group().split("\\.");
		try {
			return Double.valueOf(parts.length > 1 ? parts[0] + "." + parts[1] : parts[0]);
		} catch (NumberFormatException e) {
			return null;
		}
	}
}
