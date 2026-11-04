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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.metadata.ChatGenerationMetadata;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.beans.factory.ObjectProvider;

import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.core.contents.security.services.IGKnowledgebaseVisibilityService;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.GChatAnswer;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGTextToSpeechModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGTranscriptModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCalling;
import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCallingManagerFactory;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.repository.LLMGeneratedResourceRepository;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatAgentsToolsDescriber;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatResponseParsingFixerServiceRepository;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatStorageAreaService;
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.IGSecurityService;

/**
 * A direct chat hands its model what the chat adds to the call: the chat as the
 * caller, nothing else; without the factory the call is the plain one.
 */
class ChatToolCallingTest {

	@SuppressWarnings("unchecked")
	private static AbstractChatService service(GeboToolCallingManagerFactory factory) {
		return mock(AbstractChatService.class,
				withSettings().useConstructor(mock(IGChatModelRuntimeConfigurationDao.class),
						mock(IGToolCallbackSourceRepositoryPattern.class), mock(IGPersistentObjectManager.class),
						mock(IGPromptConfigDao.class), mock(InteractionsContextService.class),
						mock(IGSecurityService.class), mock(IGChatResponseParsingFixerServiceRepository.class),
						mock(IGChatStorageAreaService.class), mock(LLMGeneratedResourceRepository.class),
						mock(IGKnowledgebaseVisibilityService.class), mock(IGChatSessionLifeCycleService.class),
						mock(IGTextToSpeechModelRuntimeConfigurationDao.class),
						mock(IGTranscriptModelRuntimeConfigurationDao.class),
						mock(IGSecurityAuditLoggerService.class), (ObjectProvider<IGChatAgentsToolsDescriber>) null,
						factory).defaultAnswer(Answers.CALLS_REAL_METHODS));
	}

	private static GChatAnswer answer(String text) {
		final ChatResponse source = new ChatResponse(List.of(new Generation(new AssistantMessage(text),
				ChatGenerationMetadata.builder().finishReason("STOP").build())));
		return new GChatAnswer(text, "", "STOP", source);
	}

	private static IGConfigurableChatModel model() throws Exception {
		final IGConfigurableChatModel model = mock(IGConfigurableChatModel.class);
		when(model.getConfig()).thenReturn(new GBaseChatModelConfig());
		when(model.answer(any(), any(), any())).thenReturn(answer("plain"));
		when(model.answer(any(), any(), any(), any())).thenReturn(answer("governed"));
		return model;
	}

	private static LLMChatRequestResources resources() {
		final GeboChatRequest request = new GeboChatRequest();
		request.setId("request-1");
		final LLMChatRequestResources resources = new LLMChatRequestResources();
		resources.setCurrentRequest(request);
		return resources;
	}

	@Test
	void aDirectChatNamesItselfAsTheCallerOfItsModelCall() throws Exception {
		final IGConfigurableChatModel model = model();
		final GeboChatResponse response = new GeboChatResponse();

		service(new GeboToolCallingManagerFactory()).chat(new GPromptTemplateConfig(), resources(), response, model);

		final ArgumentCaptor<GeboToolCalling> toolCalling = ArgumentCaptor.forClass(GeboToolCalling.class);
		verify(model).answer(any(), any(), any(), toolCalling.capture());
		verify(model, never()).answer(any(), any(), any());
		assertEquals("chat:request-1", toolCalling.getValue().getCaller());
		assertNull(toolCalling.getValue().getListener(), "the request's recorder keeps recording");
		assertEquals("governed", response.getQueryResponse());
	}

	@Test
	void withoutTheFactoryTheCallIsThePlainOne() throws Exception {
		final IGConfigurableChatModel model = model();
		final GeboChatResponse response = new GeboChatResponse();

		service(null).chat(new GPromptTemplateConfig(), resources(), response, model);

		verify(model).answer(any(), any(), any());
		verify(model, never()).answer(any(), any(), any(), any());
		assertEquals("plain", response.getQueryResponse());
	}
}
