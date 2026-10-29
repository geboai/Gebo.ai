/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.standard.services;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.GAgentsNetwork.CommunicationPolicy;
import ai.gebo.architecture.agents.services.AgentException;
import ai.gebo.architecture.agents.services.IAgentsNetworkDao;
import ai.gebo.architecture.agents.services.INotificationSink;
import ai.gebo.architecture.agents.services.INotificationSink.NotificationObject.NotificationType;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.llms.agent.standard.services.GChatEnvironmentNetworkRunner.ChatEnvironmentRun;
import ai.gebo.llms.agent.standard.services.GChatEnvironmentNetworkRunner.ChatRunAttachments;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

/**
 * Runs a network of agents, or a single agent, for a caller outside a chat - an
 * exported A2A skill, an MCP tool built from an agent or a network: a text
 * request in, the text of the final answer out, as the caller whose identity is
 * in the current security context.
 * <p>
 * The run happens the way every run of a network happens, in a chat environment
 * ({@link IGChatEnvironment}): in a conversation opened for the run and dropped
 * when it is over, with a current request understood as a chat understands one,
 * on every knowledge base the caller can see. A single agent runs as a network
 * of its own, behind the chat input adapter that turns the chat runtime data
 * into its query.
 */
@Service
@AllArgsConstructor
public class GTextAgentsNetworkRunner {
	private static final Logger LOGGER = LoggerFactory.getLogger(GTextAgentsNetworkRunner.class);
	/** Where a run comes from when the caller does not say. */
	public static final String DEFAULT_ORIGIN = "EXTERNAL";
	/** How long the chunks a writer streamed are waited for, once the run is over. */
	private static final Duration CHAT_ANSWER_COLLECTION_TIMEOUT = Duration.ofSeconds(30);

	/** Logs the network notifications when the caller gives no sink. */
	public static final INotificationSink LOGGING_SINK = state -> {
		if (state == null) {
			return;
		}
		if (state.getNotificationType() == NotificationType.ERROR) {
			LOGGER.warn("Network notification: " + state.getMessage());
		} else if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Network notification: " + state.getMessage());
		}
	};

	private final IGRuntimeBinder runtimeBinder;
	private final IGChatEnvironment chatEnvironment;

	/**
	 * Runs the persisted network with the given code as the current caller.
	 *
	 * @throws AgentException when the network does not exist or its run fails
	 */
	public String runNetwork(String networkCode, String inputText, INotificationSink sink) throws AgentException {
		return runNetwork(networkCode, inputText, sink, DEFAULT_ORIGIN);
	}

	/** The same, saying where the run comes from ({@code A2A}, {@code MCP}...). */
	public String runNetwork(String networkCode, String inputText, INotificationSink sink, String origin)
			throws AgentException {
		IAgentsNetworkDao networkDao = runtimeBinder.getImplementationOf(IAgentsNetworkDao.class);
		GAgentsNetwork network = networkDao.findByCode(networkCode);
		if (network == null) {
			throw new AgentException("Network of agents '" + networkCode + "' does not exist");
		}
		return runNetwork(network, inputText, sink, origin);
	}

	/**
	 * Runs a single agent, as a network made of that agent alone behind the chat
	 * input adapter.
	 *
	 * @param codePrefix prefix of the code of the ephemeral network, for the logs
	 * @throws AgentException when the agent configuration is missing or the run fails
	 */
	public String runAgent(String agentConfigCode, String codePrefix, String inputText, INotificationSink sink)
			throws AgentException {
		return runAgent(agentConfigCode, codePrefix, inputText, sink, DEFAULT_ORIGIN);
	}

	/** The same, saying where the run comes from ({@code A2A}, {@code MCP}...). */
	public String runAgent(String agentConfigCode, String codePrefix, String inputText, INotificationSink sink,
			String origin) throws AgentException {
		if (agentConfigCode == null || agentConfigCode.isBlank()) {
			throw new AgentException("A single agent run needs the agent configuration code");
		}
		return runNetwork(adaptedSingleAgentNetwork(agentConfigCode, codePrefix), inputText, sink, origin);
	}

	/**
	 * Runs the given network (persisted or ephemeral) as the current caller.
	 */
	public String runNetwork(GAgentsNetwork network, String inputText, INotificationSink sink)
			throws AgentException {
		return runNetwork(network, inputText, sink, DEFAULT_ORIGIN);
	}

	/**
	 * The same, saying where the run comes from. The network runs in a transient
	 * conversation of the caller's, dropped when the run is over, and answers with
	 * the text of its last response, or the chunks its writer streamed.
	 */
	public String runNetwork(GAgentsNetwork network, String inputText, INotificationSink sink, String origin)
			throws AgentException {
		final INotificationSink notifications = sink != null ? sink : LOGGING_SINK;
		final GeboChatRequest request = chatEnvironment.requestOf(inputText != null ? inputText : "", null, null);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Network {} runs from {} in a transient conversation of the caller", network.getCode(),
					origin);
		}
		final ChatEnvironmentRun run = chatEnvironment.run(GChatEnvironmentRunRequest.builder().network(network)
				.request(request).attachments(ChatRunAttachments.none())
				.knowledgeBases(GKnowledgeBaseScope.PROFILE_OR_ALL_VISIBLE).understood(true)
				.transientConversation(true).origin(origin != null ? origin : DEFAULT_ORIGIN).sink(notifications)
				.build());
		try {
			// the writer's envelopes buffer in the network's sink until read: the run
			// completed first, the answer is read after
			final List<GeboChatMessageEnvelope> streamed = run.getFrames() != null
					? run.getFrames().collectList().block(CHAT_ANSWER_COLLECTION_TIMEOUT)
					: List.of();
			return answerOf(run.getResponse(), streamed);
		} finally {
			run.dispose();
		}
	}

	/**
	 * The text of a chat network's answer: the response's text when there is one,
	 * else the chunks the writer streamed, in order.
	 */
	static String answerOf(GeboChatResponse response, List<GeboChatMessageEnvelope> streamed) {
		if (response != null && response.getQueryResponse() != null && !response.getQueryResponse().isBlank()) {
			return response.getQueryResponse();
		}
		String answered = null;
		StringBuilder chunks = new StringBuilder();
		for (GeboChatMessageEnvelope envelope : streamed != null ? streamed : List.<GeboChatMessageEnvelope>of()) {
			Object content = envelope != null ? envelope.getContent() : null;
			if (content instanceof GeboChatResponse chatResponse && chatResponse.getQueryResponse() != null
					&& !chatResponse.getQueryResponse().isBlank()) {
				answered = chatResponse.getQueryResponse();
			} else if (content instanceof String chunk) {
				chunks.append(chunk);
			}
		}
		return answered != null ? answered : chunks.toString();
	}

	/**
	 * An ephemeral chat network made of the chat input adapter (the input node,
	 * turning the chat runtime data into the query) and the agent (the output
	 * node), for an agent that answers with chat envelopes.
	 */
	public GAgentsNetwork adaptedSingleAgentNetwork(String agentConfigCode, String codePrefix) {
		AgentNetworkParticipant adapter = new AgentNetworkParticipant();
		adapter.setAgentConfigCode(ChatRuntimeDataQueryAdapterAgentService.CHAT_RUNTIME_DATA_QUERY_ADAPTER);
		adapter.setInputNode(true);
		adapter.setOutputNode(false);
		adapter.setCommunicationPolicy(CommunicationPolicy.ALLOW_ALL);
		AgentNetworkParticipant agent = new AgentNetworkParticipant();
		agent.setAgentConfigCode(agentConfigCode);
		agent.setInputNode(false);
		agent.setOutputNode(true);
		agent.setAllowedToNotifyUser(true);
		agent.setCommunicationPolicy(CommunicationPolicy.ALLOW_ALL);
		adapter.setCommunicationList(List.of(agent.getNetworkAgentName()));
		agent.setCommunicationList(List.of(adapter.getNetworkAgentName()));
		GAgentsNetwork network = new GAgentsNetwork();
		network.setCode((codePrefix != null ? codePrefix : "single-") + agentConfigCode);
		network.setDescription("Ephemeral single-agent chat network for agent " + agentConfigCode);
		network.setScenarioDescription("Single agent invoked directly from outside a chat, behind the chat input adapter");
		network.setAgents(new ArrayList<>(List.of(adapter, agent)));
		network.setMaxLoopIteration(2);
		return network;
	}
}
