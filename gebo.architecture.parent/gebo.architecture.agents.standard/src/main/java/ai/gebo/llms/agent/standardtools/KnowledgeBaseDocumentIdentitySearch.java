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
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import ai.gebo.acl.AclGrantType;
import ai.gebo.acl.ContentAccessPolicy;
import ai.gebo.architecture.rag.support.layer.model.SemanticSearchMetaDataFilter;
import ai.gebo.core.contents.security.services.IGKnowledgebaseVisibilityService;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.model.EmbedType;
import ai.gebo.model.base.GObjectRef;
import ai.gebo.security.services.IGSecurityService;

/**
 * Finds the documents of knowledge bases by the meaning of their file name or of
 * their title: a semantic search on the vectors embedding them (see
 * {@link EmbedType#FILE_NAME} and {@link EmbedType#TITLE}), with the access rights
 * of the knowledge base searches, one hit per document, the best first.
 */
@Component
public class KnowledgeBaseDocumentIdentitySearch {
	private static final Logger LOGGER = LoggerFactory.getLogger(KnowledgeBaseDocumentIdentitySearch.class);

	/** A document found: its uniqueId, the name or title matched and how similar it is. */
	public record FoundDocument(Long uniqueId, String matched, double score, String embeddingModelCode) {
	}

	private final ObjectProvider<IGEmbeddingModelRuntimeConfigurationDao> embeddingModels;
	private final ObjectProvider<IGKnowledgebaseVisibilityService> visibilityService;
	private final ObjectProvider<IGSecurityService> securityService;

	public KnowledgeBaseDocumentIdentitySearch(ObjectProvider<IGEmbeddingModelRuntimeConfigurationDao> embeddingModels,
			ObjectProvider<IGKnowledgebaseVisibilityService> visibilityService,
			ObjectProvider<IGSecurityService> securityService) {
		this.embeddingModels = embeddingModels;
		this.visibilityService = visibilityService;
		this.securityService = securityService;
	}

	/**
	 * The documents of the knowledge bases whose file name or title (as the type says)
	 * is the most similar to the text, best first, one per document, searched in the
	 * vector store of the default embedding model and of those of the knowledge bases.
	 */
	public List<FoundDocument> search(String text, EmbedType type, List<String> knowledgeBaseCodes, int topK,
			double similarityThreshold) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin search(type:" + type + " knowledge bases:" + knowledgeBaseCodes + " topK:" + topK
					+ " threshold:" + similarityThreshold + ")");
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("search(...) text:" + text);
		}
		if (text == null || text.isBlank() || knowledgeBaseCodes == null || knowledgeBaseCodes.isEmpty()) {
			return List.of();
		}
		final SemanticSearchMetaDataFilter filter = new SemanticSearchMetaDataFilter();
		filter.setKnowledgeBasesCodes(knowledgeBaseCodes);
		filter.setEmbedType(type);
		final IGSecurityService security = securityService.getObject();
		if (security.getPlatformContentAccessPolicy() == ContentAccessPolicy.ACL_BASED
				&& !security.isCurrentUserAdmin()) {
			filter.setAclAliases(security.getCurrentAclGrantedAccessor(AclGrantType.READ).getAllOwnedAclAliases());
		}
		final SearchRequest request = SearchRequest.builder().query(text).topK(topK)
				.similarityThreshold(similarityThreshold).filterExpression(filter.build()).build();
		final Map<Long, FoundDocument> best = new LinkedHashMap<>();
		for (IGConfigurableEmbeddingModel model : models(knowledgeBaseCodes)) {
			final List<Document> hits;
			try {
				hits = model.getVectorStore().similaritySearch(request);
			} catch (RuntimeException e) {
				LOGGER.warn("Cannot search the " + type + " vectors of the store of the embedding model "
						+ model.getCode() + ": " + e.getMessage());
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("identity search failure", e);
				}
				continue;
			}
			for (Document hit : hits != null ? hits : List.<Document>of()) {
				final Long uniqueId = uniqueId(hit.getMetadata().get(DocumentMetaInfos.GEBO_UNIQUE_ID));
				if (uniqueId == null) {
					continue;
				}
				final double score = hit.getScore() != null ? hit.getScore() : 0.0;
				final FoundDocument found = best.get(uniqueId);
				if (found == null || found.score() < score) {
					best.put(uniqueId, new FoundDocument(uniqueId, hit.getText(), score, model.getCode()));
				}
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("search(...) embedding model " + model.getCode() + ": " + (hits != null ? hits.size() : 0)
						+ " hit(s)");
			}
		}
		final List<FoundDocument> found = new ArrayList<>(best.values());
		found.sort(Comparator.comparingDouble(FoundDocument::score).reversed());
		final List<FoundDocument> result = found.size() > topK ? found.subList(0, topK) : found;
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End search(...) " + result.size() + " document(s)");
		}
		return result;
	}

	/**
	 * The embedding models whose vector stores keep the knowledge bases' vectors: the
	 * default one first, then those of the knowledge bases.
	 */
	List<IGConfigurableEmbeddingModel> models(List<String> knowledgeBaseCodes) {
		final IGEmbeddingModelRuntimeConfigurationDao dao = embeddingModels.getObject();
		final List<IGConfigurableEmbeddingModel> models = new ArrayList<>();
		final IGConfigurableEmbeddingModel defaultModel = dao.defaultHandler();
		if (defaultModel != null && defaultModel.getVectorStore() != null) {
			models.add(defaultModel);
		}
		final List<GKnowledgeBase> knowledgeBases = visibilityService.getObject()
				.getVisibleKnowledgeBaseByCodes(knowledgeBaseCodes);
		for (GKnowledgeBase knowledgeBase : knowledgeBases != null ? knowledgeBases : List.<GKnowledgeBase>of()) {
			final List<GObjectRef> references = knowledgeBase.getEmbeddingModelReferences();
			for (GObjectRef reference : references != null ? references : List.<GObjectRef>of()) {
				final IGConfigurableEmbeddingModel model = dao.findByModelReference(reference);
				if (model != null && model.getVectorStore() != null && !models.contains(model)) {
					models.add(model);
				}
			}
		}
		return models;
	}

	private static Long uniqueId(Object value) {
		if (value instanceof Number number) {
			return number.longValue();
		}
		if (value != null) {
			try {
				return Long.valueOf(value.toString().trim());
			} catch (NumberFormatException e) {
				return null;
			}
		}
		return null;
	}
}
