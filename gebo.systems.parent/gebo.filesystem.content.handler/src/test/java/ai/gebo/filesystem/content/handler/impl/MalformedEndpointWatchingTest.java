/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.filesystem.content.handler.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;

import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.filesystem.content.handler.GFilesystemProjectEndpoint;
import ai.gebo.filesystem.content.handler.repositories.FilesystemProjectEndpointRepository;
import ai.gebo.model.virtualfs.VFilesystemReference;

/**
 * A filesystem endpoint that cannot be watched - a path entry naming no folder, or
 * one failing in any other way - never stops the other endpoints from being watched,
 * nor the application from starting.
 */
class MalformedEndpointWatchingTest {
	private static GFilesystemProjectEndpoint endpoint(String code, List<VFilesystemReference> path) {
		final GFilesystemProjectEndpoint endpoint = new GFilesystemProjectEndpoint();
		endpoint.setCode(code);
		endpoint.setPath(path);
		return endpoint;
	}

	@Test
	void aPathEntryNamingNoFolderIsLeftOut(@TempDir Path folder) {
		final GFilesystemChangesHandlingService service = new GFilesystemChangesHandlingService(
				mock(IGRuntimeBinder.class));
		final List<VFilesystemReference> path = new ArrayList<>();
		path.add(new VFilesystemReference());
		path.add(VFilesystemReference.from(folder));
		assertDoesNotThrow(() -> service.addHandling(endpoint("mixed", path)));
		assertEquals(1, service.handledEndpoints.size(), "its valid folder is watched");
		assertEquals(folder.toFile().getAbsolutePath(), service.handledEndpoints.get(0).folder.getAbsolutePath());
	}

	@Test
	void anEndpointThatCannotBeWatchedDoesNotStopTheOthersNorTheStart(@TempDir Path folder) {
		final GFilesystemProjectEndpoint failing = mock(GFilesystemProjectEndpoint.class);
		when(failing.getCode()).thenReturn("failing");
		when(failing.getPath()).thenThrow(new IllegalStateException("unreadable record"));
		final FilesystemProjectEndpointRepository repository = mock(FilesystemProjectEndpointRepository.class);
		when(repository.findAll()).thenReturn(List.of(failing,
				endpoint("no-folder", List.of(new VFilesystemReference())),
				endpoint("good", List.of(VFilesystemReference.from(folder)))));
		final IGRuntimeBinder binder = mock(IGRuntimeBinder.class);
		when(binder.getImplementationOf(FilesystemProjectEndpointRepository.class)).thenReturn(repository);
		final GFilesystemChangesHandlingService service = new GFilesystemChangesHandlingService(binder);
		try {
			assertDoesNotThrow(() -> service.onApplicationEvent(new ContextRefreshedEvent(mock(ApplicationContext.class))));
			assertTrue(service.started, "the watching started");
			assertEquals(1, service.handledEndpoints.size());
			assertEquals("good", service.handledEndpoints.get(0).endpoint.getCode());
		} finally {
			service.watcher.stop();
		}
	}
}
