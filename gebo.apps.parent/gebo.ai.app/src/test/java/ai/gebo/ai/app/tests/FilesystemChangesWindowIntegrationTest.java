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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestPropertySource;

import ai.gebo.filesystem.content.handler.GFilesystemProjectEndpoint;

/**
 * Certifies that the changes of the files of a filesystem data source gather for the
 * window of the installation and are then handed to the ingestion together, over the
 * real path: files are written at different moments in the folder of a followed data
 * source, each look stores what it finds, and when the window from the first change
 * ends the whole group reaches the ingestion in one session.
 */
@TestPropertySource(properties = { "ai.gebo.filesystem.watchers-period=1",
		"ai.gebo.filesystem.watcher-change-events-window=1" })
public class FilesystemChangesWindowIntegrationTest extends AbstractFilesystemChangesSyncIntegrationTest {

	@Override
	protected void afterEachCallback() throws Exception {
		storedChanges.deleteAll();
	}

	@Test
	public void theChangesGatherForTheWindowAndAreHandedOverTogether() throws Exception {
		final GFilesystemProjectEndpoint endpoint = dataSource("filesystem data source gathering its changes", true);
		final Path folder = folderOf(endpoint);
		Thread.sleep(3000);
		final long sessionsBefore = sessionsOf(endpoint);

		final Path first = folder.resolve("first.txt");
		Files.writeString(first, "The first change of the window.");
		assertTrue(within(20, () -> storedFor(endpoint).size() == 1), "the first change is stored");
		final Path second = folder.resolve("second.txt");
		Files.writeString(second, "A change found by a later look.");
		assertTrue(within(20, () -> storedFor(endpoint).size() == 2), "the next one is stored beside it");
		// written and removed within the window: it comes to nothing
		final Path passing = folder.resolve("passing.txt");
		Files.writeString(passing, "Gone before the window ends.");
		assertTrue(within(20, () -> storedFor(endpoint).size() == 3));
		Files.delete(passing);
		assertTrue(within(20, () -> storedFor(endpoint).size() == 4));

		assertNull(documentOf(first), "while the changes gather nothing is handed to the ingestion");
		assertEquals(sessionsBefore, sessionsOf(endpoint), "and no session of the ingestion is opened");

		assertTrue(within(70, () -> documentOf(first) != null && documentOf(second) != null),
				"when the window from the first change ends the changes are handed over");
		assertTrue(within(10, () -> storedFor(endpoint).isEmpty()), "the whole group");
		assertEquals(sessionsBefore + 1, sessionsOf(endpoint), "together, in one session of the ingestion");
		assertNull(documentOf(passing), "a file written and removed within the window is not handed over");
		cleanPersistent(endpoint);
	}
}
