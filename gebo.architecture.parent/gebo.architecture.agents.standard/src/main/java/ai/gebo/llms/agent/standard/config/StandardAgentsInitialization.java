package ai.gebo.llms.agent.standard.config;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBooleanProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.services.IAgentConfigDao;
import ai.gebo.architecture.agents.services.IAgentRoleDao;
import ai.gebo.architecture.agents.services.IDynamicAgentsNetworkDataSource;
import ai.gebo.architecture.agents.services.IGAgentsNetworkServiceFactory;
import ai.gebo.architecture.agents.services.IGAgentsNetworkServiceFactoryRepositoryPattern;
import ai.gebo.architecture.agents.services.IGDynamicAgentConfigDataSource;
import ai.gebo.architecture.agents.services.IGDynamicAgentServiceSupplier;
import ai.gebo.architecture.agents.services.IGGenericAgentService;
import ai.gebo.architecture.agents.services.impl.TextProcessingTaskPerformerAgentService;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.service.IGDocumentContentRendererProvider;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.architecture.search.service.INativeSearchService;
import ai.gebo.architecture.search.service.ISearchService;
import ai.gebo.architecture.search.service.ISearchServiceRepositoryPattern;
import ai.gebo.core.contents.security.services.IGKnowledgebaseVisibilityService;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.agent.chat.service.IGReactiveChatAgentsNetworkService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.services.IGDocumentsSearchService;
import ai.gebo.llms.chat.abstraction.layer.services.IGRankerService;
import ai.gebo.llms.agent.chat.service.impl.ReportWriterReactiveAgentServiceImpl;
import ai.gebo.llms.agent.chat.service.impl.ReactiveChatAgentsNetworkStreamingOutputChatPipelineService;
import ai.gebo.llms.agent.standard.services.ChatRuntimeDataQueryAdapterAgentService;
import ai.gebo.llms.agent.standard.services.DefaultControllerNetworkAgentService;
import ai.gebo.llms.agent.standard.services.DocumentsSearchNetworkAgentServiceWrapper;
import ai.gebo.llms.agent.standard.services.IGInternalKnowledgeBaseDocumentsSearchNetworkAgentService;
import ai.gebo.llms.agent.standard.services.InternalKnowledgeBaseSearchNetworkAgentService;
import ai.gebo.llms.agent.standard.services.NativeDocumentsSearchNetworkAgentService;
import ai.gebo.llms.agent.standard.services.SearchAgentPromptPatcher;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.service.IStreamingOutputChatPipelineService;
import ai.gebo.llms.deepsearch.service.IGExternalSearchSecurityService;
import ai.gebo.security.services.IGSecurityService;

@ConditionalOnProperty(prefix = "ai.gebo.agents.standard", name = "enabled", havingValue = "true")
@Configuration
public class StandardAgentsInitialization {
	private static final String INITIALIZING_STANDARD_AGENTS_NETWORK_FOR_REACTIVE_CHAT = "*             Initializing standard agents network for reactive chat                 *";
	private static final String START_ROW = "**************************************************************************************";
	private static final String SEARCH_AGENT_INTERNALKB_SERVICE_DESCRIPTION = "Search agent service specialized in Internal Knowledge Base Searches";
	public static final String INTERNAL_KNOWLEDGEBASE_AGENT_CODE = "internalKnowledgeBaseSearchAgent";
	public static final String INTERNAL_KNOWLEDGE_BASE_SEARCH_QUALIFIER = "internalKnowledgeBaseSearch";
	private static final String REPORTER_AGENT_DESCRIPTION = "Reporter agent that evaluates controller's initiatives, eventually present evidences/documents and user question to create the fittest answer";
	private static final String CONTROLLER_AND_COORDINATOR_DESCRIPTION = "Agent's network controller and coordinator";
	private static final String INPUT_ADAPTER_DESCRIPTION = "Input adapter extracting the user query from the chat runtime data";
	private static final String EVIDENCES_SEARCHER_AGENT = "EVIDENCES_SEARCHER_AGENT";
	private static final String REPORT_WRITER_AGENT = "REPORT_WRITER_AGENT";
	private static final String SUPERVISOR_AGENT = "SUPERVISOR_AGENT";
	private static final String DEFAULT_NETWORK_SCENARIO_DESCRIPTION = "The network of agent is meant to try to delivery the best answer and interaction to user's questions with a leader controller node controlling if the quality of the network output is ok.\r\n The controller agent is comunicating with one or more searching agents to supply evidences on an evidence analyzer node that responds.\r\n";
	private static final String DEFAULT_AGENTS_NETWORK_FOR_CHAT_PURPOSES = "Default agents network for chat purposes";
	private static final String DEFAULT_AGENTS_NETWORK = "DEFAULT_AGENTS_NETWORK";
	private final static Logger LOGGER = LoggerFactory.getLogger(StandardAgentsInitialization.class);
	private final ISearchServiceRepositoryPattern searchServicesRepositoryPattern;
	private final IGChatModelRuntimeConfigurationDao chatModelsDao;
	private final IGToolCallbackSourceRepositoryPattern toolsRepositoryPattern;
	private final IGPromptConfigDao promptsDao;
	private final IGRuntimeBinder runtimeBinder;
	private final IGSecurityService securityService;
	private final IAgentRoleDao agentRoleDao;
	private final IDocumentsChunkService chunkingService;
	private final IGRankerService rankerService;
	private final IGDocumentContentRendererProvider rendererFactory;
	private final IGExternalSearchSecurityService  externalSearchSecurityService;
	private final StandardAgentsConfig standardAgentsConfig;

	public StandardAgentsInitialization(ISearchServiceRepositoryPattern searchServicesRepositoryPattern,
			IGToolCallbackSourceRepositoryPattern toolsRepositoryPattern, IGSecurityService securityService,
			IDocumentsChunkService chunkingService, IGRankerService rankerService, IGPromptConfigDao promptsDao,
			IGChatModelRuntimeConfigurationDao chatModelsDao, IAgentRoleDao agentRoleDao, IGRuntimeBinder runtimeBinder,
			IGDocumentContentRendererProvider rendererFactory, IGExternalSearchSecurityService externalSearchSecurityService,
			StandardAgentsConfig standardAgentsConfig) {
		this.searchServicesRepositoryPattern = searchServicesRepositoryPattern;
		this.chatModelsDao = chatModelsDao;
		this.toolsRepositoryPattern = toolsRepositoryPattern;
		this.promptsDao = promptsDao;
		this.runtimeBinder = runtimeBinder;
		this.securityService = securityService;
		this.agentRoleDao = agentRoleDao;
		this.chunkingService = chunkingService;
		this.rankerService = rankerService;
		this.rendererFactory = rendererFactory;
		this.externalSearchSecurityService = externalSearchSecurityService;
		this.standardAgentsConfig = standardAgentsConfig;
		LOGGER.info(START_ROW);
		LOGGER.info(INITIALIZING_STANDARD_AGENTS_NETWORK_FOR_REACTIVE_CHAT);
		LOGGER.info(START_ROW);
	}

	@Bean
	@Qualifier(IStreamingOutputChatPipelineService.DEFAULT_PIPELINE_SERVICE)
	public IStreamingOutputChatPipelineService defaultStreamingOutputPipelineService(
			@Autowired @Qualifier(IDynamicAgentsNetworkDataSource.DEFAULT_CHAT_AGENTS_NETWORK_QUALIFIER) IDynamicAgentsNetworkDataSource networkDataSource,
			IGAgentsNetworkServiceFactoryRepositoryPattern agentsNetworkServiceFactory, IGChatSessionLifeCycleService lifeCycleService) {
		IGAgentsNetworkServiceFactory<ChatPipelineExecutionRuntimeData, GeboChatMessageEnvelope, IGReactiveChatAgentsNetworkService> factory = agentsNetworkServiceFactory
				.getFactory(IGReactiveChatAgentsNetworkService.class);

		return new ReactiveChatAgentsNetworkStreamingOutputChatPipelineService(factory, networkDataSource, lifeCycleService);
	}

	@Bean
	@Qualifier(IDynamicAgentsNetworkDataSource.DEFAULT_CHAT_AGENTS_NETWORK_QUALIFIER)
	public IDynamicAgentsNetworkDataSource defaultAgentsNetworkDataSource(
			@Autowired(required = false) @Qualifier(INTERNAL_KNOWLEDGE_BASE_SEARCH_QUALIFIER) IGDynamicAgentConfigDataSource internalKnowledgebaseAgentConfigDataSource) {
		return new IDynamicAgentsNetworkDataSource() {

			@Override
			public List<GAgentsNetwork> getConfigurations() {

				return List.of(createDefaultAgentsNetwork(internalKnowledgebaseAgentConfigDataSource));
			}
		};
	}

	@ConditionalOnMissingBean(IGInternalKnowledgeBaseDocumentsSearchNetworkAgentService.class)
	@Bean
	@Qualifier(INTERNAL_KNOWLEDGE_BASE_SEARCH_QUALIFIER)
	public IGDynamicAgentConfigDataSource defaultInternalKnowledgeBaseSearch() {
		final GAgentConfig internalKnowledgeBaseAgentConfig = new GAgentConfig();
		internalKnowledgeBaseAgentConfig.setCode(INTERNAL_KNOWLEDGEBASE_AGENT_CODE);
		internalKnowledgeBaseAgentConfig.setDescription(SEARCH_AGENT_INTERNALKB_SERVICE_DESCRIPTION);
		internalKnowledgeBaseAgentConfig.setAgentRoleCode(EVIDENCES_SEARCHER_AGENT);
		internalKnowledgeBaseAgentConfig.setAccessibleToAll(true);
		internalKnowledgeBaseAgentConfig
				.setAgentServiceId(InternalKnowledgeBaseSearchNetworkAgentService.INTERNAL_KNOWLEDGE_BASE_SEARCHER);
		// The internal-KB searcher reuses the standard RAG search-planner prompt,
		// runtime-patched with the agent network placeholders (same pattern as the
		// external searchers).
		internalKnowledgeBaseAgentConfig.setCustomLoopPrompt(SearchAgentPromptPatcher.withAgentPlaceholders(promptsDao
				.findByPromptUse(InternalKnowledgeBaseSearchNetworkAgentService.SEARCH_PLANNER_PROMPT_USE_CODE)));
		internalKnowledgeBaseAgentConfig.setUseDefaultChatModel(true);
		internalKnowledgeBaseAgentConfig.setEnabledFunctions(List.of());
		internalKnowledgeBaseAgentConfig.setSubscribeAllTools(false);
		return new IGDynamicAgentConfigDataSource() {

			@Override
			public List<GAgentConfig> getConfigurations() {

				return List.of(internalKnowledgeBaseAgentConfig);

			}
		};
	}

	@ConditionalOnMissingBean(IGInternalKnowledgeBaseDocumentsSearchNetworkAgentService.class)
	@Bean
	@Qualifier(INTERNAL_KNOWLEDGE_BASE_SEARCH_QUALIFIER)
	public IGInternalKnowledgeBaseDocumentsSearchNetworkAgentService defaultInternalKnowledgeBaseSearchAgentService(
			IGDocumentsSearchService documentsSearchService,
			IGKnowledgebaseVisibilityService knowledgeBaseVisibilityService) {
		return new InternalKnowledgeBaseSearchNetworkAgentService(chatModelsDao, toolsRepositoryPattern, promptsDao,
				securityService, agentRoleDao, runtimeBinder, rendererFactory, rankerService,
				documentsSearchService, knowledgeBaseVisibilityService);

	}

	private GAgentsNetwork createDefaultAgentsNetwork(
			IGDynamicAgentConfigDataSource internalKnowledgebaseAgentConfigDataSource) {
		GAgentsNetwork network = new GAgentsNetwork();
		network.setCode(DEFAULT_AGENTS_NETWORK);
		network.setDescription(DEFAULT_AGENTS_NETWORK_FOR_CHAT_PURPOSES);
		network.setReadOnly(true);
		network.setDefaultUserInteractionNetwork(true);
		// Global safety backstop on TOTAL agent invocations (the controller's own cycle
		// budget below is the real terminator); sized so a legitimate multi-cycle
		// gather/finalize run with a wide searcher fan-out is never truncated.
		network.setMaxLoopIteration(60);
		network.setScenarioDescription(DEFAULT_NETWORK_SCENARIO_DESCRIPTION);
		GAgentConfig controller = defaultControllerAgentConfigDataSource().getConfigurations().get(0);
		List<GAgentConfig> dataSources = externalSourcesAgentConfigDataSource().getConfigurations();
		GAgentConfig reportWriter = defaultReportWriterConfigDataSource().getConfigurations().get(0);
		List<String> coordinatedAgentCodes = Stream.concat(dataSources.stream(), Stream.of(reportWriter))
				.map(x -> x.getCode()).toList();
		GAgentConfig internalKnowledgeBaseConfig = null;
		if (internalKnowledgebaseAgentConfigDataSource != null) {
			List<GAgentConfig> internalKBSearchAgentConfigs = internalKnowledgebaseAgentConfigDataSource
					.getConfigurations();
			if (!internalKBSearchAgentConfigs.isEmpty()) {
				internalKnowledgeBaseConfig = internalKBSearchAgentConfigs.get(0);
				List<String> fullSearchersList = new ArrayList<String>();
				fullSearchersList.add(internalKnowledgeBaseConfig.getCode());
				fullSearchersList.addAll(coordinatedAgentCodes);
				coordinatedAgentCodes = fullSearchersList;
			}
		}
		List<AgentNetworkParticipant> participants = new ArrayList<>();
		// Non-LLM input node: adapts ChatPipelineExecutionRuntimeData -> query String
		// and
		// forwards it to the String-input controller.
		GAgentConfig inputAdapter = defaultInputAdapterConfigDataSource().getConfigurations().get(0);
		AgentNetworkParticipant inputAdapterParticipant = new AgentNetworkParticipant();
		inputAdapterParticipant.setAgentConfigCode(inputAdapter.getCode());
		inputAdapterParticipant.setInputNode(true);
		inputAdapterParticipant.setOutputNode(false);
		inputAdapterParticipant.setCommunicationList(List.of(controller.getCode()));
		participants.add(inputAdapterParticipant);
		AgentNetworkParticipant controllerParticipant = new AgentNetworkParticipant();
		controllerParticipant.setAgentConfigCode(controller.getCode());
		controllerParticipant.setInputNode(false);
		// maxInvocations is the controller's cycle budget: the maximum number of
		// gather/finalize cycles it may run before it must deliver the final answer.
		controllerParticipant.setMaxInvocations(5);
		controllerParticipant.setMaxConsecutiveInvocations(5);
		controllerParticipant.setOutputNode(false);
		controllerParticipant.setCommunicationList(coordinatedAgentCodes);
		participants.add(controllerParticipant);
		// Searcher participants: the internal knowledge base searcher (when present)
		// plus
		// every external search-service searcher. Each is reachable from the controller
		// and
		// shares its results with the report writer. The internal-KB searcher must be a
		// participant too; otherwise the controller's communication list references an
		// agent
		// with no runtime allocation and routing fails.
		List<GAgentConfig> searcherConfigs = new ArrayList<>();
		if (internalKnowledgeBaseConfig != null) {
			searcherConfigs.add(internalKnowledgeBaseConfig);
		}
		searcherConfigs.addAll(dataSources);
		for (GAgentConfig searcher : searcherConfigs) {
			AgentNetworkParticipant participant = new AgentNetworkParticipant();
			participant.setAgentConfigCode(searcher.getCode());
			participant.setMaxConsecutiveInvocations(5);
			participant.setMaxInvocations(10);
			participant.setCommunicationList(List.of(reportWriter.getCode()));
			participants.add(participant);
		}
		AgentNetworkParticipant outParticipant = new AgentNetworkParticipant();
		outParticipant.setAgentConfigCode(reportWriter.getCode());
		outParticipant.setCommunicationList(List.of());
		outParticipant.setOutputNode(true);
		outParticipant.setCommunicationList(List.of(controller.getCode()));
		participants.add(outParticipant);
		network.setAgents(participants);
		network.setAccessibleToAll(true);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Assembled default agents network code:" + network.getCode() + " with " + participants.size()
					+ " participant(s); coordinated searchers:" + coordinatedAgentCodes);
		}
		return network;
	}

	@Bean
	public IGDynamicAgentConfigDataSource externalSourcesAgentConfigDataSource() {

		return new IGDynamicAgentConfigDataSource() {

			@Override
			public List<GAgentConfig> getConfigurations() {
				final List<ISearchService> implementations = searchServicesRepositoryPattern.getImplementations();
				final List<GAgentConfig> agentConfigs = new ArrayList<>();
				for (ISearchService search : implementations) {
					try {
						if (!search.isEnabled())
							continue;
						if (!externalSearchSecurityService.isEnabledForCurrentUser(search)) {
							continue;
						}
						String serviceId = search.getProductId() + (search instanceof INativeSearchService
								? NativeDocumentsSearchNetworkAgentService.NATIVE_SEARCHER_AGENT
								: DocumentsSearchNetworkAgentServiceWrapper.SEARCH_AGENT);
						GAgentConfig agentConfig = new GAgentConfig();
						agentConfig.setCode(serviceId);
						agentConfig.setDescription(search.getProductId() + " search agent ");
						// Native searchers generate with their native query template; the others use
						// their query-generation prompt. The chosen template is runtime-patched with
						// the agent network placeholders and stored as the agent's custom loop prompt.
						String searchPromptUseCode = search instanceof INativeSearchService nativeSearch
								? nativeSearch.getNativePromptTemplateUseCode()
								: search.getQueriesGenerationPromptUseCode();
						agentConfig.setCustomLoopPrompt(processSearchPrompt(searchPromptUseCode));
						agentConfig.setAccessibleToAll(true);
						agentConfig.setAgentRoleCode(EVIDENCES_SEARCHER_AGENT);
						agentConfig.setAgentServiceId(serviceId);
						agentConfig.setSubscribeAllTools(false);
						agentConfig.setEnabledFunctions(List.of());
						agentConfig.setUseDefaultChatModel(true);
						agentConfigs.add(agentConfig);
						if (LOGGER.isDebugEnabled()) {
							LOGGER.debug("Registered external search agent config code:" + serviceId);
						}
					} catch (SearchServiceException e) {
						LOGGER.error("Error initializing search agent config", e);
					}
				}
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("externalSourcesAgentConfigDataSource produced " + agentConfigs.size()
							+ " agent config(s)");
				}
				return agentConfigs;
			}
		};
	}

	GAgentConfig controllerConfig = null;
	GAgentConfig reporterConfig = null;
	GAgentConfig inputAdapterConfig = null;

	@Bean
	public IGDynamicAgentConfigDataSource defaultInputAdapterConfigDataSource() {
		if (inputAdapterConfig == null) {
			inputAdapterConfig = new GAgentConfig();
			inputAdapterConfig.setCode(ChatRuntimeDataQueryAdapterAgentService.CHAT_RUNTIME_DATA_QUERY_ADAPTER);
			inputAdapterConfig
					.setAgentServiceId(ChatRuntimeDataQueryAdapterAgentService.CHAT_RUNTIME_DATA_QUERY_ADAPTER);
			inputAdapterConfig.setDescription(INPUT_ADAPTER_DESCRIPTION);
			inputAdapterConfig.setAccessibleToAll(true);
			inputAdapterConfig.setUseDefaultChatModel(false);
		}
		return IGDynamicAgentConfigDataSource.of(inputAdapterConfig);
	}

	@Bean
	public IGDynamicAgentConfigDataSource defaultControllerAgentConfigDataSource() {
		if (controllerConfig == null) {
			controllerConfig = new GAgentConfig();
			controllerConfig.setCode(DefaultControllerNetworkAgentService.CONTROLLER_AGENT);
			controllerConfig.setAgentServiceId(DefaultControllerNetworkAgentService.CONTROLLER_AGENT);
			controllerConfig.setMainLoopPromptUseCode(StandardAgentsPromptsLibraryConfig.COORDINATOR_AGENT_PROMPT);
			controllerConfig.setDescription(CONTROLLER_AND_COORDINATOR_DESCRIPTION);
			controllerConfig.setUseDefaultChatModel(true);
			controllerConfig.setAgentRoleCode(SUPERVISOR_AGENT);
		}
		return IGDynamicAgentConfigDataSource.of(controllerConfig);
	}

	@Bean
	public IGDynamicAgentConfigDataSource defaultReportWriterConfigDataSource() {
		if (reporterConfig == null) {
			reporterConfig = new GAgentConfig();
			reporterConfig.setCode(ReportWriterReactiveAgentServiceImpl.REPORT_WRITER_NETWORK_AGENT_SERVICE);
			reporterConfig.setAgentServiceId(ReportWriterReactiveAgentServiceImpl.REPORT_WRITER_NETWORK_AGENT_SERVICE);
			reporterConfig
					.setMainLoopPromptUseCode(StandardAgentsPromptsLibraryConfig.REPORT_AND_ANSWER_WRITER_AGENT_PROMPT);
			reporterConfig.setDescription(REPORTER_AGENT_DESCRIPTION);
			reporterConfig.setAgentRoleCode(REPORT_WRITER_AGENT);
		}
		return IGDynamicAgentConfigDataSource.of(reporterConfig);
	}

	private GPromptTemplateConfig processSearchPrompt(String searchPromptUseCode) {
		GPromptTemplateConfig prompt = promptsDao.findByPromptUse(searchPromptUseCode);
		return SearchAgentPromptPatcher.withAgentPlaceholders(prompt);
	}

	@Bean
	public IGDynamicAgentServiceSupplier externalSourcesAgentServicesSupplier() {
		return new IGDynamicAgentServiceSupplier() {

			@Override
			public List<IGGenericAgentService> get() {
				List<IGGenericAgentService> outServices = new ArrayList<>();
				final List<ISearchService> implementations = searchServicesRepositoryPattern.getImplementations();
				for (ISearchService search : implementations) {
					try {
						if (!search.isEnabled())
							continue;

						if (search instanceof INativeSearchService nativeSearch) {
							NativeDocumentsSearchNetworkAgentService nativeWrapper = new NativeDocumentsSearchNetworkAgentService(
									chatModelsDao, toolsRepositoryPattern, promptsDao, securityService, agentRoleDao,
									runtimeBinder, rendererFactory, chunkingService, rankerService,
									standardAgentsConfig.getMaxChunksPerDocument(), nativeSearch);
							outServices.add(nativeWrapper);
							if (LOGGER.isDebugEnabled()) {
								LOGGER.debug("Registered native search agent service id:" + nativeWrapper.getId());
							}
						} else {
							DocumentsSearchNetworkAgentServiceWrapper wrapper = new DocumentsSearchNetworkAgentServiceWrapper(
									chatModelsDao, toolsRepositoryPattern, promptsDao, securityService, agentRoleDao,
									runtimeBinder, rendererFactory, chunkingService, rankerService,
									standardAgentsConfig.getMaxChunksPerDocument(), search);
							outServices.add(wrapper);
							if (LOGGER.isDebugEnabled()) {
								LOGGER.debug("Registered search agent service id:" + wrapper.getId());
							}
						}
					} catch (SearchServiceException e) {
						LOGGER.error("Error initializing search agent", e);
					}
				}
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("externalSourcesAgentServicesSupplier produced " + outServices.size() + " service(s)");
				}
				return outServices;
			}

		};

	}
}
