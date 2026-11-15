/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.ai.app.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCalling;
import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCallingManagerFactory;
import ai.gebo.llms.abstraction.layer.tests.TestChatModel;
import ai.gebo.llms.agent.chat.service.impl.ReactiveChatAgentsNetworkStreamingOutputChatPipelineService;
import ai.gebo.llms.agent.chat.service.impl.ReportWriterReactiveAgentServiceImpl;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.ChatNotificationContent.NotificationType;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.session.model.CSSConsolidatedChatHistory;
import ai.gebo.llms.chat.abstraction.layer.session.model.MinimalChatContext;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.service.ISinkUIEmitter;
import ai.gebo.llms.chat.pipelines.service.IStreamingOutputChatPipelineService;
import ai.gebo.llms.chat.pipelines.service.impl.ChatPipelinesExecutorImpl;
import reactor.core.publisher.Flux;

/**
 * The whole chain at once: a chat request's tool calling, built as the pipelines
 * executor builds it, reaches every model call a real network of agents makes for
 * it - the coordinator's and the writer's - through the network step, the network
 * service, the agents and the model.
 */
@TestPropertySource(properties = { "ai.gebo.agents.standard.enabled=true",
		"ai.gebo.agents.standard.default-chat-network-of-agents=DEFAULT_AGENTS_NETWORK" })
public class ChatRunToolCallingEndToEndTest extends AbstractBaseTestLLmsIntegrationTests {
	private static final String WRITING_INSTRUCTION = "Write a concise direct answer to the user question.";
	private static final String USER_QUESTION = "What is the capital of France?";

	@Autowired
	private List<IStreamingOutputChatPipelineService> pipelineServices;
	@Autowired
	private IGChatSessionLifeCycleService lifeCycleService;
	@Autowired
	private GeboToolCallingManagerFactory toolCallingFactory;

	/** What each model call of a run was made with: the container found in its tool context, or null. */
	private final List<GeboToolCalling> seenByTheModel = new CopyOnWriteArrayList<>();
	private final List<Prompt> prompts = new CopyOnWriteArrayList<>();
	/** The beginning of each prompt, to name the call that went wrong. */
	private final List<String> heads = new CopyOnWriteArrayList<>();
	/** The chat session the test opened, removed after it. */
	private String sessionCode = null;

	@Override
	protected void beforeEachCallback() throws Exception {
		TestChatModel.setGlobalResponseLogic(prompt -> {
			if (prompt == null) {
				return "";
			}
			if (prompt.contains("coordinator-controller")) {
				return "{\n" //
						+ "  \"" + ReportWriterReactiveAgentServiceImpl.REPORT_WRITER_NETWORK_AGENT_SERVICE + "\": {\n" //
						+ "    \"deliveryOrder\": 1,\n" //
						+ "    \"concurrency\": \"SERIAL\",\n" //
						+ "    \"commandData\": \"" + WRITING_INSTRUCTION + "\"\n" //
						+ "  }\n" //
						+ "}";
			}
			if (prompt.contains("Writer/Reporter")) {
				return "The capital of France is Paris.";
			}
			return "";
		});
		TestChatModel.setGlobalPromptObserver(prompt -> {
			prompts.add(prompt);
			final String contents = prompt.getContents();
			heads.add(contents != null ? contents.substring(0, Math.min(160, contents.length())).replace('\n', ' ')
					: "");
			final Map<String, Object> toolContext = prompt.getOptions() instanceof ToolCallingChatOptions options
					? options.getToolContext()
					: null;
			seenByTheModel.add(toolContext != null ? GeboToolCalling.from(toolContext) : null);
		});
	}

	@Override
	protected void afterEachCallback() throws Exception {
		TestChatModel.clearGlobalResponseLogic();
		TestChatModel.clearGlobalPromptObserver();
	}

	/** Removes the session the test opened, while the test's identity still holds. */
	private void removeSession() throws Exception {
		if (sessionCode != null) {
			lifeCycleService.removeChatSession(sessionCode);
			sessionCode = null;
		}
	}

	private IStreamingOutputChatPipelineService agentsPipeline() {
		return pipelineServices.stream()
				.filter(s -> ReactiveChatAgentsNetworkStreamingOutputChatPipelineService.SERVICE_ID.equals(s.getStepId())).findFirst()
				.orElseThrow(() -> new IllegalStateException(
						"Agents-network pipeline service not present; is ai.gebo.agents.standard.enabled=true?"));
	}

	/** A request in a real chat session of the authenticated user, with or without a tool calling. */
	private ChatPipelineExecutionRuntimeData runtimeOf(GeboToolCalling toolCalling) throws Exception {
		GeboChatRequest request = new GeboChatRequest();
		request.setQuery(USER_QUESTION);
		lifeCycleService.createChatSession(request);
		sessionCode = request.getUserChatContextCode();
		assertNotNull(sessionCode, "the lifecycle service assigns the session");
		MinimalChatContext minimalChatContext = new MinimalChatContext();
		minimalChatContext.setCurrentRequest(request);
		LLMChatRequestResources resources = new LLMChatRequestResources();
		resources.setCurrentRequest(request);
		resources.setChathistory(new CSSConsolidatedChatHistory());
		return new ChatPipelineExecutionRuntimeData(null, 8192, resources, new GeboChatResponse(), minimalChatContext,
				true, toolCalling);
	}

	private List<GeboChatMessageEnvelope> run(ChatPipelineExecutionRuntimeData runtimeData) throws Exception {
		IGConfigurableChatModel model = chatModelRuntimeDao.findByCode(DEFAULT_TEST_CHAT_MODEL_CODE);
		final List<GeboChatMessageEnvelope> notified = new ArrayList<>();
		ISinkUIEmitter emitter = new ISinkUIEmitter() {
			@Override
			public void notifyUser(String code, String message, String icon, Long duration,
					NotificationType notificationType) {
			}

			@Override
			public void next(GeboChatMessageEnvelope event) {
				notified.add(event);
			}

			@Override
			public void error(Throwable error) {
				LOGGER.error("Error emitted by agents network", error);
			}

			@Override
			public void complete() {
			}
		};
		Flux<GeboChatMessageEnvelope> flux = agentsPipeline().execute(runtimeData, emitter, model, model);
		List<GeboChatMessageEnvelope> streamed = flux.collectList().block(Duration.ofSeconds(90));
		assertNotNull(streamed, "the network answers");
		List<GeboChatMessageEnvelope> all = new ArrayList<>(streamed);
		all.addAll(notified);
		return all;
	}

	@Test
	public void theRequestsToolCallingReachesEveryModelCallOfTheNetwork() throws Exception {
		try {
			theRequestsToolCallingReachesEveryModelCall();
		} finally {
			removeSession();
		}
	}

	private void theRequestsToolCallingReachesEveryModelCall() throws Exception {
		final ChatPipelineExecutionRuntimeData runtimeData = runtimeOf(null);
		final String requestId = runtimeData.getCurrentRequest().getId();
		final GeboToolCalling toolCalling = toolCallingFactory.builder()
				.caller(ChatPipelinesExecutorImpl.CHAT_CALLER_PREFIX + requestId).build();
		final ChatPipelineExecutionRuntimeData governed = new ChatPipelineExecutionRuntimeData(null, 8192,
				runtimeData.getRequestResources(), runtimeData.getChatResponse(), runtimeData.getMinimalChatContext(),
				true, toolCalling);

		final List<GeboChatMessageEnvelope> all = run(governed);

		assertFalse(all.isEmpty(), "the network emits its answer");
		assertTrue(seenByTheModel.size() >= 2, "the coordinator and the writer both called the model, saw "
				+ seenByTheModel.size() + " call(s)");
		for (int call = 0; call < seenByTheModel.size(); call++) {
			final GeboToolCalling seen = seenByTheModel.get(call);
			assertNotNull(seen, "model call " + (call + 1) + " of " + seenByTheModel.size()
					+ " was made without the request's tool calling: " + heads.get(call));
			assertEquals(ChatPipelinesExecutorImpl.CHAT_CALLER_PREFIX + requestId, seen.getCaller(),
					"model call " + (call + 1) + " names the chat as its caller");
		}
		assertTrue(toolCalling.getCalls() >= 1, "the container records the calls made with it");
	}
}
