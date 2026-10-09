/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.services;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;

import reactor.core.publisher.Flux;

/**
 * Puts a blocking call's answer first among its generations.
 * <p>
 * Spring AI reads the answer of a call from the first generation:
 * {@code ChatResponse.getResult()} is {@code generations.get(0)}, and the ChatClient's
 * {@code content()} and {@code entity()} as well as {@code ChatModel.call(String)} read
 * it. Some providers return the model's reasoning as generations of their own before
 * the answer — Spring AI's Anthropic model adds one generation per thinking block first
 * and the answer last — so those calls read the reasoning (empty when the provider
 * omits it) instead of the answer. Which generations are reasoning is the provider's to
 * say ({@link GAbstractConfigurableChatModel#isReasoningGeneration(Generation)}); here
 * the last generation that is not reasoning is moved to the first place, the others
 * keeping their order. Nothing is dropped or changed.
 */
public final class AnswerFirstGenerations {

	private AnswerFirstGenerations() {
	}

	/**
	 * The response with its answer generation first, or the response itself when its
	 * first generation is already not reasoning (or no generation is the answer).
	 */
	public static ChatResponse answerFirst(ChatResponse response, Predicate<Generation> isReasoning) {
		if (response == null || isReasoning == null) {
			return response;
		}
		final List<Generation> generations = response.getResults();
		if (generations == null || generations.size() < 2 || !isReasoning.test(generations.get(0))) {
			return response;
		}
		int answer = -1;
		for (int i = generations.size() - 1; i >= 0; i--) {
			if (!isReasoning.test(generations.get(i))) {
				answer = i;
				break;
			}
		}
		if (answer < 0) {
			return response;
		}
		final List<Generation> reordered = new ArrayList<>(generations.size());
		reordered.add(generations.get(answer));
		for (int i = 0; i < generations.size(); i++) {
			if (i != answer) {
				reordered.add(generations.get(i));
			}
		}
		return ChatResponse.builder().from(response).generations(reordered).build();
	}

	/**
	 * The ChatClient side: the response a call returns has its answer first. Ordered
	 * outside the tool calling advisor, so it touches only what the call returns.
	 */
	public static final class Advisor implements CallAdvisor {
		/** Outside Spring AI's ToolCallingAdvisor (DEFAULT_ORDER = HIGHEST_PRECEDENCE + 300). */
		static final int ORDER = org.springframework.core.Ordered.HIGHEST_PRECEDENCE + 100;
		private final Predicate<Generation> isReasoning;

		public Advisor(Predicate<Generation> isReasoning) {
			this.isReasoning = isReasoning;
		}

		@Override
		public ChatClientResponse adviseCall(ChatClientRequest request, CallAdvisorChain chain) {
			final ChatClientResponse response = chain.nextCall(request);
			if (response == null || response.chatResponse() == null) {
				return response;
			}
			final ChatResponse reordered = answerFirst(response.chatResponse(), isReasoning);
			return reordered == response.chatResponse() ? response
					: response.mutate().chatResponse(reordered).build();
		}

		@Override
		public String getName() {
			return "geboAnswerFirstGenerations";
		}

		@Override
		public int getOrder() {
			return ORDER;
		}
	}

	/**
	 * The raw model side ({@code doWithChatModel}): a call's response has its answer
	 * first, so {@code ChatModel.call(String)} reads the answer. Streams are forwarded
	 * as they are.
	 */
	public static final class Model implements ChatModel {
		private final ChatModel delegate;
		private final Predicate<Generation> isReasoning;

		public Model(ChatModel delegate, Predicate<Generation> isReasoning) {
			this.delegate = delegate;
			this.isReasoning = isReasoning;
		}

		@Override
		public ChatResponse call(Prompt prompt) {
			return answerFirst(delegate.call(prompt), isReasoning);
		}

		@Override
		public Flux<ChatResponse> stream(Prompt prompt) {
			return delegate.stream(prompt);
		}

		@Override
		public ChatOptions getOptions() {
			return delegate.getOptions();
		}

		@Override
		public ChatOptions getDefaultOptions() {
			return delegate.getDefaultOptions();
		}
	}
}
