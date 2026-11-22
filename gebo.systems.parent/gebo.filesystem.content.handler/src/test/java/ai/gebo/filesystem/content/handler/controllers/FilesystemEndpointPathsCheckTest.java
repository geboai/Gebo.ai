/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.filesystem.content.handler.controllers;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import ai.gebo.filesystem.content.handler.GFilesystemProjectEndpoint;
import ai.gebo.model.virtualfs.VFilesystemReference;

/** An endpoint whose path names no folder is refused before it is saved. */
class FilesystemEndpointPathsCheckTest {
	@Test
	void aPathEntryNamingNoFolderIsRefused(@TempDir Path folder) {
		final GFilesystemProjectEndpoint endpoint = new GFilesystemProjectEndpoint();
		endpoint.setPath(List.of(VFilesystemReference.from(folder), new VFilesystemReference()));
		final ResponseStatusException refused = assertThrows(ResponseStatusException.class,
				() -> FileSystemsController.checkPaths(endpoint));
		assertEquals(HttpStatus.BAD_REQUEST, refused.getStatusCode());
		assertTrue(refused.getReason().startsWith("Path entry 2 names no folder"), refused.getReason());

		endpoint.setPath(List.of(VFilesystemReference.from(folder)));
		assertDoesNotThrow(() -> FileSystemsController.checkPaths(endpoint), "a folder is accepted");
		endpoint.setPath(null);
		assertDoesNotThrow(() -> FileSystemsController.checkPaths(endpoint), "no path, as before");
	}
}
