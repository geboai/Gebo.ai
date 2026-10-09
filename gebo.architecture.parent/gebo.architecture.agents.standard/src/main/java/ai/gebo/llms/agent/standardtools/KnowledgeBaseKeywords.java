/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.standardtools;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.ObjectProvider;

import ai.gebo.architecture.fulltext.service.IGFullTextSearchService;

/**
 * The keywords of the knowledge base tools: exact terms for the full-text (lexical)
 * leg of their search. The tools declare a keywords parameter only when that leg
 * exists (a full-text search service is configured); otherwise they keep their
 * plain signature.
 */
public final class KnowledgeBaseKeywords {

	public static final String KEYWORDS_DESCRIPTION = "Optional exact terms for the full-text search: names, technical terms, "
			+ "short quotations, written as the documents write them (their language, spelling and accents). "
			+ "Each keyword is searched as a whole (all its words), the keywords one or the other. One string, the "
			+ "keywords separated by commas (e.g. \"Svabhavat, Dhyan Chohans\").";

	private KnowledgeBaseKeywords() {
	}

	/** Whether the full-text leg exists, so the tools take keywords. */
	static boolean enabled(ObjectProvider<IGFullTextSearchService> fullTextSearchService) {
		return fullTextSearchService != null && fullTextSearchService.getIfAvailable() != null;
	}

	/**
	 * The full-text queries of a search: its keywords when it gives some, its queries
	 * otherwise; blanks and repetitions left out.
	 */
	static List<String> fullTextQueries(String keywords, List<String> queries) {
		return fullTextQueries(commaSeparated(keywords), queries);
	}

	/**
	 * The items of a comma separated string (the keywords, the alternative queries of a
	 * tool call: one string, which every model writes, where a list is not), trimmed, the
	 * blank ones left out.
	 */
	static List<String> commaSeparated(String text) {
		final List<String> items = new ArrayList<>();
		if (text != null) {
			for (String item : text.split(",")) {
				if (!item.isBlank()) {
					items.add(item.strip());
				}
			}
		}
		return items;
	}

	static List<String> fullTextQueries(List<String> keywords, List<String> queries) {
		final List<String> fromKeywords = cleaned(keywords);
		return !fromKeywords.isEmpty() ? fromKeywords : cleaned(queries);
	}

	static List<String> cleaned(List<String> values) {
		final Set<String> kept = new LinkedHashSet<>();
		if (values != null) {
			for (String value : values) {
				if (value != null && !value.isBlank()) {
					kept.add(value.trim());
				}
			}
		}
		return new ArrayList<>(kept);
	}
}
