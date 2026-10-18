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
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.definition.ToolDefinition;

import ai.gebo.architecture.ai.model.ToolReference;
import ai.gebo.architecture.ai.model.ToolSourceInfo;
import ai.gebo.architecture.ai.model.ToolsCategory;
import ai.gebo.architecture.ai.service.IGToolCallbackSource;

/**
 * The registered tool sources are listed for the administrator with their id,
 * description, category and the tools they export now; a source that cannot be
 * read counts no tools but is still listed.
 */
class ToolSourceInfosTest {

	private static ToolCallback tool(String name) {
		final ToolDefinition definition = ToolDefinition.builder().name(name).description(name).inputSchema("{}")
				.build();
		return new ToolCallback() {
			@Override
			public ToolDefinition getToolDefinition() {
				return definition;
			}

			@Override
			public String call(String toolInput) {
				return "";
			}
		};
	}

	private static IGToolCallbackSource source(String id, String description, ToolsCategory category,
			List<ToolCallback> tools) {
		return new IGToolCallbackSource() {
			@Override
			public String getId() {
				return id;
			}

			@Override
			public String getDescription() {
				return description != null ? description : IGToolCallbackSource.super.getDescription();
			}

			@Override
			public ToolsCategory getToolCategory() {
				return category;
			}

			@Override
			public List<ToolReference> getFullToolReferences() {
				return List.of();
			}

			@Override
			public List<ToolCallback> getToolCallbacks() {
				if (tools == null) {
					throw new IllegalStateException("not readable");
				}
				return tools;
			}
		};
	}

	@Test
	void theSourcesAreListedWithTheirDescriptionCategoryAndToolsCount() {
		ToolsCategory searches = new ToolsCategory("SEARCHES", "Searches", true);
		GToolCallbackRepositoryPatternImpl repository = new GToolCallbackRepositoryPatternImpl(List.of(
				source("kb-search", "Knowledge base search", searches, List.of(tool("searchKnowledgeBase"))),
				source("date", null, new ToolsCategory("DATE", "Date and time", false), List.of(tool("now"), tool("today"))),
				source("broken", "Broken", searches, null)));

		List<ToolSourceInfo> infos = repository.getToolSourceInfos();

		assertEquals(List.of("kb-search", "date", "broken"), infos.stream().map(ToolSourceInfo::getId).toList());
		assertEquals("Knowledge base search", infos.get(0).getDescription());
		assertEquals("SEARCHES", infos.get(0).getCategoryCode());
		assertEquals("Searches", infos.get(0).getCategoryDescription());
		assertEquals(1, infos.get(0).getToolsCount());
		assertEquals("Date and time", infos.get(1).getDescription(), "told by its category when it says nothing");
		assertEquals(2, infos.get(1).getToolsCount());
		assertEquals(0, infos.get(2).getToolsCount(), "a source that cannot be read counts no tools");
	}

	@Test
	void noSourcesNoInfos() {
		assertTrue(new GToolCallbackRepositoryPatternImpl(null).getToolSourceInfos().isEmpty());
	}
}
