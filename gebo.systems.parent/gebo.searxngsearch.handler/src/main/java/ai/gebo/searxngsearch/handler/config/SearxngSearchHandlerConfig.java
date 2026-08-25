/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.searxngsearch.handler.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import ai.gebo.architecture.ai.model.ContextContentRequired;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.service.IGStaticPromptsProvider;
import lombok.Data;

/**
 * Registers the single SearXNG native-search planner prompt (the provider is an
 * INativeSearchService: the LLM produces a {@code SearxngNativeSearchQuery} via
 * structured output).
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.searxngsearch")
@Data
public class SearxngSearchHandlerConfig implements IGStaticPromptsProvider {

	public static final String SEARXNG_SEARCH_QUERY_EXTRACTION_PROMPT = "searxng-search-query-extraction-prompt";

	private static final String queryExtractionPrompt = "You are a WEB SEARCH PLANNER.\r\n"
			+ "\r\n"
			+ "Produce a native search query object:\r\n"
			+ "- searchedTexts: 2 to 8 concise, natural-language web search queries (no duplicates).\r\n"
			+ "- categories (optional): comma-separated categories such as \"general\", \"news\", \"science\", \"it\".\r\n"
			+ "- timeRange (optional): \"day\", \"week\", \"month\" or \"year\" when recency matters.\r\n"
			+ "- language (optional): a language code such as \"en\" or \"it\".\r\n"
			+ "\r\n"
			+ "Only set an option when it clearly improves the search; leave it unset otherwise.\r\n"
			+ "\r\n"
			+ "CONTEXT:\r\n"
			+ "The actual consolidated knowledge (eventually blank) is:\r\n{consolidated}\r\n"
			+ "\r\n"
			+ "OUTPUT FORMAT\r\n"
			+ "{format}\r\n";

	private GPromptTemplateConfig prompt = null;

	public SearxngSearchHandlerConfig() {
		prompt = new GPromptTemplateConfig();
		prompt.setChatHistory(ContextContentRequired.NOT_REQUIRED);
		prompt.setPromptUse(SEARXNG_SEARCH_QUERY_EXTRACTION_PROMPT);
		prompt.setSystemPromptTemplate(queryExtractionPrompt);
		prompt.setUserPromptTemplate("INPUT\r\nThe user question is: {question}\r\n");
	}

	@Override
	public List<GPromptTemplateConfig> promptsList() {
		return List.of(prompt);
	}
}
