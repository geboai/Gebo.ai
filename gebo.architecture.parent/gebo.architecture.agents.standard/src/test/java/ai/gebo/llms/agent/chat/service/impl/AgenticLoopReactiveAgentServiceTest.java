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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.services.INotificationSink;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.model.ITokensCountable;
import ai.gebo.architecture.ai.service.IGDocumentContentRenderer;
import ai.gebo.architecture.ai.service.IGDocumentContentRendererProvider;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.ToolCallsListener;
import ai.gebo.llms.agent.chat.service.impl.AgenticLoopReactiveAgentServiceImpl.ControlMarkerStripper;
import ai.gebo.llms.agent.chat.service.impl.AgenticLoopReactiveAgentServiceImpl.LoopIteration;
import ai.gebo.llms.agent.standard.services.StandardAgentsNetworkEnvironmentEntries;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.DeliverableIntent;
import reactor.core.publisher.Flux;

/**
 * Pins the loop of the single agent with tools: the control markers never reach the
 * user, the model decides when the loop ends, the iterations are bounded and each
 * one receives the history of the previous ones.
 */
class AgenticLoopReactiveAgentServiceTest {
	private static final String STOP = ReportWriterReactiveAgentServiceImpl.AGENT_CONTROL_FINISHED;
	private static final String MORE = ReportWriterReactiveAgentServiceImpl.AGENT_CONTROL_MORE_TOOLS;

	private static final IGDocumentContentRendererProvider NO_RENDERER = new IGDocumentContentRendererProvider() {
		@Override
		public <T> IGDocumentContentRenderer<T> get(T doc) {
			return null;
		}
	};

	/** A loop agent whose model streams scripted answers, one per iteration. */
	static class ScriptedLoopAgent extends AgenticLoopReactiveAgentServiceImpl {
		final List<List<String>> answers;
		final List<Map<String, Object>> receivedParams = new ArrayList<>();

		ScriptedLoopAgent(List<List<String>> answers) {
			super(null, null, null, null, null, null, NO_RENDERER);
			this.answers = answers;
		}

		@Override
		protected Flux<String> callLLMReactive(IGConfigurableChatModel chatModel, GPromptTemplateConfig prompt,
				IChatRequestContext context, Map<String, Object> params) {
			receivedParams.add(params);
			int index = Math.min(receivedParams.size() - 1, answers.size() - 1);
			return Flux.fromIterable(answers.get(index));
		}
	}

	private static String run(ScriptedLoopAgent agent, int maxIterations) {
		AgentNetworkParticipant persona = mock(AgentNetworkParticipant.class);
		when(persona.getNetworkAgentName()).thenReturn("agenticLoopAgent");
		List<LoopIteration> history = new ArrayList<>();
		return String.join("", agent.iteration(1, maxIterations, 10_000, history, null, new GPromptTemplateConfig(), null,
				persona, mock(INotificationSink.class), new ToolCallsListener()).collectList().block());
	}

	@Test
	void aMarkerSplitAcrossChunksIsRemovedAndRecognised() {
		ControlMarkerStripper stripper = new ControlMarkerStripper();
		StringBuilder shown = new StringBuilder();
		int half = MORE.length() / 2;
		shown.append(stripper.accept("The answer so far."));
		shown.append(stripper.accept(" " + MORE.substring(0, half)));
		shown.append(stripper.accept(MORE.substring(half)));
		shown.append(stripper.complete());

		assertEquals("The answer so far. ", shown.toString());
		assertTrue(stripper.isContinueRequested());
		assertFalse(stripper.isFinishRequested());
	}

	@Test
	void textThatOnlyLooksLikeAMarkerIsKept() {
		ControlMarkerStripper stripper = new ControlMarkerStripper();
		String shown = stripper.accept("a < b and <AGENT-C") + stripper.accept("hat") + stripper.complete();

		assertEquals("a < b and <AGENT-Chat", shown);
		assertFalse(stripper.isContinueRequested());
	}

	@Test
	void theModelEndsTheLoopWhenItSaysItIsDone() {
		ScriptedLoopAgent agent = new ScriptedLoopAgent(List.of(List.of("Searching ", "first. ", MORE),
				List.of("The final ", "answer.", STOP), List.of("never reached")));

		String shown = run(agent, 5);

		assertEquals("Searching first. \r\n\r\nThe final answer.", shown);
		assertEquals(2, agent.receivedParams.size(), "the model ended the loop at the second iteration");
		assertEquals(2, agent.receivedParams.get(1).get(ReportWriterReactiveAgentServiceImpl.CURRENT_ITERATION_PROMPT_PARAM));
		assertTrue(String.valueOf(agent.receivedParams.get(1).get(ReportWriterReactiveAgentServiceImpl.AGENT_SESSION_STORY_PROMPT_PARAM))
				.contains("Searching first."), "the second iteration receives the first one's text");
	}

	@Test
	void anAnswerWithoutMarkerEndsTheLoop() {
		ScriptedLoopAgent agent = new ScriptedLoopAgent(List.of(List.of("Just the answer.")));

		assertEquals("Just the answer.", run(agent, 5));
		assertEquals(1, agent.receivedParams.size());
	}

	@Test
	void theLoopNeverExceedsItsIterations() {
		ScriptedLoopAgent agent = new ScriptedLoopAgent(List.of(List.of("more work", MORE)));

		String shown = run(agent, 3);

		assertEquals(3, agent.receivedParams.size());
		assertFalse(shown.contains("AGENT-CONTROL"), "no marker reaches the user");
		assertEquals(3, agent.receivedParams.get(2).get(AgenticLoopReactiveAgentServiceImpl.MAX_ITERATIONS_PARAM));
	}

	@Test
	void theLoopHistoryFitsHalfOfTheBudget() {
		ScriptedLoopAgent agent = new ScriptedLoopAgent(List.of(List.of("x")));
		StringBuilder longText = new StringBuilder();
		for (int i = 0; i < 20_000; i++) {
			longText.append("word").append(i % 50).append(' ');
		}
		List<LoopIteration> history = List.of(new LoopIteration(1, longText.toString(), List.of()),
				new LoopIteration(2, "short", List.of()));

		String story = agent.loopStory(history, 4000);

		assertTrue(ITokensCountable.stringsTokensSize(story) <= 2000 + 100, "story of "
				+ ITokensCountable.stringsTokensSize(story));
		assertTrue(story.contains("BEGIN_AGENT-LOOP-2") && story.contains("short"));
		assertEquals("No previous iteration: this is the first one.", agent.loopStory(List.of(), 4000));
	}

	@Test
	void everyIterationIsShapedForTheDeliverableTheUserAskedFor() {
		ScriptedLoopAgent agent = new ScriptedLoopAgent(List.of(List.of("Searching. " + MORE), List.of("4. " + STOP)));
		AgentsCollaborationSessionContext session = mock(AgentsCollaborationSessionContext.class);
		Map<String, Object> environment = new HashMap<>();
		environment.put(StandardAgentsNetworkEnvironmentEntries.USER_INTENT, DeliverableIntent.QA);
		when(session.getEnvironment()).thenReturn(environment);
		AgentNetworkParticipant persona = mock(AgentNetworkParticipant.class);
		when(persona.getNetworkAgentName()).thenReturn("agenticLoopAgent");

		Map<String, Object> deliverable = agent.deliverableTemplateParams(agent.sessionUserIntent(session));
		agent.iteration(1, 3, 10_000, new ArrayList<>(), null, new GPromptTemplateConfig(), null, persona,
				mock(INotificationSink.class), new ToolCallsListener(), deliverable).collectList().block();

		assertEquals(2, agent.receivedParams.size());
		for (Map<String, Object> params : agent.receivedParams) {
			assertEquals("QA: direct short answer",
					params.get(ReportWriterReactiveAgentServiceImpl.REQUIRED_AGENT_COMPLETENESS_TEMPLATE_PARAM));
			assertTrue(String.valueOf(
					params.get(ReportWriterReactiveAgentServiceImpl.DELIVERABLE_FORMATTING_RULES_TEMPLATE_PARAM))
					.startsWith("Answer the question first"), "the QA formatting rules");
		}
	}

	@Test
	void withoutAClassifiedIntentTheLoopAsksForASummary() {
		ScriptedLoopAgent agent = new ScriptedLoopAgent(List.of(List.of("x")));
		AgentsCollaborationSessionContext session = mock(AgentsCollaborationSessionContext.class);
		when(session.getEnvironment()).thenReturn(new HashMap<>());

		assertEquals(DeliverableIntent.SUMMARY, agent.sessionUserIntent(session));
	}
}
