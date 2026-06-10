package ai.gebo.llms.agent.standard.config;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.services.IAgentConfigDao;
import ai.gebo.architecture.agents.services.IAgentRoleDao;
import ai.gebo.architecture.agents.services.IGDynamicAgentConfigDataSource;
import ai.gebo.architecture.agents.services.IGDynamicAgentServiceSupplier;
import ai.gebo.architecture.agents.services.IGGenericAgentService;
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
	public IGDynamicAgentConfigDataSource externalSourcesAgentConfigDataSource() {
		final List<GAgentConfig> agentConfigs = new ArrayList<>();
		final List<ISearchService> implementations = searchServicesRepositoryPattern.getImplementations();
		for (ISearchService search : implementations) {
			String serviceId = search.getProductId() + (search instanceof INativeSearchService
					? NativeDocumentsSearchNetworkAgentService.NATIVE_SEARCHER_AGENT
					: DocumentsSearchNetworkAgentServiceWrapper.SEARCH_AGENT);
			GAgentConfig agentConfig = new GAgentConfig();
			agentConfig.setCode(serviceId);
			agentConfig.setDescription(search.getProductId() + " search agent ");
			agentConfig.setCustomLoopPrompt(processSearchPrompt(search.getQueriesGenerationPromptUseCode()));
			agentConfig.setAccessibleToAll(true);
			agentConfig.setAgentRoleCode("searcher");
			agentConfig.setAgentServiceId(serviceId);
			agentConfig.setSubscribeAllTools(false);
			agentConfig.setEnabledFunctions(List.of());
			agentConfig.setUseDefaultChatModel(true);
			agentConfigs.add(agentConfig);
		}
		return new IGDynamicAgentConfigDataSource() {

			@Override
			public List<GAgentConfig> getConfigurations() {
				return agentConfigs;
			}
		};
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
