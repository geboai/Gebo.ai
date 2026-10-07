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
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.ArgumentCaptor;

import ai.gebo.architecture.documents.access.IGDocumentContentStreamer;
import ai.gebo.architecture.documents.access.StreamingPurpose;
import ai.gebo.architecture.documents.cache.config.DocumentsCacheTtlConfig;
import ai.gebo.architecture.documents.cache.repository.DocumentCacheEntryRepository;
import ai.gebo.architecture.documents.cache.service.impl.model.DocumentCacheEntry;
import ai.gebo.config.service.IGGeboConfigService;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.model.base.TypedInputStream;

/**
 * Pins the life of a cached document copy: deleted with its file once not accessed for
 * its time to live (5 minutes unless configured), and a stale copy leaving no file
 * behind.
 */
class DocumentsCacheTtlTest {

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
		cache = new DocumentsCacheServiceImpl(configService, entries, streamer, new DocumentsCacheTtlConfig());
		document = new GDocumentReference();
		document.setCode("web/page");
	}

	private Path fileOf(DocumentCacheEntry entry) {
		return workDirectory.resolve(".FCACHE").resolve(entry.getBinaryDocumentName());
	}

	@Test
	void aCopyNotAccessedForItsTimeToLiveIsDeletedWithItsFile() throws Exception {
		cache.streamDocument(StreamingPurpose.INGESTING, document).getInputStream().close();
		final DocumentCacheEntry copy = saved.get(0);
		when(entries.findByLastAccessedLessThan(any())).thenReturn(Stream.of(copy));
		final long before = System.currentTimeMillis();

		cache.expireCopies();

		assertFalse(Files.exists(fileOf(copy)));
		final ArgumentCaptor<Date> threshold = ArgumentCaptor.forClass(Date.class);
		verify(entries).deleteByLastAccessedLessThan(threshold.capture());
		final long age = before - threshold.getValue().getTime();
		assertTrue(age >= 299_000 && age <= 301_000, "five minutes after the last access: " + age);
	}

	@Test
	void theTimeToLiveIsFiveMinutesUnlessConfigured() {
		assertEquals(300_000L, new DocumentsCacheTtlConfig().ttlMillis());
		final DocumentsCacheTtlConfig configured = new DocumentsCacheTtlConfig();
		configured.setTtlSeconds(30);
		assertEquals(30_000L, configured.ttlMillis());
	}

	@Test
	void aStaleCopyLeavesNoFileBehind() throws Exception {
		cache.streamDocument(StreamingPurpose.INGESTING, document).getInputStream().close();
		final DocumentCacheEntry stale = saved.get(0);
		final Path staleFile = fileOf(stale);
		when(entries.findById("web/page")).thenReturn(Optional.of(stale));
		// the document changed after the copy was made
		document.setModificationDate(new Date(System.currentTimeMillis() + 60_000));

		cache.streamDocument(StreamingPurpose.INGESTING, document).getInputStream().close();

		assertFalse(Files.exists(staleFile), "the stale copy's file is deleted with its record");
		assertTrue(Files.exists(fileOf(saved.get(saved.size() - 1))));
	}
}
