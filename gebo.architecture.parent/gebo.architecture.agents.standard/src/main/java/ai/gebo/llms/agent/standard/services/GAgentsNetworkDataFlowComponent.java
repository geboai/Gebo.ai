/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.standard.services;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.IGMessageEmitter;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.DataEndpoint;
import ai.gebo.application.messaging.model.DataEndpointLocality;
import ai.gebo.application.messaging.model.DataTransformationInfo;
import ai.gebo.application.messaging.model.DataTransformationMetaInfo;
import ai.gebo.application.messaging.model.GDataFlowMetaInfos;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.application.messaging.model.MetaEndpointType;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.services.IAgentsNetworkDao;
import ai.gebo.architecture.ai.model.ToolDataFlowTarget;
import ai.gebo.architecture.ai.service.IGToolCallbackSource;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.search.service.AbstractWebSearchServiceImpl;
import ai.gebo.architecture.search.service.ISearchService;
import ai.gebo.architecture.search.service.ISearchServiceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGRankerModelRuntimeConfigurationDao;
import ai.gebo.llms.agent.chat.service.impl.AgenticLoopPureChatReactiveAgentServiceImpl;
import ai.gebo.llms.agent.standard.config.AgenticLoopAgentsInitialization;
import ai.gebo.llms.chat.abstraction.layer.services.impl.DataFlowEndpoints;
import ai.gebo.model.base.GeboComponentInfo;
import ai.gebo.security.services.IGeboSystemUserService;
import ai.gebo.security.services.IdentityUtil;
import ai.gebo.security.services.RunAsWithReturn;

/**
 * A <b>symbolic</b> messaging component that puts the agent-network responder's
 * data flow into the compliance register.
 *
 * <p>
 * A network of agents can act as the chat responder: its finder agents are wired
 * to the internal knowledge-base search service (semantic over the vector store,
 * lexical over the full-text index) and to the external web-search services. Like
 * the chat pipeline, the network is a set of services rather than a broker
 * component, so this stand-in emitter exists only to report - for each configured
 * network - the query fan-out its finders perform: query -&gt; internal KB search
 * (vector store, full-text index) and query -&gt; each enabled external web-search
 * provider.
 * </p>
 *
 * <p>
 * The single agent networks ({@link #SINGLE_AGENT_NETWORKS}) have no finders: their
 * one agent sends the query to its chat model and operates the tools it mounts, so
 * they report what those tools reach, as each tool source declares it
 * ({@link IGToolCallbackSource#getDataFlowTargets(String)}).
 * </p>
 *
 * <p>
 * Every collaborator is resolved lazily through an {@code ObjectProvider}, for the
 * same cycle-avoidance reason as the other symbolic reporters.
 * </p>
 */
@Component
public class GAgentsNetworkDataFlowComponent implements IGMessageEmitter {
	private static final Logger LOGGER = LoggerFactory.getLogger(GAgentsNetworkDataFlowComponent.class);

	public static final String AGENT_NETWORK_MODULE = "agent-network-module";
	public static final String AGENTS_NETWORK_RESPONDER_COMPONENT = "agents-network-responder";

	private final ObjectProvider<IAgentsNetworkDao> agentsNetworkDaoProvider;
	private final ObjectProvider<ISearchServiceRepositoryPattern> searchServicesProvider;
	private final ObjectProvider<IGeboSystemUserService> systemUserServiceProvider;
	private final ObjectProvider<IGToolCallbackSourceRepositoryPattern> toolSourcesProvider;
	private final ObjectProvider<IGChatModelRuntimeConfigurationDao> chatModelsDaoProvider;
	private final ObjectProvider<IGEmbeddingModelRuntimeConfigurationDao> embeddingModelsDaoProvider;
	private final ObjectProvider<IGRankerModelRuntimeConfigurationDao> rankerModelsDaoProvider;

	/**
	 * The single agent networks: their one agent mounts the tools itself (all of them,
	 * the free chat one but the knowledge base ones), so their data flow is the one of
	 * those tools, not the finders' fan-out of the multi agent networks.
	 */
	static final Set<String> SINGLE_AGENT_NETWORKS = Set.of(AgenticLoopAgentsInitialization.AGENTIC_LOOP_AGENTS_NETWORK,
			AgenticLoopAgentsInitialization.AGENTIC_LOOP_PURE_CHAT_AGENTS_NETWORK);

	public GAgentsNetworkDataFlowComponent(@Autowired ObjectProvider<IAgentsNetworkDao> agentsNetworkDaoProvider,
			@Autowired ObjectProvider<ISearchServiceRepositoryPattern> searchServicesProvider,
			@Autowired ObjectProvider<IGeboSystemUserService> systemUserServiceProvider,
			@Autowired ObjectProvider<IGToolCallbackSourceRepositoryPattern> toolSourcesProvider,
			@Autowired ObjectProvider<IGChatModelRuntimeConfigurationDao> chatModelsDaoProvider,
			@Autowired ObjectProvider<IGEmbeddingModelRuntimeConfigurationDao> embeddingModelsDaoProvider,
			@Autowired ObjectProvider<IGRankerModelRuntimeConfigurationDao> rankerModelsDaoProvider) {
		this.agentsNetworkDaoProvider = agentsNetworkDaoProvider;
		this.searchServicesProvider = searchServicesProvider;
		this.systemUserServiceProvider = systemUserServiceProvider;
		this.toolSourcesProvider = toolSourcesProvider;
		this.chatModelsDaoProvider = chatModelsDaoProvider;
		this.embeddingModelsDaoProvider = embeddingModelsDaoProvider;
		this.rankerModelsDaoProvider = rankerModelsDaoProvider;
	}

	@Override
	public String getMessagingModuleId() {
		return AGENT_NETWORK_MODULE;
	}

	@Override
	public String getMessagingSystemId() {
		return AGENTS_NETWORK_RESPONDER_COMPONENT;
	}

	@Override
	public SystemComponentType getComponentType() {
		return SystemComponentType.APPLICATION_COMPONENT;
	}

	@Override
	public List<String> getEmittedPayloadTypes() {
		return List.of();
	}

	@Override
	public GDataFlowMetaInfos getDataFlowMetaInfos() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin getDataFlowMetaInfos() for the symbolic component:" + getMessagingSystemId());
		}
		IAgentsNetworkDao agentsNetworkDao = agentsNetworkDaoProvider.getIfAvailable();
		if (agentsNetworkDao == null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("No agents network DAO available yet, the data flow register gets no agent network entry");
			}
			return null;
		}
		List<GAgentsNetwork> networks;
		try {
			networks = listNetworks(agentsNetworkDao);
		} catch (RuntimeException e) {
			LOGGER.warn("Cannot enumerate the configured agents networks for the data flow register", e);
			return null;
		}
		if (networks == null || networks.isEmpty()) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("No agents network configured, nothing to report in the data flow register");
			}
			return null;
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Reporting the data flow of " + networks.size() + " configured agents network(s)");
		}

		GDataFlowMetaInfos flow = new GDataFlowMetaInfos();
		flow.setComponent(new GeboComponentInfo(getMessagingModuleId(), getMessagingSystemId()));

		List<ISearchService> webProviders = enabledWebSearchProviders();
		// what each registered tool reaches, resolved once for the single agent networks
		Map<String, List<ToolDataFlowTarget>> toolTargets = null;

		for (GAgentsNetwork network : networks) {
			if (network == null || network.getCode() == null) {
				continue;
			}
			String code = network.getCode();
			if (LOGGER.isDebugEnabled() && !SINGLE_AGENT_NETWORKS.contains(code)) {
				LOGGER.debug("Reporting the query fan-out of network:" + code + " towards the internal knowledge base and "
						+ webProviders.size() + " external web search provider(s)");
			}

			DataEndpoint query = new DataEndpoint();
			query.setId("network-query-" + code);
			query.setDescription("Agent-network query - '" + code + "'");
			query.setProduct("Network of agents");
			query.setEndpoint("agents-network", code, null, null);
			query.setInput(true);
			query.setTypes(list(MetaEndpointType.CHAT_SESSION));
			query.setPersonalData(false);
			query.setLocality(DataEndpointLocality.LOCAL_DEPLOYMENT);
			flow.getDataEndpoints().add(query);

			if (SINGLE_AGENT_NETWORKS.contains(code)) {
				if (toolTargets == null) {
					toolTargets = toolsDataFlowTargets();
				}
				reportSingleAgent(flow, query, code, toolTargets);
				continue;
			}

			// Finder agents on the internal knowledge-base search service: semantic
			// over the vector store, lexical over the full-text index. Both are drawn
			// only when the store actually exists (the view drops edges to absent
			// endpoints), keeping this faithful to what is configured.
			link(flow, "kb-semantic", code, "Finder: semantic knowledge-base search", MetaEndpointType.CHAT_SESSION,
					MetaEndpointType.VECTORIAL_DATABASE, flow.qualifiedId(query.getId()), vectorStoreRef());
			link(flow, "kb-fulltext", code, "Finder: full-text knowledge-base search", MetaEndpointType.CHAT_SESSION,
					MetaEndpointType.FULLTEXT_INDEX, flow.qualifiedId(query.getId()), fullTextIndexRef());

			// Finder agents on the external web-search services.
			for (ISearchService provider : webProviders) {
				String providerEndpointId = "web-search-" + safe(provider.getId(), safe(provider.getProductId(), ""));
				addUnique(flow, webProviderEndpoint(provider));
				link(flow, "web-" + providerEndpointId, code, "Finder: external web search", MetaEndpointType.CHAT_SESSION,
						MetaEndpointType.WEB_SEARCH, flow.qualifiedId(query.getId()), flow.qualifiedId(providerEndpointId));
			}
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End getDataFlowMetaInfos() reporting " + flow.getDataEndpoints().size() + " endpoint(s) and "
					+ flow.getTransformations().size() + " transformation(s)");
		}
		return flow.getDataEndpoints().isEmpty() ? null : flow;
	}

	/**
	 * A single agent network: the query goes to the agent's chat model and, through
	 * the tools the agent mounts, to what each of them reaches: one endpoint per
	 * target, linked once from the query with the tools reaching it.
	 */
	void reportSingleAgent(GDataFlowMetaInfos flow, DataEndpoint query, String code,
			Map<String, List<ToolDataFlowTarget>> toolTargets) {
		final String queryId = flow.qualifiedId(query.getId());
		final IGChatModelRuntimeConfigurationDao chatModelsDao = chatModelsDaoProvider.getIfAvailable();
		final DataEndpoint agentModel = DataFlowEndpoints.chatModel("agent", defaultChatModel(chatModelsDao),
				"Single agent chat model");
		if (agentModel != null) {
			DataFlowEndpoints.addUnique(flow, agentModel);
			link(flow, "agent-model", code, "Single agent: reasoning, tool calls and answer on the query and the tools' results",
					MetaEndpointType.CHAT_SESSION, MetaEndpointType.LLM_ENDPOINT, queryId,
					flow.qualifiedId(agentModel.getId()));
		}
		final boolean freeChat = AgenticLoopAgentsInitialization.AGENTIC_LOOP_PURE_CHAT_AGENTS_NETWORK.equals(code);
		// endpoint reached -> its type, what is done there and the tools reaching it, in order
		final Map<String, MetaEndpointType> types = new LinkedHashMap<>();
		final Map<String, String> descriptions = new LinkedHashMap<>();
		final Map<String, List<String>> reachingTools = new LinkedHashMap<>();
		for (Map.Entry<String, List<ToolDataFlowTarget>> tool : toolTargets.entrySet()) {
			if (freeChat && AgenticLoopPureChatReactiveAgentServiceImpl.KNOWLEDGE_BASE_TOOLS.contains(tool.getKey())) {
				continue;
			}
			for (ToolDataFlowTarget target : tool.getValue()) {
				final ResolvedTarget resolved = resolve(flow, target, chatModelsDao);
				if (resolved == null) {
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Network:" + code + " tool:" + tool.getKey() + " target " + target.kind() + " ("
								+ target.reference() + ") is not configured here, not reported");
					}
					continue;
				}
				types.putIfAbsent(resolved.qualifiedId(), resolved.type());
				// what is done there, the same for every tool reaching it (each tool's own
				// wording is its source's, shown at TRACE)
				descriptions.putIfAbsent(resolved.qualifiedId(), whatIsDoneAt(target.kind()));
				if (LOGGER.isTraceEnabled()) {
					LOGGER.trace("Network:" + code + " tool:" + tool.getKey() + " -> " + resolved.qualifiedId() + " : "
							+ target.description());
				}
				final List<String> tools = reachingTools.computeIfAbsent(resolved.qualifiedId(), k -> new ArrayList<>());
				if (!tools.contains(tool.getKey())) {
					tools.add(tool.getKey());
				}
			}
		}
		int index = 0;
		for (Map.Entry<String, List<String>> reached : reachingTools.entrySet()) {
			final String description = descriptions.get(reached.getKey());
			link(flow, "tool", code + "-" + (index++),
					description + " (tools: " + String.join(", ", reached.getValue()) + ")",
					MetaEndpointType.CHAT_SESSION, types.get(reached.getKey()), queryId, reached.getKey());
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Reported single agent network:" + code + " towards its chat model:"
					+ (agentModel != null ? agentModel.getId() : null) + " and " + reachingTools.size()
					+ " endpoint(s) reached by its tools");
		}
	}

	/** What the tools do at a target of the kind, as the register describes the link. */
	static String whatIsDoneAt(ToolDataFlowTarget.Kind kind) {
		switch (kind) {
		case KNOWLEDGE_BASE_VECTOR_STORE:
			return "Semantic search of the knowledge bases";
		case KNOWLEDGE_BASE_FULLTEXT_INDEX:
			return "Full-text search of the knowledge bases";
		case KNOWLEDGE_BASE_GRAPH_STORE:
			return "Knowledge graph search";
		case EMBEDDING_MODEL:
			return "Search queries embedded";
		case RANKER_MODEL:
			return "Contents found ranked against the query";
		case SERVICE_MODEL:
			return "Contents found analysed";
		case SEARCH_SERVICE:
			return "Search queries sent, results read";
		case INTERNET:
			return "Pages read from their URL";
		case MCP_SERVER:
			return "Tool arguments sent, results read";
		case PLATFORM_DATA:
			return "Platform data read";
		default:
			return "Data exchanged";
		}
	}

	/** An endpoint a tool reaches: its qualified id in the register and its type. */
	record ResolvedTarget(String qualifiedId, MetaEndpointType type) {
	}

	/**
	 * The endpoint of a tool's target, added to the flow when this component owns it
	 * (the knowledge stores are the indexers' own); null when it does not exist here
	 * (no such model or service configured).
	 */
	ResolvedTarget resolve(GDataFlowMetaInfos flow, ToolDataFlowTarget target,
			IGChatModelRuntimeConfigurationDao chatModelsDao) {
		if (target == null || target.kind() == null) {
			return null;
		}
		switch (target.kind()) {
		case KNOWLEDGE_BASE_VECTOR_STORE:
			return new ResolvedTarget(DataFlowEndpoints.vectorStoreRef(), MetaEndpointType.VECTORIAL_DATABASE);
		case KNOWLEDGE_BASE_FULLTEXT_INDEX:
			return new ResolvedTarget(DataFlowEndpoints.fullTextIndexRef(), MetaEndpointType.FULLTEXT_INDEX);
		case KNOWLEDGE_BASE_GRAPH_STORE:
			return new ResolvedTarget(DataFlowEndpoints.graphStoreRef(), MetaEndpointType.GRAPH_DATABASE);
		case EMBEDDING_MODEL:
			return own(flow, DataFlowEndpoints.embeddingModel(defaultEmbeddingModel()), MetaEndpointType.LLM_ENDPOINT);
		case RANKER_MODEL:
			return own(flow, DataFlowEndpoints.rankerModel(rankerModelsDaoProvider.getIfAvailable()),
					MetaEndpointType.LLM_ENDPOINT);
		case SERVICE_MODEL:
			return own(flow, DataFlowEndpoints.chatModel("service", DataFlowEndpoints.utilityModel(chatModelsDao),
					"Internal services model"), MetaEndpointType.LLM_ENDPOINT);
		case SEARCH_SERVICE: {
			final ISearchService service = searchService(target.reference());
			if (service == null) {
				return null;
			}
			return service instanceof AbstractWebSearchServiceImpl
					? own(flow, webProviderEndpoint(service), MetaEndpointType.WEB_SEARCH)
					: own(flow, searchServiceEndpoint(service), MetaEndpointType.DOCUMENTS);
		}
		case INTERNET:
			return own(flow, internetEndpoint(), MetaEndpointType.WEB_SEARCH);
		case MCP_SERVER:
			return own(flow, mcpServerEndpoint(target), MetaEndpointType.WEB_SEARCH);
		case PLATFORM_DATA:
			return own(flow, platformDataEndpoint(target), MetaEndpointType.DATABASE);
		default:
			return null;
		}
	}

	private static ResolvedTarget own(GDataFlowMetaInfos flow, DataEndpoint endpoint, MetaEndpointType type) {
		if (endpoint == null) {
			return null;
		}
		DataFlowEndpoints.addUnique(flow, endpoint);
		return new ResolvedTarget(flow.qualifiedId(endpoint.getId()), type);
	}

	/** A system searched through a non web search service: its documents are read. */
	private static DataEndpoint searchServiceEndpoint(ISearchService service) {
		String product = safe(service.getProductId(), safe(service.getId(), "search service"));
		DataEndpoint endpoint = new DataEndpoint();
		endpoint.setId("search-service-" + safe(service.getId(), product));
		endpoint.setDescription(safe(service.getDescription(), product));
		endpoint.setProduct(product);
		endpoint.setEndpoint(product + ":" + safe(service.getId(), ""));
		endpoint.setInput(true);
		endpoint.setOutput(true);
		endpoint.setTypes(list(MetaEndpointType.DOCUMENTS));
		endpoint.setPersonalData(false);
		endpoint.setLocality(DataEndpointLocality.EXTERNAL_PROVIDER);
		return endpoint;
	}

	/** Any internet page a tool reads from its URL: a transfer to third parties. */
	private static DataEndpoint internetEndpoint() {
		DataEndpoint endpoint = new DataEndpoint();
		endpoint.setId("internet-pages");
		endpoint.setDescription("Internet pages read from their URL");
		endpoint.setProduct("Internet pages");
		endpoint.setEndpoint("internet");
		endpoint.setInput(true);
		endpoint.setOutput(true);
		endpoint.setTypes(list(MetaEndpointType.WEB_SEARCH));
		endpoint.setPersonalData(false);
		endpoint.setLocality(DataEndpointLocality.EXTERNAL_PROVIDER);
		return endpoint;
	}

	/**
	 * An MCP server the tools' arguments are sent to: local when launched as a
	 * process here (stdio), else as its URL says.
	 */
	static DataEndpoint mcpServerEndpoint(ToolDataFlowTarget target) {
		final String locator = target.locator();
		DataEndpoint endpoint = new DataEndpoint();
		endpoint.setId("mcp-server-" + safe(target.reference(), "unknown"));
		endpoint.setDescription(safe(target.product(), "MCP server"));
		endpoint.setProduct(safe(target.product(), "MCP server"));
		endpoint.setEndpoint(safe(locator, "mcp"));
		endpoint.setInput(true);
		endpoint.setOutput(true);
		endpoint.setTypes(list(MetaEndpointType.WEB_SEARCH));
		endpoint.setPersonalData(target.personalData());
		if (DataFlowEndpoints.notEmpty(target.secretReference())) {
			endpoint.setSecretReference(target.secretReference());
		}
		endpoint.setLocality(locator != null && locator.startsWith("stdio:") ? DataEndpointLocality.LOCAL_DEPLOYMENT
				: DataFlowEndpoints.localityOf(locator));
		return endpoint;
	}

	/** Data of the platform itself the tools read (users, catalogues). */
	static DataEndpoint platformDataEndpoint(ToolDataFlowTarget target) {
		final String name = safe(target.reference(), "platform data");
		final String slug = name.toLowerCase().replaceAll("[^a-z0-9]+", "-");
		DataEndpoint endpoint = new DataEndpoint();
		endpoint.setId("platform-data-" + slug);
		endpoint.setDescription(name);
		endpoint.setProduct(safe(target.product(), name));
		endpoint.setEndpoint("platform", slug, null, null);
		endpoint.setInput(true);
		endpoint.setOutput(false);
		endpoint.setTypes(list(MetaEndpointType.DATABASE));
		endpoint.setPersonalData(target.personalData());
		endpoint.setLocality(DataEndpointLocality.LOCAL_DEPLOYMENT);
		return endpoint;
	}

	/**
	 * What each registered tool reaches, by tool name (the first source declaring a
	 * name wins, as the tools repository resolves them), read under the platform's
	 * system identity for the same reason as {@link #listNetworks}: the tool sources
	 * check the caller's rights.
	 */
	Map<String, List<ToolDataFlowTarget>> toolsDataFlowTargets() {
		final IGToolCallbackSourceRepositoryPattern sources = toolSourcesProvider.getIfAvailable();
		if (sources == null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("No tool sources available, the single agent networks report no tool data flow");
			}
			return Map.of();
		}
		return asSystem(() -> {
			final Map<String, List<ToolDataFlowTarget>> targets = new LinkedHashMap<>();
			final List<IGToolCallbackSource> implementations = sources.getImplementations();
			if (implementations == null) {
				return targets;
			}
			for (IGToolCallbackSource source : implementations) {
				if (source == null) {
					continue;
				}
				try {
					final List<ToolCallback> callbacks = source.getToolCallbacks();
					if (callbacks == null) {
						continue;
					}
					for (ToolCallback callback : callbacks) {
						final String name = callback.getToolDefinition().name();
						if (!targets.containsKey(name)) {
							final List<ToolDataFlowTarget> declared = source.getDataFlowTargets(name);
							targets.put(name, declared != null ? declared : List.of());
							if (LOGGER.isTraceEnabled()) {
								LOGGER.trace("Tool:" + name + " of source:" + source.getId() + " data flow targets:"
										+ declared);
							}
						}
					}
				} catch (RuntimeException e) {
					LOGGER.warn("Cannot resolve the data flow of the tools of source " + source.getId()
							+ " for the data flow register", e);
				}
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Resolved the data flow targets of " + targets.size() + " registered tool(s)");
			}
			return targets;
		});
	}

	private ISearchService searchService(String id) {
		final ISearchServiceRepositoryPattern searchServices = searchServicesProvider.getIfAvailable();
		if (searchServices == null || id == null) {
			return null;
		}
		try {
			final List<ISearchService> all = searchServices.getImplementations();
			if (all == null) {
				return null;
			}
			for (ISearchService service : all) {
				if (service != null && id.equals(service.getId())) {
					return service;
				}
			}
		} catch (RuntimeException e) {
			LOGGER.warn("Cannot find the search service " + id + " for the data flow register", e);
		}
		return null;
	}

	private static IGConfigurableChatModel defaultChatModel(IGChatModelRuntimeConfigurationDao chatModelsDao) {
		try {
			return chatModelsDao != null ? chatModelsDao.defaultHandler() : null;
		} catch (RuntimeException e) {
			LOGGER.warn("Cannot read the default chat model for the data flow register", e);
			return null;
		}
	}

	private IGConfigurableEmbeddingModel defaultEmbeddingModel() {
		final IGEmbeddingModelRuntimeConfigurationDao embeddingModelsDao = embeddingModelsDaoProvider.getIfAvailable();
		try {
			return embeddingModelsDao != null ? embeddingModelsDao.defaultHandler() : null;
		} catch (RuntimeException e) {
			LOGGER.warn("Cannot read the default embedding model for the data flow register", e);
			return null;
		}
	}

	private List<ISearchService> enabledWebSearchProviders() {
		ISearchServiceRepositoryPattern searchServices = searchServicesProvider.getIfAvailable();
		if (searchServices == null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("No search service repository available, no external web search provider is reported");
			}
			return List.of();
		}
		try {
			List<ISearchService> all = searchServices.getImplementations();
			if (all == null) {
				return List.of();
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Filtering " + all.size() + " registered search service(s) down to the enabled ones");
			}
			return all.stream().filter(s -> {
				try {
					return s != null && s.isEnabled();
				} catch (Exception e) {
					LOGGER.warn("Cannot tell whether search service {} is enabled, excluding it from the register",
							s != null ? s.getId() : null, e);
					return false;
				}
			}).toList();
		} catch (RuntimeException e) {
			LOGGER.warn("Cannot enumerate the registered search services for the data flow register", e);
			return List.of();
		}
	}

	private DataEndpoint webProviderEndpoint(ISearchService provider) {
		String product = safe(provider.getProductId(), "web search");
		DataEndpoint endpoint = new DataEndpoint();
		endpoint.setId("web-search-" + safe(provider.getId(), product));
		endpoint.setDescription(safe(provider.getDescription(), product));
		endpoint.setProduct(product);
		endpoint.setEndpoint(product + ":" + safe(provider.getId(), ""));
		endpoint.setInput(true);
		endpoint.setOutput(true);
		endpoint.setTypes(list(MetaEndpointType.WEB_SEARCH));
		endpoint.setPersonalData(false);
		endpoint.setLocality(DataEndpointLocality.EXTERNAL_PROVIDER);
		return endpoint;
	}

	private String vectorStoreRef() {
		return GDataFlowMetaInfos.qualifiedId(new GeboComponentInfo(GStandardModulesConstraints.VECTORIZATOR_MODULE,
				GStandardModulesConstraints.VECTORIZATION_COMPONENT), "vector-store");
	}

	private String fullTextIndexRef() {
		return GDataFlowMetaInfos.qualifiedId(new GeboComponentInfo(GStandardModulesConstraints.FULLTEXT_MODULE,
				GStandardModulesConstraints.FULLTEXT_INDEXING_COMPONENT), "fulltext-index");
	}

	private void link(GDataFlowMetaInfos flow, String kind, String key, String description, MetaEndpointType from,
			MetaEndpointType to, String sourceQualifiedId, String destQualifiedId) {
		DataTransformationMetaInfo engine = DataTransformationMetaInfo.of(kind + "-" + key, description, list(from),
				list(to));
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("Data flow link " + kind + "-" + key + " : " + sourceQualifiedId + " -> " + destQualifiedId
					+ " (" + from + " -> " + to + ")");
		}
		flow.getEngines().add(engine);
		flow.getTransformations()
				.add(DataTransformationInfo.of(kind + "-flow-" + key, description, engine, sourceQualifiedId,
						destQualifiedId));
	}

	private void addUnique(GDataFlowMetaInfos flow, DataEndpoint endpoint) {
		for (DataEndpoint existing : flow.getDataEndpoints()) {
			if (existing.getId() != null && existing.getId().equals(endpoint.getId())) {
				return;
			}
		}
		flow.getDataEndpoints().add(endpoint);
	}

	private static String safe(String s, String fallback) {
		return s != null && !s.trim().isEmpty() ? s : fallback;
	}

	private static List<MetaEndpointType> list(MetaEndpointType... types) {
		return new java.util.ArrayList<MetaEndpointType>(List.of(types));
	}

	/**
	 * Enumerates the configured networks under the platform's own system identity.
	 *
	 * <p>
	 * The register is assembled from a {@code ContextRefreshedEvent} (see
	 * {@code MessageBrokeringAssembler}), on a thread that carries no caller identity,
	 * while building each network configuration transitively reaches security checks -
	 * {@code GSecurityServiceImpl.isCurrentUserAdmin()} through the tool repository -
	 * that require an authenticated {@code SecurityContext}. Without one the MCP tool
	 * export fails with "Not authenticated" and the snapshot silently describes the
	 * networks as having no tools at all.
	 * </p>
	 *
	 * <p>
	 * This impersonation is for the compliance snapshot only, and must not be extended
	 * to the request path: nothing caches these configurations, so at request time the
	 * same network is rebuilt on the caller's own thread and each user keeps getting a
	 * network resolved under their own profile and ACLs.
	 * </p>
	 */
	private List<GAgentsNetwork> listNetworks(IAgentsNetworkDao agentsNetworkDao) {
		IGeboSystemUserService systemUserService = systemUserServiceProvider.getIfAvailable();
		if (systemUserService == null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("No system user service available, listing the networks without impersonation");
			}
			return agentsNetworkDao.getConfigurations();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Listing the configured networks under the platform system identity:"
					+ systemUserService.getUsername());
		}
		return IdentityUtil.create(systemUserService.getUsername(), systemUserService.getRoles())
				.doRunAsWithReturn(() -> agentsNetworkDao.getConfigurations());
	}

	/** The work done under the platform's system identity, as is when there is none. */
	private <T> T asSystem(RunAsWithReturn<T> work) {
		final IGeboSystemUserService systemUserService = systemUserServiceProvider.getIfAvailable();
		if (systemUserService == null) {
			return work.apply();
		}
		return IdentityUtil.create(systemUserService.getUsername(), systemUserService.getRoles())
				.doRunAsWithReturn(work);
	}
}
