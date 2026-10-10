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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.filter.Filter;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.vectorstores.EmbedTypeFilters;
import ai.gebo.llms.abstraction.layer.vectorstores.IGExtendedVectorStore;
import ai.gebo.llms.abstraction.layer.vectorstores.model.GVectorizedContent;
import ai.gebo.llms.abstraction.layer.vectorstores.repository.VectorizedContentRepository;
import ai.gebo.model.DocumentMetaInfos;

/**
 * Reads the chunks of a knowledge base document back from the vector store, the
 * one store every knowledge base has, in document order.
 *
 * <p>
 * In the standard workflow the documents cache splits the document into chunks
 * numbered by position ({@link DocumentMetaInfos#GEBO_CHUNK_POSITION}), each with
 * the document's metadata ({@link DocumentMetaInfos#CONTENT_CODE},
 * {@link DocumentMetaInfos#GEBO_UNIQUE_ID}); the vectorization stores them in the
 * vector store of each embedding model and tracks their ids, in that order, in the
 * document's {@link GVectorizedContent}. The chunks are found by the document's
 * uniqueId, else by its code (chunks vectorized before documents had a uniqueId,
 * or a store whose index cannot filter on it).
 * </p>
 */
@Component
public class KnowledgeBaseDocumentChunksReader {
	private static final Logger LOGGER = LoggerFactory.getLogger(KnowledgeBaseDocumentChunksReader.class);

	/** The chunks of a document, and the embedding model whose vector store they come from. */
	public record DocumentChunks(List<Document> chunks, String embeddingModelCode) {
		static final DocumentChunks NONE = new DocumentChunks(List.of(), null);
	}

	private final ObjectProvider<VectorizedContentRepository> vectorizedContents;
	private final ObjectProvider<IGEmbeddingModelRuntimeConfigurationDao> embeddingModels;

	public KnowledgeBaseDocumentChunksReader(ObjectProvider<VectorizedContentRepository> vectorizedContents,
			ObjectProvider<IGEmbeddingModelRuntimeConfigurationDao> embeddingModels) {
		this.vectorizedContents = vectorizedContents;
		this.embeddingModels = embeddingModels;
	}

	/**
	 * The chunks of the document in document order, from the vector store of the
	 * default embedding model when the document is vectorized there, else of the
	 * first other one; none when it is not vectorized (yet).
	 */
	public DocumentChunks read(GDocumentReference document) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin read(uniqueId:" + document.getUniqueId() + " code:" + document.getCode() + ")");
		}
		final IGEmbeddingModelRuntimeConfigurationDao models = embeddingModels.getObject();
		final IGConfigurableEmbeddingModel defaultModel = models.defaultHandler();
		final List<GVectorizedContent> vectorized = new ArrayList<>(
				vectorizedContents.getObject().findByIdDocReferenceCode(document.getCode()));
		vectorized.removeIf(v -> Boolean.TRUE.equals(v.getDeleted()) || v.getId() == null || v.getId().getVectorStoreId() == null
				|| v.getVectorsId() == null || v.getVectorsId().isEmpty());
		// the default embedding model first
		vectorized.sort(Comparator.comparing(
				(GVectorizedContent v) -> defaultModel == null || !v.getId().getVectorStoreId().equals(defaultModel.getCode())));
		for (GVectorizedContent entry : vectorized) {
			final String modelCode = entry.getId().getVectorStoreId();
			final IGConfigurableEmbeddingModel model = models.findByCode(modelCode);
			if (model == null || model.getVectorStore() == null) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("read(...) code:" + document.getCode() + " the embedding model " + modelCode
							+ " is not configured, skipped");
				}
				continue;
			}
			final List<Document> chunks = chunks(model.getVectorStore(), document, entry.getVectorsId());
			if (!chunks.isEmpty()) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("End read(...) code:" + document.getCode() + " " + chunks.size() + " chunk(s) of "
							+ entry.getVectorsId().size() + " vectorized, from the vector store of " + modelCode);
				}
				return new DocumentChunks(chunks, modelCode);
			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End read(...) code:" + document.getCode() + " no chunks in " + vectorized.size()
					+ " vectorization(s)");
		}
		return DocumentChunks.NONE;
	}

	/**
	 * The document's chunks in one vector store, by uniqueId else by code, ordered:
	 * the vectors of its contents, not those of its file name and title.
	 */
	List<Document> chunks(IGExtendedVectorStore store, GDocumentReference document, List<String> vectorsId) {
		final FilterExpressionBuilder filters = new FilterExpressionBuilder();
		List<Document> found = List.of();
		if (document.getUniqueId() != null) {
			try {
				found = search(store, document, vectorsId.size(),
						filters.and(filters.eq(DocumentMetaInfos.GEBO_UNIQUE_ID, document.getUniqueId()),
								EmbedTypeFilters.contentsOnly()).build());
			} catch (RuntimeException e) {
				// an index created before the uniqueId was a filterable attribute
				LOGGER.warn("Cannot filter the vector store by uniqueId " + document.getUniqueId()
						+ ", reading by code " + document.getCode() + ": " + e.getMessage());
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("uniqueId filter failure", e);
				}
			}
		}
		if (found.isEmpty()) {
			found = search(store, document, vectorsId.size(),
					filters.and(filters.eq(DocumentMetaInfos.CONTENT_CODE, document.getCode()),
							EmbedTypeFilters.contentsOnly()).build());
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("chunks(...) code:" + document.getCode() + " by code: " + found.size() + " chunk(s)");
			}
		} else if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("chunks(...) uniqueId:" + document.getUniqueId() + " by uniqueId: " + found.size()
					+ " chunk(s)");
		}
		return ordered(found, vectorsId);
	}

	/**
	 * Every chunk passing the filter: the query only serves the stores that need
	 * one to embed, the threshold accepts any similarity, topK is as many chunks as
	 * the document was vectorized into.
	 */
	List<Document> search(IGExtendedVectorStore store, GDocumentReference document, int chunksCount,
			Filter.Expression filter) {
		final String query = document.getName() != null && !document.getName().isBlank() ? document.getName()
				: document.getCode();
		final List<Document> found = store.similaritySearch(SearchRequest.builder().query(query)
				.topK(Math.max(1, chunksCount)).similarityThresholdAll().filterExpression(filter).build());
		return found != null ? found : List.of();
	}

	/**
	 * In document order: by chunk position, else by the order the vectorization
	 * recorded their ids in, else as found.
	 */
	static List<Document> ordered(List<Document> chunks, List<String> vectorsId) {
		final Map<String, Integer> recorded = new HashMap<>();
		for (int i = 0; vectorsId != null && i < vectorsId.size(); i++) {
			recorded.putIfAbsent(vectorsId.get(i), i);
		}
		final List<Document> ordered = new ArrayList<>(chunks);
		ordered.sort(Comparator.comparingDouble((Document chunk) -> position(chunk))
				.thenComparingInt(chunk -> recorded.getOrDefault(chunk.getId(), Integer.MAX_VALUE)));
		return ordered;
	}

	/** The chunk's position in the document, last when unknown. */
	static double position(Document chunk) {
		final Object value = chunk.getMetadata() != null ? chunk.getMetadata().get(DocumentMetaInfos.GEBO_CHUNK_POSITION)
				: null;
		if (value instanceof Number number) {
			return number.doubleValue();
		}
		if (value != null) {
			try {
				return Double.parseDouble(value.toString().trim());
			} catch (NumberFormatException e) {
				// not a position
			}
		}
		return Double.MAX_VALUE;
	}
}
