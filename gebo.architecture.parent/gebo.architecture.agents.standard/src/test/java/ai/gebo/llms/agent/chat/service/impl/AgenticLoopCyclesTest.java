package ai.gebo.llms.agent.chat.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.IGPartialOperation;
import ai.gebo.architecture.agents.services.INotificationSink;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.llms.abstraction.layer.services.ToolCallsListener;
import ai.gebo.llms.agent.chat.service.impl.AgenticLoopReactiveAgentServiceImpl.CycleRenderer;
import ai.gebo.llms.agent.chat.service.impl.AgenticLoopReactiveAgentServiceImpl.LoopIteration;
import ai.gebo.llms.agent.chat.service.impl.AgenticLoopReactiveAgentServiceTest.ScriptedLoopAgent;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GAnswerCycleEvent;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GResponseDocumentRef;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import reactor.core.publisher.Flux;

/**
 * Pins the cycles of the single agent with tools: up to five, each writing the whole
 * answer again in place of the previous one, which the user saw and the next cycle
 * receives, the documents of the cycles' answers added up without repeating one.
 */
class AgenticLoopCyclesTest {
	private static final String STOP = ReportWriterReactiveAgentServiceImpl.AGENT_CONTROL_FINISHED;
	private static final String MORE = ReportWriterReactiveAgentServiceImpl.AGENT_CONTROL_MORE_TOOLS;
	private static final String END = AgenticLoopReactiveAgentServiceImpl.CYCLE_END_MARK;

	private static AgentNetworkParticipant persona() {
		AgentNetworkParticipant persona = mock(AgentNetworkParticipant.class);
		when(persona.getNetworkAgentName()).thenReturn("agenticLoopAgent");
		return persona;
	}

	private static GResponseDocumentRef document(String code) {
		GResponseDocumentRef ref = new GResponseDocumentRef();
		ref.setDocumentCode(code);
		ref.setName(code);
		return ref;
	}

	/** The documents each cycle's answer rests on, by the answer's text. */
	private static CycleRenderer renderer(ScriptedLoopAgent agent, int maxCycles,
			Map<String, List<GResponseDocumentRef>> documentsByAnswer) {
		return agent.new CycleRenderer(maxCycles, persona(), mock(INotificationSink.class), new ToolCallsListener(),
				answer -> answer.setDocumentsRef(
						new ArrayList<>(documentsByAnswer.getOrDefault(answer.getQueryResponse(), List.of()))),
				answer -> {
				});
	}

	private static List<GeboChatMessageEnvelope> messages(CycleRenderer renderer, String... chunks) {
		return renderer.render(Flux.fromArray(chunks)).map(IGPartialOperation::getData).collectList().block();
	}

	private static List<String> codes(GeboChatResponse answer) {
		return answer.getDocumentsRef().stream().map(GResponseDocumentRef::getDocumentCode).toList();
	}

	@Test
	void aCycleAnswerIsGivenWholeThenTheNextCycleWritesItAgainInItsPlace() {
		ScriptedLoopAgent agent = new ScriptedLoopAgent(List.of(List.of("x")));

		List<GeboChatMessageEnvelope> out = messages(renderer(agent, 5, Map.of()), "Cycle one", " text" + END + "Cycle ",
				"two");

		assertEquals(List.of("String", "String", "GeboChatResponse", "GAnswerCycleEvent", "String", "String",
				"GeboChatResponse"), out.stream().map(GeboChatMessageEnvelope::getContentObjectType).toList());
		assertEquals("Cycle one", out.get(0).getContent());
		assertEquals(" text", out.get(1).getContent());
		assertEquals("Cycle one text", ((GeboChatResponse) out.get(2).getContent()).getQueryResponse(),
				"the first cycle's answer, whole");
		GAnswerCycleEvent next = (GAnswerCycleEvent) out.get(3).getContent();
		assertEquals(2, next.getCycle());
		assertEquals(5, next.getMaxCycles());
		assertEquals("Cycle two", ((GeboChatResponse) out.get(6).getContent()).getQueryResponse(),
				"the last cycle's answer alone is the response");
		assertTrue(out.stream().noneMatch(GeboChatMessageEnvelope::isLastMessage), "the network ends the stream");
	}

	@Test
	void theDocumentsOfTheCyclesAnswersAddUpWithoutRepeatingOne() {
		ScriptedLoopAgent agent = new ScriptedLoopAgent(List.of(List.of("x")));
		Map<String, List<GResponseDocumentRef>> documents = Map.of("one", List.of(document("A")), "two",
				List.of(document("B"), document("A")), "three", List.of());

		List<GeboChatMessageEnvelope> out = messages(renderer(agent, 5, documents), "one" + END + "two" + END + "three");

		List<GeboChatResponse> answers = out.stream().filter(m -> m.getContent() instanceof GeboChatResponse)
				.map(m -> (GeboChatResponse) m.getContent()).toList();
		assertEquals(3, answers.size());
		assertEquals(List.of("A"), codes(answers.get(0)));
		assertEquals(List.of("A", "B"), codes(answers.get(1)), "the second cycle adds B, A is not repeated");
		assertEquals(List.of("A", "B"), codes(answers.get(2)), "a cycle resting on no new document keeps them");
	}

	@Test
	void theAnswerWrittenAgainAfterACutOneIsTheCyclesAnswer() {
		ScriptedLoopAgent agent = new ScriptedLoopAgent(List.of(List.of("x")));

		List<GeboChatMessageEnvelope> out = messages(renderer(agent, 5, Map.of()),
				"Cut begins" + AgenticLoopReactiveAgentServiceImpl.ANSWER_RESTART + "Whole answer" + END + "Final");

		assertEquals("Cut begins\n\n" + AgenticLoopReactiveAgentServiceImpl.ANSWER_RESTART_RULE + "\n\nWhole answer",
				out.get(0).getContent(), "the cut answer is parted from the one written again by a rule");
		assertEquals("Whole answer", ((GeboChatResponse) out.get(1).getContent()).getQueryResponse());
		assertEquals("Final", ((GeboChatResponse) out.get(4).getContent()).getQueryResponse(),
				"a cut in a cycle leaves the next one alone");
	}

	@Test
	void theLoopRunsUpToFiveCyclesEachOneReceivingTheAnswerTheUserSees() {
		assertEquals(5, AgenticLoopReactiveAgentServiceImpl.DEFAULT_MAX_ITERATIONS);
		ScriptedLoopAgent agent = new ScriptedLoopAgent(List.of(List.of("First try. " + MORE),
				List.of("Second try. " + MORE), List.of("Third try. " + MORE), List.of("Fourth try. " + MORE),
				List.of("Fifth try. " + MORE), List.of("never reached")));
		assertEquals(5, agent.maxIterations(null, persona()));
		final GAgentsNetwork network = new GAgentsNetwork();
		network.setMaxLoopIteration(3);
		assertEquals(5, agent.maxIterations(network, persona()));
		final AgentNetworkParticipant controller = persona();
		when(controller.isController()).thenReturn(true);
		assertEquals(3, agent.maxIterations(network, controller));

		String shown = String.join("", agent.iteration(1, agent.maxIterations(null, persona()), 10_000, new ArrayList<>(),
				null, new GPromptTemplateConfig(), null, persona(), mock(INotificationSink.class),
				new ToolCallsListener()).collectList().block());

		assertEquals(List.of("First try. ", "Second try. ", "Third try. ", "Fourth try. ", "Fifth try. "),
				List.of(shown.split(END)), "five cycles, each one's answer ended by the mark");
		assertEquals(5, agent.receivedParams.size(), "the loop never exceeds five cycles");
		String thirdStory = String.valueOf(
				agent.receivedParams.get(2).get(ReportWriterReactiveAgentServiceImpl.AGENT_SESSION_STORY_PROMPT_PARAM));
		assertTrue(thirdStory.contains(AgenticLoopReactiveAgentServiceImpl.LAST_ANSWER + "Second try. "),
				"the third cycle receives the answer the user sees, whole: " + thirdStory);
		assertTrue(thirdStory.contains(AgenticLoopReactiveAgentServiceImpl.SUPERSEDED_ANSWER)
				&& !thirdStory.contains("First try."), "the first answer is superseded: " + thirdStory);
		assertFalse(shown.contains("AGENT-CONTROL"), "no marker reaches the user");
	}

	@Test
	void theCyclesOfTheLoopAreTheChatsMessages() {
		ScriptedLoopAgent agent = new ScriptedLoopAgent(List.of(List.of("A ", MORE), List.of("B " + STOP)));
		CycleRenderer renderer = renderer(agent, 5, Map.of());

		List<GeboChatMessageEnvelope> out = renderer
				.render(agent.iteration(1, 5, 10_000, new ArrayList<>(), null, new GPromptTemplateConfig(), null,
						persona(), mock(INotificationSink.class), new ToolCallsListener()))
				.map(IGPartialOperation::getData).collectList().block();

		assertEquals(List.of("String", "GeboChatResponse", "GAnswerCycleEvent", "String", "GeboChatResponse"),
				out.stream().map(GeboChatMessageEnvelope::getContentObjectType).toList());
		assertEquals("A ", ((GeboChatResponse) out.get(1).getContent()).getQueryResponse());
		assertEquals("B ", ((GeboChatResponse) out.get(4).getContent()).getQueryResponse());
	}

	@Test
	void anIterationThatSearchedNothingAndWentOnTellsTheNextOneToChangeAngleOrFinish() {
		ScriptedLoopAgent agent = new ScriptedLoopAgent(List.of(List.of("x")));
		ToolCallsListener listener = new ToolCallsListener();
		listener.addCall("searchKnowledgeBase", "search", "{\"query\":\"archons\"}", "found");
		List<LoopIteration> searched = List.of(new LoopIteration(1, "Searched answer.", listener.getCalls()));
		List<LoopIteration> notSearched = List.of(new LoopIteration(1, "Searched answer.", listener.getCalls()),
				new LoopIteration(2, "The same answer again.", List.of()));

		assertFalse(agent.loopStory(searched, 40_000, null).contains(AgenticLoopReactiveAgentServiceImpl.NO_SEARCH_NOTE),
				"an iteration that searched is not told to change angle");
		String story = agent.loopStory(notSearched, 40_000, null);
		assertTrue(story.contains(AgenticLoopReactiveAgentServiceImpl.LAST_ANSWER + "The same answer again."), story);
		assertTrue(story.contains(AgenticLoopReactiveAgentServiceImpl.NO_SEARCH_NOTE), story);
		assertTrue(story.indexOf("searchKnowledgeBase") < story.indexOf(AgenticLoopReactiveAgentServiceImpl.NO_SEARCH_NOTE),
				"the searches already made come before the note, as the angles to avoid: " + story);
		assertTrue(story.contains("archons"), "a search is told with its input, the angle it covered: " + story);
	}

	@Test
	void theStoryGivesTheLastShownAnswerWholeAndADraftDiscardedAfterIt() {
		ScriptedLoopAgent agent = new ScriptedLoopAgent(List.of(List.of("x")));
		List<LoopIteration> history = List.of(new LoopIteration(1, "Shown first.", List.of()),
				new LoopIteration(2, "Shown second.", List.of()),
				new LoopIteration(3, "A draft on a thin coverage.", List.of(), "coverage thin: complete X", true));

		String story = agent.loopStory(history, 40_000, null);

		assertTrue(story.contains(AgenticLoopReactiveAgentServiceImpl.LAST_ANSWER + "Shown second."), story);
		assertTrue(story.contains(AgenticLoopReactiveAgentServiceImpl.SUPERSEDED_ANSWER) && !story.contains("Shown first."),
				story);
		assertTrue(story.contains(AgenticLoopReactiveAgentServiceImpl.DISCARDED_DRAFT + "A draft on a thin coverage."),
				"the draft the user never saw, to build on: " + story);
	}
}
