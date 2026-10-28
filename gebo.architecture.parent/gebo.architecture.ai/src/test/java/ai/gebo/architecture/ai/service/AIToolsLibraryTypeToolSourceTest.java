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
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.definition.ToolDefinition;

import ai.gebo.architecture.ai.model.AIToolsAndResourcesLibraryInstance;
import ai.gebo.architecture.ai.model.AIToolsAndResourcesLibraryType;
import ai.gebo.architecture.ai.model.ToolReference;

/**
 * The tools of a library type stand in for those of whichever instance the run
 * holds: a call reaches the instance's own tool of that name, and a run holding
 * no instance is told the library is not available rather than failed.
 */
class AIToolsLibraryTypeToolSourceTest {
	static final String TYPE = "working-directory";

	/** A library whose instances answer with their own id, so the test sees which one was reached. */
	static final class AWorkingDirectoryLibrary implements IGAIToolsAndResourcesLibrarySource {
		final AIToolsAndResourcesLibraryType type = aType();

		static AIToolsAndResourcesLibraryType aType() {
			AIToolsAndResourcesLibraryType type = new AIToolsAndResourcesLibraryType();
			type.setId(TYPE);
			type.setDescription("A working directory");
			ToolReference read = new ToolReference();
			read.setName("readFile");
			read.setDescription("Reads a file");
			read.setInputSchema("{\"type\":\"object\",\"properties\":{\"path\":{\"type\":\"string\"}}}");
			ToolReference list = new ToolReference();
			list.setName("listFiles");
			list.setDescription("Lists the files");
			ToolReference nameless = new ToolReference();
			type.setTools(List.of(read, list, nameless));
			return type;
		}

		@Override
		public String getId() {
			return "working-directories";
		}

		@Override
		public String getDescription() {
			return "Working directories";
		}

		@Override
		public List<AIToolsAndResourcesLibraryType> getManagedTypes() {
			return List.of(type);
		}

		@Override
		public AIToolsAndResourcesLibraryInstance getInstanceById(String id, String owner) {
			return null;
		}

		@Override
		public AIToolsAndResourcesLibraryInstance createInstance(AIToolsAndResourcesLibraryType type,
				List<String> owners) {
			AIToolsAndResourcesLibraryInstance instance = new AIToolsAndResourcesLibraryInstance();
			instance.setId("dir-of-" + owners.get(0));
			instance.setLibraryType(type);
			return instance;
		}

		@Override
		public void dropInstance(AIToolsAndResourcesLibraryInstance instance) {
		}

		@Override
		public List<ToolCallback> getToolCallbacks(AIToolsAndResourcesLibraryInstance instance) {
			// only readFile: listFiles is declared by the type but this instance lacks it
			return List.of(new ToolCallback() {
				@Override
				public ToolDefinition getToolDefinition() {
					return ToolDefinition.builder().name("readFile").description("Reads a file").inputSchema("{}")
							.build();
				}

				@Override
				public String call(String toolInput) {
					return "read " + toolInput + " in " + instance.getId();
				}
			});
		}
	}

	private static ToolCallback toolNamed(AIToolsLibraryTypeToolSource source, String name) {
		return source.getToolCallbacks().stream().filter(tool -> name.equals(tool.getToolDefinition().name()))
				.findFirst().orElseThrow();
	}

	@Test
	void theTypeExportsOneStandInPerNamedTool() {
		AWorkingDirectoryLibrary library = new AWorkingDirectoryLibrary();
		AIToolsLibraryTypeToolSource source = new AIToolsLibraryTypeToolSource(library.type, library);

		assertEquals("ai-tools-library:" + TYPE, source.getId());
		assertTrue(source.isMountedOnlyWhenAsked(), "a library belongs to the agents naming it");
		assertEquals(List.of("readFile", "listFiles"),
				source.getToolCallbacks().stream().map(tool -> tool.getToolDefinition().name()).toList(),
				"the nameless reference is left out");
		assertEquals(AIToolsLibraryTypeToolSource.NO_INPUT_SCHEMA,
				toolNamed(source, "listFiles").getToolDefinition().inputSchema(),
				"a tool declaring no schema is offered as taking nothing");
	}

	@Test
	void aCallReachesTheToolOfTheInstanceTheRunHolds() {
		AWorkingDirectoryLibrary library = new AWorkingDirectoryLibrary();
		AIToolsLibraryTypeToolSource source = new AIToolsLibraryTypeToolSource(library.type, library);
		AIToolsAndResourcesLibraryInstance held = library.createInstance(library.type, List.of("session-1"));
		ToolContext context = new ToolContext(
				Map.of(ToolCallbackDeclarationUtil.LIBRARY_INSTANCES_CONTEXT_KEY, Map.of(TYPE, held)));

		assertEquals("read {\"path\":\"a.txt\"} in dir-of-session-1",
				toolNamed(source, "readFile").call("{\"path\":\"a.txt\"}", context));
	}

	@Test
	void aRunHoldingNoInstanceIsToldTheLibraryIsNotAvailable() {
		AWorkingDirectoryLibrary library = new AWorkingDirectoryLibrary();
		AIToolsLibraryTypeToolSource source = new AIToolsLibraryTypeToolSource(library.type, library);

		String answer = toolNamed(source, "readFile").call("{}", new ToolContext(Map.of()));

		assertTrue(answer.contains("not available in this run"), answer);
		assertTrue(toolNamed(source, "readFile").call("{}").contains("not available in this run"),
				"no context at all is the same: no instance");
	}

	@Test
	void aToolTheInstanceLacksIsSaidSo() {
		AWorkingDirectoryLibrary library = new AWorkingDirectoryLibrary();
		AIToolsLibraryTypeToolSource source = new AIToolsLibraryTypeToolSource(library.type, library);
		AIToolsAndResourcesLibraryInstance held = library.createInstance(library.type, List.of("session-1"));
		ToolContext context = new ToolContext(
				Map.of(ToolCallbackDeclarationUtil.LIBRARY_INSTANCES_CONTEXT_KEY, Map.of(TYPE, held)));

		String answer = toolNamed(source, "listFiles").call("{}", context);

		assertTrue(answer.contains("does not provide it"), answer);
	}
}
