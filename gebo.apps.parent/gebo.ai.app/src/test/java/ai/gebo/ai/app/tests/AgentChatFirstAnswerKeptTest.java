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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.tests.TestChatModel;
import ai.gebo.llms.agent.chat.service.impl.ReportWriterReactiveAgentServiceImpl;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.ChatNotificationContent.NotificationType;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMRequestGenerationPolicy;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatInfo;
import ai.gebo.llms.chat.abstraction.layer.repository.GUserChatSessionRepository;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.session.model.ChatInteractions;
import ai.gebo.llms.chat.abstraction.layer.session.model.GUserChatSession;
import ai.gebo.llms.chat.abstraction.layer.session.model.MinimalChatContext;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.service.ISinkUIEmitter;
import ai.gebo.llms.chat.pipelines.service.defaultsteps.impl.DefaultPipelineStreamingDelegatedStepServiceImpl;
import ai.gebo.security.services.ReactiveIdentityUtil;
import reactor.core.publisher.Flux;

/**
 * The first answer of a chat served by the agents network survives the chat
 * title written right after it.
 * <p>
 * The user interface asks for the chat title as soon as the last message of the
 * first answer arrives. The session may already be saved without that answer:
 * the default router stores the chat's language, detected on the first message,
 * while the request runs. A title generated from that copy and saved whole
 * after the handler ended the request wrote the interaction back without its
 * answer, which the reloaded chat then lacked.
 * <p>
 * The race is forced here: the session is saved without the answer as the router
 * does, the title is asked, on another thread, when the last message passes, and
 * the fake model takes a while to write it so that the title's save lands after
 * the request has been ended. Like {@link DefaultAgentsNetworkTest} the fake
 * model also drives the standard default network through the markers of its
 * prompts; the network is run through the pipeline step that wraps it
 * ({@link DefaultPipelineStreamingDelegatedStepServiceImpl}), the one that ends
 * the request.
 */
@TestPropertySource(properties = { "ai.gebo.agents.standard.enabled=true",
		"ai.gebo.agents.standard.default-chat-network-of-agents=DEFAULT_AGENTS_NETWORK" })
public class AgentChatFirstAnswerKeptTest extends AbstractBaseTestLLmsIntegrationTests {

	private static final String ANSWER_MARKER = "GEBO_TEST_FIRST_ANSWER_KEPT";
	private static final String TITLE = "Capital of France asked";
	private static final String WRITING_INSTRUCTION = "Write a concise direct answer to the user question.";
	// at least 30 characters: the router stores the chat's language only when the
	// message is long enough for the detection to be trusted
	private static final String USER_QUESTION = "What is the capital city of France, and how many people live there?";
	/** How long the fake model takes to write the title. */
	private static final long TITLE_WRITING_MILLIS = 1500L;
	/** The marker of the title prompt (its system prompt's first line). */
	private static final String TITLE_PROMPT_MARKER = "summarization assistant";

	@Autowired
	private DefaultPipelineStreamingDelegatedStepServiceImpl delegatedStep;
	@Autowired
	private IGChatSessionLifeCycleService lifeCycleService;
	@Autowired
	private GUserChatSessionRepository sessionsRepo;

	@Override
	protected void beforeEachCallback() throws Exception {
		TestChatModel.setGlobalResponseLogic(prompt -> {
			if (prompt == null) {
				return "";
			}
			if (prompt.contains(TITLE_PROMPT_MARKER)) {
				try {
					Thread.sleep(TITLE_WRITING_MILLIS);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
				return TITLE;
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
	public void testFirstAnswerSurvivesTheChatTitle() throws Exception {
		assertTrue(delegatedStep.isDefaultPipelineStreamingDefined(),
				"The agents network must be wired as the default streaming pipeline service");
		IGConfigurableChatModel model = chatModelRuntimeDao.findByCode(DEFAULT_TEST_CHAT_MODEL_CODE);

		GeboChatRequest request = new GeboChatRequest();
		request.setQuery(USER_QUESTION);
		lifeCycleService.createChatSession(request);
		final String sessionCode = request.getUserChatContextCode();
		assertNotNull(sessionCode, "A valid chat session context code must be assigned by the lifecycle service");

		// what the pipelines executor and the default router do before the step runs
		GeboChatResponse response = lifeCycleService.createEmptyResponse(request);
		LLMChatRequestResources resources = lifeCycleService.startRequest(request, model,
				LLMRequestGenerationPolicy.ADDING_RESOURCES_DO_NOT_FIT_TOKENS_BUDGET);
		MinimalChatContext minimalChatContext = lifeCycleService.getMinimalChatContext(request, 4000);
		lifeCycleService.setChatLanguage(request, "English");
		GUserChatSession savedEarly = sessionsRepo.findById(sessionCode).orElseThrow();
		assertEquals(1, savedEarly.getInteractions().size(),
				"The chat's language saved the session with its first interaction");
		assertNull(savedEarly.getInteractions().get(0).getResponse(),
				"The first interaction is saved without its answer while the request runs");

		ChatPipelineExecutionRuntimeData runtimeData = new ChatPipelineExecutionRuntimeData(null, 8192, resources,
				response, minimalChatContext, true);
		ISinkUIEmitter emitter = new ISinkUIEmitter() {
			@Override
			public void notifyUser(String code, String message, String icon, Long duration,
					NotificationType notificationType) {
			}

			@Override
			public void next(GeboChatMessageEnvelope event) {
			}

			@Override
			public void error(Throwable error) {
				LOGGER.error("Error emitted by the agents network", error);
			}

			@Override
			public void complete() {
			}
		};

		// the title is asked as the user interface does: as soon as the last message
		// arrives, while the stream goes on
		final ReactiveIdentityUtil runAs = ReactiveIdentityUtil.create();
		final AtomicReference<CompletableFuture<GUserChatInfo>> title = new AtomicReference<>();
		Flux<GeboChatMessageEnvelope> flux = delegatedStep.execute(runtimeData, emitter, model, model)
				.doOnNext(envelope -> {
					if (envelope != null && envelope.isLastMessage() && title.get() == null) {
						title.set(CompletableFuture.supplyAsync(() -> {
							try {
								return runAs.doRunAsWithReturnAndException(
										() -> lifeCycleService.suggestChatDescription(sessionCode));
							} catch (Exception e) {
								throw new IllegalStateException("The chat title could not be suggested", e);
							}
						}));
					}
				});
		List<GeboChatMessageEnvelope> streamed = flux.collectList().block(Duration.ofSeconds(90));
		assertNotNull(streamed, "The agents network must produce a non-null output stream");
		assertNotNull(title.get(), "The stream must carry a last message, on which the title is asked");
		GUserChatInfo suggested = title.get().get(60, TimeUnit.SECONDS);
		assertNotNull(suggested, "A chat title must be suggested");
		assertEquals(TITLE, suggested.getDescription(), "The suggested title is the one the model wrote");

		GUserChatSession saved = sessionsRepo.findById(sessionCode).orElseThrow();
		assertEquals(TITLE, saved.getDescription(), "The chat keeps the suggested title");
		assertEquals(1, saved.getInteractions().size(), "The chat has its first interaction");
		ChatInteractions interaction = saved.getInteractions().get(0);
		assertNotNull(interaction.getResponse(),
				"The first interaction keeps its answer once the title is written after it");
		assertNotNull(interaction.getResponse().getQueryResponse(), "The saved answer has its text");
		assertTrue(String.valueOf(interaction.getResponse().getQueryResponse()).contains(ANSWER_MARKER),
				"The saved answer is the one the network wrote");
		assertFalse(saved.getInteractions().stream().anyMatch(x -> x.getResponse() == null),
				"No interaction is saved without its answer once the request ended");
	}
}
