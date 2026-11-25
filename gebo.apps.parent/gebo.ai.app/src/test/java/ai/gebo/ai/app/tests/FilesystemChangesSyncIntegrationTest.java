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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import ai.gebo.filesystem.content.handler.GFilesystemChangesQueueEntry;
import ai.gebo.filesystem.content.handler.GFilesystemProjectEndpoint;
import ai.gebo.jobs.services.IGGeboIngestionJobQueueService;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;

/**
 * Certifies how the changes of the files of a filesystem data source reach the
 * ingestion, over the real path: files are written, changed and removed in the folder
 * of a data source, the service that looks for the changes finds them, stores them and
 * hands them to the ingestion, and the documents of the platform follow. Only a data
 * source flagged to follow its changes is looked at; one being published is left
 * alone, and what was stored for it is still handed over after a restart.
 * <p>
 * The service looks every second and hands over what it finds at once; the gathering
 * of the changes over a window has a test of its own. Whether a publication is running
 * is the only thing told to the platform rather than done: a publication cannot be
 * held running for as long as the test needs.
 */
@TestPropertySource(properties = { "ai.gebo.filesystem.watchers-period=1",
		"ai.gebo.filesystem.watcher-change-events-window=0" })
public class FilesystemChangesSyncIntegrationTest extends AbstractFilesystemChangesSyncIntegrationTest {
	@MockitoSpyBean
	private IGGeboIngestionJobQueueService jobs;
	/** The codes of the data sources told to be under publication. */
	private final Set<String> beingPublished = ConcurrentHashMap.newKeySet();

	@Override
	protected void beforeEachCallback() throws Exception {
		beingPublished.clear();
		doAnswer(call -> beingPublished.contains(((GProjectEndpoint) call.getArgument(0)).getCode()) ? Boolean.TRUE
				: call.callRealMethod()).when(jobs).isRunningSyncJob(any(GProjectEndpoint.class));
	}

	@Override
	protected void afterEachCallback() throws Exception {
		beingPublished.clear();
		storedChanges.deleteAll();
	}

	@Test
	public void theFilesWrittenChangedAndRemovedInAFollowedDataSourceReachTheIngestion() throws Exception {
		final GFilesystemProjectEndpoint endpoint = dataSource("followed filesystem data source", true);
		final Path folder = folderOf(endpoint);
		Files.createDirectories(folder.resolve("inner"));
		// the folder as it is now is pictured: what changes from now on is told
		Thread.sleep(3000);

		final Path note = folder.resolve("note.txt");
		final Path inner = folder.resolve("inner").resolve("minutes.txt");
		Files.writeString(note, "The meeting is on Monday.");
		Files.writeString(inner, "All agreed.");
		assertTrue(within(40, () -> documentOf(note) != null && documentOf(inner) != null),
				"the files written in the folder, and in a folder inside it, are handed to the ingestion");
		assertTrue(within(10, () -> storedFor(endpoint).isEmpty()), "and nothing stays stored once handed over");

		final Long sizeBefore = documentOf(note).getFileSize();
		Files.writeString(note, "The meeting is on Monday, in the large room on the second floor.");
		assertTrue(within(40, () -> {
			final GDocumentReference changed = documentOf(note);
			return changed != null && changed.getFileSize() != null && !changed.getFileSize().equals(sizeBefore);
		}), "a file changed is handed over again");

		Files.delete(inner);
		assertTrue(within(40, () -> {
			final GDocumentReference removed = documentOf(inner);
			return removed != null && Boolean.TRUE.equals(removed.getDeleted());
		}), "a file removed is told deleted");
		assertFalse(Boolean.TRUE.equals(documentOf(note).getDeleted()), "the file still there is not");
		cleanPersistent(endpoint);
	}

	@Test
	public void handingOverAChangeDoesNotCheckEveryOtherFileOfTheDataSource() throws Exception {
		final GFilesystemProjectEndpoint endpoint = dataSource("filesystem data source with many files", true);
		final Path folder = folderOf(endpoint);
		Thread.sleep(3000);

		final Path settled = folder.resolve("settled.txt");
		Files.writeString(settled, "A file handed over some time ago.");
		assertTrue(within(40, () -> documentOf(settled) != null));
		assertTrue(within(10, () -> storedFor(endpoint).isEmpty()));
		final String sessionOfSettled = documentOf(settled).getLastesJobId();
		assertNotNull(sessionOfSettled, "a document keeps the session that last worked on it");

		final Path changed = folder.resolve("changed-later.txt");
		Files.writeString(changed, "The only file that changes now.");
		assertTrue(within(40, () -> documentOf(changed) != null), "the change is handed over");
		assertTrue(within(10, () -> storedFor(endpoint).isEmpty()), "and its session ended");
		assertFalse(sessionOfSettled.equals(documentOf(changed).getLastesJobId()), "in a session of its own");
		assertEquals(sessionOfSettled, documentOf(settled).getLastesJobId(),
				"which did not work on the file that did not change");
		cleanPersistent(endpoint);
	}

	@Test
	public void aFolderThatCannotBeListedIsNotReadAsEmptied() throws Exception {
		final GFilesystemProjectEndpoint endpoint = dataSource("filesystem data source on a share that drops", true);
		final Path folder = folderOf(endpoint);
		final Path inner = folder.resolve("inner");
		Files.createDirectories(inner);
		Thread.sleep(3000);
		final Path note = folder.resolve("note.txt");
		final Path minutes = inner.resolve("minutes.txt");
		Files.writeString(note, "On the share.");
		Files.writeString(minutes, "In a folder of the share.");
		assertTrue(within(40, () -> documentOf(note) != null && documentOf(minutes) != null));
		assertTrue(within(10, () -> storedFor(endpoint).isEmpty()));

		// the share drops: the folder is no longer where the data source says
		final Path away = folder.resolveSibling(folder.getFileName() + "-away");
		Files.move(folder, away);
		try {
			Thread.sleep(12000);
			assertEquals(List.of(), storedFor(endpoint), "a folder that cannot be listed is not read as emptied");
			assertFalse(Boolean.TRUE.equals(documentOf(note).getDeleted()), "its files are not told deleted");
		} finally {
			Files.move(away, folder);
		}

		// a folder inside it can no longer be listed
		final java.util.Set<java.nio.file.attribute.PosixFilePermission> permissions = Files
				.getPosixFilePermissions(inner);
		Files.setPosixFilePermissions(inner, java.util.Set.of());
		try {
			Thread.sleep(12000);
			assertEquals(List.of(), storedFor(endpoint), "nor is a folder inside it");
			assertFalse(Boolean.TRUE.equals(documentOf(minutes).getDeleted()));
		} finally {
			Files.setPosixFilePermissions(inner, permissions);
		}

		// the share is back: what changes is followed again, and nothing was lost
		final Path later = folder.resolve("written-once-back.txt");
		Files.writeString(later, "The share is back.");
		assertTrue(within(40, () -> documentOf(later) != null), "once it can be listed again it is followed again");
		assertFalse(Boolean.TRUE.equals(documentOf(note).getDeleted()));
		assertFalse(Boolean.TRUE.equals(documentOf(minutes).getDeleted()));
		cleanPersistent(endpoint);
	}

	@Test
	public void onlyADataSourceFlaggedToFollowItsChangesIsLookedAt() throws Exception {
		GFilesystemProjectEndpoint endpoint = dataSource("filesystem data source saved before the flag", null);
		final Path folder = folderOf(endpoint);
		Thread.sleep(3000);

		final Path unseen = folder.resolve("written-while-not-followed.txt");
		Files.writeString(unseen, "Nobody follows this folder yet.");
		Thread.sleep(12000);
		assertNull(documentOf(unseen), "a data source not flagged is not looked at");
		assertEquals(List.of(), storedFor(endpoint), "and nothing is stored for it");

		// the administrator ticks the flag: followed from the next look on
		endpoint.setSyncOnFilesModifications(true);
		endpoint = persistentObjectManager.update(endpoint);
		Thread.sleep(3000);
		final Path seen = folder.resolve("written-once-followed.txt");
		Files.writeString(seen, "Now the folder is followed.");
		assertTrue(within(40, () -> documentOf(seen) != null), "once flagged, what changes is handed to the ingestion");
		assertNull(documentOf(unseen), "what was there when the flag was set is left to the publication");
		cleanPersistent(endpoint);
	}

	@Test
	public void aDataSourceBeingPublishedIsLeftAloneAndLookedAtAgainAfter() throws Exception {
		final GFilesystemProjectEndpoint endpoint = dataSource("filesystem data source under publication", true);
		final Path folder = folderOf(endpoint);
		Thread.sleep(3000);

		beingPublished.add(endpoint.getCode());
		final Path file = folder.resolve("written-during-the-publication.txt");
		Files.writeString(file, "Written while the data source was being published.");
		Thread.sleep(12000);
		assertEquals(List.of(), storedFor(endpoint), "a data source being published is not looked at");
		assertNull(documentOf(file), "and nothing of it is handed to the ingestion");

		beingPublished.remove(endpoint.getCode());
		assertTrue(within(40, () -> documentOf(file) != null),
				"once the publication ended, what changed meanwhile is found and handed over");
		cleanPersistent(endpoint);
	}

	@Test
	public void theChangesStoredWaitThroughAPublicationAndARestart() throws Exception {
		final GFilesystemProjectEndpoint endpoint = dataSource("filesystem data source with stored changes", true);
		final Path folder = folderOf(endpoint);
		// a file the service pictures with the folder: no look will find it changed
		final Path file = folder.resolve("found-before-the-restart.txt");
		Files.writeString(file, "Found changed before the application stopped.");
		Thread.sleep(3000);
		assertNull(documentOf(file));

		// what a run of the application stored and did not hand over before stopping,
		// found by this one while the data source is being published
		beingPublished.add(endpoint.getCode());
		final GFilesystemChangesQueueEntry left = new GFilesystemChangesQueueEntry();
		left.setEndpointCode(endpoint.getCode());
		left.setFolder(folder.toAbsolutePath().toString());
		left.setDetectedAt(new Date());
		left.getAdded().add(file.toAbsolutePath().toString());
		storedChanges.save(left);
		Thread.sleep(5000);
		assertEquals(1, storedFor(endpoint).size(), "what is stored waits while the data source is being published");
		assertNull(documentOf(file));

		beingPublished.remove(endpoint.getCode());
		assertTrue(within(30, () -> documentOf(file) != null), "and is handed to the ingestion after");
		assertNotNull(documentOf(file));
		assertTrue(within(10, () -> storedFor(endpoint).isEmpty()), "then removed");
		cleanPersistent(endpoint);
	}
}
