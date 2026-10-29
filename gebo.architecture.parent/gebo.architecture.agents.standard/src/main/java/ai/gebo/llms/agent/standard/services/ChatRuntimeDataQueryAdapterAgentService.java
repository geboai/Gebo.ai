package ai.gebo.llms.agent.standard.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.model.AgentCapabilities;
import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage.MessageSemantic;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentRole;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.services.AgentException;
import ai.gebo.architecture.agents.services.GAbstractGenericalNetworkAgentService;
import ai.gebo.architecture.agents.services.IAgentRoleDao;
import ai.gebo.architecture.agents.services.IGAgentsNetworkRuntimeDao;
import ai.gebo.architecture.agents.services.IGNetworkAgentService;
import ai.gebo.architecture.agents.services.INotificationSink;
import ai.gebo.architecture.ai.service.IGDocumentContentRendererProvider;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.security.services.ReactiveIdentityUtil;

/**
 * The input node of a chat network: forwards the question the network was
 * asked to the controller, without calling a model. The request the question
 * is asked in travels in the session environment (see {@link ChatRuntimeDataOf});
 * this node is kept for the networks that name it as their input node, and
 * does nothing a controller as input node would not do.
 */
@Service
public class ChatRuntimeDataQueryAdapterAgentService
		extends GAbstractGenericalNetworkAgentService<String, String>
		implements IGNetworkAgentService<String, String> {

	private static final String DESCRIPTION = "Input adapter that forwards the user query to the controller, without calling an LLM";
	public static final String CHAT_RUNTIME_DATA_QUERY_ADAPTER = "chatRuntimeDataQueryAdapter";

	public ChatRuntimeDataQueryAdapterAgentService(IGChatModelRuntimeConfigurationDao chatModelsDao,
			IGToolCallbackSourceRepositoryPattern toolsRepositoryPattern, IGPromptConfigDao promptsDao,
			IGSecurityService securityService, IAgentRoleDao agentRoleDao, IGRuntimeBinder runtimeBinder,
			IGDocumentContentRendererProvider rendererFactory) {
		super(chatModelsDao, toolsRepositoryPattern, promptsDao, securityService, agentRoleDao, runtimeBinder,
				rendererFactory);
	}

	@Override
	public String getId() {
		return CHAT_RUNTIME_DATA_QUERY_ADAPTER;
	}

	@Override
	public String getDescription() {
		return DESCRIPTION;
	}

	/** It only forwards the user query to its peers: no chat model is called. */
	@Override
	public boolean isCallingChatModel() {
		return false;
	}

	@Override
	public AgentCapabilities getAgentCapabilities(GAgentConfig agentConfig) {
		AgentCapabilities capabilities = super.getAgentCapabilities(agentConfig);
		capabilities.addCapability(
				"Extract the user query from the chat runtime data and forward it to the controller (input node, no LLM call)");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Chat runtime data query adapter id:" + getId()
					+ " advertises the input adaptation capability");
		}
		return capabilities;
	}

	@Override
	public Class<String> getInputType() {
		return String.class;
	}

	@Override
	public Class<String> getOutputType() {
		return String.class;
	}

	@Override
	public List<AgentsExchangeMessage<String>> onMessage(IChatRequestContext chatRequestContext, GAgentConfig config,
			AgentsExchangeMessage<String> msg, int actualContributionNr, GAgentsNetwork network,
			AgentNetworkParticipant contextAgentPersona, INotificationSink notificationSink,
			AgentsCollaborationSessionContext session,
			AgentPrivateSessionContext<String, String> mySessionContext,
			ReactiveIdentityUtil runAs, IGAgentsNetworkRuntimeDao agentsDao) throws LLMConfigException, AgentException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin onMessage(...) chat runtime data query adapter persona:"
					+ contextAgentPersona.getNetworkAgentName() + " contributionNr:" + actualContributionNr);
		}
		final List<String> targets = contextAgentPersona.getCommunicationList();
		if (targets == null || targets.isEmpty()) {
			throw new AgentException("Input adapter '" + contextAgentPersona.getNetworkAgentName()
					+ "' has no target agent to forward the query to");
		}
		final String query = extractQuery(msg.getPayload());
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Extracted user query (length:" + query.length() + ") forwarding to " + targets.size()
					+ " target(s): " + targets);
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("<ADAPTED_USER_QUERY>");
			LOGGER.trace(query);
			LOGGER.trace("</ADAPTED_USER_QUERY>");
		}
		final GAgentRole agentRole = config.getAgentRoleCode() != null
				? agentRoleDao.findByCode(config.getAgentRoleCode())
				: null;
		final List<AgentsExchangeMessage<String>> out = new ArrayList<>();
		for (String target : targets) {
			// a forwarder: the query is delivered to each target as its input
			out.add(new AgentsExchangeMessage<String>(session.getId(), getProducedMessageSemantic(),
					contextAgentPersona.getNetworkAgentName(), agentRole, target, query, 1));
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End onMessage(...) chat runtime data query adapter emitting " + out.size() + " message(s)");
		}
		return out;
	}

	/**
	 * The query the network was asked, which is what the input node is given; the
	 * request it is asked in is in the session environment, for the agents that
	 * need more of it (see {@link ChatRuntimeDataOf}).
	 */
	private String extractQuery(String payload) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("extractQuery(...) resolved a user query of " + (payload != null ? payload.length() : 0)
					+ " character(s)");
		}
		return payload != null ? payload : "";
	}

}
