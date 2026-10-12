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

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentFragment;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentReferenceItem;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.tests.TestChatModel;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMRequestGenerationPolicy;
import ai.gebo.llms.chat.abstraction.layer.model.MinimalChatContextCacheItem;
import ai.gebo.llms.chat.abstraction.layer.repository.ChatFullSessionStateRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.MinimalChatContextCacheItemRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.ShrinkedChatSessionStateRepository;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.session.model.CSSInteractionReferredContent;
import ai.gebo.llms.chat.abstraction.layer.session.model.CSSRelevantShrinkedDocument;
import ai.gebo.llms.chat.abstraction.layer.session.model.ChatFullSessionState;
import ai.gebo.llms.chat.abstraction.layer.session.model.GDocumentReferenceSTO;
import ai.gebo.llms.chat.abstraction.layer.session.model.MinimalChatContext;
import ai.gebo.llms.chat.abstraction.layer.session.model.ShrinkedChatSessionState;

@TestPropertySource(properties = "ai.gebo.chatsession.maximum-context-window-token-used="
		+ ChatSessionBudgetAndShrinkTest.TOKENS_CAP)
public class ChatSessionBudgetAndShrinkTest extends AbstractBaseTestLLmsIntegrationTests {

	static final int TOKENS_CAP = 3000;

	@Autowired
	private IGChatSessionLifeCycleService lifeCycleService;
	@Autowired
	private ShrinkedChatSessionStateRepository shrinkedRepository;
	@Autowired
	private ChatFullSessionStateRepository fullRepository;
	@Autowired
	private MinimalChatContextCacheItemRepository minimalContextCache;

	private final AtomicInteger llmCalls = new AtomicInteger();

	@Override
	protected void beforeEachCallback() throws Exception {
		// New chats of the same user get the same code once the base class wipes the sessions.
		fullRepository.deleteAll();
		shrinkedRepository.deleteAll();
		minimalContextCache.deleteAll();
		llmCalls.set(0);
		TestChatModel.setGlobalResponseLogic(prompt -> {
			llmCalls.incrementAndGet();
			return "The user asked about the capital cities of Europe.";
		});
	}

	@Override
	protected void afterEachCallback() throws Exception {
		TestChatModel.clearGlobalResponseLogic();
	}

	@Test
	public void testOverBudgetRequestIsTrimmedWithoutLosingSessionDocuments() throws Exception {
		IGConfigurableChatModel model = chatModelRuntimeDao.findByCode(DEFAULT_TEST_CHAT_MODEL_CODE);
		int budget = budgetOf(model);
		int perDocument = budget / 3;
		String session = newSession();

		ShrinkedChatSessionState shrinked = shrinkedRepository.findById(session).orElseThrow();
		ChatFullSessionState full = fullRepository.findById(session).orElseThrow();
		for (int i = 0; i < 6; i++) {
			CSSInteractionReferredContent<GDocumentReferenceSTO> latest = new CSSInteractionReferredContent<>(0,
					document("latest-" + i, perDocument), null);
			shrinked.getLatestRequestsRetrievedDocuments().getData().add(latest);
			full.getRetrievedDocuments().getValue().getData().add(latest);
		}
		for (int i = 0; i < 3; i++) {
			CSSRelevantShrinkedDocument relevant = new CSSRelevantShrinkedDocument();
			relevant.setId("relevant-" + i);
			relevant.setDocumentReference("relevant-" + i);
			relevant.setSummarizedContent("summary " + i);
			relevant.setTokensSize(perDocument);
			relevant.getMetaData().put(DocumentMetaInfos.CONTENT_CODE, "relevant-" + i);
			shrinked.getRelevantRetrievedDocuments().add(relevant);
		}
		shrinkedRepository.save(shrinked);
		fullRepository.save(full);

		GeboChatRequest request = request(session, "And what about the next question?");
		LLMChatRequestResources resources = lifeCycleService.startRequest(request, model,
				LLMRequestGenerationPolicy.ADDING_RESOURCES_FIT_TOKENS_BUDGET);
		int sentTokens = documentTokens(resources);
		assertTrue(sentTokens < budget,
				"The documents sent to the model (" + sentTokens + " tokens) must fit the budget of " + budget);
		assertTrue(sentTokens > 0, "Trimming must stop once the request fits, not drop every document");
		end(request);

		ShrinkedChatSessionState saved = shrinkedRepository.findById(session).orElseThrow();
		assertEquals(6, saved.getLatestRequestsRetrievedDocuments().getData().size(),
				"Trimming a request must not remove documents from the saved session state");
		assertEquals(3, saved.getRelevantRetrievedDocuments().size(),
				"Trimming a request must not remove summarized documents from the saved session state");
	}

	@Test
	public void testMinimalContextIsCachedForItsBudget() throws Exception {
		IGConfigurableChatModel model = chatModelRuntimeDao.findByCode(DEFAULT_TEST_CHAT_MODEL_CODE);
		String session = newSession();
		for (String question : List.of("What is the capital of France?", "And the capital of Italy?",
				"And the capital of Spain?")) {
			GeboChatRequest exchange = request(session, question);
			lifeCycleService.startRequest(exchange, model, LLMRequestGenerationPolicy.ADDING_RESOURCES_FIT_TOKENS_BUDGET);
			end(exchange);
		}
		GeboChatRequest request = request(session, "Which of them is the largest?");
		lifeCycleService.startRequest(request, model, LLMRequestGenerationPolicy.ADDING_RESOURCES_FIT_TOKENS_BUDGET);
		int tinyBudget = 5;

		llmCalls.set(0);
		MinimalChatContext first = lifeCycleService.getMinimalChatContext(request, tinyBudget);
		int callsForFirst = llmCalls.get();
		assertTrue(callsForFirst > 0, "A history over the budget must be summarized by the LLM");
		assertNotNull(first.getChatHistory().getConsolidationText());
		assertTrue(first.getChatHistory().getLatestEntries().getInteractions().isEmpty(),
				"The minimized context keeps no verbatim exchange: all of them go into the summary");

		lifeCycleService.getMinimalChatContext(request, tinyBudget);
		assertEquals(callsForFirst, llmCalls.get(), "The second request for the same budget must hit the cache");

		List<MinimalChatContextCacheItem> cached = minimalContextCache.findByUserChatContextCode(session);
		assertTrue(cached.stream().anyMatch(x -> Integer.valueOf(tinyBudget).equals(x.getTokensBudget())),
				"The cached minimal context must record the budget it was made for");
		end(request);
	}

	@Test
	public void testMinimalContextOfAChatWithoutHistoryDoesNotFail() throws Exception {
		IGConfigurableChatModel model = chatModelRuntimeDao.findByCode(DEFAULT_TEST_CHAT_MODEL_CODE);
		String session = newSession();
		GeboChatRequest request = request(session,
				"Please compare in detail the history, the economy and the culture of the main European capitals");
		lifeCycleService.startRequest(request, model, LLMRequestGenerationPolicy.ADDING_RESOURCES_FIT_TOKENS_BUDGET);

		MinimalChatContext minimal = lifeCycleService.getMinimalChatContext(request, 5);
		assertEquals(request.getId(), minimal.getCurrentRequest().getId());
		assertFalse(minimal.getChatHistory().getLatestEntries().getInteractions().iterator().hasNext());
		end(request);
	}

	private int budgetOf(IGConfigurableChatModel model) {
		int budget = (int) Math.min(0.7 * model.getContextLength(), TOKENS_CAP);
		assertTrue(budget > 300, "The test chat model must leave room for a meaningful budget, got " + budget);
		return budget;
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

	private static int documentTokens(LLMChatRequestResources resources) {
		int tokens = 0;
		for (AIDocumentsSet set : List.of(orEmpty(resources.getChatWithDocuments()),
				orEmpty(resources.getRetrievedDocuments()), orEmpty(resources.getUploadedDocuments()),
				orEmpty(resources.getLlmGeneratedDocuments()))) {
			for (AIDocumentReferenceItem item : set.getDocumentItems()) {
				tokens += item.getTokensSize();
			}
		}
		return tokens;
	}

	private static AIDocumentsSet orEmpty(AIDocumentsSet set) {
		return set != null ? set : new AIDocumentsSet();
	}
}
