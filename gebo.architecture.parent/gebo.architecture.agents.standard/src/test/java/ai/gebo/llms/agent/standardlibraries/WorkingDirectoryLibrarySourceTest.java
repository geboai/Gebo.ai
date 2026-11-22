/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.agent.standardlibraries;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.ToolCallback;

import ai.gebo.architecture.ai.files.GLibraryFile;
import ai.gebo.architecture.ai.files.IGFilesLocation;
import ai.gebo.architecture.ai.model.AIResourceContent;
import ai.gebo.model.base.TypedInputStream;
import ai.gebo.architecture.ai.model.AIResourceType.ConsentActions;
import ai.gebo.architecture.ai.model.AIToolsAndResourcesLibraryInstance;
import ai.gebo.architecture.ai.model.AIToolsAndResourcesLibraryType;
import ai.gebo.architecture.ai.model.ToolReference;
import ai.gebo.llms.agent.standardtools.ToolsFoundDocuments;

/**
 * A working directory: a folder of its own the agents build files in with the
 * file tools, never reaching outside it, and that whoever reads the
 * conversation takes away as a zip.
 */
class WorkingDirectoryLibrarySourceTest {
	@TempDir
	Path root;
	private WorkingDirectoryLibrarySource source;
	private AIToolsAndResourcesLibraryType type;

	@BeforeEach
	void setUp() {
		WorkingDirectoriesConfig config = new WorkingDirectoriesConfig();
		config.setRoot(root.toString());
		source = new WorkingDirectoryLibrarySource(config);
		type = source.getManagedTypes().get(0);
	}

	private static ToolCallback tool(List<ToolCallback> tools, String name) {
		return tools.stream().filter(t -> name.equals(t.getToolDefinition().name())).findFirst().orElseThrow();
	}

	@Test
	void theTypeDeclaresItsToolsWithTheirSchemasAndADownloadableResource() {
		assertEquals(WorkingDirectoryLibrarySource.TYPE_ID, type.getId());
		assertEquals(List.of("listFiles", "readFile", "writeFile", "createFolder", "deleteEntry", "moveEntry",
				"viewFile"),
				type.getTools().stream().map(ToolReference::getName).toList());
		for (ToolReference reference : type.getTools()) {
			assertTrue(reference.getInputSchema() != null && reference.getInputSchema().contains("\"properties\""),
					reference.getName() + " declares its input schema");
		}
		assertEquals(1, type.getResourcesTypes().size());
		assertTrue(type.getResourcesTypes().get(0).isDownloadable());
		assertEquals(List.of(ConsentActions.READ_INFORMATIONS, ConsentActions.WRITE_INFORMATIONS),
				type.getResourcesTypes().get(0).getConsented());
	}

	@Test
	void theFolderIsCreatedUnderTheHandleWhenAnAgentAccessesItAndNeverOverAnotherOne() {
		String handle = java.util.UUID.randomUUID().toString();
		assertNull(source.getInstanceById(handle, "the-conversation"), "a handle is not an instance yet");
		assertFalse(Files.exists(root.resolve(handle)), "nothing is allocated for a handle");

		AIToolsAndResourcesLibraryInstance allocated = source.allocateInstance(type,
				List.of("the-conversation", "somebody"), handle);

		assertEquals(handle, allocated.getId(), "the instance has the id it was reserved");
		assertTrue(Files.isDirectory(root.resolve(handle)));
		assertNotNull(source.getInstanceById(handle, "the-conversation"));
		assertNull(source.getInstanceById(handle, "somebody-else"), "not theirs");

		org.junit.jupiter.api.Assertions.assertThrows(IllegalStateException.class,
				() -> source.allocateInstance(type, List.of("somebody-else"), handle),
				"a handle is allocated once: nobody takes the folder of another");
		assertNotNull(source.getInstanceById(handle, "the-conversation"), "still its owner's");
		org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class,
				() -> source.allocateInstance(type, List.of("the-conversation"), "../elsewhere"));
	}

	@Test
	void anInstanceIsAFolderOfItsOwnThatItsOwnerGetsBack() {
		AIToolsAndResourcesLibraryInstance created = source.createInstance(type, List.of("the-conversation", "somebody"));

		assertTrue(Files.isDirectory(root.resolve(created.getId())));
		assertEquals(List.of("the-conversation", "somebody"), created.getOwnersId());
		assertEquals(1, created.getResources().size());
		assertEquals(WorkingDirectoryLibrarySource.FILES_RESOURCE_ID, created.getResources().get(0).getId());

		assertNotNull(source.getInstanceById(created.getId(), "the-conversation"));
		assertNull(source.getInstanceById(created.getId(), "somebody-else"), "not theirs");
		assertNull(source.getInstanceById("no-such-id", "the-conversation"));
		assertNull(source.getInstanceById("00000000-0000-0000-0000-000000000000", "the-conversation"));
	}

	@Test
	void theToolsBuildFilesAndFoldersInsideTheFolder() throws IOException {
		AIToolsAndResourcesLibraryInstance instance = source.createInstance(type, List.of("the-conversation"));
		List<ToolCallback> tools = source.getToolCallbacks(instance);

		String written = tool(tools, "writeFile").call("{\"path\":\"report/chapter-1.md\",\"content\":\"# One\"}");
		assertTrue(written.contains("Created report/chapter-1.md"), written);
		assertEquals("# One", Files.readString(root.resolve(instance.getId()).resolve("report/chapter-1.md"),
				StandardCharsets.UTF_8));

		assertEquals("\"# One\"", tool(tools, "readFile").call("{\"path\":\"report/chapter-1.md\"}"),
				"a string result comes back as JSON");

		String listing = tool(tools, "listFiles").call("{\"recursive\":true}");
		assertTrue(listing.contains("report/chapter-1.md"), listing);
		assertFalse(listing.contains(WorkingDirectoryLibrarySource.METADATA_FILE), "the folder's own file is nobody's");

		tool(tools, "createFolder").call("{\"path\":\"drafts\"}");
		assertTrue(Files.isDirectory(root.resolve(instance.getId()).resolve("drafts")));

		tool(tools, "moveEntry").call("{\"from\":\"report/chapter-1.md\",\"to\":\"drafts/one.md\"}");
		assertTrue(Files.exists(root.resolve(instance.getId()).resolve("drafts/one.md")));

		String deleted = tool(tools, "deleteEntry").call("{\"path\":\"drafts\"}");
		assertTrue(deleted.contains("Deleted drafts"), deleted);
		assertFalse(Files.exists(root.resolve(instance.getId()).resolve("drafts")));

		AIToolsAndResourcesLibraryInstance seen = source.getInstanceById(instance.getId(), "the-conversation");
		assertTrue(seen.getDescription().contains("empty so far"), seen.getDescription());
	}

	@Test
	void nothingOutsideTheFolderIsReached() {
		AIToolsAndResourcesLibraryInstance instance = source.createInstance(type, List.of("the-conversation"));
		Path folder = root.resolve(instance.getId());

		assertThrows(IllegalArgumentException.class,
				() -> WorkingDirectoryLibrarySource.inside(folder, "../" + instance.getId() + "-other/x.txt"));
		assertThrows(IllegalArgumentException.class, () -> WorkingDirectoryLibrarySource.inside(folder, "../../etc"));
		assertThrows(IllegalArgumentException.class,
				() -> WorkingDirectoryLibrarySource.inside(folder, WorkingDirectoryLibrarySource.METADATA_FILE));
		assertEquals(folder.toAbsolutePath().normalize().resolve("a/b.txt"),
				WorkingDirectoryLibrarySource.inside(folder, "a/./b.txt"));
	}

	@Test
	void theToolsOfAnInstanceWorkOnItsFolderWhenCalledByARun() {
		AIToolsAndResourcesLibraryInstance instance = source.createInstance(type, List.of("the-conversation"));
		ToolContext context = new ToolContext(Map.of());

		String written = tool(source.getToolCallbacks(instance), "writeFile")
				.call("{\"path\":\"a.txt\",\"content\":\"hi\"}", context);

		assertTrue(written.contains("Created a.txt"), written);
		assertTrue(Files.exists(root.resolve(instance.getId()).resolve("a.txt")));
	}

	@Test
	void theWholeFolderComesAsAZipAtAnyMoment() throws IOException {
		AIToolsAndResourcesLibraryInstance instance = source.createInstance(type, List.of("the-conversation"));
		List<ToolCallback> tools = source.getToolCallbacks(instance);
		tool(tools, "writeFile").call("{\"path\":\"report/chapter-1.md\",\"content\":\"# One\"}");
		tool(tools, "writeFile").call("{\"path\":\"notes.txt\",\"content\":\"n\"}");

		AIResourceContent zip = source.download(instance, WorkingDirectoryLibrarySource.FILES_RESOURCE_ID);

		assertEquals("application/zip", zip.contentType());
		assertEquals("working-directory-" + instance.getId() + ".zip", zip.fileName());
		List<String> entries = new ArrayList<>();
		try (InputStream in = zip.content(); ZipInputStream unzipped = new ZipInputStream(in)) {
			for (ZipEntry entry = unzipped.getNextEntry(); entry != null; entry = unzipped.getNextEntry()) {
				entries.add(entry.getName());
				if ("report/chapter-1.md".equals(entry.getName())) {
					assertEquals("# One", new String(unzipped.readAllBytes(), StandardCharsets.UTF_8));
				}
			}
		}
		assertEquals(List.of("notes.txt", "report/", "report/chapter-1.md"), entries);
		assertNull(source.download(instance, "no-such-resource"));
	}

	@Test
	void droppingRemovesTheFolder() {
		AIToolsAndResourcesLibraryInstance instance = source.createInstance(type, List.of("the-conversation"));
		tool(source.getToolCallbacks(instance), "writeFile").call("{\"path\":\"a/b/c.txt\",\"content\":\"x\"}");

		source.dropInstance(instance);

		assertFalse(Files.exists(root.resolve(instance.getId())));
		assertNull(source.getInstanceById(instance.getId(), "the-conversation"));
		source.dropInstance(instance);
	}

	@Test
	void theFolderIsAFilesLocationTheOtherLibrariesReadAndWrite() throws IOException {
		AIToolsAndResourcesLibraryInstance instance = source.createInstance(type, List.of("the-conversation"));
		List<ToolCallback> tools = source.getToolCallbacks(instance);
		tool(tools, "writeFile").call("{\"path\":\"data/in.csv\",\"content\":\"a,b\"}");

		IGFilesLocation location = source.filesOf(instance);
		assertEquals(WorkingDirectoryLibrarySource.TYPE_ID, location.getId());
		assertTrue(location.isWritable());
		assertEquals(List.of("data/in.csv"), location.list(null, true).stream().map(GLibraryFile::path).toList(),
				"the metadata file is not part of it");
		assertEquals("a,b", new String(location.read("data/in.csv").content().getInputStream().readAllBytes(),
				StandardCharsets.UTF_8));
		location.write("data/out.csv", TypedInputStream.of(
				new java.io.ByteArrayInputStream("c,d".getBytes(StandardCharsets.UTF_8)), "text/csv", ".csv"),
				IGFilesLocation.ABSENT);
		assertEquals("c,d", tool(tools, "readFile").call("{\"path\":\"data/out.csv\"}").replace("\"", ""));
		assertThrows(IllegalArgumentException.class, () -> location.read(".gebo-working-directory.json"));

		source.dropInstance(instance);
		assertNull(source.filesOf(instance), "a folder gone holds no files");
	}

	@Test
	void theFilesTheToolsHandleAreReportedToTheRunTheyArePartOf() {
		AIToolsAndResourcesLibraryInstance instance = source.createInstance(type, List.of("the-conversation"));
		List<ToolCallback> tools = source.getToolCallbacks(instance);
		ToolsFoundDocuments run = new ToolsFoundDocuments();
		ToolContext context = new ToolContext(Map.of(ToolsFoundDocuments.TOOLS_CONTEXT_KEY, run));
		tool(tools, "writeFile").call("{\"path\":\"report/total.md\",\"content\":\"151.5\"}", context);
		tool(tools, "readFile").call("{\"path\":\"report/total.md\"}", context);
		tool(tools, "moveEntry").call("{\"from\":\"report/total.md\",\"to\":\"total.md\"}", context);
		tool(tools, "createFolder").call("{\"path\":\"empty\"}", context);
		tool(tools, "listFiles").call("{\"recursive\":true}", context);
		assertEquals(List.of("report/total.md", "total.md"), run.getHandledNames(),
				"files written, read, moved and listed, once each; no folder");
		tool(tools, "readFile").call("{\"path\":\"total.md\"}");
		assertEquals(2, run.getHandledNames().size(), "a call outside the run reports to nobody");
	}

	@Test
	void aPictureOfTheWorkingDirectoryIsShownToAModelThatTakesPictures() throws Exception {
		AIToolsAndResourcesLibraryInstance instance = source.createInstance(type, List.of("the-conversation"));
		Path folder = root.resolve(instance.getId());
		Files.createDirectories(folder.resolve("charts"));
		Files.write(folder.resolve("charts/sales.png"), new byte[] { 1, 2, 3 });
		WorkingDirectoryLibrarySource.ViewParam param = new WorkingDirectoryLibrarySource.ViewParam();
		param.setPath("charts/sales.png");

		List<String> shown = new ArrayList<>();
		ai.gebo.architecture.ai.files.IGToolShownFiles viewer = (name, mimeType, data) -> {
			shown.add(name + " " + mimeType + " " + data.length);
			return true;
		};
		String told = source.viewFile(folder, param, new ToolContext(
				Map.of(ai.gebo.architecture.ai.files.IGToolShownFiles.TOOLS_CONTEXT_KEY, viewer)));
		assertEquals(List.of("sales.png image/png 3"), shown);
		assertTrue(told.contains("is shown to you in the next message"), told);

		// a model that does not take pictures, and a call with no way to show files, are told so
		ai.gebo.architecture.ai.files.IGToolShownFiles blind = (name, mimeType, data) -> false;
		assertTrue(source.viewFile(folder, param, new ToolContext(
				Map.of(ai.gebo.architecture.ai.files.IGToolShownFiles.TOOLS_CONTEXT_KEY, blind)))
				.contains("cannot be shown"));
		assertTrue(source.viewFile(folder, param, new ToolContext(Map.of())).contains("cannot be shown in this call"));

		// nothing outside the working directory is reached
		param.setPath("../outside.png");
		assertThrows(IllegalArgumentException.class, () -> source.viewFile(folder, param, new ToolContext(Map.of())));
	}
}
