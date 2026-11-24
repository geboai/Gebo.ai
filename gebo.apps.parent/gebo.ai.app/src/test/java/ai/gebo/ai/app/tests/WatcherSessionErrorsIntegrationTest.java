/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */


package ai.gebo.ai.app.tests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.devtools.filewatch.ChangedFile;
import org.springframework.boot.devtools.filewatch.ChangedFiles;

import ai.gebo.filesystem.content.handler.GFilesystemContentManagementSystem;
import ai.gebo.filesystem.content.handler.GFilesystemProjectEndpoint;
import ai.gebo.filesystem.content.handler.IGFilesystemContentManagementSystemHandler;
import ai.gebo.jobs.services.IGeboInversionOfControlIngestionService;
import ai.gebo.jobs.services.IGeboInversionOfControlIngestionService.GConsumers;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.systems.abstraction.layer.NoContentConsumingSessionParam;

/**
 * Certifies that a session of the folder watcher records the files it could not read:
 * the session is opened and run as GFilesystemChangesHandlingService.onChange does, on a
 * file reported as added that is no longer there when it is read.
 */
public class WatcherSessionErrorsIntegrationTest extends AbstractBaseTestLLmsIntegrationTests {
	@Autowired
	IGFilesystemContentManagementSystemHandler filesystemHandler;

	@Autowired
	IGeboInversionOfControlIngestionService inversionOfControlIngestionService;

	@Test
	public void aChangedFileThatCannotBeReadIsAnErrorOfTheWatcherSession() throws Exception {
		GFilesystemProjectEndpoint endpoint = createAndPersist("watcher session errors",
				GFilesystemProjectEndpoint.class);
		GFilesystemContentManagementSystem system = filesystemHandler.getSystem(endpoint);
		Path folder = Path.of(localFolderDiscoveryService.getLocalPersistentFolder(system, endpoint));
		Files.createDirectories(folder);
		endpoint.setPath(List.of(VFilesystemReference.from(folder)));
		endpoint = persistentObjectManager.update(endpoint);
		final Path gone = folder.resolve("gone-before-it-was-read.docx");
		final ChangedFiles changes = new ChangedFiles(folder.toFile(),
				Set.of(new ChangedFile(folder.toFile(), gone.toFile(), ChangedFile.Type.ADD)));

		final GConsumers consumers = inversionOfControlIngestionService.getConsumer(endpoint,
				new NoContentConsumingSessionParam());
		try {
			filesystemHandler.consume(endpoint, consumers.getContentConsumer(), consumers.getMessagesConsumer(),
					consumers.getErrorConsumer(), changes);
			assertNotNull(consumers.getErrorConsumer(), "the session of the watcher has where to record its errors");
			assertTrue(consumers.getErrorConsumer().hasErrors(),
					"the file that could not be read is an error of the session");
		} finally {
			consumers.getContentConsumer().endConsuming();
			consumers.getMessagesConsumer().endConsuming();
			consumers.end();
			cleanPersistent(endpoint);
		}
	}
}
