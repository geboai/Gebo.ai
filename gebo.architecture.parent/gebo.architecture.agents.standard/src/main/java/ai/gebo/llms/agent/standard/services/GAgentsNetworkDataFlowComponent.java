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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.IGMessageEmitter;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.DataEndpoint;
import ai.gebo.application.messaging.model.DataEndpointAccess;
import ai.gebo.application.messaging.model.DataEndpointLocality;
import ai.gebo.application.messaging.model.DataTransformationInfo;
import ai.gebo.application.messaging.model.DataTransformationMetaInfo;
import ai.gebo.application.messaging.model.GDataFlowMetaInfos;
import ai.gebo.application.messaging.model.MetaEndpointType;
import ai.gebo.application.messaging.model.GStandardDataFlowEndpoints;
import ai.gebo.architecture.agents.model.AgentMountedTools;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.services.IAgentConfigDao;
import ai.gebo.architecture.agents.services.IGAgentServiceRuntimeDao;
import ai.gebo.architecture.agents.services.IGGenericAgentService;
import ai.gebo.architecture.search.service.INativeSearchService;
import ai.gebo.llms.chat.abstraction.layer.services.impl.GSearchSourcesDataFlowComponent;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.services.IAgentsNetworkDao;
import ai.gebo.architecture.ai.model.ToolDataFlowTarget;
import ai.gebo.architecture.ai.service.IGToolCallbackSource;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.graphrag.services.IKnowledgeGraphSearchService;
import ai.gebo.architecture.rag.support.layer.services.IGFullTextSearchDocumentsCachedDao;
import ai.gebo.architecture.search.service.ISearchService;
import ai.gebo.architecture.search.service.ISearchServiceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGRankerModelRuntimeConfigurationDao;
import ai.gebo.llms.chat.abstraction.layer.services.impl.DataFlowEndpoints;
import ai.gebo.llms.chat.abstraction.layer.services.impl.DataFlowEndpoints.KnowledgeBaseSearchLegs;
import ai.gebo.model.base.GeboComponentInfo;
import ai.gebo.security.services.IGeboSystemUserService;
import ai.gebo.security.services.IdentityUtil;
import ai.gebo.security.services.RunAsWithReturn;

/**
 * A <b>symbolic</b> messaging component that puts the agent-network responder's
 * data flow into the compliance register.
 *
 * <p>
 * Each configured network is reported member by member, as it runs: every member
 * is a participant whose configuration ({@link IAgentConfigDao}) names the agent
 * service running it ({@link IGAgentServiceRuntimeDao}). A member calling a chat
 * model receives the network's messages there, the model being the one its service
 * resolves ({@link IGGenericAgentService#resolveAgentChatModel}); the
 * knowledge-base searcher reads the knowledge stores the knowledge-base search
 * reads on this installation; a search-service searcher writes its queries to its
 * search service and chunks the documents found for the request; the tools a member
 * mounts ({@link IGGenericAgentService#getMountedTools}) reach what each tool source
 * declares ({@link IGToolCallbackSource#getDataFlowTargets(String)}). A network
 * adapting another one hands it its requests and gets its answers.
 * </p>
 *
 * <p>
 * The network's query endpoint stands for the network's shared context, the steps
 * reported in the direction the data travel: what the network reads (the knowledge
 * stores, the platform data) reaches it, and from it the members' chat models and
 * whatever the members write to (the search services, the tools' targets), since a
 * model writes its tool arguments from what it has read. Models are reached and do
 * not pass anything on: a model is shared by networks and keeps nothing between
 * calls, so personal data never travel from one network to another through it.
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
	private final ObjectProvider<IGFullTextSearchDocumentsCachedDao> fullTextSearchProvider;
	private final ObjectProvider<IKnowledgeGraphSearchService> knowledgeGraphSearchProvider;
	private final ObjectProvider<IAgentConfigDao> agentConfigDaoProvider;
	private final ObjectProvider<IGAgentServiceRuntimeDao> agentServicesDaoProvider;

	public GAgentsNetworkDataFlowComponent(@Autowired ObjectProvider<IAgentsNetworkDao> agentsNetworkDaoProvider,
			@Autowired ObjectProvider<ISearchServiceRepositoryPattern> searchServicesProvider,
			@Autowired ObjectProvider<IGeboSystemUserService> systemUserServiceProvider,
			@Autowired ObjectProvider<IGToolCallbackSourceRepositoryPattern> toolSourcesProvider,
			@Autowired ObjectProvider<IGChatModelRuntimeConfigurationDao> chatModelsDaoProvider,
			@Autowired ObjectProvider<IGEmbeddingModelRuntimeConfigurationDao> embeddingModelsDaoProvider,
			@Autowired ObjectProvider<IGRankerModelRuntimeConfigurationDao> rankerModelsDaoProvider,
			@Autowired ObjectProvider<IGFullTextSearchDocumentsCachedDao> fullTextSearchProvider,
			@Autowired ObjectProvider<IKnowledgeGraphSearchService> knowledgeGraphSearchProvider,
			@Autowired ObjectProvider<IAgentConfigDao> agentConfigDaoProvider,
			@Autowired ObjectProvider<IGAgentServiceRuntimeDao> agentServicesDaoProvider) {
		this.agentsNetworkDaoProvider = agentsNetworkDaoProvider;
		this.searchServicesProvider = searchServicesProvider;
		this.systemUserServiceProvider = systemUserServiceProvider;
		this.toolSourcesProvider = toolSourcesProvider;
		this.chatModelsDaoProvider = chatModelsDaoProvider;
		this.embeddingModelsDaoProvider = embeddingModelsDaoProvider;
		this.rankerModelsDaoProvider = rankerModelsDaoProvider;
		this.fullTextSearchProvider = fullTextSearchProvider;
		this.knowledgeGraphSearchProvider = knowledgeGraphSearchProvider;
		this.agentConfigDaoProvider = agentConfigDaoProvider;
		this.agentServicesDaoProvider = agentServicesDaoProvider;
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
		flow.setDescription("Agents networks");

		// every enabled search service: the searcher agents search them
		final List<ISearchService> searchServices = GSearchSourcesDataFlowComponent
				.searchableServices(searchServicesProvider.getIfAvailable());
		// the stores the knowledge-base search reads here: the vector store always, the
		// full-text index and the knowledge graph only when deployed
		final KnowledgeBaseSearchLegs legs = KnowledgeBaseSearchLegs.deployed(fullTextSearchProvider,
				knowledgeGraphSearchProvider);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Knowledge base search legs of this installation:" + legs + ", enabled search service(s):"
					+ searchServices.size());
		}
		// what each registered tool reaches, by tool name
		final Map<String, List<ToolDataFlowTarget>> toolTargets = toolsDataFlowTargets();
		final IAgentConfigDao agentConfigs = agentConfigDaoProvider.getIfAvailable();
		final IGAgentServiceRuntimeDao agentServices = agentServicesDaoProvider.getIfAvailable();

		for (GAgentsNetwork network : networks) {
			if (network == null || network.getCode() == null) {
				continue;
			}
			String code = network.getCode();

			DataEndpoint query = new DataEndpoint();
			query.setId("network-query-" + code);
			query.setDescription("Agent-network query - '" + code + "'");
			query.setProduct("Network of agents");
			query.setEndpoint("agents-network", code, null, null);
			query.setInput(true);
			query.setTypes(list(MetaEndpointType.CHAT_SESSION));
			query.setPersonalData(false);
			query.setLocality(DataEndpointLocality.LOCAL_DEPLOYMENT);
			// who may use the network: checked as an EXECUTE when it is offered as an MCP
			// tool (filterCanDoAction), so its ACL entries in the ACL model
			DataEndpointAccess access = DataEndpointAccess
					.of(network, "Network of agents '" + code + "'", "Using it as an MCP tool",
							DataEndpointAccess.Mechanism.CONTENT)
					.withAclAliases(network.getAclAliases());
			access.setGrant("EXECUTE");
			query.setAccess(new ArrayList<DataEndpointAccess>(List.of(access)));
			flow.getDataEndpoints().add(query);

			// the members' configurations and tools are read under the platform's system
			// identity, as the network is assembled outside any user request
			final String queryId = flow.qualifiedId(query.getId());
			asSystem(() -> {
				reportNetwork(flow, network, queryId, searchServices, legs, toolTargets, agentConfigs, agentServices);
				return null;
			});
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End getDataFlowMetaInfos() reporting " + flow.getDataEndpoints().size() + " endpoint(s) and "
					+ flow.getTransformations().size() + " transformation(s)");
		}
		return flow.getDataEndpoints().isEmpty() ? null : flow;
	}

	/**
	 * One network, member by member, as described on the class: the members' chat
	 * models, the searches of the searcher members, the targets of the tools the
	 * members mount (one step per target, with the tools reaching it) and the network
	 * an adapter member hands its requests to.
	 */
	void reportNetwork(GDataFlowMetaInfos flow, GAgentsNetwork network, String queryId,
			List<ISearchService> searchServices, KnowledgeBaseSearchLegs legs,
			Map<String, List<ToolDataFlowTarget>> toolTargets, IAgentConfigDao agentConfigs,
			IGAgentServiceRuntimeDao agentServices) {
		final String code = network.getCode();
		if (network.getAgents() == null || network.getAgents().isEmpty() || agentConfigs == null
				|| agentServices == null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Network:" + code + " has no member, or the agent configurations/services are not available:"
						+ " no member reported");
			}
			return;
		}
		final IGChatModelRuntimeConfigurationDao chatModelsDao = chatModelsDaoProvider.getIfAvailable();
		// endpoint reached by the members' tools -> the target, what is done there and
		// the tools reaching it, in order
		final Map<String, ResolvedTarget> targets = new LinkedHashMap<>();
		final Map<String, String> descriptions = new LinkedHashMap<>();
		final Map<String, List<String>> reachingTools = new LinkedHashMap<>();
		int members = 0;
		for (AgentNetworkParticipant participant : network.getAgents()) {
			if (participant == null || participant.getAgentConfigCode() == null) {
				continue;
			}
			final String member = participant.getNetworkAgentName();
			final GAgentConfig config = agentConfigs.findByCode(participant.getAgentConfigCode());
			if (config == null) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Network:" + code + " member:" + member + " has no configuration "
							+ participant.getAgentConfigCode() + ", not reported");
				}
				continue;
			}
			members++;
			final String key = code + "-" + member;
			if (config.getAgentType() == GAgentConfig.AgentType.AGENTS_NETWORK
					&& config.getAdaptedAgentNetworkCode() != null) {
				// the adapted network is reported as a network of its own
				final String adaptedId = flow.qualifiedId("network-query-" + config.getAdaptedAgentNetworkCode());
				link(flow, "delegation", key,
						"Agent " + member + ": requests handed to the network '" + config.getAdaptedAgentNetworkCode() + "'",
						MetaEndpointType.CHAT_SESSION, MetaEndpointType.CHAT_SESSION, queryId, adaptedId);
				link(flow, "delegation-answer", key,
						"Agent " + member + ": answers of the network '" + config.getAdaptedAgentNetworkCode() + "'",
						MetaEndpointType.CHAT_SESSION, MetaEndpointType.CHAT_SESSION, adaptedId, queryId);
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Network:" + code + " member:" + member + " adapts the network:"
							+ config.getAdaptedAgentNetworkCode());
				}
				continue;
			}
			final IGGenericAgentService service = config.getAgentServiceId() != null
					? agentServices.findByCode(config.getAgentServiceId())
					: null;
			if (service == null) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Network:" + code + " member:" + member + " agent service " + config.getAgentServiceId()
							+ " is not available here, not reported");
				}
				continue;
			}
			DataEndpoint model = null;
			if (service.isCallingChatModel()) {
				model = DataFlowEndpoints.chatModel("agent", service.resolveAgentChatModel(config), "Agent chat model");
				if (model != null) {
					addUnique(flow, model);
					processed(flow, "agent-model", key, "Agent " + member + ": the network's messages to its chat model",
							MetaEndpointType.CHAT_SESSION, MetaEndpointType.LLM_ENDPOINT, queryId,
							flow.qualifiedId(model.getId()));
				}
			}
			reportSearch(flow, member, key, queryId, config.getAgentServiceId(), searchServices, legs);
			if (!participant.isCanCallTools()) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Network:" + code + " member:" + member + " is not allowed to call tools");
				}
				continue;
			}
			final AgentMountedTools mounted = service.getMountedTools(config);
			final List<AgentMountedTools.MountedTool> tools = mounted != null && mounted.getTools() != null
					? mounted.getTools()
					: List.of();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Network:" + code + " member:" + member + " service:" + config.getAgentServiceId()
						+ " model:" + (model != null ? model.getId() : null) + " mounts " + tools.size() + " tool(s)");
			}
			for (AgentMountedTools.MountedTool tool : tools) {
				final List<ToolDataFlowTarget> declared = tool != null ? toolTargets.get(tool.getName()) : null;
				if (declared == null) {
					continue;
				}
				for (ToolDataFlowTarget target : declared) {
					final List<ResolvedTarget> resolvedTargets = resolve(flow, target, chatModelsDao, legs, searchServices);
					if (resolvedTargets.isEmpty() && LOGGER.isDebugEnabled()) {
						LOGGER.debug("Network:" + code + " tool:" + tool.getName() + " target " + target.kind() + " ("
								+ target.reference() + ") is not configured here, not reported");
					}
					for (ResolvedTarget resolved : resolvedTargets) {
					targets.putIfAbsent(resolved.qualifiedId(), resolved);
					// what is done there, the same for every tool reaching it (each tool's own
					// wording is its source's, shown at TRACE)
					descriptions.putIfAbsent(resolved.qualifiedId(), whatIsDoneAt(target.kind()));
					if (LOGGER.isTraceEnabled()) {
						LOGGER.trace("Network:" + code + " member:" + member + " tool:" + tool.getName() + " -> "
								+ resolved.qualifiedId() + " : " + target.description());
					}
					final List<String> reaching = reachingTools.computeIfAbsent(resolved.qualifiedId(),
							k -> new ArrayList<>());
					if (!reaching.contains(tool.getName())) {
						reaching.add(tool.getName());
					}
					}
				}
			}
		}
		int index = 0;
		for (Map.Entry<String, List<String>> reached : reachingTools.entrySet()) {
			final ResolvedTarget target = targets.get(reached.getKey());
			final String description = descriptions.get(reached.getKey()) + " (tools: "
					+ String.join(", ", reached.getValue()) + ")";
			final String key = code + "-" + (index++);
			if (target.writes()) {
				// what the network's models write as the tool's arguments reaches the target
				processed(flow, "tool", key, description, MetaEndpointType.CHAT_SESSION, target.type(), queryId,
						reached.getKey());
			}
			if (target.reads()) {
				// what the tool reads reaches the network
				link(flow, "tool-read", key, description, target.type(), MetaEndpointType.CHAT_SESSION, reached.getKey(),
						queryId);
			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Reported network:" + code + " with " + members + " member(s) and " + reachingTools.size()
					+ " endpoint(s) reached by their tools");
		}
	}

	/**
	 * What a searcher member searches. The knowledge-base searcher
	 * ({@link InternalKnowledgeBaseSearchNetworkAgentService}, through the
	 * knowledge-base search) reads the knowledge stores deployed here, embedding the
	 * queries it writes with the default embedding model. A search-service searcher -
	 * its service id is the search service's product plus the searcher kind, as
	 * {@code StandardAgentsInitialization} declares it - writes its queries to its
	 * search service, and the documents found are chunked for the request
	 * ({@code SearchResultsChunker}). Both rank what they find when a ranker is
	 * configured ({@code GAbstractStandardDocumentsSearchAgentService}).
	 */
	private void reportSearch(GDataFlowMetaInfos flow, String member, String key, String queryId, String serviceId,
			List<ISearchService> searchServices, KnowledgeBaseSearchLegs legs) {
		if (serviceId == null) {
			return;
		}
		if (InternalKnowledgeBaseSearchNetworkAgentService.INTERNAL_KNOWLEDGE_BASE_SEARCHER.equals(serviceId)) {
			link(flow, "kb-semantic", key, "Agent " + member + ": semantic knowledge-base search",
					MetaEndpointType.VECTORIAL_DATABASE, MetaEndpointType.CHAT_SESSION, DataFlowEndpoints.vectorStoreRef(),
					queryId);
			if (legs.fullText()) {
				link(flow, "kb-fulltext", key, "Agent " + member + ": full-text knowledge-base search",
						MetaEndpointType.FULLTEXT_INDEX, MetaEndpointType.CHAT_SESSION, DataFlowEndpoints.fullTextIndexRef(),
						queryId);
			}
			if (legs.graph()) {
				link(flow, "kb-graph", key, "Agent " + member + ": knowledge-graph knowledge-base search",
						MetaEndpointType.GRAPH_DATABASE, MetaEndpointType.CHAT_SESSION,
						DataFlowEndpoints.knowledgeGraphRef(), queryId);
			}
			final DataEndpoint embedding = DataFlowEndpoints.embeddingModel(defaultEmbeddingModel());
			if (embedding != null) {
				addUnique(flow, embedding);
				processed(flow, "kb-embed", key, "Agent " + member + ": search queries embedded", MetaEndpointType.CHAT_SESSION,
						MetaEndpointType.LLM_ENDPOINT, queryId, flow.qualifiedId(embedding.getId()));
			}
			reportRanking(flow, member, key, queryId);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Member:" + member + " searches the knowledge bases (legs:" + legs + ")");
			}
			return;
		}
		for (ISearchService service : searchServices) {
			if (!serviceId.equals(searcherServiceId(service))) {
				continue;
			}
			int index = 0;
			for (GSearchSourcesDataFlowComponent.SearchSource source : GSearchSourcesDataFlowComponent.sourcesOf(service)) {
				final String sourceKey = key + "-" + (index++);
				processed(flow, "search", sourceKey, "Agent " + member + ": search queries written to " + service.getId(),
						MetaEndpointType.CHAT_SESSION, source.type(), queryId, source.qualifiedId());
				link(flow, "search-read", sourceKey, "Agent " + member + ": documents found by " + service.getId() + " read",
						source.type(), MetaEndpointType.CHAT_SESSION, source.qualifiedId(), queryId);
				link(flow, "search-chunking", sourceKey, "Agent " + member + ": documents found chunked for the request",
						source.type(), MetaEndpointType.CHUNK, source.qualifiedId(), GStandardDataFlowEndpoints.chunkCacheRef());
			}
			reportRanking(flow, member, key, queryId);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Member:" + member + " searches the search service:" + service.getId());
			}
			return;
		}
	}

	/** The searcher agent service id of a search service, as {@code StandardAgentsInitialization} declares it. */
	static String searcherServiceId(ISearchService service) {
		return service.getProductId() + (service instanceof INativeSearchService
				? NativeDocumentsSearchNetworkAgentService.NATIVE_SEARCHER_AGENT
				: DocumentsSearchNetworkAgentServiceWrapper.SEARCH_AGENT);
	}

	/** The documents a searcher finds, ranked by the ranker when one is configured. */
	private void reportRanking(GDataFlowMetaInfos flow, String member, String key, String queryId) {
		final DataEndpoint ranker = DataFlowEndpoints.rankerModel(rankerModelsDaoProvider.getIfAvailable());
		if (ranker == null) {
			return;
		}
		addUnique(flow, ranker);
		processed(flow, "search-rank", key, "Agent " + member + ": documents found ranked against the query",
				MetaEndpointType.CHAT_SESSION, MetaEndpointType.LLM_ENDPOINT, queryId, flow.qualifiedId(ranker.getId()));
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

	/**
	 * An endpoint a tool reaches: its qualified id in the register, its type, whether
	 * the tool reads it (its content reaches the network) and whether the tool writes to
	 * it what the network's models give the tool as arguments.
	 */
	record ResolvedTarget(String qualifiedId, MetaEndpointType type, boolean reads, boolean writes) {
	}

	/**
	 * The endpoints of a tool's target, added to the flow when this component owns them
	 * (the knowledge stores are the indexers' own, the search sources the search
	 * sources component's); none when the target does not exist here (no such model or
	 * search service configured, or a knowledge store whose search leg this installation
	 * does not deploy: a tool declares every store its search may read, the search reads
	 * only the deployed ones). A search service is reached through each system it
	 * searches: the queries written there, the documents found read.
	 */
	List<ResolvedTarget> resolve(GDataFlowMetaInfos flow, ToolDataFlowTarget target,
			IGChatModelRuntimeConfigurationDao chatModelsDao, KnowledgeBaseSearchLegs legs,
			List<ISearchService> searchServices) {
		if (target == null || target.kind() == null) {
			return List.of();
		}
		switch (target.kind()) {
		case KNOWLEDGE_BASE_VECTOR_STORE:
			return List.of(new ResolvedTarget(DataFlowEndpoints.vectorStoreRef(), MetaEndpointType.VECTORIAL_DATABASE,
					true, false));
		case KNOWLEDGE_BASE_FULLTEXT_INDEX:
			return legs.fullText()
					? List.of(new ResolvedTarget(DataFlowEndpoints.fullTextIndexRef(), MetaEndpointType.FULLTEXT_INDEX,
							true, false))
					: List.of();
		case KNOWLEDGE_BASE_GRAPH_STORE:
			return legs.graph()
					? List.of(new ResolvedTarget(DataFlowEndpoints.knowledgeGraphRef(), MetaEndpointType.GRAPH_DATABASE,
							true, false))
					: List.of();
		case EMBEDDING_MODEL:
			return written(flow, DataFlowEndpoints.embeddingModel(defaultEmbeddingModel()), MetaEndpointType.LLM_ENDPOINT);
		case RANKER_MODEL:
			return written(flow, DataFlowEndpoints.rankerModel(rankerModelsDaoProvider.getIfAvailable()),
					MetaEndpointType.LLM_ENDPOINT);
		case SERVICE_MODEL:
			return written(flow, DataFlowEndpoints.chatModel("service", DataFlowEndpoints.utilityModel(chatModelsDao),
					"Internal services model"), MetaEndpointType.LLM_ENDPOINT);
		case SEARCH_SERVICE: {
			final List<ResolvedTarget> out = new ArrayList<>();
			for (ISearchService service : searchServices) {
				if (service.getId() != null && service.getId().equals(target.reference())) {
					for (GSearchSourcesDataFlowComponent.SearchSource source : GSearchSourcesDataFlowComponent
							.sourcesOf(service)) {
						out.add(new ResolvedTarget(source.qualifiedId(), source.type(), true, true));
					}
				}
			}
			return out;
		}
		case INTERNET:
			return written(flow, internetEndpoint(), MetaEndpointType.WEB_SEARCH);
		case MCP_SERVER:
			return written(flow, mcpServerEndpoint(target), MetaEndpointType.WEB_SEARCH);
		case PLATFORM_DATA: {
			// the platform's own data are read, nothing is written there
			final DataEndpoint platform = platformDataEndpoint(target);
			DataFlowEndpoints.addUnique(flow, platform);
			return List.of(new ResolvedTarget(flow.qualifiedId(platform.getId()), MetaEndpointType.DATABASE, true, false));
		}
		default:
			return List.of();
		}
	}

	/** An endpoint this component owns that the tools write to, none when it is not configured. */
	private static List<ResolvedTarget> written(GDataFlowMetaInfos flow, DataEndpoint endpoint, MetaEndpointType type) {
		if (endpoint == null) {
			return List.of();
		}
		DataFlowEndpoints.addUnique(flow, endpoint);
		return List.of(new ResolvedTarget(flow.qualifiedId(endpoint.getId()), type, false, true));
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
		endpoint.setPersonalData(false);
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
		endpoint.setPersonalData(false);
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

	private IGConfigurableEmbeddingModel defaultEmbeddingModel() {
		final IGEmbeddingModelRuntimeConfigurationDao embeddingModelsDao = embeddingModelsDaoProvider.getIfAvailable();
		try {
			return embeddingModelsDao != null ? embeddingModelsDao.defaultHandler() : null;
		} catch (RuntimeException e) {
			LOGGER.warn("Cannot read the default embedding model for the data flow register", e);
			return null;
		}
	}

	private void link(GDataFlowMetaInfos flow, String kind, String key, String description, MetaEndpointType from,
			MetaEndpointType to, String sourceQualifiedId, String destQualifiedId) {
		add(flow, kind, key, description, from, to, sourceQualifiedId, destQualifiedId, DataTransformationInfo.Carried.CONTENT);
	}

	/**
	 * A step whose destination processes what the network gives it and passes it to no
	 * one else: a member's chat model, a model, a search source or a tool target given
	 * the arguments the members' models write.
	 */
	private void processed(GDataFlowMetaInfos flow, String kind, String key, String description, MetaEndpointType from,
			MetaEndpointType to, String sourceQualifiedId, String destQualifiedId) {
		add(flow, kind, key, description, from, to, sourceQualifiedId, destQualifiedId,
				DataTransformationInfo.Carried.PROCESSED);
	}

	private void add(GDataFlowMetaInfos flow, String kind, String key, String description, MetaEndpointType from,
			MetaEndpointType to, String sourceQualifiedId, String destQualifiedId, DataTransformationInfo.Carried carried) {
		DataTransformationMetaInfo engine = DataTransformationMetaInfo.of(kind + "-" + key, description, list(from),
				list(to));
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("Data flow link " + kind + "-" + key + " : " + sourceQualifiedId + " -> " + destQualifiedId
					+ " (" + from + " -> " + to + ", " + carried + ")");
		}
		flow.getEngines().add(engine);
		DataTransformationInfo step = DataTransformationInfo.of(kind + "-flow-" + key, description, engine,
				sourceQualifiedId, destQualifiedId);
		step.setCarried(carried);
		flow.getTransformations().add(step);
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
