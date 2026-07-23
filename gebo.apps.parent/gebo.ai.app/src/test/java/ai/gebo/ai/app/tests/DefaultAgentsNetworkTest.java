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
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.tests.TestChatModel;
import ai.gebo.llms.agent.chat.service.impl.ReactiveChatAgentsNetworkStreamingOutputChatPipelineService;
import ai.gebo.llms.agent.chat.service.impl.ReportWriterReactiveAgentServiceImpl;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.ChatNotificationContent.NotificationType;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.repository.GUserChatSessionRepository;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.session.model.CSSConsolidatedChatHistory;
import ai.gebo.llms.chat.abstraction.layer.session.model.GUserChatSession;
import ai.gebo.llms.chat.abstraction.layer.session.model.MinimalChatContext;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.service.ISinkUIEmitter;
import ai.gebo.llms.chat.pipelines.service.IStreamingOutputChatPipelineService;
import reactor.core.publisher.Flux;

/**
 * Integration test for the standard default agents network (controller +
 * searchers + report writer) wired as the default streaming chat pipeline.
 * <p>
 * The whole infrastructure runs against testcontainers; only the LLM is mocked
 * via {@link TestChatModel}. The single default chat model serves every agent,
 * so a test-global response function branches on recognizable markers in each
 * agent's rendered prompt:
 * <ul>
 * <li>the coordinator-controller prompt -&gt; a structured routing plan that
 * activates only the report writer (so the search path, which needs ingested
 * content, is not exercised here);</li>
 * <li>the writer/reporter prompt -&gt; the final answer text.</li>
 * </ul>
 * Unlike a bare unit invocation, the network is driven under a coherent runtime:
 * an authenticated Spring security context for the default all-roles user
 * (impersonated for every test by {@link AbstractBaseIntegrationTest}) and a
 * real {@link GUserChatSession} created through the chat session lifecycle
 * service, with the request bound to that session's context code. This is what
 * the pipeline relies on to resolve the session-available knowledge bases and to
 * propagate the caller identity into the reactive network execution.
 */
@TestPropertySource(properties = "ai.gebo.agents.standard.enabled=true")
public class DefaultAgentsNetworkTest extends AbstractBaseTestLLmsIntegrationTests {

	private static final String ANSWER_MARKER = "GEBO_TEST_ANSWER_42";
	private static final String WRITING_INSTRUCTION = "Write a concise direct answer to the user question.";
	private static final String USER_QUESTION = "What is the capital of France?";

	@Autowired
	private List<IStreamingOutputChatPipelineService> pipelineServices;
	@Autowired
	private IGChatSessionLifeCycleService lifeCycleService;
	@Autowired
	private GUserChatSessionRepository sessionsRepo;

	@Override
	protected void beforeEachCallback() throws Exception {
		// Program the (shared) fake model. Set as a static hook so it survives the
		// per-agent cloneWithOptions(...) that JSON round-trips the configuration.
		TestChatModel.setGlobalResponseLogic(prompt -> {
			if (prompt == null) {
				return "";
			}
			// Coordinator-controller turn: emit a routing plan that activates only the
			// report writer. The schema marks every reachable agent as required, but the
			// routing engine skips any envelope whose commandData is null, so a partial
			// map is enough.
			if (prompt.contains("coordinator-controller")) {
				return "{\n" //
						+ "  \"" + ReportWriterReactiveAgentServiceImpl.REPORT_WRITER_NETWORK_AGENT_SERVICE + "\": {\n" //
						+ "    \"deliveryOrder\": 1,\n" //
						+ "    \"concurrency\": \"SERIAL\",\n" //
						+ "    \"commandData\": \"" + WRITING_INSTRUCTION + "\"\n" //
						+ "  }\n" //
						+ "}";
			}
			// Writer/reporter turn: produce the final user-facing answer.
			if (prompt.contains("Writer/Reporter")) {
				return ANSWER_MARKER + ": the capital of France is Paris.";
			}
			return "";
		});
	}

	@Override
	protected void afterEachCallback() throws Exception {
		TestChatModel.clearGlobalResponseLogic();
	}

	@Test
	public void testDefaultNetworkRoutesToReportWriter() throws Exception {
		// Locate the agents-network pipeline (registered only when
		// ai.gebo.agents.standard.enabled=true).
		IStreamingOutputChatPipelineService agentsPipeline = pipelineServices.stream()
				.filter(s -> s instanceof ReactiveChatAgentsNetworkStreamingOutputChatPipelineService).findFirst()
				.orElseThrow(() -> new IllegalStateException(
						"Agents-network pipeline service not present; is ai.gebo.agents.standard.enabled=true?"));

		// Coherent runtime identity: the default all-roles user is created in
		// prepareEnvironment() and impersonated for every test in
		// AbstractBaseIntegrationTest, so the synchronous session / knowledge-base
		// resolution and the reactive network execution (which samples the identity
		// through ReactiveIdentityUtil) both run authenticated.

		// Initialize a real chat session for the current user and bind the request to
		// it: the agents pipeline resolves the session-available knowledge bases from
		// this context code. Leaving the code null lets the lifecycle service create
		// and assign a valid session.
		GeboChatRequest request = new GeboChatRequest();
		request.setQuery(USER_QUESTION);
		lifeCycleService.createChatSession(request);
		final String sessionCode = request.getUserChatContextCode();
		assertNotNull(sessionCode, "A valid chat session context code must be assigned by the lifecycle service");
		Optional<GUserChatSession> persistedSession = sessionsRepo.findById(sessionCode);
		assertTrue(persistedSession.isPresent(),
				"The chat session must be persisted and resolvable by its context code: " + sessionCode);
		assertEquals(DEFAULT_ALL_ROLES_USER, persistedSession.get().getUsername(),
				"The chat session must belong to the authenticated user");

		MinimalChatContext minimalChatContext = new MinimalChatContext();
		minimalChatContext.setCurrentRequest(request);

		LLMChatRequestResources resources = new LLMChatRequestResources();
		resources.setCurrentRequest(request);
		resources.setChathistory(new CSSConsolidatedChatHistory());

		ChatPipelineExecutionRuntimeData runtimeData = new ChatPipelineExecutionRuntimeData(null, 8192, resources,
				new GeboChatResponse(), minimalChatContext, true);

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

		Flux<GeboChatMessageEnvelope> flux = agentsPipeline.execute(runtimeData, emitter, model, model);
		List<GeboChatMessageEnvelope> streamed = flux.collectList().block(Duration.ofSeconds(90));

		assertNotNull(streamed, "The agents network must produce a non-null output stream");

		List<GeboChatMessageEnvelope> all = new ArrayList<>(streamed);
		all.addAll(notified);
		assertFalse(all.isEmpty(), "The agents network must emit at least one message envelope");

		// The report writer streams the answer in small fragments and a final
		// GeboChatResponse carrying the full text, so accumulate fragments and also
		// inspect the consolidated response.
		StringBuilder accumulatedText = new StringBuilder();
		boolean foundInResponse = false;
		for (GeboChatMessageEnvelope envelope : all) {
			Object content = envelope != null ? envelope.getContent() : null;
			if (content instanceof String s) {
				accumulatedText.append(s);
			} else if (content instanceof GeboChatResponse response && response.getQueryResponse() != null
					&& response.getQueryResponse().contains(ANSWER_MARKER)) {
				foundInResponse = true;
			}
		}

		assertTrue(foundInResponse || accumulatedText.toString().contains(ANSWER_MARKER),
				"The report writer's answer (" + ANSWER_MARKER + ") must appear in the streamed network output");
	}
}
