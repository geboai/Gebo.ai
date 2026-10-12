/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.pipelines.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMRequestGenerationPolicy;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.services.IGDocumentsSearchService;
import ai.gebo.llms.chat.abstraction.layer.services.IGRankerService;
import ai.gebo.llms.chat.abstraction.layer.session.model.MinimalChatContext;
import ai.gebo.llms.chat.pipelines.config.ChatPipelinesConfiguration;
import ai.gebo.llms.chat.pipelines.service.IInternalKnowledgeLLMAssistedRetrieveService;
import ai.gebo.security.services.IGSecurityService;
import reactor.core.publisher.Flux;

/**
 * Pins how the LLM assisted retrieval ranks what it found: the RAG chat removes the
 * fragments the ranker service judges irrelevant, the deep search only ranks them
 * (its analysis judges them itself).
 */
class InternalKnowledgeLLMAssistedRetrieveRankingTest {

	@Test
	void theFragmentsJudgedIrrelevantAreRemovedOnlyWhenAsked() throws Exception {
		IGRankerService ranker = mock(IGRankerService.class);
		AIDocumentsSet found = new AIDocumentsSet();
		AIDocumentsSet ranked = new AIDocumentsSet();
		AIDocumentsSet filtered = new AIDocumentsSet();
		when(ranker.rank(any(AIDocumentsSet.class), anyString(), anyInt())).thenReturn(ranked);
		when(ranker.rankAndRemoveIrrelevant(any(AIDocumentsSet.class), anyString(), anyInt())).thenReturn(filtered);
		InternalKnowledgeLLMAssistedRetrieveServiceImpl service = new InternalKnowledgeLLMAssistedRetrieveServiceImpl(
				mock(IGChatSessionLifeCycleService.class), mock(ChatPipelinesConfiguration.class),
				mock(IGPromptConfigDao.class), mock(IGDocumentsSearchService.class), mock(IGSecurityService.class),
				ranker);

		assertSame(ranked, service.rankFound(found, "query", 8, false), "the deep search: ranked only");
		verify(ranker, never()).rankAndRemoveIrrelevant(any(AIDocumentsSet.class), anyString(), anyInt());

		assertSame(filtered, service.rankFound(found, "query", 8, true), "the RAG chat: irrelevant ones removed");
	}

	@Test
	void theRetrieveWithoutTheChoiceStillRemovesTheIrrelevantFragments() throws Exception {
		List<Boolean> asked = new ArrayList<>();
		IInternalKnowledgeLLMAssistedRetrieveService service = new IInternalKnowledgeLLMAssistedRetrieveService() {
			@Override
			public Flux<AIDocumentsSet> doDocumentsRetrieve(MinimalChatContext minimalChatContext,
					IGConfigurableChatModel targetChatModel, LLMRequestGenerationPolicy policy, int topK,
					boolean removeIrrelevant) {
				asked.add(removeIrrelevant);
				return Flux.empty();
			}
		};

		service.doDocumentsRetrieve(null, null, null, 8);

		assertEquals(List.of(true), asked);
	}
}
