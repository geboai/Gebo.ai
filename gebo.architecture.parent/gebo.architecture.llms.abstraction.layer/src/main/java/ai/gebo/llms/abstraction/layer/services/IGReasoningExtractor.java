/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.services;

import org.springframework.ai.chat.model.Generation;

/**
 * Reads the reasoning a provider's Spring AI model gives with its answer: each provider
 * keeps it its own way (a metadata of the answer, a field of its own message class, a
 * generation of its own, a signal with no text). A provider handler gives its reading
 * ({@link GAbstractConfigurableChatModel#reasoningExtractor()}); the
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
	}

	/** A new reading, for a new model round. */
	Round round();

	/** No reasoning apart from the answer: the generation's text is the answer. */
	IGReasoningExtractor NONE = () -> Reasoning::answerOnly;

	/**
	 * The metadata Spring AI's OpenAI model keeps the reasoning under (the reasoning_content
	 * or reasoning field of the OpenAI compatible APIs), all of it so far on each streamed
	 * chunk of a round.
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

	/** The reasoning of the OpenAI compatible APIs, as Spring AI's OpenAI model keeps it. */
	IGReasoningExtractor OPENAI = metadata(REASONING_CONTENT_METADATA);

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
