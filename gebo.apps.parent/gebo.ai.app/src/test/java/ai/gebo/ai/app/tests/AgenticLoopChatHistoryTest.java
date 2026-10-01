/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.ai.app.tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.tests.TestChatModel;
import ai.gebo.llms.agent.chat.service.impl.ReactiveChatAgentsNetworkStreamingOutputChatPipelineService;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.ChatNotificationContent.NotificationType;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.session.model.CSSConsolidatedChatHistory;
import ai.gebo.llms.chat.abstraction.layer.session.model.CSSSimplefiedInteraction;
import ai.gebo.llms.chat.abstraction.layer.session.model.MinimalChatContext;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.service.ISinkUIEmitter;
import ai.gebo.llms.chat.pipelines.service.IStreamingOutputChatPipelineService;
import ai.gebo.model.DocumentMetaInfos;

/**
 * Verifies that the single agent of the agentic loop receives the chat history in
 * its model calls: the previous turns and, for a long chat, the consolidated summary
 * of the older ones. The test model records the whole prompt of the loop agent's
 * call (every message: system, history, user).
 */
@TestPropertySource(properties = { "ai.gebo.agents.standard.enabled=true",
		"ai.gebo.agents.standard.default-chat-network-of-agents=AGENTIC_LOOP_AGENTS_NETWORK" })
public class AgenticLoopChatHistoryTest extends AbstractBaseTestLLmsIntegrationTests {
	private static final String USER_QUESTION = "And which of those is the oldest one?";
	private static final String PREVIOUS_QUESTION = "PREVIOUS_QUESTION_MARKER which cities did the Romans found?";
	private static final String PREVIOUS_ANSWER = "PREVIOUS_ANSWER_MARKER London, Cologne and Vienna.";
	private static final String OLDER_SUMMARY = "CONSOLIDATED_SUMMARY_MARKER the user asked about Roman history.";
	private static final String CHOSEN_DOCUMENT_TEXT = "CHOSEN_DOCUMENT_MARKER Londinium was founded around 47 AD.";
	/** Text of the loop agent's system prompt, to tell its calls apart. */
	private static final String LOOP_PROMPT_TEXT = "agentic chat assistant";

	@Autowired
	private List<IStreamingOutputChatPipelineService> pipelineServices;
	@Autowired
	private IGChatSessionLifeCycleService lifeCycleService;

	private final List<String> loopPrompts = Collections.synchronizedList(new ArrayList<>());
	private String sessionCode = null;

	@Override
	protected void beforeEachCallback() throws Exception {
		loopPrompts.clear();
		TestChatModel.setGlobalResponseLogic(prompt -> {
			if (prompt == null) {
				return "";
			}
			if (prompt.contains(USER_QUESTION)) {
				if (prompt.contains(LOOP_PROMPT_TEXT)) {
					loopPrompts.add(prompt);
				}
				return "London is the oldest one.";
			}
			return "";
		});
	}

	@Override
	protected void afterEachCallback() throws Exception {
		TestChatModel.clearGlobalResponseLogic();
	}

	private static CSSSimplefiedInteraction turn(String user, String assistant) {
		CSSSimplefiedInteraction interaction = new CSSSimplefiedInteraction();
		interaction.setUser(user);
		interaction.setAssistant(assistant);
		return interaction;
	}

	/** Runs the agentic loop on a chat with the given history, returning the loop agent's prompts. */
	private List<String> runLoop(CSSConsolidatedChatHistory history) throws Exception {
		return runLoop(history, null);
	}

	/** Runs the agentic loop on a chat with the given history and chosen documents. */
	private List<String> runLoop(CSSConsolidatedChatHistory history, AIDocumentsSet chosenDocuments)
			throws Exception {
		IStreamingOutputChatPipelineService agentsPipeline = pipelineServices.stream()
				.filter(s -> s instanceof ReactiveChatAgentsNetworkStreamingOutputChatPipelineService).findFirst()
				.orElseThrow();
		GeboChatRequest request = new GeboChatRequest();
		request.setQuery(USER_QUESTION);
		// one new chat per user: the tests share it, each bringing its own history
		if (sessionCode == null) {
			lifeCycleService.createChatSession(request);
			sessionCode = request.getUserChatContextCode();
		} else {
			request.setUserChatContextCode(sessionCode);
		}
		MinimalChatContext minimalChatContext = new MinimalChatContext();
		minimalChatContext.setCurrentRequest(request);
		minimalChatContext.setChatHistory(history);
		LLMChatRequestResources resources = new LLMChatRequestResources();
		resources.setCurrentRequest(request);
		resources.setChathistory(history);
		resources.setChatWithDocuments(chosenDocuments);
		ChatPipelineExecutionRuntimeData runtimeData = new ChatPipelineExecutionRuntimeData(null, 8192, resources,
				new GeboChatResponse(), minimalChatContext, true);
		IGConfigurableChatModel model = chatModelRuntimeDao.findByCode(DEFAULT_TEST_CHAT_MODEL_CODE);
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
				LOGGER.error("Error emitted by agentic loop network", error);
			}

			@Override
			public void complete() {
			}
		};
		List<GeboChatMessageEnvelope> streamed = agentsPipeline.execute(runtimeData, emitter, model, model)
				.collectList().block(Duration.ofSeconds(90));
		assertNotNull(streamed);
		assertFalse(loopPrompts.isEmpty(), "The loop agent must have called its model");
		return new ArrayList<>(loopPrompts);
	}

	/**
	 * The shapes a chat history reaches the loop with, in one test sharing one chat
	 * (one new chat per user): the recent turns only, the summary of the older turns
	 * with the recent ones, the summary alone (when no recent turn is kept), and the
	 * documents chosen for the chat.
	 */
	@Test
	public void theLoopAgentReceivesTheChatHistory() throws Exception {
		CSSConsolidatedChatHistory recentOnly = new CSSConsolidatedChatHistory();
		recentOnly.getLatestEntries().getInteractions().add(turn(PREVIOUS_QUESTION, PREVIOUS_ANSWER));
		String prompt = runLoop(recentOnly).get(0);
		assertTrue(prompt.contains(PREVIOUS_QUESTION), "recent turns: previous question missing: " + prompt);
		assertTrue(prompt.contains(PREVIOUS_ANSWER), "recent turns: previous answer missing: " + prompt);

		loopPrompts.clear();
		CSSConsolidatedChatHistory summaryAndRecent = new CSSConsolidatedChatHistory();
		summaryAndRecent.setConsolidationText(OLDER_SUMMARY);
		summaryAndRecent.getLatestEntries().getInteractions().add(turn(PREVIOUS_QUESTION, PREVIOUS_ANSWER));
		prompt = runLoop(summaryAndRecent).get(0);
		assertTrue(prompt.contains(OLDER_SUMMARY), "summary and recent turns: summary missing: " + prompt);
		assertTrue(prompt.contains(PREVIOUS_QUESTION), "summary and recent turns: previous question missing: " + prompt);
		assertTrue(prompt.contains(PREVIOUS_ANSWER), "summary and recent turns: previous answer missing: " + prompt);

		loopPrompts.clear();
		CSSConsolidatedChatHistory summaryOnly = new CSSConsolidatedChatHistory();
		summaryOnly.setConsolidationText(OLDER_SUMMARY);
		prompt = runLoop(summaryOnly).get(0);
		assertTrue(prompt.contains(OLDER_SUMMARY), "summary only: summary missing: " + prompt);

		loopPrompts.clear();
		Map<String, Object> metaData = new HashMap<>();
		metaData.put(DocumentMetaInfos.CONTENT_CODE, "chosen-document");
		metaData.put(DocumentMetaInfos.GEBO_FILE_NAME, "chosen.txt");
		Document chosen = Document.builder().id("chosen-fragment").text(CHOSEN_DOCUMENT_TEXT).metadata(metaData)
				.build();
		prompt = runLoop(new CSSConsolidatedChatHistory(), AIDocumentsSet.from(List.of(chosen))).get(0);
		assertTrue(prompt.contains(CHOSEN_DOCUMENT_TEXT), "chosen documents missing: " + prompt);

	}
}
