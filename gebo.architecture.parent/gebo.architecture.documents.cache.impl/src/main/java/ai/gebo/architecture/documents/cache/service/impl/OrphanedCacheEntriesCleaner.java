/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.documents.cache.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ai.gebo.architecture.documents.cache.config.CacheOrphansCleanupConfig;
import ai.gebo.architecture.documents.cache.repository.ChunkingSessionRepository;
import ai.gebo.architecture.documents.cache.repository.DocumentCacheEntryRepository;
import ai.gebo.architecture.documents.cache.repository.DocumentChunkOperationRepository;
import ai.gebo.architecture.documents.cache.service.impl.model.DocumentCacheEntry;
import ai.gebo.architecture.documents.cache.service.impl.model.DocumentChunkOperation;
import ai.gebo.config.service.IGGeboConfigService;
import lombok.AllArgsConstructor;

/**
 * Deletes what the chunking sessions left behind, older than the grace period (see
 * {@link CacheOrphansCleanupConfig}): the cached chunks whose session no longer exists
 * (or never had one), their records and their files, and the files of the cache folders
 * no record names (written before their record by a chunking that never recorded it, a
 * copy whose record was replaced, or left by a record another instance deleted). The
 * cached document copies expire by their own time to live (see DocumentsCacheServiceImpl).
 */
@Component
@AllArgsConstructor
public class OrphanedCacheEntriesCleaner {
	private static final Logger LOGGER = LoggerFactory.getLogger(OrphanedCacheEntriesCleaner.class);
	static final String CHUNKS_FOLDER = ".CHCACHE";
	static final String DOCUMENTS_FOLDER = ".FCACHE";

	private final DocumentChunkOperationRepository chunkOperations;
	private final DocumentCacheEntryRepository documentCopies;
	private final ChunkingSessionRepository sessions;
	private final IGGeboConfigService configService;
	private final CacheOrphansCleanupConfig config;

	/** What a check deleted. */
	record Released(int chunkOperations, int unreferencedFiles) {
		int total() {
			return chunkOperations + unreferencedFiles;
		}
	}

	@Scheduled(initialDelayString = "#{@cacheOrphansCleanupConfig.graceMillis()}", fixedDelayString = "#{@cacheOrphansCleanupConfig.graceMillis()}")
	public void releaseOrphans() {
		try {
			final Released released = releaseOrphans(new Date());
			if (released.total() > 0) {
				LOGGER.info("Released the orphaned cache entries older than " + config.getGraceSeconds() + " s: "
						+ released.chunkOperations() + " cached chunk operation(s), " + released.unreferencedFiles()
						+ " unreferenced file(s)");
			} else if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("releaseOrphans() no orphaned cache entry older than " + config.getGraceSeconds() + " s");
			}
		} catch (Throwable th) {
			LOGGER.error("Exception releasing the orphaned cache entries", th);
		}
	}

	Released releaseOrphans(Date now) throws IOException {
		final Date threshold = new Date(now.getTime() - config.graceMillis());
		final Map<String, Boolean> alive = new HashMap<>();
		final Path work = Path.of(configService.getGeboWorkDirectory());

		final List<DocumentChunkOperation> orphanOperations;
		try (Stream<DocumentChunkOperation> stale = chunkOperations.findByLastAccessedLessThan(threshold)) {
			orphanOperations = stale.filter(op -> !sessionExists(op.getChunkingSessionId(), alive)).toList();
		}
		for (DocumentChunkOperation operation : orphanOperations) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Releasing the cached chunks of " + operation.getOriginalDocumentCode() + " (operation "
						+ operation.getId() + ", session " + operation.getChunkingSessionId() + ")");
			}
			for (String chunkSet : operation.getChunkSetsList()) {
				delete(work.resolve(CHUNKS_FOLDER).resolve(chunkSet));
			}
			chunkOperations.delete(operation);
		}


		// the files no record names, older than the grace period
		final Set<String> chunkFiles = new HashSet<>();
		chunkOperations.findAll().forEach(op -> chunkFiles.addAll(op.getChunkSetsList()));
		final Set<String> copyFiles = new HashSet<>();
		documentCopies.findAll().forEach(copy -> {
			if (copy.getBinaryDocumentName() != null) {
				copyFiles.add(copy.getBinaryDocumentName());
			}
		});
		final int unreferenced = deleteUnreferenced(work.resolve(CHUNKS_FOLDER), chunkFiles, threshold)
				+ deleteUnreferenced(work.resolve(DOCUMENTS_FOLDER), copyFiles, threshold);
		return new Released(orphanOperations.size(), unreferenced);
	}

	private boolean sessionExists(String sessionId, Map<String, Boolean> alive) {
		if (sessionId == null) {
			return false;
		}
		return alive.computeIfAbsent(sessionId, id -> sessions.findById(id).isPresent());
	}

	private int deleteUnreferenced(Path folder, Set<String> referenced, Date threshold) throws IOException {
		if (!Files.isDirectory(folder)) {
			return 0;
		}
		final List<Path> unreferenced;
		try (Stream<Path> files = Files.list(folder)) {
			unreferenced = files.filter(Files::isRegularFile)
					.filter(file -> !referenced.contains(file.getFileName().toString()))
					.filter(file -> olderThan(file, threshold)).toList();
		}
		unreferenced.forEach(this::delete);
		if (!unreferenced.isEmpty() && LOGGER.isDebugEnabled()) {
			LOGGER.debug("Deleted " + unreferenced.size() + " unreferenced file(s) of " + folder);
		}
		return unreferenced.size();
	}

	private static boolean olderThan(Path file, Date threshold) {
		try {
			return Files.getLastModifiedTime(file).toMillis() < threshold.getTime();
		} catch (IOException e) {
			return false;
		}
	}

	private void delete(Path file) {
		try {
			Files.deleteIfExists(file);
		} catch (IOException e) {
			LOGGER.warn("Cannot delete the cache file " + file, e);
		}
	}
}
