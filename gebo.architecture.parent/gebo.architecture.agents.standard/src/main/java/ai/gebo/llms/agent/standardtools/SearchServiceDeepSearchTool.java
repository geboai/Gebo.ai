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

import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.document.Document;

import ai.gebo.architecture.documents.cache.model.ChunkingParams;
import ai.gebo.architecture.search.model.SearchQuery;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchableSystemMetaData;
import ai.gebo.architecture.search.model.SystemSearchOutcome;
import ai.gebo.architecture.search.service.INativeQueryObject;
import ai.gebo.architecture.search.service.INativeSearchService;
import ai.gebo.architecture.search.service.ISearchService;
import ai.gebo.llms.agent.standard.services.SearchResultsChunker;
import ai.gebo.llms.agent.standardtools.model.DeepSearchToolParam;
import ai.gebo.llms.agent.standardtools.model.DeepSearchToolResult.Source;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GResponseDocumentRef;
import ai.gebo.model.DocumentMetaInfos;

/**
 * The deep search of an external search service (the web, a document management
 * system, a mail server...): the agent's searches run on every system of the
 * service, and the documents found are loaded as LLM-sized fragments.
 * <p>
 * Q is the type of a search: the service's own query structure when it is a
 * native search service (the model then writes precise searches, a JQL filter, a
 * web search with its site or freshness...), plain text otherwise. A native search
 * a system rejects is run on it as a text search instead.
 */
public class SearchServiceDeepSearchTool<Q> extends AbstractDeepSearchTool<Q> {
	/** Fragments read of each document found. */
	static final int MAX_FRAGMENTS_PER_DOCUMENT = 6;
	/** Most results asked to a system for one search. */
	static final int MAX_RESULTS_PER_SEARCH = 20;
	static final int MIN_RESULTS_PER_SEARCH = 5;
	private final ISearchService<?> service;
	/** The service, when its searches are its own query structure. */
	@SuppressWarnings("rawtypes")
	private final INativeSearchService nativeService;
	private final String sourceDescription;

	SearchServiceDeepSearchTool(DeepSearchToolsSupport support, ISearchService<?> service, Class<Q> queryType,
			String toolName, String sourceDescription) {
		super(support, queryType, toolName, description(sourceDescription));
		this.service = service;
		this.nativeService = queryType != String.class && service instanceof INativeSearchService<?, ?> native_
				? native_
				: null;
		this.sourceDescription = sourceDescription;
	}

	/**
	 * The deep search tool of a service: searched with its own query structure when it
	 * is a native search service, with plain text otherwise.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static SearchServiceDeepSearchTool<?> of(DeepSearchToolsSupport support, ISearchService<?> service,
			String toolName, String sourceDescription) {
		if (service instanceof INativeSearchService nativeService
				&& nativeService.getNativeSearchDataStructureType() != null) {
			return new SearchServiceDeepSearchTool(support, service, nativeService.getNativeSearchDataStructureType(),
					toolName, sourceDescription);
		}
		return new SearchServiceDeepSearchTool<>(support, service, String.class, toolName, sourceDescription);
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

	/** Searches as the call asks, telling the systems it could not search. */
	@Override
	protected List<Document> searchDocuments(DeepSearchToolParam<Q> param, List<Q> queries, String question,
			int maxDocuments, int fragmentsPerDocument, Map<String, FoundDocument> foundByFragmentId,
			ToolContext toolContext, List<String> unavailableSources) throws Exception {
		return searchDocuments(queries, question, maxDocuments, foundByFragmentId, unavailableSources);
	}

	@Override
	protected List<Document> searchDocuments(List<Q> queries, String question, int maxDocuments,
			int fragmentsPerDocument, Map<String, FoundDocument> foundByFragmentId) throws Exception {
		return searchDocuments(queries, question, maxDocuments, foundByFragmentId, new ArrayList<>());
	}

	List<Document> searchDocuments(List<Q> queries, String question, int maxDocuments,
			Map<String, FoundDocument> foundByFragmentId, List<String> unavailableSources) throws Exception {
		// each document found is read whole (see SearchResultsChunker): fragmentsPerDocument
		// does not apply
		// no native search given: the question is searched as text
		final List<Object> searches = queries.isEmpty() ? List.of(question) : new ArrayList<>(queries);
		final int perSearch = Math.max(MIN_RESULTS_PER_SEARCH,
				Math.min(MAX_RESULTS_PER_SEARCH, (int) Math.ceil((double) maxDocuments / searches.size())));
		final List<SearchableSystemMetaData> systems = service.getSearchableSystems();
		final Map<String, SearchResult> found = new LinkedHashMap<>();
		int runs = 0;
		int failed = 0;
		search: for (Object query : searches) {
			if (systems == null) {
				break;
			}
			for (SearchableSystemMetaData system : systems) {
				if (system == null) {
					continue;
				}
				runs++;
				// best effort: a system out of service or not responding is told to the model
				final SystemSearchOutcome outcome = support.searchCalls().search(system, toolName,
						() -> searchSystem(query, system, perSearch));
				if (!outcome.available()) {
					failed++;
					if (!unavailableSources.contains(outcome.unavailableNotice())) {
						unavailableSources.add(outcome.unavailableNotice());
					}
					continue;
				}
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Tool:" + toolName + " search on system:" + system.getCode() + " returned "
							+ outcome.results().size() + " result(s)");
				}
				service.setOriginOn(outcome.results());
				for (SearchResult result : outcome.results()) {
					if (result != null && result.getCode() != null) {
						found.putIfAbsent(result.getCode(), result);
						if (found.size() >= maxDocuments) {
							break search;
						}
					}
				}
			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Tool:" + toolName + " ran " + runs + " search(es), " + failed + " not done, found "
					+ found.size() + " distinct document(s)");
		}
		if (found.isEmpty()) {
			if (runs > 0 && failed == runs) {
				throw new NoSourceSearchedException(unavailableSources);
			}
			return List.of();
		}
		final Set<String> keywords = new LinkedHashSet<>(SearchResultsChunker.keywordsFromText(question));
		for (Object query : searches) {
			keywords.addAll(SearchResultsChunker.keywordsFromText(queryText(query)));
		}
		final ChunkingParams params = SearchResultsChunker.buildChunkingParams(
				MAX_FRAGMENTS_PER_DOCUMENT * SearchResultsChunker.LLM_CHUNK_TOKENS, MAX_FRAGMENTS_PER_DOCUMENT,
				new ArrayList<>(keywords));
		final List<Document> fragments = SearchResultsChunker.chunkToDocuments(support.chunkingService(),
				new ArrayList<>(found.values()), params, MAX_FRAGMENTS_PER_DOCUMENT, toolName,
				support.documentsParallelism());
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

	/**
	 * Runs one search on one system: a native search with the service's own query
	 * structure, as text when it is text or when the system rejects the native one.
	 */
	@SuppressWarnings("unchecked")
	List<SearchResult> searchSystem(Object query, SearchableSystemMetaData system, int nEntryLimit)
			throws Exception {
		if (nativeService != null && query instanceof INativeQueryObject nativeQuery) {
			try {
				return nativeService.nativeSearch(nativeQuery, system, nEntryLimit);
			} catch (Exception e) {
				LOGGER.warn("Tool:" + toolName + " native search failed on system:" + system.getCode()
						+ ", searching it with the query text instead", e);
			}
		}
		final String text = queryText(query);
		final SearchQuery searchQuery = new SearchQuery();
		searchQuery.setQueryText(text);
		searchQuery.setRelevantKeywords(SearchResultsChunker.keywordsFromText(text));
		return service.search(searchQuery, system, nEntryLimit);
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
