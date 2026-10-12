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

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

import ai.gebo.architecture.rag.support.layer.model.AIDocumentFragment;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentReferenceItem;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMRequestGenerationPolicy;
import ai.gebo.llms.chat.abstraction.layer.repository.ChatFullSessionStateRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.MinimalChatContextCacheItemRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.ShrinkedChatSessionStateRepository;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.session.model.CSSInteractionReferredContent;
import ai.gebo.llms.chat.abstraction.layer.session.model.ChatFullSessionState;
import ai.gebo.llms.chat.abstraction.layer.session.model.GDocumentReferenceSTO;

@TestPropertySource(properties = "ai.gebo.chatsession.maximum-full-state-bytes=60000")
public class ChatFullStateSizeGuardTest extends AbstractBaseTestLLmsIntegrationTests {

	@Autowired
	private IGChatSessionLifeCycleService lifeCycleService;
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
	}

	@Test
	public void testTheOldestDocumentsAreDroppedWhenTheFullStateGetsTooBig() throws Exception {
		String chat = newSession();
		for (int i = 0; i < 5; i++) {
			exchange(chat, "Question " + i);
		}
		ChatFullSessionState full = fullRepository.findById(chat).orElseThrow();
		for (int interaction = 0; interaction < 5; interaction++) {
			for (int n = 0; n < 2; n++) {
				full.getRetrievedDocuments().getValue().getData().add(new CSSInteractionReferredContent<GDocumentReferenceSTO>(
						interaction, document("doc-" + interaction + "-" + n), null));
			}
		}
		fullRepository.save(full);

		exchange(chat, "Question 5");

		ChatFullSessionState saved = fullRepository.findById(chat).orElseThrow();
		List<Integer> kept = saved.getRetrievedDocuments().getValue().getData().stream()
				.map(CSSInteractionReferredContent::getInteractionIndex).distinct().toList();
		assertFalse(kept.contains(0), "The oldest interaction's documents are dropped first");
		assertTrue(kept.contains(4), "The newest documents are kept: " + kept);
		assertEquals(6, saved.getChatHistory().getValue().getInteractions().size(), "The history is never pruned");
	}

	private static AIDocumentReferenceItem document(String code) {
		AIDocumentFragment fragment = new AIDocumentFragment();
		fragment.setCode(code + "-fragment");
		fragment.setDocumentContent("x".repeat(12000));
		fragment.setTokensSize(3000);
		AIDocumentReferenceItem document = new AIDocumentReferenceItem();
		document.setCode(code);
		document.setName(code + ".txt");
		document.getFragments().add(fragment);
		document.recalculateSize();
		return document;
	}

	private void exchange(String chat, String question) throws Exception {
		IGConfigurableChatModel model = chatModelRuntimeDao.findByCode(DEFAULT_TEST_CHAT_MODEL_CODE);
		GeboChatRequest request = new GeboChatRequest();
		request.setId(UUID.randomUUID().toString());
		request.setUserChatContextCode(chat);
		request.setQuery(question);
		lifeCycleService.startRequest(request, model, LLMRequestGenerationPolicy.ADDING_RESOURCES_DO_NOT_FIT_TOKENS_BUDGET);
		GeboChatResponse response = lifeCycleService.createEmptyResponse(request);
		response.setQueryResponse("Answer to " + question);
		lifeCycleService.endRequest(request, response);
	}

	private String newSession() throws Exception {
		GeboChatRequest first = new GeboChatRequest();
		first.setQuery("new chat");
		lifeCycleService.createChatSession(first);
		return first.getUserChatContextCode();
	}
}
