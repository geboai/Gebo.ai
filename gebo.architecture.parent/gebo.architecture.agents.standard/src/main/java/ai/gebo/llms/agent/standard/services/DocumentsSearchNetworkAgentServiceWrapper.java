package ai.gebo.llms.agent.standard.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.ai.document.Document;

import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage;
import ai.gebo.architecture.agents.model.GAgentRole;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.SearchAgentCommand;
import ai.gebo.architecture.agents.services.AgentException;
import ai.gebo.architecture.agents.services.IAgentRoleDao;
import ai.gebo.architecture.agents.services.IGAgentsNetworkRuntimeDao;
import ai.gebo.architecture.agents.services.INotificationSink;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.service.IGDocumentContentRendererProvider;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.architecture.search.model.SearchQuery;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.architecture.search.model.SearchableSystemMetaData;
import ai.gebo.architecture.search.service.ISearchService;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.services.IGRankerService;
import ai.gebo.llms.deepsearch.datasources.model.DeepSearchDataSourceExtractedSearchQueries;
import ai.gebo.security.services.IGSecurityService;

public class DocumentsSearchNetworkAgentServiceWrapper extends GAbstractStandardDocumentsSearchAgentService {

	private static final String SEARCH_AGENT_DESCRIPTION = " search agent";
	public static final String SEARCH_AGENT = "SearchAgent";
	private final ISearchService<?> wrappedSearchService;

	public DocumentsSearchNetworkAgentServiceWrapper(IGChatModelRuntimeConfigurationDao chatModelsDao,
			IGToolCallbackSourceRepositoryPattern toolsRepositoryPattern, IGPromptConfigDao promptsDao,
			IGSecurityService securityService, IAgentRoleDao agentRoleDao, IGRuntimeBinder runtimeBinder,
			IGDocumentContentRendererProvider rendererFactory, IDocumentsChunkService chunkingService,
			IGRankerService rankerService, ISearchService<?> wrappedSearchService) {
		super(chatModelsDao, toolsRepositoryPattern, promptsDao, securityService, agentRoleDao, runtimeBinder,
				rendererFactory, chunkingService, rankerService);
		this.wrappedSearchService = wrappedSearchService;
	}

	@Override
	public String getId() {

		return wrappedSearchService.getProductId() + SEARCH_AGENT;
	}

	@Override
	public String getDescription() {

		return wrappedSearchService.getProductId() + SEARCH_AGENT_DESCRIPTION;
	}

	@Override
	protected List<Document> retrieveDocuments(GPromptTemplateConfig prompt, IChatRequestContext chatRequestContext,
			IGConfigurableChatModel agentModel, Map<String, Object> params, GAgentsNetwork network,
			GAgentRole agentRole, AgentNetworkParticipant contextAgentPersona,
			AgentsCollaborationSessionContext session,
			AgentPrivateSessionContext<SearchAgentCommand, List<Document>> mySessionContext,
			AgentsExchangeMessage<SearchAgentCommand> msg, IGAgentsNetworkRuntimeDao agentsDao,
			INotificationSink notificationSink) throws AgentException {
		final SearchAgentCommand command = msg.getPayload();
		final int topK = retrievalTopK(command);
		final List<SearchResult> results = new ArrayList<>();
		try {
			// LLM-assisted query extraction using the wrapped service's queries-generation
			// prompt, fed with the agent placeholder params.
			final GPromptTemplateConfig queriesPrompt = promptsDao
					.findByPromptUse(wrappedSearchService.getQueriesGenerationPromptUseCode());
			DeepSearchDataSourceExtractedSearchQueries extracted = callLLMStructuredReturn(agentModel, queriesPrompt,
					chatRequestContext, params, DeepSearchDataSourceExtractedSearchQueries.class);
			if (extracted != null && extracted.getSearchQuery() != null) {
				for (SearchQuery query : extracted.getSearchQuery()) {
					for (SearchableSystemMetaData system : wrappedSearchService.getSearchableSystems()) {
						results.addAll(wrappedSearchService.search(query, system, topK));
					}
				}
			}
		} catch (LLMConfigException | IOException | SearchServiceException e) {
			throw new AgentException("Error executing search agent " + getId(), e);
		}
		return maybeRank(chunkToDocuments(results), command);
	}

}
