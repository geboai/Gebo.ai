package ai.gebo.architecture.agents.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentProducedSessionContribution;
import ai.gebo.architecture.agents.model.AgentSuspendedWaitingCondition;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage.MessageSemantic;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension.FrozenDeliveries;
import ai.gebo.architecture.agents.model.NetworkOfAgentsExecutionResult;
import ai.gebo.architecture.agents.model.NetworkOfAgentsExecutionResult.NStatus;
import ai.gebo.architecture.agents.model.RuntimeAgentInfos;
import ai.gebo.architecture.agents.repository.GNetworkOfAgentsSuspensionRepository;
import ai.gebo.architecture.multithreading.IGeboThreadManager;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCalling;

/**
 * The execution and the respawn of a network of mocked agents, through the
 * network base. A router fans the question out to an asker and a searcher, run
 * in parallel; the asker asks to suspend the run while the searcher answers,
 * so the searcher's answer is frozen beside the asker's, and the writer at the
 * output never runs. Respawned with the answer, the run carries on from the
 * cut: the router is not run again, the asker is re-executed on the message it
 * was given and finds its private turns and the router's contribution where it
 * left them, the writer is given everything in delivery order, and the network
 * answers. Respawned without the answer, the writer is given what was frozen
 * and the run suspends again, into a block linked to the first.
 */
class MockedAgentsNetworkRespawnTest {
	private static final GeboToolCalling TOOL_CALLING = GeboToolCalling.builder()
			.caller("test:MockedAgentsNetworkRespawnTest").build();
	private static final String ROUTER = "the-router";
	private static final String ASKER = "the-asker";
	private static final String SEARCHER = "the-searcher";
	private static final String WRITER = "the-writer";

	private final GNetworkOfAgentsSuspensionRepository repository = mock(GNetworkOfAgentsSuspensionRepository.class);
	private final IGeboThreadManager threadManager = mock(IGeboThreadManager.class);
	private final ExecutorService executor = Executors.newFixedThreadPool(4);
	private final List<GNetworkOfAgentsSuspension> saved = new ArrayList<GNetworkOfAgentsSuspension>();
	private final AtomicInteger routerRuns = new AtomicInteger();
	private final List<Object> answersSeenByTheAsker = Collections.synchronizedList(new ArrayList<Object>());
	private boolean askerAsksTwice = false;
	private boolean secondQuestionMandatory = false;
	private boolean askerAsksAgainWhenRespawned = false;
	private final List<List<String>> solvedKnownToTheAsker = Collections.synchronizedList(new ArrayList<List<String>>());
	private final List<String> sharedByTheAskerSeenByTheWriter = Collections.synchronizedList(new ArrayList<String>());
	private final List<Integer> askerPriorTurnsWhenRespawned = Collections.synchronizedList(new ArrayList<Integer>());
	private final List<Object> messagesSeenByTheWriter = Collections.synchronizedList(new ArrayList<Object>());
	private final List<Integer> contributionsSeenByTheWriter = Collections
			.synchronizedList(new ArrayList<Integer>());
	private GAgentsNetwork network;
	private TestNetworkService service;

	/** A network service of no behaviour of its own: the base does the work. */
	private final class TestNetworkService extends GAbstractAgentsNetworkService<String, String> {
		private TestNetworkService(IGAgentsNetworkRuntimeDao agentsDao) {
			super(null, null, threadManager, network, state -> {
			}, String.class, String.class, null, agentsDao, repository);
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
			return "A network service used to pin the execution and the respawn of mocked agents";
		}
	}

	private static AgentNetworkParticipant participant(String name, boolean input, boolean output,
			List<String> talksTo) {
		AgentNetworkParticipant participant = new AgentNetworkParticipant();
		// no contextual name: the network agent name is the config code
		participant.setAgentConfigCode(name);
		participant.setInputNode(input);
		participant.setOutputNode(output);
		participant.setCommunicationList(talksTo);
		return participant;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private RuntimeAgentInfos runtimeOf(AgentNetworkParticipant participant, IGNetworkAgentService agent) {
		when(agent.getId()).thenReturn(participant.getNetworkAgentName());
		when(agent.getInputType()).thenReturn((Class) String.class);
		GAgentConfig config = new GAgentConfig();
		config.setCode(participant.getAgentConfigCode());
		return new RuntimeAgentInfos(agent, config, participant, 0, 0);
	}

	/** A message of the exchange from one agent to another, in the given execution order. */
	private static AgentsExchangeMessage<String> to(AgentsCollaborationSessionContext session, String from, String to,
			String payload, int executionOrder) {
		AgentsExchangeMessage<String> message = AgentsExchangeMessage.of(session, from, to, payload,
				MessageSemantic.EXECUTE_AND_SHARE_RESULT);
		message.setExecutionOrder(executionOrder);
		return message;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static IGNetworkAgentService agent(java.util.function.Function<Object[], List<?>> behaviour)
			throws Exception {
		IGNetworkAgentService agent = mock(IGNetworkAgentService.class);
		when(agent.onMessage(any(), any(), any(), org.mockito.ArgumentMatchers.anyInt(), any(), any(), any(), any(),
				any(), any(), any(), any(), any())).thenAnswer(invocation -> behaviour.apply(invocation.getArguments()));
		return agent;
	}

	/**
	 * The router, the input node: it shares its plan with the session and sends
	 * the question to the asker and the searcher, to run in parallel.
	 */
	private IGNetworkAgentService router() throws Exception {
		return agent(args -> {
			routerRuns.incrementAndGet();
			AgentsExchangeMessage<String> msg = (AgentsExchangeMessage<String>) args[2];
			AgentsCollaborationSessionContext session = (AgentsCollaborationSessionContext) args[7];
			return List.of(
					AgentsExchangeMessage.of(session, ROUTER, ASKER, "plan: ask and search about " + msg.getPayload(),
							MessageSemantic.RESPONSE),
					to(session, ROUTER, ASKER, "ask about " + msg.getPayload(), 1),
					to(session, ROUTER, SEARCHER, "search about " + msg.getPayload(), 1));
		});
	}

	/**
	 * The asker: the first time it is given a message it asks to suspend the run,
	 * waiting for the year among the ones the model chose, and still tells the
	 * writer what it found. Respawned, it reads its answer from the handler, notes
	 * how many turns of its own it finds, and tells the writer the answer.
	 */
	private IGNetworkAgentService asker() throws Exception {
		return agent(args -> {
			GAgentConfig config = (GAgentConfig) args[1];
			AgentsExchangeMessage<String> msg = (AgentsExchangeMessage<String>) args[2];
			AgentNetworkParticipant me = (AgentNetworkParticipant) args[5];
			AgentsCollaborationSessionContext session = (AgentsCollaborationSessionContext) args[7];
			AgentPrivateSessionContext<?, ?> mine = (AgentPrivateSessionContext<?, ?>) args[8];
			IGNetworkOfAgentsSuspensionHandler handler = (IGNetworkOfAgentsSuspensionHandler) args[12];
			List<AgentSuspendedWaitingCondition> answers = handler.takeResolvedConditionsOf(me);
			if (!answers.isEmpty()) {
				for (AgentSuspendedWaitingCondition answer : answers) {
					answersSeenByTheAsker.add(answer.getInstanceValue());
				}
				askerPriorTurnsWhenRespawned.add(mine.getInteractions().size());
				List<String> solved = new ArrayList<String>();
				for (AgentSuspendedWaitingCondition<?, ?> known : mine.getSolvedConditions()) {
					solved.add(known.getWaitingConditionStatement() + "=" + known.getInstanceValue() + "/"
							+ known.getNotesForLater());
				}
				solvedKnownToTheAsker.add(solved);
				if (askerAsksAgainWhenRespawned) {
					askerAsksAgainWhenRespawned = false;
					try {
						handler.enterSuspensionState(network, config, me, mine,
								new AgentSuspendedWaitingCondition<String, Object>(String.class, "which country?",
										"the user's answer", null, true));
					} catch (AgentException e) {
						throw new IllegalStateException(e);
					}
					return List.of();
				}
				// after what was frozen: the writer is given the answer last
				return List.of(to(session, ASKER, WRITER, "asker: the year is " + answers.get(0).getInstanceValue(), 3));
			}
			try {
				handler.enterSuspensionState(network, config, me, mine, new AgentSuspendedWaitingCondition<String, List>(
						String.class, "which year?", "the user's answer", null, false, List.class,
						List.of("2023", "2024")));
				if (askerAsksTwice) {
					handler.enterSuspensionState(network, config, me, mine,
							new AgentSuspendedWaitingCondition<String, Object>(String.class, "which country?",
									"the user's answer", null, secondQuestionMandatory));
				}
			} catch (AgentException e) {
				throw new IllegalStateException(e);
			}
			return List.of(to(session, ASKER, WRITER, "asker: found this on " + msg.getPayload(), 1));
		});
	}

	/** The searcher: it answers the writer, after whatever the asker says. */
	private IGNetworkAgentService searcher() throws Exception {
		return agent(args -> {
			AgentsExchangeMessage<String> msg = (AgentsExchangeMessage<String>) args[2];
			AgentsCollaborationSessionContext session = (AgentsCollaborationSessionContext) args[7];
			return List.of(to(session, SEARCHER, WRITER, "searcher: results of " + msg.getPayload(), 2));
		});
	}

	/** The writer, the output node: it writes what it is given, noting what the session holds. */
	private IGNetworkAgentService writer() throws Exception {
		return agent(args -> {
			AgentsExchangeMessage<String> msg = (AgentsExchangeMessage<String>) args[2];
			AgentsCollaborationSessionContext session = (AgentsCollaborationSessionContext) args[7];
			messagesSeenByTheWriter.add(msg.getPayload());
			contributionsSeenByTheWriter.add(session.getSampledContributions().size());
			for (Object contribution : session.getSampledContributions()) {
				AgentProducedSessionContribution<?> shared = (AgentProducedSessionContribution<?>) contribution;
				if (ASKER.equals(shared.getAgentName())
						&& !sharedByTheAskerSeenByTheWriter.contains(String.valueOf(shared.getData()))) {
					sharedByTheAskerSeenByTheWriter.add(String.valueOf(shared.getData()));
				}
			}
			return List.of(AgentsExchangeMessage.of(session, WRITER, ROUTER, "wrote: " + msg.getPayload(),
					MessageSemantic.RESPONSE));
		});
	}

	@BeforeEach
	void setUp() throws Exception {
		when(threadManager.getExecutorService()).thenReturn(executor);
		when(repository.save(any())).thenAnswer(invocation -> {
			saved.add(invocation.getArgument(0, GNetworkOfAgentsSuspension.class));
			return invocation.getArgument(0);
		});
		AgentNetworkParticipant router = participant(ROUTER, true, false, List.of(ASKER, SEARCHER));
		AgentNetworkParticipant asker = participant(ASKER, false, false, List.of(WRITER));
		AgentNetworkParticipant searcher = participant(SEARCHER, false, false, List.of(WRITER));
		AgentNetworkParticipant writer = participant(WRITER, false, true, List.of(ROUTER));
		network = new GAgentsNetwork();
		network.setCode("the-network");
		network.setAgents(List.of(router, asker, searcher, writer));
		network.setMaxLoopIteration(10);
		service = newService();
	}

	/** A service created for the network, as the factory creates one: fresh runtimes over the agents. */
	private TestNetworkService newService() throws Exception {
		List<AgentNetworkParticipant> agents = network.getAgents();
		Map<String, RuntimeAgentInfos> runtimes = new HashMap<String, RuntimeAgentInfos>();
		runtimes.put(ROUTER, runtimeOf(agents.get(0), router()));
		runtimes.put(ASKER, runtimeOf(agents.get(1), asker()));
		runtimes.put(SEARCHER, runtimeOf(agents.get(2), searcher()));
		runtimes.put(WRITER, runtimeOf(agents.get(3), writer()));
		return new TestNetworkService(IGAgentsNetworkRuntimeDao.of(runtimes));
	}

	@AfterEach
	void tearDown() {
		executor.shutdownNow();
	}

	private NetworkOfAgentsExecutionResult<String> run() throws Exception {
		return service.executeNetwork(IChatRequestContext.of("What is new?"), "What is new?", Map.of("KEY", "value"),
				TOOL_CALLING);
	}

	private static List<String> payloadsOf(List<FrozenDeliveries> frozen, String producer) {
		List<String> payloads = new ArrayList<String>();
		for (FrozenDeliveries deliveries : frozen) {
			if (producer.equals(deliveries.getAgentConfigCode())) {
				deliveries.getMessages().forEach(m -> payloads.add(String.valueOf(m.getPayload())));
			}
		}
		return payloads;
	}

	@Test
	void theRunSuspendsWithTheAskersTurnAndWhatTheParallelSearcherProducedFrozen() throws Exception {
		NetworkOfAgentsExecutionResult<String> result = run();

		assertEquals(NStatus.SUSPENDED, result.getExecutedStatus());
		assertNull(result.getResult());
		assertEquals(1, result.getWaitingConditions().size());
		assertEquals(List.of("2023", "2024"), result.getWaitingConditions().get(0).getLlmChosenOptions());
		assertFalse(result.getWaitingConditions().get(0).isMandatory(), "the asker lets the user leave it unanswered");
		assertEquals(1, routerRuns.get());
		assertTrue(messagesSeenByTheWriter.isEmpty(), "the writer never ran: what was for it is frozen");
		assertEquals(1, saved.size());
		GNetworkOfAgentsSuspension block = saved.get(0);
		assertEquals(block.getCode(), result.getSuspensionId());
		assertEquals("What is new?", block.getNetworkInput());
		assertEquals(1, block.getSuspendedAgents().size());
		assertEquals(ASKER, block.getSuspendedAgents().get(0).getParticipant().getNetworkAgentName());
		assertEquals("ask about What is new?", block.getSuspendedAgents().get(0).getInputMessage().getPayload(),
				"the message the asker was executing when it asked");
		assertEquals(List.of("asker: found this on ask about What is new?"),
				payloadsOf(block.getFrozenDeliveries(), ASKER));
		assertEquals(List.of("searcher: results of search about What is new?"),
				payloadsOf(block.getFrozenDeliveries(), SEARCHER), "what the searcher produced in parallel is frozen too");
		assertEquals(4, block.getAgentsRuntime().size());
		assertEquals(1, block.getSharedState().getContributions().size(), "the router's plan");
		assertEquals("plan: ask and search about What is new?",
				block.getSharedState().getContributions().get(0).getData());
		assertEquals(Map.of("KEY", "value"), block.getSharedState().getEnvironment());
	}

	@Test
	void respawnedWithTheAnswerTheRunCarriesOnFromTheCutAndAnswers() throws Exception {
		run();
		GNetworkOfAgentsSuspension block = saved.get(0);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		AgentSuspendedWaitingCondition answered = block.getSuspendedAgents().get(0).getWaitingConditions().get(0)
				.toCondition();
		answered.setInstanceValue("2024");
		// the respawn runs on a service created for the block, as the factory creates one
		service = newService();

		@SuppressWarnings("unchecked")
		NetworkOfAgentsExecutionResult<String> respawned = service.respawnNetworkExecution(
				IChatRequestContext.of("resume"), block, List.<AgentSuspendedWaitingCondition<?, ?>>of(answered),
				null, TOOL_CALLING);

		assertEquals(NStatus.EXECUTED, respawned.getExecutedStatus());
		assertEquals(1, routerRuns.get(), "the router, before the cut, is not run again");
		assertEquals(List.of("2024"), answersSeenByTheAsker, "the asker was re-executed with its answer");
		assertEquals(List.of(1), askerPriorTurnsWhenRespawned, "the asker found the turn it had made before the cut");
		assertEquals(List.of("asker: found this on ask about What is new?",
				"searcher: results of search about What is new?", "asker: the year is 2024"), messagesSeenByTheWriter,
				"the writer was given what was frozen and the asker's new message, in delivery order");
		assertEquals("wrote: asker: the year is 2024", respawned.getResult());
		assertTrue(contributionsSeenByTheWriter.get(0) >= 1, "the writer saw the router's plan in the session");
		assertEquals(2, saved.size(), "the block was written once and marked once");
		assertNotNull(block.getRespawnedAt());
		assertNull(block.getRespawnedToSuspensionCode());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private AgentSuspendedWaitingCondition<?, ?> answerOf(GNetworkOfAgentsSuspension block, String value, String notes) {
		AgentSuspendedWaitingCondition answered = block.getSuspendedAgents().get(0).getWaitingConditions().get(0)
				.toCondition();
		answered.setInstanceValue(value);
		answered.setNotesForLater(notes);
		return answered;
	}

	@SuppressWarnings("unchecked")
	private NetworkOfAgentsExecutionResult<String> respawn(GNetworkOfAgentsSuspension block,
			AgentSuspendedWaitingCondition<?, ?> answered) throws Exception {
		service = newService();
		return service.respawnNetworkExecution(IChatRequestContext.of("resume"), block,
				List.<AgentSuspendedWaitingCondition<?, ?>>of(answered), null, TOOL_CALLING);
	}

	@Test
	void theAnswerStaysPrivateWithTheNotesAndIsSharedWithoutThemNoTurnSpent() throws Exception {
		run();

		respawn(saved.get(0), answerOf(saved.get(0), "2024", "the closed year only"));

		assertEquals(List.of(List.of("which year?=2024/the closed year only")), solvedKnownToTheAsker,
				"the asker keeps what it waited for in its private session, with its notes");
		assertEquals(List.of("Already settled, do not ask for it again. Agent the-asker waited for: which year?"
				+ "\nWhy it was needed: the user's answer\nIt came: 2024"), sharedByTheAskerSeenByTheWriter,
				"the other agents read it in the shared session, without the notes");
		assertEquals(List.of(1), askerPriorTurnsWhenRespawned, "what was solved is not a turn of the asker");
	}

	@Test
	void whatWasSolvedSurvivesAFurtherSuspension() throws Exception {
		askerAsksAgainWhenRespawned = true;
		run();
		GNetworkOfAgentsSuspension first = saved.get(0);
		NetworkOfAgentsExecutionResult<String> again = respawn(first, answerOf(first, "2024", "a note"));
		assertEquals(NStatus.SUSPENDED, again.getExecutedStatus(), "the respawned asker asked something else");
		GNetworkOfAgentsSuspension second = saved.stream().filter(x -> x != first).findFirst().orElseThrow();

		NetworkOfAgentsExecutionResult<String> respawned = respawn(second, answerOf(second, "Italy", null));

		assertEquals(NStatus.EXECUTED, respawned.getExecutedStatus());
		assertEquals(List.of("which year?=2024/a note", "which country?=Italy/null"),
				solvedKnownToTheAsker.get(solvedKnownToTheAsker.size() - 1),
				"the block keeps what was solved before it, and the notes with it");
		assertEquals(2, sharedByTheAskerSeenByTheWriter.size(),
				"each answer is shared once: " + sharedByTheAskerSeenByTheWriter);
		assertTrue(sharedByTheAskerSeenByTheWriter.get(0).contains("which year?"));
		assertTrue(sharedByTheAskerSeenByTheWriter.get(1).contains("which country?"));
	}

	@Test
	void respawnedWithoutTheAnswerTheRunDeliversWhatWasFrozenAndSuspendsAgain() throws Exception {
		run();
		GNetworkOfAgentsSuspension block = saved.get(0);
		service = newService();

		NetworkOfAgentsExecutionResult<String> respawned = service.respawnNetworkExecution(
				IChatRequestContext.of("resume"), block, List.of(), null, TOOL_CALLING);

		assertEquals(NStatus.SUSPENDED, respawned.getExecutedStatus());
		assertTrue(answersSeenByTheAsker.isEmpty(), "the asker, still waiting, was not re-executed");
		assertEquals(List.of("asker: found this on ask about What is new?",
				"searcher: results of search about What is new?"), messagesSeenByTheWriter);
		assertEquals(3, saved.size(), "the first block, the new block, the first block marked");
		GNetworkOfAgentsSuspension again = saved.get(1);
		assertEquals(again.getCode(), respawned.getSuspensionId());
		assertEquals(block.getCode(), again.getRespawnedFromSuspensionCode());
		assertEquals(again.getCode(), block.getRespawnedToSuspensionCode());
		assertEquals("ask about What is new?", again.getSuspendedAgents().get(0).getInputMessage().getPayload());
		assertEquals(List.of("2023", "2024"), again.getSuspendedAgents().get(0).getWaitingConditions().get(0).getLlmChosenOptions());
		assertEquals(3, again.getSharedState().getContributions().size(),
				"the router's plan and the writer's two answers are in the shared state");
		assertTrue(again.getFrozenDeliveries().isEmpty(), "nothing was left undelivered this time");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private AgentSuspendedWaitingCondition answered(GNetworkOfAgentsSuspension block, int condition, String value) {
		AgentSuspendedWaitingCondition answered = block.conditions().get(condition).toCondition();
		answered.setInstanceValue(value);
		return answered;
	}

	@Test
	@SuppressWarnings("unchecked")
	void twoQuestionsOfOneTurnAreOneEntryAndTheAgentIsReExecutedOnceWithBothAnswers() throws Exception {
		askerAsksTwice = true;
		run();
		GNetworkOfAgentsSuspension block = saved.get(0);
		assertEquals(1, block.getSuspendedAgents().size(), "one entry per agent");
		assertEquals(2, block.getSuspendedAgents().get(0).getWaitingConditions().size());
		assertNotNull(block.getSuspendedAgents().get(0).getInputMessage());
		service = newService();

		NetworkOfAgentsExecutionResult<String> respawned = service.respawnNetworkExecution(
				IChatRequestContext.of("resume"), block,
				List.<AgentSuspendedWaitingCondition<?, ?>>of(answered(block, 0, "2024"), answered(block, 1, "Italy")),
				null, TOOL_CALLING);

		assertEquals(NStatus.EXECUTED, respawned.getExecutedStatus());
		assertEquals(List.of("2024", "Italy"), answersSeenByTheAsker, "told both answers");
		assertEquals(List.of(1), askerPriorTurnsWhenRespawned, "re-executed once");
		assertNotNull(block.getRespawnedAt());
	}

	@Test
	@SuppressWarnings("unchecked")
	void anOptionalQuestionLeftWithoutReactionCountsAsDeclinedWhenTheAgentGoesOn() throws Exception {
		askerAsksTwice = true;
		run();
		GNetworkOfAgentsSuspension block = saved.get(0);
		service = newService();

		NetworkOfAgentsExecutionResult<String> respawned = service.respawnNetworkExecution(
				IChatRequestContext.of("resume"), block,
				List.<AgentSuspendedWaitingCondition<?, ?>>of(answered(block, 0, "2024")), null, TOOL_CALLING);

		assertEquals(NStatus.EXECUTED, respawned.getExecutedStatus());
		assertEquals(java.util.Arrays.asList("2024", null), answersSeenByTheAsker, "the other one has no value");
	}

	@Test
	@SuppressWarnings("unchecked")
	void anAgentWithAMandatoryQuestionLeftKeepsWaitingForAllOfThem() throws Exception {
		askerAsksTwice = true;
		secondQuestionMandatory = true;
		run();
		GNetworkOfAgentsSuspension block = saved.get(0);
		service = newService();

		NetworkOfAgentsExecutionResult<String> respawned = service.respawnNetworkExecution(
				IChatRequestContext.of("resume"), block,
				List.<AgentSuspendedWaitingCondition<?, ?>>of(answered(block, 0, "2024")), null, TOOL_CALLING);

		assertEquals(NStatus.SUSPENDED, respawned.getExecutedStatus());
		assertTrue(answersSeenByTheAsker.isEmpty(), "not re-executed");
		GNetworkOfAgentsSuspension again = saved.get(1);
		assertEquals(1, again.getSuspendedAgents().size());
		assertEquals(2, again.getSuspendedAgents().get(0).getWaitingConditions().size());
		assertNotNull(again.getSuspendedAgents().get(0).getInputMessage(), "its turn goes on with it");
	}

	@Test
	void anAgentWaitingWithNoConditionSuspendsAgainWithItsTurn() throws Exception {
		run();
		GNetworkOfAgentsSuspension block = saved.get(0);
		block.getSuspendedAgents().get(0).getWaitingConditions().clear();
		service = newService();

		NetworkOfAgentsExecutionResult<String> respawned = service.respawnNetworkExecution(
				IChatRequestContext.of("resume"), block, List.<AgentSuspendedWaitingCondition<?, ?>>of(), null,
				TOOL_CALLING);

		assertEquals(NStatus.SUSPENDED, respawned.getExecutedStatus());
		GNetworkOfAgentsSuspension again = saved.get(1);
		assertEquals(1, again.getSuspendedAgents().size());
		assertTrue(again.getSuspendedAgents().get(0).getWaitingConditions().isEmpty());
		assertNotNull(again.getSuspendedAgents().get(0).getInputMessage());
	}
}
