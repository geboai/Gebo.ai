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
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.ai.document.Document;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import ai.gebo.acl.AclGrantType;
import ai.gebo.acl.ContentAccessPolicy;
import ai.gebo.architecture.agents.services.GAbstractGenericalAgentService;
import ai.gebo.architecture.ai.model.LLMtInteractionContextThreadLocal;
import ai.gebo.architecture.ai.model.LLMtInteractionContextThreadLocal.KBContext;
import ai.gebo.architecture.ai.model.ToolReference;
import ai.gebo.architecture.ai.model.ToolsCategory;
import ai.gebo.architecture.ai.service.IGDocumentContentRenderer;
import ai.gebo.architecture.ai.service.IGDocumentContentRendererProvider;
import ai.gebo.architecture.ai.service.IGToolCallbackSource;
import ai.gebo.architecture.ai.service.ToolCallbackDeclarationUtil;
import ai.gebo.architecture.fulltext.model.FullTextSearchMetaDataFilter;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.architecture.rag.support.layer.model.SemanticSearchMetaDataFilter;
import ai.gebo.core.contents.security.services.IGKnowledgebaseVisibilityService;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GResponseDocumentRef;
import ai.gebo.llms.chat.abstraction.layer.services.IGDocumentsSearchService;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.security.services.IGSecurityService;
import lombok.Data;

/**
 * The internal knowledge base search as a tool, for an agent that operates its own
 * tools, such as the single agent working in a loop. It searches the knowledge
 * bases of the chat when the interaction context carries them, otherwise all the
 * ones the current user can see, with the user's ACL filter when the platform
 * access policy is ACL based.
 * <p>
 * The answer is bounded to {@value #MAX_RESULT_TOKENS} tokens, shared equally among
 * the documents found, so a search never floods the model's context.
 */
@ConditionalOnProperty(prefix = "ai.gebo.agents.standard", name = "enabled", havingValue = "true", matchIfMissing = true)
@Service
public class InternalKnowledgeBaseSearchToolSource implements IGToolCallbackSource {
	private static final Logger LOGGER = LoggerFactory.getLogger(InternalKnowledgeBaseSearchToolSource.class);
	/** The id of this tool source, kept out of the default network's automatic mounting. */
	public static final String INTERNAL_KNOWLEDGE_BASE_SEARCH_TOOL_SOURCE = "internal-knowledge-base-search-tool-source";
	public static final String SEARCH_KNOWLEDGE_BASE_TOOL = "searchKnowledgeBase";
	private static final String SEARCH_KNOWLEDGE_BASE_DESCRIPTION = "Search the company's internal knowledge base documents "
			+ "visible to the user. Give a precise question or topic, and optionally a few alternative phrasings; returns "
			+ "the most relevant document fragments with their titles and sources.";
	static final int MAX_RESULT_TOKENS = 6000;
	static final int DEFAULT_TOP_K = 10;
	static final int MAX_TOP_K = 30;
	private static final String NEWLINE = "\r\n";

	// Resolved on use: the tool sources are collected while the chat models are built,
	// and the search reaches back to the chat models, so injecting it directly would be
	// a dependency cycle at startup.
	private final ObjectProvider<IGDocumentsSearchService> documentsSearchService;
	private final ObjectProvider<IGKnowledgebaseVisibilityService> knowledgeBaseVisibilityService;
	private final IGSecurityService securityService;
	private final IGDocumentContentRendererProvider rendererFactory;

	public InternalKnowledgeBaseSearchToolSource(ObjectProvider<IGDocumentsSearchService> documentsSearchService,
			ObjectProvider<IGKnowledgebaseVisibilityService> knowledgeBaseVisibilityService,
			IGSecurityService securityService, IGDocumentContentRendererProvider rendererFactory) {
		this.documentsSearchService = documentsSearchService;
		this.knowledgeBaseVisibilityService = knowledgeBaseVisibilityService;
		this.securityService = securityService;
		this.rendererFactory = rendererFactory;
	}

	@Data
	@JsonClassDescription("An internal knowledge base search")
	public static class KnowledgeBaseSearchParam {
		@JsonPropertyDescription("The question or topic to search, as precise as possible")
		private String query;
		@JsonPropertyDescription("Optional alternative phrasings of the query, to widen the semantic search")
		private List<String> alternativeQueries;
		@JsonPropertyDescription("Optional maximum number of fragments to return, 10 when not given")
		private Integer topK;
	}

	@Override
	public String getId() {
		return INTERNAL_KNOWLEDGE_BASE_SEARCH_TOOL_SOURCE;
	}

	@Override
	public ToolsCategory getToolCategory() {
		return ToolsCategory.KNOWLEDGE_BASE_VARIOUS_SEARCHES;
	}

	@Override
	public List<ToolReference> getFullToolReferences() {
		ToolReference reference = new ToolReference();
		reference.setName(SEARCH_KNOWLEDGE_BASE_TOOL);
		reference.setDescription(SEARCH_KNOWLEDGE_BASE_DESCRIPTION);
		return List.of(reference);
	}

	@Override
	public List<ToolCallback> getToolCallbacks() {
		// the call is recorded for the request by the tool wrapper (RunAsToolCallback)
		BiFunction<KnowledgeBaseSearchParam, ToolContext, String> search = (param, toolContext) -> {
			KBContext interaction = LLMtInteractionContextThreadLocal.Context.get();
			if (param != null && param.getQuery() != null && !param.getQuery().isBlank()) {
				ToolsProgress.notify(toolContext,
						"Searching the knowledge base: " + ToolsProgress.shown(param.getQuery()));
			}
			return search(param, interaction, ToolsFoundDocuments.from(toolContext));
		};
		return List.of(ToolCallbackDeclarationUtil.declare(search, SEARCH_KNOWLEDGE_BASE_TOOL,
				SEARCH_KNOWLEDGE_BASE_DESCRIPTION, KnowledgeBaseSearchParam.class, String.class));
	}

	/**
	 * Runs the search and renders the fragments found within
	 * {@value #MAX_RESULT_TOKENS} tokens. A failure is answered as text, so the model
	 * can go on without this search.
	 */
	String search(KnowledgeBaseSearchParam param, KBContext interaction) {
		return search(param, interaction, null);
	}

	/**
	 * Runs the search, sharing the documents found with the calling agent when it
	 * collects them (see {@link ToolsFoundDocuments}).
	 */
	String search(KnowledgeBaseSearchParam param, KBContext interaction, ToolsFoundDocuments collector) {
		if (param == null || param.getQuery() == null || param.getQuery().isBlank()) {
			return "No search done: the query is empty.";
		}
		try {
			List<String> kbCodes = knowledgeBaseCodes(interaction);
			if (kbCodes.isEmpty()) {
				return "No internal knowledge base is available to the user.";
			}
			SemanticSearchMetaDataFilter semanticFilter = new SemanticSearchMetaDataFilter();
			semanticFilter.setKnowledgeBasesCodes(kbCodes);
			FullTextSearchMetaDataFilter fullTextFilter = new FullTextSearchMetaDataFilter();
			fullTextFilter.setKnowledgebaseCodes(kbCodes);
			if (securityService.getPlatformContentAccessPolicy() == ContentAccessPolicy.ACL_BASED
					&& !securityService.isCurrentUserAdmin()) {
				List<Integer> aclAliases = securityService.getCurrentAclGrantedAccessor(AclGrantType.READ)
						.getAllOwnedAclAliases();
				semanticFilter.setAclAliases(aclAliases);
				fullTextFilter.setAclAliases(aclAliases);
			}
			List<String> semanticQueries = new ArrayList<>();
			semanticQueries.add(param.getQuery());
			if (param.getAlternativeQueries() != null) {
				param.getAlternativeQueries().stream().filter(x -> x != null && !x.isBlank()).limit(4)
						.forEach(semanticQueries::add);
			}
			int topK = param.getTopK() != null ? Math.max(1, Math.min(MAX_TOP_K, param.getTopK())) : DEFAULT_TOP_K;
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Begin search(...) knowledge base tool over " + kbCodes.size() + " knowledge base(s) with "
						+ semanticQueries.size() + " quer(ies) topK:" + topK);
			}
			AIDocumentsSet found = documentsSearchService.getObject().search(param.getQuery(), semanticQueries, semanticFilter,
					List.of(param.getQuery()), fullTextFilter, param.getQuery(), topK, MAX_RESULT_TOKENS * 2);
			List<Document> documents = found != null ? found.aiDocumentsList() : List.of();
			if (documents.isEmpty()) {
				return "No document found in the internal knowledge base for: " + param.getQuery();
			}
			if (collector != null) {
				// the documents found become the calling agent's answer documents; sharing
				// them never fails the search
				try {
					List<GResponseDocumentRef> refs = GResponseDocumentRef.from(found);
					collector.add(refs);
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Knowledge base tool shared " + refs.size()
								+ " document(s) with the calling agent's answer");
					}
				} catch (Throwable th) {
					LOGGER.warn("Knowledge base tool could not share its documents with the calling agent", th);
				}
			}
			List<String> rendered = new ArrayList<>();
			for (Document document : documents) {
				IGDocumentContentRenderer<Object> renderer = rendererFactory.get(document);
				rendered.add(renderer != null ? renderer.render(document) : document.getText());
			}
			List<String> fitted = GAbstractGenericalAgentService.fitEqually(rendered, MAX_RESULT_TOKENS);
			StringBuilder answer = new StringBuilder();
			answer.append(documents.size()).append(" fragment(s) found:").append(NEWLINE);
			// the documents these fragments come from, the only evidence of this search
			answer.append(documentsLine(documents)).append(NEWLINE);
			for (String fragment : fitted) {
				answer.append(fragment).append(NEWLINE);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("End search(...) knowledge base tool found " + documents.size() + " fragment(s), answer of "
						+ answer.length() + " character(s)");
			}
			if (LOGGER.isTraceEnabled()) {
				LOGGER.trace("<KNOWLEDGE_BASE_TOOL_ANSWER>");
				LOGGER.trace(answer.toString());
				LOGGER.trace("</KNOWLEDGE_BASE_TOOL_ANSWER>");
			}
			return answer.toString();
		} catch (Throwable e) {
			LOGGER.error("Knowledge base search tool failed for query:" + param.getQuery(), e);
			return "The internal knowledge base search failed, go on without it.";
		}
	}

	/**
	 * The documents the fragments come from, with their fragments: what this search
	 * found is evidence of these documents only.
	 */
	static String documentsLine(List<Document> documents) {
		final Map<String, Integer> fragmentsByDocument = new LinkedHashMap<>();
		for (Document document : documents) {
			final Map<String, Object> metaData = document.getMetadata();
			Object name = metaData != null ? metaData.get(DocumentMetaInfos.GEBO_FILE_NAME) : null;
			if (name == null && metaData != null) {
				name = metaData.get(DocumentMetaInfos.CONTENT_CODE);
			}
			fragmentsByDocument.merge(name != null ? String.valueOf(name) : "unnamed document", 1, Integer::sum);
		}
		final StringBuilder line = new StringBuilder("Documents of these fragments (the only ones this search found): ");
		boolean first = true;
		for (Map.Entry<String, Integer> entry : fragmentsByDocument.entrySet()) {
			if (!first) {
				line.append(", ");
			}
			line.append(entry.getKey()).append(" (").append(entry.getValue()).append(")");
			first = false;
		}
		return line.append(".").toString();
	}

	/** The knowledge bases of the chat when known, otherwise all the visible ones. */
	private List<String> knowledgeBaseCodes(KBContext interaction) throws Exception {
		if (interaction != null && interaction.getKnowledgeBasesCodes() != null
				&& !interaction.getKnowledgeBasesCodes().isEmpty()) {
			return interaction.getKnowledgeBasesCodes();
		}
		List<GKnowledgeBase> visibles = knowledgeBaseVisibilityService.getObject().allVisibleKnowledgebases();
		return visibles != null ? visibles.stream().map(GKnowledgeBase::getCode).toList() : List.of();
	}
}
