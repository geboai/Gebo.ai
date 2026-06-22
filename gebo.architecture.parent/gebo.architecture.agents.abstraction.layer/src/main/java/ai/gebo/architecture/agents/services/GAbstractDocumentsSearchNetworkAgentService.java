package ai.gebo.architecture.agents.services;

import java.util.List;
import java.util.Map;

import org.springframework.ai.document.Document;

import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage.MessageSemantic;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentRole;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.SearchAgentCommand;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.service.IGDocumentContentRendererProvider;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.ToolCallsListener;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.security.services.ReactiveIdentityUtil;

public abstract class GAbstractDocumentsSearchNetworkAgentService
		extends GAbstractGenericalNetworkAgentService<SearchAgentCommand, List<Document>>
		implements IGDocumentsSearchNetworkAgentService {

	public GAbstractDocumentsSearchNetworkAgentService(IGChatModelRuntimeConfigurationDao chatModelsDao,
			IGToolCallbackSourceRepositoryPattern toolsRepositoryPattern, IGPromptConfigDao promptsDao,
			IGSecurityService securityService, IAgentRoleDao agentRoleDao, IGRuntimeBinder runtimeBinder,
			IGDocumentContentRendererProvider rendererFactory) {
		super(chatModelsDao, toolsRepositoryPattern, promptsDao, securityService, agentRoleDao, runtimeBinder,
				rendererFactory);

	}

	@Override
	public Class<List<Document>> getOutputType() {

		return (Class<List<Document>>) (Class<?>) List.class;
	}

	@Override
	public Class<SearchAgentCommand> getInputType() {

		return SearchAgentCommand.class;
	}

	@Override
	public List<AgentsExchangeMessage<List<Document>>> onMessage(IChatRequestContext chatRequestContext,
			GAgentConfig config, AgentsExchangeMessage<SearchAgentCommand> msg, int actualContributionNr,
			GAgentsNetwork network, AgentNetworkParticipant contextAgentPersona, INotificationSink notificationSink,
			AgentsCollaborationSessionContext session,
			AgentPrivateSessionContext<SearchAgentCommand, List<Document>> mySessionContext, ReactiveIdentityUtil runAs,
			IGAgentsNetworkRuntimeDao agentsDao) throws LLMConfigException, AgentException {
		GPromptTemplateConfig prompt = resolvePrompt(config.getCustomLoopPrompt(), config.getMainLoopPromptUseCode(),
				false);
		GAgentRole agentRole = agentRoleDao.findByCode(config.getAgentRoleCode());
		ToolCallsListener listener = new ToolCallsListener();
		IGConfigurableChatModel agentModel = getAgentModel(config, listener, runAs);
		int tokenBudget = (agentModel.getContextLength() - prompt.getTokensSize()) * 2 / 3;
		Map<String, Object> params = createAgentTemplateParams(prompt, network, agentRole, contextAgentPersona, session,
				mySessionContext, msg, agentsDao, actualContributionNr, tokenBudget);
		List<Document> documents = retrieveDocuments(prompt, chatRequestContext, agentModel, params, network, agentRole,
				contextAgentPersona, session, mySessionContext, msg, agentsDao, notificationSink);
		AgentsExchangeMessage<List<Document>> outMsg = AgentsExchangeMessage.of(session, msg.getFromAgent(), documents,
				MessageSemantic.RESPONSE);
		return List.of(outMsg);
	}

	protected abstract List<Document> retrieveDocuments(GPromptTemplateConfig prompt,
			IChatRequestContext chatRequestContext, IGConfigurableChatModel agentModel, Map<String, Object> params,
			GAgentsNetwork network, GAgentRole agentRole, AgentNetworkParticipant contextAgentPersona,
			AgentsCollaborationSessionContext session,
			AgentPrivateSessionContext<SearchAgentCommand, List<Document>> mySessionContext,
			AgentsExchangeMessage<SearchAgentCommand> msg, IGAgentsNetworkRuntimeDao agentsDao,
			INotificationSink notificationSink) throws AgentException;
}
