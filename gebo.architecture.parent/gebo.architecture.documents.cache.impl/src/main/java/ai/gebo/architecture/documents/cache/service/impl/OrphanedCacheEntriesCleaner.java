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
import ai.gebo.architecture.documents.cache.service.impl.model.ChunkingSession;
import ai.gebo.architecture.documents.cache.service.impl.model.DocumentChunkOperation;
import ai.gebo.config.service.IGGeboConfigService;
import lombok.AllArgsConstructor;

/**
 * Deletes what the chunking sessions left behind (see {@link CacheOrphansCleanupConfig}):
 * <ul>
 * <li>the sessions disposed longer than the retention, with their records;</li>
 * <li>the records whose session no longer exists, or never had one, older than the grace
 * period;</li>
 * <li>the chunk files no record of a living session names, older than the grace period: a
 * chunk file may be named by the records of several sessions (one reusing another's
 * chunks), it stays while one of them lives. A disposed session's records keep naming
 * their files, which go: a late read produces them again;</li>
 * <li>the files of the documents cache folder no record names, older than the grace
 * period (the cached copies expire by their own time to live, see
 * DocumentsCacheServiceImpl).</li>
 * </ul>
 * A file's age is its last modified time, restarted when its session is disposed or a
 * session reuses it, and checked again just before the file is deleted.
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
	record Released(int expiredSessions, int chunkOperations, int unreferencedFiles) {
		int total() {
			return expiredSessions + chunkOperations + unreferencedFiles;
		}
	}

	@Scheduled(initialDelayString = "#{@cacheOrphansCleanupConfig.graceMillis()}", fixedDelayString = "#{@cacheOrphansCleanupConfig.graceMillis()}")
	public void releaseOrphans() {
		try {
			final Released released = releaseOrphans(new Date());
			if (released.total() > 0) {
				LOGGER.info("Released the cache entries left behind: " + released.expiredSessions()
						+ " session(s) disposed more than " + config.getRetentionDays() + " day(s) ago, "
						+ released.chunkOperations() + " cached chunk operation(s) without session, "
						+ released.unreferencedFiles() + " file(s) no living record names, older than "
						+ config.getGraceSeconds() + " s");
			} else if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("releaseOrphans() nothing to release");
			}
		} catch (Throwable th) {
			LOGGER.error("Exception releasing the orphaned cache entries", th);
		}
	}

	Released releaseOrphans(Date now) throws IOException {
		final Date threshold = new Date(now.getTime() - config.graceMillis());
		final Date retentionThreshold = new Date(now.getTime() - config.retentionMillis());
		final Path work = Path.of(configService.getGeboWorkDirectory());

		// the sessions, the ones disposed longer than the retention deleted with their records
		final Map<String, ChunkingSession> byId = new HashMap<>();
		int expiredSessions = 0;
		for (ChunkingSession session : sessions.findAll()) {
			if (session.getLogicalDeletionTimestamp() != null
					&& session.getLogicalDeletionTimestamp().before(retentionThreshold)) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Deleting the chunking session " + session.getCode() + " of "
							+ session.getChunkingReference() + ", disposed at " + session.getLogicalDeletionTimestamp()
							+ ", with its records");
				}
				chunkOperations.deleteByChunkingSessionId(session.getCode());
				sessions.deleteById(session.getCode());
				expiredSessions++;
			} else if (session.getCode() != null) {
				byId.put(session.getCode(), session);
			}
		}

		// the records whose session no longer exists, or never had one
		final List<DocumentChunkOperation> orphanOperations;
		try (Stream<DocumentChunkOperation> stale = chunkOperations.findByLastAccessedLessThan(threshold)) {
			orphanOperations = stale.filter(
					op -> op.getChunkingSessionId() == null || !byId.containsKey(op.getChunkingSessionId())).toList();
		}
		for (DocumentChunkOperation operation : orphanOperations) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Releasing the cached chunks of " + operation.getOriginalDocumentCode() + " (operation "
						+ operation.getId() + ", session " + operation.getChunkingSessionId() + ")");
			}
			// its files go below, unless a living session's record names them too
			chunkOperations.delete(operation);
		}

		// the files no record of a living session names, no record at all for the copies
		final Set<String> liveChunkFiles = new HashSet<>();
		chunkOperations.findAll().forEach(op -> {
			final ChunkingSession session = op.getChunkingSessionId() != null ? byId.get(op.getChunkingSessionId())
					: null;
			if (session != null && !session.disposed() && op.getChunkSetsList() != null) {
				liveChunkFiles.addAll(op.getChunkSetsList());
			}
		});
		final Set<String> copyFiles = new HashSet<>();
		documentCopies.findAll().forEach(copy -> {
			if (copy.getBinaryDocumentName() != null) {
				copyFiles.add(copy.getBinaryDocumentName());
			}
		});
		final int unreferenced = deleteUnreferenced(work.resolve(CHUNKS_FOLDER), liveChunkFiles, threshold)
				+ deleteUnreferenced(work.resolve(DOCUMENTS_FOLDER), copyFiles, threshold);
		return new Released(expiredSessions, orphanOperations.size(), unreferenced);
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
		int deleted = 0;
		for (Path file : unreferenced) {
			// touched meanwhile (a session reusing it, or disposed): its grace restarted
			if (olderThan(file, threshold)) {
				delete(file);
				deleted++;
			}
		}
		if (deleted > 0 && LOGGER.isDebugEnabled()) {
			LOGGER.debug("Deleted " + deleted + " unreferenced file(s) of " + folder);
		}
		return deleted;
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
