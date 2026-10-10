/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.standardtools;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.filter.Filter;
import org.springframework.beans.factory.ObjectProvider;

import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.vectorstores.EmbedTypeFilters;
import ai.gebo.llms.abstraction.layer.vectorstores.IGExtendedVectorStore;
import ai.gebo.llms.abstraction.layer.vectorstores.model.GVectorizedContent;
import ai.gebo.llms.abstraction.layer.vectorstores.model.GVectorizedContent.GVectorizedContentId;
import ai.gebo.llms.abstraction.layer.vectorstores.repository.VectorizedContentRepository;
import ai.gebo.llms.agent.standardtools.KnowledgeBaseDocumentChunksReader.DocumentChunks;
import ai.gebo.model.DocumentMetaInfos;

/**
 * Pins the reading of a document's chunks from the vector store: by its uniqueId,
 * by its code when that finds nothing or the store cannot filter on it, from the
 * default embedding model first, in document order.
 */
class KnowledgeBaseDocumentChunksReaderTest {

	private VectorizedContentRepository vectorized;
	private IGEmbeddingModelRuntimeConfigurationDao models;
	private IGExtendedVectorStore defaultStore;
	private IGExtendedVectorStore otherStore;
	private KnowledgeBaseDocumentChunksReader reader;

	@SuppressWarnings("unchecked")
	private static <T> ObjectProvider<T> provider(T value) {
		ObjectProvider<T> provider = mock(ObjectProvider.class);
		when(provider.getObject()).thenReturn(value);
		when(provider.getIfAvailable()).thenReturn(value);
		return provider;
	}

	private static IGConfigurableEmbeddingModel model(String code, IGExtendedVectorStore store) {
		IGConfigurableEmbeddingModel model = mock(IGConfigurableEmbeddingModel.class);
		when(model.getCode()).thenReturn(code);
		when(model.getVectorStore()).thenReturn(store);
		return model;
	}

	private static GVectorizedContent vectorization(String modelCode, List<String> ids, Boolean deleted) {
		GVectorizedContent content = new GVectorizedContent();
		content.setId(new GVectorizedContentId());
		content.getId().setDocReferenceCode("doc-5");
		content.getId().setVectorStoreId(modelCode);
		content.setVectorsId(ids);
		content.setDeleted(deleted);
		return content;
	}

	private static Document chunk(String id, String text, Object position) {
		return Document.builder().id(id).text(text)
				.metadata(position != null ? Map.of(DocumentMetaInfos.GEBO_CHUNK_POSITION, position) : Map.of()).build();
	}

	private static GDocumentReference document(Long uniqueId) {
		GDocumentReference document = new GDocumentReference();
		document.setUniqueId(uniqueId);
		document.setCode("doc-5");
		document.setName("a.pdf");
		return document;
	}

	/**
	 * The key the document is filtered by, after checking the filter keeps the
	 * vectors of the contents only.
	 */
	private static String filteredKey(SearchRequest request) {
		Filter.Expression expression = request.getFilterExpression();
		assertEquals(Filter.ExpressionType.AND, expression.type());
		assertEquals(EmbedTypeFilters.contentsOnly().build(), expression.right());
		return ((Filter.Key) ((Filter.Expression) expression.left()).left()).key();
	}

	@BeforeEach
	void setUp() {
		vectorized = mock(VectorizedContentRepository.class);
		models = mock(IGEmbeddingModelRuntimeConfigurationDao.class);
		defaultStore = mock(IGExtendedVectorStore.class);
		otherStore = mock(IGExtendedVectorStore.class);
		IGConfigurableEmbeddingModel defaultModel = model("embedding-default", defaultStore);
		IGConfigurableEmbeddingModel otherModel = model("embedding-other", otherStore);
		when(models.defaultHandler()).thenReturn(defaultModel);
		when(models.findByCode("embedding-default")).thenReturn(defaultModel);
		when(models.findByCode("embedding-other")).thenReturn(otherModel);
		reader = new KnowledgeBaseDocumentChunksReader(provider(vectorized), provider(models));
	}

	@Test
	void theChunksAreFoundByUniqueIdInTheDefaultModelsStoreInDocumentOrder() {
		when(vectorized.findByIdDocReferenceCode("doc-5")).thenReturn(List.of(
				vectorization("embedding-other", List.of("x"), false),
				vectorization("embedding-default", List.of("c1", "c2", "c3"), null)));
		// the stores give positions as numbers or as text
		when(defaultStore.similaritySearch(any(SearchRequest.class)))
				.thenReturn(List.of(chunk("c3", "third", "3"), chunk("c1", "first", 1L), chunk("c2", "second", 2.0)));

		DocumentChunks read = reader.read(document(5L));

		assertEquals("embedding-default", read.embeddingModelCode());
		assertEquals(List.of("first", "second", "third"), read.chunks().stream().map(Document::getText).toList());
		ArgumentCaptor<SearchRequest> request = ArgumentCaptor.forClass(SearchRequest.class);
		verify(defaultStore).similaritySearch(request.capture());
		assertEquals(DocumentMetaInfos.GEBO_UNIQUE_ID, filteredKey(request.getValue()));
		assertEquals(3, request.getValue().getTopK());
		assertEquals(0.0, request.getValue().getSimilarityThreshold());
		verify(otherStore, never()).similaritySearch(any(SearchRequest.class));
	}

	@Test
	void chunksVectorizedBeforeTheUniqueIdAreFoundByCode() {
		when(vectorized.findByIdDocReferenceCode("doc-5"))
				.thenReturn(List.of(vectorization("embedding-default", List.of("c1", "c2"), false)));
		// no position: the order the vectorization recorded their ids in
		when(defaultStore.similaritySearch(any(SearchRequest.class))).thenReturn(List.of())
				.thenReturn(List.of(chunk("c2", "second", null), chunk("c1", "first", null)));

		DocumentChunks read = reader.read(document(5L));

		assertEquals(List.of("first", "second"), read.chunks().stream().map(Document::getText).toList());
		ArgumentCaptor<SearchRequest> requests = ArgumentCaptor.forClass(SearchRequest.class);
		verify(defaultStore, times(2)).similaritySearch(requests.capture());
		assertEquals(DocumentMetaInfos.GEBO_UNIQUE_ID, filteredKey(requests.getAllValues().get(0)));
		assertEquals(DocumentMetaInfos.CONTENT_CODE, filteredKey(requests.getAllValues().get(1)));
	}

	@Test
	void aStoreThatCannotFilterByUniqueIdIsReadByCode() {
		when(vectorized.findByIdDocReferenceCode("doc-5"))
				.thenReturn(List.of(vectorization("embedding-default", List.of("c1"), false)));
		when(defaultStore.similaritySearch(any(SearchRequest.class)))
				.thenThrow(new IllegalArgumentException("Unknown field GEBO_UNIQUE_ID"))
				.thenReturn(List.of(chunk("c1", "only", 1L)));

		DocumentChunks read = reader.read(document(5L));

		assertEquals(List.of("only"), read.chunks().stream().map(Document::getText).toList());
	}

	@Test
	void aDocumentWithoutUniqueIdIsReadByCodeOnly() {
		when(vectorized.findByIdDocReferenceCode("doc-5"))
				.thenReturn(List.of(vectorization("embedding-default", List.of("c1"), false)));
		when(defaultStore.similaritySearch(any(SearchRequest.class))).thenReturn(List.of(chunk("c1", "only", 1L)));

		reader.read(document(null));

		ArgumentCaptor<SearchRequest> request = ArgumentCaptor.forClass(SearchRequest.class);
		verify(defaultStore).similaritySearch(request.capture());
		assertEquals(DocumentMetaInfos.CONTENT_CODE, filteredKey(request.getValue()));
	}

	@Test
	void deletedEmptyOrUnconfiguredVectorizationsAreSkipped() {
		when(models.findByCode("embedding-gone")).thenReturn(null);
		when(vectorized.findByIdDocReferenceCode("doc-5")).thenReturn(List.of(
				vectorization("embedding-default", List.of("c1"), true),
				vectorization("embedding-other", List.of(), false),
				vectorization("embedding-gone", List.of("c9"), false)));

		DocumentChunks read = reader.read(document(5L));

		assertTrue(read.chunks().isEmpty());
		assertNull(read.embeddingModelCode());
		verify(defaultStore, never()).similaritySearch(any(SearchRequest.class));
		verify(otherStore, never()).similaritySearch(any(SearchRequest.class));
	}

	@Test
	void anotherModelsStoreIsReadWhenTheDefaultHasNothing() {
		when(vectorized.findByIdDocReferenceCode("doc-5")).thenReturn(List.of(
				vectorization("embedding-default", List.of("c1"), false),
				vectorization("embedding-other", List.of("o1"), false)));
		when(defaultStore.similaritySearch(any(SearchRequest.class))).thenReturn(List.of());
		when(otherStore.similaritySearch(any(SearchRequest.class))).thenReturn(List.of(chunk("o1", "from other", 1L)));

		DocumentChunks read = reader.read(document(5L));

		assertEquals("embedding-other", read.embeddingModelCode());
		assertEquals(List.of("from other"), read.chunks().stream().map(Document::getText).toList());
	}
}
