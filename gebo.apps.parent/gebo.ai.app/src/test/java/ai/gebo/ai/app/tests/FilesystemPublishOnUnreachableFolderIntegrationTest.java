/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.ai.app.tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import ai.gebo.application.messaging.workflow.GStandardWorkflow;
import ai.gebo.application.messaging.workflow.GWorkflowType;
import ai.gebo.filesystem.content.handler.GFilesystemProjectEndpoint;

/**
 * Certifies what a publication of a filesystem data source does when its folder
 * cannot be reached - a network share that dropped - over the real path: the data
 * source is published with its files, the folder is taken away, and it is published
 * again. The documents of the data source are not told deleted because of the outage.
 */
public class FilesystemPublishOnUnreachableFolderIntegrationTest extends AbstractFilesystemChangesSyncIntegrationTest {

	@Test
	public void aPublicationThatCannotReachTheFolderDeletesNothing() throws Exception {
		final GFilesystemProjectEndpoint endpoint = dataSource("filesystem data source published while its share is down",
				null);
		final Path folder = folderOf(endpoint);
		final Path note = folder.resolve("note.txt");
		final Path minutes = folder.resolve("minutes.txt");
		Files.writeString(note, "The meeting is on Monday.");
		Files.writeString(minutes, "All agreed on the plan of the quarter.");
		runAndWaitDoneCheckingResults(endpoint, 2, true);
		assertNotNull(documentOf(note));
		assertNotNull(documentOf(minutes));

		final Path away = folder.resolveSibling(folder.getFileName() + "-away");
		Files.move(folder, away);
		try {
			try {
				ingestionJobService.executeSyncJob(endpoint, null, GWorkflowType.STANDARD.name(),
						GStandardWorkflow.INGESTION.name());
			} catch (Exception unreachable) {
				LOGGER.info("The publication told it could not reach the folder: " + unreachable);
			}
			Thread.sleep(5000);
			assertFalse(Boolean.TRUE.equals(documentOf(note).getDeleted()),
					"a publication that cannot reach the folder does not tell its files deleted");
			assertFalse(Boolean.TRUE.equals(documentOf(minutes).getDeleted()));
		} finally {
			Files.move(away, folder);
		}
		cleanPersistent(endpoint);
	}
}
