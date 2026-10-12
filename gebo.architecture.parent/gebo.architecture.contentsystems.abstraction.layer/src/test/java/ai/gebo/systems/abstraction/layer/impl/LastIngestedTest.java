/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.systems.abstraction.layer.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import ai.gebo.core.messages.GContentEmbeddingHandshakePayload;
import ai.gebo.core.messages.GDocumentMessageFragmentPayload;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.systems.abstraction.layer.impl.repository.ContentHandshakeDataRepository;
import ai.gebo.systems.abstraction.layer.model.ContentHandshakeData;

/**
 * Pins what a content handler tells the chunker of a document's last ingestion:
 * the newest acknowledgement received, with the size of the document ingested;
 * an acknowledgement stored before the receiving date was recorded comes before
 * any received since; one not processed tells nothing.
 */
class LastIngestedTest {
	private ContentHandshakeDataRepository repository;
	private GContentDispatchingEvaluatorImpl evaluator;

	@BeforeEach
	void setUp() {
		repository = mock(ContentHandshakeDataRepository.class);
		evaluator = new GContentDispatchingEvaluatorImpl();
		evaluator.chRepository = repository;
	}

	private static ContentHandshakeData ack(String hash, Date received, Date modified, Boolean processed) {
		final ContentHandshakeData ack = new ContentHandshakeData();
		ack.setContentCode("doc-1");
		ack.setHash(hash);
		ack.setReceivedDate(received);
		ack.setModificationDate(modified);
		ack.setProcessed(processed);
		return ack;
	}

	@Test
	void theNewestAcknowledgementReceivedIsTheLastIngestion() {
		when(repository.findByContentCode("doc-1")).thenReturn(Stream.of(
				ack("LEGACY", null, new Date(9_000l), true), ack("FIRST", new Date(1_000l), new Date(100l), true),
				ack("SECOND", new Date(2_000l), new Date(50l), true), ack("FAILED", new Date(3_000l), null, false)));

		assertEquals("SECOND", evaluator.lastIngested("doc-1").getHash());
	}

	@Test
	void acknowledgementsStoredBeforeTheReceivingDateAreOrderedByModificationDate() {
		when(repository.findByContentCode("doc-1")).thenReturn(
				Stream.of(ack("OLD", null, new Date(1_000l), true), ack("NEWER", null, new Date(2_000l), true)));

		assertEquals("NEWER", evaluator.lastIngested("doc-1").getHash());
	}

	@Test
	void aDocumentNeverIngestedHasNoLastIngestion() {
		when(repository.findByContentCode("doc-1"))
				.thenReturn(Stream.of(ack("FAILED", new Date(1_000l), null, false)));

		assertNull(evaluator.lastIngested("doc-1"));
		assertNull(evaluator.lastIngested(null));
	}

	@Test
	void theAcknowledgementStoredKeepsTheSizeAndWhenItWasReceived() {
		final GDocumentReference document = new GDocumentReference();
		document.setCode("doc-1");
		document.setModificationDate(new Date(5_000l));
		document.setFileSize(4321l);
		final GDocumentMessageFragmentPayload indexed = new GDocumentMessageFragmentPayload();
		indexed.setDocumentReference(document);
		indexed.setHash("ABCD");

		evaluator.evaluateContentHandshake(GContentEmbeddingHandshakePayload.ack(indexed));

		final ArgumentCaptor<ContentHandshakeData> saved = ArgumentCaptor.forClass(ContentHandshakeData.class);
		Mockito.verify(repository).save(saved.capture());
		assertEquals("ABCD", saved.getValue().getHash());
		assertEquals(4321l, saved.getValue().getFileSize());
		assertEquals(new Date(5_000l), saved.getValue().getModificationDate());
		assertEquals(Boolean.TRUE, saved.getValue().getProcessed());
		org.junit.jupiter.api.Assertions.assertNotNull(saved.getValue().getReceivedDate());
	}
}
