/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.openai.services;

import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.responses.OpenAiResponsesChatOptions;

/**
 * The options of a chat model on the Responses api, from the ones the OpenAI handler
 * builds for Chat Completions: one configuration, whichever api the model goes through
 * ({@link OpenAiApiRouting}).
 * <ul>
 * <li>the model, sampling, timeout, retries and endpoint as they are;</li>
 * <li>{@code max_output_tokens} from the completion cap (it bounds the reasoning too);</li>
 * <li>the reasoning effort as it is, and the reasoning summaries asked ({@value #SUMMARY}):
 * the thinking shown in the chat. An organization refused them is answered by
 * {@link ReasoningSummariesFallbackChatModel};</li>
 * <li>the tools, {@code parallel_tool_calls} only where the handler set it (with tools:
 * without tools OpenAI refuses the parameter);</li>
 * <li>the {@code user} of the key as the {@code safety_identifier} OpenAI replaces it with
 * on the Responses api.</li>
 * </ul>
 */
public final class OpenAiResponsesOptions {
	/** The reasoning summaries asked, as the guide's examples ask them. */
	static final String SUMMARY = "auto";

	private OpenAiResponsesOptions() {
	}

	public static OpenAiResponsesChatOptions of(OpenAiChatOptions chat) {
		final OpenAiResponsesChatOptions.Builder builder = OpenAiResponsesChatOptions.builder().model(chat.getModel())
				.apiKey(chat.getApiKey()).baseUrl(chat.getBaseUrl()).organizationId(chat.getOrganizationId())
				.timeout(chat.getTimeout()).maxRetries(chat.getMaxRetries()).temperature(chat.getTemperature())
				.topP(chat.getTopP()).reasoningEffort(chat.getReasoningEffort()).reasoningSummary(SUMMARY)
				.responseFormat(chat.getResponseFormat()).strict(chat.getStrict())
				.parallelToolCalls(chat.getParallelToolCalls()).safetyIdentifier(chat.getUser());
		final Integer maxOutputTokens = chat.getMaxCompletionTokens() != null ? chat.getMaxCompletionTokens()
				: chat.getMaxTokens();
		if (maxOutputTokens != null) {
			builder.maxOutputTokens(maxOutputTokens);
		}
		if (chat.getToolCallbacks() != null && !chat.getToolCallbacks().isEmpty()) {
			builder.toolCallbacks(chat.getToolCallbacks());
		}
		if (chat.getToolContext() != null && !chat.getToolContext().isEmpty()) {
			builder.toolContext(chat.getToolContext());
		}
		return builder.build();
	}
}
