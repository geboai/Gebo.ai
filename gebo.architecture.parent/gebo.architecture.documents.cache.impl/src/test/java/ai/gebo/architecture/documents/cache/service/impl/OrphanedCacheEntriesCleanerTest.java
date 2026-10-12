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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import ai.gebo.architecture.documents.cache.config.CacheOrphansCleanupConfig;
import ai.gebo.architecture.documents.cache.repository.ChunkingSessionRepository;
import ai.gebo.architecture.documents.cache.repository.DocumentCacheEntryRepository;
import ai.gebo.architecture.documents.cache.repository.DocumentChunkOperationRepository;
import ai.gebo.architecture.documents.cache.service.impl.model.ChunkingSession;
import ai.gebo.architecture.documents.cache.service.impl.model.DocumentChunkOperation;
import ai.gebo.config.service.IGGeboConfigService;

/**
 * Pins what is released: the sessions disposed longer than the retention with their
 * records, the records without session older than the grace period, the chunk files no
 * record of a living session names once older than the grace period (a file shared with
 * a living session stays, a file touched meanwhile stays), and the documents cache files
 * no record names.
 */
class OrphanedCacheEntriesCleanerTest {

	@TempDir
	Path work;

	private DocumentChunkOperationRepository operations;
	private DocumentCacheEntryRepository copies;
	private ChunkingSessionRepository sessions;
	private OrphanedCacheEntriesCleaner cleaner;
	private final Date now = new Date();
	private final long old = now.getTime() - 3_600_000L;
	private final List<ChunkingSession> allSessions = new ArrayList<>();
	private final List<DocumentChunkOperation> allRecords = new ArrayList<>();

	@BeforeEach
	void aCleanerWithAFiveMinutesGraceAndATwoDaysRetention() throws Exception {
		Files.createDirectories(work.resolve(".CHCACHE"));
		Files.createDirectories(work.resolve(".FCACHE"));
		operations = mock(DocumentChunkOperationRepository.class);
		copies = mock(DocumentCacheEntryRepository.class);
		sessions = mock(ChunkingSessionRepository.class);
		when(sessions.findAll()).thenReturn(allSessions);
		when(operations.findAll()).thenAnswer(call -> new ArrayList<>(allRecords));
		when(operations.findByLastAccessedLessThan(any())).thenAnswer(call -> allRecords.stream()
				.filter(op -> op.getLastAccessed().before(call.getArgument(0))).toList().stream());
		when(copies.findAll()).thenReturn(List.of());
		IGGeboConfigService configService = mock(IGGeboConfigService.class);
		when(configService.getGeboWorkDirectory()).thenReturn(work.toString());
		cleaner = new OrphanedCacheEntriesCleaner(operations, copies, sessions, configService,
				new CacheOrphansCleanupConfig());
	}

	private ChunkingSession session(String id, Date disposedAt) {
		final ChunkingSession session = new ChunkingSession();
		session.setCode(id);
		session.setChunkingReference("ref:" + id);
		session.setLogicalDeletionTimestamp(disposedAt);
		allSessions.add(session);
		return session;
	}

	private Path file(String folder, String name, long modifiedMillis) throws Exception {
		final Path file = work.resolve(folder).resolve(name);
		Files.writeString(file, "x");
		Files.setLastModifiedTime(file, FileTime.fromMillis(modifiedMillis));
		return file;
	}

	private DocumentChunkOperation record(String id, String session, String... files) {
		final DocumentChunkOperation operation = new DocumentChunkOperation();
		operation.setId(id);
		operation.setChunkingSessionId(session);
		operation.setChunkSetsList(List.of(files));
		operation.setLastAccessed(new Date(old));
		allRecords.add(operation);
		return operation;
	}

	@Test
	void aSessionDisposedLongerThanTheRetentionGoesWithItsRecords() throws Exception {
		session("expired", new Date(now.getTime() - 3L * 24 * 3600 * 1000));
		session("recently-disposed", new Date(now.getTime() - 3600 * 1000));
		session("living", null);

		assertEquals(1, cleaner.releaseOrphans(now).expiredSessions());

		verify(operations).deleteByChunkingSessionId("expired");
		verify(sessions).deleteById("expired");
		verify(sessions, never()).deleteById("recently-disposed");
		verify(sessions, never()).deleteById("living");
	}

	@Test
	void theRecordsWithoutSessionGoTheirFilesUnlessALivingSessionNamesThem() throws Exception {
		session("living", null);
		final DocumentChunkOperation gone = record("gone", "deleted-session", "only-gone-set", "shared-set");
		final DocumentChunkOperation none = record("none", null, "none-set");
		record("living-record", "living", "shared-set");
		file(".CHCACHE", "only-gone-set", old);
		file(".CHCACHE", "shared-set", old);
		file(".CHCACHE", "none-set", old);

		final OrphanedCacheEntriesCleaner.Released released = cleaner.releaseOrphans(now);

		assertEquals(2, released.chunkOperations());
		verify(operations).delete(gone);
		verify(operations).delete(none);
		assertFalse(Files.exists(work.resolve(".CHCACHE").resolve("only-gone-set")));
		assertFalse(Files.exists(work.resolve(".CHCACHE").resolve("none-set")));
		assertTrue(Files.exists(work.resolve(".CHCACHE").resolve("shared-set")), "a living session still uses it");
	}

	@Test
	void aDisposedSessionKeepsItsRecordsAndItsFilesGoOnceOlderThanTheGrace() throws Exception {
		session("disposed", new Date(now.getTime() - 3600 * 1000));
		final DocumentChunkOperation kept = record("kept", "disposed", "released-set", "just-disposed-set");
		final Path released = file(".CHCACHE", "released-set", old);
		final Path young = file(".CHCACHE", "just-disposed-set", now.getTime());

		assertEquals(0, cleaner.releaseOrphans(now).chunkOperations());

		verify(operations, never()).delete(kept);
		assertFalse(Files.exists(released), "no living session names it");
		assertTrue(Files.exists(young), "its grace restarted at the disposal");
	}

	@Test
	void aFileNoRecordNamesIsDeletedOnlyOnceOlderThanTheGrace() throws Exception {
		session("living", null);
		record("recorded", "living", "recorded-set");
		final Path oldOrphan = file(".CHCACHE", "written-before-its-record", old);
		final Path freshOrphan = file(".CHCACHE", "being-written-now", now.getTime());
		final Path recordedFile = file(".CHCACHE", "recorded-set", old);
		final Path oldCopy = file(".FCACHE", "copy-of-a-deleted-record", old);

		assertEquals(2, cleaner.releaseOrphans(now).unreferencedFiles());
		assertFalse(Files.exists(oldOrphan));
		assertFalse(Files.exists(oldCopy));
		assertTrue(Files.exists(freshOrphan), "younger than the grace period: a chunking may still record it");
		assertTrue(Files.exists(recordedFile));
	}

	@Test
	void theGraceIsFiveMinutesAndTheRetentionTwoDaysUnlessConfigured() {
		assertEquals(300_000L, new CacheOrphansCleanupConfig().graceMillis());
		assertEquals(2L * 24 * 3600 * 1000, new CacheOrphansCleanupConfig().retentionMillis());
		final CacheOrphansCleanupConfig configured = new CacheOrphansCleanupConfig();
		configured.setGraceSeconds(60);
		configured.setRetentionDays(0);
		assertEquals(60_000L, configured.graceMillis());
		assertEquals(60_000L, configured.retentionMillis(), "never shorter than the grace period");
	}

	@Test
	void nothingLeftBehindReleasesNothing() throws Exception {
		session("living", null);
		record("recorded", "living", "recorded-set");
		file(".CHCACHE", "recorded-set", old);

		assertEquals(0, cleaner.releaseOrphans(now).total());
		verify(operations, never()).delete(any(DocumentChunkOperation.class));
		assertTrue(Stream.of("recorded-set").allMatch(f -> Files.exists(work.resolve(".CHCACHE").resolve(f))));
	}
}
