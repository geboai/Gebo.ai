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
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.ai.document.Document;

import ai.gebo.architecture.documents.cache.model.ChunkingParams;
import ai.gebo.architecture.search.model.SearchQuery;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchableSystemMetaData;
import ai.gebo.architecture.search.service.ISearchService;
import ai.gebo.llms.agent.standard.services.SearchResultsChunker;
import ai.gebo.llms.agent.standardtools.model.DeepSearchToolResult.Source;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GResponseDocumentRef;
import ai.gebo.model.DocumentMetaInfos;

/**
 * The deep search of an external search service (the web, a document management
 * system, a mail server...): the agent's searches run on every system of the
 * service, and the documents found are loaded as LLM-sized fragments.
 */
public class SearchServiceDeepSearchTool extends AbstractDeepSearchTool {
	/** Fragments read of each document found. */
	static final int MAX_FRAGMENTS_PER_DOCUMENT = 6;
	/** Most results asked to a system for one search. */
	static final int MAX_RESULTS_PER_SEARCH = 20;
	static final int MIN_RESULTS_PER_SEARCH = 5;
	private final ISearchService<?> service;
	private final String sourceDescription;

	public SearchServiceDeepSearchTool(DeepSearchToolsSupport support, ISearchService<?> service, String toolName,
			String sourceDescription) {
		super(support, toolName, description(sourceDescription));
		this.service = service;
		this.sourceDescription = sourceDescription;
	}

	static String description(String sourceDescription) {
		return "Deep search of " + sourceDescription + ": runs your searches, reads every document found and "
				+ "returns an analysis of them against your question, with the documents it relies on. Slow and "
				+ "expensive: use it only when the answer needs many documents (a report, an analysis, a comparison, "
				+ "a decision), not for a fact a plain search finds.";
	}

	public ISearchService<?> getService() {
		return service;
	}

	@Override
	protected boolean isAvailable() throws Exception {
		// the same per-user check as the search tools, the search agents and deep search
		return support.externalSearchSecurityService().isEnabledForCurrentUser(service);
	}

	@Override
	protected String sourceDescription() {
		return sourceDescription;
	}

	@Override
	protected List<Document> searchDocuments(List<String> queries, String question, int maxDocuments,
			Map<String, FoundDocument> foundByFragmentId) throws Exception {
		final int perSearch = Math.max(MIN_RESULTS_PER_SEARCH,
				Math.min(MAX_RESULTS_PER_SEARCH, (int) Math.ceil((double) maxDocuments / queries.size())));
		final List<SearchableSystemMetaData> systems = service.getSearchableSystems();
		final Map<String, SearchResult> found = new LinkedHashMap<>();
		int searches = 0;
		int failed = 0;
		search: for (String query : queries) {
			final SearchQuery searchQuery = new SearchQuery();
			searchQuery.setQueryText(query);
			searchQuery.setRelevantKeywords(SearchResultsChunker.keywordsFromText(query));
			if (systems == null) {
				break;
			}
			for (SearchableSystemMetaData system : systems) {
				if (system == null) {
					continue;
				}
				searches++;
				try {
					final List<SearchResult> results = service.search(searchQuery, system, perSearch);
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Tool:" + toolName + " search on system:" + system.getCode() + " returned "
								+ (results != null ? results.size() : 0) + " result(s)");
					}
					if (results == null) {
						continue;
					}
					service.setOriginOn(results);
					for (SearchResult result : results) {
						if (result != null && result.getCode() != null) {
							found.putIfAbsent(result.getCode(), result);
							if (found.size() >= maxDocuments) {
								break search;
							}
						}
					}
				} catch (Throwable th) {
					failed++;
					LOGGER.error("Tool:" + toolName + " failed searching system:" + system.getCode(), th);
				}
			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Tool:" + toolName + " ran " + searches + " search(es), " + failed + " failed, found "
					+ found.size() + " distinct document(s)");
		}
		if (found.isEmpty()) {
			if (searches > 0 && failed == searches) {
				throw new IllegalStateException("Every search on " + service.getId() + " failed");
			}
			return List.of();
		}
		final Set<String> keywords = new LinkedHashSet<>(SearchResultsChunker.keywordsFromText(question));
		for (String query : queries) {
			keywords.addAll(SearchResultsChunker.keywordsFromText(query));
		}
		final ChunkingParams params = SearchResultsChunker.buildChunkingParams(
				MAX_FRAGMENTS_PER_DOCUMENT * SearchResultsChunker.LLM_CHUNK_TOKENS, MAX_FRAGMENTS_PER_DOCUMENT,
				new ArrayList<>(keywords));
		final List<Document> fragments = SearchResultsChunker.chunkToDocuments(support.chunkingService(),
				new ArrayList<>(found.values()), params, MAX_FRAGMENTS_PER_DOCUMENT, toolName);
		for (Document fragment : fragments) {
			final Object code = fragment.getMetadata().get(DocumentMetaInfos.CONTENT_CODE);
			final SearchResult result = code != null ? found.get(code.toString()) : null;
			if (result != null) {
				// the ref keeps the search result, so the user can chat with it
				foundByFragmentId.put(fragment.getId(),
						new FoundDocument(sourceOf(result), new GResponseDocumentRef(result)));
			}
		}
		return fragments;
	}

	static Source sourceOf(SearchResult result) {
		String title = null;
		String location = null;
		if (result.getResultReference() != null) {
			title = notBlank(result.getResultReference().getName()) ? result.getResultReference().getName()
					: result.getResultReference().getTitle();
			location = result.getResultReference().getUri();
		}
		if (result.getNavigationReference() != null && result.getNavigationReference().path != null) {
			if (!notBlank(title)) {
				title = result.getNavigationReference().path.name;
			}
			if (!notBlank(location)) {
				location = result.getNavigationReference().path.absolutePath;
			}
		}
		if (!notBlank(title)) {
			title = result.getDescriptiveText();
		}
		return new Source(title, location, result.getCode());
	}
}
