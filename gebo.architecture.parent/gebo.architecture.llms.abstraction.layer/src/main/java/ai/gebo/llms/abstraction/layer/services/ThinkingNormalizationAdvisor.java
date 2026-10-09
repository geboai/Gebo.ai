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
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisor;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisorChain;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.core.Ordered;

import ai.gebo.llms.abstraction.layer.model.GChatAnswerChunk;
import ai.gebo.llms.abstraction.layer.services.IGReasoningExtractor.Reasoning;
import reactor.core.publisher.Flux;

/**
 * Gives every provider's answer the same shape: the text of a response is the answer
 * only, and the reasoning the model wrote with it is in the response metadata.
 * <p>
 * Ordered inside Spring AI's ToolCallingAdvisor (and inside the usage advisor), so it
 * reads every model round of a tool calling loop, the rounds that end calling tools
 * included. For each chunk of a round the provider's {@link IGReasoningExtractor} gives
 * the reasoning and the answer text of each generation; when the model writes its
 * reasoning in its text between thinking tags, an {@link InlineThinkingSplitter} parts
 * it from the answer. The chunk then carries the reasoning it adds under
 * {@link GChatAnswerChunk#THINKING_DELTA_METADATA} and whether the model is reasoning
 * under {@link GChatAnswerChunk#THINKING_ACTIVE_METADATA}, in the response metadata:
 * Spring AI's MessageAggregator builds the assistant message a tool round replays from
 * the message metadata, never from the response metadata, so nothing of it is replayed.
 * The message metadata and the message class are kept, rebuilt only when the text
 * changes (thinking tags taken out).
 * <p>
 * A chunk carrying both reasoning and tool calls is preceded by a chunk carrying the
 * reasoning only: the ToolCallingAdvisor drops the chunks with tool calls, so the
 * reasoning of a round ending in tool calls reaches the user as well. A blocking call's
 * reasoning is put under {@link GChatAnswerChunk#THINKING_METADATA}.
 */
public final class ThinkingNormalizationAdvisor implements CallAdvisor, StreamAdvisor {
	private static final Logger LOGGER = LoggerFactory.getLogger(ThinkingNormalizationAdvisor.class);
	/**
	 * Inside the ToolCallingAdvisor (HIGHEST_PRECEDENCE + 300) and the usage advisor
	 * (LOWEST_PRECEDENCE - 100), outside the model's own advisors (LOWEST_PRECEDENCE).
	 */
	static final int ORDER = Ordered.LOWEST_PRECEDENCE - 50;

	private final IGReasoningExtractor extractor;
	private final BooleanSupplier inlineTags;
	private final Predicate<Generation> isReasoning;

	/**
	 * @param extractor   the provider's reading of the reasoning
	 * @param inlineTags  whether the model writes its reasoning between thinking tags
	 * @param isReasoning which generations of a blocking call are reasoning of their own
	 */
	public ThinkingNormalizationAdvisor(IGReasoningExtractor extractor, BooleanSupplier inlineTags,
			Predicate<Generation> isReasoning) {
		this.extractor = extractor != null ? extractor : IGReasoningExtractor.NONE;
		this.inlineTags = inlineTags != null ? inlineTags : () -> false;
		this.isReasoning = isReasoning != null ? isReasoning : g -> false;
	}

	@Override
	public Flux<ChatClientResponse> adviseStream(ChatClientRequest request, StreamAdvisorChain chain) {
		// one reading per round: the ToolCallingAdvisor calls the chain again for each round
		return Flux.defer(() -> {
			final IGReasoningExtractor.Round reading = extractor.round();
			final Round round = new Round(reading, inlineTags.getAsBoolean() ? new InlineThinkingSplitter() : null);
			final AtomicReference<ChatClientResponse> last = new AtomicReference<>();
			return chain.nextStream(reading.streaming(request)).concatMap(chunk -> {
				last.set(chunk);
				return Flux.fromIterable(round.normalize(chunk));
			}).concatWith(Flux.defer(() -> Flux.fromIterable(round.rest(last.get()))))
					.doFinally(signal -> reading.close());
		});
	}

	@Override
	public ChatClientResponse adviseCall(ChatClientRequest request, CallAdvisorChain chain) {
		final ChatClientResponse response = chain.nextCall(request);
		final ChatResponse chat = response != null ? response.chatResponse() : null;
		if (chat == null || chat.getResults() == null) {
			return response;
		}
		final IGReasoningExtractor.Round reading = extractor.round();
		final boolean inline = inlineTags.getAsBoolean();
		final StringBuilder thinking = new StringBuilder();
		final List<Generation> generations = new ArrayList<>();
		boolean changed = false;
		for (Generation generation : chat.getResults()) {
			if (isReasoning.test(generation)) {
				// a generation of its own holding reasoning (Claude's thinking blocks)
				thinking.append(IGReasoningExtractor.textOf(generation));
				generations.add(generation);
				continue;
			}
			final Reasoning reasoning = reading.of(generation);
			thinking.append(reasoning.thinking());
			String answer = reasoning.answer();
			if (inline) {
				final InlineThinkingSplitter.Split split = InlineThinkingSplitter.split(answer);
				thinking.append(split.thinking());
				answer = split.answer();
			}
			final Generation normalized = withText(generation, answer);
			changed |= normalized != generation;
			generations.add(normalized);
		}
		if (thinking.isEmpty() && !changed) {
			return response;
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("adviseCall(...) normalized a response with " + thinking.length()
					+ " character(s) of reasoning" + (changed ? ", its text without reasoning" : ""));
		}
		final ChatResponse normalized = ChatResponse.builder().from(chat).generations(generations)
				.metadata(GChatAnswerChunk.THINKING_METADATA, thinking.toString()).build();
		return response.mutate().chatResponse(normalized).build();
	}

	@Override
	public String getName() {
		return "geboThinkingNormalization";
	}

	@Override
	public int getOrder() {
		return ORDER;
	}

	/** The generation with the text given, itself when its text is that already. */
	static Generation withText(Generation generation, String text) {
		final AssistantMessage output = generation.getOutput();
		if (output == null || Objects.equals(text, IGReasoningExtractor.textOf(generation))) {
			return generation;
		}
		return new Generation(output.mutate().content(text).build(), generation.getMetadata());
	}

	/** The reading of one model round, as its chunks stream. */
	private static final class Round {
		private final IGReasoningExtractor.Round reading;
		private final InlineThinkingSplitter splitter;

		Round(IGReasoningExtractor.Round reading, InlineThinkingSplitter splitter) {
			this.reading = reading;
			this.splitter = splitter;
		}

		/** The chunk normalized, preceded by its reasoning alone when it calls tools. */
		List<ChatClientResponse> normalize(ChatClientResponse chunk) {
			final ChatResponse chat = chunk != null ? chunk.chatResponse() : null;
			if (chat == null || chat.getResults() == null) {
				return chunk != null ? List.of(chunk) : List.of();
			}
			final StringBuilder thinking = new StringBuilder();
			final List<Generation> generations = new ArrayList<>();
			boolean active = false;
			boolean changed = false;
			for (Generation generation : chat.getResults()) {
				final Reasoning reasoning = reading.of(generation);
				thinking.append(reasoning.thinking());
				active |= reasoning.active();
				String answer = reasoning.answer();
				if (splitter != null) {
					final InlineThinkingSplitter.Split split = splitter.next(answer);
					thinking.append(split.thinking());
					answer = split.answer();
				}
				final Generation normalized = withText(generation, answer);
				changed |= normalized != generation;
				generations.add(normalized);
			}
			active |= !thinking.isEmpty() || (splitter != null && splitter.isInsideThinking());
			if (thinking.isEmpty() && !active && !changed) {
				return List.of(chunk);
			}
			if (!thinking.isEmpty() && chat.hasToolCalls()) {
				// the ToolCallingAdvisor drops this chunk: its reasoning goes on its own before it
				final ChatResponse reasoningOnly = ChatResponse.builder()
						.generations(List.of(new Generation(new AssistantMessage(""))))
						.metadata(GChatAnswerChunk.THINKING_DELTA_METADATA, thinking.toString())
						.metadata(GChatAnswerChunk.THINKING_ACTIVE_METADATA, Boolean.TRUE).build();
				final ChatResponse toolCalls = ChatResponse.builder().from(chat).generations(generations).build();
				return List.of(chunk.mutate().chatResponse(reasoningOnly).build(),
						chunk.mutate().chatResponse(toolCalls).build());
			}
			final ChatResponse.Builder normalized = ChatResponse.builder().from(chat).generations(generations)
					.metadata(GChatAnswerChunk.THINKING_ACTIVE_METADATA, active);
			if (!thinking.isEmpty()) {
				normalized.metadata(GChatAnswerChunk.THINKING_DELTA_METADATA, thinking.toString());
			}
			return List.of(chunk.mutate().chatResponse(normalized.build()).build());
		}

		/**
		 * What the reading and the splitter still held when the round ended, as a last
		 * chunk.
		 */
		List<ChatClientResponse> rest(ChatClientResponse last) {
			final String held = reading.end();
			final InlineThinkingSplitter.Split rest = splitter != null ? splitter.end() : InlineThinkingSplitter.Split.EMPTY;
			final String thinking = (held != null ? held : "") + rest.thinking();
			if ((thinking.isEmpty() && rest.answer().isEmpty()) || last == null) {
				return List.of();
			}
			final ChatResponse.Builder chat = ChatResponse.builder()
					.generations(List.of(new Generation(new AssistantMessage(rest.answer()))))
					.metadata(GChatAnswerChunk.THINKING_ACTIVE_METADATA, Boolean.FALSE);
			if (!thinking.isEmpty()) {
				chat.metadata(GChatAnswerChunk.THINKING_DELTA_METADATA, thinking);
			}
			return List.of(last.mutate().chatResponse(chat.build()).build());
		}
	}
}
