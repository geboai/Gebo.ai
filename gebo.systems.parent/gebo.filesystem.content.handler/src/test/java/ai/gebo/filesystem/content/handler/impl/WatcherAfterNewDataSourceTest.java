/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.filesystem.content.handler.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.boot.devtools.filewatch.ChangedFiles;
import org.springframework.boot.devtools.filewatch.FileSystemWatcher;
import org.springframework.boot.devtools.filewatch.SnapshotStateRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;

import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.filesystem.content.handler.GFilesystemProjectEndpoint;
import ai.gebo.filesystem.content.handler.repositories.FilesystemProjectEndpointRepository;
import ai.gebo.model.virtualfs.VFilesystemReference;

/**
 * A folder data source created while the application runs is watched, and the ones
 * already watched keep being watched: a stopped FileSystemWatcher never scans again, so
 * restarting it left every folder unwatched until the next start of the application.
 * Found live.
 */
class WatcherAfterNewDataSourceTest {
	/** The service with a quick watcher, recording the changes it is told of. */
	static class Recording extends GFilesystemChangesHandlingService {
		final List<String> changed = new CopyOnWriteArrayList<>();

		Recording(IGRuntimeBinder binder) {
			super(binder);
		}

		@Override
		protected FileSystemWatcher newWatcher() {
			return new FileSystemWatcher(true, Duration.ofMillis(200), Duration.ofMillis(100),
					SnapshotStateRepository.NONE);
		}

		@Override
		public void onChange(Set<ChangedFiles> changeSet) {
			changeSet.forEach(c -> c.forEach(f -> changed.add(f.getFile().getName())));
		}
	}

	private static GFilesystemProjectEndpoint endpoint(String code, Path folder) {
		final GFilesystemProjectEndpoint endpoint = new GFilesystemProjectEndpoint();
		endpoint.setCode(code);
		endpoint.setPath(List.of(VFilesystemReference.from(folder)));
		return endpoint;
	}

	private static boolean seen(Recording service, String name, long millis) throws InterruptedException {
		final long until = System.currentTimeMillis() + millis;
		while (System.currentTimeMillis() < until) {
			if (service.changed.contains(name)) {
				return true;
			}
			Thread.sleep(100);
		}
		return service.changed.contains(name);
	}

	@Test
	void foldersAddedAfterTheStartAreWatchedAndTheOthersStillAre(@TempDir Path first, @TempDir Path second)
			throws Exception {
		final FilesystemProjectEndpointRepository repository = mock(FilesystemProjectEndpointRepository.class);
		when(repository.findAll()).thenReturn(List.of(endpoint("at-start", first)));
		final IGRuntimeBinder binder = mock(IGRuntimeBinder.class);
		when(binder.getImplementationOf(FilesystemProjectEndpointRepository.class)).thenReturn(repository);
		final Recording service = new Recording(binder);
		service.onApplicationEvent(new ContextRefreshedEvent(mock(ApplicationContext.class)));
		try {
			Thread.sleep(400);
			// a data source created while the application runs
			service.addHandling(endpoint("added", second));
			Thread.sleep(400);
			Files.writeString(second.resolve("new-in-added.txt"), "x");
			Files.writeString(first.resolve("new-in-first.txt"), "x");
			assertTrue(seen(service, "new-in-added.txt", 5000), "the folder added after the start is watched");
			assertTrue(seen(service, "new-in-first.txt", 5000), "the folder watched from the start still is");

			// removing a data source stops watching its folder, the others go on
			service.removeHandling(endpoint("added", second));
			Thread.sleep(400);
			service.changed.clear();
			Files.writeString(second.resolve("after-removal.txt"), "x");
			Files.writeString(first.resolve("still-watched.txt"), "x");
			assertTrue(seen(service, "still-watched.txt", 5000), "the remaining folder is watched");
			assertTrue(!service.changed.contains("after-removal.txt"), "the removed folder is not");
		} finally {
			service.watcher.stop();
		}
	}
}
