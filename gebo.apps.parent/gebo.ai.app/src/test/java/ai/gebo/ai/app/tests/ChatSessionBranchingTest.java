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
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ai.gebo.architecture.rag.support.layer.model.AIDocumentFragment;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentReferenceItem;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMRequestGenerationPolicy;
import ai.gebo.llms.chat.abstraction.layer.model.ChatRuleScope;
import ai.gebo.llms.chat.abstraction.layer.model.GChatRule;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatInfo;
import ai.gebo.llms.chat.abstraction.layer.repository.ChatRuleRepository;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatRulesService;
import ai.gebo.llms.chat.abstraction.layer.repository.ChatFullSessionStateRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.GUserChatSessionRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.MinimalChatContextCacheItemRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.ShrinkedChatSessionStateRepository;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.session.model.CSSInteractionReferredContent;
import ai.gebo.llms.chat.abstraction.layer.session.model.CSSSimplefiedInteraction;
import ai.gebo.llms.chat.abstraction.layer.session.model.ChatFullSessionState;
import ai.gebo.llms.chat.abstraction.layer.session.model.ChatInteractions;
import ai.gebo.llms.chat.abstraction.layer.session.model.GDocumentReferenceSTO;
import ai.gebo.llms.chat.abstraction.layer.session.model.GUserChatSession;
import ai.gebo.llms.chat.abstraction.layer.session.model.ShrinkedChatSessionState;

public class ChatSessionBranchingTest extends AbstractBaseTestLLmsIntegrationTests {

	@Autowired
	private IGChatSessionLifeCycleService lifeCycleService;
	@Autowired
	private GUserChatSessionRepository sessionRepository;
	@Autowired
	private ShrinkedChatSessionStateRepository shrinkedRepository;
	@Autowired
	private ChatFullSessionStateRepository fullRepository;
	@Autowired
	private MinimalChatContextCacheItemRepository minimalContextCache;
	@Autowired
	private IGChatRulesService rulesService;
	@Autowired
	private ChatRuleRepository ruleRepository;

	@Override
	protected void beforeEachCallback() throws Exception {
		// New chats of the same user get the same code once the base class wipes the sessions.
		fullRepository.deleteAll();
		shrinkedRepository.deleteAll();
		minimalContextCache.deleteAll();
		ruleRepository.deleteAll();
	}

	@Test
	public void testBranchKeepsHistoryAndDocumentsUpToTheRequest() throws Exception {
		IGConfigurableChatModel model = chatModelRuntimeDao.findByCode(DEFAULT_TEST_CHAT_MODEL_CODE);
		String source = newSession();
		List<GeboChatRequest> asked = new ArrayList<>();
		for (String question : List.of("What is the capital of France?", "And the capital of Italy?",
				"And the capital of Spain?")) {
			GeboChatRequest exchange = request(source, question);
			lifeCycleService.startRequest(exchange, model, LLMRequestGenerationPolicy.ADDING_RESOURCES_FIT_TOKENS_BUDGET);
			end(exchange);
			asked.add(exchange);
		}
		ChatFullSessionState sourceFull = fullRepository.findById(source).orElseThrow();
		for (int i = 0; i < 3; i++) {
			sourceFull.getRetrievedDocuments().getValue().getData()
					.add(new CSSInteractionReferredContent<GDocumentReferenceSTO>(i, document("doc-" + i, 50), null));
		}
		fullRepository.save(sourceFull);

		GUserChatInfo info = lifeCycleService.branchChatSession(source, asked.get(1).getId());
		String branch = info.getCode();
		assertNotEquals(source, branch);

		GUserChatSession branchSession = sessionRepository.findById(branch).orElseThrow();
		assertEquals(2, branchSession.getInteractions().size());
		assertEquals(asked.get(1).getId(), branchSession.getInteractions().get(1).getRequest().getId());
		for (ChatInteractions interaction : branchSession.getInteractions()) {
			assertEquals(branch, interaction.getRequest().getUserChatContextCode());
			assertEquals(branch, interaction.getResponse().getUserChatContextCode());
		}

		ChatFullSessionState branchFull = fullRepository.findById(branch).orElseThrow();
		assertEquals(List.of(asked.get(0).getId(), asked.get(1).getId()), requestIds(
				branchFull.getChatHistory().getValue().getInteractions()));
		assertEquals(List.of("doc-0", "doc-1"), branchFull.getRetrievedDocuments().getValue().getData().stream()
				.map(x -> x.getAiDocument().getCode()).toList());

		ShrinkedChatSessionState branchShrinked = shrinkedRepository.findById(branch).orElseThrow();
		assertEquals(List.of(asked.get(0).getId(), asked.get(1).getId()),
				requestIds(branchShrinked.getChatHistory().getLatestEntries().getInteractions()));
		assertEquals(2, branchShrinked.getLatestRequestsRetrievedDocuments().getData().size());

		assertEquals(3, sessionRepository.findById(source).orElseThrow().getInteractions().size(),
				"Branching must leave the source chat untouched");
		assertEquals(3, fullRepository.findById(source).orElseThrow().getRetrievedDocuments().getValue().getData()
				.size());
	}

	@Test
	public void testBranchGetsItsOwnCopyOfTheChatRules() throws Exception {
		IGConfigurableChatModel model = chatModelRuntimeDao.findByCode(DEFAULT_TEST_CHAT_MODEL_CODE);
		String source = newSession();
		GeboChatRequest exchange = request(source, "What is the capital of France?");
		lifeCycleService.startRequest(exchange, model, LLMRequestGenerationPolicy.ADDING_RESOURCES_FIT_TOKENS_BUDGET);
		end(exchange);
		GChatRule rule = new GChatRule();
		rule.setScope(ChatRuleScope.SESSION);
		rule.setUserChatContextCode(source);
		rule.setText("Answer in Italian");
		GChatRule sourceRule = rulesService.createRule(rule);

		String branch = lifeCycleService.branchChatSession(source, exchange.getId()).getCode();
		List<GChatRule> branchRules = rulesService.getChatRules(branch);
		assertEquals(1, branchRules.size());
		assertEquals("Answer in Italian", branchRules.get(0).getText());
		assertNotEquals(sourceRule.getId(), branchRules.get(0).getId());

		GChatRule edited = branchRules.get(0);
		edited.setText("Answer in French");
		rulesService.updateRule(edited);
		assertEquals("Answer in Italian", rulesService.getChatRules(source).get(0).getText(),
				"Editing the branch's rule leaves the source chat's rule as it was");
	}

	@Test
	public void testBranchContinuesIndependentlyOfTheSource() throws Exception {
		IGConfigurableChatModel model = chatModelRuntimeDao.findByCode(DEFAULT_TEST_CHAT_MODEL_CODE);
		String source = newSession();
		GeboChatRequest first = request(source, "What is the capital of France?");
		lifeCycleService.startRequest(first, model, LLMRequestGenerationPolicy.ADDING_RESOURCES_FIT_TOKENS_BUDGET);
		end(first);
		GeboChatRequest second = request(source, "And the capital of Italy?");
		lifeCycleService.startRequest(second, model, LLMRequestGenerationPolicy.ADDING_RESOURCES_FIT_TOKENS_BUDGET);
		end(second);

		String branch = lifeCycleService.branchChatSession(source, first.getId()).getCode();
		GeboChatRequest onBranch = request(branch, "And the capital of Germany?");
		lifeCycleService.startRequest(onBranch, model, LLMRequestGenerationPolicy.ADDING_RESOURCES_FIT_TOKENS_BUDGET);
		end(onBranch);

		assertEquals(List.of(first.getId(), onBranch.getId()), requestIds(
				fullRepository.findById(branch).orElseThrow().getChatHistory().getValue().getInteractions()));
		assertEquals(List.of(first.getId(), second.getId()), requestIds(
				fullRepository.findById(source).orElseThrow().getChatHistory().getValue().getInteractions()));
	}

	@Test
	public void testBranchFromAnUnknownRequestIsRefused() throws Exception {
		IGConfigurableChatModel model = chatModelRuntimeDao.findByCode(DEFAULT_TEST_CHAT_MODEL_CODE);
		String source = newSession();
		GeboChatRequest exchange = request(source, "What is the capital of France?");
		lifeCycleService.startRequest(exchange, model, LLMRequestGenerationPolicy.ADDING_RESOURCES_FIT_TOKENS_BUDGET);
		end(exchange);
		long sessions = sessionRepository.count();

		assertThrows(GeboChatSessionLifecycleException.class,
				() -> lifeCycleService.branchChatSession(source, "not-a-request-of-this-chat"));
		assertEquals(sessions, sessionRepository.count(), "A refused branch must not create a chat");
	}

	private String newSession() throws Exception {
		GeboChatRequest first = new GeboChatRequest();
		first.setQuery("new chat");
		lifeCycleService.createChatSession(first);
		return first.getUserChatContextCode();
	}

	private static GeboChatRequest request(String session, String query) {
		GeboChatRequest request = new GeboChatRequest();
		request.setId(UUID.randomUUID().toString());
		request.setUserChatContextCode(session);
		request.setQuery(query);
		return request;
	}

	private void end(GeboChatRequest request) throws Exception {
		GeboChatResponse response = lifeCycleService.createEmptyResponse(request);
		response.setQueryResponse("An answer to: " + request.getQuery());
		lifeCycleService.endRequest(request, response);
	}

	private static List<String> requestIds(List<CSSSimplefiedInteraction> history) {
		assertTrue(history != null);
		return history.stream().map(CSSSimplefiedInteraction::getRequestId).toList();
	}

	private static AIDocumentReferenceItem document(String code, int tokens) {
		AIDocumentFragment fragment = new AIDocumentFragment();
		fragment.setCode(code + "-fragment");
		fragment.setDocumentContent("content of " + code);
		fragment.setTokensSize(tokens);
		AIDocumentReferenceItem document = new AIDocumentReferenceItem();
		document.setCode(code);
		document.setName(code + ".txt");
		document.getFragments().add(fragment);
		document.recalculateSize();
		return document;
	}
}
