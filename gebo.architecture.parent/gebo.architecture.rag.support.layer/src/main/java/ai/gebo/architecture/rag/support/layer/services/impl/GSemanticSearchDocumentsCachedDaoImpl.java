/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.architecture.rag.support.layer.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SearchRequest.Builder;
import org.springframework.ai.vectorstore.filter.Filter;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder.Op;
import org.springframework.ai.vectorstore.filter.FilterExpressionTextParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.acl.ContentAccessPolicy;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentFragment;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentReferenceItem;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.architecture.rag.support.layer.model.RagQueryOptions;
import ai.gebo.architecture.rag.support.layer.model.RagQueryOptions.CompletenessLevel;
import ai.gebo.architecture.rag.support.layer.repository.RagDocumentCacheItemRepository;
import ai.gebo.architecture.rag.support.layer.services.IGSemanticSearchDocumentsCachedDao;
import ai.gebo.config.service.IGGeboConfigService;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knowledgebase.repositories.DocumentReferenceSnapshotRepository;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGVectorSearchRestrictingFilterExpressionFactory;

import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.model.base.GObjectRef;
import ai.gebo.security.repository.UserRepository.UserInfos;
import ai.gebo.security.services.IAclGrantedAccessorService;
import ai.gebo.security.services.IGSecurityService;
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
public class GSemanticSearchDocumentsCachedDaoImpl implements IGSemanticSearchDocumentsCachedDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(GSemanticSearchDocumentsCachedDaoImpl.class);

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
	AIDocumentsCacheService cacheService;

	@Autowired
	SimilaritySearchService searchService;

	@Autowired(required = false)
	List<IGVectorSearchRestrictingFilterExpressionFactory> vectorSearchRestrictingFactories;
	@Autowired
	IAclGrantedAccessorService accessorService;
	@Autowired
	IGSecurityService securityService;

	public GSemanticSearchDocumentsCachedDaoImpl() {

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
	private static final String ACL_ALIASES_METADATA_FIELD = "aclAliases";

	private SearchRequest createSimilarityQuery(RagQueryOptions options, String query, String filter,
			List<Integer> aclAliases) {

		SearchRequest.Builder builder = SearchRequest.builder().query(query);

		if (options.getTopK() > 0) {
			builder.topK(options.getTopK());
		}

		if (options.getSimilarityThreashold() > 0.0) {
			builder.similarityThreshold(options.getSimilarityThreashold());
		}
		boolean filterWithAcl = !securityService.isCurrentUserAdmin()
				&& securityService.getPlatformContentAccessPolicy() == ContentAccessPolicy.ACL_BASED;
		Filter.Expression combinedFilter = buildCombinedFilterExpression(filter, filterWithAcl ? aclAliases : null);
		if (combinedFilter != null) {
			builder.filterExpression(combinedFilter);
		}

		return builder.build();
	}

	private Filter.Expression buildCombinedFilterExpression(String filter, List<Integer> aclAliases) {
		Filter.Expression baseExpression = null;
		if (filter != null && !filter.isBlank()) {
			baseExpression = new FilterExpressionTextParser().parse(filter);
		}

		Filter.Expression aclExpression = buildAclOverlapExpression(aclAliases);

		if (baseExpression == null) {
			return aclExpression;
		}
		if (aclExpression == null) {
			return baseExpression;
		}

		FilterExpressionBuilder b = new FilterExpressionBuilder();
		Op exp1 = new FilterExpressionBuilder.Op(baseExpression);
		Op exp2 = new FilterExpressionBuilder.Op(aclExpression);
		return b.and(exp1, exp2).build();
	}

	private Filter.Expression buildAclOverlapExpression(List<Integer> aclAliases) {
		if (aclAliases == null || aclAliases.isEmpty()) {
			return null;
		}

		FilterExpressionBuilder b = new FilterExpressionBuilder();

		Filter.Expression expr = null;
		for (Integer alias : aclAliases.stream().filter(Objects::nonNull).distinct().toList()) {
			Filter.Expression single = b.eq(ACL_ALIASES_METADATA_FIELD, alias.longValue()).build();
			Op singleOp = new Op(single);
			expr = (expr == null) ? single : b.or(new Op(expr), singleOp).build();
		}
		return expr;
	}

	@Override
	public AIDocumentsSet chatWithDocumentsSearch(String query, RagQueryOptions ragQueryOptions, List<String> codes,
			List<String> knowledgeBases, IGConfigurableEmbeddingModel<?> embeddingModel, UserInfos user) {
		if (codes == null || codes.isEmpty() || knowledgeBases == null || knowledgeBases.isEmpty())
			return new AIDocumentsSet();
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
	private AIDocumentsSet loadDocumentsFullContents(List<String> codes, List<String> knowledgeBases, UserInfos user) {
		try {
			AIDocumentsSet result = new AIDocumentsSet();
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
	public AIDocumentsSet semanticSearchOnDocumentsList(String query, RagQueryOptions options, List<String> codes,
			List<String> knowledgeBases, IGConfigurableEmbeddingModel<?> embeddingModel, UserInfos user) {
		if (codes == null || codes.isEmpty() || knowledgeBases == null || knowledgeBases.isEmpty())
			return new AIDocumentsSet();
		String condition = filteringConditions(query, user, knowledgeBases, codes);
		List<Integer> aclAliases = accessorService.fromUser(user).getAllOwnedAclAliases();
		SearchRequest searchRequest = null;
		switch (options.getCompleteness()) {
		case FULL_DOCUMENTS: {
			if (options.getMaxTokens() <= 0)
				return loadDocumentsFullContents(codes, knowledgeBases, user);
			else
				return loadDocumentsWithTokenBudget(query, options, codes, knowledgeBases, embeddingModel, user);
		}
		default: {
			searchRequest = createSimilarityQuery(options, query, condition, aclAliases);
		}
		}
		AIDocumentsSet result = searchService.executeSearch(searchRequest, embeddingModel);
		if (options.getMaxTokens() > 0) {
			if (result.getTokensSize() > options.getMaxTokens()) {
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
	public AIDocumentsSet semanticSearch(String query, RagQueryOptions options, List<String> knowledgeBases,
			IGConfigurableEmbeddingModel<?> embeddingModel, UserInfos user) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin semanticSearch({" + query.length() + " chars}, " + options + ", " + knowledgeBases
					+ ",...)");
		}
		if (knowledgeBases == null || knowledgeBases.isEmpty())
			return new AIDocumentsSet();
		List<Integer> aclAliases = accessorService.fromUser(user).getAllOwnedAclAliases();
		String condition = filteringConditions(query, user, knowledgeBases, null);
		SearchRequest searchRequest = null;
		searchRequest = createSimilarityQuery(options, query, condition, aclAliases);
		CompletenessLevel completeness = options.getCompleteness();
		if (completeness == null) {
			completeness = CompletenessLevel.STRICT_QUERY_RELATED;
		}
		AIDocumentsSet result = searchService.executeSearch(searchRequest, embeddingModel);
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
			if (options.getMaxTokens() >= result.getTokensSize()) {
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
	private AIDocumentsSet decreaseSemanticSearchResultWithBudget(String query, AIDocumentsSet result,
			RagQueryOptions options, List<String> knowledgeBases, IGConfigurableEmbeddingModel<?> embeddingModel) {
		result.recalculateSize();
		boolean continueRemoving = result.getTokensSize() > options.getMaxTokens();
		for (int i = result.getDocumentItems().size() - 1; continueRemoving && i >= 0; i--) {
			AIDocumentReferenceItem docreference = result.getDocumentItems().get(i);
			for (int w = docreference.getFragments().size() - 1; continueRemoving && w >= 0; w--) {
				docreference.getFragments().remove(w);
				result.recalculateSize();
				continueRemoving = result.getTokensSize() > options.getMaxTokens();
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
	private AIDocumentsSet loadDocumentsWithTokenBudget(String query, RagQueryOptions options, List<String> codes,
			List<String> knowledgeBases, IGConfigurableEmbeddingModel<?> embeddingModel, UserInfos user) {

		AIDocumentsSet result = loadDocumentsFullContents(codes, knowledgeBases, user);

		// if token budget is set and loaded documents are too heavy
		if (options.getMaxTokens() > 0 && result.getTokensSize() > options.getMaxTokens()) {
			final Map<String, AIDocumentReferenceItem> perCodeFullContents = new HashMap<String, AIDocumentReferenceItem>();
			result.getDocumentItems().forEach(x -> {
				perCodeFullContents.put(x.getCode(), x);
			});
			String condition = filteringConditions(query, user, knowledgeBases, codes);
			SearchRequest searchRequest = null;
			RagQueryOptions restrictOptions = new RagQueryOptions(options);
			restrictOptions.setSimilarityThreashold(0.5);
			restrictOptions.setTopK(4 * codes.size());
			List<Integer> aclAliases = accessorService.fromUser(user).getAllOwnedAclAliases();
			// Running a search on the documents base
			searchRequest = createSimilarityQuery(restrictOptions, query, condition, aclAliases);
			final AIDocumentsSet partializedResults = searchService.executeSearch(searchRequest, embeddingModel);
			// Ordering by token weight in results which is the most significant document
			// if this ordering fails at least document fragments will be organized with
			// most relevant document's fragment on top so it is pretty coherent
			// in a "per relevance" order
			partializedResults.orderByDocumentWeight();
			result = new AIDocumentsSet();

			final long tokensBudget = options.getMaxTokens();
			for (int i = 0; i < partializedResults.getDocumentItems().size(); i++) {
				AIDocumentReferenceItem document = partializedResults.getDocumentItems().get(i);
				AIDocumentReferenceItem fullVersion = perCodeFullContents.get(document.getCode());
				if (fullVersion == null) {
					LOGGER.warn("Full content for:" + document.getCode() + " has not been loaded");
					result.getDocumentItems().add(document);
					result.recalculateSize();
				} else {
					if (tokensBudget > (result.getTokensSize() + fullVersion.getTokensSize())) {
						result.getDocumentItems().add(fullVersion);
						result.recalculateSize();
					} else if (tokensBudget > (result.getTokensSize() + document.getTokensSize())) {
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
	public AIDocumentsSet multiHopSemanticSearch(String initialQuery, RagQueryOptions options,
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
		AIDocumentsSet result = semanticSearch(initialQuery, options, knowledgeBases, embeddingModel, user);

		// If there are tokens remaining, use the retrieved content as a basis for a
		// second query
		long tokensFirstHop = result.getTokensSize();
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
						AIDocumentsSet secondHop = semanticSearch(refinedQuery, secondHopOptions, knowledgeBases,
								embeddingModel, user);
						result = mergeResults(result, secondHop, options.getMaxTokens());
						long tokensTotal = result.getTokensSize();
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
	String print(AIDocumentsSet result) {
		String s = "{";
		int nSegments = 0;
		for (AIDocumentReferenceItem item : result.getDocumentItems()) {
			s += item.getCode() + ": " + item.getTokensSize() + ",";
			nSegments += item.getFragments().size();
		}
		s += ",totalTokens: " + result.getTokensSize() + ",totalSegments: " + nSegments + "}";
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
	private AIDocumentsSet mergeResults(AIDocumentsSet r1, AIDocumentsSet r2, long maxTokens) {
		AIDocumentsSet merged = new AIDocumentsSet();
		Map<String, AIDocumentReferenceItem> map = new HashMap<>();
		for (AIDocumentReferenceItem item : r1.getDocumentItems()) {
			if (!map.containsKey(item.getCode())) {
				if (merged.getTokensSize() + item.getTokensSize() <= maxTokens) {
					merged.getDocumentItems().add(item);
					merged.recalculateSize();
					map.put(item.getCode(), item);
				} else
					return merged;

			} else {
				AIDocumentReferenceItem alreadyIn = map.get(item.getCode());
				tryMerge(alreadyIn, item, merged, maxTokens);
			}
		}
		for (AIDocumentReferenceItem item : r2.getDocumentItems()) {
			if (!map.containsKey(item.getCode())) {
				if (merged.getTokensSize() + item.getTokensSize() <= maxTokens) {
					merged.getDocumentItems().add(item);
					merged.recalculateSize();
					map.put(item.getCode(), item);
				} else
					return merged;
			} else {
				AIDocumentReferenceItem alreadyIn = map.get(item.getCode());
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
	private void tryMerge(AIDocumentReferenceItem alreadyIn, AIDocumentReferenceItem item, AIDocumentsSet result,
			long maxTokens) {
		Map<String, AIDocumentFragment> fragmentsMap = new HashMap<>();
		for (AIDocumentFragment frag : alreadyIn.getFragments()) {
			fragmentsMap.put(frag.toAIDocument().getId(), frag);
		}
		for (AIDocumentFragment nested : item.getFragments()) {
			if (!fragmentsMap.containsKey(nested.toAIDocument().getId())) {
				if (result.getTokensSize() + nested.getTokensSize() <= maxTokens) {
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
	private void cleanWithoutSegments(AIDocumentsSet result) {
		List<AIDocumentReferenceItem> items2remove = result.getDocumentItems().stream()
				.filter(x -> x.getFragments().isEmpty()).toList();
		for (AIDocumentReferenceItem toremove : items2remove) {
			result.getDocumentItems().remove(toremove);
		}
	}

}