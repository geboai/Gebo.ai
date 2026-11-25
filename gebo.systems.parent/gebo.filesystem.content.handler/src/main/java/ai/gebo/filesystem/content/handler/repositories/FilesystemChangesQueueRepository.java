/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.filesystem.content.handler.repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import ai.gebo.filesystem.content.handler.GFilesystemChangesQueueEntry;

/**
 * The stored changes of the files of the filesystem data sources that follow them,
 * waiting to be handed to the ingestion.
 */
public interface FilesystemChangesQueueRepository extends MongoRepository<GFilesystemChangesQueueEntry, String> {
	/** The changes waiting for a data source, the oldest first. */
	List<GFilesystemChangesQueueEntry> findByEndpointCodeOrderByDetectedAtAscPartAsc(String endpointCode);

	/** Removes what waits for every data source but the given ones. */
	void deleteByEndpointCodeNotIn(Collection<String> endpointCodes);
}
