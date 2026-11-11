/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.mistralai.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.part.MessagePart;
import org.springframework.ai.chat.messages.part.OpaquePayload;
import org.springframework.ai.chat.messages.part.ReasoningPart;
import org.springframework.ai.chat.messages.part.TextPart;
import org.springframework.ai.chat.model.Generation;

import ai.gebo.llms.abstraction.layer.services.IGReasoningExtractor;

/**
 * Mistral's reasoning (Magistral's thinking) as Spring AI's MistralAiChatModel (2.1) gives
 * it: each thinking chunk a reasoning part of the message, marked with the provider and
 * carrying its signature, the piece the chunk adds; the text chunks the text parts. The
 * reading by default of every provider reads it.
 */
class MistralReasoningTest {

	private static Generation chunk(String thinking, String text) {
		final AssistantMessage.Builder<?> builder = AssistantMessage.builder();
		if (thinking != null) {
			builder.part(new ReasoningPart(thinking, null, new OpaquePayload("mistral_ai", "signature", "sig"),
					Map.of(MessagePart.PROVIDER_ATTRIBUTE, "mistral_ai")));
		}
		if (text != null) {
			builder.part(TextPart.of(text));
		}
		return new Generation(builder.build());
	}

	@Test
	void eachChunksPieceOfReasoningApartFromItsText() {
		final IGReasoningExtractor.Round round = IGReasoningExtractor.PARTS.round();
		assertEquals(new IGReasoningExtractor.Reasoning("Okay, ", "", true), round.of(chunk("Okay, ", null)));
		assertEquals(new IGReasoningExtractor.Reasoning("Okay, the sum.", "", true),
				round.of(chunk("Okay, the sum.", null)));
		assertEquals(new IGReasoningExtractor.Reasoning("", "It is 4.", false), round.of(chunk(null, "It is 4.")));
	}
}
