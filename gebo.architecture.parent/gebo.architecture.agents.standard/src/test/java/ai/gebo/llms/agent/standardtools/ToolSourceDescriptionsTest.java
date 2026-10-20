/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.agent.standardtools;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.ai.tool.ToolCallback;

import ai.gebo.architecture.ai.model.ToolReference;
import ai.gebo.architecture.ai.model.ToolsCategory;
import ai.gebo.architecture.ai.service.IGToolCallbackSource;
import ai.gebo.architecture.search.service.ISearchServiceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.functions.ActualDateFunctions;

/**
 * Every tool source tells the administrator what it is: the sources sharing a
 * category with another one say it themselves, the others are told by their
 * category, and a source with neither is told by its id.
 */
class ToolSourceDescriptionsTest {

	@Test
	void theSearchSourcesTellThemselvesApart() {
		ISearchServiceRepositoryPattern searches = mock(ISearchServiceRepositoryPattern.class);
		assertEquals("Knowledge base search", new InternalKnowledgeBaseSearchToolSource(null, null, null, null, null).getDescription());
		assertEquals("Web search", new WebSearchToolSource(searches, null).getDescription());
		assertEquals("Searches of the configured search services", new StandardSearchesToolsImpl(searches, null).getDescription());
		assertEquals("Deep searches of the knowledge bases, the search services and the web",
				new DeepSearchToolSource(null, searches, null, null).getDescription());
		// the knowledge base search and browsing share a category: their descriptions differ
		assertNotEquals(new InternalKnowledgeBaseSearchToolSource(null, null, null, null, null).getDescription(),
				new KnowledgeBaseBrowsingToolSource(null, null, null, null).getDescription());
	}

	@Test
	void aSourceWithItsOwnCategoryIsToldByIt() {
		assertEquals("Actual date and time functions", new ActualDateFunctions().getDescription());
	}

	@Test
	void aSourceWithoutACategoryDescriptionIsToldByItsId() {
		IGToolCallbackSource bare = new IGToolCallbackSource() {
			@Override
			public String getId() {
				return "bare-source";
			}

			@Override
			public ToolsCategory getToolCategory() {
				return new ToolsCategory();
			}

			@Override
			public List<ToolReference> getFullToolReferences() {
				return List.of();
			}

			@Override
			public List<ToolCallback> getToolCallbacks() {
				return List.of();
			}
		};
		assertEquals("bare-source", bare.getDescription());
	}
}
