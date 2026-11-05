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
import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCalling;

public abstract class GAbstractDocumentsSearchNetworkAgentService
		extends GAbstractGenericalNetworkAgentService<SearchAgentCommand, List<Document>>
		implements IGDocumentsSearchNetworkAgentService {

	/** The searcher returns the documents found with a RESPONSE, shared in the session context. */
	@Override
	public MessageSemantic getProducedMessageSemantic() {
		return MessageSemantic.RESPONSE;
	}

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
			IGAgentsNetworkRuntimeDao agentsDao, GeboToolCalling toolCalling, IGNetworkOfAgentsSuspensionHandler suspensionHandler) throws LLMConfigException, AgentException {
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
		// the libraries the agent works with are reclaimed from the conversation as it enters
		final IChatRequestContext agentContext = IChatRequestContext
				.forAgent(withTheLibrariesOfTheConversation(chatRequestContext, config), listener);
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
		// What a tool of this call declares the run must wait for: read once the call
		// ended. A respawned agent is told first what it waited for.
		final AgentSuspensionTurn turn = AgentSuspensionTurn.of("Documents search agent " + getId(), toolCalling, suspensionHandler,
				contextAgentPersona);
		final GeboToolCalling callToolCalling = turn.toolCalling();
		final AgentsExchangeMessage<SearchAgentCommand> told = toldMessage(msg, turn);
		Map<String, Object> params = createAgentTemplateParams(prompt, network, agentRole, contextAgentPersona, session,
				mySessionContext, told.getPayload(), agentsDao, actualContributionNr, tokenBudget);
		notificationSink.next("Agent: " + contextAgentPersona.getNetworkAgentName() + " is searching...",
				NotificationType.INFO);
		// how the search went (e.g. the sources it could not reach), told to the agents reading it
		final List<String> statusNotices = new ArrayList<>();
		List<Document> documents = retrieveDocuments(prompt, agentContext, agentModel, params, network, agentRole,
				contextAgentPersona,
						session, mySessionContext, told, agentsDao, notificationSink, statusNotices, callToolCalling);
		// nothing to hand over: the agent searches when it is re-executed on this message
		if (turn.suspendIfWaiting(network, config, mySessionContext)) {
			return List.of();
		}
		// what the agent hands over: the documents found, or what it made of them
		documents = deliverDocuments(documents, told.getPayload(), agentContext, agentModel, session,
				contextAgentPersona,
						notificationSink, runAs, statusNotices, callToolCalling);
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
		AgentsExchangeMessage<List<Document>> outMsg = AgentsExchangeMessage.of(session,
				contextAgentPersona.getNetworkAgentName(), msg.getFromAgent(), documents, getProducedMessageSemantic());
		outMsg.setFromAgentRole(agentRole);
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
	 * The message the agent works on: on a respawn, its search command with what it
	 * waited for told first, the only text it is given. The message received stays
	 * as it is: it is the turn a further suspension keeps.
	 */
	private static AgentsExchangeMessage<SearchAgentCommand> toldMessage(AgentsExchangeMessage<SearchAgentCommand> msg,
			AgentSuspensionTurn turn) {
		final SearchAgentCommand command = msg.getPayload();
		final String text = command != null ? turn.toldInput(command.getCommand()) : null;
		if (command == null || java.util.Objects.equals(text, command.getCommand())) {
			return msg;
		}
		return new AgentsExchangeMessage<SearchAgentCommand>(msg.getCollaborationContextId(), msg.getMessageSemantic(),
				msg.getFromAgent(), msg.getFromAgentRole(), msg.getToAgent(), command.withCommand(text),
				msg.getExecutionOrder());
	}

	/**
	 * What the agent hands over for the documents it retrieved: the documents
	 * themselves by default. A searcher able to do more with them (e.g. a deep
	 * search analysing them into one text when the command asks it) overrides this,
	 * adding to {@code statusNotices} what the agents reading the outcome must know.
	 *
	 * @param documents          the documents retrieved, never null
	 * @param command            the command of the search
	 * @param chatRequestContext the request context of this agent
	 * @param agentModel         the model the agent runs with
	 * @param session            the collaboration session of the network
	 * @param runAs              the identity the agent runs as
	 * @param toolCalling        what the controlling software adds to the model calls
	 *                           of this agent (see {@link GeboToolCalling}), for an
	 *                           agent that makes model calls of its own in handing
	 *                           over (a deep search analysis)
	 */
	protected List<Document> deliverDocuments(List<Document> documents, SearchAgentCommand command,
			IChatRequestContext chatRequestContext, IGConfigurableChatModel agentModel,
			AgentsCollaborationSessionContext session, AgentNetworkParticipant contextAgentPersona,
			INotificationSink notificationSink, ReactiveIdentityUtil runAs, List<String> statusNotices,
			GeboToolCalling toolCalling) throws AgentException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("deliverDocuments(...) documents search agent id:" + getId() + " hands over "
					+ (documents != null ? documents.size() : 0) + " document(s) as retrieved");
		}
		return documents;
	}

	/**
	 * Retrieves the documents, adding to {@code statusNotices} what the agents reading
	 * them must know of how the search went (e.g. the sources it could not reach), with
	 * what the controlling software adds to the model calls of this agent (see
	 * {@link GeboToolCalling}): an agent whose retrieval makes model calls (a search
	 * planner, a native query, a ranking) makes them with it.
	 */
	protected abstract List<Document> retrieveDocuments(GPromptTemplateConfig prompt,
			IChatRequestContext chatRequestContext,
			IGConfigurableChatModel agentModel, Map<String, Object> params, GAgentsNetwork network,
			GAgentRole agentRole, AgentNetworkParticipant contextAgentPersona,
			AgentsCollaborationSessionContext session,
			AgentPrivateSessionContext<SearchAgentCommand, List<Document>> mySessionContext,
			AgentsExchangeMessage<SearchAgentCommand> msg, IGAgentsNetworkRuntimeDao agentsDao,
			INotificationSink notificationSink, List<String> statusNotices, GeboToolCalling toolCalling)
			throws AgentException;
}
