/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.officeplugin.pipeline.agents;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.AdditionalContent;

class OfficeDocumentStreamSplitterTest {

	private static String feed(OfficeDocumentStreamSplitter splitter, String... chunks) {
		StringBuilder chat = new StringBuilder();
		for (String chunk : chunks) {
			chat.append(splitter.accept(chunk));
		}
		chat.append(splitter.complete());
		return chat.toString();
	}

	@Test
	void noDocumentPart_streamsAllAsChat() {
		OfficeDocumentStreamSplitter s = new OfficeDocumentStreamSplitter();
		String chat = feed(s, "Hello ", "world, ", "here is your answer.");
		assertEquals("Hello world, here is your answer.", chat);
		assertFalse(s.hasDocuments());
	}

	@Test
	void singleDocumentPart_splitsChatAndDocument() {
		OfficeDocumentStreamSplitter s = new OfficeDocumentStreamSplitter();
		String chat = feed(s, "Here you go.\n",
				"<GEBO-DOCUMENT name=\"intro\" contentType=\"text/markdown\">\n## Intro\nBody line\n</GEBO-DOCUMENT>",
				"\nDone.");
		assertEquals("Here you go.\n\nDone.", chat);
		List<AdditionalContent> docs = s.getDocuments();
		assertEquals(1, docs.size());
		assertEquals("intro", docs.get(0).getName());
		assertEquals("text/markdown", docs.get(0).getContentType());
		assertEquals("## Intro\nBody line", docs.get(0).getContent());
	}

	@Test
	void markerSplitAcrossChunks_isReassembled() {
		OfficeDocumentStreamSplitter s = new OfficeDocumentStreamSplitter();
		String chat = feed(s, "Chat text <GEBO-DOC", "UMENT name=\"a\" contentType=\"text/html\">", "<p>hi</p>",
				"</GEBO-DOC", "UMENT> tail");
		assertEquals("Chat text  tail", chat);
		assertEquals(1, s.getDocuments().size());
		assertEquals("a", s.getDocuments().get(0).getName());
		assertEquals("text/html", s.getDocuments().get(0).getContentType());
		assertEquals("<p>hi</p>", s.getDocuments().get(0).getContent());
	}

	@Test
	void multipleDocumentParts() {
		OfficeDocumentStreamSplitter s = new OfficeDocumentStreamSplitter();
		String chat = feed(s, "A", "<GEBO-DOCUMENT name=\"one\">first</GEBO-DOCUMENT>", "B",
				"<GEBO-DOCUMENT name=\"two\">second</GEBO-DOCUMENT>", "C");
		assertEquals("ABC", chat);
		assertEquals(2, s.getDocuments().size());
		assertEquals("first", s.getDocuments().get(0).getContent());
		assertEquals("second", s.getDocuments().get(1).getContent());
		assertEquals(OfficeDocumentStreamSplitter.DEFAULT_CONTENT_TYPE, s.getDocuments().get(0).getContentType());
	}

	@Test
	void unterminatedDocument_isKeptOnComplete() {
		OfficeDocumentStreamSplitter s = new OfficeDocumentStreamSplitter();
		String chat = feed(s, "Chat ", "<GEBO-DOCUMENT name=\"partial\">unfinished body");
		assertEquals("Chat ", chat);
		assertTrue(s.hasDocuments());
		assertEquals("unfinished body", s.getDocuments().get(0).getContent());
		assertEquals("partial", s.getDocuments().get(0).getName());
	}

	@Test
	void missingName_getsSyntheticName() {
		OfficeDocumentStreamSplitter s = new OfficeDocumentStreamSplitter();
		feed(s, "<GEBO-DOCUMENT>body</GEBO-DOCUMENT>");
		assertEquals(1, s.getDocuments().size());
		assertEquals("document-1", s.getDocuments().get(0).getName());
	}
}
