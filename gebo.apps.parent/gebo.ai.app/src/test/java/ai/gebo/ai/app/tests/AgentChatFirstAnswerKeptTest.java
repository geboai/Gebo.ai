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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

import ai.gebo.llms.abstraction.layer.tests.TestChatModel;
import ai.gebo.llms.agent.chat.service.impl.ReportWriterReactiveAgentServiceImpl;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatInfo;
import ai.gebo.llms.chat.abstraction.layer.repository.GUserChatSessionRepository;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.session.model.ChatInteractions;
import ai.gebo.llms.chat.abstraction.layer.session.model.GUserChatSession;
import ai.gebo.llms.chat.pipelines.model.PipelineRoutingInfosMessageEnvelope;
import ai.gebo.llms.chat.pipelines.service.IChatPipelineService;
import ai.gebo.llms.chat.pipelines.service.defaultsteps.impl.DefaultPipelineStreamingDelegatedStepServiceImpl;
import ai.gebo.security.services.ReactiveIdentityUtil;
import reactor.core.publisher.Flux;

/**
 * The first answer of a chat served by the agents network survives the chat
 * title written right after it, and the pipelines executor saves it before the
 * client gets the last message.
 * <p>
 * The user interface asks for the chat title as soon as the last message of the
 * first answer arrives. The session may already be saved without that answer: the
 * default router stores, while the request runs, the chat's language (detected on
 * the first message) and the language the user asked for. A title generated from
 * that copy and saved whole after the request ended wrote the interaction back
 * without its answer, which the reloaded chat then lacked.
 * <p>
 * The chat runs through the pipeline service as the client does. The fake model
 * reports the language the message asks for, so the router saves the session
 * without the answer as it does with a real model; the title is asked, on another
 * thread, when the last message passes, and the fake model takes a while to write
 * it so that the title's save lands after the request has been ended. Like
 * {@link DefaultAgentsNetworkTest} the fake model also drives the standard default
 * network through the markers of its prompts; with the network wired as the default
 * pipeline service the router routes to it without asking the model.
 */
@TestPropertySource(properties = { "ai.gebo.agents.standard.enabled=true",
		"ai.gebo.agents.standard.default-chat-network-of-agents=DEFAULT_AGENTS_NETWORK" })
public class AgentChatFirstAnswerKeptTest extends AbstractBaseTestLLmsIntegrationTests {

	private static final String ANSWER_MARKER = "GEBO_TEST_FIRST_ANSWER_KEPT";
	private static final String TITLE = "Capital of France asked";
	private static final String WRITING_INSTRUCTION = "Write a concise direct answer to the user question.";
	/** Names the language it asks for: the router keeps it on the chat, saving the session. */
	private static final String USER_QUESTION = "Please answer in English, what is the capital city of France and how many people live there?";
	/** How long the fake model takes to write the title. */
	private static final long TITLE_WRITING_MILLIS = 1500L;
	/** The marker of the title prompt (its system prompt's first line). */
	private static final String TITLE_PROMPT_MARKER = "summarization assistant";
	/** The marker of the router's request understanding prompt (its system prompt's first line). */
	private static final String REWRITE_PROMPT_MARKER = "Minimal Question Rewriter";

	@Autowired
	private DefaultPipelineStreamingDelegatedStepServiceImpl delegatedStep;
	@Autowired
	private IChatPipelineService chatPipelineService;
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
			if (prompt.contains(REWRITE_PROMPT_MARKER)) {
				return "deliverable=QA\nrewrittenQuery=" + USER_QUESTION + "\nuserRequiredLanguage=English";
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

		GeboChatRequest request = new GeboChatRequest();
		request.setQuery(USER_QUESTION);
		lifeCycleService.createChatSession(request);
		final String sessionCode = request.getUserChatContextCode();
		assertNotNull(sessionCode, "A valid chat session context code must be assigned by the lifecycle service");

		// the session as saved when the output step starts streaming (the router done),
		// and as saved when the last message reaches the client
		final AtomicReference<GUserChatSession> whenStreamingStarts = new AtomicReference<>();
		final AtomicReference<GUserChatSession> atLastMessage = new AtomicReference<>();
		// the title is asked as the user interface does: as soon as the last message
		// arrives, while the stream goes on
		final ReactiveIdentityUtil runAs = ReactiveIdentityUtil.create();
		final AtomicReference<CompletableFuture<GUserChatInfo>> title = new AtomicReference<>();
		Flux<GeboChatMessageEnvelope> flux = chatPipelineService
				.streamingChat(null, request, new LinkedHashMap<String, Object>()).doOnNext(envelope -> {
					if (envelope instanceof PipelineRoutingInfosMessageEnvelope && whenStreamingStarts.get() == null) {
						whenStreamingStarts.set(sessionsRepo.findById(sessionCode).orElse(null));
					}
					if (envelope != null && envelope.isLastMessage()
							&& envelope.getContent() instanceof GeboChatResponse && title.get() == null) {
						atLastMessage.set(sessionsRepo.findById(sessionCode).orElse(null));
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
		List<GeboChatMessageEnvelope> streamed = flux.collectList().block(Duration.ofSeconds(120));
		assertNotNull(streamed, "The pipeline must produce a non-null output stream");

		// the hazard: the router saved the session with the request-only interaction
		GUserChatSession early = whenStreamingStarts.get();
		assertNotNull(early, "The routing infos message is sent once the router decided");
		assertEquals(1, early.getInteractions().size(),
				"The language kept on the chat saved the session with its first interaction while the request runs");
		assertNull(early.getInteractions().get(0).getResponse(),
				"The first interaction is saved without its answer while the request runs");

		// the executor's contract: the interaction is saved, with its answer, before the
		// last message reaches the client
		assertNotNull(title.get(), "The stream must carry a last message, on which the title is asked");
		GUserChatSession atLast = atLastMessage.get();
		assertNotNull(atLast, "The session is saved when the last message passes");
		assertEquals(1, atLast.getInteractions().size(), "The chat has its first interaction at the last message");
		assertNotNull(atLast.getInteractions().get(0).getResponse(),
				"The interaction is saved with its answer before the last message reaches the client");

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
