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
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.security.services.ReactiveIdentityUtil;

/**
 * Input adapter agent for the default chat network. It is the network input node:
 * it does not call an LLM, it simply extracts the user query (rewritten query, or
 * the raw query) from the {@link ChatPipelineExecutionRuntimeData} and forwards it
 * as a plain String to its configured target(s) (the controller), so the rest of
 * the network can be wired on a String input type.
 */
@Service
public class ChatRuntimeDataQueryAdapterAgentService
		extends GAbstractGenericalNetworkAgentService<ChatPipelineExecutionRuntimeData, String>
		implements IGNetworkAgentService<ChatPipelineExecutionRuntimeData, String> {

	private static final String DESCRIPTION = "Input adapter that extracts the user query from the chat runtime data and forwards it to the controller, without calling an LLM";
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

	@Override
	public AgentCapabilities getAgentCapabilities(GAgentConfig agentConfig) {
		AgentCapabilities capabilities = super.getAgentCapabilities(agentConfig);
		capabilities.addCapability(
				"Extract the user query from the chat runtime data and forward it to the controller (input node, no LLM call)");
		return capabilities;
	}

	@Override
	public Class<ChatPipelineExecutionRuntimeData> getInputType() {
		return ChatPipelineExecutionRuntimeData.class;
	}

	@Override
	public Class<String> getOutputType() {
		return String.class;
	}

	@Override
	public List<AgentsExchangeMessage<String>> onMessage(IChatRequestContext chatRequestContext, GAgentConfig config,
			AgentsExchangeMessage<ChatPipelineExecutionRuntimeData> msg, int actualContributionNr, GAgentsNetwork network,
			AgentNetworkParticipant contextAgentPersona, INotificationSink notificationSink,
			AgentsCollaborationSessionContext session,
			AgentPrivateSessionContext<ChatPipelineExecutionRuntimeData, String> mySessionContext,
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
		final GAgentRole agentRole = config.getAgentRoleCode() != null
				? agentRoleDao.findByCode(config.getAgentRoleCode())
				: null;
		final List<AgentsExchangeMessage<String>> out = new ArrayList<>();
		for (String target : targets) {
			out.add(new AgentsExchangeMessage<String>(session.getId(), MessageSemantic.EXECUTE_AND_SHARE_RESULT,
					contextAgentPersona.getNetworkAgentName(), agentRole, target, query, 1));
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End onMessage(...) chat runtime data query adapter emitting " + out.size() + " message(s)");
		}
		return out;
	}

	private String extractQuery(ChatPipelineExecutionRuntimeData runtimeData) throws AgentException {
		if (runtimeData == null || runtimeData.getMinimalChatContext() == null) {
			throw new AgentException("No chat context available to extract the user query");
		}
		GeboChatRequest request = runtimeData.getMinimalChatContext().getCurrentRequest();
		if (request == null) {
			throw new AgentException("No chat request available to extract the user query");
		}
		String query = GeboChatRequest.actualQuery(request);
		return query != null ? query : "";
	}

}
