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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.metadata.ChatGenerationMetadata;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;

import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.environment.ArchitectureType;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.core.contents.security.services.IGKnowledgebaseVisibilityService;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig.ChatModelThinkingOption;
import ai.gebo.llms.abstraction.layer.model.GChatAnswer;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGTextToSpeechModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGTranscriptModelRuntimeConfigurationDao;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.repository.LLMGeneratedResourceRepository;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatResponseParsingFixerServiceRepository;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatStorageAreaService;
import ai.gebo.model.GUserMessage;
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.IGSecurityAuditLoggerService.SecurityEvent;
import ai.gebo.security.services.IGSecurityService;

/**
 * A chat answered by a blocking call: the answer saved is the answer without its
 * reasoning, the reasoning saved apart; a cut answer whose reasoning took the generated
 * tokens is asked again with less reasoning.
 */
class CalledAnswerTest {

	private static AbstractChatService service() {
		final IGSecurityAuditLoggerService audit = mock(IGSecurityAuditLoggerService.class);
		when(audit.newSecurityEvent()).thenAnswer(i -> new SecurityEvent(ArchitectureType.MONOLITHIC, "user", "ip",
				"app", "correlation", "now", "test", "POST", "/chat"));
		return mock(AbstractChatService.class,
				withSettings().useConstructor(mock(IGChatModelRuntimeConfigurationDao.class),
						mock(IGToolCallbackSourceRepositoryPattern.class), mock(IGPersistentObjectManager.class),
						mock(IGPromptConfigDao.class), mock(InteractionsContextService.class),
						mock(IGSecurityService.class), mock(IGChatResponseParsingFixerServiceRepository.class),
						mock(IGChatStorageAreaService.class), mock(LLMGeneratedResourceRepository.class),
						mock(IGKnowledgebaseVisibilityService.class), mock(IGChatSessionLifeCycleService.class),
						mock(IGTextToSpeechModelRuntimeConfigurationDao.class),
						mock(IGTranscriptModelRuntimeConfigurationDao.class), audit)
						.defaultAnswer(Answers.CALLS_REAL_METHODS));
	}

	private static GChatAnswer answer(String text, String thinking, String finish) {
		final ChatResponse source = new ChatResponse(List.of(new Generation(new AssistantMessage(text),
				ChatGenerationMetadata.builder().finishReason(finish).build())));
		return new GChatAnswer(text, thinking, finish, source);
	}

	private static IGConfigurableChatModel model(ChatModelThinkingOption thinking, GChatAnswer answer)
			throws Exception {
		final GBaseChatModelConfig config = new GBaseChatModelConfig();
		config.setThinking(thinking);
		final IGConfigurableChatModel model = mock(IGConfigurableChatModel.class);
		when(model.getConfig()).thenReturn(config);
		when(model.answer(any(), any(), any())).thenReturn(answer);
		return model;
	}

	private static GeboChatResponse call(IGConfigurableChatModel model) throws Exception {
		final GeboChatResponse response = new GeboChatResponse();
		service().callChatClient(model, new GPromptTemplateConfig(), null, new GeboChatRequest(), response,
				mock(IChatRequestContext.class), null);
		return response;
	}

	@Test
	void theAnswerIsSavedItsReasoningApart() throws Exception {
		final GeboChatResponse response = call(model(ChatModelThinkingOption.MEDIUM_THINKING,
				answer("The answer.", "Step one.\n\nStep two.", "STOP")));

		assertEquals("The answer.", response.getQueryResponse());
		assertEquals(List.of("Step one.", "Step two."), response.getThinkingOutputs());
		assertTrue(response.getBackendMessages().isEmpty());
	}

	@Test
	void aCutAnswerWhoseReasoningTookTheBudgetIsAskedAgainWithLessReasoning() throws Exception {
		final IGConfigurableChatModel model = model(ChatModelThinkingOption.HIGH_THINKING,
				answer("Sho", "Thinking it over. ".repeat(40), "LENGTH"));
		final IGConfigurableChatModel lower = model(ChatModelThinkingOption.MEDIUM_THINKING,
				answer("The whole answer.", "Brief.", "STOP"));
		when(model.cloneWithOptions(any(), any())).thenReturn(lower);

		final GeboChatResponse response = call(model);

		assertEquals("The whole answer.", response.getQueryResponse());
		assertEquals(List.of("Brief."), response.getThinkingOutputs());
		assertTrue(response.getBackendMessages().isEmpty(), "the user never saw the cut one");
	}

	@Test
	void aCutAnswerTheReasoningDidNotTakeIsWarned() throws Exception {
		final GeboChatResponse response = call(model(ChatModelThinkingOption.HIGH_THINKING,
				answer("A long answer cut at the end of its tokens", "Brief.", "LENGTH")));

		assertEquals("A long answer cut at the end of its tokens", response.getQueryResponse());
		assertEquals(List.of(CutAnswer.incompleteWarning().getId()),
				response.getBackendMessages().stream().map(GUserMessage::getId).toList());
	}
}
