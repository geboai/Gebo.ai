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
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.tests.TestChatModel;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMRequestGenerationPolicy;
import ai.gebo.llms.chat.abstraction.layer.repository.ChatFullSessionStateRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.MinimalChatContextCacheItemRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.ShrinkedChatSessionStateRepository;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionStateShrinkerService;
import ai.gebo.llms.chat.abstraction.layer.session.model.ShrinkedChatSessionState;

public class ChatHistoryShrinkTest extends AbstractBaseTestLLmsIntegrationTests {

	@Autowired
	private IGChatSessionLifeCycleService lifeCycleService;
	@Autowired
	private IGChatSessionStateShrinkerService shrinker;
	@Autowired
	private ShrinkedChatSessionStateRepository shrinkedRepository;
	@Autowired
	private ChatFullSessionStateRepository fullRepository;
	@Autowired
	private MinimalChatContextCacheItemRepository minimalContextCache;

	private final List<String> prompts = new ArrayList<>();

	@Override
	protected void beforeEachCallback() throws Exception {
		// New chats of the same user get the same code once the base class wipes the sessions.
		fullRepository.deleteAll();
		shrinkedRepository.deleteAll();
		minimalContextCache.deleteAll();
		prompts.clear();
		TestChatModel.setGlobalResponseLogic(prompt -> {
			prompts.add(prompt);
			return "SUMMARY-" + prompts.size();
		});
	}

	@Override
	protected void afterEachCallback() throws Exception {
		TestChatModel.clearGlobalResponseLogic();
	}

	@Test
	public void testEachShrinkSummarizesOnlyTheExchangesNotYetSummarized() throws Exception {
		String chat = newSession();
		chat(chat, 0, 12);

		int before = prompts.size();
		shrinker.shrink(chat, 10);
		String first = prompts.get(before);
		for (int i = 0; i < 4; i++) {
			assertTrue(first.contains(question(i)), question(i));
		}
		assertFalse(first.contains(question(4)), "The last 8 exchanges stay verbatim");
		ShrinkedChatSessionState shrinked = shrinkedRepository.findById(chat).orElseThrow();
		assertEquals(4, shrinked.getChatHistory().getLastInteractionPointer());
		String firstSummary = shrinked.getChatHistory().getConsolidationText();

		chat(chat, 12, 15);
		before = prompts.size();
		shrinker.shrink(chat, 10);
		String second = prompts.get(before);
		for (int i = 4; i < 7; i++) {
			assertTrue(second.contains(question(i)), question(i));
		}
		for (int i = 0; i < 4; i++) {
			assertFalse(second.contains(question(i)), "Already summarized: " + question(i));
		}
		assertTrue(second.contains(firstSummary), "The previous summary is carried forward");
		assertEquals(7, shrinkedRepository.findById(chat).orElseThrow().getChatHistory().getLastInteractionPointer());
	}

	@Test
	public void testAShrinkDoesNotOverwriteAnExchangeSavedMeanwhile() throws Exception {
		String chat = newSession();
		chat(chat, 0, 12);
		IGConfigurableChatModel model = chatModelRuntimeDao.findByCode(DEFAULT_TEST_CHAT_MODEL_CODE);
		GeboChatRequest pending = new GeboChatRequest();
		pending.setId(UUID.randomUUID().toString());
		pending.setUserChatContextCode(chat);
		pending.setQuery("QUESTION-12");
		lifeCycleService.startRequest(pending, model, LLMRequestGenerationPolicy.ADDING_RESOURCES_FIT_TOKENS_BUDGET);
		long revisionBefore = shrinkedRepository.findById(chat).orElseThrow().getRevision();
		AtomicBoolean ended = new AtomicBoolean();
		TestChatModel.setGlobalResponseLogic(prompt -> {
			if (ended.compareAndSet(false, true)) {
				try {
					GeboChatResponse response = lifeCycleService.createEmptyResponse(pending);
					response.setQueryResponse("Answer number 12");
					lifeCycleService.endRequest(pending, response);
				} catch (Exception e) {
					throw new IllegalStateException(e);
				}
			}
			return "SUMMARY";
		});

		shrinker.shrink(chat, 10);

		ShrinkedChatSessionState after = shrinkedRepository.findById(chat).orElseThrow();
		assertTrue(ended.get());
		assertEquals(revisionBefore + 1, after.getRevision(), "Only the request's save went through");
		assertEquals(null, after.getChatHistory().getConsolidationText(), "The concurrent shrink is discarded");
		List<String> latest = after.getChatHistory().getLatestEntries().getInteractions().stream()
				.map(x -> x.getUser()).toList();
		assertEquals(13, latest.size());
		assertEquals("QUESTION-12", latest.get(12));
	}

	private void chat(String chat, int from, int to) throws Exception {
		IGConfigurableChatModel model = chatModelRuntimeDao.findByCode(DEFAULT_TEST_CHAT_MODEL_CODE);
		for (int i = from; i < to; i++) {
			GeboChatRequest request = new GeboChatRequest();
			request.setId(UUID.randomUUID().toString());
			request.setUserChatContextCode(chat);
			request.setQuery(question(i));
			lifeCycleService.startRequest(request, model, LLMRequestGenerationPolicy.ADDING_RESOURCES_FIT_TOKENS_BUDGET);
			GeboChatResponse response = lifeCycleService.createEmptyResponse(request);
			response.setQueryResponse("Answer number " + i);
			lifeCycleService.endRequest(request, response);
		}
	}

	private static String question(int i) {
		return String.format("QUESTION-%02d", i);
	}

	private String newSession() throws Exception {
		GeboChatRequest first = new GeboChatRequest();
		first.setQuery("new chat");
		lifeCycleService.createChatSession(first);
		return first.getUserChatContextCode();
	}
}
