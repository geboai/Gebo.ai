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
class CutAnswerStreamingTest {

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
	private static ChatResponse chunk(String reasoning, String text, String finish) {
		final AssistantMessage message = AssistantMessage.builder().content(text).build();
		final ChatGenerationMetadata metadata = finish != null ? ChatGenerationMetadata.builder().finishReason(finish).build()
				: ChatGenerationMetadata.NULL;
		final ChatResponse.Builder chunk = ChatResponse.builder().generations(List.of(new Generation(message, metadata)))
				.metadata(GChatAnswerChunk.THINKING_ACTIVE_METADATA, reasoning != null);
		if (reasoning != null) {
			chunk.metadata(GChatAnswerChunk.THINKING_DELTA_METADATA, reasoning);
		}
		return chunk.build();
	}

	private static List<GeboChatMessageEnvelope> run(AbstractChatService service, Flux<ChatResponse> res,
			IGConfigurableChatModel model, GeboChatResponse response, AbstractChatService.AnswerRetry retry) {
		return service.composeFlux(res, null, new GeboChatRequest(), response, Map.of(), false, 0, model, null, null,
				retry).collectList().block();
	}

	private static String streamedText(List<GeboChatMessageEnvelope> envelopes) {
		final StringBuilder out = new StringBuilder();
		envelopes.stream().filter(e -> e.getContent() instanceof String).forEach(e -> out.append(e.getContent()));
		return out.toString();
	}

	@Test
	void theReasoningTookTheBudgetTheAnswerIsWrittenAgain() {
		final String reasoning = "Let me think about every part of the question. ".repeat(20);
		final Flux<ChatResponse> cut = Flux.just(chunk(reasoning, "", null), chunk(null, "Short", "LENGTH"));
		final List<ChatModelThinkingOption> asked = new ArrayList<>();
		final GeboChatResponse response = new GeboChatResponse();

		final List<GeboChatMessageEnvelope> envelopes = run(service(), cut, model(ChatModelThinkingOption.HIGH_THINKING),
				response, thinking -> {
					asked.add(thinking);
					return Flux.just(chunk(null, "The whole answer.\n\n", null), chunk(null, "Its end.", "STOP"));
				});

		assertEquals(List.of(ChatModelThinkingOption.MEDIUM_THINKING), asked, "once, one level lower");
		assertEquals("The whole answer.\n\nIts end.", response.getQueryResponse(), "the answer written again is saved");
		assertEquals("Short" + AbstractChatService.ANSWER_WRITTEN_AGAIN_SEPARATOR + "The whole answer.\n\nIts end.",
				streamedText(envelopes), "streamed after the cut one, line ends kept");
		assertEquals(List.of(CutAnswer.writtenAgainNote().getId()),
				response.getBackendMessages().stream().map(GUserMessage::getId).toList());
		assertTrue(envelopes.stream().anyMatch(e -> e.getContent() instanceof GUserMessage), "the note streamed");
		assertEquals(1, envelopes.stream()
				.filter(e -> e.getContent() instanceof GThinkingEvent t && t.isCompleted()).count());
	}

	@Test
	void aModelLoopingIsWarnedNotAskedAgain() {
		final Flux<ChatResponse> cut = Flux.just(chunk(null, "The same line again. ", null),
				chunk(null, "The same line again. ", "length"));
		final List<ChatModelThinkingOption> asked = new ArrayList<>();
		final GeboChatResponse response = new GeboChatResponse();

		run(service(), cut, model(ChatModelThinkingOption.HIGH_THINKING), response, thinking -> {
			asked.add(thinking);
			return Flux.empty();
		});

		assertTrue(asked.isEmpty());
		assertEquals("The same line again. The same line again. ", response.getQueryResponse());
		assertEquals(List.of(CutAnswer.incompleteWarning().getId()),
				response.getBackendMessages().stream().map(GUserMessage::getId).toList());
	}

	@Test
	void noLowerThinkingLevelIsWarned() {
		final String reasoning = "Thinking it over. ".repeat(20);
		final GeboChatResponse response = new GeboChatResponse();
		final List<ChatModelThinkingOption> asked = new ArrayList<>();

		run(service(), Flux.just(chunk(reasoning, "Cut", "LENGTH")), model(ChatModelThinkingOption.LOW_THINKING),
				response, thinking -> {
					asked.add(thinking);
					return Flux.empty();
				});

		assertTrue(asked.isEmpty());
		assertEquals(List.of(CutAnswer.incompleteWarning().getId()),
				response.getBackendMessages().stream().map(GUserMessage::getId).toList());
	}

	@Test
	void writtenAgainAndCutAgainIsWarned() {
		final String reasoning = "Thinking it over. ".repeat(20);
		final GeboChatResponse response = new GeboChatResponse();

		run(service(), Flux.just(chunk(reasoning, "Cut", "LENGTH")), model(ChatModelThinkingOption.MEDIUM_THINKING),
				response, thinking -> Flux.just(chunk(null, "Cut again", "LENGTH")));

		assertEquals("Cut again", response.getQueryResponse());
		assertEquals(List.of(CutAnswer.writtenAgainNote().getId(), CutAnswer.incompleteWarning().getId()),
				response.getBackendMessages().stream().map(GUserMessage::getId).toList());
	}

	@Test
	void anAnswerEndingNormallyIsLeftAlone() {
		final GeboChatResponse response = new GeboChatResponse();
		final List<ChatModelThinkingOption> asked = new ArrayList<>();

		final List<GeboChatMessageEnvelope> envelopes = run(service(),
				Flux.just(chunk("Brief thought.", "", null), chunk(null, "# Title\n\n", null),
						chunk(null, "Body.", "STOP")),
				model(ChatModelThinkingOption.HIGH_THINKING), response, thinking -> {
					asked.add(thinking);
					return Flux.empty();
				});

		assertTrue(asked.isEmpty());
		assertTrue(response.getBackendMessages().isEmpty());
		assertEquals("# Title\n\nBody.", streamedText(envelopes), "a piece made of line ends is streamed");
	}
}
