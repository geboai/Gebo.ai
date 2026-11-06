/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.ai.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import ai.gebo.architecture.ai.model.AIToolsAndResourcesLibraryType;
import ai.gebo.architecture.ai.model.LibraryToolCollision;
import ai.gebo.architecture.ai.model.ToolReference;
import ai.gebo.architecture.ai.service.LibraryToolCollisions.MountedSourceTool;

/**
 * A library's tool takes the place of the tool of the same name an agent mounts from
 * a tool source, and of the one of a library coming after it: each case is reported.
 */
class LibraryToolCollisionsTest {
	private static final MountedSourceTool MCP_READ = new MountedSourceTool("readFile", "mcp-files", "An MCP server");

	private static AIToolsAndResourcesLibraryType library(String id, String... tools) {
		AIToolsAndResourcesLibraryType type = new AIToolsAndResourcesLibraryType();
		type.setId(id);
		type.setDescription("The " + id);
		type.setTools(Arrays.stream(tools).map(name -> {
			ToolReference reference = new ToolReference();
			reference.setName(name);
			return reference;
		}).toList());
		return type;
	}

	@Test
	void aToolOfASourceWithTheNameOfALibraryToolCollides() {
		List<LibraryToolCollision> found = LibraryToolCollisions.find(
				List.of(library("directory", "readFile", "writeFile")),
				List.of(MCP_READ, new MountedSourceTool("actualDateTime", "time", "Time")));

		assertEquals(1, found.size());
		LibraryToolCollision collision = found.get(0);
		assertEquals("readFile", collision.getToolName());
		assertEquals("directory", collision.getLibraryTypeId());
		assertEquals("The directory", collision.getLibraryDescription());
		assertEquals("mcp-files", collision.getShadowedSourceId());
		assertEquals("An MCP server", collision.getShadowedDescription());
		assertNull(collision.getShadowedLibraryTypeId());
		assertEquals("the tool:readFile of library type:directory takes the place of the one of tool source:mcp-files",
				collision.written());
	}

	@Test
	void betweenTwoLibrariesTheFirstOneStays() {
		List<LibraryToolCollision> found = LibraryToolCollisions
				.find(List.of(library("directory", "readFile"), library("sandbox", "readFile", "runCode")), List.of());

		assertEquals(1, found.size());
		assertEquals("directory", found.get(0).getLibraryTypeId());
		assertEquals("sandbox", found.get(0).getShadowedLibraryTypeId());
		assertNull(found.get(0).getShadowedSourceId());
		assertEquals("the tool:readFile of library type:directory takes the place of the one of library type:sandbox",
				found.get(0).written());
	}

	@Test
	void aNameCollidingWithASourceAndALaterLibraryIsReportedForBoth() {
		List<LibraryToolCollision> found = LibraryToolCollisions
				.find(List.of(library("directory", "readFile"), library("sandbox", "readFile")), List.of(MCP_READ));

		assertEquals(2, found.size());
		assertEquals("mcp-files", found.get(0).getShadowedSourceId());
		assertEquals("sandbox", found.get(1).getShadowedLibraryTypeId());
	}

	@Test
	void aLibraryNamedTwiceDoesNotCollideWithItself() {
		AIToolsAndResourcesLibraryType directory = library("directory", "readFile");

		assertTrue(LibraryToolCollisions.find(List.of(directory, directory), List.of()).isEmpty());
	}

	@Test
	void noLibrariesOrDifferentNamesNoCollisions() {
		assertTrue(LibraryToolCollisions.find(List.of(), List.of(MCP_READ)).isEmpty());
		assertTrue(LibraryToolCollisions.find(null, null).isEmpty());
		assertTrue(LibraryToolCollisions.find(List.of(library("directory", "writeFile")), List.of(MCP_READ)).isEmpty());
	}
}
