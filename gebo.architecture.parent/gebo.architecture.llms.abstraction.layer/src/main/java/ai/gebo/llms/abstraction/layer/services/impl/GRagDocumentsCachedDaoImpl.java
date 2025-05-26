/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.abstraction.layer.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SearchRequest.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.config.service.IGGeboConfigService;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knowledgebase.repositories.DocumentReferenceSnapshotRepository;
import ai.gebo.llms.abstraction.layer.model.RagDocumentFragment;
import ai.gebo.llms.abstraction.layer.model.RagDocumentReferenceItem;
import ai.gebo.llms.abstraction.layer.model.RagDocumentsCachedDaoResult;
import ai.gebo.llms.abstraction.layer.model.RagQueryOptions;
import ai.gebo.llms.abstraction.layer.model.RagQueryOptions.CompletenessLevel;
import ai.gebo.llms.abstraction.layer.repositories.RagDocumentCacheItemRepository;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGRagDocumentsCachedDao;
import ai.gebo.llms.abstraction.layer.services.IGVectorSearchRestrictingFilterExpressionFactory;
import ai.gebo.llms.abstraction.layer.vectorstores.repository.VectorizedContentRepository;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.model.base.GObjectRef;
import ai.gebo.security.repository.UserRepository.UserInfos;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler;
import ai.gebo.systems.abstraction.layer.IGContentManagementSystemHandlerRepositoryPattern;
import jakarta.el.MethodNotFoundException;

/**
 * Implementation of the IGRagDocumentsCachedDao interface, responsible for
 * managing cached documents within the RAG (Retrieval-Augmented Generation)
 * framework.
 * 
 * AI generated comments
 */
@Component
@Scope("singleton")
public class GRagDocumentsCachedDaoImpl implements IGRagDocumentsCachedDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(GRagDocumentsCachedDaoImpl.class);

	@Autowired
	IGGeboConfigService geboConfigService;

	@Autowired
	IGPersistentObjectManager persistentObject;

	@Autowired
	IGContentManagementSystemHandlerRepositoryPattern contentSystemHandlersPattern;

	@Autowired
	RagDocumentCacheItemRepository cacheItemsRepository;

	@Autowired
	IGDocumentReferenceIngestionHandler ingestionHandler;

	@Autowired
	DocumentReferenceSnapshotRepository documentSnapshotRepository;

	@Autowired
	FullDocumentsCacheService cacheService;

	@Autowired
	VectorizedContentRepository vectorizedContentRepository;

	@Autowired
	SimilaritySearchService searchService;

	@Autowired(required = false)
	List<IGVectorSearchRestrictingFilterExpressionFactory> vectorSearchRestrictingFactories;

	public GRagDocumentsCachedDaoImpl() {

	}

	/**
	 * Creates a SearchRequest based on given filter conditions.
	 * 
	 * @param conditions The filter conditions for search.
	 * @return A constructed SearchRequest.
	 */
	private SearchRequest createSearchOnFilters(String conditions) {
		Builder builder = SearchRequest.builder();
		builder.filterExpression(conditions);
		builder.query("ricerca full");
		builder.similarityThresholdAll();
		builder.topK(1000000000);
		SearchRequest searchRequest = builder.build();
		return searchRequest;
	}

	/**
	 * Constructs an IN expression for a field using a list of IDs.
	 * 
	 * @param field The field name.
	 * @param ids   The list of IDs.
	 * @return The constructed IN expression as a string.
	 */
	private String inExpression(String field, List<String> ids) {
		String _expression = field + " IN [";
		for (int i = 0; i < ids.size(); i++) {
			_expression += "'" + ids.get(i) + "'";
			if (i < ids.size() - 1) {
				_expression += ",";
			}
		}
		_expression += "]";
		return _expression;
	}

	/**
	 * Creates a similarity query SearchRequest based on provided options, query,
	 * and filter.
	 * 
	 * @param options The query options.
	 * @param query   The query string.
	 * @param filter  The filter expression.
	 * @return The created SearchRequest.
	 */
	private SearchRequest createSimilarityQuery(RagQueryOptions options, String query, String filter) {
		Builder builder = SearchRequest.builder();
		builder.filterExpression(filter);
		builder.query(query);
		if (options.getTopK() > 0) {
			builder.topK(options.getTopK());
		}
		if (options.getSimilarityThreashold() > 0.0) {
			builder.similarityThreshold(options.getSimilarityThreashold());
		}
		SearchRequest searchRequest = builder.build();
		return searchRequest;
	}

	@Override
	public RagDocumentsCachedDaoResult chatWithDocumentsSearch(String query, RagQueryOptions ragQueryOptions,
			List<String> codes, List<String> knowledgeBases, IGConfigurableEmbeddingModel<?> embeddingModel,
			UserInfos user) {
		if (codes == null || codes.isEmpty() || knowledgeBases == null || knowledgeBases.isEmpty())
			return new RagDocumentsCachedDaoResult();
		if (ragQueryOptions.getMaxTokens() > 0) {

			return loadDocumentsWithTokenBudget(query, ragQueryOptions, codes, knowledgeBases, embeddingModel, user);

		} else
			return loadDocumentsFullContents(codes, knowledgeBases, user);
	}

	/**
	 * Loads full document contents without considering the token count.
	 * 
	 * @param codes          List of document codes.
	 * @param knowledgeBases List of knowledge bases.
	 * @param user
	 * @return The result containing full document contents.
	 */
	private RagDocumentsCachedDaoResult loadDocumentsFullContents(List<String> codes, List<String> knowledgeBases,
			UserInfos user) {
		try {
			RagDocumentsCachedDaoResult result = new RagDocumentsCachedDaoResult();
			final Map<String, GObjectRef<GProjectEndpoint>> endpointsCache = new HashMap<String, GObjectRef<GProjectEndpoint>>();
			final Map<String, List<GDocumentReference>> documentsCache = new HashMap<String, List<GDocumentReference>>();
			List<GDocumentReference> documents = persistentObject.findAllByIds(GDocumentReference.class, codes);
			documents.stream()
					.filter(x -> x.getRootKnowledgebaseCode() != null
							&& knowledgeBases.contains(x.getRootKnowledgebaseCode())
							&& x.getProjectEndpointReference() != null)
					.forEach(x -> {
						String className = x.getProjectEndpointReference().getClassName();
						String endPointCode = x.getProjectEndpointReference().getCode();
						String key = className + "|" + endPointCode;
						if (!endpointsCache.containsKey(key))
							endpointsCache.put(key, x.getProjectEndpointReference());
						if (!documentsCache.containsKey(key)) {
							documentsCache.put(key, new ArrayList<GDocumentReference>());
						}
						documentsCache.get(key).add(x);
					});

			for (Map.Entry<String, GObjectRef<GProjectEndpoint>> entry : endpointsCache.entrySet()) {
				String key = entry.getKey();
				GObjectRef<GProjectEndpoint> objectRef = entry.getValue();
				List<GDocumentReference> docList = documentsCache.get(key);
				try {
					cacheService.addCachedOrRetrieve(objectRef, docList, result);
				} catch (Throwable th) {
					LOGGER.error("Error in documents cache operations", th);
				}
			}
			result.recalculateSize();
			return result;
		} catch (GeboPersistenceException e) {
			throw new RuntimeException("Exception accessing persistence", e);
		} finally {
		}
	}

	@Override
	public RagDocumentsCachedDaoResult semanticSearchOnDocumentsList(String query, RagQueryOptions options,
			List<String> codes, List<String> knowledgeBases, IGConfigurableEmbeddingModel<?> embeddingModel,
			UserInfos user) {
		if (codes == null || codes.isEmpty() || knowledgeBases == null || knowledgeBases.isEmpty())
			return new RagDocumentsCachedDaoResult();
		String condition = filteringConditions(query, user, knowledgeBases, codes);
		SearchRequest searchRequest = null;
		switch (options.getCompleteness()) {
		case FULL_DOCUMENTS: {
			if (options.getMaxTokens() <= 0)
				return loadDocumentsFullContents(codes, knowledgeBases, user);
			else
				return loadDocumentsWithTokenBudget(query, options, codes, knowledgeBases, embeddingModel, user);
		}
		default: {
			searchRequest = createSimilarityQuery(options, query, condition);
		}
		}
		RagDocumentsCachedDaoResult result = searchService.executeSearch(searchRequest, embeddingModel);
		if (options.getMaxTokens() > 0) {
			if (result.getNTokens() > options.getMaxTokens()) {
				return decreaseSemanticSearchResultWithBudget(query, result, options, knowledgeBases, embeddingModel);
			}
		}
		return result;
	}

	private String filteringConditions(String query, UserInfos user, List<String> knowledgeBases,
			List<String> docsList) {
		String condition = null;
		if (knowledgeBases != null) {
			condition = inExpression(DocumentMetaInfos.KNOWLEDGEBASE_CODE, knowledgeBases);
		}
		if (docsList != null) {
			if (condition != null)
				condition += " AND ";
			else
				condition = "";
			condition += inExpression(DocumentMetaInfos.CONTENT_CODE, docsList);
		}
		if (vectorSearchRestrictingFactories != null && !vectorSearchRestrictingFactories.isEmpty()) {
			for (IGVectorSearchRestrictingFilterExpressionFactory vectorSearchRestrictingFactory : vectorSearchRestrictingFactories) {
				String restrictingFilter = vectorSearchRestrictingFactory.createAdditionalFilterExpression(query, user,
						knowledgeBases, docsList);
				if (restrictingFilter != null) {
					if (condition != null) {
						condition += " AND ";
					} else {
						condition = "";
					}
					condition += restrictingFilter;
				}
			}
		}
		return condition;
	}

	@Override
	public RagDocumentsCachedDaoResult semanticSearch(String query, RagQueryOptions options,
			List<String> knowledgeBases, IGConfigurableEmbeddingModel<?> embeddingModel, UserInfos user) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin semanticSearch({" + query.length() + " chars}, " + options + ", " + knowledgeBases
					+ ",...)");
		}
		if (knowledgeBases == null || knowledgeBases.isEmpty())
			return new RagDocumentsCachedDaoResult();
		String condition = filteringConditions(query, user, knowledgeBases, null);
		SearchRequest searchRequest = null;
		searchRequest = createSimilarityQuery(options, query, condition);
		CompletenessLevel completeness = options.getCompleteness();
		if (completeness == null) {
			completeness = CompletenessLevel.STRICT_QUERY_RELATED;
		}
		RagDocumentsCachedDaoResult result = searchService.executeSearch(searchRequest, embeddingModel);
		switch (completeness) {
		case STRICT_QUERY_RELATED: {

		}
			break;
		case FULL_DOCUMENTS: {

			result = chatWithDocumentsSearch(query, options,
					new ArrayList<String>(result.getDocumentItems().stream().map(x -> x.getCode()).toList()),
					knowledgeBases, embeddingModel, user);
		}
			break;
		case MAX_TOKENS: {
			if (options.getMaxTokens() <= 0)
				throw new RuntimeException(
						"Running a semanticSearch with MAX_TOKEN and no specified tokens budget makes no sense");
			if (options.getMaxTokens() >= result.getNTokens()) {
				// return result;
			} else {
				result = decreaseSemanticSearchResultWithBudget(query, result, options, knowledgeBases, embeddingModel);
			}

		}
			break;
		default: {
			throw new MethodNotFoundException(
					"Cannot search with " + options.getCompleteness().name() + " not yet implemented");
		}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End semanticSearch({" + query.length() + " chars}, " + options + ", " + knowledgeBases
					+ ",...)=>" + print(result));
		}
		return result;
	}

	/**
	 * Removes contents to stay within the token budget.
	 * 
	 * @param query          The search query.
	 * @param result         The search result.
	 * @param options        The query options.
	 * @param knowledgeBases The list of knowledge bases.
	 * @param embeddingModel The embedding model to use.
	 * @return The adjusted result that fits within the token budget.
	 */
	private RagDocumentsCachedDaoResult decreaseSemanticSearchResultWithBudget(String query,
			RagDocumentsCachedDaoResult result, RagQueryOptions options, List<String> knowledgeBases,
			IGConfigurableEmbeddingModel<?> embeddingModel) {
		result.recalculateSize();
		boolean continueRemoving = result.getNTokens() > options.getMaxTokens();
		for (int i = result.getDocumentItems().size() - 1; continueRemoving && i >= 0; i--) {
			RagDocumentReferenceItem docreference = result.getDocumentItems().get(i);
			for (int w = docreference.getFragments().size() - 1; continueRemoving && w >= 0; w--) {
				docreference.getFragments().remove(w);
				result.recalculateSize();
				continueRemoving = result.getNTokens() > options.getMaxTokens();
			}
			if (continueRemoving) {
				result.getDocumentItems().remove(i);
			}
		}
		return result;
	}

	/**
	 * Fills context window with available tokens that are coherent with the query.
	 * 
	 * @param query           The search query.
	 * @param ragQueryOptions The query options.
	 * @param codes           The list of document codes.
	 * @param knowledgeBases  The list of knowledge bases.
	 * @param embeddingModel  The embedding model used.
	 * @param user
	 * @return The result containing tokens that fit within the budget.
	 */
	private RagDocumentsCachedDaoResult loadDocumentsWithTokenBudget(String query, RagQueryOptions options,
			List<String> codes, List<String> knowledgeBases, IGConfigurableEmbeddingModel<?> embeddingModel,
			UserInfos user) {
		RagDocumentsCachedDaoResult result = loadDocumentsFullContents(codes, knowledgeBases, user);

		// if token budget is set and loaded documents are too heavy
		if (options.getMaxTokens() > 0 && result.getNTokens() > options.getMaxTokens()) {
			final Map<String, RagDocumentReferenceItem> perCodeFullContents = new HashMap<String, RagDocumentReferenceItem>();
			result.getDocumentItems().forEach(x -> {
				perCodeFullContents.put(x.getCode(), x);
			});
			String condition = filteringConditions(query, user, knowledgeBases, codes);
			SearchRequest searchRequest = null;
			RagQueryOptions restrictOptions = new RagQueryOptions(options);
			restrictOptions.setSimilarityThreashold(0.5);
			restrictOptions.setTopK(4 * codes.size());
			// Running a search on the documents base
			searchRequest = createSimilarityQuery(restrictOptions, query, condition);
			final RagDocumentsCachedDaoResult partializedResults = searchService.executeSearch(searchRequest,
					embeddingModel);
			// Ordering by token weight in results which is the most significant document
			// if this ordering fails at least document fragments will be organized with
			// most relevant document's fragment on top so it is pretty coherent
			// in a "per relevance" order
			partializedResults.orderByDocumentWeight();
			result = new RagDocumentsCachedDaoResult();

			final long tokensBudget = options.getMaxTokens();
			for (int i = 0; i < partializedResults.getDocumentItems().size(); i++) {
				RagDocumentReferenceItem document = partializedResults.getDocumentItems().get(i);
				RagDocumentReferenceItem fullVersion = perCodeFullContents.get(document.getCode());
				if (fullVersion == null) {
					LOGGER.warn("Full content for:" + document.getCode() + " has not been loaded");
					result.getDocumentItems().add(document);
					result.recalculateSize();
				} else {
					if (tokensBudget > (result.getNTokens() + fullVersion.getNTokens())) {
						result.getDocumentItems().add(fullVersion);
						result.recalculateSize();
					} else if (tokensBudget > (result.getNTokens() + document.getNTokens())) {
						result.getDocumentItems().add(document);
						result.recalculateSize();
					}
				}
			}

		}
		cleanWithoutSegments(result);
		return result;
	}

	/**
	 * Performs a multi-hop semantic search using the initial query to refine the
	 * subsequent queries.
	 * 
	 * @param initialQuery         The initial query string.
	 * @param options              The query options.
	 * @param knowledgeBases       The list of knowledge bases.
	 * @param embeddingModel       The embedding model used.
	 * @param firstSearchThreshold The threshold for the first hop.
	 * @param otherSearchThreshold The threshold for subsequent hops.
	 * @return The final search result after multiple hops.
	 */
	public RagDocumentsCachedDaoResult multiHopSemanticSearch(String initialQuery, RagQueryOptions options,
			List<String> knowledgeBases, IGConfigurableEmbeddingModel<?> embeddingModel, Double firstSearchThreshold,
			Double otherSearchThreshold, UserInfos user) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin multiHopSemanticSearch({" + initialQuery.length() + " chars}, " + options + ", "
					+ knowledgeBases + ", " + "..." + ", " + firstSearchThreshold + ", " + otherSearchThreshold + ")");
		}
		if (options.getMaxTokens() <= 0) {
			throw new IllegalArgumentException("Token budget richiesto per multi-hop");
		}
		if (firstSearchThreshold != null) {
			options.setSimilarityThreashold(firstSearchThreshold);
		}
		// First hop: retrieve fragments most similar to the initial query
		RagDocumentsCachedDaoResult result = semanticSearch(initialQuery, options, knowledgeBases, embeddingModel,
				user);

		// If there are tokens remaining, use the retrieved content as a basis for a
		// second query
		long tokensFirstHop = result.getNTokens();
		long remainingTokens = options.getMaxTokens() - tokensFirstHop;
		List<Document> newContextSlices = result.aiDocumentsList();
		if (remainingTokens > 100 && newContextSlices.size() < options.getTopK()) { // leave a minimum buffer

			if (!newContextSlices.isEmpty()) {
				for (Document item : newContextSlices) {
					if (item.getText() != null) {
						String refinedQuery = initialQuery + "\n[CONTEXT]\n" + item.getText() + "[/CONTEXT]";
						RagQueryOptions secondHopOptions = new RagQueryOptions(options);
						if (otherSearchThreshold != null) {
							secondHopOptions.setSimilarityThreashold(otherSearchThreshold);
						}
						secondHopOptions.setMaxTokens(remainingTokens);
						RagDocumentsCachedDaoResult secondHop = semanticSearch(refinedQuery, secondHopOptions,
								knowledgeBases, embeddingModel, user);
						result = mergeResults(result, secondHop, options.getMaxTokens());
						long tokensTotal = result.getNTokens();
						remainingTokens = options.getMaxTokens() - tokensTotal;
						if (result.aiDocumentsList().size() >= options.getTopK()) {
							break;
						}
					}
				}

			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End multiHopSemanticSearch({" + initialQuery.length() + " chars}, " + options + ", "
					+ knowledgeBases + ", " + "..." + ", " + firstSearchThreshold + ", " + otherSearchThreshold + ")=>"
					+ print(result));
		}
		return result;
	}

	/**
	 * Prints a summary of the RagDocumentsCachedDaoResult including total tokens
	 * and segments.
	 * 
	 * @param result The result to be printed.
	 * @return A string representation of the result summary.
	 */
	String print(RagDocumentsCachedDaoResult result) {
		String s = "{";
		int nSegments = 0;
		for (RagDocumentReferenceItem item : result.getDocumentItems()) {
			s += item.getCode() + ": " + item.getNTokens() + ",";
			nSegments += item.getFragments().size();
		}
		s += ",totalTokens: " + result.getNTokens() + ",totalSegments: " + nSegments + "}";
		return s;
	}

	/**
	 * Merges two RagDocumentsCachedDaoResult objects by concatenating their
	 * contents, ensuring the total tokens remain within the limit.
	 * 
	 * @param r1        The first result to merge.
	 * @param r2        The second result to merge.
	 * @param maxTokens The maximum allowable number of tokens.
	 * @return The merged result.
	 */
	private RagDocumentsCachedDaoResult mergeResults(RagDocumentsCachedDaoResult r1, RagDocumentsCachedDaoResult r2,
			long maxTokens) {
		RagDocumentsCachedDaoResult merged = new RagDocumentsCachedDaoResult();
		Map<String, RagDocumentReferenceItem> map = new HashMap<>();
		for (RagDocumentReferenceItem item : r1.getDocumentItems()) {
			if (!map.containsKey(item.getCode())) {
				if (merged.getNTokens() + item.getNTokens() <= maxTokens) {
					merged.getDocumentItems().add(item);
					merged.recalculateSize();
					map.put(item.getCode(), item);
				} else
					return merged;

			} else {
				RagDocumentReferenceItem alreadyIn = map.get(item.getCode());
				tryMerge(alreadyIn, item, merged, maxTokens);
			}
		}
		for (RagDocumentReferenceItem item : r2.getDocumentItems()) {
			if (!map.containsKey(item.getCode())) {
				if (merged.getNTokens() + item.getNTokens() <= maxTokens) {
					merged.getDocumentItems().add(item);
					merged.recalculateSize();
					map.put(item.getCode(), item);
				} else
					return merged;
			} else {
				RagDocumentReferenceItem alreadyIn = map.get(item.getCode());
				tryMerge(alreadyIn, item, merged, maxTokens);
			}
		}
		return merged;
	}

	/**
	 * Attempts to merge fragments of two document reference items within a token
	 * limit.
	 * 
	 * @param alreadyIn The document item already in the result.
	 * @param item      The new document item to merge.
	 * @param result    The result object into which merging is done.
	 * @param maxTokens The maximum allowable number of tokens.
	 */
	private void tryMerge(RagDocumentReferenceItem alreadyIn, RagDocumentReferenceItem item,
			RagDocumentsCachedDaoResult result, long maxTokens) {
		Map<String, RagDocumentFragment> fragmentsMap = new HashMap<>();
		for (RagDocumentFragment frag : alreadyIn.getFragments()) {
			fragmentsMap.put(frag.getDocument().getId(), frag);
		}
		for (RagDocumentFragment nested : item.getFragments()) {
			if (!fragmentsMap.containsKey(nested.getDocument().getId())) {
				if (result.getNTokens() + nested.getNTokens() <= maxTokens) {
					alreadyIn.getFragments().add(nested);
					result.recalculateSize();
				} else
					return;
			}
		}
	}

	/**
	 * Cleans up the result by removing document items that do not have any
	 * segments.
	 * 
	 * @param result The result to be cleaned.
	 */
	private void cleanWithoutSegments(RagDocumentsCachedDaoResult result) {
		List<RagDocumentReferenceItem> items2remove = result.getDocumentItems().stream()
				.filter(x -> x.getFragments().isEmpty()).toList();
		for (RagDocumentReferenceItem toremove : items2remove) {
			result.getDocumentItems().remove(toremove);
		}
	}

}