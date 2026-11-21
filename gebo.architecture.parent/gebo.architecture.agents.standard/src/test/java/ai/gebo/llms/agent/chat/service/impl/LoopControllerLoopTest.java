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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;

import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.IGPartialOperation;
import ai.gebo.architecture.agents.services.GAgentCommandsSink;
import ai.gebo.architecture.agents.services.IGAgentCommands;
import ai.gebo.architecture.agents.services.INotificationSink;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.service.IGDocumentContentRenderer;
import ai.gebo.architecture.ai.service.IGDocumentContentRendererProvider;
import ai.gebo.llms.abstraction.layer.model.GChatAnswerChunk;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.ToolCallsListener;
import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCalling;
import ai.gebo.llms.agent.chat.service.impl.AgenticLoopReactiveAgentServiceImpl.CycleRenderer;
import ai.gebo.llms.agent.chat.service.impl.AgenticLoopReactiveAgentServiceImpl.LoopIteration;
import ai.gebo.llms.agent.chat.service.impl.AgenticLoopReactiveAgentServiceImpl.LoopRun;
import ai.gebo.llms.agent.chat.service.impl.AgenticLoopReactiveAgentServiceImpl.SourceGate;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.DeliverableIntent;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GAnswerCycleEvent;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import reactor.core.publisher.Flux;

/**
 * Pins the loop of the agent that is also the controller of a network: an iteration
 * handing work to another agent stops the loop before its answer, the execution
 * that follows goes on from it with the story and the cycle it had, and the last
 * iteration hands no work over.
 */
class LoopControllerLoopTest {
	private static final String STOP = ReportWriterReactiveAgentServiceImpl.AGENT_CONTROL_FINISHED;
	private static final String MORE = ReportWriterReactiveAgentServiceImpl.AGENT_CONTROL_MORE_TOOLS;
	private static final String END = AgenticLoopReactiveAgentServiceImpl.CYCLE_END_MARK;
	private static final String ASK_PRESENTATIONS = "askAgent_presentations";

	private static final IGDocumentContentRendererProvider NO_RENDERER = new IGDocumentContentRendererProvider() {
		@Override
		public <T> IGDocumentContentRenderer<T> get(T doc) {
			return null;
		}
	};

	/** One scripted iteration: what the model writes, and what its tools do while it does. */
	private record Step(List<String> text, Consumer<GeboToolCalling> tools) {
		static Step writes(String... text) {
			return new Step(List.of(text), toolCalling -> {
			});
		}

		Step calling(Consumer<GeboToolCalling> tools) {
			return new Step(text, tools);
		}
	}

	/** A loop controller whose model follows a script, one step per iteration. */
	private static final class ScriptedController extends LoopControllerReactiveAgentServiceImpl {
		private final List<Step> script;
		private final List<Map<String, Object>> receivedParams = new ArrayList<>();

		ScriptedController(Step... script) {
			super(null, null, null, null, null, null, NO_RENDERER);
			this.script = List.of(script);
		}

		@Override
		protected Flux<GChatAnswerChunk> callLLMReactiveResponses(IGConfigurableChatModel chatModel,
				GPromptTemplateConfig prompt, IChatRequestContext context, Map<String, Object> params,
				GeboToolCalling toolCalling) {
			receivedParams.add(params);
			final Step step = script.get(Math.min(receivedParams.size() - 1, script.size() - 1));
			return Flux.defer(() -> {
				step.tools().accept(toolCalling);
				return Flux.fromIterable(step.text());
			}).map(text -> new GChatAnswerChunk(text, "", false, null, List.of(), null));
		}

		String storyOfCall(int call) {
			return String.valueOf(
					receivedParams.get(call - 1).get(ReportWriterReactiveAgentServiceImpl.AGENT_SESSION_STORY_PROMPT_PARAM));
		}
	}

	private final ToolCallsListener calls = new ToolCallsListener();
	private final List<LoopIteration> history = new ArrayList<>();

	/** The model's tool handing work to the presentations agent: the command, and the call as it is recorded. */
	private Consumer<GeboToolCalling> askingPresentations(String request, List<String> told) {
		return toolCalling -> {
			final String refused = toolCalling.getProvider().get(IGAgentCommands.class).command("presentations", request);
			told.add(refused);
			calls.addCall(ASK_PRESENTATIONS, "Hands a piece of work to the agent presentations.",
					"{\"request\":\"" + request + "\"}", refused != null ? refused : "handed over");
		};
	}

	/** The tool calling of one execution of the agent: its adapter loads a sink of commands for each. */
	private static GeboToolCalling anExecution(GAgentCommandsSink commands) {
		return GeboToolCalling.builder().caller("test:LoopControllerLoopTest")
				.provide(IGAgentCommands.class, commands)
				.provide(LoopControllerRun.Execution.class, new LoopControllerRun().execution(commands)).build();
	}

	private static AgentNetworkParticipant persona() {
		AgentNetworkParticipant persona = mock(AgentNetworkParticipant.class);
		when(persona.getNetworkAgentName()).thenReturn("loopController");
		return persona;
	}

	private String run(ScriptedController agent, int from, int maxIterations, GeboToolCalling toolCalling) {
		return String.join("", agent.iteration(from, maxIterations, 10_000, history, null, new GPromptTemplateConfig(),
				null, persona(), mock(INotificationSink.class), calls,
				agent.deliverableTemplateParams(DeliverableIntent.SUMMARY), SourceGate.NONE, null, toolCalling)
				.collectList().block());
	}

	@Test
	void anIterationHandingWorkOverStopsTheLoopAndTheExecutionAfterItGoesOnFromThere() {
		List<String> told = new ArrayList<>();
		ScriptedController agent = new ScriptedController(Step.writes("The figures of Q3 are found.", MORE),
				Step.writes("I ask the presentations agent for the deck.", MORE)
						.calling(askingPresentations("A deck of three slides on Q3.", told)),
				Step.writes("The deck q3.pptx is ready.", STOP));
		GAgentCommandsSink first = new GAgentCommandsSink(List.of("presentations"));

		String shown = run(agent, 1, 5, anExecution(first));

		assertEquals("The figures of Q3 are found." + END + "I ask the presentations agent for the deck." + END, shown,
				"what the stopped iteration wrote is the cycle's answer, written again when the loop goes on");
		assertEquals(2, agent.receivedParams.size(), "the model asked to go on, but the loop waits for the agent");
		assertEquals(1, told.size());
		assertNull(told.get(0), "the command was taken");
		assertEquals(List.of(new IGAgentCommands.Command("presentations", "A deck of three slides on Q3.")),
				first.commanded());
		assertEquals(2, history.size());
		assertFalse(history.get(0).stopped());
		assertTrue(history.get(1).stopped());
		assertEquals(LoopControllerReactiveAgentServiceImpl.HANDED_WORK_OVER, history.get(1).stoppedFor());
		assertEquals(List.of(ASK_PRESENTATIONS), history.get(1).calls().stream().map(x -> x.getName()).toList());

		// the network delivered the command and executes the agent again: a new sink, the same story
		GAgentCommandsSink second = new GAgentCommandsSink(List.of("presentations"));
		String thenShown = run(agent, history.get(history.size() - 1).number() + 1, 5, anExecution(second));

		assertEquals("The deck q3.pptx is ready.", thenShown);
		assertEquals(3, agent.receivedParams.size());
		assertEquals(3, agent.receivedParams.get(2).get(ReportWriterReactiveAgentServiceImpl.CURRENT_ITERATION_PROMPT_PARAM));
		String story = agent.storyOfCall(3);
		assertTrue(story.contains("TOOL-CALLED-1: " + ASK_PRESENTATIONS), story);
		assertTrue(story.contains(AgenticLoopReactiveAgentServiceImpl.STOPPED_HERE
				+ LoopControllerReactiveAgentServiceImpl.HANDED_WORK_OVER), story);
		assertTrue(story.contains(
				AgenticLoopReactiveAgentServiceImpl.LAST_ANSWER + "I ask the presentations agent for the deck."), story);
		assertFalse(second.isCommanding());
		assertEquals(3, history.size());
		assertFalse(history.get(2).stopped());
	}

	@Test
	void anIterationThatOnlyHandsWorkOverKeepsTheAnswerTheUserSees() {
		List<String> told = new ArrayList<>();
		ScriptedController agent = new ScriptedController(Step.writes("The answer so far.", MORE),
				Step.writes().calling(askingPresentations("A deck of three slides on Q3.", told)),
				Step.writes("The answer, with the deck q3.pptx.", STOP));

		String shown = run(agent, 1, 5, anExecution(new GAgentCommandsSink(List.of("presentations"))));

		assertEquals("The answer so far." + END, shown, "an iteration that wrote nothing ends no cycle");
		assertTrue(history.get(1).stopped());
		assertEquals("", history.get(1).text());

		run(agent, 3, 5, anExecution(new GAgentCommandsSink(List.of("presentations"))));

		String story = agent.storyOfCall(3);
		assertTrue(story.contains(AgenticLoopReactiveAgentServiceImpl.LAST_ANSWER + "The answer so far."),
				"the answer the user sees is still the one to write again: " + story);
		assertFalse(story.contains(AgenticLoopReactiveAgentServiceImpl.SUPERSEDED_ANSWER), story);
		assertTrue(story.contains(AgenticLoopReactiveAgentServiceImpl.STOPPED_HERE), story);
	}

	@Test
	void theLastIterationHandsNoWorkOver() {
		List<String> told = new ArrayList<>();
		ScriptedController agent = new ScriptedController(Step.writes("A draft.", MORE),
				Step.writes("The answer, without the deck.", STOP)
						.calling(askingPresentations("A deck of three slides on Q3.", told)));
		GAgentCommandsSink commands = new GAgentCommandsSink(List.of("presentations"));

		String shown = run(agent, 1, 2, anExecution(commands));

		assertEquals(List.of(LoopControllerReactiveAgentServiceImpl.LAST_STEP), told,
				"no execution follows the last iteration to read the answer: the model is told to write its own");
		assertFalse(commands.isCommanding());
		assertEquals("A draft." + END + "The answer, without the deck.", shown);
		assertFalse(history.get(1).stopped());
	}

	@Test
	void anAgentExecutedWithNoSinkLoadedIsThePlainLoop() {
		ScriptedController agent = new ScriptedController(Step.writes("A draft.", MORE), Step.writes("The answer.", STOP));

		String shown = run(agent, 1, 5, GeboToolCalling.builder().caller("test:LoopControllerLoopTest").build());

		assertEquals("A draft." + END + "The answer.", shown);
		assertTrue(history.stream().noneMatch(LoopIteration::stopped));
	}

	private static List<GeboChatMessageEnvelope> messages(CycleRenderer renderer, String... chunks) {
		return renderer.render(Flux.fromArray(chunks)).map(IGPartialOperation::getData).collectList().block();
	}

	private static CycleRenderer renderer(AgenticLoopReactiveAgentServiceImpl agent, LoopRun run) {
		return agent.new CycleRenderer(5, persona(), mock(INotificationSink.class), new ToolCallsListener(), answer -> {
		}, answer -> {
		}, text -> "", run);
	}

	@Test
	void theStreamOfAStoppedLoopEndsWithNoAnswerAndTheNextOneGoesOnFromItsCycle() {
		ScriptedController agent = new ScriptedController(Step.writes("x"));
		LoopRun run = new LoopRun();

		run.history.add(LoopIteration.stopped(1, "I ask the presentations agent.", List.of(), "handed over"));
		List<GeboChatMessageEnvelope> first = messages(renderer(agent, run), "I ask the presentations agent." + END);

		assertEquals(List.of("String", "GeboChatResponse", "GAnswerCycleEvent"),
				first.stream().map(GeboChatMessageEnvelope::getContentObjectType).toList(),
				"the cycle's answer is given whole, and no answer of the agent follows it");
		assertEquals(2, ((GAnswerCycleEvent) first.get(2).getContent()).getCycle());
		assertEquals(1, run.iterations());
		assertTrue(run.stopped());

		run.history.add(new LoopIteration(2, "The deck is ready.", List.of()));
		List<GeboChatMessageEnvelope> second = messages(renderer(agent, run), "The deck is ready.");

		assertEquals(List.of("String", "GeboChatResponse"),
				second.stream().map(GeboChatMessageEnvelope::getContentObjectType).toList());
		GeboChatResponse answer = (GeboChatResponse) second.get(1).getContent();
		assertEquals("The deck is ready.", answer.getQueryResponse());
		assertEquals(2, answer.getPipelineParams().get(AgenticLoopReactiveAgentServiceImpl.AGENTIC_LOOP_CYCLES_PARAM),
				"the answer is at the cycle after the one the loop stopped at");
		assertFalse(run.stopped());
	}
}
