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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.beans.factory.ObjectProvider;

import ai.gebo.acl.AclGrantType;
import ai.gebo.acl.ContentAccessPolicy;
import ai.gebo.acl.IAclGrantedAccessor;
import ai.gebo.core.contents.security.services.IGKnowledgebaseVisibilityService;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.vectorstores.IGExtendedVectorStore;
import ai.gebo.llms.agent.standardtools.KnowledgeBaseDocumentIdentitySearch.FoundDocument;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.model.EmbedType;
import ai.gebo.model.base.GObjectRef;
import ai.gebo.security.services.IGSecurityService;

/**
 * Pins the search of documents by their file name or title: on the vectors of that
 * type only, in the knowledge bases given, with the user's access rights, in the
 * stores of the default and the knowledge bases' embedding models, one hit per
 * document, the best first.
 */
class KnowledgeBaseDocumentIdentitySearchTest {

	private IGEmbeddingModelRuntimeConfigurationDao models;
	private IGKnowledgebaseVisibilityService visibility;
	private IGSecurityService security;
	private IGExtendedVectorStore defaultStore;

	@SuppressWarnings("unchecked")
	private static <T> ObjectProvider<T> provider(T value) {
		ObjectProvider<T> provider = mock(ObjectProvider.class);
		when(provider.getObject()).thenReturn(value);
		when(provider.getIfAvailable()).thenReturn(value);
		return provider;
	}

	@SuppressWarnings("unchecked")
	private static IGConfigurableEmbeddingModel model(String code, IGExtendedVectorStore store) {
		IGConfigurableEmbeddingModel model = mock(IGConfigurableEmbeddingModel.class);
		when(model.getCode()).thenReturn(code);
		when(model.getVectorStore()).thenReturn(store);
		return model;
	}

	private static Document hit(Object uniqueId, String text, double score) {
		Map<String, Object> metadata = new HashMap<>();
		metadata.put(DocumentMetaInfos.GEBO_UNIQUE_ID, uniqueId);
		return Document.builder().text(text).metadata(metadata).score(score).build();
	}

	@BeforeEach
	void setUp() {
		models = mock(IGEmbeddingModelRuntimeConfigurationDao.class);
		visibility = mock(IGKnowledgebaseVisibilityService.class);
		security = mock(IGSecurityService.class);
		defaultStore = mock(IGExtendedVectorStore.class);
		IGConfigurableEmbeddingModel defaultModel = model("default", defaultStore);
		when(models.defaultHandler()).thenReturn(defaultModel);
		when(security.getPlatformContentAccessPolicy()).thenReturn(ContentAccessPolicy.ACL_BASED);
		when(security.isCurrentUserAdmin()).thenReturn(false);
		IAclGrantedAccessor accessor = mock(IAclGrantedAccessor.class);
		when(accessor.getAllOwnedAclAliases()).thenReturn(List.of(3, 4));
		when(security.getCurrentAclGrantedAccessor(AclGrantType.READ)).thenReturn(accessor);
	}

	private KnowledgeBaseDocumentIdentitySearch search() {
		return new KnowledgeBaseDocumentIdentitySearch(provider(models), provider(visibility), provider(security));
	}

	@Test
	void theTitlesAreSearchedInTheKnowledgeBasesWithTheAccessRightsOneHitPerDocumentTheBestFirst() {
		IGExtendedVectorStore kbStore = mock(IGExtendedVectorStore.class);
		IGConfigurableEmbeddingModel kbModel = model("kb-model", kbStore);
		GObjectRef reference = new GObjectRef();
		GKnowledgeBase kb = new GKnowledgeBase();
		kb.setCode("kb1");
		kb.setEmbeddingModelReferences(List.of(reference));
		when(visibility.getVisibleKnowledgeBaseByCodes(List.of("kb1"))).thenReturn(List.of(kb));
		when(models.findByModelReference(reference)).thenReturn(kbModel);
		ArgumentCaptor<SearchRequest> request = ArgumentCaptor.forClass(SearchRequest.class);
		when(defaultStore.similaritySearch(request.capture()))
				.thenReturn(List.of(hit(7L, "The Secret Doctrine", 0.8), hit(5L, "Isis", 0.6), hit(7L, "Secret", 0.5)));
		when(kbStore.similaritySearch(any(SearchRequest.class)))
				.thenReturn(List.of(hit("7", "The Secret Doctrine", 0.9), hit("x", "no id", 0.99)));

		List<FoundDocument> found = search().search("secret doctrine", EmbedType.TITLE, List.of("kb1"), 5, 0.4);

		assertEquals(List.of(7L, 5L), found.stream().map(FoundDocument::uniqueId).toList());
		assertEquals(0.9, found.get(0).score());
		assertEquals("kb-model", found.get(0).embeddingModelCode());
		assertEquals("The Secret Doctrine", found.get(0).matched());
		String filter = request.getValue().getFilterExpression().toString();
		assertTrue(filter.contains(DocumentMetaInfos.EMBED_TYPE) && filter.contains("TITLE"), filter);
		assertTrue(filter.contains(DocumentMetaInfos.KNOWLEDGEBASE_CODE) && filter.contains("kb1"), filter);
		assertTrue(filter.contains(DocumentMetaInfos.GEBO_ACL_ALIASES), filter);
		assertEquals(5, request.getValue().getTopK());
		assertEquals(0.4, request.getValue().getSimilarityThreshold());
	}

	@Test
	void theBestOnesAreKeptAStoreThatFailsIsSkippedAndNoTextSearchesNothing() {
		when(defaultStore.similaritySearch(any(SearchRequest.class)))
				.thenReturn(List.of(hit(1L, "a", 0.9), hit(2L, "b", 0.8), hit(3L, "c", 0.7)));
		IGExtendedVectorStore failing = mock(IGExtendedVectorStore.class);
		when(failing.similaritySearch(any(SearchRequest.class))).thenThrow(new IllegalStateException("down"));
		GObjectRef reference = new GObjectRef();
		GKnowledgeBase kb = new GKnowledgeBase();
		kb.setEmbeddingModelReferences(List.of(reference));
		when(visibility.getVisibleKnowledgeBaseByCodes(List.of("kb1"))).thenReturn(List.of(kb));
		IGConfigurableEmbeddingModel failingModel = model("failing", failing);
		when(models.findByModelReference(reference)).thenReturn(failingModel);

		assertEquals(List.of(1L, 2L), search().search("a", EmbedType.FILE_NAME, List.of("kb1"), 2, 0.0).stream()
				.map(FoundDocument::uniqueId).toList());

		IGExtendedVectorStore untouched = mock(IGExtendedVectorStore.class);
		setUp();
		IGConfigurableEmbeddingModel untouchedModel = model("default", untouched);
		when(models.defaultHandler()).thenReturn(untouchedModel);
		assertTrue(search().search(" ", EmbedType.FILE_NAME, List.of("kb1"), 2, 0.0).isEmpty());
		assertTrue(search().search("a", EmbedType.FILE_NAME, List.of(), 2, 0.0).isEmpty());
		verifyNoInteractions(untouched);
	}
}
