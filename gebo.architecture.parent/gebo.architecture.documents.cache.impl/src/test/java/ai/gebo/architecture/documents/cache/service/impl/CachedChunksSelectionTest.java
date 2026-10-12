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
 * Pins the chunk cache's sessions: the chunks a session reads are its own (else a twin
 * session's of the same reference), never another caller's; another session's chunks of
 * the same document version and parameters are reused in the session's own record; a
 * disposal is logical, and a late read produces the released chunks again.
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
		document.setModificationDate(new Date(5_000L));
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
	void theChunksAnotherCallerMadeOfTheDocumentAreNeverRead() throws Exception {
		// a tool's sample or keyword filtered chunking of the same document: its chunking
		// parameters are not recorded, so it can not stand for the job's chunks
		final DocumentChunkOperation toolSample = operation("tool-sample", "search-session", 2_000L, true);
		when(operations.findByOriginalDocumentCode(document.getCode())).thenReturn(List.of(toolSample));

		assertThrows(DocumentCacheAccessException.class, () -> service.getCachedChunkSet(document, "job-session"));
		assertThrows(DocumentCacheAccessException.class, () -> service.getCachedChunkSet(document, null),
				"no session, no chunks of whoever made them");
	}

	@Test
	void theChunksOfAnotherSessionOfTheSameJobAreRead() throws Exception {
		final ChunkingSession jobSession = session("job-session", "job:J1");
		final ChunkingSession twin = session("job-session-twin", "job:J1");
		when(sessions.findByChunkingReference("job:J1")).thenReturn(List.of(jobSession, twin));
		final DocumentChunkOperation ofTheTwin = operation("of-the-twin", "job-session-twin", 1_000L, true);
		final DocumentChunkOperation toolSample = operation("tool-sample", "search-session", 2_000L, true);
		when(operations.findByOriginalDocumentCode(document.getCode())).thenReturn(List.of(ofTheTwin, toolSample));

		assertEquals("of-the-twin", service.getCachedChunkSet(document, "job-session").getId(),
				"the job's session opened twice, not the newer chunks of another caller");
	}

	/** A session with its reference, found by its id. */
	private ChunkingSession session(String id, String reference) {
		final ChunkingSession session = new ChunkingSession();
		session.setCode(id);
		session.setChunkingReference(reference);
		when(sessions.findById(id)).thenReturn(Optional.of(session));
		return session;
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

		Thread.currentThread().interrupt();
		final boolean stillInterrupted;
		try {
			service.disposeChunkingSession("cancelled-session");
		} finally {
			stillInterrupted = Thread.interrupted();
		}

		verify(sessions).save(org.mockito.ArgumentMatchers.argThat(ChunkingSession::disposed));
		assertTrue(stillInterrupted, "the caller still sees its request cancelled");
	}

	@Test
	void aDisposalKeepsTheRecordsAndRestartsTheGraceOfTheFiles() throws Exception {
		final ChunkingSession living = session("living-session", "job:J2");
		final DocumentChunkOperation written = operation("written", "living-session", 1_000L, true);
		final Path file = workDirectory.resolve(".CHCACHE").resolve("written-set");
		Files.setLastModifiedTime(file, java.nio.file.attribute.FileTime.fromMillis(1_000L));
		when(operations.findByChunkingSessionId("living-session")).thenReturn(java.util.stream.Stream.of(written));

		service.disposeChunkingSession("living-session");

		assertTrue(living.disposed(), "disposed logically");
		verify(sessions).save(living);
		verify(operations, never()).deleteByChunkingSessionId(any());
		verify(sessions, never()).deleteById(any());
		assertTrue(Files.exists(file), "the files go after the grace period");
		assertTrue(Files.getLastModifiedTime(file).toMillis() > 1_000L, "their grace starts at the disposal");

		service.disposeChunkingSession("living-session");
		verify(sessions).save(living);
	}

	@Test
	void anotherSessionsChunksOfTheSameVersionAndParametersAreReusedInItsOwnRecord() throws Exception {
		final java.util.Map<String, DocumentChunkOperation> stored = storedRecords();
		final DocumentChunkOperation made = producible("made", "tool-session", 1_000L, true);
		stored.put(made.getId(), made);
		when(operations.findByOriginalDocumentCode(document.getCode())).thenReturn(List.of(made));

		final DocumentChunkingResponse response = service.getChunkSet(document, params(), "job-session");

		final DocumentChunkOperation own = stored.get(response.getId());
		assertEquals("job-session", own.getChunkingSessionId(), "the session's own record");
		assertEquals(made.getChunkSetsList(), own.getChunkSetsList(), "naming the same files");
		assertEquals("tool-session", made.getChunkingSessionId(), "the other session's record untouched");
		assertEquals("job-session", response.getCurrentChunkSet().getChunkingSessionId(), "read as the reader's");
	}

	@Test
	void reusedChunksCarryTheHashOfTheTextTheyWereMadeFrom() throws Exception {
		final java.util.Map<String, DocumentChunkOperation> stored = storedRecords();
		final DocumentChunkOperation made = producible("made", "tool-session", 1_000L, true);
		made.setContentHash("0123ABCD");
		stored.put(made.getId(), made);
		when(operations.findByOriginalDocumentCode(document.getCode())).thenReturn(List.of(made));

		final DocumentChunkingResponse response = service.getChunkSet(document, params(), "job-session");

		assertEquals("0123ABCD", response.getContentHash());
		assertEquals("0123ABCD", stored.get(response.getId()).getContentHash(), "on the session's own record");
	}

	@Test
	void noChunksAreReusedFromAnotherVersionOrOtherParameters() throws Exception {
		final java.util.Map<String, DocumentChunkOperation> stored = storedRecords();
		final DocumentChunkOperation olderVersion = producible("older-version", "tool-session", 1_000L, true);
		olderVersion.setDocumentModificationDate(new Date(1L));
		final DocumentChunkOperation sampled = producible("sampled", "tool-session", 2_000L, true);
		sampled.getChunkingParams().setSamplingMode(true);
		stored.put(olderVersion.getId(), olderVersion);
		stored.put(sampled.getId(), sampled);
		when(operations.findByOriginalDocumentCode(document.getCode())).thenReturn(List.of(olderVersion, sampled));

		service.getChunkSet(document, params(), "job-session");

		verify(operations, never()).insert(any(DocumentChunkOperation.class));
	}

	@Test
	void aLateReadOfADisposedSessionProducesItsChunksAgain() throws Exception {
		final java.util.Map<String, DocumentChunkOperation> stored = storedRecords();
		final DocumentChunkOperation released = producible("released", "job-session", 1_000L, false);
		final DocumentChunkOperation alive = producible("alive", "tool-session", 2_000L, true);
		stored.put(released.getId(), released);
		stored.put(alive.getId(), alive);
		when(operations.findByOriginalDocumentCode(document.getCode())).thenReturn(List.of(released, alive));

		final DocumentChunkingResponse response = service.getCachedChunkSet(document, "job-session");

		assertEquals("job-session", stored.get(response.getId()).getChunkingSessionId());
		verify(operations).delete(released);
	}

	@Test
	void aReadWhoseNextFileIsGoneGoesOnFromTheSameSetProducedAgain() throws Exception {
		final java.util.Map<String, DocumentChunkOperation> stored = storedRecords();
		final DocumentChunkOperation half = producible("half", "job-session", 1_000L, true);
		half.setChunkSetsList(List.of("half-set", "half-gone-set"));
		final DocumentChunkOperation whole = producible("whole", "tool-session", 2_000L, true);
		whole.setChunkSetsList(List.of("whole-set", "whole-second-set"));
		writeSet("whole-second-set");
		stored.put(half.getId(), half);
		stored.put(whole.getId(), whole);
		when(operations.findByOriginalDocumentCode(document.getCode())).thenReturn(List.of(half, whole));

		final DocumentChunkingResponse response = service.getNextChunkSet(document, "half", "half-gone-set",
				"job-session");

		final DocumentChunkOperation again = stored.get(response.getId());
		assertEquals("job-session", again.getChunkingSessionId());
		assertEquals("whole-second-set", response.getCurrentChunkSet().getId(), "the second set, as asked");
	}

	@Test
	void aSessionCreatedMeanwhileByAConcurrentCallerIsReportedAsExisting() {
		when(sessions.findByChunkingReference("job:J3")).thenReturn(List.of());
		when(sessions.insert(any(ChunkingSession.class)))
				.thenThrow(new org.springframework.dao.DuplicateKeyException("chunkingReference_unique"));

		assertThrows(IllegalStateException.class, () -> service.createChunkingSession("job:J3"));
	}

	@Test
	void creatingAgainTheSessionOfAProcedureThatEndedReopensItOnlyALivingOneIsADuplicate() {
		final ChunkingSession ended = session("request-session", "request:R1");
		ended.setLogicalDeletionTimestamp(new Date());
		when(sessions.findByChunkingReference("request:R1")).thenReturn(List.of(ended));

		assertEquals("request-session", service.createChunkingSession("request:R1"), "the same request run again");
		assertFalse(ended.disposed(), "reopened");
		verify(sessions).save(ended);
		verify(sessions, never()).insert(any(ChunkingSession.class));

		assertThrows(IllegalStateException.class, () -> service.createChunkingSession("request:R1"),
				"a living session with the reference: a duplicate");
	}

	/** Records inserted or stubbed, found by their id. */
	private java.util.Map<String, DocumentChunkOperation> storedRecords() {
		final java.util.Map<String, DocumentChunkOperation> stored = new java.util.HashMap<>();
		when(operations.findById(anyString())).thenAnswer(call -> Optional.ofNullable(stored.get(call.getArgument(0))));
		when(operations.insert(any(DocumentChunkOperation.class))).thenAnswer(call -> {
			final DocumentChunkOperation inserted = call.getArgument(0);
			stored.put(inserted.getId(), inserted);
			return inserted;
		});
		when(operations.existsById(anyString())).thenAnswer(call -> stored.containsKey(call.getArgument(0)));
		return stored;
	}

	/** The chunking parameters of the records made by {@link #producible}. */
	private static ai.gebo.architecture.documents.cache.model.ChunkingParams params() {
		return new ai.gebo.architecture.documents.cache.model.ChunkingParams(
				ai.gebo.architecture.documents.cache.model.ChunkingPolicy.SPLIT_CHUNKS, null, null, null,
				List.of(ai.gebo.architecture.documents.cache.model.TextChunkingSpecs.DEFAULT_SPECS), true, 50000,
				-1l, false);
	}

	/** A record of the document's version with its chunking parameters recorded. */
	private DocumentChunkOperation producible(String id, String session, long createdMillis, boolean fileWritten)
			throws Exception {
		final DocumentChunkOperation operation = operation(id, session, createdMillis, fileWritten);
		operation.setChunkingParams(params());
		operation.setDocumentModificationDate(document.getModificationDate());
		return operation;
	}

	private void writeSet(String name) throws Exception {
		final DocumentChunksSet set = new DocumentChunksSet();
		set.setId(name);
		set.setChunks(List.of());
		new ObjectMapper().writeValue(workDirectory.resolve(".CHCACHE").resolve(name).toFile(), set);
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
