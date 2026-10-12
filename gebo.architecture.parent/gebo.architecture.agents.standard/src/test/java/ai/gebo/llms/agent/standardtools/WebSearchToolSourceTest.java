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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.ai.tool.ToolCallback;

import ai.gebo.architecture.search.service.AbstractWebSearchServiceImpl;
import ai.gebo.architecture.search.service.ISearchService;
import ai.gebo.architecture.search.service.ISearchServiceRepositoryPattern;

/**
 * Pins the web search tool: one searchWeb tool over the first enabled provider,
 * searched with the provider's own query structure.
 */
class WebSearchToolSourceTest {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	void searchWebUsesTheEnabledProviderNativeQuery() throws Exception {
		AbstractWebSearchServiceImpl disabled = mock(AbstractWebSearchServiceImpl.class);
		when(disabled.isEnabled()).thenReturn(false);
		AbstractWebSearchServiceImpl provider = mock(AbstractWebSearchServiceImpl.class);
		when(provider.isEnabled()).thenReturn(true);
		when(provider.getId()).thenReturn("brave-web-search-service");
		when(provider.getNativeSearchDataStructureType()).thenReturn(SearchToolContentPipelineTest.JqlQuery.class);
		ISearchServiceRepositoryPattern repository = mock(ISearchServiceRepositoryPattern.class);
		when(repository.getImplementations()).thenReturn((List) List.of(disabled, provider));
		WebSearchToolSource source = new WebSearchToolSource(repository, mock(SearchToolContentPipeline.class));

		AbstractSearchServiceWrapperTool tool = source.webSearchTool();

		assertTrue(tool instanceof NativeSearchServiceWrapperTool, String.valueOf(tool));
		assertEquals(provider, tool.getWrapped());
		ToolCallback callback = source.getToolCallbacks().get(0);
		assertEquals(AbstractWebSearchServiceImpl.WEB_SEARCH_TOOL_NAME, callback.getToolDefinition().name());
		String schema = callback.getToolDefinition().inputSchema();
		assertTrue(schema.contains("\"jql\""), schema);
		assertTrue(schema.contains("\"searchObjective\""), schema);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	void noEnabledWebProviderMeansNoTool() throws Exception {
		ISearchService jira = mock(ISearchService.class);
		when(jira.isEnabled()).thenReturn(true);
		ISearchServiceRepositoryPattern repository = mock(ISearchServiceRepositoryPattern.class);
		when(repository.getImplementations()).thenReturn((List) List.of(jira));
		WebSearchToolSource source = new WebSearchToolSource(repository, mock(SearchToolContentPipeline.class));

		assertNull(source.webSearchTool());
		assertTrue(source.getToolCallbacks().isEmpty());
	}
}
