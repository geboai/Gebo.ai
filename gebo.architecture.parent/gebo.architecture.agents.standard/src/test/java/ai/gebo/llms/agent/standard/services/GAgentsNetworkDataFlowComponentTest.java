/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.standard.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.definition.ToolDefinition;
import org.springframework.beans.factory.ObjectProvider;

import ai.gebo.application.messaging.model.ComponentMetaInfo;
import ai.gebo.application.messaging.model.DataEndpoint;
import ai.gebo.application.messaging.model.DataEndpointLocality;
import ai.gebo.application.messaging.model.DataFlowPersonalDataPropagation;
import ai.gebo.application.messaging.model.DataTransformationInfo;
import ai.gebo.application.messaging.model.DataTransformationMetaInfo;
import ai.gebo.application.messaging.model.GDataFlowMetaInfos;
import ai.gebo.application.messaging.model.GDataFlowReport;
import ai.gebo.application.messaging.model.GModuleMetaInfo;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.application.messaging.model.MetaEndpointType;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.services.IAgentsNetworkDao;
import ai.gebo.architecture.ai.model.ToolDataFlowTarget;
import ai.gebo.architecture.ai.model.ToolDataFlowTarget.Kind;
import ai.gebo.architecture.ai.service.IGToolCallbackSource;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.search.service.AbstractWebSearchServiceImpl;
import ai.gebo.architecture.search.service.ISearchService;
import ai.gebo.architecture.search.service.ISearchServiceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelConfig;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGRankerModelRuntimeConfigurationDao;
import ai.gebo.llms.agent.standard.config.AgenticLoopAgentsInitialization;
import ai.gebo.llms.agent.standardtools.InternalKnowledgeBaseSearchToolSource;
import ai.gebo.llms.chat.abstraction.layer.services.impl.DataFlowEndpoints;
import ai.gebo.model.base.GeboComponentInfo;
import ai.gebo.security.services.IGeboSystemUserService;

/**
 * Pins the register entries of the agents networks: the single agent networks report
 * their chat model and what their mounted tools reach (the free chat one without the
 * knowledge bases), the other networks keep the finders' fan-out. Personal data
 * reach them only from a data source an administrator flags.
 */
class GAgentsNetworkDataFlowComponentTest {

	private static final String AGENTIC = AgenticLoopAgentsInitialization.AGENTIC_LOOP_AGENTS_NETWORK;
	private static final String FREE_CHAT = AgenticLoopAgentsInitialization.AGENTIC_LOOP_PURE_CHAT_AGENTS_NETWORK;

	private IAgentsNetworkDao networksDao;
	private IGToolCallbackSourceRepositoryPattern toolSources;
	private IGChatModelRuntimeConfigurationDao chatModelsDao;
	private IGEmbeddingModelRuntimeConfigurationDao embeddingModelsDao;
	private IGRankerModelRuntimeConfigurationDao rankerModelsDao;
	private ISearchServiceRepositoryPattern searchServices;

	@SuppressWarnings("unchecked")
	private static <T> ObjectProvider<T> provider(T value) {
		ObjectProvider<T> provider = mock(ObjectProvider.class);
		when(provider.getIfAvailable()).thenReturn(value);
		return provider;
	}

	private static GAgentsNetwork network(String code) {
		GAgentsNetwork network = new GAgentsNetwork();
		network.setCode(code);
		return network;
	}

	private static ToolCallback tool(String name) {
		ToolCallback callback = mock(ToolCallback.class);
		when(callback.getToolDefinition())
				.thenReturn(ToolDefinition.builder().name(name).description(name).inputSchema("{}").build());
		return callback;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@BeforeEach
	void setUp() throws Exception {
		networksDao = mock(IAgentsNetworkDao.class);
		toolSources = mock(IGToolCallbackSourceRepositoryPattern.class);
		chatModelsDao = mock(IGChatModelRuntimeConfigurationDao.class);
		embeddingModelsDao = mock(IGEmbeddingModelRuntimeConfigurationDao.class);
		rankerModelsDao = mock(IGRankerModelRuntimeConfigurationDao.class);
		searchServices = mock(ISearchServiceRepositoryPattern.class);

		GBaseChatModelConfig chatConfig = new GBaseChatModelConfig();
		chatConfig.setModelTypeCode("openai");
		chatConfig.setBaseUrl("https://api.openai.com/v1");
		IGConfigurableChatModel chatModel = mock(IGConfigurableChatModel.class);
		when(chatModel.getCode()).thenReturn("gpt");
		when(chatModel.getConfig()).thenReturn(chatConfig);
		when(chatModelsDao.defaultHandler()).thenReturn(chatModel);

		GBaseEmbeddingModelConfig embeddingConfig = mock(GBaseEmbeddingModelConfig.class);
		when(embeddingConfig.getModelTypeCode()).thenReturn("ollama");
		when(embeddingConfig.getBaseUrl()).thenReturn("http://localhost:11434");
		IGConfigurableEmbeddingModel embeddingModel = mock(IGConfigurableEmbeddingModel.class);
		when(embeddingModel.getCode()).thenReturn("nomic");
		when(embeddingModel.getConfig()).thenReturn(embeddingConfig);
		when(embeddingModelsDao.defaultHandler()).thenReturn(embeddingModel);
		// no ranker configured
		when(rankerModelsDao.defaultHandler()).thenReturn(null);

		AbstractWebSearchServiceImpl brave = mock(AbstractWebSearchServiceImpl.class);
		when(brave.getId()).thenReturn("brave");
		when(brave.getProductId()).thenReturn("brave-search");
		when(brave.isEnabled()).thenReturn(true);
		when(searchServices.getImplementations()).thenReturn((List) List.of(brave));

		IGToolCallbackSource knowledgeBase = mock(IGToolCallbackSource.class);
		when(knowledgeBase.getId()).thenReturn("kb");
		List<ToolCallback> knowledgeBaseTools = List.of(tool(InternalKnowledgeBaseSearchToolSource.SEARCH_KNOWLEDGE_BASE_TOOL));
		when(knowledgeBase.getToolCallbacks()).thenReturn(knowledgeBaseTools);
		when(knowledgeBase.getDataFlowTargets(InternalKnowledgeBaseSearchToolSource.SEARCH_KNOWLEDGE_BASE_TOOL))
				.thenReturn(List.of(ToolDataFlowTarget.of(Kind.EMBEDDING_MODEL, "query embedded"),
						ToolDataFlowTarget.of(Kind.KNOWLEDGE_BASE_VECTOR_STORE, "semantic search"),
						ToolDataFlowTarget.of(Kind.RANKER_MODEL, "contents ranked")));
		IGToolCallbackSource others = mock(IGToolCallbackSource.class);
		when(others.getId()).thenReturn("others");
		List<ToolCallback> otherTools = List.of(tool("searchWeb"), tool("readUrl"), tool("getActualUser"),
				tool("github_search"), tool("now"));
		when(others.getToolCallbacks()).thenReturn(otherTools);
		when(others.getDataFlowTargets("searchWeb"))
				.thenReturn(List.of(ToolDataFlowTarget.searchService("brave", "web searched")));
		when(others.getDataFlowTargets("readUrl"))
				.thenReturn(List.of(ToolDataFlowTarget.of(Kind.INTERNET, "page read")));
		when(others.getDataFlowTargets("getActualUser"))
				.thenReturn(List.of(ToolDataFlowTarget.platformData("Platform users and groups", "user read")));
		when(others.getDataFlowTargets("github_search")).thenReturn(List.of(new ToolDataFlowTarget(Kind.MCP_SERVER,
				"github", "MCP server github", "stdio:npx", null, "arguments sent")));
		when(others.getDataFlowTargets("now")).thenReturn(List.of());
		when(toolSources.getImplementations()).thenReturn(List.of(knowledgeBase, others));
	}

	private GAgentsNetworkDataFlowComponent component() {
		return new GAgentsNetworkDataFlowComponent(provider(networksDao), provider(searchServices),
				provider((IGeboSystemUserService) null), provider(toolSources), provider(chatModelsDao),
				provider(embeddingModelsDao), provider(rankerModelsDao));
	}

	private static DataEndpoint endpoint(GDataFlowMetaInfos flow, String id) {
		return flow.getDataEndpoints().stream().filter(x -> id.equals(x.getId())).findFirst().orElse(null);
	}

	private static List<String> destinationsFrom(GDataFlowMetaInfos flow, String networkCode) {
		String query = flow.qualifiedId("network-query-" + networkCode);
		return flow.getTransformations().stream().filter(x -> query.equals(x.getDataSourceId()))
				.map(DataTransformationInfo::getDataDestinationId).toList();
	}

	@Test
	void singleAgentNetworkReportsItsModelAndWhatItsToolsReach() {
		when(networksDao.getConfigurations()).thenReturn(List.of(network(AGENTIC)));

		GDataFlowMetaInfos flow = component().getDataFlowMetaInfos();

		assertNotNull(flow);
		List<String> reached = destinationsFrom(flow, AGENTIC);
		assertEquals(List.of(flow.qualifiedId("agent-model-gpt"), flow.qualifiedId("embedding-model-nomic"),
				DataFlowEndpoints.vectorStoreRef(), flow.qualifiedId("web-search-brave"),
				flow.qualifiedId("internet-pages"), flow.qualifiedId("platform-data-platform-users-and-groups"),
				flow.qualifiedId("mcp-server-github")), reached);
		// no finders' fan-out: the full-text index is reached by no mounted tool here
		assertFalse(reached.contains(DataFlowEndpoints.fullTextIndexRef()));
		// no ranker configured: no ranker endpoint, no link
		assertTrue(flow.getDataEndpoints().stream().noneMatch(x -> x.getId().startsWith("ranker-model-")));

		DataEndpoint internet = endpoint(flow, "internet-pages");
		assertEquals(DataEndpointLocality.EXTERNAL_PROVIDER, internet.getLocality());
		DataEndpoint users = endpoint(flow, "platform-data-platform-users-and-groups");
		// a tool marks no personal data: they come only from the data sources flagged so
		assertTrue(flow.getDataEndpoints().stream().noneMatch(DataEndpoint::isPersonalData));
		assertEquals(DataEndpointLocality.LOCAL_DEPLOYMENT, users.getLocality());
		DataEndpoint mcp = endpoint(flow, "mcp-server-github");
		assertEquals(DataEndpointLocality.LOCAL_DEPLOYMENT, mcp.getLocality());
		assertEquals("stdio:npx", mcp.getEndpoint());
		DataEndpoint embedding = endpoint(flow, "embedding-model-nomic");
		assertEquals(DataEndpointLocality.LOCAL_DEPLOYMENT, embedding.getLocality());

		DataTransformationInfo kbLink = flow.getTransformations().stream()
				.filter(x -> DataFlowEndpoints.vectorStoreRef().equals(x.getDataDestinationId())).findFirst()
				.orElseThrow();
		assertEquals("Semantic search of the knowledge bases (tools: "
				+ InternalKnowledgeBaseSearchToolSource.SEARCH_KNOWLEDGE_BASE_TOOL + ")", kbLink.getDescription());
	}

	@Test
	void freeChatNetworkReachesNoKnowledgeBase() {
		when(networksDao.getConfigurations()).thenReturn(List.of(network(FREE_CHAT)));

		GDataFlowMetaInfos flow = component().getDataFlowMetaInfos();

		List<String> reached = destinationsFrom(flow, FREE_CHAT);
		assertTrue(reached.contains(flow.qualifiedId("agent-model-gpt")), String.valueOf(reached));
		assertTrue(reached.contains(flow.qualifiedId("web-search-brave")), String.valueOf(reached));
		assertFalse(reached.contains(DataFlowEndpoints.vectorStoreRef()), String.valueOf(reached));
		// the embedding model was reached only through the knowledge base search
		assertNull(endpoint(flow, "embedding-model-nomic"));
	}

	@Test
	void multiAgentNetworksKeepTheFindersFanOut() {
		when(networksDao.getConfigurations()).thenReturn(List.of(network("MY_NETWORK")));

		GDataFlowMetaInfos flow = component().getDataFlowMetaInfos();

		assertEquals(List.of(DataFlowEndpoints.vectorStoreRef(), DataFlowEndpoints.fullTextIndexRef(),
				flow.qualifiedId("web-search-brave")), destinationsFrom(flow, "MY_NETWORK"));
		assertNull(endpoint(flow, "agent-model-gpt"));
	}

	@Test
	void toolTargetsAreReadOnceAndAToolNameIsOwnedByItsFirstSource() {
		IGToolCallbackSource shadow = mock(IGToolCallbackSource.class);
		List<ToolCallback> shadowTools = List.of(tool("readUrl"));
		when(shadow.getToolCallbacks()).thenReturn(shadowTools);
		when(shadow.getDataFlowTargets("readUrl"))
				.thenReturn(List.of(ToolDataFlowTarget.platformData("Shadow", "shadow")));
		IGToolCallbackSource failing = mock(IGToolCallbackSource.class);
		when(failing.getToolCallbacks()).thenThrow(new IllegalStateException("Not authenticated"));
		List<IGToolCallbackSource> sources = new java.util.ArrayList<>(toolSources.getImplementations());
		sources.add(failing);
		sources.add(shadow);
		when(toolSources.getImplementations()).thenReturn(sources);

		Map<String, List<ToolDataFlowTarget>> targets = component().toolsDataFlowTargets();

		assertEquals(Kind.INTERNET, targets.get("readUrl").get(0).kind());
		assertEquals(List.of(), targets.get("now"));
		assertEquals(6, targets.size());
	}

	/**
	 * The single agent network's flow merged with a data source vectorized into the
	 * vector store its knowledge base search reads, the source flagged or not as
	 * holding personal data, after the register's propagation.
	 */
	private static GDataFlowMetaInfos propagatedWithSource(GDataFlowMetaInfos networkFlow, boolean personalSource) {
		GeboComponentInfo sourceComponent = new GeboComponentInfo("test-module", "test-content-handler");
		GDataFlowMetaInfos source = new GDataFlowMetaInfos();
		source.setComponent(sourceComponent);
		DataEndpoint documents = new DataEndpoint();
		documents.setId("data-source");
		documents.setTypes(List.of(MetaEndpointType.DOCUMENTS));
		documents.setPersonalData(personalSource);
		source.getDataEndpoints().add(documents);

		GDataFlowMetaInfos vectorizator = new GDataFlowMetaInfos();
		vectorizator.setComponent(new GeboComponentInfo(GStandardModulesConstraints.VECTORIZATOR_MODULE,
				GStandardModulesConstraints.VECTORIZATION_COMPONENT));
		DataEndpoint vectorStore = new DataEndpoint();
		vectorStore.setId("vector-store");
		vectorStore.setTypes(List.of(MetaEndpointType.VECTORIAL_DATABASE));
		vectorizator.getDataEndpoints().add(vectorStore);
		vectorizator.getTransformations().add(DataTransformationInfo.of("embed", "embedding",
				DataTransformationMetaInfo.of("engine", "vectorizes", List.of(MetaEndpointType.DOCUMENTS),
						List.of(MetaEndpointType.VECTORIAL_DATABASE)),
				GDataFlowMetaInfos.qualifiedId(sourceComponent, "data-source"), DataFlowEndpoints.vectorStoreRef()));

		DataFlowPersonalDataPropagation.apply(new GDataFlowReport("node", new Date(), new ArrayList<>(List.of(
				new GModuleMetaInfo("network", List.of(componentOf(networkFlow))),
				new GModuleMetaInfo("source", List.of(componentOf(source))),
				new GModuleMetaInfo("vectorizator", List.of(componentOf(vectorizator)))))));
		return vectorizator;
	}

	private static ComponentMetaInfo componentOf(GDataFlowMetaInfos flow) {
		ComponentMetaInfo component = new ComponentMetaInfo();
		component.setDataFlowMetaInfos(flow);
		return component;
	}

	@Test
	void withoutAFlaggedDataSourceNothingIsPersonalData() {
		when(networksDao.getConfigurations()).thenReturn(List.of(network(AGENTIC)));
		GDataFlowMetaInfos flow = component().getDataFlowMetaInfos();

		GDataFlowMetaInfos vectorizator = propagatedWithSource(flow, false);

		assertTrue(flow.getDataEndpoints().stream().noneMatch(DataEndpoint::isPersonalData));
		assertFalse(vectorizator.getDataEndpoints().get(0).isPersonalData());
	}

	@Test
	void aFlaggedDataSourceReachesTheToolsReadingItsStore() {
		when(networksDao.getConfigurations()).thenReturn(List.of(network(AGENTIC)));
		GDataFlowMetaInfos flow = component().getDataFlowMetaInfos();

		GDataFlowMetaInfos vectorizator = propagatedWithSource(flow, true);

		assertTrue(vectorizator.getDataEndpoints().get(0).isPersonalData());
		// the knowledge base search links the network's query to that vector store
		assertTrue(endpoint(flow, "network-query-" + AGENTIC).isPersonalData());
	}
}
