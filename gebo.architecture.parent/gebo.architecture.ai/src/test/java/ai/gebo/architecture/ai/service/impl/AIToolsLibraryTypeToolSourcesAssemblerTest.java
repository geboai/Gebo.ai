/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.ai.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.ai.tool.ToolCallback;

import ai.gebo.architecture.ai.model.AIToolsAndResourcesLibraryInstance;
import ai.gebo.architecture.ai.model.AIToolsAndResourcesLibraryType;
import ai.gebo.architecture.ai.model.ToolReference;
import ai.gebo.architecture.ai.service.AIToolsLibraryTypeToolSource;
import ai.gebo.architecture.ai.service.IGAIToolsAndResourcesLibrarySource;
import ai.gebo.architecture.ai.service.IGToolCallbackSource;

/**
 * Every library type of every library source becomes a registered tool source
 * once, with the id the agent configurations name it by.
 */
class AIToolsLibraryTypeToolSourcesAssemblerTest {

	private static AIToolsAndResourcesLibraryType type(String id) {
		AIToolsAndResourcesLibraryType type = new AIToolsAndResourcesLibraryType();
		type.setId(id);
		type.setDescription("Type " + id);
		ToolReference tool = new ToolReference();
		tool.setName("tool-of-" + id);
		tool.setDescription("A tool");
		type.setTools(List.of(tool));
		return type;
	}

	private static IGAIToolsAndResourcesLibrarySource library(String id, AIToolsAndResourcesLibraryType... types) {
		return new IGAIToolsAndResourcesLibrarySource() {
			@Override
			public String getId() {
				return id;
			}

			@Override
			public String getDescription() {
				return id;
			}

			@Override
			public List<AIToolsAndResourcesLibraryType> getManagedTypes() {
				return List.of(types);
			}

			@Override
			public AIToolsAndResourcesLibraryInstance getInstanceById(String id, String owner) {
				return null;
			}

			@Override
			public AIToolsAndResourcesLibraryInstance createInstance(AIToolsAndResourcesLibraryType type,
					List<String> owners) {
				return null;
			}

			@Override
			public void dropInstance(AIToolsAndResourcesLibraryInstance instance) {
			}

			@Override
			public List<ToolCallback> getToolCallbacks(AIToolsAndResourcesLibraryInstance instance) {
				return List.of();
			}
		};
	}

	@Test
	void everyTypeOfEverySourceIsRegisteredOnce() {
		GIAIToolsAndResourcesLibrarySourceRepositoryPatternImpl libraries = new GIAIToolsAndResourcesLibrarySourceRepositoryPatternImpl(
				List.of(library("files", type("working-directory"), type("scratch-db")),
						library("browsers", type("browser"))));
		GToolCallbackRepositoryPatternImpl tools = new GToolCallbackRepositoryPatternImpl(
				new ArrayList<IGToolCallbackSource>());
		AIToolsLibraryTypeToolSourcesAssembler assembler = new AIToolsLibraryTypeToolSourcesAssembler(libraries, tools);

		assertEquals(3, assembler.assemble());
		assertEquals(0, assembler.assemble(), "a second refresh registers nothing again");

		IGToolCallbackSource registered = tools.findByCode(AIToolsLibraryTypeToolSource.sourceIdOf("browser"));
		assertNotNull(registered);
		assertTrue(registered instanceof AIToolsLibraryTypeToolSource);
		assertEquals("tool-of-browser", registered.getToolCallbacks().get(0).getToolDefinition().name());
		assertNotNull(tools.resolve("tool-of-scratch-db"), "the registry knows the library's tools by name");
	}

	@Test
	void anInstallationWithNoLibrariesRegistersNothing() {
		GIAIToolsAndResourcesLibrarySourceRepositoryPatternImpl libraries = new GIAIToolsAndResourcesLibrarySourceRepositoryPatternImpl(
				null);
		GToolCallbackRepositoryPatternImpl tools = new GToolCallbackRepositoryPatternImpl(
				new ArrayList<IGToolCallbackSource>());

		assertEquals(0, new AIToolsLibraryTypeToolSourcesAssembler(libraries, tools).assemble());
	}
}
