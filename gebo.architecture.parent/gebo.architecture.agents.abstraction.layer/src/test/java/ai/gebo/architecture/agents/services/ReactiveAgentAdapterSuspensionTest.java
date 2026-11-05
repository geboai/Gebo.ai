package ai.gebo.architecture.agents.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentSuspendedWaitingCondition;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage.MessageSemantic;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.IGPartialOperation;
import ai.gebo.architecture.agents.model.ClarificationAnswer;
import ai.gebo.architecture.agents.model.ClarificationOption;
import ai.gebo.architecture.agents.model.ClarificationOptions;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCalling;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

/**
 * Pins how the adapter of a reactive agent suspends a run and resumes it, as the
 * tool-calling agent does: the wait sink it loads in the agent's tool calling,
 * filled by a waiting tool, makes it ask the network to suspend the run and say
 * nothing to the network, what was streamed having reached the user; respawned
 * with the answer, it tells the agent the answer before the request.
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
class ReactiveAgentAdapterSuspensionTest {
	private static final GeboToolCalling RUN_TOOL_CALLING = GeboToolCalling.builder()
			.caller("test:ReactiveAgentAdapterSuspensionTest").build();
	private final IGReactiveAgentService<String, String> agent = mock(IGReactiveAgentService.class);
	private final IGNetworkOfAgentsSuspensionHandler handler = mock(IGNetworkOfAgentsSuspensionHandler.class);
	private final Sinks.Many<IGPartialOperation<String>> sink = Sinks.many().unicast().onBackpressureBuffer();
	private final AgentNetworkParticipant me = new AgentNetworkParticipant();
	private final GAgentConfig config = new GAgentConfig();
	private final GAgentsNetwork network = new GAgentsNetwork();
	private final AgentsCollaborationSessionContext session = new AgentsCollaborationSessionContext("session-1");

	private final class TestAdapter extends AbstractReactiveAgentServiceNetworkAdapter<String, String> {
		private TestAdapter() {
			super(agent, String.class, String.class, sink);
		}

		@Override
		protected String extractResponse(List<IGPartialOperation<String>> buffered) {
			return buffered.isEmpty() ? null : buffered.get(buffered.size() - 1).getData();
		}
	}

	private List<AgentsExchangeMessage<String>> run() throws Exception {
		me.setAgentConfigCode("the-agent");
		config.setCode("the-agent");
		network.setCode("the-network");
		return new TestAdapter().onMessage(IChatRequestContext.of("What is new?"), config,
				AgentsExchangeMessage.of(session, "the-router", "the-agent", "What is new?",
						MessageSemantic.EXECUTE_AND_SHARE_RESULT),
				0, network, me, state -> {
				}, session, new AgentPrivateSessionContext<String, String>(), null,
				mock(IGAgentsNetworkRuntimeDao.class), RUN_TOOL_CALLING, handler);
	}

	private static AgentSuspendedWaitingCondition<ClarificationAnswer, ClarificationOptions> clarification(
			ClarificationAnswer answer) {
		ClarificationOptions options = new ClarificationOptions();
		options.setQuestion("Which year?");
		options.getOptions().addAll(List.of(ClarificationOption.of("2023"), ClarificationOption.of("2024")));
		return new AgentSuspendedWaitingCondition<ClarificationAnswer, ClarificationOptions>(ClarificationAnswer.class,
				"Which year?", "it matters", answer, true, ClarificationOptions.class, options);
	}

	@Test
	void theWaitSinkIsLoadedInTheAgentsToolCallingBesideWhatTheRunCarries() throws Exception {
		when(handler.resolvedConditionsOf(any())).thenReturn(List.of());
		when(agent.execute(any(), any(), any(), any(), any(), any(), any(), any(), any(), any()))
				.thenReturn(Flux.just(IGPartialOperation.of("the answer", true)));

		List<AgentsExchangeMessage<String>> out = run();

		ArgumentCaptor<GeboToolCalling> callToolCalling = ArgumentCaptor.forClass(GeboToolCalling.class);
		verify(agent).execute(any(), any(), eq("What is new?"), any(), any(), any(), any(), any(), any(),
				callToolCalling.capture());
		assertNotNull(callToolCalling.getValue().getProvider().get(IGWaitExternalCondition.class));
		assertEquals("test:ReactiveAgentAdapterSuspensionTest", callToolCalling.getValue().getProvider().caller());
		assertEquals(1, out.size());
		assertEquals("the answer", out.get(0).getPayload());
		verify(handler, never()).enterSuspensionState(any(), any(), any(), any(), any());
	}

	@Test
	void aToolDeclaringAConditionMakesTheAdapterSuspendTheRunAndSayNothing() throws Exception {
		when(handler.resolvedConditionsOf(any())).thenReturn(List.of());
		when(agent.execute(any(), any(), any(), any(), any(), any(), any(), any(), any(), any()))
				.thenAnswer(invocation -> {
					// the agent's tool, in the agent's stream, declares what the run waits for
					GeboToolCalling callToolCalling = invocation.getArgument(9, GeboToolCalling.class);
					callToolCalling.getProvider().get(IGWaitExternalCondition.class).waitFor(clarification(null));
					return Flux.just(IGPartialOperation.of("The question was put to the user.", true));
				});

		List<AgentsExchangeMessage<String>> out = run();

		assertTrue(out.isEmpty(), "the adapter waits: nothing is said to the network");
		ArgumentCaptor<AgentSuspendedWaitingCondition> condition = ArgumentCaptor
				.forClass(AgentSuspendedWaitingCondition.class);
		verify(handler).enterSuspensionState(eq(network), eq(config), eq(me), any(), condition.capture());
		assertEquals("Which year?", condition.getValue().getWaitingConditionStatement());
	}

	@Test
	void respawnedWithTheAnswerTheAdapterTellsTheAgentBeforeTheRequest() throws Exception {
		when(handler.resolvedConditionsOf(me)).thenReturn(List.<AgentSuspendedWaitingCondition>of(clarification(ClarificationAnswer.option("2024"))));
		when(agent.execute(any(), any(), any(), any(), any(), any(), any(), any(), any(), any()))
				.thenReturn(Flux.just(IGPartialOperation.of("In 2024: nothing new.", true)));

		List<AgentsExchangeMessage<String>> out = run();

		ArgumentCaptor<IChatRequestContext> context = ArgumentCaptor.forClass(IChatRequestContext.class);
		verify(agent).execute(context.capture(), any(),
				eq("You asked the user: Which year?\nThe user chose: 2024\n\nWhat is new?"), any(), any(), any(), any(),
				any(), any(), any());
		assertEquals("You asked the user: Which year?\nThe user chose: 2024\n\nWhat is new?",
				context.getValue().getActualUserRequest(), "a model reading the request from the context is told too");
		assertEquals("In 2024: nothing new.", out.get(0).getPayload());
		verify(handler, never()).enterSuspensionState(any(), any(), any(), any(), any());
	}
}
