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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ai.gebo.architecture.agents.model.AgentCapabilities;
import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentSuspendedWaitingCondition;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage.MessageSemantic;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension;
import ai.gebo.architecture.agents.model.IGPartialOperation;
import ai.gebo.architecture.agents.model.NetworkOfAgentsExecutionResult;
import ai.gebo.architecture.agents.model.NetworkOfAgentsExecutionResult.NStatus;
import ai.gebo.architecture.agents.model.RuntimeAgentInfos;
import ai.gebo.architecture.agents.repository.GNetworkOfAgentsSuspensionRepository;
import ai.gebo.architecture.agents.services.GAbstractAgentsNetworkService;
import ai.gebo.architecture.agents.services.IGAgentCommands;
import ai.gebo.architecture.agents.services.IGAgentsNetworkRuntimeDao;
import ai.gebo.architecture.agents.services.IGNetworkAgentService;
import ai.gebo.architecture.agents.services.IGNetworkOfAgentsSuspensionHandler;
import ai.gebo.architecture.agents.services.IGReactiveAgentService;
import ai.gebo.architecture.multithreading.IGeboThreadManager;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCalling;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

/**
 * A network run by the network base, with the loop controller's own adapter in it:
 * an input adapter gives the request to the controller, which is the output node
 * and hands work to a specialist. The controller's model is scripted; the network,
 * the delivery of its commands, the sessions, the suspension and the respawn are
 * the real ones.
 * <p>
 * Pinned: the controller hands work over more than once and then answers, told each
 * time what the specialist said so far; and when the specialist asks the user, the
 * run is suspended with the controller's next turn frozen, and the respawn, on a
 * service and an adapter made anew, ends with the controller's answer, the controller
 * told what the specialist said and given the request the user made, not their answer
 * to the question.
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
class LoopControllerNetworkRunTest {
	private static final GeboToolCalling TOOL_CALLING = GeboToolCalling.builder()
			.caller("test:LoopControllerNetworkRunTest").build();
	private static final String INPUT = "the-input";
	private static final String CONTROLLER = "loopController";
	private static final String SPECIALIST = "presentations";
	private static final String REQUEST = "A deck on the Q3 figures, please.";

	private final GNetworkOfAgentsSuspensionRepository repository = mock(GNetworkOfAgentsSuspensionRepository.class);
	private final IGeboThreadManager threadManager = mock(IGeboThreadManager.class);
	private final ExecutorService executor = Executors.newFixedThreadPool(4);
	private final List<GNetworkOfAgentsSuspension> saved = new ArrayList<>();
	private GAgentsNetwork network;
	private Map<String, RuntimeAgentInfos> runtimes;
	/** What the controller's model does at each of its executions: the commands it gives, then what it writes. */
	private Function<Object[], String> controllerModel;
	private int controllerExecutions = 0;
	/** The request the controller was given at each execution, and the one its model reads in the context. */
	private final List<String> requestsGiven = new ArrayList<>();
	private final List<String> requestsInTheContext = new ArrayList<>();
	private final List<List<Integer>> privateTurnsSeen = new ArrayList<>();
	private final List<String> briefsTheSpecialistGot = new ArrayList<>();
	private boolean specialistAsksTheUser = false;

	/** A network service of no behaviour of its own: the base does the work. */
	private final class TestNetworkService extends GAbstractAgentsNetworkService<String, GeboChatMessageEnvelope> {
		private TestNetworkService(IGAgentsNetworkRuntimeDao agentsDao) {
			super(null, null, threadManager, network, state -> {
			}, String.class, GeboChatMessageEnvelope.class, null, agentsDao, repository);
		}

		@Override
		protected <OutputType> OutputType compose(OutputType actualOutput, OutputType incremental) {
			return incremental != null ? incremental : actualOutput;
		}

		@Override
		public void dispose() {
		}

		@Override
		public String getId() {
			return "TEST_NETWORK_SERVICE";
		}

		@Override
		public String getDescription() {
			return "A network service used to pin the runs of a loop controller and its specialist";
		}
	}

	private static AgentNetworkParticipant participant(String name, boolean input, boolean output,
			List<String> talksTo) {
		AgentNetworkParticipant participant = new AgentNetworkParticipant();
		participant.setAgentConfigCode(name);
		participant.setInputNode(input);
		participant.setOutputNode(output);
		participant.setCommunicationList(talksTo);
		return participant;
	}

	private static RuntimeAgentInfos runtimeOf(AgentNetworkParticipant participant, IGNetworkAgentService agent) {
		GAgentConfig config = new GAgentConfig();
		config.setCode(participant.getAgentConfigCode());
		config.setDescription("The agent " + participant.getAgentConfigCode());
		return new RuntimeAgentInfos(agent, config, participant, 0, 0);
	}

	/** What the network did to its agents, in the order it did: initialized for the run, or given a message. */
	private final List<String> whatTheNetworkDid = new java.util.ArrayList<>();

	private IGNetworkAgentService agent(Function<Object[], List<?>> behaviour) throws Exception {
		IGNetworkAgentService agent = mock(IGNetworkAgentService.class);
		when(agent.onMessage(any(), any(), any(), org.mockito.ArgumentMatchers.anyInt(), any(), any(), any(), any(),
				any(), any(), any(), any(), any())).thenAnswer(invocation -> {
					whatTheNetworkDid.add("message to " + ((GAgentConfig) invocation.getArgument(1)).getCode());
					return behaviour.apply(invocation.getArguments());
				});
		org.mockito.Mockito.doAnswer(invocation -> {
			whatTheNetworkDid.add("initialized " + ((GAgentConfig) invocation.getArgument(1)).getCode());
			return null;
		}).when(agent).initializeForRun(any(), any());
		when(agent.getInputType()).thenReturn((Class) String.class);
		when(agent.getAgentCapabilities(any())).thenReturn(new AgentCapabilities());
		return agent;
	}

	private static GeboChatMessageEnvelope answer(String text) {
		GeboChatResponse response = new GeboChatResponse();
		response.setQueryResponse(text);
		return new GeboChatMessageEnvelope(response);
	}

	private static String textOf(NetworkOfAgentsExecutionResult<GeboChatMessageEnvelope> result) {
		return result.getResult() != null ? ((GeboChatResponse) result.getResult().getContent()).getQueryResponse()
				: null;
	}

	/** The controller's model hands the specialist the work: null when the command was taken. */
	private static String handOver(Object[] execution, String brief) {
		return ((GeboToolCalling) execution[9]).getProvider().get(IGAgentCommands.class).command(SPECIALIST, brief);
	}

	/** The input adapter: it gives the request to the controller. */
	private IGNetworkAgentService inputAdapter() throws Exception {
		return agent(args -> {
			AgentsExchangeMessage<String> msg = (AgentsExchangeMessage<String>) args[2];
			AgentsCollaborationSessionContext session = (AgentsCollaborationSessionContext) args[7];
			return List.of(AgentsExchangeMessage.of(session, INPUT, CONTROLLER, msg.getPayload(),
					MessageSemantic.EXECUTE_AND_SHARE_RESULT));
		});
	}

	/**
	 * The specialist: it does what it is asked and answers who asked. When it must ask
	 * the user first, it asks to suspend the run and says nothing, and does the work
	 * once respawned with the answer.
	 */
	private IGNetworkAgentService specialist() throws Exception {
		return agent(args -> {
			GAgentConfig config = (GAgentConfig) args[1];
			AgentsExchangeMessage<String> msg = (AgentsExchangeMessage<String>) args[2];
			AgentNetworkParticipant me = (AgentNetworkParticipant) args[5];
			AgentsCollaborationSessionContext session = (AgentsCollaborationSessionContext) args[7];
			AgentPrivateSessionContext mine = (AgentPrivateSessionContext) args[8];
			IGNetworkOfAgentsSuspensionHandler handler = (IGNetworkOfAgentsSuspensionHandler) args[12];
			briefsTheSpecialistGot.add(msg.getPayload());
			List<AgentSuspendedWaitingCondition> answers = handler.takeResolvedConditionsOf(me);
			if (specialistAsksTheUser && answers.isEmpty()) {
				try {
					handler.enterSuspensionState(network, config, me, mine,
							new AgentSuspendedWaitingCondition<String, Object>(String.class, "which template?",
									"the deck needs one", null, true));
				} catch (Exception e) {
					throw new IllegalStateException(e);
				}
				return List.of();
			}
			return List.of(AgentsExchangeMessage.of(session, SPECIALIST, msg.getFromAgent(),
					"done: " + msg.getPayload()
							+ (answers.isEmpty() ? "" : " In the template " + answers.get(0).getInstanceValue() + "."),
					MessageSemantic.RESPONSE));
		});
	}

	/** The controller: its own adapter, made anew for each service as its factory does, over a scripted loop. */
	private IGNetworkAgentService controller() throws Exception {
		IGReactiveAgentService<String, GeboChatMessageEnvelope> loop = mock(IGReactiveAgentService.class);
		when(loop.getId()).thenReturn(LoopControllerReactiveAgentServiceImpl.LOOP_CONTROLLER_NETWORK_AGENT_SERVICE);
		when(loop.execute(any(), any(), any(), any(), any(), any(), any(), any(), any(), any()))
				.thenAnswer(invocation -> {
					Object[] execution = invocation.getArguments();
					controllerExecutions++;
					requestsGiven.add((String) execution[2]);
					requestsInTheContext.add(((IChatRequestContext) execution[0]).getActualUserRequest());
					privateTurnsSeen.add(((AgentPrivateSessionContext) execution[7]).getContributionTurnNumbers());
					return Flux.just(IGPartialOperation.of(answer(controllerModel.apply(execution)), false));
				});
		// the adapter hands the initialization to the agent it adapts
		org.mockito.Mockito.doAnswer(invocation -> {
			whatTheNetworkDid.add("initialized " + ((GAgentConfig) invocation.getArgument(1)).getCode());
			return null;
		}).when(loop).initializeForRun(any(), any());
		return new LoopControllerNetworkAdapter(loop, Sinks.many().unicast().onBackpressureBuffer());
	}

	/** A service created for the network, as the factory creates one: fresh runtimes, a fresh controller adapter. */
	private TestNetworkService newService() throws Exception {
		List<AgentNetworkParticipant> agents = network.getAgents();
		runtimes = new HashMap<>();
		runtimes.put(INPUT, runtimeOf(agents.get(0), inputAdapter()));
		runtimes.put(CONTROLLER, runtimeOf(agents.get(1), controller()));
		runtimes.put(SPECIALIST, runtimeOf(agents.get(2), specialist()));
		return new TestNetworkService(IGAgentsNetworkRuntimeDao.of(runtimes));
	}

	private int dispatches(String agent) {
		return runtimes.get(agent).getInvocations().get();
	}

	@BeforeEach
	void setUp() {
		when(threadManager.getExecutorService()).thenReturn(executor);
		when(repository.save(any())).thenAnswer(invocation -> {
			saved.add(invocation.getArgument(0, GNetworkOfAgentsSuspension.class));
			return invocation.getArgument(0);
		});
		AgentNetworkParticipant controller = participant(CONTROLLER, false, true, List.of(SPECIALIST));
		controller.setController(true);
		controller.setCanCallOtherAgents(true);
		network = new GAgentsNetwork();
		network.setCode("the-network");
		network.setAgents(List.of(participant(INPUT, true, false, List.of(CONTROLLER)), controller,
				participant(SPECIALIST, false, false, List.of())));
		network.setMaxLoopIteration(5);
	}

	@AfterEach
	void tearDown() {
		executor.shutdownNow();
	}

	@Test
	void theControllerHandsWorkOverTwiceThenAnswers() throws Exception {
		controllerModel = execution -> switch (controllerExecutions) {
		case 1 -> {
			assertNull(handOver(execution, "A deck of three slides on Q3."), "the command is taken");
			yield "I ask for the deck.";
		}
		case 2 -> {
			assertNull(handOver(execution, "Add a fourth slide with the outlook."));
			yield "I ask for one more slide.";
		}
		default -> "The deck is ready.";
		};

		NetworkOfAgentsExecutionResult<GeboChatMessageEnvelope> result = newService()
				.executeNetwork(IChatRequestContext.of(REQUEST), REQUEST, Map.of(), TOOL_CALLING);

		assertEquals(NStatus.EXECUTED, result.getExecutedStatus());
		assertEquals("The deck is ready.", textOf(result), "the answer of the network is the controller's last one");
		assertEquals(List.of("A deck of three slides on Q3.", "Add a fourth slide with the outlook."),
				briefsTheSpecialistGot, "the network delivered each command to the specialist");
		assertEquals(3, dispatches(CONTROLLER));
		assertEquals(2, dispatches(SPECIALIST));
		assertEquals(1, dispatches(INPUT));
		assertEquals(REQUEST, requestsGiven.get(0), "nothing to tell at the first execution");
		assertEquals(
				"The agent presentations said: done: A deck of three slides on Q3.\n\n" + REQUEST,
				requestsGiven.get(1));
		assertEquals("The agent presentations said: done: A deck of three slides on Q3.\n\n"
				+ "The agent presentations said: done: Add a fourth slide with the outlook.\n\n" + REQUEST,
				requestsGiven.get(2), "executed again, the controller is told everything the specialist said so far");
		assertEquals(requestsGiven, requestsInTheContext, "the loop's model reads the request from the context");
		assertEquals(List.of(List.of(), List.of(1), List.of(1, 3)), privateTurnsSeen,
				"each turn it handed work over is a turn of its own in its private session");
		assertEquals(List.of("initialized " + INPUT, "initialized " + CONTROLLER, "initialized " + SPECIALIST,
				"message to " + INPUT), whatTheNetworkDid.subList(0, 4),
				"every agent is initialized for the run, one at a time, before the first message is delivered");
		assertEquals(3, whatTheNetworkDid.stream().filter(x -> x.startsWith("initialized ")).count(), "once each");
	}

	@Test
	void whenTheSpecialistAsksTheUserTheRunIsSuspendedAndTheRespawnEndsWithTheControllersAnswer() throws Exception {
		specialistAsksTheUser = true;
		controllerModel = execution -> switch (controllerExecutions) {
		case 1 -> {
			assertNull(handOver(execution, "A deck of three slides on Q3."), "the command is taken");
			yield "I ask for the deck.";
		}
		default -> "The deck is ready.";
		};

		NetworkOfAgentsExecutionResult<GeboChatMessageEnvelope> suspended = newService()
				.executeNetwork(IChatRequestContext.of(REQUEST), REQUEST, Map.of(), TOOL_CALLING);

		assertEquals(NStatus.SUSPENDED, suspended.getExecutedStatus());
		assertNull(suspended.getResult());
		assertEquals(1, controllerExecutions, "the controller waits for the specialist, which waits for the user");
		assertEquals(1, saved.size());
		GNetworkOfAgentsSuspension block = saved.get(0);
		assertEquals(List.of(SPECIALIST),
				block.getSuspendedAgents().stream().map(x -> x.getParticipant().getNetworkAgentName()).toList());
		assertEquals(List.of(CONTROLLER + ">" + CONTROLLER),
				block.getFrozenDeliveries().stream().flatMap(x -> x.getMessages().stream())
						.map(m -> m.getFromAgent() + ">" + m.getToAgent()).toList(),
				"the controller's own next turn is frozen with the block");

		// the user answers: another request, whose text is the answer, on a service made for the block
		AgentSuspendedWaitingCondition answered = block.getSuspendedAgents().get(0).getWaitingConditions().get(0)
				.toCondition();
		answered.setInstanceValue("corporate");
		NetworkOfAgentsExecutionResult<GeboChatMessageEnvelope> respawned = newService().respawnNetworkExecution(
				IChatRequestContext.of("corporate"), block, List.<AgentSuspendedWaitingCondition<?, ?>>of(answered),
				null, TOOL_CALLING);

		assertEquals(NStatus.EXECUTED, respawned.getExecutedStatus());
		assertEquals("The deck is ready.", textOf(respawned));
		assertEquals(2, controllerExecutions, "the controller is executed again once the specialist answered");
		assertEquals(2, dispatches(CONTROLLER), "the turn before the cut is counted, on the service made anew");
		assertEquals(List.of(1), privateTurnsSeen.get(1), "its private session is given back with the turn it made");
		String told = requestsGiven.get(1);
		assertTrue(told.contains("The agent presentations said: done: A deck of three slides on Q3."
				+ " In the template corporate."), told);
		assertTrue(told.endsWith("\n\n" + REQUEST), "what the user asked is what the controller works on: " + told);
		assertEquals(told, requestsInTheContext.get(1),
				"the context of the respawn asks the user's answer, 'corporate': the controller is given its request");
	}
}
