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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.architecture.documents.cache.config.GeboDocumentsCacheConfig;
import ai.gebo.architecture.documents.cache.model.DocumentChunkingResponse;
import ai.gebo.architecture.documents.cache.model.DocumentChunksSet;
import ai.gebo.architecture.documents.cache.repository.ChunkingSessionRepository;
import ai.gebo.architecture.documents.cache.repository.DocumentChunkOperationRepository;
import ai.gebo.architecture.documents.cache.service.DocumentCacheAccessException;
import ai.gebo.architecture.documents.cache.service.IDocumentsCacheService;
import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;
import ai.gebo.architecture.documents.cache.service.impl.model.ChunkingSession;
import ai.gebo.architecture.documents.cache.service.impl.model.DocumentChunkOperation;
import ai.gebo.architecture.multithreading.IGeboThreadManager;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.architecture.search.service.IKeywordMatcherService;
import ai.gebo.config.service.IGGeboConfigService;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.system.ingestion.IGAIDocumentMetaDataEnricher;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler;
import ai.gebo.system.ingestion.IGLanguageDetector;
import reactor.core.scheduler.Schedulers;
import tools.jackson.databind.ObjectMapper;

/**
 * Pins which cached chunks the indexing steps read: the ones of their chunking session,
 * else the most recent ones, never an operation whose chunk files are gone (written by
 * another instance in its own work directory, or cleaned up).
 */
class CachedChunksSelectionTest {

	@TempDir
	Path workDirectory;

	private DocumentChunkOperationRepository operations;
	private ChunkingSessionRepository sessions;
	private DocumentsChunkServiceImpl service;
	private GDocumentReference document;

	@BeforeEach
	void aServiceOnATemporaryWorkDirectory() throws Exception {
		Files.createDirectories(workDirectory.resolve(".CHCACHE"));
		IGGeboConfigService configService = mock(IGGeboConfigService.class);
		when(configService.getGeboWorkDirectory()).thenReturn(workDirectory.toString());
		IGeboThreadManager threads = mock(IGeboThreadManager.class);
		when(threads.getScheduler()).thenReturn(Schedulers.immediate());
		operations = mock(DocumentChunkOperationRepository.class);
		sessions = mock(ChunkingSessionRepository.class);
		when(sessions.findById(anyString())).thenReturn(Optional.of(new ChunkingSession()));
		service = new DocumentsChunkServiceImpl(mock(IDocumentsCacheService.class), configService, operations,
				mock(IGAIDocumentMetaDataEnricher.class), mock(IGDocumentReferenceIngestionHandler.class),
				mock(IGDocumentReferenceFactory.class), threads, mock(IGPersistentObjectManager.class),
				mock(GeboDocumentsCacheConfig.class), sessions, operations, mock(IKeywordMatcherService.class),
				mock(IGLanguageDetector.class));
		document = new GDocumentReference();
		document.setCode("kb/project/source/book.pdf");
	}

	/** An operation of a session, its chunks in one file, written or not in the work directory. */
	private DocumentChunkOperation operation(String id, String session, long createdMillis, boolean fileWritten)
			throws Exception {
		final DocumentChunkOperation operation = new DocumentChunkOperation();
		operation.setId(id);
		operation.setChunkingSessionId(session);
		operation.setCreated(new Date(createdMillis));
		operation.setOriginalDocumentCode(document.getCode());
		operation.setChunkSetsList(List.of(id + "-set"));
		if (fileWritten) {
			final DocumentChunksSet set = new DocumentChunksSet();
			set.setChunks(List.of());
			new ObjectMapper().writeValue(workDirectory.resolve(".CHCACHE").resolve(id + "-set").toFile(), set);
		}
		when(operations.findById(id)).thenReturn(Optional.of(operation));
		return operation;
	}

	@Test
	void theChunksOfTheSessionAreReadNotAnOperationWhoseFilesAreGone() throws Exception {
		final DocumentChunkOperation orphan = operation("orphan", "other-instance-session", 1_000L, false);
		final DocumentChunkOperation current = operation("current", "job-session", 2_000L, true);
		when(operations.findByOriginalDocumentCode(document.getCode())).thenReturn(List.of(orphan, current));

		final DocumentChunkingResponse response = service.getCachedChunkSet(document, "job-session");

		assertEquals("current", response.getId());
	}

	@Test
	void anOperationReusedFromAnotherSessionIsReadWhenTheSessionHasNone() throws Exception {
		final DocumentChunkOperation older = operation("older", "session-a", 1_000L, true);
		final DocumentChunkOperation newer = operation("newer", "session-b", 2_000L, true);
		when(operations.findByOriginalDocumentCode(document.getCode())).thenReturn(List.of(older, newer));

		assertEquals("newer", service.getCachedChunkSet(document, "job-session").getId(), "the most recent");
	}

	@Test
	void noOperationWithItsFilesIsACacheMiss() throws Exception {
		final DocumentChunkOperation orphan = operation("orphan", "job-session", 1_000L, false);
		when(operations.findByOriginalDocumentCode(document.getCode())).thenReturn(List.of(orphan));

		assertThrows(DocumentCacheAccessException.class, () -> service.getCachedChunkSet(document, "job-session"));
	}

	@Test
	void disposingASessionAlreadyDisposedIsNothing() {
		when(sessions.findById("gone")).thenReturn(Optional.empty());

		service.disposeChunkingSession("gone");
		service.disposeChunkingSession(null);

		verify(operations, never()).deleteByChunkingSessionId(any());
		verify(sessions, never()).deleteById(any());
	}

	@Test
	void aSessionIsDisposedOnTheThreadOfACancelledRequestWhichStaysInterrupted() throws Exception {
		// MongoDB refuses to work on an interrupted thread ("Interrupted waiting for lock")
		when(sessions.findById("cancelled-session")).thenAnswer(call -> {
			if (Thread.currentThread().isInterrupted()) {
				throw new IllegalStateException("Interrupted waiting for lock");
			}
			return Optional.of(new ChunkingSession());
		});
		final DocumentChunkOperation written = operation("written", "cancelled-session", 1_000L, true);
		when(operations.findByChunkingSessionId("cancelled-session")).thenReturn(java.util.stream.Stream.of(written));
		final Path file = workDirectory.resolve(".CHCACHE").resolve("written-set");

		Thread.currentThread().interrupt();
		final boolean stillInterrupted;
		try {
			service.disposeChunkingSession("cancelled-session");
		} finally {
			stillInterrupted = Thread.interrupted();
		}

		assertFalse(Files.exists(file), "its chunk files deleted");
		verify(operations).deleteByChunkingSessionId("cancelled-session");
		verify(sessions).deleteById("cancelled-session");
		assertTrue(stillInterrupted, "the caller still sees its request cancelled");
	}

	@Test
	void aReadDoesNotWriteAgainARecordDisposedMeanwhile() throws Exception {
		final DocumentChunkOperation disposed = operation("disposed", "job-session", 1_000L, true);
		when(operations.findByOriginalDocumentCode(document.getCode())).thenReturn(List.of(disposed));
		when(operations.existsById("disposed")).thenReturn(false);

		service.getCachedChunkSet(document, "job-session");

		verify(operations, never()).save(any());
	}

	@Test
	void aReadRefreshesARecordStillThere() throws Exception {
		final DocumentChunkOperation alive = operation("alive", "job-session", 1_000L, true);
		when(operations.findByOriginalDocumentCode(document.getCode())).thenReturn(List.of(alive));
		when(operations.existsById("alive")).thenReturn(true);

		service.getCachedChunkSet(document, "job-session");

		verify(operations).save(alive);
	}

	@Test
	void theSessionOfAFinishedJobIsDisposedByItsId() {
		final IDocumentsChunkService chunking = mock(IDocumentsChunkService.class);
		when(chunking.retrieveChunkingSession("job:J1")).thenReturn("session-uuid");
		final ChunkingSessionDisposerReceiverFactory factory = new ChunkingSessionDisposerReceiverFactory(null);
		final ai.gebo.application.messaging.model.GMessageEnvelope<ai.gebo.core.messages.GFinishedWorkflowPayload> envelope = new ai.gebo.application.messaging.model.GMessageEnvelope<>();
		final ai.gebo.core.messages.GFinishedWorkflowPayload finished = new ai.gebo.core.messages.GFinishedWorkflowPayload();
		finished.setJobId("J1");
		envelope.setPayload(finished);

		factory.new DisposeChunkingSessionForJobReceiver(chunking).accept(envelope);

		verify(chunking).disposeChunkingSession("session-uuid");
	}

	@Test
	void aJobWithoutChunkingSessionDisposesNothing() {
		final IDocumentsChunkService chunking = mock(IDocumentsChunkService.class);
		final ChunkingSessionDisposerReceiverFactory factory = new ChunkingSessionDisposerReceiverFactory(null);
		final ai.gebo.application.messaging.model.GMessageEnvelope<ai.gebo.core.messages.GFinishedWorkflowPayload> envelope = new ai.gebo.application.messaging.model.GMessageEnvelope<>();
		final ai.gebo.core.messages.GFinishedWorkflowPayload finished = new ai.gebo.core.messages.GFinishedWorkflowPayload();
		finished.setJobId("J2");
		envelope.setPayload(finished);

		factory.new DisposeChunkingSessionForJobReceiver(chunking).accept(envelope);

		verify(chunking, never()).disposeChunkingSession(any());
	}
}
