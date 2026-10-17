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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import ai.gebo.architecture.search.service.ISearchService;
import ai.gebo.llms.chat.abstraction.layer.services.IGDocumentsSearchService;
import ai.gebo.security.services.IGSecurityService;

/**
 * The admin descriptions of the search tools: short, telling the source searched,
 * never the long text the model reads.
 */
class ToolAdminDescriptionsTest {

	@Test
	void deepSearchOfTheKnowledgeBaseTellsTheSource() {
		KnowledgeBaseDeepSearchTool tool = new KnowledgeBaseDeepSearchTool(mock(DeepSearchToolsSupport.class),
				mock(IGDocumentsSearchService.class), mock(IGSecurityService.class));
		assertEquals("Deep search of the user accessible knowledge base(s): many searches, the contents found analysed and summarised",
				DeepSearchToolSource.adminDescription(tool));
	}

	@Test
	void deepSearchOfTheWebTellsTheWeb() {
		ISearchService<?> service = mock(ISearchService.class);
		when(service.getProductId()).thenReturn("google");
		SearchServiceDeepSearchTool<?> tool = SearchServiceDeepSearchTool.of(mock(DeepSearchToolsSupport.class), service,
				DeepSearchToolSource.DEEP_SEARCH_WEB_TOOL, "the web");
		assertEquals("Deep search of the public web: many searches, the contents found analysed and summarised",
				DeepSearchToolSource.adminDescription(tool));
	}

	@Test
	void deepSearchOfAServiceTellsItsDescriptionAndProduct() {
		ISearchService<?> service = mock(ISearchService.class);
		when(service.getProductId()).thenReturn("confluence");
		when(service.getDescription()).thenReturn("Company wiki");
		SearchServiceDeepSearchTool<?> tool = SearchServiceDeepSearchTool.of(mock(DeepSearchToolsSupport.class), service,
				"deepSearchConfluence", "Company wiki (confluence)");
		assertEquals("Deep search of Company wiki (confluence): many searches, the contents found analysed and summarised",
				DeepSearchToolSource.adminDescription(tool));
	}

	@Test
	void searchedNamesTheServiceByDescriptionThenProduct() {
		ISearchService<?> withDescription = mock(ISearchService.class);
		when(withDescription.getProductId()).thenReturn("confluence");
		when(withDescription.getDescription()).thenReturn("Company wiki");
		assertEquals("Company wiki (confluence)", StandardSearchesToolsImpl.searched(withDescription));
		ISearchService<?> bare = mock(ISearchService.class);
		when(bare.getProductId()).thenReturn("confluence");
		assertEquals("confluence", StandardSearchesToolsImpl.searched(bare));
	}
}
