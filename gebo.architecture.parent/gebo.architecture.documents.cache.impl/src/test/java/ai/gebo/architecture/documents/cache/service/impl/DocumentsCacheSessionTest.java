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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import ai.gebo.architecture.documents.access.IGDocumentContentStreamer;
import ai.gebo.architecture.documents.access.StreamingPurpose;
import ai.gebo.architecture.documents.cache.repository.DocumentCacheEntryRepository;
import ai.gebo.architecture.documents.cache.service.impl.model.DocumentCacheEntry;
import ai.gebo.config.service.IGGeboConfigService;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.model.base.TypedInputStream;

/**
 * Pins the documents a chunking session downloads: kept for it and released with it,
 * their files too.
 */
class DocumentsCacheSessionTest {

	@TempDir
	Path workDirectory;

	private DocumentCacheEntryRepository entries;
	private DocumentsCacheServiceImpl cache;
	private GDocumentReference document;
	private final List<DocumentCacheEntry> saved = new ArrayList<>();

	@BeforeEach
	void aCacheOnATemporaryWorkDirectory() throws Exception {
		IGGeboConfigService configService = mock(IGGeboConfigService.class);
		when(configService.getGeboWorkDirectory()).thenReturn(workDirectory.toString());
		IGDocumentContentStreamer streamer = mock(IGDocumentContentStreamer.class);
		when(streamer.streamContent(any(StreamingPurpose.class), any())).thenAnswer(
				call -> TypedInputStream.of(new ByteArrayInputStream("a page".getBytes()), "text/html", "html"));
		entries = mock(DocumentCacheEntryRepository.class);
		when(entries.findById(any())).thenReturn(Optional.empty());
		when(entries.save(any())).thenAnswer(call -> {
			saved.add(call.getArgument(0));
			return call.getArgument(0);
		});
		cache = new DocumentsCacheServiceImpl(configService, entries, streamer);
		document = new GDocumentReference();
		document.setCode("web/page");
	}

	private Path fileOf(DocumentCacheEntry entry) {
		return workDirectory.resolve(".FCACHE").resolve(entry.getBinaryDocumentName());
	}

	@Test
	void aDocumentDownloadedForASessionIsKeptForIt() throws Exception {
		cache.streamDocument(StreamingPurpose.INGESTING, document, "tool-session").getInputStream().close();

		assertEquals(1, saved.size());
		assertEquals("tool-session", saved.get(0).getChunkingSessionId());
		assertTrue(Files.exists(fileOf(saved.get(0))));
	}

	@Test
	void releasingTheSessionDeletesItsCopiesAndTheirFiles() throws Exception {
		cache.streamDocument(StreamingPurpose.INGESTING, document, "tool-session").getInputStream().close();
		final DocumentCacheEntry entry = saved.get(0);
		when(entries.findByChunkingSessionId("tool-session")).thenReturn(java.util.stream.Stream.of(entry));

		cache.releaseSession("tool-session");

		assertFalse(Files.exists(fileOf(entry)));
		verify(entries).deleteByChunkingSessionId("tool-session");
	}

	@Test
	void aCopyServedToAnotherSessionIsNowKeptForIt() throws Exception {
		cache.streamDocument(StreamingPurpose.INGESTING, document, "first").getInputStream().close();
		final DocumentCacheEntry entry = saved.get(0);
		when(entries.findById("web/page")).thenReturn(Optional.of(entry));

		cache.streamDocument(StreamingPurpose.INGESTING, document, "second").getInputStream().close();

		assertEquals("second", entry.getChunkingSessionId());
	}

	@Test
	void aStaleCopyLeavesNoFileBehind() throws Exception {
		cache.streamDocument(StreamingPurpose.INGESTING, document, "first").getInputStream().close();
		final DocumentCacheEntry stale = saved.get(0);
		final Path staleFile = fileOf(stale);
		when(entries.findById("web/page")).thenReturn(Optional.of(stale));
		// the document changed after the copy was made
		document.setModificationDate(new java.util.Date(System.currentTimeMillis() + 60_000));

		cache.streamDocument(StreamingPurpose.INGESTING, document, "second").getInputStream().close();

		assertFalse(Files.exists(staleFile), "the stale copy's file is deleted with its record");
		assertTrue(Files.exists(fileOf(saved.get(saved.size() - 1))));
	}
}
