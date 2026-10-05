/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.standardtools;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.springframework.ai.document.Document;

import ai.gebo.acl.AclGrantType;
import ai.gebo.acl.ContentAccessPolicy;
import ai.gebo.architecture.ai.model.LLMtInteractionContextThreadLocal;
import ai.gebo.architecture.ai.model.LLMtInteractionContextThreadLocal.KBContext;
import ai.gebo.architecture.fulltext.model.FullTextSearchMetaDataFilter;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.architecture.rag.support.layer.model.SemanticSearchMetaDataFilter;
import ai.gebo.core.contents.security.services.IGKnowledgebaseVisibilityService;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.llms.agent.standardtools.model.DeepSearchToolParam;
import ai.gebo.llms.agent.standardtools.model.DeepSearchToolResult.Source;
import ai.gebo.llms.agent.standardtools.model.KnowledgeBaseDeepSearchToolParam;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GResponseDocumentRef;
import ai.gebo.llms.chat.abstraction.layer.services.IGDocumentsSearchService;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.security.services.IGSecurityService;

/**
 * The deep search of the internal knowledge bases: those of the chat when the
 * interaction context carries them, otherwise all the ones the user can see, with
 * the user's ACL filter when the platform access policy is ACL based. The agent's
 * searches run both as semantic and as full text searches.
 */
public class KnowledgeBaseDeepSearchTool extends AbstractDeepSearchTool<String> {
	public static final String DEEP_SEARCH_KNOWLEDGE_BASE_TOOL = "deepSearchKnowledgeBase";
	static final String DESCRIPTION = "Deep search of the company's internal knowledge base: runs your searches, "
			+ "reads every document fragment found and returns an analysis of them against your question, with the "
			+ "documents it relies on. Slow and expensive: use it only when the answer needs many documents (a "
			+ "report, an analysis, a comparison, a decision), not for a fact a plain search finds.";
	/** Fragments retrieved, on average, for each document a deep search reads. */
	static final int FRAGMENTS_PER_DOCUMENT = 3;
	/** Most tokens of fragments a knowledge base deep search reads. */
	static final int MAX_RETRIEVED_TOKENS = 120000;
	private final IGDocumentsSearchService documentsSearchService;
	private final IGKnowledgebaseVisibilityService knowledgeBaseVisibilityService;
	private final IGSecurityService securityService;

	public KnowledgeBaseDeepSearchTool(DeepSearchToolsSupport support, IGDocumentsSearchService documentsSearchService,
			IGKnowledgebaseVisibilityService knowledgeBaseVisibilityService, IGSecurityService securityService) {
		super(support, String.class, DEEP_SEARCH_KNOWLEDGE_BASE_TOOL, DESCRIPTION);
		this.documentsSearchService = documentsSearchService;
		this.knowledgeBaseVisibilityService = knowledgeBaseVisibilityService;
		this.securityService = securityService;
	}

	@Override
	protected boolean isAvailable() {
		// the search only reaches the knowledge bases and documents the user can see
		return true;
	}

	@Override
	protected String sourceDescription() {
		return "the internal knowledge base";
	}

	/** The parameter with keywords when the knowledge base searches have a full-text leg. */
	@Override
	protected Type paramType() {
		if (support.knowledgeBaseKeywordsEnabled()) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Declaring tool:" + toolName + " with keywords for the full-text search");
			}
			return KnowledgeBaseDeepSearchToolParam.class;
		}
		return super.paramType();
	}

	/** The full-text leg searches the keywords of the call when it gives some, else its queries. */
	@Override
	protected List<Document> searchDocuments(DeepSearchToolParam<String> param, List<String> queries, String question,
			int maxDocuments, int fragmentsPerDocument, Map<String, FoundDocument> foundByFragmentId) throws Exception {
		final List<String> fullTextQueries = KnowledgeBaseKeywords.fullTextQueries(
				param instanceof KnowledgeBaseDeepSearchToolParam withKeywords ? withKeywords.getKeywords() : null, queries);
		return searchDocuments(queries, fullTextQueries, question, maxDocuments, fragmentsPerDocument, foundByFragmentId);
	}

	@Override
	protected List<Document> searchDocuments(List<String> queries, String question, int maxDocuments,
			int fragmentsPerDocument, Map<String, FoundDocument> foundByFragmentId) throws Exception {
		return searchDocuments(queries, queries, question, maxDocuments, fragmentsPerDocument, foundByFragmentId);
	}

	List<Document> searchDocuments(List<String> queries, List<String> fullTextQueries, String question,
			int maxDocuments, int fragmentsPerDocument, Map<String, FoundDocument> foundByFragmentId) throws Exception {
		final List<String> kbCodes = knowledgeBaseCodes(LLMtInteractionContextThreadLocal.Context.get());
		if (kbCodes.isEmpty()) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Tool:" + toolName + " no knowledge base available to the user");
			}
			return List.of();
		}
		final SemanticSearchMetaDataFilter semanticFilter = new SemanticSearchMetaDataFilter();
		semanticFilter.setKnowledgeBasesCodes(kbCodes);
		final FullTextSearchMetaDataFilter fullTextFilter = new FullTextSearchMetaDataFilter();
		fullTextFilter.setKnowledgebaseCodes(kbCodes);
		// the full text search keeps the best chunks of each document (3 by default): a
		// knowledge base of a few documents would give a deep search a handful of fragments
		fullTextFilter.setPerDocumentInnerHits(fragmentsPerDocument);
		if (securityService.getPlatformContentAccessPolicy() == ContentAccessPolicy.ACL_BASED
				&& !securityService.isCurrentUserAdmin()) {
			final List<Integer> aclAliases = securityService.getCurrentAclGrantedAccessor(AclGrantType.READ)
					.getAllOwnedAclAliases();
			semanticFilter.setAclAliases(aclAliases);
			fullTextFilter.setAclAliases(aclAliases);
		}
		final int topK = maxDocuments * FRAGMENTS_PER_DOCUMENT;
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Tool:" + toolName + " searching " + kbCodes.size() + " knowledge base(s) with "
					+ queries.size() + " search(es) topK:" + topK + " fragmentsPerDocument:" + fragmentsPerDocument
					+ " full-text queries:" + fullTextQueries);
		}
		final AIDocumentsSet found = documentsSearchService.search(question, queries, semanticFilter, fullTextQueries,
				fullTextFilter, question, topK, MAX_RETRIEVED_TOKENS);
		final List<Document> fragments = found != null ? found.aiDocumentsList() : List.of();
		for (Document fragment : fragments) {
			final Map<String, Object> metaData = fragment.getMetadata();
			final Object code = metaData.get(DocumentMetaInfos.CONTENT_CODE);
			if (code != null && fragment.getId() != null) {
				foundByFragmentId.put(fragment.getId(),
						new FoundDocument(
								new Source(stringOf(metaData.get(DocumentMetaInfos.GEBO_FILE_NAME)),
										stringOf(metaData.get(DocumentMetaInfos.CONTENT_ORIGINAL_URL)), code.toString()),
								new GResponseDocumentRef(fragment)));
			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Tool:" + toolName + " found " + fragments.size() + " fragment(s) from "
					+ distinctByDocument(foundByFragmentId.values()).size() + " document(s)");
		}
		return fragments;
	}

	/** The knowledge bases of the chat when known, otherwise all the visible ones. */
	private List<String> knowledgeBaseCodes(KBContext interaction) throws Exception {
		if (interaction != null && interaction.getKnowledgeBasesCodes() != null
				&& !interaction.getKnowledgeBasesCodes().isEmpty()) {
			return interaction.getKnowledgeBasesCodes();
		}
		final List<GKnowledgeBase> visibles = knowledgeBaseVisibilityService.allVisibleKnowledgebases();
		return visibles != null ? visibles.stream().map(GKnowledgeBase::getCode).toList() : List.of();
	}

	private static String stringOf(Object value) {
		return value != null ? String.valueOf(value) : null;
	}
}
