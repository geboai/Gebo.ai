/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.a2aserver.runtime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.a2aproject.sdk.spec.DataPart;
import org.a2aproject.sdk.spec.FilePart;
import org.a2aproject.sdk.spec.FileWithBytes;
import org.a2aproject.sdk.spec.FileWithUri;
import org.a2aproject.sdk.spec.Part;
import org.a2aproject.sdk.spec.TextPart;
import org.junit.jupiter.api.Test;

/**
 * Pins what is taken in from an A2A message: the words, the files actually
 * sent, and what the caller stated about the run - and what is deliberately not
 * taken.
 */
class A2AInboundResourcesTest {

	private static FilePart fileOf(String name, String type, String content) {
		final String bytes = Base64.getEncoder().encodeToString(content.getBytes(StandardCharsets.UTF_8));
		return new FilePart(new FileWithBytes(type, name, bytes));
	}

	@Test
	void theWordsOfEveryTextPartAreTheQuestion() {
		A2AInboundResources read = A2AInboundResources
				.of(List.of(new TextPart("Read the attachment"), new TextPart("and summarise it.")));

		assertEquals("Read the attachment\nand summarise it.", read.getText());
		assertFalse(read.hasResources());
	}

	@Test
	void aFileTheCallerSentIsTakenInWithItsNameAndType() throws Exception {
		A2AInboundResources read = A2AInboundResources
				.of(List.of(new TextPart("What does this say?"), fileOf("notes.txt", "text/plain", "hello there")));

		assertEquals("What does this say?", read.getText());
		assertTrue(read.hasResources());
		assertEquals(1, read.getFiles().size());
		assertEquals("notes.txt", read.getFiles().get(0).getOriginalFilename());
		assertEquals("text/plain", read.getFiles().get(0).getContentType());
		assertEquals("hello there", new String(read.getFiles().get(0).getBytes(), StandardCharsets.UTF_8));
		assertTrue(read.getNotTaken().isEmpty());
	}

	@Test
	void aFileNamedByALinkIsNotFetchedAndTheCallerIsTold() {
		// Fetching whatever a caller points at is a decision for whoever runs the
		// installation, not something to do quietly on its behalf.
		A2AInboundResources read = A2AInboundResources
				.of(List.of(new FilePart(new FileWithUri("text/plain", "remote.txt", "https://example.invalid/x"))));

		assertTrue(read.getFiles().isEmpty(), "nothing is fetched");
		assertEquals(1, read.getNotTaken().size(), "and the caller is told it was left out");
		assertTrue(read.getNotTaken().get(0).contains("remote.txt"));
	}

	@Test
	void bytesThatAreNotReadableAreLeftOutRatherThanLosingTheQuestion() {
		A2AInboundResources read = A2AInboundResources
				.of(List.of(new TextPart("still a question"), new FilePart(new FileWithBytes("text/plain", "broken.txt", "!!not base64!!"))));

		assertEquals("still a question", read.getText(), "the question survives a bad attachment");
		assertTrue(read.getFiles().isEmpty());
		assertEquals(1, read.getNotTaken().size());
	}

	@Test
	void whatTheCallerStatedAboutTheRunIsTakenIn() {
		A2AInboundResources read = A2AInboundResources.of(List.of(new TextPart("Only in these"),
				new DataPart(Map.of(A2AInboundResources.KNOWLEDGE_BASES_KEY, List.of("kb-one", "kb-two"),
						A2AInboundResources.DOCUMENTS_KEY, List.of("doc-1")))));

		assertEquals(List.of("kb-one", "kb-two"), read.getKnowledgeBases());
		assertEquals(List.of("doc-1"), read.getDocuments());
		assertTrue(read.hasResources());
	}

	@Test
	void asingleStatedValueCountsAsOne() {
		A2AInboundResources read = A2AInboundResources
				.of(List.of(new DataPart(Map.of(A2AInboundResources.KNOWLEDGE_BASES_KEY, "kb-one"))));

		assertEquals(List.of("kb-one"), read.getKnowledgeBases());
	}

	@Test
	void aMessageOfNothingIsReadWithoutFailing() {
		A2AInboundResources read = A2AInboundResources.of(null);

		assertEquals("", read.getText());
		assertFalse(read.hasResources());
		assertTrue(read.getNotTaken().isEmpty());
	}

	@Test
	void aDataPartThatIsNotASetOfValuesIsLeftOut() {
		A2AInboundResources read = A2AInboundResources.of(List.<Part<?>>of(new DataPart("just a string")));

		assertFalse(read.hasResources());
		assertEquals(1, read.getNotTaken().size());
	}

	/** A file named by a link is fetched only from a host this installation listed. */
	@org.junit.jupiter.api.Test
	void aLinkedFileIsFetchedOnlyFromAListedHost() throws Exception {
		final com.sun.net.httpserver.HttpServer server = com.sun.net.httpserver.HttpServer
				.create(new java.net.InetSocketAddress("127.0.0.1", 0), 0);
		server.createContext("/files/chart.png", exchange -> {
			exchange.getResponseHeaders().add("Content-Type", "image/png");
			exchange.sendResponseHeaders(200, 3);
			try (java.io.OutputStream out = exchange.getResponseBody()) {
				out.write(new byte[] { 1, 2, 3 });
			}
		});
		server.start();
		try {
			final String link = "http://127.0.0.1:" + server.getAddress().getPort() + "/files/chart.png";
			final List<org.a2aproject.sdk.spec.Part<?>> parts = List
					.of(new org.a2aproject.sdk.spec.FilePart(new org.a2aproject.sdk.spec.FileWithUri(null, null, link)));

			final A2AInboundResources listed = A2AInboundResources.of(parts,
					new A2ALinkedFilesFetcher(List.of("127.0.0.1")));
			org.junit.jupiter.api.Assertions.assertEquals(1, listed.getFiles().size());
			org.junit.jupiter.api.Assertions.assertEquals("chart.png", listed.getFiles().get(0).getOriginalFilename());
			org.junit.jupiter.api.Assertions.assertEquals("image/png", listed.getFiles().get(0).getContentType());
			org.junit.jupiter.api.Assertions.assertArrayEquals(new byte[] { 1, 2, 3 }, listed.getFiles().get(0).getBytes());
			org.junit.jupiter.api.Assertions.assertTrue(listed.getNotTaken().isEmpty());

			// no host listed, as an installation is at first: the link is not called
			final A2AInboundResources unlisted = A2AInboundResources.of(parts, new A2ALinkedFilesFetcher(List.of()));
			org.junit.jupiter.api.Assertions.assertTrue(unlisted.getFiles().isEmpty());
			org.junit.jupiter.api.Assertions.assertEquals(1, unlisted.getNotTaken().size());
		} finally {
			server.stop(0);
		}
	}
}
