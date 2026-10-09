/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.documents.cache.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.workflow.IWorkflowRouter;
import ai.gebo.architecture.documents.cache.messaging.IDocumentChunkingMessagesReceiverFactoryComponent;
import ai.gebo.architecture.documents.cache.model.ChunkingParams;
import ai.gebo.architecture.documents.cache.model.DocumentChunkingResponse;
import ai.gebo.architecture.documents.cache.service.IChunkingParametersProvider;
import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;
import ai.gebo.core.messages.GContentsProcessingStatusUpdatePayload;
import ai.gebo.core.messages.GDocumentReferencePayload;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knlowledgebase.model.projects.GCentralizedProjectEndpoint;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.model.base.IGComponentOriginatedDocument;

/**
 * Pins the chunker's part of the re-ingestion change detection: a document whose
 * date and size are the ones last ingested is not read again; one read again
 * whose text hash is the last ingested one is discarded; any other is sent on
 * with the hash of its text, the hash the indexing acknowledges back to its
 * content handler.
 */
class ReingestionChangeDetectionTest {
	private static final Date LAST_MODIFIED = new Date(1_700_000_000_000l);
	private static final long LAST_SIZE = 1234l;
	private static final String LAST_HASH = "AAAA";

	private IDocumentsChunkService chunkingService;
	private IWorkflowRouter workflowRouter;
	private DocumentChunkingBatchReceiver receiver;

	@BeforeEach
	void setUp() throws Exception {
		chunkingService = mock(IDocumentsChunkService.class);
		workflowRouter = mock(IWorkflowRouter.class);
		final IChunkingParametersProvider parameters = mock(IChunkingParametersProvider.class);
		when(parameters.provideChunkingParams(any())).thenReturn(new ChunkingParams());
		when(chunkingService.retrieveChunkingSession(anyString())).thenReturn("session");
		receiver = new DocumentChunkingBatchReceiver(chunkingService, parameters, workflowRouter,
				mock(IDocumentChunkingMessagesReceiverFactoryComponent.class), mock(IGMessageBroker.class));
	}

	private static GDocumentReferencePayload payload(Date modified, Long size, String lastHash) {
		final GDocumentReference document = new GDocumentReference();
		document.setCode("doc-1");
		document.setModificationDate(modified);
		document.setFileSize(size);
		final GDocumentReferencePayload payload = new GDocumentReferencePayload();
		payload.setDocumentReference(document);
		payload.setJobId("job-1");
		payload.setRequiresEmbeddingHandshake(true);
		final GKnowledgeBase knowledgeBase = new GKnowledgeBase();
		knowledgeBase.setCode("kb");
		payload.setKnowledgeBase(knowledgeBase);
		final GProject project = new GProject();
		project.setCode("project");
		payload.setProject(project);
		payload.setEndPoint(new GCentralizedProjectEndpoint());
		if (lastHash != null) {
			payload.setLastIngestedHash(lastHash);
			payload.setLastIngestedModificationDate(LAST_MODIFIED);
			payload.setLastIngestedFileSize(LAST_SIZE);
		}
		return payload;
	}

	private static GMessageEnvelope<GDocumentReferencePayload> envelope(GDocumentReferencePayload payload) {
		final GMessageEnvelope<GDocumentReferencePayload> envelope = new GMessageEnvelope<>();
		envelope.setPayload(payload);
		return envelope;
	}

	private void chunkedWithHash(String hash) throws Exception {
		final DocumentChunkingResponse response = new DocumentChunkingResponse();
		response.setEmpty(false);
		response.setTotalChunksNumber(3);
		response.setContentHash(hash);
		when(chunkingService.prepareChunks(any(IGComponentOriginatedDocument.class), any(), anyString()))
				.thenReturn(response);
	}

	@Test
	void aDocumentNeverIngestedIsChunkedAndSentOnWithItsTextHash() throws Exception {
		chunkedWithHash("BBBB");
		final GDocumentReferencePayload payload = payload(LAST_MODIFIED, LAST_SIZE, null);

		final GContentsProcessingStatusUpdatePayload status = receiver.acceptSingleMessage(envelope(payload));

		assertEquals(1, status.getBatchSentToNextStep());
		assertEquals(0, status.getBatchDiscardedInput());
		assertEquals("BBBB", payload.getHash(), "the hash acknowledged back once indexed");
		verify(workflowRouter, times(1)).routeToNextSteps(any(), any(), any(), any(), any());
	}

	@Test
	void aDocumentWithTheLastIngestedDateAndSizeIsNotReadAgain() throws Exception {
		final GDocumentReferencePayload payload = payload(new Date(LAST_MODIFIED.getTime()), LAST_SIZE, LAST_HASH);

		final GContentsProcessingStatusUpdatePayload status = receiver.acceptSingleMessage(envelope(payload));

		assertEquals(1, status.getBatchDocumentsInput());
		assertEquals(1, status.getBatchDiscardedInput());
		assertEquals(0, status.getBatchSentToNextStep());
		verify(chunkingService, never()).prepareChunks(any(IGComponentOriginatedDocument.class), any(), anyString());
		verify(workflowRouter, never()).routeToNextSteps(any(), any(), any(), any(), any());
	}

	@Test
	void aTouchedDocumentWithTheSameTextIsReadAndDiscarded() throws Exception {
		chunkedWithHash(LAST_HASH);
		final GDocumentReferencePayload payload = payload(new Date(LAST_MODIFIED.getTime() + 60_000l), LAST_SIZE,
				LAST_HASH);

		final GContentsProcessingStatusUpdatePayload status = receiver.acceptSingleMessage(envelope(payload));

		verify(chunkingService, times(1)).prepareChunks(any(IGComponentOriginatedDocument.class), any(), anyString());
		assertEquals(1, status.getBatchDiscardedInput());
		assertEquals(0, status.getBatchSentToNextStep());
		verify(workflowRouter, never()).routeToNextSteps(any(), any(), any(), any(), any());
	}

	@Test
	void aDocumentOfAnotherSizeWithAnotherTextIsSentOn() throws Exception {
		chunkedWithHash("CCCC");
		final GDocumentReferencePayload payload = payload(LAST_MODIFIED, LAST_SIZE + 10, LAST_HASH);

		final GContentsProcessingStatusUpdatePayload status = receiver.acceptSingleMessage(envelope(payload));

		assertEquals(1, status.getBatchSentToNextStep());
		assertEquals(0, status.getBatchDiscardedInput());
		assertEquals("CCCC", payload.getHash());
		verify(workflowRouter, times(1)).routeToNextSteps(any(), any(), any(), any(), any());
	}

	@Test
	void aDocumentWithNoDateOrSizeIsAlwaysReadAndComparedByHash() throws Exception {
		chunkedWithHash(LAST_HASH);
		final GDocumentReferencePayload payload = payload(null, null, LAST_HASH);

		final GContentsProcessingStatusUpdatePayload status = receiver.acceptSingleMessage(envelope(payload));

		verify(chunkingService, times(1)).prepareChunks(any(IGComponentOriginatedDocument.class), any(), anyString());
		assertEquals(1, status.getBatchDiscardedInput(), "the same text: discarded");
	}

	@Test
	void aDocumentOfASourceGivingNoSizeWithTheLastIngestedDateIsNotReadAgain() throws Exception {
		// a wiki page: a remote date, no size
		final GDocumentReferencePayload payload = payload(new Date(LAST_MODIFIED.getTime()), null, LAST_HASH);
		payload.setLastIngestedFileSize(null);

		final GContentsProcessingStatusUpdatePayload status = receiver.acceptSingleMessage(envelope(payload));

		assertEquals(1, status.getBatchDiscardedInput());
		verify(chunkingService, never()).prepareChunks(any(IGComponentOriginatedDocument.class), any(), anyString());
	}

	@Test
	void aSizeKnownOnOneSideOnlyIsAChange() throws Exception {
		chunkedWithHash("DDDD");
		final GDocumentReferencePayload sizeNow = payload(new Date(LAST_MODIFIED.getTime()), LAST_SIZE, LAST_HASH);
		sizeNow.setLastIngestedFileSize(null);
		final GDocumentReferencePayload sizeBefore = payload(new Date(LAST_MODIFIED.getTime()), null, LAST_HASH);

		assertEquals(1, receiver.acceptSingleMessage(envelope(sizeNow)).getBatchSentToNextStep());
		assertEquals(1, receiver.acceptSingleMessage(envelope(sizeBefore)).getBatchSentToNextStep());
		verify(chunkingService, times(2)).prepareChunks(any(IGComponentOriginatedDocument.class), any(), anyString());
	}

	@Test
	void chunksWithNoHashAreSentOn() throws Exception {
		// chunks recorded before the hash was (reused), or a sample: nothing tells the text
		// is the same
		chunkedWithHash(null);
		final GDocumentReferencePayload payload = payload(new Date(LAST_MODIFIED.getTime() + 1), LAST_SIZE,
				LAST_HASH);

		final GContentsProcessingStatusUpdatePayload status = receiver.acceptSingleMessage(envelope(payload));

		assertEquals(1, status.getBatchSentToNextStep());
		assertNull(payload.getHash());
	}
}
