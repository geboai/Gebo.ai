/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.abstraction.layer.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;

import ai.gebo.application.messaging.model.DataTransformationInfo;
import ai.gebo.application.messaging.model.GDataFlowMetaInfos;
import ai.gebo.application.messaging.model.GStandardDataFlowEndpoints;
import ai.gebo.architecture.graphrag.services.IKnowledgeGraphSearchService;
import ai.gebo.architecture.rag.support.layer.services.IGFullTextSearchDocumentsCachedDao;
import ai.gebo.architecture.search.service.ISearchService;
import ai.gebo.architecture.search.service.ISearchServiceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.model.ChatModelsUses;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelConfig;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGRankerModelRuntimeConfigurationDao;
import ai.gebo.llms.chat.abstraction.layer.model.GChatProfileConfiguration;
import ai.gebo.llms.chat.abstraction.layer.repository.ChatProfilesRepository;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatProfileChatModel;
import ai.gebo.llms.chat.abstraction.layer.services.IGRuntimeChatProfileChatModelDao;

/**
 * Pins the read side the chat pipeline puts in the register: a profile's query, and
 * the deep search, read the knowledge stores the knowledge-base search reads on the
 * installation (the vector store always, the full-text index with OpenSearch, the
 * knowledge graph with Neo4j), whatever the profile's own flags say; the deep search
 * also reaches every enabled search service, plans with the utility model and answers
 * with the chat's own model.
 */
class GStandardChatPipelineDataFlowComponentTest {

	private ChatProfilesRepository profiles;
	private IGRuntimeChatProfileChatModelDao profileModels;
	private IGChatModelRuntimeConfigurationDao chatModels;
	private IGEmbeddingModelRuntimeConfigurationDao embeddingModels;
	private IGRankerModelRuntimeConfigurationDao rankerModels;
	private ISearchServiceRepositoryPattern searchServices;
	private IGFullTextSearchDocumentsCachedDao fullTextSearch;
	private IKnowledgeGraphSearchService knowledgeGraphSearch;

	@SuppressWarnings("unchecked")
	private static <T> ObjectProvider<T> provider(T value) {
		ObjectProvider<T> provider = mock(ObjectProvider.class);
		when(provider.getIfAvailable()).thenReturn(value);
		return provider;
	}

	private static IGConfigurableChatModel chatModel(String code, String baseUrl) {
		GBaseChatModelConfig config = new GBaseChatModelConfig();
		config.setModelTypeCode("openai");
		config.setBaseUrl(baseUrl);
		IGConfigurableChatModel model = mock(IGConfigurableChatModel.class);
		when(model.getCode()).thenReturn(code);
		when(model.getConfig()).thenReturn(config);
		return model;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@BeforeEach
	void setUp() throws Exception {
		profiles = mock(ChatProfilesRepository.class);
		profileModels = mock(IGRuntimeChatProfileChatModelDao.class);
		chatModels = mock(IGChatModelRuntimeConfigurationDao.class);
		embeddingModels = mock(IGEmbeddingModelRuntimeConfigurationDao.class);
		rankerModels = mock(IGRankerModelRuntimeConfigurationDao.class);
		searchServices = mock(ISearchServiceRepositoryPattern.class);

		GChatProfileConfiguration profile = new GChatProfileConfiguration();
		profile.setCode("support");
		// the lexical leg does not depend on this flag: the search reads no profile flag for it
		profile.setUseAlsoKeywordSearch(false);
		when(profiles.findAll()).thenReturn(List.of(profile));

		IGConfigurableChatModel responder = chatModel("responder", "https://api.openai.com/v1");
		IGChatProfileChatModel profileModel = mock(IGChatProfileChatModel.class);
		when(profileModel.getChatModel()).thenReturn(responder);
		when(profileModels.getChatModel(any())).thenReturn(profileModel);
		IGConfigurableChatModel utility = chatModel("utility", "http://localhost:11434");
		when(chatModels.findByUsesOrGetDefault(ChatModelsUses.INTERNAL_SERVICES)).thenReturn(utility);

		GBaseEmbeddingModelConfig embeddingConfig = mock(GBaseEmbeddingModelConfig.class);
		when(embeddingConfig.getModelTypeCode()).thenReturn("ollama");
		when(embeddingConfig.getBaseUrl()).thenReturn("http://localhost:11434");
		IGConfigurableEmbeddingModel embedding = mock(IGConfigurableEmbeddingModel.class);
		when(embedding.getCode()).thenReturn("nomic");
		when(embedding.getConfig()).thenReturn(embeddingConfig);
		when(embeddingModels.defaultHandler()).thenReturn(embedding);
		when(rankerModels.defaultHandler()).thenReturn(null);

		ISearchService brave = mock(ISearchService.class);
		when(brave.getId()).thenReturn("brave");
		when(brave.getProductId()).thenReturn("brave-search");
		when(brave.isEnabled()).thenReturn(true);
		when(searchServices.getImplementations()).thenReturn((List) List.of(brave));

		// a semantic only installation unless a test deploys the optional legs
		fullTextSearch = null;
		knowledgeGraphSearch = null;
	}

	private GDataFlowMetaInfos flow() {
		return new GStandardChatPipelineDataFlowComponent(provider(profiles), provider(profileModels),
				provider(chatModels), provider(embeddingModels), provider(rankerModels), provider(searchServices),
				provider(fullTextSearch), provider(knowledgeGraphSearch)).getDataFlowMetaInfos();
	}

	private static List<String> destinationsFrom(GDataFlowMetaInfos flow, String endpointId) {
		String source = flow.qualifiedId(endpointId);
		return flow.getTransformations().stream().filter(x -> source.equals(x.getDataSourceId()))
				.map(DataTransformationInfo::getDataDestinationId).toList();
	}

	@Test
	void aSemanticOnlyInstallationReadsTheVectorStoreOnly() {
		GDataFlowMetaInfos flow = flow();

		assertNotNull(flow);
		List<String> fromQuery = destinationsFrom(flow, "query-support");
		List<String> fromDeepSearch = destinationsFrom(flow, "deep-search-query");
		for (List<String> reached : List.of(fromQuery, fromDeepSearch)) {
			assertFalse(reached.contains(GStandardDataFlowEndpoints.fullTextIndexRef()), String.valueOf(reached));
			assertFalse(reached.contains(GStandardDataFlowEndpoints.knowledgeGraphRef()), String.valueOf(reached));
		}
		// the profile's semantic leg goes through its embedding model
		assertTrue(destinationsFrom(flow, "embedding-model-nomic").contains(GStandardDataFlowEndpoints.vectorStoreRef()));
		assertTrue(fromDeepSearch.contains(GStandardDataFlowEndpoints.vectorStoreRef()), String.valueOf(fromDeepSearch));
	}

	@Test
	void everyDeployedLegIsReadWhateverTheProfileFlags() {
		fullTextSearch = mock(IGFullTextSearchDocumentsCachedDao.class);
		knowledgeGraphSearch = mock(IKnowledgeGraphSearchService.class);

		GDataFlowMetaInfos flow = flow();

		for (String reader : List.of("query-support", "deep-search-query")) {
			List<String> reached = destinationsFrom(flow, reader);
			assertTrue(reached.contains(GStandardDataFlowEndpoints.fullTextIndexRef()), reader + " " + reached);
			assertTrue(reached.contains(GStandardDataFlowEndpoints.knowledgeGraphRef()), reader + " " + reached);
		}
	}

	@Test
	void deepSearchPlansWithTheUtilityModelAnswersWithTheChatsOwnAndReachesTheSearchServices() {
		GDataFlowMetaInfos flow = flow();

		List<String> reached = destinationsFrom(flow, "deep-search-query");
		assertTrue(reached.contains(flow.qualifiedId("service-model-utility")), String.valueOf(reached));
		assertTrue(reached.contains(flow.qualifiedId("chat-model-responder")), String.valueOf(reached));
		assertTrue(reached.contains(flow.qualifiedId("embedding-model-nomic")), String.valueOf(reached));
		assertTrue(reached.contains(flow.qualifiedId("web-search-brave")), String.valueOf(reached));
		// the documents the search services return are chunked for the request
		assertTrue(reached.contains(GStandardDataFlowEndpoints.chunkCacheRef()), String.valueOf(reached));
		// no endpoint of the chat pipeline is personal data by itself
		assertTrue(flow.getDataEndpoints().stream().noneMatch(x -> x.isPersonalData()));
	}

	@Test
	void withoutSearchServicesDeepSearchReadsTheKnowledgeBasesOnly() {
		when(searchServices.getImplementations()).thenReturn(List.of());

		GDataFlowMetaInfos flow = flow();

		List<String> reached = destinationsFrom(flow, "deep-search-query");
		assertTrue(reached.contains(GStandardDataFlowEndpoints.vectorStoreRef()), String.valueOf(reached));
		assertFalse(reached.contains(GStandardDataFlowEndpoints.chunkCacheRef()), String.valueOf(reached));
		assertEquals(0, flow.getDataEndpoints().stream().filter(x -> x.getId().startsWith("web-search-")).count());
	}
}
