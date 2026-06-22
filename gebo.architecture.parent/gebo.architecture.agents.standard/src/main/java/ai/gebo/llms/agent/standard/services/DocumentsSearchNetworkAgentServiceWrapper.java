package ai.gebo.llms.agent.standard.services;

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
import ai.gebo.architecture.agents.services.GAbstractDocumentsSearchNetworkAgentService;
import ai.gebo.architecture.agents.services.IAgentConfigDao;
import ai.gebo.architecture.agents.services.IAgentRoleDao;
import ai.gebo.architecture.agents.services.IGAgentsNetworkRuntimeDao;
import ai.gebo.architecture.agents.services.INotificationSink;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.service.IGDocumentContentRendererProvider;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.architecture.search.service.ISearchService;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.security.services.IGSecurityService;

public class DocumentsSearchNetworkAgentServiceWrapper extends GAbstractDocumentsSearchNetworkAgentService {
	

	private static final String SEARCH_AGENT_DESCRIPTION = " search agent";
	public static final String SEARCH_AGENT = "SearchAgent";
	private final ISearchService<?> wrappedSearchService;
	public DocumentsSearchNetworkAgentServiceWrapper(IGChatModelRuntimeConfigurationDao chatModelsDao,
			IGToolCallbackSourceRepositoryPattern toolsRepositoryPattern, IGPromptConfigDao promptsDao,
			IGSecurityService securityService, IAgentRoleDao agentRoleDao, IGRuntimeBinder runtimeBinder, ISearchService<?> wrappedSearchService, IGDocumentContentRendererProvider rendererFactory) {
		super(chatModelsDao, toolsRepositoryPattern, promptsDao, securityService, agentRoleDao, runtimeBinder, rendererFactory);
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
			INotificationSink notificationSink) {
		// TODO Auto-generated method stub
		return null;
	}

}
