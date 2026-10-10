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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import ai.gebo.application.messaging.model.DataFlowSection;
import ai.gebo.application.messaging.model.DataFlowPersonalDataPropagation;
import ai.gebo.application.messaging.model.DataTransformationInfo;
import ai.gebo.application.messaging.model.DataTransformationMetaInfo;
import ai.gebo.application.messaging.model.GDataFlowMetaInfos;
import ai.gebo.application.messaging.model.GDataFlowReport;
import ai.gebo.application.messaging.model.GModuleMetaInfo;
import ai.gebo.application.messaging.model.GStandardDataFlowEndpoints;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.application.messaging.model.MetaEndpointType;
import ai.gebo.architecture.agents.model.AgentMountedTools;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.services.IAgentConfigDao;
import ai.gebo.architecture.agents.services.IAgentsNetworkDao;
import ai.gebo.architecture.agents.services.IGAgentServiceRuntimeDao;
import ai.gebo.architecture.agents.services.IGGenericAgentService;
import ai.gebo.architecture.ai.model.ToolDataFlowTarget;
import ai.gebo.architecture.ai.model.ToolDataFlowTarget.Kind;
import ai.gebo.architecture.ai.service.IGToolCallbackSource;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.graphrag.services.IKnowledgeGraphSearchService;
import ai.gebo.architecture.rag.support.layer.services.IGFullTextSearchDocumentsCachedDao;
import ai.gebo.architecture.search.model.SearchableSystemMetaData;
import ai.gebo.architecture.search.service.AbstractWebSearchServiceImpl;
import ai.gebo.architecture.search.service.ISearchServiceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelConfig;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGRankerModelRuntimeConfigurationDao;
import ai.gebo.llms.agent.standardtools.InternalKnowledgeBaseSearchToolSource;
import ai.gebo.llms.chat.abstraction.layer.services.impl.DataFlowEndpoints;
import ai.gebo.model.base.GeboComponentInfo;
import ai.gebo.security.services.IGeboSystemUserService;

/**
 * Pins the register entries of the agents networks, reported member by member: a
 * member calling a chat model gets the network's messages there (the input adapter
 * calls none), the knowledge-base searcher reads the knowledge stores deployed here,
 * a search-service searcher writes its queries to its search sources and reads and
 * chunks what they find, and the tools a member mounts reach their targets: the
 * stores and the platform data are read, the models, the internet and the MCP
 * servers are written to, the search sources both. Every step follows the data, so
 * personal data reach a network only from a data source an administrator flags, and
 * never travel to another network through a shared model.
 */
class GAgentsNetworkDataFlowComponentTest {

	private static final String AGENTIC = "AGENTIC";
	private static final String FREE_CHAT = "FREE_CHAT";
	private static final String MULTI = "MULTI";
	private static final String ADAPTER_NETWORK = "ADAPTER_NETWORK";
	// a web search service is a native search service: its searcher is a native searcher
	private static final String BRAVE_SEARCHER = "brave-search" + NativeDocumentsSearchNetworkAgentService.NATIVE_SEARCHER_AGENT;
	private static final String BRAVE_SOURCE = GStandardDataFlowEndpoints.searchSourceRef("brave", "brave-account");

	private IAgentsNetworkDao networksDao;
	private IAgentConfigDao agentConfigs;
	private IGAgentServiceRuntimeDao agentServices;
	private IGToolCallbackSourceRepositoryPattern toolSources;
	private IGChatModelRuntimeConfigurationDao chatModelsDao;
	private IGEmbeddingModelRuntimeConfigurationDao embeddingModelsDao;
	private IGRankerModelRuntimeConfigurationDao rankerModelsDao;
	private ISearchServiceRepositoryPattern searchServices;
	/** The full-text search, present on an installation deploying OpenSearch. */
	private IGFullTextSearchDocumentsCachedDao fullTextSearch;
	/** The knowledge graph search, present on an installation deploying Neo4j. */
	private IKnowledgeGraphSearchService knowledgeGraphSearch;
	private final Map<String, GAgentConfig> configs = new HashMap<>();
	private final Map<String, IGGenericAgentService> services = new HashMap<>();

	@SuppressWarnings("unchecked")
	private static <T> ObjectProvider<T> provider(T value) {
		ObjectProvider<T> provider = mock(ObjectProvider.class);
		when(provider.getIfAvailable()).thenReturn(value);
		return provider;
	}

	private static ToolCallback tool(String name) {
		ToolCallback callback = mock(ToolCallback.class);
		when(callback.getToolDefinition())
				.thenReturn(ToolDefinition.builder().name(name).description(name).inputSchema("{}").build());
		return callback;
	}

	/** An agent configuration run by the given agent service. */
	private void agent(String configCode, String serviceId) {
		GAgentConfig config = new GAgentConfig();
		config.setCode(configCode);
		config.setAgentServiceId(serviceId);
		configs.put(configCode, config);
	}

	/** An agent service calling the given model (none when null) and mounting the given tools. */
	private void service(String serviceId, IGConfigurableChatModel model, String... tools) {
		IGGenericAgentService service = mock(IGGenericAgentService.class);
		when(service.getId()).thenReturn(serviceId);
		when(service.isCallingChatModel()).thenReturn(model != null);
		when(service.resolveAgentChatModel(any())).thenReturn(model);
		AgentMountedTools mounted = new AgentMountedTools();
		for (String tool : tools) {
			mounted.getTools().add(new AgentMountedTools.MountedTool(tool, tool, null, null));
		}
		when(service.getMountedTools(any())).thenReturn(mounted);
		services.put(serviceId, service);
	}

	private static GAgentsNetwork network(String code, String... configCodes) {
		GAgentsNetwork network = new GAgentsNetwork();
		network.setCode(code);
		List<AgentNetworkParticipant> participants = new ArrayList<>();
		for (String configCode : configCodes) {
			AgentNetworkParticipant participant = new AgentNetworkParticipant();
			participant.setAgentConfigCode(configCode);
			participants.add(participant);
		}
		network.setAgents(participants);
		return network;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@BeforeEach
	void setUp() throws Exception {
		networksDao = mock(IAgentsNetworkDao.class);
		agentConfigs = mock(IAgentConfigDao.class);
		agentServices = mock(IGAgentServiceRuntimeDao.class);
		toolSources = mock(IGToolCallbackSourceRepositoryPattern.class);
		chatModelsDao = mock(IGChatModelRuntimeConfigurationDao.class);
		embeddingModelsDao = mock(IGEmbeddingModelRuntimeConfigurationDao.class);
		rankerModelsDao = mock(IGRankerModelRuntimeConfigurationDao.class);
		searchServices = mock(ISearchServiceRepositoryPattern.class);
		configs.clear();
		services.clear();
		when(agentConfigs.findByCode(any())).thenAnswer(invocation -> configs.get(invocation.getArgument(0)));
		when(agentServices.findByCode(any())).thenAnswer(invocation -> services.get(invocation.getArgument(0)));

		GBaseChatModelConfig chatConfig = new GBaseChatModelConfig();
		chatConfig.setModelTypeCode("openai");
		chatConfig.setBaseUrl("https://api.openai.com/v1");
		IGConfigurableChatModel gpt = mock(IGConfigurableChatModel.class);
		when(gpt.getCode()).thenReturn("gpt");
		when(gpt.getConfig()).thenReturn(chatConfig);

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
		SearchableSystemMetaData braveAccount = new SearchableSystemMetaData();
		braveAccount.setCode("brave-account");
		when(brave.getSearchableSystems()).thenReturn((List) List.of(braveAccount));
		when(searchServices.getImplementations()).thenReturn((List) List.of(brave));

		IGToolCallbackSource knowledgeBase = mock(IGToolCallbackSource.class);
		when(knowledgeBase.getId()).thenReturn("kb");
		List<ToolCallback> knowledgeBaseTools = List.of(tool(InternalKnowledgeBaseSearchToolSource.SEARCH_KNOWLEDGE_BASE_TOOL));
		when(knowledgeBase.getToolCallbacks()).thenReturn(knowledgeBaseTools);
		when(knowledgeBase.getDataFlowTargets(InternalKnowledgeBaseSearchToolSource.SEARCH_KNOWLEDGE_BASE_TOOL))
				.thenReturn(List.of(ToolDataFlowTarget.of(Kind.EMBEDDING_MODEL, "query embedded"),
						ToolDataFlowTarget.of(Kind.KNOWLEDGE_BASE_VECTOR_STORE, "semantic search"),
						ToolDataFlowTarget.of(Kind.KNOWLEDGE_BASE_FULLTEXT_INDEX, "full-text search"),
						ToolDataFlowTarget.of(Kind.KNOWLEDGE_BASE_GRAPH_STORE, "graph search"),
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

		// the members: the input adapter calls no model; the agentic loop agents mount
		// every tool (the free chat one but the knowledge base ones)
		agent("adapter-config", "adapter");
		service("adapter", null);
		agent("agentic-config", "agentic");
		service("agentic", gpt, InternalKnowledgeBaseSearchToolSource.SEARCH_KNOWLEDGE_BASE_TOOL, "searchWeb",
				"readUrl", "getActualUser", "github_search", "now");
		agent("free-config", "free");
		service("free", gpt, "searchWeb", "readUrl", "getActualUser", "github_search", "now");
		// the multi agent members: the searchers, a tool calling agent and a controller
		agent("kb-searcher-config", InternalKnowledgeBaseSearchNetworkAgentService.INTERNAL_KNOWLEDGE_BASE_SEARCHER);
		service(InternalKnowledgeBaseSearchNetworkAgentService.INTERNAL_KNOWLEDGE_BASE_SEARCHER, gpt);
		agent("brave-searcher-config", BRAVE_SEARCHER);
		service(BRAVE_SEARCHER, gpt);
		agent("tool-agent-config", "tool-agent");
		service("tool-agent", gpt, "readUrl");
		agent("controller-config", "controller");
		service("controller", gpt);
		GAgentConfig adapting = new GAgentConfig();
		adapting.setCode("adapting-config");
		adapting.setAgentType(GAgentConfig.AgentType.AGENTS_NETWORK);
		adapting.setAdaptedAgentNetworkCode(MULTI);
		configs.put("adapting-config", adapting);

		// a semantic only installation unless a test deploys the optional legs
		fullTextSearch = null;
		knowledgeGraphSearch = null;
	}

	/** Deploys the full-text and the knowledge graph search, as OpenSearch and Neo4j do. */
	private void deployAllSearchLegs() {
		fullTextSearch = mock(IGFullTextSearchDocumentsCachedDao.class);
		knowledgeGraphSearch = mock(IKnowledgeGraphSearchService.class);
	}

	private GAgentsNetworkDataFlowComponent component() {
		return new GAgentsNetworkDataFlowComponent(provider(networksDao), provider(searchServices),
				provider((IGeboSystemUserService) null), provider(toolSources), provider(chatModelsDao),
				provider(embeddingModelsDao), provider(rankerModelsDao), provider(fullTextSearch),
				provider(knowledgeGraphSearch), provider(agentConfigs), provider(agentServices));
	}

	private GDataFlowMetaInfos flowOf(GAgentsNetwork... networks) {
		when(networksDao.getConfigurations()).thenReturn(List.of(networks));
		return component().getDataFlowMetaInfos();
	}

	private static DataEndpoint endpoint(GDataFlowMetaInfos flow, String id) {
		return flow.getDataEndpoints().stream().filter(x -> id.equals(x.getId())).findFirst().orElse(null);
	}

	/** Where the network's data are written to. */
	private static List<String> destinationsFrom(GDataFlowMetaInfos flow, String networkCode) {
		String query = flow.qualifiedId("network-query-" + networkCode);
		return flow.getTransformations().stream().filter(x -> query.equals(x.getDataSourceId()))
				.map(DataTransformationInfo::getDataDestinationId).toList();
	}

	/** What the network reads. */
	private static List<String> sourcesInto(GDataFlowMetaInfos flow, String networkCode) {
		String query = flow.qualifiedId("network-query-" + networkCode);
		return flow.getTransformations().stream().filter(x -> query.equals(x.getDataDestinationId()))
				.map(DataTransformationInfo::getDataSourceId).toList();
	}

	@Test
	void anAgenticLoopNetworkReportsItsAgentsModelAndWhatItsToolsReach() {
		GDataFlowMetaInfos flow = flowOf(network(AGENTIC, "adapter-config", "agentic-config"));

		assertEquals(List.of(flow.qualifiedId("agent-model-gpt"), flow.qualifiedId("embedding-model-nomic"), BRAVE_SOURCE,
				flow.qualifiedId("internet-pages"), flow.qualifiedId("mcp-server-github")), destinationsFrom(flow, AGENTIC));
		// a semantic only installation: the knowledge base tool reads the vector store only
		assertEquals(List.of(DataFlowEndpoints.vectorStoreRef(), BRAVE_SOURCE,
				flow.qualifiedId("platform-data-platform-users-and-groups")), sourcesInto(flow, AGENTIC));
		// the input adapter calls no model, no ranker is configured
		assertEquals(1, flow.getDataEndpoints().stream().filter(x -> x.getId().startsWith("agent-model-")).count());
		assertTrue(flow.getDataEndpoints().stream().noneMatch(x -> x.getId().startsWith("ranker-model-")));
		// the search sources are reported once, by the search sources component
		assertTrue(flow.getDataEndpoints().stream().noneMatch(x -> x.getId().startsWith("search-")));

		assertEquals(DataEndpointLocality.EXTERNAL_PROVIDER, endpoint(flow, "internet-pages").getLocality());
		assertEquals(DataEndpointLocality.LOCAL_DEPLOYMENT,
				endpoint(flow, "platform-data-platform-users-and-groups").getLocality());
		DataEndpoint mcp = endpoint(flow, "mcp-server-github");
		assertEquals(DataEndpointLocality.LOCAL_DEPLOYMENT, mcp.getLocality());
		assertEquals("stdio:npx", mcp.getEndpoint());
		// a tool marks no personal data: they come only from the data sources flagged so
		assertTrue(flow.getDataEndpoints().stream().noneMatch(DataEndpoint::isPersonalData));

		DataTransformationInfo kbRead = flow.getTransformations().stream()
				.filter(x -> DataFlowEndpoints.vectorStoreRef().equals(x.getDataSourceId())).findFirst().orElseThrow();
		assertEquals("Semantic search of the knowledge bases (tools: "
				+ InternalKnowledgeBaseSearchToolSource.SEARCH_KNOWLEDGE_BASE_TOOL + ")", kbRead.getDescription());
	}

	@Test
	void aFreeChatNetworkReadsNoKnowledgeBase() {
		GDataFlowMetaInfos flow = flowOf(network(FREE_CHAT, "adapter-config", "free-config"));

		assertTrue(destinationsFrom(flow, FREE_CHAT).contains(flow.qualifiedId("agent-model-gpt")));
		assertTrue(destinationsFrom(flow, FREE_CHAT).contains(BRAVE_SOURCE));
		assertFalse(sourcesInto(flow, FREE_CHAT).contains(DataFlowEndpoints.vectorStoreRef()));
		// the embedding model was reached only through the knowledge base search
		assertNull(endpoint(flow, "embedding-model-nomic"));
	}

	@Test
	void anAgenticLoopNetworkReadsEveryDeployedStore() {
		deployAllSearchLegs();

		GDataFlowMetaInfos flow = flowOf(network(AGENTIC, "adapter-config", "agentic-config"));

		List<String> read = sourcesInto(flow, AGENTIC);
		assertTrue(read.contains(DataFlowEndpoints.fullTextIndexRef()), String.valueOf(read));
		// named as the graph extraction publishes it, so the step reaches the store
		assertEquals("knowledge-graph-module.knowledge-graph-component<->knowledge-graph",
				DataFlowEndpoints.knowledgeGraphRef());
		assertTrue(read.contains(DataFlowEndpoints.knowledgeGraphRef()), String.valueOf(read));
	}

	@Test
	void aMultiAgentNetworkReportsEachMemberOnASemanticOnlyInstallation() {
		GDataFlowMetaInfos flow = flowOf(network(MULTI, "adapter-config", "kb-searcher-config",
				"brave-searcher-config", "tool-agent-config", "controller-config"));

		List<String> read = sourcesInto(flow, MULTI);
		assertEquals(List.of(DataFlowEndpoints.vectorStoreRef(), BRAVE_SOURCE), read);
		List<String> written = destinationsFrom(flow, MULTI);
		// the four members calling a model, the searchers' queries, the tool's target
		assertEquals(4, written.stream().filter(x -> x.equals(flow.qualifiedId("agent-model-gpt"))).count());
		assertTrue(written.contains(flow.qualifiedId("embedding-model-nomic")), String.valueOf(written));
		assertTrue(written.contains(BRAVE_SOURCE), String.valueOf(written));
		assertTrue(written.contains(flow.qualifiedId("internet-pages")), String.valueOf(written));
		// what the search service finds is chunked for the request
		assertTrue(flow.getTransformations().stream().anyMatch(x -> BRAVE_SOURCE.equals(x.getDataSourceId())
				&& GStandardDataFlowEndpoints.chunkCacheRef().equals(x.getDataDestinationId())));
	}

	@Test
	void aMultiAgentKnowledgeBaseSearcherReadsEveryDeployedStore() {
		deployAllSearchLegs();

		GDataFlowMetaInfos flow = flowOf(network(MULTI, "kb-searcher-config"));

		assertEquals(List.of(DataFlowEndpoints.vectorStoreRef(), DataFlowEndpoints.fullTextIndexRef(),
				DataFlowEndpoints.knowledgeGraphRef()), sourcesInto(flow, MULTI));
	}

	@Test
	void aNetworkAdaptingAnotherOneHandsItItsRequestsAndGetsItsAnswers() {
		GDataFlowMetaInfos flow = flowOf(network(ADAPTER_NETWORK, "adapting-config"), network(MULTI, "controller-config"));

		String adapted = flow.qualifiedId("network-query-" + MULTI);
		assertEquals(List.of(adapted), destinationsFrom(flow, ADAPTER_NETWORK));
		assertEquals(List.of(adapted), sourcesInto(flow, ADAPTER_NETWORK));
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
		List<IGToolCallbackSource> sources = new ArrayList<>(toolSources.getImplementations());
		sources.add(failing);
		sources.add(shadow);
		when(toolSources.getImplementations()).thenReturn(sources);

		Map<String, List<ToolDataFlowTarget>> targets = component().toolsDataFlowTargets();

		assertEquals(Kind.INTERNET, targets.get("readUrl").get(0).kind());
		assertEquals(List.of(), targets.get("now"));
		assertEquals(6, targets.size());
	}

	/**
	 * The networks' flow merged with a data source vectorized into the vector store
	 * the agentic loop network reads, the source flagged or not as holding personal
	 * data, after the register's propagation.
	 */
	private static void propagateWithSource(GDataFlowMetaInfos networksFlow, boolean personalSource) {
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
		vectorStore.setId(GStandardDataFlowEndpoints.VECTOR_STORE);
		vectorStore.setTypes(List.of(MetaEndpointType.VECTORIAL_DATABASE));
		vectorizator.getDataEndpoints().add(vectorStore);
		vectorizator.getTransformations().add(DataTransformationInfo.of("embed", "embedding",
				DataTransformationMetaInfo.of("engine", "vectorizes", List.of(MetaEndpointType.DOCUMENTS),
						List.of(MetaEndpointType.VECTORIAL_DATABASE)),
				GDataFlowMetaInfos.qualifiedId(sourceComponent, "data-source"), DataFlowEndpoints.vectorStoreRef()));

		DataFlowPersonalDataPropagation.apply(new GDataFlowReport("node", new Date(), new ArrayList<>(List.of(
				new GModuleMetaInfo("network", List.of(componentOf(networksFlow))),
				new GModuleMetaInfo("source", List.of(componentOf(source))),
				new GModuleMetaInfo("vectorizator", List.of(componentOf(vectorizator)))))));
	}

	private static ComponentMetaInfo componentOf(GDataFlowMetaInfos flow) {
		ComponentMetaInfo component = new ComponentMetaInfo();
		component.setDataFlowMetaInfos(flow);
		return component;
	}

	@Test
	void withoutAFlaggedDataSourceNothingIsPersonalData() {
		GDataFlowMetaInfos flow = flowOf(network(AGENTIC, "adapter-config", "agentic-config"));

		propagateWithSource(flow, false);

		assertTrue(flow.getDataEndpoints().stream().noneMatch(DataEndpoint::isPersonalData));
	}

	@Test
	void aFlaggedDataSourceReachesTheNetworkReadingItsStoreAndWhatItWritesToButNotOtherNetworks() {
		GDataFlowMetaInfos flow = flowOf(network(AGENTIC, "adapter-config", "agentic-config"),
				network(FREE_CHAT, "adapter-config", "free-config"));

		propagateWithSource(flow, true);

		// the network reading the store, its model and what it writes its tool arguments to
		assertTrue(endpoint(flow, "network-query-" + AGENTIC).isPersonalData());
		assertTrue(endpoint(flow, "agent-model-gpt").isPersonalData());
		assertTrue(endpoint(flow, "mcp-server-github").isPersonalData());
		// the free chat network reads no knowledge base: the model it shares with the
		// agentic network passes nothing on
		assertFalse(endpoint(flow, "network-query-" + FREE_CHAT).isPersonalData());
		// the platform's data are only read
		assertFalse(endpoint(flow, "platform-data-platform-users-and-groups").isPersonalData());
	}

	@Test
	void eachNetworkIsASectionNamedByItsDescriptionWithItsOwnSteps() {
		GAgentsNetwork agentic = network(AGENTIC, "adapter-config", "agentic-config");
		agentic.setDescription("Agentic loop");
		GDataFlowMetaInfos flow = flowOf(agentic, network(FREE_CHAT, "adapter-config", "free-config"));

		assertEquals(List.of(new DataFlowSection(AGENTIC, "Agentic loop"), new DataFlowSection(FREE_CHAT, FREE_CHAT)),
				flow.getSections(), "named by the description, else by the code");
		assertEquals(AGENTIC, endpoint(flow, "network-query-" + AGENTIC).getSection());
		assertEquals("Agent-network query - 'Agentic loop'", endpoint(flow, "network-query-" + AGENTIC).getDescription());
		assertEquals(FREE_CHAT, endpoint(flow, "network-query-" + FREE_CHAT).getSection());
		String agenticQuery = flow.qualifiedId("network-query-" + AGENTIC);
		assertTrue(flow.getTransformations().stream().filter(x -> agenticQuery.equals(x.getDataSourceId()))
				.allMatch(x -> AGENTIC.equals(x.getSection())));
		assertTrue(flow.getTransformations().stream().allMatch(x -> x.getSection() != null), "every step is a network's");
		// the model both networks call is shared by them
		assertNull(endpoint(flow, "agent-model-gpt").getSection());
	}
}
