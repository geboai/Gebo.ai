/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.abstraction.layer.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GThinkingEvent;

/**
 * Pins the reasoning streamed to the user: fragments of the reasoning as it grows, never
 * one per tiny piece, the whole reasoning once, and a completing event when it ends.
 */
class ThinkingStreamTest {

	private static String texts(List<GThinkingEvent> events) {
		final StringBuilder out = new StringBuilder();
		events.forEach(e -> out.append(e.getText()));
		return out.toString();
	}

	@Test
	void theReasoningIsStreamedInFragmentsOnce() {
		final ThinkingStream stream = new ThinkingStream();
		final List<GThinkingEvent> events = new ArrayList<>();
		String whole = "";
		for (int i = 0; i < 60; i++) {
			whole += "word" + i + " ";
			events.addAll(stream.delta("word" + i + " "));
		}
		events.addAll(stream.complete());

		assertEquals(whole, texts(events), "every piece once, in order");
		assertTrue(events.size() < 15, "fragments, not one event per piece: " + events.size());
		assertTrue(events.get(events.size() - 1).isCompleted());
		assertEquals(1, events.stream().filter(GThinkingEvent::isCompleted).count());
	}

	@Test
	void aLineEndSendsTheFragmentAtOnce() {
		final ThinkingStream stream = new ThinkingStream();

		assertTrue(stream.delta("short").isEmpty());
		assertEquals("short thought\n", texts(stream.delta(" thought\n")));
	}

	@Test
	void theReasoningWrittenBetweenTagsIsStreamedAsGiven() {
		final ThinkingStream stream = new ThinkingStream();
		final List<GThinkingEvent> events = new ArrayList<>(stream.delta("Let me consider "));
		events.addAll(stream.delta("the question.\n"));
		events.addAll(stream.complete());

		assertEquals("Let me consider the question.\n", texts(events));
	}

	@Test
	void noReasoningNoEvents() {
		final ThinkingStream stream = new ThinkingStream();

		assertTrue(stream.delta("").isEmpty());
		assertTrue(stream.complete().isEmpty(), "nothing to complete");
	}

	@Test
	void aLaterReasoningStartsAgain() {
		final ThinkingStream stream = new ThinkingStream();
		stream.delta("first round of reasoning\n");
		stream.complete();

		final List<GThinkingEvent> second = stream.delta("second\n");
		assertEquals("second\n", texts(second), "a new reasoning, not the end of the first");
		assertFalse(stream.complete().isEmpty());
	}
}
