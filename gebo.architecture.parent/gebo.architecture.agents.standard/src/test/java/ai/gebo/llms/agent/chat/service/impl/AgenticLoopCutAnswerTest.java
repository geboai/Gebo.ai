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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.metadata.ChatGenerationMetadata;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;

import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.services.INotificationSink;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.service.IGDocumentContentRenderer;
import ai.gebo.architecture.ai.service.IGDocumentContentRendererProvider;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.ToolCallsListener;
import ai.gebo.llms.agent.chat.service.impl.AgenticLoopReactiveAgentServiceImpl.LoopIteration;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GThinkingEvent;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.services.impl.CutAnswer;
import ai.gebo.llms.chat.abstraction.layer.services.impl.ThinkingStream;
import ai.gebo.llms.chat.pipelines.service.ISinkUIEmitter;
import ai.gebo.model.GUserMessage;
import reactor.core.publisher.Flux;

/**
 * Pins the agentic loop's reasoning streamed to the chat and its answer cut by the
 * model's output limit: written again once with less reasoning when the reasoning took
 * the limit, the one written again kept; the user told otherwise.
 */
class AgenticLoopCutAnswerTest {
	private static final IGDocumentContentRendererProvider NO_RENDERER = new IGDocumentContentRendererProvider() {
		@Override
		public <T> IGDocumentContentRenderer<T> get(T doc) {
			return null;
		}
	};

	/** A loop agent whose model streams scripted chunks, one list per model call. */
	static class ScriptedChunksAgent extends AgenticLoopReactiveAgentServiceImpl {
		final List<List<ChatResponse>> calls;
		final List<IGConfigurableChatModel> models = new ArrayList<>();
		final IGConfigurableChatModel lowered = mock(IGConfigurableChatModel.class);
		final List<IGConfigurableChatModel> loweredFrom = new ArrayList<>();

		ScriptedChunksAgent(List<List<ChatResponse>> calls) {
			super(null, null, null, null, null, null, NO_RENDERER);
			this.calls = calls;
		}

		@Override
		protected Flux<ChatResponse> callLLMReactiveResponses(IGConfigurableChatModel chatModel,
				GPromptTemplateConfig prompt, IChatRequestContext context, Map<String, Object> params) {
			models.add(chatModel);
			return Flux.fromIterable(calls.get(Math.min(models.size() - 1, calls.size() - 1)));
		}

		@Override
		protected IGConfigurableChatModel lowerThinking(IGConfigurableChatModel agentModel) {
			loweredFrom.add(agentModel);
			return lowered;
		}
	}

	/** A chunk: the reasoning so far, its text, why the model stopped (null while it writes). */
	private static ChatResponse chunk(String reasoningSoFar, String text, String finish) {
		final AssistantMessage message = AssistantMessage.builder().content(text)
				.properties(reasoningSoFar != null ? Map.of(ThinkingStream.REASONING_CONTENT_METADATA, reasoningSoFar)
						: Map.of())
				.build();
		return new ChatResponse(List.of(new Generation(message,
				finish != null ? ChatGenerationMetadata.builder().finishReason(finish).build()
						: ChatGenerationMetadata.NULL)));
	}

	private static String run(ScriptedChunksAgent agent, IGConfigurableChatModel model, List<LoopIteration> history,
			INotificationSink sink) {
		final AgentNetworkParticipant persona = mock(AgentNetworkParticipant.class);
		when(persona.getNetworkAgentName()).thenReturn("agenticLoopAgent");
		return String.join("", agent.iteration(1, 3, 10_000, history, model, new GPromptTemplateConfig(), null, persona,
				sink, new ToolCallsListener()).collectList().block());
	}

	@Test
	void theReasoningIsSentToTheChatUntilTheTextStarts() {
		final ScriptedChunksAgent agent = new ScriptedChunksAgent(List.of(List.of(chunk("Thinking about it.\n", "", null),
				chunk("Thinking about it.\nMore thoughts.\n", "", null),
				chunk("Thinking about it.\nMore thoughts.\n", "The answer.", "STOP"))));
		final ISinkUIEmitter chat = mock(ISinkUIEmitter.class);

		final String answer = run(agent, mock(IGConfigurableChatModel.class), new ArrayList<>(), chat);

		assertEquals("The answer.", answer, "the reasoning is never part of the answer");
		final ArgumentCaptor<GeboChatMessageEnvelope> sent = ArgumentCaptor.forClass(GeboChatMessageEnvelope.class);
		verify(chat, atLeastOnce()).next(sent.capture());
		final List<GThinkingEvent> events = sent.getAllValues().stream()
				.filter(e -> e.getContent() instanceof GThinkingEvent).map(e -> (GThinkingEvent) e.getContent()).toList();
		assertEquals("Thinking about it.\nMore thoughts.\n",
				String.join("", events.stream().map(GThinkingEvent::getText).toList()));
		assertTrue(events.get(events.size() - 1).isCompleted());
		assertEquals(1, events.stream().filter(GThinkingEvent::isCompleted).count());
	}

	@Test
	void aSinkThatIsNoChatGetsNoReasoning() {
		final ScriptedChunksAgent agent = new ScriptedChunksAgent(
				List.of(List.of(chunk("Thinking.\n", "", null), chunk("Thinking.\n", "The answer.", "STOP"))));

		assertEquals("The answer.", run(agent, mock(IGConfigurableChatModel.class), new ArrayList<>(),
				mock(INotificationSink.class)));
	}

	@Test
	void anAnswerCutWhileReasoningIsWrittenAgainWithLessReasoning() {
		final String reasoning = "Let me weigh every part of the question. ".repeat(20);
		final ScriptedChunksAgent agent = new ScriptedChunksAgent(
				List.of(List.of(chunk(reasoning, "", null), chunk(reasoning, "The answer begins", "LENGTH")),
						List.of(chunk(null, "The whole answer.", "STOP"))));
		final IGConfigurableChatModel model = mock(IGConfigurableChatModel.class);
		final List<LoopIteration> history = new ArrayList<>();

		final String streamed = run(agent, model, history, mock(INotificationSink.class));

		assertEquals(List.of(model, agent.lowered), agent.models, "once more, asking for less reasoning");
		assertEquals("The answer begins" + AgenticLoopReactiveAgentServiceImpl.ANSWER_RESTART + "The whole answer.",
				streamed, "what the user saw of the cut answer ends with the mark");
		assertEquals(AgenticLoopReactiveAgentServiceImpl.DISCARDED_CUT, history.get(0).discardedFor());
		assertTrue(history.get(0).draftToBuildOn(), "the next iteration is told the cut draft");
		assertFalse(history.get(1).cut());
	}

	@Test
	void anAnswerCutWhileWritingIsToldNotWrittenAgain() {
		final ScriptedChunksAgent agent = new ScriptedChunksAgent(
				List.of(List.of(chunk("Brief.", "A long answer, line after line. ".repeat(10), "length"))));
		final List<LoopIteration> history = new ArrayList<>();
		final GeboChatResponse response = new GeboChatResponse();

		run(agent, mock(IGConfigurableChatModel.class), history, mock(INotificationSink.class));
		agent.warnAboutCutAnswer(response, history);

		assertEquals(1, agent.models.size(), "a model looping is not asked again");
		assertTrue(agent.loweredFrom.isEmpty());
		assertTrue(history.get(0).cut());
		assertEquals(List.of(CutAnswer.incompleteWarning().getId()),
				response.getBackendMessages().stream().map(GUserMessage::getId).toList());
	}

	@Test
	void anEmptyAnswerCutIsWrittenAgainWithLessReasoning() {
		final String reasoning = "Thinking it all over. ".repeat(20);
		final ScriptedChunksAgent agent = new ScriptedChunksAgent(List.of(List.of(chunk(reasoning, "", "LENGTH")),
				List.of(chunk(null, "The answer.", "STOP"))));
		final IGConfigurableChatModel model = mock(IGConfigurableChatModel.class);

		final String streamed = run(agent, model, new ArrayList<>(), mock(INotificationSink.class));

		assertEquals("The answer.", streamed, "nothing was shown: no mark");
		assertEquals(List.of(model, agent.lowered), agent.models);
	}

	@Test
	void anEmptyAnswerNotCutIsWrittenAgainAsBefore() {
		final ScriptedChunksAgent agent = new ScriptedChunksAgent(
				List.of(List.of(chunk(null, "", "STOP")), List.of(chunk(null, "The answer.", "STOP"))));
		final IGConfigurableChatModel model = mock(IGConfigurableChatModel.class);

		run(agent, model, new ArrayList<>(), mock(INotificationSink.class));

		assertEquals(List.of(model, model), agent.models, "the same model, as before");
		assertTrue(agent.loweredFrom.isEmpty());
	}

	@Test
	void theAnswerWrittenAgainIsKeptAndTheUserTold() {
		final ScriptedChunksAgent agent = new ScriptedChunksAgent(List.of(List.of()));
		final GeboChatResponse response = new GeboChatResponse();
		final String cut = "The answer begins\n\n---";
		response.setQueryResponse(cut + "\n\nThe whole answer.");

		agent.keepTheAnswerWrittenAgain(response, cut.length());

		assertEquals("The whole answer.", response.getQueryResponse());
		assertEquals(List.of(CutAnswer.writtenAgainNote().getId()),
				response.getBackendMessages().stream().map(GUserMessage::getId).toList());
	}

	@Test
	void noAnswerWrittenAgainLeavesItAlone() {
		final ScriptedChunksAgent agent = new ScriptedChunksAgent(List.of(List.of()));
		final GeboChatResponse response = new GeboChatResponse();
		response.setQueryResponse("The answer.");

		agent.keepTheAnswerWrittenAgain(response, -1);
		agent.warnAboutCutAnswer(response, List.of(new LoopIteration(1, "The answer.", List.of())));

		assertEquals("The answer.", response.getQueryResponse());
		assertTrue(response.getBackendMessages().isEmpty());
	}

	@Test
	void aMarkInTheTextIsNoTextOfTheAnswer() {
		assertFalse(AgenticLoopReactiveAgentServiceImpl.ANSWER_RESTART_MARK.isBlank());
		assertEquals(CutAnswer.SEPARATOR, AgenticLoopReactiveAgentServiceImpl.ANSWER_RESTART.replace(
				AgenticLoopReactiveAgentServiceImpl.ANSWER_RESTART_MARK,
				AgenticLoopReactiveAgentServiceImpl.ANSWER_RESTART_RULE), "the mark becomes the separator");
	}
}
