/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.chat.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import ai.gebo.architecture.agents.services.INotificationSink;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.service.IGDocumentContentRenderer;
import ai.gebo.architecture.ai.service.IGDocumentContentRendererProvider;
import ai.gebo.llms.abstraction.layer.model.GChatAnswerChunk;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GThinkingEvent;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.pipelines.service.ISinkUIEmitter;
import reactor.core.publisher.Flux;

/**
 * The report writer is an output of its network: its reasoning goes to the user's chat as
 * it comes, completed when the text starts, and its text is the answer only.
 */
class ReportWriterThinkingTest {
	private static final IGDocumentContentRendererProvider NO_RENDERER = new IGDocumentContentRendererProvider() {
		@Override
		public <T> IGDocumentContentRenderer<T> get(T doc) {
			return null;
		}
	};

	private static GChatAnswerChunk chunk(String thinking, String answer) {
		return new GChatAnswerChunk(answer, thinking, !thinking.isEmpty(), null, List.of(), null);
	}

	/** A writer whose model streams the chunks given. */
	private static ReportWriterReactiveAgentServiceImpl writer(GChatAnswerChunk... chunks) {
		return new ReportWriterReactiveAgentServiceImpl(null, null, null, null, null, null, NO_RENDERER) {
			@Override
			protected Flux<GChatAnswerChunk> callLLMReactiveResponses(IGConfigurableChatModel chatModel,
					GPromptTemplateConfig prompt, IChatRequestContext context, Map<String, Object> params) {
				return Flux.just(chunks);
			}
		};
	}

	@Test
	void theReasoningGoesToTheChatBeforeTheText() throws Exception {
		final ISinkUIEmitter chat = mock(ISinkUIEmitter.class);
		final List<String> order = new ArrayList<>();

		final List<String> text = writer(chunk("Outline: intro, findings.\n", ""), chunk("Then the summary.\n", ""),
				chunk("", "# Report"), chunk("", "\n\nBody.")).writeReactive(mock(IGConfigurableChatModel.class),
						new GPromptTemplateConfig(), null, Map.of(), chat)
				.doOnNext(order::add).collectList().block();

		assertEquals(List.of("# Report", "\n\nBody."), text, "the text is the answer only");
		final ArgumentCaptor<GeboChatMessageEnvelope> sent = ArgumentCaptor.forClass(GeboChatMessageEnvelope.class);
		verify(chat, atLeastOnce()).next(sent.capture());
		final List<GThinkingEvent> events = sent.getAllValues().stream()
				.filter(e -> e.getContent() instanceof GThinkingEvent).map(e -> (GThinkingEvent) e.getContent()).toList();
		assertEquals("Outline: intro, findings.\nThen the summary.\n",
				String.join("", events.stream().map(GThinkingEvent::getText).toList()));
		assertEquals(1, events.stream().filter(GThinkingEvent::isCompleted).count());
		assertTrue(events.get(events.size() - 1).isCompleted());
	}

	@Test
	void aSinkThatIsNoChatGetsTheTextOnly() throws Exception {
		assertEquals(List.of("Text."), writer(chunk("Thinking.\n", ""), chunk("", "Text."))
				.writeReactive(mock(IGConfigurableChatModel.class), new GPromptTemplateConfig(), null, Map.of(),
						mock(INotificationSink.class))
				.collectList().block());
	}
}
