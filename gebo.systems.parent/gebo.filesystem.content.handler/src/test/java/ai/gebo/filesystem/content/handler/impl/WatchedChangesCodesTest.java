/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.filesystem.content.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.boot.devtools.filewatch.ChangedFile;
import org.springframework.boot.devtools.filewatch.ChangedFiles;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.architecture.buildsystems.abstraction.layer.IGBuildSystemHandlerRepositoryPattern;
import ai.gebo.architecture.contenthandling.interfaces.IGContentConsumer;
import ai.gebo.architecture.contenthandling.interfaces.IGUserMessagesConsumer;
import ai.gebo.architecture.contenthandling.interfaces.impl.GDocumentReferenceFactoryImpl;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.filesystem.content.handler.GFilesystemContentManagementSystem;
import ai.gebo.filesystem.content.handler.GFilesystemProjectEndpoint;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GVirtualFolder;
import ai.gebo.model.base.GBaseVersionableObject;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler;
import ai.gebo.systems.abstraction.layer.IGContentManagementSystemConfigurationDao;
import ai.gebo.systems.abstraction.layer.IGContentsAccessErrorConsumer;
import ai.gebo.systems.abstraction.layer.IGLocalPersistentFolderDiscoveryService;
import ai.gebo.systems.abstraction.layer.IGProjectEndpointRuntimeConfigurationDao;

/**
 * A file the folder watcher reports gets the code the publish gave it: in the watched
 * folder itself and in its subfolders, when added, modified or deleted. Otherwise a
 * changed file is registered a second time and a deletion misses the document.
 */
class WatchedChangesCodesTest {
	static final String ROOT_CODE = "kb/project/endpoint";

	@SuppressWarnings("unchecked")
	static class Handler extends GFilesystemContentManagementSystemHandlerImpl {
		Handler() {
			super(mock(IGBuildSystemHandlerRepositoryPattern.class), new GDocumentReferenceFactoryImpl(),
					mock(IGContentManagementSystemConfigurationDao.class),
					mock(IGProjectEndpointRuntimeConfigurationDao.class),
					mock(IGLocalPersistentFolderDiscoveryService.class), mock(IGPersistentObjectManager.class),
					mock(IGMessageBroker.class), mock(IGDocumentReferenceIngestionHandler.class));
		}

		@Override
		protected GVirtualFolder createRootItem(GFilesystemContentManagementSystem system,
				GFilesystemProjectEndpoint endpoint) {
			final GVirtualFolder root = new GVirtualFolder();
			root.setCode(ROOT_CODE);
			root.setName(".");
			root.setRelativePath("");
			return root;
		}
	}

	static class Collected implements IGContentConsumer {
		final List<GBaseVersionableObject> items = new ArrayList<>();

		@Override
		public void accept(GBaseVersionableObject item) {
			items.add(item);
		}

		@Override
		public void endConsuming() {
		}

		Set<String> documentCodes() {
			final Set<String> codes = new TreeSet<>();
			items.stream().filter(x -> x instanceof GDocumentReference).forEach(x -> codes.add(x.getCode()));
			return codes;
		}

		Set<String> folderCodes() {
			final Set<String> codes = new TreeSet<>();
			items.stream().filter(x -> x instanceof GVirtualFolder).forEach(x -> codes.add(x.getCode()));
			return codes;
		}
	}

	private static GFilesystemProjectEndpoint endpoint(Path folder) {
		final GFilesystemProjectEndpoint endpoint = new GFilesystemProjectEndpoint();
		endpoint.setCode("endpoint");
		endpoint.setParentProjectCode("project");
		endpoint.setPath(List.of(VFilesystemReference.from(folder)));
		return endpoint;
	}

	private static Path write(Path file) throws Exception {
		Files.createDirectories(file.getParent());
		Files.writeString(file, "text of " + file.getFileName());
		return file;
	}

	private static Collected watched(Handler handler, GFilesystemProjectEndpoint endpoint, Path folder,
			ChangedFile.Type type, Path... files) throws Exception {
		final Set<ChangedFile> changes = new java.util.LinkedHashSet<>();
		for (Path file : files) {
			changes.add(new ChangedFile(folder.toFile(), file.toFile(), type));
		}
		final Collected consumed = new Collected();
		handler.consume(endpoint, consumed, mock(IGUserMessagesConsumer.class), mock(IGContentsAccessErrorConsumer.class),
				new ChangedFiles(folder.toFile(), changes));
		return consumed;
	}

	@Test
	void aWatchedFileGetsTheCodeThePublishGaveIt(@TempDir Path folder) throws Exception {
		final Path top = write(folder.resolve("ferry.png"));
		final Path nested = write(folder.resolve("reports/2026/heron.docx"));
		final Handler handler = new Handler();
		final GFilesystemProjectEndpoint endpoint = endpoint(folder);

		final Collected published = new Collected();
		handler.consumeImplementation(null, List.of(), endpoint, null, published, mock(IGUserMessagesConsumer.class),
				mock(IGContentsAccessErrorConsumer.class));
		assertEquals(Set.of(ROOT_CODE + "/ferry.png", ROOT_CODE + "/reports/2026/heron.docx"),
				published.documentCodes());

		for (ChangedFile.Type type : new ChangedFile.Type[] { ChangedFile.Type.ADD, ChangedFile.Type.MODIFY }) {
			final Collected seen = watched(handler, endpoint, folder, type, top, nested);
			assertEquals(published.documentCodes(), seen.documentCodes(), type + ": the codes the publish gave");
			assertTrue(published.folderCodes().containsAll(seen.folderCodes()), type + ": the folders the publish made");
		}
		Files.delete(nested);
		final Collected deleted = watched(handler, endpoint, folder, ChangedFile.Type.DELETE, nested);
		assertEquals(Set.of(ROOT_CODE + "/reports/2026/heron.docx"), deleted.documentCodes(),
				"the deletion reaches the document the publish registered");
	}

	@Test
	void theFoldersBetweenTheWatchedFolderAndAFile(@TempDir Path folder) {
		assertEquals(List.of(), GFilesystemContentManagementSystemHandlerImpl.foldersBetween(folder,
				folder.resolve("ferry.png")), "a file in the watched folder itself: no folder, never an empty name");
		assertEquals(List.of("reports", "2026"), GFilesystemContentManagementSystemHandlerImpl
				.foldersBetween(folder, folder.resolve("reports/2026/heron.docx")));
		assertEquals(List.of(), GFilesystemContentManagementSystemHandlerImpl.foldersBetween(folder,
				new File("/elsewhere/file.txt").toPath()), "a file outside the watched folder");
	}
}
