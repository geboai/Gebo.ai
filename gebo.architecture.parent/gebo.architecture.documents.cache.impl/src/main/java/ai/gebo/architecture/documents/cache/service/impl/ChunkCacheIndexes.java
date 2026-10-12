/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.documents.cache.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.stereotype.Component;

import ai.gebo.architecture.documents.cache.service.impl.model.ChunkingSession;
import ai.gebo.architecture.documents.cache.service.impl.model.DocumentChunkOperation;
import jakarta.annotation.PostConstruct;

/**
 * The indexes of the chunk cache, created at startup: the automatic index creation of
 * Spring Data is off, so the ones declared on the entities do not exist. The reference
 * of a chunking session is unique (two batches of a job starting together can not open
 * it twice); the records are looked up by document and by session.
 */
@Component
public class ChunkCacheIndexes {
	private static final Logger LOGGER = LoggerFactory.getLogger(ChunkCacheIndexes.class);
	static final String UNIQUE_REFERENCE_INDEX = "chunkingReference_unique";
	private final ObjectProvider<MongoOperations> mongo;

	public ChunkCacheIndexes(ObjectProvider<MongoOperations> mongo) {
		this.mongo = mongo;
	}

	@PostConstruct
	public void createIndexes() {
		final MongoOperations operations = mongo.getIfAvailable();
		if (operations == null) {
			LOGGER.warn("No MongoDB operations: the chunk cache indexes are not created");
			return;
		}
		try {
			operations.indexOps(ChunkingSession.class).createIndex(
					new Index().on("chunkingReference", Direction.ASC).unique().named(UNIQUE_REFERENCE_INDEX));
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Chunking session references unique (index " + UNIQUE_REFERENCE_INDEX + ")");
			}
		} catch (RuntimeException e) {
			// sessions already sharing a reference: the creation stays check then insert
			LOGGER.warn("Cannot make the chunking session references unique (sessions sharing a reference?): "
					+ e.getMessage());
		}
		try {
			operations.indexOps(DocumentChunkOperation.class)
					.createIndex(new Index().on("originalDocumentCode", Direction.ASC));
			operations.indexOps(DocumentChunkOperation.class)
					.createIndex(new Index().on("chunkingSessionId", Direction.ASC));
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Chunk records indexed by document and by session");
			}
		} catch (RuntimeException e) {
			LOGGER.warn("Cannot index the chunk records: " + e.getMessage());
		}
	}
}
