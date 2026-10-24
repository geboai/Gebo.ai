/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.a2aserver.runtime;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.services.AgentException;
import ai.gebo.architecture.agents.services.IAgentsNetworkDao;
import ai.gebo.architecture.agents.services.INotificationSink;
import ai.gebo.architecture.agents.services.NetworkOfAgentsException;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.knlowledgebase.model.contents.UserUploadedContent;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.ToolCallsListener;
import ai.gebo.llms.agent.chat.service.IGReactiveChatAgentsNetworkService;
import ai.gebo.llms.agent.chat.service.IGReactiveChatAgentsNetworkServiceFactory;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMRequestGenerationPolicy;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.services.IGUserUploadContentHandler;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionStateShrinkerService;
import ai.gebo.llms.chat.abstraction.layer.session.model.MinimalChatContext;
import ai.gebo.model.OperationStatus;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.security.services.ReactiveIdentityUtil;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

/**
 * Runs an exported network of agents in the environment it has when it answers
 * a chat.
 * <p>
 * A network that reaches knowledge bases is only as good as what it is pointed
 * at. In a chat that is the chat profile's doing: which knowledge bases, which
 * chat and embedding models, which retrieval thresholds, which rules. Run from
 * outside a chat without one, the same network sees every knowledge base its
 * caller can see and the platform's default thresholds - a different thing
 * altogether, and not something an A2A caller can correct, having no screen to
 * choose on.
 * <p>
 * So this builds the request the chat would have built and lets the chat's own
 * services furnish it: {@link IGChatSessionLifeCycleService#getSessionChatModel}
 * resolves the model, {@link IGChatSessionLifeCycleService#startRequest} the
 * resources (the profile, its knowledge bases, the history), and
 * {@link IGChatSessionLifeCycleService#getMinimalChatContext} the context. The
 * network then runs on exactly what a chat would have handed it.
 * <p>
 * The answer is read from the network's own output. Both ways of reading it are
 * the same run: the frames as they come for a caller that streams, and the
 * frame marked as the last one for a caller that waits.
 */
@Service
@AllArgsConstructor
public class A2AChatEnvironmentRunner {
	private static final Logger LOGGER = LoggerFactory.getLogger(A2AChatEnvironmentRunner.class);

	private final IGRuntimeBinder runtimeBinder;
	private final IGChatSessionLifeCycleService chatSessionLifecycleService;
	private final IGChatModelRuntimeConfigurationDao chatModelsDao;
	/** Stores what a caller attaches, when this installation takes uploads at all. */
	private final ObjectProvider<IGUserUploadContentHandler> uploadContentHandler;

	/** What a run produced: the frames, and the response of the last one. */
	public static class ChatEnvironmentRun {
		private final Flux<GeboChatMessageEnvelope> frames;
		private final GeboChatResponse response;
		private final IGReactiveChatAgentsNetworkService service;

		ChatEnvironmentRun(Flux<GeboChatMessageEnvelope> frames, GeboChatResponse response,
				IGReactiveChatAgentsNetworkService service) {
			this.frames = frames;
			this.response = response;
			this.service = service;
		}

		/** Every frame the network produced, in order. */
		public Flux<GeboChatMessageEnvelope> getFrames() {
			return frames;
		}

		/** The response the run filled in, which the last frame also carries. */
		public GeboChatResponse getResponse() {
			return response;
		}

		/** Releases the network service of the run. */
		public void dispose() {
			if (service != null) {
				service.dispose();
			}
		}
	}

	/**
	 * Builds the request a chat would have built for this run.
	 *
	 * @param text            what the caller asked
	 * @param chatProfileCode the profile the export states, or null for none
	 * @param chatContextCode the chat this run belongs to, as the caller named it,
	 *                        or null to let one be created
	 */
	public GeboChatRequest requestOf(String text, String chatProfileCode, String chatContextCode) {
		GeboChatRequest request = new GeboChatRequest();
		request.setQuery(text != null ? text : "");
		request.setStreamResponse(true);
		if (chatProfileCode != null && !chatProfileCode.isBlank()) {
			request.setChatProfileCode(chatProfileCode);
		}
		if (chatContextCode != null && !chatContextCode.isBlank()) {
			// The caller's own notion of a conversation: the same one twice goes on with
			// what was said before, as it does in a chat.
			request.setUserChatContextCode(chatContextCode);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("A2A request built with chatProfileCode:" + chatProfileCode + " chatContext:"
					+ chatContextCode);
		}
		return request;
	}

	/**
	 * Runs the named network on the environment of the given request.
	 *
	 * @throws AgentException when the network does not exist or the run cannot be
	 *                        prepared
	 */
	public ChatEnvironmentRun run(String networkCode, GeboChatRequest request, A2AInboundResources inbound,
			INotificationSink sink) throws AgentException {
		final GAgentsNetwork network = runtimeBinder.getImplementationOf(IAgentsNetworkDao.class)
				.findByCode(networkCode);
		if (network == null) {
			throw new AgentException("The exported network of agents does not exist: " + networkCode);
		}
		final IGReactiveChatAgentsNetworkServiceFactory factory = runtimeBinder
				.getImplementationOf(IGReactiveChatAgentsNetworkServiceFactory.class);
		if (factory == null) {
			throw new AgentException("The chat network of agents factory is not available");
		}
		IGReactiveChatAgentsNetworkService service = null;
		try {
			final IGConfigurableChatModel chatModel = chatSessionLifecycleService.getSessionChatModel(request);
			final IGConfigurableChatModel serviceModel = chatModelsDao.defaultHandler();
			// The session has to exist before its request can be started, and a caller
			// naming a conversation we have never seen is naming a new one.
			chatSessionLifecycleService.ensureChatSessionExists(request);
			// What the caller sent with its message is attached before the resources of
			// the request are built, so the run is prepared knowing about it.
			attachInbound(request, inbound, chatModel);
			final GeboChatResponse response = chatSessionLifecycleService.createEmptyResponse(request);
			final LLMChatRequestResources resources = chatSessionLifecycleService.startRequest(request, chatModel,
					LLMRequestGenerationPolicy.ADDING_RESOURCES_DO_NOT_FIT_TOKENS_BUDGET);
			final MinimalChatContext minimalChatContext = chatSessionLifecycleService.getMinimalChatContext(request,
					IGChatSessionStateShrinkerService.serviceModelContextBudget(serviceModel));
			// Every tool the run calls is recorded into the response, so what is sent back
			// says what was done to answer, as a chat's response does.
			final ToolCallsListener toolCalls = ToolCallsListener.appendingTo(response.getCalledFunctions());
			resources.setToolCallsListener(toolCalls);
			minimalChatContext.setToolCallsListener(toolCalls);
			final ChatPipelineExecutionRuntimeData runtimeData = new ChatPipelineExecutionRuntimeData(null,
					chatModel.getContextLength(), resources, response, minimalChatContext, true);
			final ReactiveIdentityUtil runAs = ReactiveIdentityUtil.create();
			service = factory.create(network, sink, ChatPipelineExecutionRuntimeData.class,
					GeboChatMessageEnvelope.class, runAs);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Running exported network:" + network.getCode() + " in the environment of chat profile:"
						+ request.getChatProfileCode() + " over " + knowledgeBasesOf(resources)
						+ " knowledge base(s), context window:" + chatModel.getContextLength());
			}
			// The network's own frames. The sink buffers what it emits, so reading them
			// after the run loses nothing and reading them as they come loses nothing
			// either: it is one run, read one way or the other.
			final Flux<GeboChatMessageEnvelope> frames = service.getFlux();
			final GeboChatMessageEnvelope last = service.executeNetwork(resources.createChatRequestContext(),
					runtimeData, null);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Exported network:" + network.getCode() + " answered, last frame present:"
						+ (last != null));
			}
			return new ChatEnvironmentRun(frames, responseOf(last, response), service);
		} catch (AgentException ae) {
			disposeQuietly(service);
			throw ae;
		} catch (GeboChatSessionLifecycleException | GeboPersistenceException | NetworkOfAgentsException
				| LLMConfigException | java.io.IOException | RuntimeException e) {
			disposeQuietly(service);
			LOGGER.error("The run of the exported network '" + networkCode + "' failed", e);
			throw new AgentException(
					"The run of the exported network '" + networkCode + "' failed: " + e.getMessage(), e);
		}
	}

	/**
	 * Attaches what the caller sent with its message: the knowledge bases and the
	 * documents it stated, and the files it sent, stored in this conversation's own
	 * area and handed to the request as a chat hands over an upload.
	 */
	private void attachInbound(GeboChatRequest request, A2AInboundResources inbound,
			IGConfigurableChatModel chatModel) {
		if (inbound == null || !inbound.hasResources()) {
			return;
		}
		if (!inbound.getKnowledgeBases().isEmpty()) {
			request.setChoosedKnowledgeBases(List.copyOf(inbound.getKnowledgeBases()));
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("The caller asked the run to work on " + inbound.getKnowledgeBases().size()
						+ " stated knowledge base(s)");
			}
		}
		if (!inbound.getDocuments().isEmpty()) {
			final List<String> forced = new ArrayList<>(
					request.getForcedRequestDocuments() != null ? request.getForcedRequestDocuments() : List.of());
			forced.addAll(inbound.getDocuments());
			request.setForcedRequestDocuments(forced);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("The caller named " + inbound.getDocuments().size() + " document(s) for the run");
			}
		}
		if (inbound.getFiles().isEmpty()) {
			return;
		}
		final IGUserUploadContentHandler uploads = uploadContentHandler.getIfAvailable();
		if (uploads == null) {
			LOGGER.warn("The caller sent " + inbound.getFiles().size()
					+ " file(s) but this installation cannot take uploads: they are left out of the run");
			return;
		}
		final OperationStatus<List<UserUploadedContent>> stored = uploads
				.chatSessionUpload(request.getUserChatContextCode(), inbound.getFiles());
		final List<UserUploadedContent> contents = stored != null ? stored.getResult() : null;
		if (contents == null || contents.isEmpty()) {
			LOGGER.warn("The file(s) the caller sent could not be stored: they are left out of the run");
			return;
		}
		for (UserUploadedContent content : contents) {
			try {
				chatSessionLifecycleService.addUploadedDocument(request, content, chatModel,
						LLMRequestGenerationPolicy.ADDING_RESOURCES_DO_NOT_FIT_TOKENS_BUDGET);
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("The file " + content.getFileName() + " the caller sent is part of the run");
				}
			} catch (GeboChatSessionLifecycleException | java.io.IOException | RuntimeException notAttached) {
				// One attachment that cannot be taken in must not lose the question it came
				// with: the run goes on without it, and the log says which.
				LOGGER.error("The file " + content.getFileName()
						+ " the caller sent could not be added to the run; it goes on without it", notAttached);
			}
		}
	}

	/**
	 * The response of the run: the one the last frame carries when it carries one,
	 * else the one the run filled in.
	 */
	private GeboChatResponse responseOf(GeboChatMessageEnvelope last, GeboChatResponse filledIn) {
		if (last != null && last.getContent() instanceof GeboChatResponse answered) {
			return answered;
		}
		return filledIn;
	}

	private int knowledgeBasesOf(LLMChatRequestResources resources) {
		final List<String> codes = resources != null ? resources.getAvailableKnowledgeBaseCodes() : null;
		return codes != null ? codes.size() : 0;
	}

	private void disposeQuietly(IGReactiveChatAgentsNetworkService service) {
		if (service == null) {
			return;
		}
		try {
			service.dispose();
		} catch (RuntimeException disposing) {
			LOGGER.warn("Disposing the network service of a failed A2A run failed", disposing);
		}
	}
}
