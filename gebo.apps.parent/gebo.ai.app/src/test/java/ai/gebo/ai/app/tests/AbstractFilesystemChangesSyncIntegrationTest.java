/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.ai.app.tests;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import ai.gebo.filesystem.content.handler.GFilesystemChangesQueueEntry;
import ai.gebo.filesystem.content.handler.GFilesystemContentManagementSystem;
import ai.gebo.filesystem.content.handler.GFilesystemProjectEndpoint;
import ai.gebo.filesystem.content.handler.IGFilesystemContentManagementSystemHandler;
import ai.gebo.filesystem.content.handler.repositories.FilesystemChangesQueueRepository;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus.JobType;
import ai.gebo.model.virtualfs.VFilesystemReference;

/**
 * What the tests of the changes of the files of the filesystem data sources share: a
 * data source on a folder of its own, as an administrator leaves it after publishing
 * it, and what the platform holds of its files.
 */
public abstract class AbstractFilesystemChangesSyncIntegrationTest extends AbstractBaseTestLLmsIntegrationTests {
	@Autowired
	protected IGFilesystemContentManagementSystemHandler filesystemHandler;
	@Autowired
	protected FilesystemChangesQueueRepository storedChanges;

	/**
	 * A published filesystem data source on a new folder.
	 *
	 * @param follows whether it follows the changes of its files, null as a data source
	 *                saved before the flag existed
	 */
	protected GFilesystemProjectEndpoint dataSource(String description, Boolean follows) throws Exception {
		GFilesystemProjectEndpoint endpoint = createAndPersist(description, GFilesystemProjectEndpoint.class);
		final GFilesystemContentManagementSystem system = filesystemHandler.getSystem(endpoint);
		final Path folder = Path.of(localFolderDiscoveryService.getLocalPersistentFolder(system, endpoint));
		Files.createDirectories(folder);
		endpoint.setPath(List.of(VFilesystemReference.from(folder)));
		endpoint.setPublished(true);
		endpoint.setSyncOnFilesModifications(follows);
		return persistentObjectManager.update(endpoint);
	}

	protected static Path folderOf(GFilesystemProjectEndpoint endpoint) {
		return Path.of(VFilesystemReference.absolutePathOf(endpoint.getPath().get(0)));
	}

	/** The document the platform holds of a file, null when it holds none. */
	protected GDocumentReference documentOf(Path file) {
		try (Stream<GDocumentReference> found = documentReferenceRepository
				.findByAbsolutePathIn(List.of(file.toAbsolutePath().toString()))) {
			return found.findFirst().orElse(null);
		}
	}

	/** The changes stored for a data source, waiting to be handed to the ingestion. */
	protected List<GFilesystemChangesQueueEntry> storedFor(GFilesystemProjectEndpoint endpoint) {
		return storedChanges.findByEndpointCodeOrderByDetectedAtAscPartAsc(endpoint.getCode());
	}

	/** How many sessions of the ingestion the data source had. */
	protected long sessionsOf(GFilesystemProjectEndpoint endpoint) {
		return statusRepo.findByProjectEndpointReferenceClassNameAndProjectEndpointReferenceCodeAndJobType(
				endpoint.getClass().getName(), endpoint.getCode(), JobType.CONTENTS_READING_VECTORIZING,
				PageRequest.of(0, 1)).getTotalElements();
	}

	/** Waits until the condition holds; whether it did within the time. */
	protected static boolean within(long seconds, BooleanSupplier condition) throws InterruptedException {
		final long until = System.currentTimeMillis() + seconds * 1000;
		while (System.currentTimeMillis() < until) {
			if (condition.getAsBoolean()) {
				return true;
			}
			Thread.sleep(500);
		}
		return condition.getAsBoolean();
	}
}
