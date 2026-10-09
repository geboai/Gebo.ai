package ai.gebo.architecture.agents.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.ai.document.Document;

import ai.gebo.architecture.agents.model.AgentCapabilities;
import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage.MessageSemantic;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentRole;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.services.INotificationSink.NotificationObject.NotificationType;
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
	public AgentCapabilities getAgentCapabilities(GAgentConfig agentConfig) {
		AgentCapabilities capabilities = super.getAgentCapabilities(agentConfig);
		capabilities.addCapability(
				"Search for and retrieve the document fragments most relevant to a given search command, optionally re-ranking them by relevance");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Documents search agent id:" + getId() + " advertises the document retrieval capability");
		}
		return capabilities;
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
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin onMessage(...) documents search agent id:" + getId() + " persona:"
					+ (contextAgentPersona != null ? contextAgentPersona.getAgentContextualName() : null)
					+ " contributionNr:" + actualContributionNr);
		}
		GPromptTemplateConfig prompt = resolvePrompt(config.getCustomLoopPrompt(), config.getMainLoopPromptUseCode(),
				false);
		GAgentRole agentRole = agentRoleDao.findByCode(config.getAgentRoleCode());
		// this agent's own tool calls, forwarded to the user request's recorder
		ToolCallsListener listener = agentToolCallsListener(chatRequestContext);
		final IChatRequestContext agentContext = IChatRequestContext.forAgent(chatRequestContext, listener);
		IGConfigurableChatModel agentModel = getAgentModel(config, listener,
				contextAgentPersona.isAllowedToNotifyUser() ? notificationSink : null, runAs);
		int tokenBudget = agentTokenBudget(agentModel, prompt, chatRequestContext);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Documents search agent id:" + getId() + " agentRole:"
					+ (agentRole != null ? agentRole.getCode() : null) + " contextLength:"
					+ agentModel.getContextLength() + " promptSize:" + prompt.getTokensSize() + " (tok) tokenBudget:"
					+ tokenBudget + " (tok)");
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("<SEARCH_AGENT_COMMAND agent=" + getId() + ">");
			LOGGER.trace(String.valueOf(msg.getPayload()));
			LOGGER.trace("</SEARCH_AGENT_COMMAND>");
		}
		Map<String, Object> params = createAgentTemplateParams(prompt, network, agentRole, contextAgentPersona, session,
				mySessionContext, msg.getPayload(), agentsDao, actualContributionNr, tokenBudget);
		notificationSink.next("Agent: " + contextAgentPersona.getNetworkAgentName() + " is searching...",
				NotificationType.INFO);
		// how the search went (e.g. the sources it could not reach), told to the agents reading it
		final List<String> statusNotices = new ArrayList<>();
		List<Document> documents = retrieveDocuments(prompt, agentContext, agentModel, params, network, agentRole,
				contextAgentPersona, session, mySessionContext, msg, agentsDao, notificationSink, statusNotices);
		notificationSink.next("Agent: " + contextAgentPersona.getNetworkAgentName() + " has found: "
				+ documents.size() + " evidences", NotificationType.INFO);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End onMessage(...) documents search agent id:" + getId() + " retrieved "
					+ (documents != null ? documents.size() : 0) + " document(s)");
		}
		if (LOGGER.isTraceEnabled() && documents != null) {
			int index = 1;
			for (Document document : documents) {
				LOGGER.trace("<RETRIEVED_DOCUMENT nr=" + index + " id=" + document.getId() + ">");
				LOGGER.trace(document.getText());
				LOGGER.trace("</RETRIEVED_DOCUMENT>");
				index++;
			}
		}
		AgentsExchangeMessage<List<Document>> outMsg = AgentsExchangeMessage.of(session, msg.getFromAgent(), documents,
				MessageSemantic.RESPONSE);
		if (!statusNotices.isEmpty()) {
			outMsg.setStatusNotices(statusNotices);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Documents search agent id:" + getId() + " shares " + statusNotices.size()
						+ " status notice(s) with its documents");
			}
		}
		return List.of(outMsg);
	}

	/**
	 * Retrieves the documents, adding to {@code statusNotices} what the agents reading
	 * them must know of how the search went (e.g. the sources it could not reach). By
	 * default {@link #retrieveDocuments(GPromptTemplateConfig, IChatRequestContext, IGConfigurableChatModel, Map, GAgentsNetwork, GAgentRole, AgentNetworkParticipant, AgentsCollaborationSessionContext, AgentPrivateSessionContext, AgentsExchangeMessage, IGAgentsNetworkRuntimeDao, INotificationSink)},
	 * telling nothing.
	 */
	protected List<Document> retrieveDocuments(GPromptTemplateConfig prompt, IChatRequestContext chatRequestContext,
			IGConfigurableChatModel agentModel, Map<String, Object> params, GAgentsNetwork network,
			GAgentRole agentRole, AgentNetworkParticipant contextAgentPersona,
			AgentsCollaborationSessionContext session,
			AgentPrivateSessionContext<SearchAgentCommand, List<Document>> mySessionContext,
			AgentsExchangeMessage<SearchAgentCommand> msg, IGAgentsNetworkRuntimeDao agentsDao,
			INotificationSink notificationSink, List<String> statusNotices) throws AgentException {
		return retrieveDocuments(prompt, chatRequestContext, agentModel, params, network, agentRole,
				contextAgentPersona, session, mySessionContext, msg, agentsDao, notificationSink);
	}

	protected abstract List<Document> retrieveDocuments(GPromptTemplateConfig prompt,
			IChatRequestContext chatRequestContext, IGConfigurableChatModel agentModel, Map<String, Object> params,
			GAgentsNetwork network, GAgentRole agentRole, AgentNetworkParticipant contextAgentPersona,
			AgentsCollaborationSessionContext session,
			AgentPrivateSessionContext<SearchAgentCommand, List<Document>> mySessionContext,
			AgentsExchangeMessage<SearchAgentCommand> msg, IGAgentsNetworkRuntimeDao agentsDao,
			INotificationSink notificationSink) throws AgentException;
}
