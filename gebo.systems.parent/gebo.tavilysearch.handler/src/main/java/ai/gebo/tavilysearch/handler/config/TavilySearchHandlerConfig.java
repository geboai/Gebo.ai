/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.tavilysearch.handler.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import ai.gebo.architecture.ai.model.ContextContentRequired;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.service.IGStaticPromptsProvider;
import lombok.Data;

/**
 * Registers the single Tavily native-search planner prompt (the provider is an
 * INativeSearchService, so the LLM produces a {@code TavilyNativeSearchQuery}
 * via structured output). One prompt, referenced by both the native and the
 * fallback query-generation use-codes (see the Google handler for the pattern).
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.tavilysearch")
@Data
public class TavilySearchHandlerConfig implements IGStaticPromptsProvider {

	public static final String TAVILY_SEARCH_QUERY_EXTRACTION_PROMPT = "tavily-search-query-extraction-prompt";

	private static final String queryExtractionPrompt = "You are a WEB SEARCH PLANNER.\r\n"
			+ "\r\n"
			+ "Produce a native search query object:\r\n"
			+ "- searchedTexts: 2 to 8 concise, natural-language web search queries (no operators, no duplicates).\r\n"
			+ "- searchDepth (optional): \"basic\" for quick lookups, \"advanced\" for deeper/high-quality research.\r\n"
			+ "- topic (optional): \"news\" when the question is about current events, otherwise \"general\".\r\n"
			+ "- timeRange (optional): \"day\", \"week\", \"month\" or \"year\" when recency matters; leave unset otherwise.\r\n"
			+ "\r\n"
			+ "Only set an option when it clearly improves the search; leave it unset otherwise.\r\n"
			+ "\r\n"
			+ "CONTEXT:\r\n"
			+ "The actual consolidated knowledge (eventually blank) is:\r\n{consolidated}\r\n"
			+ "\r\n"
			+ "OUTPUT FORMAT\r\n"
			+ "{format}\r\n";

	private GPromptTemplateConfig prompt = null;

	public TavilySearchHandlerConfig() {
		prompt = new GPromptTemplateConfig();
		prompt.setChatHistory(ContextContentRequired.NOT_REQUIRED);
		prompt.setPromptUse(TAVILY_SEARCH_QUERY_EXTRACTION_PROMPT);
		prompt.setSystemPromptTemplate(queryExtractionPrompt);
		prompt.setUserPromptTemplate("INPUT\r\nThe user question is: {question}\r\n");
	}

	@Override
	public List<GPromptTemplateConfig> promptsList() {
		return List.of(prompt);
	}
}
