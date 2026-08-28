/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.bravesearch.handler.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import ai.gebo.architecture.ai.model.ContextContentRequired;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.service.IGStaticPromptsProvider;
import lombok.Data;

/**
 * Registers the single Brave native-search planner prompt (the provider is an
 * INativeSearchService: the LLM produces a {@code BraveNativeSearchQuery} via
 * structured output).
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.bravesearch")
@Data
public class BraveSearchHandlerConfig implements IGStaticPromptsProvider {

	public static final String BRAVE_SEARCH_QUERY_EXTRACTION_PROMPT = "brave-search-query-extraction-prompt";

	private static final String queryExtractionPrompt = "You are a WEB SEARCH PLANNER for the Brave Search engine.\r\n"
			+ "\r\n"
			+ "Produce a native Brave query object:\r\n"
			+ "- searchedTexts: 2 to 8 concise web search queries (no duplicates). You may use Brave operators (\"exact\", -exclude, site:) when they clearly help.\r\n"
			+ "- freshness (optional): \"pd\" (past day), \"pw\" (past week), \"pm\" (past month), \"py\" (past year) when recency matters.\r\n"
			+ "- country (optional): 2-letter code to bias results (e.g. \"us\", \"it\") when the question is region-specific.\r\n"
			+ "- safesearch (optional): \"off\", \"moderate\" or \"strict\".\r\n"
			+ "\r\n"
			+ "Only set an option when it clearly improves the search; leave it unset otherwise.\r\n"
			+ "\r\n"
			+ "CONTEXT:\r\n"
			+ "The actual consolidated knowledge (eventually blank) is:\r\n{consolidated}\r\n"
			+ "\r\n"
			+ "OUTPUT FORMAT\r\n"
			+ "{format}\r\n";

	private GPromptTemplateConfig prompt = null;

	public BraveSearchHandlerConfig() {
		prompt = new GPromptTemplateConfig();
		prompt.setChatHistory(ContextContentRequired.NOT_REQUIRED);
		prompt.setPromptUse(BRAVE_SEARCH_QUERY_EXTRACTION_PROMPT);
		prompt.setSystemPromptTemplate(queryExtractionPrompt);
		prompt.setUserPromptTemplate("INPUT\r\nThe user question is: {question}\r\n");
	}

	@Override
	public List<GPromptTemplateConfig> promptsList() {
		return List.of(prompt);
	}
}
