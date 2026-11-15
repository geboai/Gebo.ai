/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.agent.standard.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;

import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.search.service.INativeSearchService;
import ai.gebo.architecture.search.service.ISearchService;
import ai.gebo.architecture.search.service.ISearchServiceRepositoryPattern;
import ai.gebo.llms.agent.standard.services.InternalKnowledgeBaseSearchNetworkAgentService;
import ai.gebo.llms.deepsearch.service.IGExternalSearchSecurityService;

/**
 * A search agent runs a prompt of its own, patched for the network, and names the
 * template it comes from: the agent editor requires that code, so a copy of a
 * default search agent, the way to change one, can be saved.
 */
class SearchAgentsPromptCodeTest {
	private final IGPromptConfigDao prompts = mock(IGPromptConfigDao.class);
	private final ISearchServiceRepositoryPattern searches = mock(ISearchServiceRepositoryPattern.class);
	private final IGExternalSearchSecurityService searchSecurity = mock(IGExternalSearchSecurityService.class);

	private StandardAgentsInitialization initialization() throws Exception {
		when(prompts.findByPromptUse(anyString())).thenAnswer(call -> {
			GPromptTemplateConfig prompt = new GPromptTemplateConfig();
			prompt.setPromptUse(call.getArgument(0));
			prompt.setUserPromptTemplate("search for {question}");
			return prompt;
		});
		when(searchSecurity.isEnabledForCurrentUser(any())).thenReturn(true);
		return new StandardAgentsInitialization(searches, null, null, null, null, prompts, null, null, null, null,
				searchSecurity, new StandardAgentsConfig());
	}

	private static void assertRunsItsOwnPromptAndNamesItsTemplate(GAgentConfig config, String template) {
		assertNotNull(config.getCustomLoopPrompt());
		assertTrue(config.getCustomLoopPrompt().getUserPromptTemplate().contains("AGENT NETWORK CONTEXT"),
				"the prompt that runs is the one patched for the network");
		assertEquals(template, config.getMainLoopPromptUseCode());
		assertEquals(template, config.jsonClone().getMainLoopPromptUseCode(), "a copy keeps it");
	}

	@Test
	void theKnowledgeBaseSearchAgentNamesTheTemplateOfItsPrompt() throws Exception {
		GAgentConfig config = initialization().defaultInternalKnowledgeBaseSearch().getConfigurations().get(0);

		assertRunsItsOwnPromptAndNamesItsTemplate(config,
				InternalKnowledgeBaseSearchNetworkAgentService.SEARCH_PLANNER_PROMPT_USE_CODE);
	}

	@Test
	void aSearchServiceAgentNamesTheTemplateOfItsPrompt() throws Exception {
		ISearchService search = mock(ISearchService.class);
		when(search.isEnabled()).thenReturn(true);
		when(search.getProductId()).thenReturn("acme");
		when(search.getQueriesGenerationPromptUseCode()).thenReturn("acme-queries-prompt");
		when(searches.getImplementations()).thenReturn(List.of(search));

		GAgentConfig config = initialization().externalSourcesAgentConfigDataSource().getConfigurations().get(0);

		assertRunsItsOwnPromptAndNamesItsTemplate(config, "acme-queries-prompt");
	}

	@Test
	void aNativeSearchAgentNamesItsNativeTemplate() throws Exception {
		INativeSearchService search = mock(INativeSearchService.class);
		when(search.isEnabled()).thenReturn(true);
		when(search.getProductId()).thenReturn("google");
		when(search.getNativePromptTemplateUseCode()).thenReturn("google-search-query-extraction-prompt");
		when(searches.getImplementations()).thenReturn(List.of(search));

		GAgentConfig config = initialization().externalSourcesAgentConfigDataSource().getConfigurations().get(0);

		assertRunsItsOwnPromptAndNamesItsTemplate(config, "google-search-query-extraction-prompt");
	}
}
