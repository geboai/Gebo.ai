/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */


package ai.gebo.ai.app.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import ai.gebo.filesystem.content.handler.GFilesystemContentManagementSystem;
import ai.gebo.filesystem.content.handler.GFilesystemProjectEndpoint;
import ai.gebo.filesystem.content.handler.IGFilesystemContentManagementSystemHandler;
import ai.gebo.filesystem.content.handler.controllers.FileSystemsController;
import ai.gebo.model.virtualfs.VFilesystemReference;

/**
 * Certifies that publishing a folder data source checks its path entries as saving it
 * does: the publish saves the data source it is given before it starts the ingestion, so
 * an entry naming no folder is refused there too and what is stored stays as it was.
 */
public class FilesystemPublishPathsIntegrationTest extends AbstractBaseTestLLmsIntegrationTests {
	@Autowired
	IGFilesystemContentManagementSystemHandler filesystemHandler;

	@Autowired
	FileSystemsController controller;

	@Test
	public void aPublishWithAPathEntryNamingNoFolderIsRefusedAndNothingIsSaved() throws Exception {
		GFilesystemProjectEndpoint endpoint = createAndPersist("publish path entries", GFilesystemProjectEndpoint.class);
		GFilesystemContentManagementSystem system = filesystemHandler.getSystem(endpoint);
		Path folder = Path.of(localFolderDiscoveryService.getLocalPersistentFolder(system, endpoint));
		Files.createDirectories(folder);
		endpoint.setPath(List.of(VFilesystemReference.from(folder)));
		endpoint = persistentObjectManager.update(endpoint);
		final String code = endpoint.getCode();
		try {
			endpoint.setPath(List.of(VFilesystemReference.from(folder), new VFilesystemReference()));
			final GFilesystemProjectEndpoint published = endpoint;
			final ResponseStatusException refused = assertThrows(ResponseStatusException.class,
					() -> controller.publishFilesystemEndpoint(published));
			assertEquals(HttpStatus.BAD_REQUEST, refused.getStatusCode());
			assertTrue(refused.getReason().startsWith("Path entry 2 names no folder"), refused.getReason());
			assertEquals(1, persistentObjectManager.findById(GFilesystemProjectEndpoint.class, code).getPath().size(),
					"the data source stored keeps the path it had");
		} finally {
			cleanPersistent(persistentObjectManager.findById(GFilesystemProjectEndpoint.class, code));
		}
	}
}
