/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.abstraction.layer.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.metadata.ChatGenerationMetadata;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;

import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.core.contents.security.services.IGKnowledgebaseVisibilityService;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGTextToSpeechModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGTranscriptModelRuntimeConfigurationDao;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GThinkingEvent;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.repository.LLMGeneratedResourceRepository;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatResponseParsingFixerServiceRepository;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatStorageAreaService;
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.IGSecurityService;
import reactor.core.publisher.Flux;

/**
 * A streamed answer of a model writing its reasoning between thinking tags: the reasoning
 * reaches the user as thinking events, completed before the answer, and never as answer
 * text, however the chunks cut the tags.
 */
class InlineThinkingStreamingTest {

	private static AbstractChatService service() {
		return mock(AbstractChatService.class,
				withSettings().useConstructor(mock(IGChatModelRuntimeConfigurationDao.class),
						mock(IGToolCallbackSourceRepositoryPattern.class), mock(IGPersistentObjectManager.class),
						mock(IGPromptConfigDao.class), mock(InteractionsContextService.class),
						mock(IGSecurityService.class), mock(IGChatResponseParsingFixerServiceRepository.class),
						mock(IGChatStorageAreaService.class), mock(LLMGeneratedResourceRepository.class),
						mock(IGKnowledgebaseVisibilityService.class), mock(IGChatSessionLifeCycleService.class),
						mock(IGTextToSpeechModelRuntimeConfigurationDao.class),
						mock(IGTranscriptModelRuntimeConfigurationDao.class), mock(IGSecurityAuditLoggerService.class))
						.defaultAnswer(Answers.CALLS_REAL_METHODS));
	}

	/** A model whose reasoning is written in its text between thinking tags */
	private static IGConfigurableChatModel taggingModel() {
		final IGConfigurableChatModel model = mock(IGConfigurableChatModel.class);
		when(model.getConfig()).thenReturn(new GBaseChatModelConfig());
		when(model.isApplyThinkingMarkupHandling()).thenReturn(true);
		return model;
	}

	private static Flux<ChatResponse> chunks(String... texts) {
		return Flux.range(0, texts.length).map(i -> {
			final AssistantMessage message = AssistantMessage.builder().content(texts[i]).properties(Map.of()).build();
			return new ChatResponse(List.of(new Generation(message, i == texts.length - 1
					? ChatGenerationMetadata.builder().finishReason("STOP").build()
					: ChatGenerationMetadata.NULL)));
		});
	}

	private static List<GeboChatMessageEnvelope> run(Flux<ChatResponse> res, GeboChatResponse response) {
		return service().composeFlux(res, null, new GeboChatRequest(), response, Map.of(), false, 0, taggingModel(),
				null, null, null).collectList().block();
	}

	private static String streamedText(List<GeboChatMessageEnvelope> envelopes) {
		final StringBuilder out = new StringBuilder();
		envelopes.stream().filter(e -> e.getContent() instanceof String).forEach(e -> out.append(e.getContent()));
		return out.toString();
	}

	private static String streamedThinking(List<GeboChatMessageEnvelope> envelopes) {
		final StringBuilder out = new StringBuilder();
		envelopes.stream().filter(e -> e.getContent() instanceof GThinkingEvent)
				.forEach(e -> out.append(((GThinkingEvent) e.getContent()).getText()));
		return out.toString();
	}

	@Test
	void theReasoningIsStreamedAsThinkingTheAnswerAsText() {
		final GeboChatResponse response = new GeboChatResponse();

		final List<GeboChatMessageEnvelope> envelopes = run(
				chunks("<think>The user", " asks.</th", "ink>\n\nThe ans", "wer: 2 <", " 3 <"), response);

		assertEquals("\n\nThe answer: 2 < 3 <", streamedText(envelopes),
				"the chunk closing the reasoning gives only its answer, a held '<' comes at the end");
		assertEquals("The user asks.", streamedThinking(envelopes));
		int completed = -1;
		int firstAnswer = -1;
		for (int i = 0; i < envelopes.size(); i++) {
			final Object content = envelopes.get(i).getContent();
			if (content instanceof GThinkingEvent t && t.isCompleted()) {
				completed = i;
			}
			if (firstAnswer < 0 && content instanceof String text && !text.isBlank()) {
				firstAnswer = i;
			}
		}
		assertTrue(completed >= 0 && completed < firstAnswer, "the reasoning completed before the answer");
		assertEquals("\n\nThe answer: 2 < 3 <", response.getQueryResponse());
		assertEquals(List.of("The user asks."), response.getThinkingOutputs());
	}

	@Test
	void aModelWritingNoTagsAnswersAsItStreams() {
		final GeboChatResponse response = new GeboChatResponse();

		final List<GeboChatMessageEnvelope> envelopes = run(chunks("The answer", " is 4."), response);

		assertEquals(List.of("The answer", " is 4."), envelopes.stream().filter(e -> e.getContent() instanceof String)
				.map(e -> (String) e.getContent()).toList(), "each chunk as it comes");
		assertFalse(envelopes.stream().anyMatch(e -> e.getContent() instanceof GThinkingEvent));
		assertEquals("The answer is 4.", response.getQueryResponse());
	}
}
