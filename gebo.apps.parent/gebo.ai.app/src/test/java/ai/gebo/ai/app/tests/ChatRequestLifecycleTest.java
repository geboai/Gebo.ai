/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.ai.app.tests;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Duration;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.tests.TestChatModel;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMRequestGenerationPolicy;
import ai.gebo.llms.chat.abstraction.layer.repository.ChatFullSessionStateRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.GUserChatSessionRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.MinimalChatContextCacheItemRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.ShrinkedChatSessionStateRepository;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;

public class ChatRequestLifecycleTest extends AbstractBaseTestLLmsIntegrationTests {

	@Autowired
	private IGChatSessionLifeCycleService lifeCycleService;
	@Autowired
	private IGChatService chatService;
	@Autowired
	private GUserChatSessionRepository sessionRepository;
	@Autowired
	private ShrinkedChatSessionStateRepository shrinkedRepository;
	@Autowired
	private ChatFullSessionStateRepository fullRepository;
	@Autowired
	private MinimalChatContextCacheItemRepository minimalContextCache;

	@Override
	protected void beforeEachCallback() throws Exception {
		// New chats of the same user get the same code once the base class wipes the sessions.
		fullRepository.deleteAll();
		shrinkedRepository.deleteAll();
		minimalContextCache.deleteAll();
		TestChatModel.setGlobalResponseLogic(prompt -> "Paris is the capital of France.");
	}

	@Override
	protected void afterEachCallback() throws Exception {
		TestChatModel.clearGlobalResponseLogic();
	}

	@Test
	public void testASecondRequestWaitsForTheFirstToBeOver() throws Exception {
		IGConfigurableChatModel model = chatModelRuntimeDao.findByCode(DEFAULT_TEST_CHAT_MODEL_CODE);
		String chat = newSession();
		GeboChatRequest first = request(chat, "What is the capital of France?");
		GeboChatRequest second = request(chat, "And the capital of Italy?");
		lifeCycleService.startRequest(first, model, LLMRequestGenerationPolicy.ADDING_RESOURCES_FIT_TOKENS_BUDGET);

		assertThrows(GeboChatSessionLifecycleException.class, () -> lifeCycleService.startRequest(second, model,
				LLMRequestGenerationPolicy.ADDING_RESOURCES_FIT_TOKENS_BUDGET));
		assertDoesNotThrow(() -> lifeCycleService.startRequest(first, model,
				LLMRequestGenerationPolicy.ADDING_RESOURCES_FIT_TOKENS_BUDGET), "Restarting the same request is fine");

		lifeCycleService.releaseRequest(first);
		lifeCycleService.startRequest(second, model, LLMRequestGenerationPolicy.ADDING_RESOURCES_FIT_TOKENS_BUDGET);
		end(second);
		assertEquals(1, sessionRepository.findById(chat).orElseThrow().getInteractions().size(),
				"A released request is not saved unless it is ended");
	}

	@Test
	public void testAReleasedRequestCanStillBeEnded() throws Exception {
		IGConfigurableChatModel model = chatModelRuntimeDao.findByCode(DEFAULT_TEST_CHAT_MODEL_CODE);
		String chat = newSession();
		GeboChatRequest request = request(chat, "What is the capital of France?");
		lifeCycleService.startRequest(request, model, LLMRequestGenerationPolicy.ADDING_RESOURCES_FIT_TOKENS_BUDGET);
		lifeCycleService.releaseRequest(request);

		end(request);
		assertEquals(1, sessionRepository.findById(chat).orElseThrow().getInteractions().size());
		assertEquals(1, fullRepository.findById(chat).orElseThrow().getChatHistory().getValue().getInteractions()
				.size());
	}

	@Test
	public void testAStreamedChatLeavesTheChatFreeForTheNextRequest() throws Exception {
		String chat = lifeCycleService.createCleanChatByModelCode(DEFAULT_TEST_CHAT_MODEL_CODE, null).getCode();
		for (String question : new String[] { "What is the capital of France?", "And the capital of Italy?" }) {
			GeboChatRequest request = request(chat, question);
			chatService.streamChat(request).collectList().block(Duration.ofSeconds(60));
		}
		assertEquals(2, sessionRepository.findById(chat).orElseThrow().getInteractions().size());
	}

	private String newSession() throws Exception {
		GeboChatRequest first = new GeboChatRequest();
		first.setQuery("new chat");
		lifeCycleService.createChatSession(first);
		return first.getUserChatContextCode();
	}

	private static GeboChatRequest request(String chat, String query) {
		GeboChatRequest request = new GeboChatRequest();
		request.setId(UUID.randomUUID().toString());
		request.setUserChatContextCode(chat);
		request.setQuery(query);
		return request;
	}

	private void end(GeboChatRequest request) throws Exception {
		GeboChatResponse response = lifeCycleService.createEmptyResponse(request);
		response.setQueryResponse("An answer to: " + request.getQuery());
		lifeCycleService.endRequest(request, response);
	}
}
