package ai.gebo.llms.agent.standard.config;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentActivationType;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.services.IAgentConfigDao;
import ai.gebo.architecture.agents.services.IAgentRoleDao;
import ai.gebo.architecture.agents.services.IDynamicAgentsNetworkDataSource;
import ai.gebo.architecture.agents.services.IGDynamicAgentConfigDataSource;
import ai.gebo.architecture.agents.services.IGDynamicAgentServiceSupplier;
import ai.gebo.architecture.agents.services.IGGenericAgentService;
import ai.gebo.architecture.agents.services.impl.DefaultControllerNetworkAgentService;
import ai.gebo.architecture.agents.services.impl.TextProcessingTaskPerformerAgentService;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.architecture.search.service.INativeSearchService;
import ai.gebo.architecture.search.service.ISearchService;
import ai.gebo.architecture.search.service.ISearchServiceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGRankerModelRuntimeConfigurationDao;
import ai.gebo.llms.agent.standard.DocumentsSearchNetworkAgentServiceWrapper;
import ai.gebo.llms.agent.standard.NativeDocumentsSearchNetworkAgentService;
import ai.gebo.security.services.IGSecurityService;
import lombok.AllArgsConstructor;
import lombok.Data;

@Configuration
@ConfigurationProperties(value = "ai.gebo.agents.standard")

@AllArgsConstructor
public class StandardAgentsAndNetworksConfig {
	private static final String REPORTER_AGENT_DESCRIPTION = "Reporter agent that evaluates controller's initiatives, eventually present evidences/documents and user question to create the fittest answer";
	private static final String CONTROLLER_AND_COORDINATOR_DESCRIPTION = "Agent's network controller and coordinator";
	private static final String EVIDENCES_SEARCHER_AGENT = "EVIDENCES_SEARCHER_AGENT";
	private static final String REPORT_WRITER_AGENT = "REPORT_WRITER_AGENT";
	private static final String SUPERVISOR_AGENT = "SUPERVISOR_AGENT";
	private static final String DEFAULT_NETWORK_SCENARIO_DESCRIPTION = "The network of agent is meant to try to delivery the best answer and interaction to user's questions with a leader controller node controlling if the quality of the network output is ok.\r\n The controller agent is comunicating with one or more searching agents to supply evidences on an evidence analyzer node that responds.\r\n";
	private static final String DEFAULT_AGENTS_NETWORK_FOR_CHAT_PURPOSES = "Default agents network for chat purposes";
	private static final String DEFAULT_AGENTS_NETWORK = "DEFAULT_AGENTS_NETWORK";
	private final static Logger LOGGER = LoggerFactory.getLogger(StandardAgentsAndNetworksConfig.class);
	private final ISearchServiceRepositoryPattern searchServicesRepositoryPattern;
	private final IGChatModelRuntimeConfigurationDao chatModelsDao;
	private final IGToolCallbackSourceRepositoryPattern toolsRepositoryPattern;
	private final IGPromptConfigDao promptsDao;
	private final IAgentConfigDao configsRepository;
	private final IGSecurityService securityService;
	private final IAgentRoleDao agentRoleDao;
	private final IGRankerModelRuntimeConfigurationDao rankersDao;

	@Bean
	public IDynamicAgentsNetworkDataSource defaultAgentsNetworkDataSource() {
		return new IDynamicAgentsNetworkDataSource() {

			@Override
			public List<GAgentsNetwork> getConfigurations() {

				return List.of(createDefaultAgentsNetwork());
			}
		};
	}

	private GAgentsNetwork createDefaultAgentsNetwork() {
		GAgentsNetwork network = new GAgentsNetwork();
		network.setCode(DEFAULT_AGENTS_NETWORK);
		network.setDescription(DEFAULT_AGENTS_NETWORK_FOR_CHAT_PURPOSES);
		network.setReadOnly(true);
		network.setDefaultUserInteractionNetwork(true);
		network.setMaxLoopIteration(5);
		network.setScenarioDescription(DEFAULT_NETWORK_SCENARIO_DESCRIPTION);
		GAgentConfig controller = defaultControllerAgentConfigDataSource().getConfigurations().get(0);
		List<GAgentConfig> dataSources = externalSourcesAgentConfigDataSource().getConfigurations();
		GAgentConfig reportWriter = defaultReportWriterConfigDataSource().getConfigurations().get(0);
		List<String> coordinatedAgentCodes = Stream.concat(dataSources.stream(), Stream.of(reportWriter))
				.map(x -> x.getCode()).toList();
		List<AgentNetworkParticipant> participants = new ArrayList<>();
		AgentNetworkParticipant controllerParticipant = new AgentNetworkParticipant();
		controllerParticipant.setAgentConfigCode(controller.getCode());
		controllerParticipant.setInputNode(true);
		controllerParticipant.setMaxConsecutiveInvocations(5);
		controllerParticipant.setOutputNode(false);
		controllerParticipant.setCommunicationList(coordinatedAgentCodes);
		participants.add(controllerParticipant);
		for (GAgentConfig searcher : dataSources) {
			AgentNetworkParticipant participant = new AgentNetworkParticipant();
			participant.setAgentConfigCode(searcher.getCode());
			participant.setCommunicationList(List.of());
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
		return network;
	}

	@Bean
	public IGDynamicAgentConfigDataSource externalSourcesAgentConfigDataSource() {

		final List<ISearchService> implementations = searchServicesRepositoryPattern.getImplementations();

		return new IGDynamicAgentConfigDataSource() {

			@Override
			public List<GAgentConfig> getConfigurations() {
				final List<GAgentConfig> agentConfigs = new ArrayList<>();
				for (ISearchService search : implementations) {
					try {
						if (!search.isEnabled())
							continue;
						String serviceId = search.getProductId() + (search instanceof INativeSearchService
								? NativeDocumentsSearchNetworkAgentService.NATIVE_SEARCHER_AGENT
								: DocumentsSearchNetworkAgentServiceWrapper.SEARCH_AGENT);
						GAgentConfig agentConfig = new GAgentConfig();
						agentConfig.setCode(serviceId);
						agentConfig.setDescription(search.getProductId() + " search agent ");
						agentConfig
								.setCustomLoopPrompt(processSearchPrompt(search.getQueriesGenerationPromptUseCode()));
						agentConfig.setAccessibleToAll(true);
						agentConfig.setAgentRoleCode(EVIDENCES_SEARCHER_AGENT);
						agentConfig.setAgentServiceId(serviceId);
						agentConfig.setSubscribeAllTools(false);
						agentConfig.setEnabledFunctions(List.of());
						agentConfig.setUseDefaultChatModel(true);
						agentConfigs.add(agentConfig);
					} catch (SearchServiceException e) {
						LOGGER.error("Error initializing search agent config", e);
					}
				}
				return agentConfigs;
			}
		};
	}

	GAgentConfig controllerConfig = null;
	GAgentConfig reporterConfig = null;

	@Bean
	public IGDynamicAgentConfigDataSource defaultControllerAgentConfigDataSource() {
		if (controllerConfig == null) {
			controllerConfig = new GAgentConfig();
			controllerConfig.setCode(DefaultControllerNetworkAgentService.CONTROLLER_AGENT);
			controllerConfig.setAgentServiceId(DefaultControllerNetworkAgentService.CONTROLLER_AGENT);
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
			reporterConfig.setCode(TextProcessingTaskPerformerAgentService.TEXT_PROCESSING_AGENT_SERVICE);
			reporterConfig.setAgentServiceId(TextProcessingTaskPerformerAgentService.TEXT_PROCESSING_AGENT_SERVICE);
			reporterConfig.setDescription(
					REPORTER_AGENT_DESCRIPTION);
			reporterConfig.setAgentRoleCode(REPORT_WRITER_AGENT);
		}
		return IGDynamicAgentConfigDataSource.of(reporterConfig);
	}

	private GPromptTemplateConfig processSearchPrompt(String queriesGenerationPromptUseCode) {
		GPromptTemplateConfig prompt = promptsDao.findByPromptUse(queriesGenerationPromptUseCode);
		return prompt;
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
									chatModelsDao, toolsRepositoryPattern, promptsDao, configsRepository,
									securityService, agentRoleDao, nativeSearch, rankersDao);
							outServices.add(nativeWrapper);
						} else {
							DocumentsSearchNetworkAgentServiceWrapper wrapper = new DocumentsSearchNetworkAgentServiceWrapper(
									chatModelsDao, toolsRepositoryPattern, promptsDao, configsRepository,
									securityService, agentRoleDao, search);
							outServices.add(wrapper);
						}
					} catch (SearchServiceException e) {
						LOGGER.error("Error initializing search agent", e);
					}
				}
				return outServices;
			}

		};

	}
}
