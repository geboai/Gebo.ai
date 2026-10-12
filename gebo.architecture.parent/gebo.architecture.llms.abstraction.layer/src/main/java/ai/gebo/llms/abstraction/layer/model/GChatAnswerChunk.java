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

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.content.Media;

/**
 * A piece of a streamed answer, every provider alike: the answer text it adds, the
 * reasoning it adds, whether the model is reasoning, why the model stopped (on the chunk
 * ending a round) and the media it carries. Read from a chunk the
 * {@code ThinkingNormalizationAdvisor} normalized.
 *
 * @param answer         the answer text added, never null, without reasoning
 * @param thinking       the reasoning added, never null
 * @param thinkingActive whether the model is reasoning (a provider may signal it with no
 *                       text)
 * @param finishReason   why the model stopped writing, null while it writes
 * @param media          the media the chunk carries, never null
 * @param source         the chunk it was read from
 */
public record GChatAnswerChunk(String answer, String thinking, boolean thinkingActive, String finishReason,
		List<Media> media, ChatResponse source) {

	/** The response metadata holding the reasoning a normalized chunk adds. */
	public static final String THINKING_DELTA_METADATA = "geboThinkingDelta";
	/** The response metadata telling the model is reasoning, on a normalized chunk. */
	public static final String THINKING_ACTIVE_METADATA = "geboThinkingActive";
	/** The response metadata holding the reasoning of a normalized blocking call. */
	public static final String THINKING_METADATA = "geboThinking";

	public GChatAnswerChunk {
		answer = answer != null ? answer : "";
		thinking = thinking != null ? thinking : "";
		media = media != null ? media : List.of();
	}

	/** The piece a normalized chunk adds. */
	public static GChatAnswerChunk of(ChatResponse response) {
		if (response == null) {
			return new GChatAnswerChunk("", "", false, null, List.of(), null);
		}
		final StringBuilder answer = new StringBuilder();
		final List<Media> media = new ArrayList<>();
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
				}
			}
		}
		final Object thinking = response.getMetadata() != null ? response.getMetadata().get(THINKING_DELTA_METADATA)
				: null;
		final Object active = response.getMetadata() != null ? response.getMetadata().get(THINKING_ACTIVE_METADATA)
				: null;
		return new GChatAnswerChunk(answer.toString(), thinking instanceof String text ? text : "",
				Boolean.TRUE.equals(active), finishReason, media, response);
	}
}
