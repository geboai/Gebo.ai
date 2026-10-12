/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.anthropic.services;

import java.lang.reflect.RecordComponent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.anthropic.AnthropicChatOptions;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.model.Generation;

import ai.gebo.llms.abstraction.layer.services.IGReasoningExtractor;
import ai.gebo.llms.anthropic.http.AnthropicThinkingTap;

/**
 * Reads Claude's thinking as Spring AI's AnthropicChatModel (2.0.1) streams it, with the
 * text read off the HTTP stream ({@link AnthropicThinkingTap}).
 * <p>
 * Each {@code thinking_delta} event becomes a text-less chunk whose message carries
 * {@code thinking=true}: the model is thinking, and the text the tap read so far for the
 * round's request is its piece of thinking. The chunk ending the turn carries the turn's
 * thinking blocks under {@value #THINKING_CONTENTS}; any part of them the tap did not
 * give (a request it could not mark) is given there, after the answer as Spring AI
 * streams it. A blocking call's answer carries them too, but its thinking blocks are
 * generations of their own as well, read as such
 * ({@code AnthropicConfigurableChatModel.isReasoningGeneration}): a call reads none here.
 */
final class AnthropicReasoningExtractor implements IGReasoningExtractor {
	private static final Logger LOGGER = LoggerFactory.getLogger(AnthropicReasoningExtractor.class);
	/** The message metadata Spring AI keeps a thinking chunk's mark under */
	static final String THINKING = "thinking";
	/** The message metadata Spring AI keeps the turn's thinking blocks under */
	static final String THINKING_CONTENTS = "anthropicThinkingContents";

	@Override
	public Round round() {
		return new ClaudeRound();
	}

	private static final class ClaudeRound implements Round {
		/** Whether the round streams: a call's thinking is read from its generations */
		private boolean streams = false;
		/** Set when the round streams: the tap's listener of its request */
		private AnthropicThinkingTap.Listener listener;
		/** The thinking given so far */
		private long given = 0;

		@Override
		public ChatClientRequest streaming(ChatClientRequest request) {
			streams = true;
			if (request == null || request.prompt() == null
					|| !(request.prompt().getOptions() instanceof AnthropicChatOptions options)) {
				return request;
			}
			listener = AnthropicThinkingTap.listen();
			final Map<String, String> headers = new HashMap<>();
			if (options.getHttpHeaders() != null) {
				headers.putAll(options.getHttpHeaders());
			}
			headers.put(AnthropicThinkingTap.HEADER, listener.id());
			// a copy: the tool calling loop sends the same options object every round
			final AnthropicChatOptions marked = options.mutate().httpHeaders(headers).build();
			return request.mutate().prompt(request.prompt().mutate().chatOptions(marked).build()).build();
		}

		@Override
		public Reasoning of(Generation generation) {
			final Map<String, Object> metadata = generation != null && generation.getOutput() != null
					? generation.getOutput().getMetadata()
					: null;
			final String text = IGReasoningExtractor.textOf(generation);
			if (metadata == null || !streams) {
				return new Reasoning("", text, false);
			}
			final StringBuilder thinking = new StringBuilder();
			final boolean active = Boolean.TRUE.equals(metadata.get(THINKING));
			if (listener != null) {
				thinking.append(listener.take());
			}
			final String turn = thinkingOf(metadata.get(THINKING_CONTENTS));
			if (turn != null && turn.length() > given + thinking.length()) {
				// the part of the turn's thinking the tap did not give
				thinking.append(turn, (int) Math.min(turn.length(), given + thinking.length()), turn.length());
			}
			given += thinking.length();
			return new Reasoning(thinking.toString(), text, active);
		}

		@Override
		public String end() {
			final String rest = listener != null ? listener.take() : "";
			given += rest.length();
			return rest;
		}

		@Override
		public void close() {
			if (listener != null) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Claude round ended: " + listener.received() + " thinking character(s) read off its stream, "
							+ given + " given");
				}
				listener.close();
			}
		}
	}

	/**
	 * The text of a turn's thinking blocks, as Spring AI's package-private
	 * AnthropicThinkingContent records hold it (a redacted block has none); null when the
	 * metadata holds no blocks.
	 */
	static String thinkingOf(Object contents) {
		if (!(contents instanceof List<?> blocks) || blocks.isEmpty()) {
			return null;
		}
		final StringBuilder text = new StringBuilder();
		for (Object block : blocks) {
			final String thinking = component(block, "thinking");
			if (thinking != null) {
				text.append(thinking);
			}
		}
		return text.toString();
	}

	private static String component(Object record, String name) {
		if (record == null || !record.getClass().isRecord()) {
			return null;
		}
		for (RecordComponent component : record.getClass().getRecordComponents()) {
			if (component.getName().equals(name)) {
				try {
					final var accessor = component.getAccessor();
					accessor.setAccessible(true);
					return accessor.invoke(record) instanceof String value ? value : null;
				} catch (ReflectiveOperationException | RuntimeException e) {
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("The " + name + " of a Claude thinking block could not be read: " + e.getMessage());
					}
					return null;
				}
			}
		}
		return null;
	}
}
