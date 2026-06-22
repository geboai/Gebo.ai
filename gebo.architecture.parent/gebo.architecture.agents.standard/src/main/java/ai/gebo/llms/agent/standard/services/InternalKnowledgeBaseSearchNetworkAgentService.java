package ai.gebo.llms.agent.standard.services;

import java.util.List;
import java.util.Map;

import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;

import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage;
import ai.gebo.architecture.agents.model.GAgentRole;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.SearchAgentCommand;
import ai.gebo.architecture.agents.services.AgentException;
import ai.gebo.architecture.agents.services.GAbstractDocumentsSearchNetworkAgentService;
import ai.gebo.architecture.agents.services.IAgentConfigDao;
import ai.gebo.architecture.agents.services.IAgentRoleDao;
import ai.gebo.architecture.agents.services.IGAgentsNetworkRuntimeDao;
import ai.gebo.architecture.agents.services.IGInternalKnowledgeBaseDocumentsSearchNetworkAgentService;
import ai.gebo.architecture.agents.services.INotificationSink;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.service.IGDocumentContentRendererProvider;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.graphrag.services.IKnowledgeGraphSearchService;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.architecture.rag.support.layer.services.IGFullTextSearchDocumentsCachedDao;
import ai.gebo.architecture.rag.support.layer.services.IGSemanticSearchDocumentsCachedDao;
import ai.gebo.architecture.rag_threasholds_autotune.service.IRagThreasholdAutotuneService;
import ai.gebo.core.contents.security.services.IGKnowledgebaseVisibilityService;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.security.services.IGSecurityService;

public class InternalKnowledgeBaseSearchNetworkAgentService extends GAbstractDocumentsSearchNetworkAgentService
		implements IGInternalKnowledgeBaseDocumentsSearchNetworkAgentService {

	public static final String AGENT_THAT_SEARCHES_THE_INTERNAL_KNOWLEDGE_BASE = "Agent that searches the internal knowledge base";
	public static final String INTERNAL_KNOWLEDGE_BASE_SEARCHER = "internalKnowledgeBaseSearcher";
	private final IGSemanticSearchDocumentsCachedDao semanticSearchDao;
	private final IGFullTextSearchDocumentsCachedDao fullTextDao;
	private final IKnowledgeGraphSearchService knowledgeGraphSearchDao;
	private final IGKnowledgebaseVisibilityService knowledgeBaseVisibilityService;

	public InternalKnowledgeBaseSearchNetworkAgentService(IGChatModelRuntimeConfigurationDao chatModelsDao,
			IGToolCallbackSourceRepositoryPattern toolsRepositoryPattern, IGPromptConfigDao promptsDao,
			IGSecurityService securityService, IAgentRoleDao agentRoleDao, IGRuntimeBinder runtimeBinder,
			IGSemanticSearchDocumentsCachedDao semanticSearchDao, IKnowledgeGraphSearchService knowledgeGraphSearchDao,
			IGKnowledgebaseVisibilityService knowledgeBaseVisibilityService,
			IGFullTextSearchDocumentsCachedDao fullTextDao, IGDocumentContentRendererProvider rendererFactory) {

		super(chatModelsDao, toolsRepositoryPattern, promptsDao, securityService, agentRoleDao, runtimeBinder,
				rendererFactory);
		this.semanticSearchDao = semanticSearchDao;
		this.fullTextDao = fullTextDao;
		this.knowledgeGraphSearchDao = knowledgeGraphSearchDao;
		this.knowledgeBaseVisibilityService = knowledgeBaseVisibilityService;

	}

	@Override
	public String getId() {

		return INTERNAL_KNOWLEDGE_BASE_SEARCHER;
	}

	@Override
	public String getDescription() {

		return AGENT_THAT_SEARCHES_THE_INTERNAL_KNOWLEDGE_BASE;
	}

	@Override
	protected List<Document> retrieveDocuments(GPromptTemplateConfig prompt, IChatRequestContext chatRequestContext,
			IGConfigurableChatModel agentModel, Map<String, Object> params, GAgentsNetwork network,
			GAgentRole agentRole, AgentNetworkParticipant contextAgentPersona,
			AgentsCollaborationSessionContext session,
			AgentPrivateSessionContext<SearchAgentCommand, List<Document>> mySessionContext,
			AgentsExchangeMessage<SearchAgentCommand> msg, IGAgentsNetworkRuntimeDao agentsDao,
			INotificationSink notificationSink) throws AgentException {
		SearchAgentCommand command = msg.getPayload();

		return null;
	}

}
