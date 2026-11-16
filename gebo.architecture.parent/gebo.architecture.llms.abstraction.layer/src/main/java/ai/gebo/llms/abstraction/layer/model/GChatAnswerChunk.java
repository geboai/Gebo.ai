/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.chat.messages.AssistantMessage.ToolCall;
import org.springframework.ai.chat.messages.part.MessagePart;
import org.springframework.ai.chat.messages.part.StreamingParts;
import org.springframework.ai.chat.messages.part.ToolCallPart;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.content.Media;

/**
 * A piece of a streamed answer, every provider alike: the answer text it adds, the
 * reasoning it adds, whether the model is reasoning, why the model stopped (on the chunk
 * ending a round), the media it carries and the tool calls the model is writing. Read
 * from a chunk the {@code ThinkingNormalizationAdvisor} normalized.
 *
 * @param answer         the answer text added, never null, without reasoning
 * @param thinking       the reasoning added, never null
 * @param thinkingActive whether the model is reasoning (a provider may signal it with no
 *                       text)
 * @param finishReason   why the model stopped writing, null while it writes
 * @param media          the media the chunk carries, never null
 * @param source         the chunk it was read from
 * @param toolCalls      the tool calls the model is writing, never null: a piece of
 *                       their arguments, or a call complete
 */
public record GChatAnswerChunk(String answer, String thinking, boolean thinkingActive, String finishReason,
		List<Media> media, ChatResponse source, List<ToolCallProgress> toolCalls) {

	/** The response metadata holding the reasoning a normalized chunk adds. */
	public static final String THINKING_DELTA_METADATA = "geboThinkingDelta";
	/** The response metadata telling the model is reasoning, on a normalized chunk. */
	public static final String THINKING_ACTIVE_METADATA = "geboThinkingActive";
	/** The response metadata holding the reasoning of a normalized blocking call. */
	public static final String THINKING_METADATA = "geboThinking";
	/**
	 * The response metadata holding the tool calls a round ended with, complete, as a
	 * {@code List<ToolCallProgress>}: the chunk carrying them as calls never reaches the
	 * consumers of the stream (the ToolCallingAdvisor runs them instead).
	 */
	public static final String TOOL_CALLS_COMPLETE_METADATA = "geboToolCallsComplete";

	/**
	 * A tool call as the model writes it (Spring AI 2.1 streams the arguments of a call
	 * in pieces with Anthropic, OpenAI, Bedrock and DeepSeek; the others send the call
	 * whole, a complete one with no piece before it).
	 *
	 * @param id        the call id, null when the provider gives none with a piece
	 * @param index     the position of the call in the model's message, null when unknown
	 * @param name      the tool called
	 * @param arguments the piece of the arguments written since the previous one, or all
	 *                  of them on the complete call
	 * @param complete  whether the call is written whole
	 */
	public record ToolCallProgress(String id, Integer index, String name, String arguments, boolean complete) {
		public ToolCallProgress {
			arguments = arguments != null ? arguments : "";
		}

		/** The progress a tool call part gives: a piece when it is partial, else the call. */
		public static ToolCallProgress of(ToolCallPart part) {
			final ToolCall call = part.toolCall();
			return new ToolCallProgress(call.id(), StreamingParts.partIndex(part), call.name(), call.arguments(),
					!StreamingParts.isPartial(part));
		}
	}

	public GChatAnswerChunk {
		answer = answer != null ? answer : "";
		thinking = thinking != null ? thinking : "";
		media = media != null ? media : List.of();
		toolCalls = toolCalls != null ? toolCalls : List.of();
	}

	/** A chunk with no tool call in progress. */
	public GChatAnswerChunk(String answer, String thinking, boolean thinkingActive, String finishReason,
			List<Media> media, ChatResponse source) {
		this(answer, thinking, thinkingActive, finishReason, media, source, List.of());
	}

	/** The piece a normalized chunk adds. */
	public static GChatAnswerChunk of(ChatResponse response) {
		if (response == null) {
			return new GChatAnswerChunk("", "", false, null, List.of(), null);
		}
		final StringBuilder answer = new StringBuilder();
		final List<Media> media = new ArrayList<>();
		final List<ToolCallProgress> toolCalls = new ArrayList<>();
		String finishReason = null;
		if (response.getResults() != null) {
			for (Generation generation : response.getResults()) {
				if (generation.getMetadata() != null && generation.getMetadata().getFinishReason() != null
						&& !generation.getMetadata().getFinishReason().isBlank()) {
					finishReason = generation.getMetadata().getFinishReason();
				}
				if (generation.getOutput() != null) {
					if (generation.getOutput().getText() != null) {
						answer.append(generation.getOutput().getText());
					}
					if (generation.getOutput().getMedia() != null) {
						media.addAll(generation.getOutput().getMedia());
					}
					// the pieces of the arguments being written: the complete calls never come
					// this way, they come announced (TOOL_CALLS_COMPLETE_METADATA)
					for (MessagePart part : generation.getOutput().getParts()) {
						if (part instanceof ToolCallPart toolCall && StreamingParts.isPartial(toolCall)) {
							toolCalls.add(ToolCallProgress.of(toolCall));
						}
					}
				}
			}
		}
		final Object thinking = response.getMetadata() != null ? response.getMetadata().get(THINKING_DELTA_METADATA)
				: null;
		final Object active = response.getMetadata() != null ? response.getMetadata().get(THINKING_ACTIVE_METADATA)
				: null;
		final Object complete = response.getMetadata() != null
				? response.getMetadata().get(TOOL_CALLS_COMPLETE_METADATA)
				: null;
		if (complete instanceof List<?> calls) {
			for (Object call : calls) {
				if (call instanceof ToolCallProgress progress) {
					toolCalls.add(progress);
				}
			}
		}
		return new GChatAnswerChunk(answer.toString(), thinking instanceof String text ? text : "",
				Boolean.TRUE.equals(active), finishReason, media, response, toolCalls);
	}
}
