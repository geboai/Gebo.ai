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
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.GAgentsNetwork.CommunicationPolicy;
import ai.gebo.architecture.agents.services.AgentException;
import ai.gebo.architecture.agents.services.IAgentsNetworkDao;
import ai.gebo.architecture.agents.services.IGAgentsNetworkService;
import ai.gebo.architecture.agents.services.IGAgentsNetworkServiceFactory;
import ai.gebo.architecture.agents.services.IGAgentsNetworkServiceFactoryRepositoryPattern;
import ai.gebo.architecture.agents.services.INotificationSink;
import ai.gebo.architecture.agents.services.INotificationSink.NotificationObject.NotificationType;
import ai.gebo.architecture.agents.services.NetworkOfAgentsException;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.agent.chat.service.IGReactiveChatAgentsNetworkService;
import ai.gebo.llms.agent.chat.service.IGReactiveChatAgentsNetworkServiceFactory;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.DeliverableIntent;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.session.model.CSSConsolidatedChatHistory;
import ai.gebo.llms.chat.abstraction.layer.session.model.MinimalChatContext;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.security.services.ReactiveIdentityUtil;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

/**
 * Runs a network of agents, or a single agent, outside a chat: a text request
 * in, the text of the final answer out, as the caller whose identity is in the
 * current security context. The run works on every knowledge base that caller
 * can see.
 * <p>
 * This is what an exported A2A skill and an MCP tool built from an agent or a
 * network execute. Two kinds of network exist:
 * <ul>
 * <li>text networks, whose input nodes take a {@link String} and whose output
 * nodes give one (remote A2A agents, custom agents): they run through
 * {@link GTextAgentsNetworkServiceFactory};</li>
 * <li>chat networks, like the standard default one, whose input node adapts
 * the chat runtime data and whose writer answers with chat envelopes: they run
 * through the chat factory on a request synthesized from the text, as the chat
 * step does, and the answer is read from the final envelope.</li>
 * </ul>
 * A network is run as a text network first; one whose nodes are not coherent
 * with text runs as a chat network. A single agent runs as a network of its
 * own, with the chat input adapter in front of it when it needs the chat
 * runtime data. Collaborators are looked up through the runtime binder, as the
 * agents' own services are.
 */
@Service
@AllArgsConstructor
public class GTextAgentsNetworkRunner {
	private static final Logger LOGGER = LoggerFactory.getLogger(GTextAgentsNetworkRunner.class);
	/** The context window the synthesized chat request is sized on (no history is carried). */
	private static final int SYNTHESIZED_REQUEST_CONTEXT_WINDOW = 8192;
	/** How long the answer of a chat network is waited for once its run returned. */
	private static final Duration CHAT_ANSWER_COLLECTION_TIMEOUT = Duration.ofSeconds(30);

	/** A sink that only logs what the network notifies. */
	public static final INotificationSink LOGGING_SINK = state -> {
		NotificationType type = state != null && state.getNotificationType() != null ? state.getNotificationType()
				: NotificationType.INFO;
		switch (type) {
		case DEBUG -> LOGGER.debug("Network notification: {}", state);
		case ERROR -> LOGGER.error("Network notification: {}", state);
		default -> LOGGER.info("Network notification: {}", state);
		}
	};

	private final IGRuntimeBinder runtimeBinder;

	/**
	 * Runs the network whose code is given.
	 *
	 * @throws AgentException when the network does not exist or its run fails
	 */
	public String runNetwork(String networkCode, String inputText, INotificationSink sink) throws AgentException {
		IAgentsNetworkDao networkDao = runtimeBinder.getImplementationOf(IAgentsNetworkDao.class);
		GAgentsNetwork network = networkDao.findByCode(networkCode);
		if (network == null) {
			throw new AgentException("Network of agents '" + networkCode + "' does not exist");
		}
		return runNetwork(network, inputText, sink);
	}

	/**
	 * Runs a single agent, as a network made of that agent alone; an agent that
	 * takes the chat runtime data gets the chat input adapter in front of it.
	 *
	 * @param codePrefix prefix of the code of the ephemeral network, for the logs
	 * @throws AgentException when the agent configuration is missing or the run fails
	 */
	public String runAgent(String agentConfigCode, String codePrefix, String inputText, INotificationSink sink)
			throws AgentException {
		if (agentConfigCode == null || agentConfigCode.isBlank()) {
			throw new AgentException("A single agent run needs the agent configuration code");
		}
		final INotificationSink notifications = sink != null ? sink : LOGGING_SINK;
		final ReactiveIdentityUtil runAs = ReactiveIdentityUtil.create();
		final RunContext context = runContext(inputText);
		GAgentsNetwork alone = singleAgentNetwork(agentConfigCode, codePrefix);
		try {
			return runAsText(alone, notifications, runAs, context);
		} catch (NetworkOfAgentsException notText) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Agent {} is not a text agent ({}): it runs behind the chat input adapter", agentConfigCode,
						notText.getMessage());
			}
			return runAsChat(adaptedSingleAgentNetwork(agentConfigCode, codePrefix), notifications, runAs, context);
		}
	}

	/**
	 * Runs the given network (persisted or ephemeral) as the current caller.
	 */
	public String runNetwork(GAgentsNetwork network, String inputText, INotificationSink sink)
			throws AgentException {
		final INotificationSink notifications = sink != null ? sink : LOGGING_SINK;
		final ReactiveIdentityUtil runAs = ReactiveIdentityUtil.create();
		final RunContext context = runContext(inputText);
		try {
			return runAsText(network, notifications, runAs, context);
		} catch (NetworkOfAgentsException notText) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Network {} is not a text network ({}): it runs as a chat network", network.getCode(),
						notText.getMessage());
			}
			return runAsChat(network, notifications, runAs, context);
		}
	}

	/** What a run needs from the caller: the request, their knowledge bases, the environment. */
	private record RunContext(String inputText, List<String> knowledgeBases, Map<String, Object> environment,
			IChatRequestContext requestContext) {
	}

	private RunContext runContext(String inputText) {
		final String text = inputText != null ? inputText : "";
		// outside a chat the task works on all the knowledge bases the caller can see,
		// read here, where the caller's identity is
		final UserKnowledgeBasesExecutionEnvironment userEnvironment = runtimeBinder
				.getImplementationOf(UserKnowledgeBasesExecutionEnvironment.class);
		final List<String> knowledgeBases = userEnvironment.knowledgeBaseCodes();
		final Map<String, Object> environment = userEnvironment.networkEnvironment(knowledgeBases);
		// what the chat step tells a network about a request without stated intent
		environment.put(StandardAgentsNetworkEnvironmentEntries.USER_INTENT, DeliverableIntent.SUMMARY);
		environment.put(StandardAgentsNetworkEnvironmentEntries.SEARCH_REQUESTED, Boolean.FALSE);
		environment.put(StandardAgentsNetworkEnvironmentEntries.SEARCH_FORBIDDEN, Boolean.FALSE);
		return new RunContext(text, knowledgeBases, environment, userEnvironment.requestContext(text, knowledgeBases));
	}

	/**
	 * The text path: input and output nodes coherent with {@link String}.
	 *
	 * @throws NetworkOfAgentsException when the network's nodes are not text nodes
	 */
	private String runAsText(GAgentsNetwork network, INotificationSink sink, ReactiveIdentityUtil runAs,
			RunContext context) throws AgentException, NetworkOfAgentsException {
		IGAgentsNetworkServiceFactory<String, String, GTextAgentsNetworkService> factory = runtimeBinder
				.getImplementationOf(IGAgentsNetworkServiceFactoryRepositoryPattern.class)
				.getFactory(GTextAgentsNetworkService.class);
		if (factory == null) {
			throw new AgentException("The text network of agents factory is not available");
		}
		IGAgentsNetworkService<String, String> service = factory.create(network, sink, String.class, String.class,
				runAs);
		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Network {} runs as text over {} knowledge base(s) of the caller", network.getCode(),
						context.knowledgeBases().size());
			}
			String output = service.executeNetwork(context.requestContext(), context.inputText(),
					context.environment());
			return output != null ? output : "";
		} catch (AgentException ae) {
			throw ae;
		} catch (Throwable t) {
			LOGGER.error("The run of network '" + network.getCode() + "' failed", t);
			throw new AgentException("The run of network '" + network.getCode() + "' failed: " + t.getMessage(), t);
		} finally {
			service.dispose();
		}
	}

	/**
	 * The chat path: the network runs on a request synthesized from the text, as
	 * the chat step runs it, and answers with chat envelopes; the answer is the
	 * text of the last response envelope, or the chunks the writer streamed.
	 */
	private String runAsChat(GAgentsNetwork network, INotificationSink sink, ReactiveIdentityUtil runAs,
			RunContext context) throws AgentException {
		IGReactiveChatAgentsNetworkServiceFactory factory = runtimeBinder
				.getImplementationOf(IGReactiveChatAgentsNetworkServiceFactory.class);
		if (factory == null) {
			throw new AgentException("The chat network of agents factory is not available");
		}
		GeboChatRequest request = new GeboChatRequest();
		request.setQuery(context.inputText());
		MinimalChatContext minimalChatContext = new MinimalChatContext();
		minimalChatContext.setCurrentRequest(request);
		LLMChatRequestResources resources = new LLMChatRequestResources();
		resources.setCurrentRequest(request);
		resources.setChathistory(new CSSConsolidatedChatHistory());
		resources.setAvailableKnowledgeBaseCodes(context.knowledgeBases());
		ChatPipelineExecutionRuntimeData runtimeData = new ChatPipelineExecutionRuntimeData(null,
				SYNTHESIZED_REQUEST_CONTEXT_WINDOW, resources, new GeboChatResponse(), minimalChatContext, true);
		IGReactiveChatAgentsNetworkService service = null;
		try {
			service = factory.create(network, sink, ChatPipelineExecutionRuntimeData.class,
					GeboChatMessageEnvelope.class, runAs);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Network {} runs as a chat network over {} knowledge base(s) of the caller",
						network.getCode(), context.knowledgeBases().size());
			}
			// the writer's envelopes buffer in the network's sink until read: the run
			// completes first, the answer is read after
			Flux<GeboChatMessageEnvelope> flux = service.getFlux();
			GeboChatMessageEnvelope last = service.executeNetwork(resources.createChatRequestContext(), runtimeData,
					context.environment());
			List<GeboChatMessageEnvelope> streamed = flux.collectList().block(CHAT_ANSWER_COLLECTION_TIMEOUT);
			return answerOf(last, streamed);
		} catch (AgentException ae) {
			throw ae;
		} catch (Throwable t) {
			LOGGER.error("The run of network '" + network.getCode() + "' failed", t);
			throw new AgentException("The run of network '" + network.getCode() + "' failed: " + t.getMessage(), t);
		} finally {
			if (service != null) {
				service.dispose();
			}
		}
	}

	/**
	 * The text of a chat network's answer: the last response envelope's text when
	 * there is one, else the chunks the writer streamed, in order.
	 */
	static String answerOf(GeboChatMessageEnvelope last, List<GeboChatMessageEnvelope> streamed) {
		List<GeboChatMessageEnvelope> all = new ArrayList<>();
		if (streamed != null) {
			all.addAll(streamed);
		}
		if (last != null) {
			all.add(last);
		}
		String response = null;
		StringBuilder chunks = new StringBuilder();
		for (GeboChatMessageEnvelope envelope : all) {
			Object content = envelope != null ? envelope.getContent() : null;
			if (content instanceof GeboChatResponse chatResponse && chatResponse.getQueryResponse() != null
					&& !chatResponse.getQueryResponse().isBlank()) {
				response = chatResponse.getQueryResponse();
			} else if (content instanceof String chunk) {
				chunks.append(chunk);
			}
		}
		return response != null ? response : chunks.toString();
	}

	/**
	 * An ephemeral network made of one agent, input and output node at once.
	 */
	public GAgentsNetwork singleAgentNetwork(String agentConfigCode, String codePrefix) throws AgentException {
		if (agentConfigCode == null || agentConfigCode.isBlank()) {
			throw new AgentException("A single agent run needs the agent configuration code");
		}
		AgentNetworkParticipant participant = new AgentNetworkParticipant();
		participant.setAgentConfigCode(agentConfigCode);
		participant.setInputNode(true);
		participant.setOutputNode(true);
		participant.setAllowedToNotifyUser(true);
		participant.setCommunicationPolicy(CommunicationPolicy.ALLOW_ALL);
		GAgentsNetwork network = new GAgentsNetwork();
		network.setCode((codePrefix != null ? codePrefix : "single-") + agentConfigCode);
		network.setDescription("Ephemeral single-agent network for agent " + agentConfigCode);
		network.setScenarioDescription("Single agent invoked directly from outside a chat");
		network.setAgentsNetworkServiceFactoryId(GTextAgentsNetworkServiceFactory.FACTORY_ID);
		network.setAgents(List.of(participant));
		network.setMaxLoopIteration(2);
		return network;
	}

	/**
	 * An ephemeral chat network made of the chat input adapter (the input node,
	 * turning the chat runtime data into the query) and the agent (the output
	 * node), for an agent that answers with chat envelopes.
	 */
	private GAgentsNetwork adaptedSingleAgentNetwork(String agentConfigCode, String codePrefix) {
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
		network.setAgents(List.of(adapter, agent));
		network.setMaxLoopIteration(2);
		return network;
	}
}
