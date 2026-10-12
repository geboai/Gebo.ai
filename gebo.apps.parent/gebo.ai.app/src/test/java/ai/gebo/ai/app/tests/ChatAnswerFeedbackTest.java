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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import ai.gebo.llms.abstraction.layer.model.IChatSessionEntry;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMRequestGenerationPolicy;
import ai.gebo.llms.chat.abstraction.layer.model.ChatAnswerFeedbackRating;
import ai.gebo.llms.chat.abstraction.layer.model.GChatAnswerFeedback;
import ai.gebo.llms.chat.abstraction.layer.repository.ChatAnswerFeedbackRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.ChatFullSessionStateRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.MinimalChatContextCacheItemRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.ShrinkedChatSessionStateRepository;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.client.rest.controllers.GeboUserChatsController;
import ai.gebo.llms.chat.client.rest.controllers.GeboUserChatsController.AnswerFeedbackRequest;
import ai.gebo.security.config.GeboAISecurityConfig;

public class ChatAnswerFeedbackTest extends AbstractBaseTestLLmsIntegrationTests {

	private static final String OTHER_USER = "feedback-other-user@gebo.ai";

	@Autowired
	private GeboUserChatsController controller;
	@Autowired
	private IGChatSessionLifeCycleService lifeCycleService;
	@Autowired
	private ChatAnswerFeedbackRepository feedbackRepository;
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
		feedbackRepository.deleteAll();
		if (getUser(OTHER_USER) == null) {
			createUser(OTHER_USER, List.of(GeboAISecurityConfig.USER_ROLE));
		}
	}

	@Test
	public void testFeedbackIsOnePerAnswerAndCanBeChangedAndRemoved() throws Exception {
		String chat = newSession();
		GeboChatRequest answered = exchange(chat, "What is the capital of France?");

		GChatAnswerFeedback first = controller.setAnswerFeedback(new AnswerFeedbackRequest(chat, answered.getId(),
				ChatAnswerFeedbackRating.NEGATIVE, "  It should cite the source  "));
		assertEquals(ChatAnswerFeedbackRating.NEGATIVE, first.getRating());
		assertEquals("It should cite the source", first.getComment());
		assertEquals(DEFAULT_ALL_ROLES_USER, first.getUsername());

		controller.setAnswerFeedback(
				new AnswerFeedbackRequest(chat, answered.getId(), ChatAnswerFeedbackRating.POSITIVE, null));
		List<GChatAnswerFeedback> feedbacks = controller.getAnswerFeedbacks(chat);
		assertEquals(1, feedbacks.size(), "A second vote on the same answer replaces the first");
		assertEquals(ChatAnswerFeedbackRating.POSITIVE, feedbacks.get(0).getRating());
		assertEquals(null, feedbacks.get(0).getComment());

		controller.removeAnswerFeedback(chat, answered.getId());
		assertTrue(controller.getAnswerFeedbacks(chat).isEmpty());
	}

	@Test
	public void testFeedbackNeedsAnAnsweredRequestOfTheChat() throws Exception {
		String chat = newSession();
		exchange(chat, "What is the capital of France?");

		assertThrows(GeboChatSessionLifecycleException.class, () -> controller.setAnswerFeedback(
				new AnswerFeedbackRequest(chat, "not-a-request-of-this-chat", ChatAnswerFeedbackRating.NEGATIVE, null)));
		assertTrue(feedbackRepository.findAll().isEmpty());
	}

	@Test
	public void testOnlyTheChatOwnerCanGiveOrReadFeedback() throws Exception {
		String chat = newSession();
		GeboChatRequest answered = exchange(chat, "What is the capital of France?");
		controller.setAnswerFeedback(
				new AnswerFeedbackRequest(chat, answered.getId(), ChatAnswerFeedbackRating.NEGATIVE, "wrong"));

		impersonate(OTHER_USER, GeboAISecurityConfig.USER_ROLE);
		assertThrows(SecurityException.class, () -> controller.setAnswerFeedback(
				new AnswerFeedbackRequest(chat, answered.getId(), ChatAnswerFeedbackRating.POSITIVE, null)));
		assertThrows(SecurityException.class, () -> controller.getAnswerFeedbacks(chat));
		assertThrows(SecurityException.class, () -> controller.removeAnswerFeedback(chat, answered.getId()));
		assertEquals(ChatAnswerFeedbackRating.NEGATIVE, feedbackRepository.findAll().get(0).getRating());
	}

	@Test
	public void testFeedbackSurvivesTheChatDeletion() throws Exception {
		String chat = newSession();
		GeboChatRequest answered = exchange(chat, "What is the capital of France?");
		controller.setAnswerFeedback(
				new AnswerFeedbackRequest(chat, answered.getId(), ChatAnswerFeedbackRating.NEGATIVE, "wrong"));

		lifeCycleService.removeChatSession(chat);
		assertEquals(1, feedbackRepository.findByUserChatContextCode(chat).size());
	}

	@Test
	public void testNextRequestShowsTheFeedbackNextToTheRatedAnswers() throws Exception {
		String chat = newSession();
		GeboChatRequest wrong = exchange(chat, "What is the capital of France?");
		GeboChatRequest fine = exchange(chat, "And the capital of Italy?");
		controller.setAnswerFeedback(new AnswerFeedbackRequest(chat, wrong.getId(),
				ChatAnswerFeedbackRating.NEGATIVE, "Always cite the source"));
		controller.setAnswerFeedback(
				new AnswerFeedbackRequest(chat, fine.getId(), ChatAnswerFeedbackRating.POSITIVE, null));

		IGConfigurableChatModel model = chatModelRuntimeDao.findByCode(DEFAULT_TEST_CHAT_MODEL_CODE);
		GeboChatRequest next = new GeboChatRequest();
		next.setId(UUID.randomUUID().toString());
		next.setUserChatContextCode(chat);
		next.setQuery("And the capital of Spain?");
		LLMChatRequestResources resources = lifeCycleService.startRequest(next, model,
				LLMRequestGenerationPolicy.ADDING_RESOURCES_FIT_TOKENS_BUDGET);
		List<IChatSessionEntry> history = resources.createChatRequestContext().getInteractions();
		assertEquals(2, history.size());
		assertTrue(history.get(0).getAssistant().endsWith(
				"[The user rated this answer as not satisfying, commenting: \"Always cite the source\"]"),
				history.get(0).getAssistant());
		assertEquals("An answer to: And the capital of Italy?", history.get(1).getAssistant(),
				"A positive rating without a comment adds nothing to the history");
		GeboChatResponse response = lifeCycleService.createEmptyResponse(next);
		response.setQueryResponse("Madrid.");
		lifeCycleService.endRequest(next, response);
	}

	private String newSession() throws Exception {
		GeboChatRequest first = new GeboChatRequest();
		first.setQuery("new chat");
		lifeCycleService.createChatSession(first);
		return first.getUserChatContextCode();
	}

	private GeboChatRequest exchange(String chat, String question) throws Exception {
		IGConfigurableChatModel model = chatModelRuntimeDao.findByCode(DEFAULT_TEST_CHAT_MODEL_CODE);
		GeboChatRequest request = new GeboChatRequest();
		request.setId(UUID.randomUUID().toString());
		request.setUserChatContextCode(chat);
		request.setQuery(question);
		lifeCycleService.startRequest(request, model, LLMRequestGenerationPolicy.ADDING_RESOURCES_FIT_TOKENS_BUDGET);
		GeboChatResponse response = lifeCycleService.createEmptyResponse(request);
		response.setQueryResponse("An answer to: " + question);
		lifeCycleService.endRequest(request, response);
		return request;
	}

	private static void impersonate(String username, String... roles) {
		List<SimpleGrantedAuthority> authorities = List.of(roles).stream().map(SimpleGrantedAuthority::new).toList();
		SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken(username, "NOPASSWORD", authorities));
	}
}
