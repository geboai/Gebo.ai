/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.services;

import java.util.List;
import java.util.function.Function;

import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.messages.part.ReasoningPart;
import org.springframework.ai.chat.model.Generation;

/**
 * Reads the reasoning a provider's Spring AI model gives with its answer. Since Spring AI
 * 2.1 every provider's model keeps it the same way, as the {@link ReasoningPart}s of the
 * assistant message ({@link #PARTS}, the reading by default); a provider keeping it its
 * own way (a metadata of the answer, a signal with no text) gives its reading
 * ({@link GAbstractConfigurableChatModel#reasoningExtractor()}). The
 * {@link ThinkingNormalizationAdvisor} reads every generation of a model round with it.
 */
@FunctionalInterface
public interface IGReasoningExtractor {

	/**
	 * What a generation carries: the piece of reasoning it adds, its answer text, and
	 * whether the model is reasoning (a provider may signal it with no text).
	 */
	record Reasoning(String thinking, String answer, boolean active) {
		public Reasoning {
			thinking = thinking != null ? thinking : "";
			answer = answer != null ? answer : "";
		}

		/** A generation carrying only its answer. */
		public static Reasoning answerOnly(Generation generation) {
			return new Reasoning("", textOf(generation), false);
		}
	}

	/**
	 * The reading of one model round, a stream or a call: a provider giving all its
	 * reasoning so far on each chunk is read as the pieces it adds.
	 */
	@FunctionalInterface
	interface Round {
		Reasoning of(Generation generation);

		/**
		 * The request of a streamed round, as the reading needs it sent (a provider whose
		 * reasoning is read off its HTTP stream marks the request); as it is by default.
		 */
		default ChatClientRequest streaming(ChatClientRequest request) {
			return request;
		}

		/** The reasoning still held when the round's stream ended; none by default. */
		default String end() {
			return "";
		}

		/** The round is over, however it ended: what the reading holds is released. */
		default void close() {
		}
	}

	/** A new reading, for a new model round. */
	Round round();

	/** No reasoning apart from the answer: the generation's text is the answer. */
	IGReasoningExtractor NONE = () -> Reasoning::answerOnly;

	/** The reasoning parts of a generation's message, none when it has none. */
	static List<ReasoningPart> reasoningPartsOf(Generation generation) {
		final List<ReasoningPart> parts = generation != null && generation.getOutput() != null
				? generation.getOutput().getReasoning()
				: null;
		return parts != null ? parts : List.of();
	}

	/**
	 * The metadata Spring AI's OpenAI model kept the reasoning under before the parts (the
	 * reasoning_content or reasoning field of the OpenAI compatible APIs), all of it so far
	 * on each streamed chunk of a round; a model of its own may still keep it there.
	 */
	String REASONING_CONTENT_METADATA = "reasoningContent";

	/** The reasoning in a metadata of the answer, all of it so far on each chunk of a round. */
	static IGReasoningExtractor metadata(String key) {
		return () -> {
			final StringBuilder soFar = new StringBuilder();
			return generation -> {
				final Object value = generation != null && generation.getOutput() != null
						&& generation.getOutput().getMetadata() != null
								? generation.getOutput().getMetadata().get(key)
								: null;
				return new Reasoning(value instanceof String text ? grown(soFar, text) : "", textOf(generation), false);
			};
		};
	}

	/**
	 * The reasoning of the OpenAI compatible APIs in the {@link #REASONING_CONTENT_METADATA}
	 * metadata: the legacy reading, which {@link #PARTS} falls back to.
	 */
	IGReasoningExtractor OPENAI = metadata(REASONING_CONTENT_METADATA);

	/**
	 * The reasoning as Spring AI (2.1 and later) keeps it, whichever the provider: the
	 * {@link ReasoningPart}s of the assistant message, each streamed chunk's parts the
	 * pieces it adds (a part with no text, a signature or a redacted block, says the model
	 * is reasoning), the whole of it on a blocking call's answer. A message with no
	 * reasoning part is read the legacy way ({@link #OPENAI}): the reasoning_content
	 * metadata a model written before the parts still keeps, all of it so far on each chunk.
	 */
	IGReasoningExtractor PARTS = () -> {
		final Round legacy = OPENAI.round();
		return generation -> {
			final List<ReasoningPart> parts = reasoningPartsOf(generation);
			if (parts.isEmpty()) {
				return legacy.of(generation);
			}
			final StringBuilder thinking = new StringBuilder();
			for (ReasoningPart part : parts) {
				if (part.text() != null) {
					thinking.append(part.text());
				}
			}
			return new Reasoning(thinking.toString(), textOf(generation), true);
		};
	};

	/**
	 * The reasoning each chunk gives as the piece it adds, as the function reads it (null
	 * when the chunk gives none); the whole reasoning on a blocking call's answer.
	 */
	static IGReasoningExtractor pieces(Function<Generation, String> reasoning) {
		return () -> generation -> {
			final String piece = generation != null && generation.getOutput() != null ? reasoning.apply(generation)
					: null;
			return new Reasoning(piece, textOf(generation), false);
		};
	}

	/** The reasoning each chunk gives as the piece it adds, in a metadata of the answer. */
	static IGReasoningExtractor metadataPieces(String key) {
		return pieces(generation -> generation.getOutput().getMetadata() != null
				&& generation.getOutput().getMetadata().get(key) instanceof String piece ? piece : null);
	}

	/**
	 * What the reasoning grew by: the text is all of it so far, or (a provider giving only
	 * the new piece, or starting a new reasoning) a piece on its own.
	 */
	static String grown(StringBuilder soFar, String text) {
		if (text.isEmpty()) {
			return "";
		}
		final String before = soFar.toString();
		if (text.startsWith(before)) {
			soFar.setLength(0);
			soFar.append(text);
			return text.substring(before.length());
		}
		soFar.append(text);
		return text;
	}

	/** The text of a generation, empty when it has none. */
	static String textOf(Generation generation) {
		final String text = generation != null && generation.getOutput() != null ? generation.getOutput().getText()
				: null;
		return text != null ? text : "";
	}
}
