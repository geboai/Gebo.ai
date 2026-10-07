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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import ai.gebo.architecture.documents.cache.config.CacheOrphansCleanupConfig;
import ai.gebo.architecture.documents.cache.repository.ChunkingSessionRepository;
import ai.gebo.architecture.documents.cache.repository.DocumentCacheEntryRepository;
import ai.gebo.architecture.documents.cache.repository.DocumentChunkOperationRepository;
import ai.gebo.architecture.documents.cache.service.impl.model.ChunkingSession;
import ai.gebo.architecture.documents.cache.service.impl.model.DocumentCacheEntry;
import ai.gebo.architecture.documents.cache.service.impl.model.DocumentChunkOperation;
import ai.gebo.config.service.IGGeboConfigService;

/**
 * Pins what is released after the grace period: the cached chunks whose session no
 * longer exists or never had one, their files, and the files no record names; never what a living session holds, nor a file younger than the grace period.
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

	@BeforeEach
	void aCleanerWithAFiveMinutesGrace() throws Exception {
		Files.createDirectories(work.resolve(".CHCACHE"));
		Files.createDirectories(work.resolve(".FCACHE"));
		operations = mock(DocumentChunkOperationRepository.class);
		copies = mock(DocumentCacheEntryRepository.class);
		sessions = mock(ChunkingSessionRepository.class);
		when(sessions.findById(anyString())).thenReturn(Optional.empty());
		when(sessions.findById("alive")).thenReturn(Optional.of(new ChunkingSession()));
		when(copies.findByLastAccessedLessThan(any())).thenReturn(Stream.of());
		when(copies.findAll()).thenReturn(List.of());
		IGGeboConfigService configService = mock(IGGeboConfigService.class);
		when(configService.getGeboWorkDirectory()).thenReturn(work.toString());
		cleaner = new OrphanedCacheEntriesCleaner(operations, copies, sessions, configService,
				new CacheOrphansCleanupConfig());
	}

	private Path file(String folder, String name, long modifiedMillis) throws Exception {
		final Path file = work.resolve(folder).resolve(name);
		Files.writeString(file, "x");
		Files.setLastModifiedTime(file, FileTime.fromMillis(modifiedMillis));
		return file;
	}

	private DocumentChunkOperation operation(String id, String session) throws Exception {
		final DocumentChunkOperation operation = new DocumentChunkOperation();
		operation.setId(id);
		operation.setChunkingSessionId(session);
		operation.setChunkSetsList(List.of(id + "-set"));
		file(".CHCACHE", id + "-set", old);
		return operation;
	}

	@Test
	void theChunksOfASessionGoneOrNeverHadAreReleasedThoseOfALivingOneKept() throws Exception {
		final DocumentChunkOperation gone = operation("gone", "disposed-session");
		final DocumentChunkOperation none = operation("none", null);
		final DocumentChunkOperation living = operation("living", "alive");
		when(operations.findByLastAccessedLessThan(any())).thenReturn(Stream.of(gone, none, living));
		when(operations.findAll()).thenReturn(List.of(living));

		final OrphanedCacheEntriesCleaner.Released released = cleaner.releaseOrphans(now);

		assertEquals(2, released.chunkOperations());
		verify(operations).delete(gone);
		verify(operations).delete(none);
		verify(operations, never()).delete(living);
		assertFalse(Files.exists(work.resolve(".CHCACHE").resolve("gone-set")));
		assertFalse(Files.exists(work.resolve(".CHCACHE").resolve("none-set")));
		assertTrue(Files.exists(work.resolve(".CHCACHE").resolve("living-set")));
	}

	@Test
	void aFileNoRecordNamesIsDeletedOnlyOnceOlderThanTheGrace() throws Exception {
		when(operations.findByLastAccessedLessThan(any())).thenReturn(Stream.of());
		final DocumentChunkOperation recorded = new DocumentChunkOperation();
		recorded.setChunkSetsList(List.of("recorded-set"));
		when(operations.findAll()).thenReturn(List.of(recorded));
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
	void theGraceIsFiveMinutesUnlessConfigured() {
		assertEquals(300_000L, new CacheOrphansCleanupConfig().graceMillis());
		final CacheOrphansCleanupConfig configured = new CacheOrphansCleanupConfig();
		configured.setGraceSeconds(60);
		assertEquals(60_000L, configured.graceMillis());
	}
}
