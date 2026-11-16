/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.util.stream.Collectors;

import org.springframework.ai.chat.messages.part.StreamingParts;
import org.springframework.ai.chat.messages.part.ToolCallPart;

import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GToolCallProgress;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

import java.util.ArrayList;
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
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig.ChatModelThinkingOption;
import ai.gebo.llms.abstraction.layer.model.GChatAnswerChunk;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGTextToSpeechModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGTranscriptModelRuntimeConfigurationDao;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GThinkingEvent;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.repository.LLMGeneratedResourceRepository;
import ai.gebo.llms.chat.pipelines.service.IPipelineStepModelsService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatResponseParsingFixerServiceRepository;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatStorageAreaService;
import ai.gebo.model.GUserMessage;
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.IGSecurityService;
import reactor.core.publisher.Flux;

/**
 * Pins a streamed answer cut by its generated tokens: written again with less reasoning
 * when its reasoning took them, the one written again saved; the user warned otherwise.
 */
/**
 * Pins the tool calls the model writes as the chat streams them: a progress envelope for
 * each piece of the arguments and for the call complete, in order with the answer, and
 * no answer envelope for a chunk carrying only a piece of a call.
 */
class ToolCallProgressStreamingTest {

	private static AbstractChatService service() {
		return mock(AbstractChatService.class,
				withSettings().useConstructor(mock(IGChatModelRuntimeConfigurationDao.class),
						mock(IGToolCallbackSourceRepositoryPattern.class), mock(IGPersistentObjectManager.class),
						mock(IGPromptConfigDao.class), mock(InteractionsContextService.class),
						mock(IGSecurityService.class), mock(IGChatResponseParsingFixerServiceRepository.class),
						mock(IGChatStorageAreaService.class), mock(LLMGeneratedResourceRepository.class),
						mock(IGKnowledgebaseVisibilityService.class), mock(IGChatSessionLifeCycleService.class),
						mock(IGTextToSpeechModelRuntimeConfigurationDao.class),
						mock(IGTranscriptModelRuntimeConfigurationDao.class), mock(IGSecurityAuditLoggerService.class),
						null, null, mock(IPipelineStepModelsService.class))
						.defaultAnswer(Answers.CALLS_REAL_METHODS));
	}

	private static IGConfigurableChatModel model(ChatModelThinkingOption thinking) {
		final GBaseChatModelConfig config = new GBaseChatModelConfig();
		config.setThinking(thinking);
		final IGConfigurableChatModel model = mock(IGConfigurableChatModel.class);
		when(model.getConfig()).thenReturn(config);
		return model;
	}

	/**
	 * A normalized chunk (ThinkingNormalizationAdvisor): the reasoning it adds, its text,
	 * why the model stopped (null while it writes).
	 */
	private static List<GeboChatMessageEnvelope> run(AbstractChatService service, Flux<ChatResponse> res,
			IGConfigurableChatModel model, GeboChatResponse response, AbstractChatService.AnswerRetry retry) {
		return service.composeFlux(res.map(GChatAnswerChunk::of), null, new GeboChatRequest(), response, Map.of(),
				false, 0, model, null, null, retry).collectList().block();
	}

	/** A piece of the arguments of the call "1" to searchKnowledgeBase, as 2.1 streams it. */
	private static ChatResponse piece(String arguments) {
		return new ChatResponse(List.of(new Generation(AssistantMessage.builder()
				.parts(List.of(StreamingParts.partial(ToolCallPart.of(
						new AssistantMessage.ToolCall("1", "function", "searchKnowledgeBase", arguments)), 0)))
				.build())));
	}

	/** The announcement of the call complete (ThinkingNormalizationAdvisor). */
	private static ChatResponse complete(String arguments) {
		return ChatResponse.builder().generations(List.of(new Generation(new AssistantMessage(""))))
				.metadata(GChatAnswerChunk.TOOL_CALLS_COMPLETE_METADATA,
						List.of(new GChatAnswerChunk.ToolCallProgress("1", 0, "searchKnowledgeBase", arguments, true)))
				.build();
	}

	private static ChatResponse answer(String text, String finish) {
		return new ChatResponse(List.of(new Generation(AssistantMessage.builder().content(text).build(),
				finish != null ? ChatGenerationMetadata.builder().finishReason(finish).build()
						: ChatGenerationMetadata.NULL)));
	}

	@Test
	void theCallIsStreamedInOrderWithTheAnswer() {
		final GeboChatResponse response = new GeboChatResponse();
		final List<GeboChatMessageEnvelope> envelopes = run(service(),
				Flux.just(piece("{\"query\":"), piece("\"qdrant\"}"), complete("{\"query\":\"qdrant\"}"),
						answer("The key is set", null), answer(" in application.yml.", "STOP")),
				model(ChatModelThinkingOption.NO_THINKING), response, null);

		final List<String> sequence = envelopes.stream()
				.filter(e -> e.getContent() instanceof GToolCallProgress || e.getContent() instanceof String)
				.map(e -> e.getContent() instanceof GToolCallProgress p
						? (p.isComplete() ? "call:" : "piece:") + p.getName() + ":" + p.getArguments()
						: "text:" + e.getContent())
				.collect(Collectors.toList());
		assertEquals(List.of("piece:searchKnowledgeBase:{\"query\":", "piece:searchKnowledgeBase:\"qdrant\"}",
				"call:searchKnowledgeBase:{\"query\":\"qdrant\"}", "text:The key is set", "text: in application.yml."),
				sequence);
		assertEquals("The key is set in application.yml.", response.getQueryResponse());
	}

	@Test
	void aChunkCarryingOnlyAPieceAddsNoAnswerEnvelope() {
		final List<GeboChatMessageEnvelope> envelopes = run(service(),
				Flux.just(piece("{\"q\":1}"), answer("Done.", "STOP")), model(ChatModelThinkingOption.NO_THINKING),
				new GeboChatResponse(), null);

		assertTrue(envelopes.stream().noneMatch(e -> e.getContent() instanceof String text && text.isEmpty()));
		assertEquals(1, envelopes.stream().filter(e -> e.getContent() instanceof String).count());
	}
}
