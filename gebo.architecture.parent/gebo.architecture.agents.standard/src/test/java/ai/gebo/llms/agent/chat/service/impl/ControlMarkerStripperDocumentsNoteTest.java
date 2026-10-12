/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.chat.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ai.gebo.llms.agent.chat.service.impl.AgenticLoopReactiveAgentServiceImpl.ControlMarkerStripper;

/**
 * Pins the notes naming an answer's documents a model copies into its answer: removed
 * from the streamed text, also when split across chunks, and the text around kept.
 */
class ControlMarkerStripperDocumentsNoteTest {

	private static String streamed(String... chunks) {
		final ControlMarkerStripper stripper = new ControlMarkerStripper();
		final StringBuilder out = new StringBuilder();
		for (String chunk : chunks) {
			out.append(stripper.accept(chunk));
		}
		return out.append(stripper.complete()).toString();
	}

	@Test
	void aNoteInOneChunkIsRemoved() {
		assertEquals("Fohat is the cosmic electricity.\n\nMore.", streamed(
				"Fohat is the cosmic electricity.\n\n[Documents this answer rested on, read then: a.pdf; b.pdf]\n\nMore."));
	}

	@Test
	void aNoteSplitAcrossChunksIsRemoved() {
		assertEquals("Fohat.\nNext", streamed("Fohat. [Docu", "ments this answer rest", "ed on, read then: a.p",
				"df; b.pdf]", "\nNext"));
	}

	@Test
	void aNoteTheStreamEndsInIsRemoved() {
		assertEquals("Fohat.", streamed("Fohat.\n\n[Documents this answer rested on, read then: a.pdf; b."));
	}

	@Test
	void aTextLikeTheStartOfANoteIsKept() {
		assertEquals("[Documents of the chat] are many [Docu", streamed("[Documents of the chat] are ", "many [Docu"));
	}
}
