package ai.gebo.architecture.agents.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentSuspendedWaitingCondition;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage.MessageSemantic;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension;
import ai.gebo.architecture.agents.model.NetworkOfAgentsExecutionResult;
import ai.gebo.architecture.agents.model.NetworkOfAgentsExecutionResult.NStatus;
import ai.gebo.architecture.agents.model.RuntimeAgentInfos;
import ai.gebo.architecture.agents.repository.GNetworkOfAgentsSuspensionRepository;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCalling;

/**
 * Pins the cut and the respawn of a run through the network base: an agent
 * asking to suspend the run, in its own control code, ends the run as a block
 * holding its turn and what the run had not delivered; respawning that block
 * re-executes the agent with its answer, delivers what was frozen and ends with
 * the network's output, or suspends again when the agent still waits.
 */
class NetworkSuspensionAndRespawnTest {
	private static final GeboToolCalling TOOL_CALLING = GeboToolCalling.builder()
			.caller("test:NetworkSuspensionAndRespawnTest").build();
	private static final String ASKER = "the-asker";
	private static final String WRITER = "the-writer";

	private final GNetworkOfAgentsSuspensionRepository repository = mock(GNetworkOfAgentsSuspensionRepository.class);
	private final List<GNetworkOfAgentsSuspension> saved = new ArrayList<GNetworkOfAgentsSuspension>();
	private final List<Object> answersSeenByTheAsker = new ArrayList<Object>();
	private final List<Object> messagesSeenByTheWriter = new ArrayList<Object>();
	private GAgentsNetwork network;
	private TestNetworkService service;

	/** A network service of no behaviour of its own: the base does the work. */
	private final class TestNetworkService extends GAbstractAgentsNetworkService<String, String> {
		private TestNetworkService(IGAgentsNetworkRuntimeDao agentsDao) {
			super(null, null, null, network, state -> {
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
			return "A network service used to pin the suspension and the respawn";
		}
	}

	private static AgentNetworkParticipant participant(String name, boolean input, boolean output, String talksTo) {
		AgentNetworkParticipant participant = new AgentNetworkParticipant();
		// no contextual name: the network agent name is the config code
		participant.setAgentConfigCode(name);
		participant.setInputNode(input);
		participant.setOutputNode(output);
		participant.setCommunicationList(talksTo != null ? List.of(talksTo) : List.of());
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

	/**
	 * The asker, the input node: the first time it is given a message it asks the
	 * handler to suspend the run, waiting for the year, and still tells the writer
	 * what it found. Respawned, it reads its answer from the handler and says
	 * nothing more.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private IGNetworkAgentService asker() throws Exception {
		IGNetworkAgentService asker = mock(IGNetworkAgentService.class);
		when(asker.onMessage(any(), any(), any(), org.mockito.ArgumentMatchers.anyInt(), any(), any(), any(), any(),
				any(), any(), any(), any(), any())).thenAnswer(invocation -> {
					GAgentConfig config = invocation.getArgument(1, GAgentConfig.class);
					AgentsExchangeMessage<String> msg = invocation.getArgument(2, AgentsExchangeMessage.class);
					AgentNetworkParticipant me = invocation.getArgument(5, AgentNetworkParticipant.class);
					AgentsCollaborationSessionContext session = invocation.getArgument(7,
							AgentsCollaborationSessionContext.class);
					AgentPrivateSessionContext mine = invocation.getArgument(8, AgentPrivateSessionContext.class);
					IGNetworkOfAgentsSuspensionHandler handler = invocation.getArgument(12,
							IGNetworkOfAgentsSuspensionHandler.class);
					Optional<AgentSuspendedWaitingCondition> answer = handler.resolvedConditionOf(me);
					if (answer.isPresent()) {
						answersSeenByTheAsker.add(answer.get().getInstanceValue());
						return List.of();
					}
					handler.enterSuspensionState(network, config, me, mine, new AgentSuspendedWaitingCondition<String, Object>(
							String.class, "which year?", "the user's answer", null, true));
					return List.of(AgentsExchangeMessage.of(session, ASKER, WRITER, "found this about " + msg.getPayload(),
							MessageSemantic.EXECUTE_AND_SHARE_RESULT));
				});
		return asker;
	}

	/** The writer, the output node: it answers what it is given. */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private IGNetworkAgentService writer() throws Exception {
		IGNetworkAgentService writer = mock(IGNetworkAgentService.class);
		when(writer.onMessage(any(), any(), any(), org.mockito.ArgumentMatchers.anyInt(), any(), any(), any(), any(),
				any(), any(), any(), any(), any())).thenAnswer(invocation -> {
					AgentsExchangeMessage<String> msg = invocation.getArgument(2, AgentsExchangeMessage.class);
					AgentsCollaborationSessionContext session = invocation.getArgument(7,
							AgentsCollaborationSessionContext.class);
					messagesSeenByTheWriter.add(msg.getPayload());
					return List.of(AgentsExchangeMessage.of(session, WRITER, ASKER, "wrote: " + msg.getPayload(),
							MessageSemantic.RESPONSE));
				});
		return writer;
	}

	@BeforeEach
	void setUp() throws Exception {
		when(repository.save(any())).thenAnswer(invocation -> {
			saved.add(invocation.getArgument(0, GNetworkOfAgentsSuspension.class));
			return invocation.getArgument(0);
		});
		AgentNetworkParticipant asker = participant(ASKER, true, false, WRITER);
		AgentNetworkParticipant writer = participant(WRITER, false, true, ASKER);
		network = new GAgentsNetwork();
		network.setCode("the-network");
		network.setAgents(List.of(asker, writer));
		network.setMaxLoopIteration(10);
		service = newService();
	}

	/** A service created for the network, as the factory creates one: fresh runtimes over the agents. */
	private TestNetworkService newService() throws Exception {
		Map<String, RuntimeAgentInfos> runtimes = new HashMap<String, RuntimeAgentInfos>();
		runtimes.put(ASKER, runtimeOf(network.getAgents().get(0), asker()));
		runtimes.put(WRITER, runtimeOf(network.getAgents().get(1), writer()));
		return new TestNetworkService(IGAgentsNetworkRuntimeDao.of(runtimes));
	}

	private NetworkOfAgentsExecutionResult<String> run() throws Exception {
		return service.executeNetwork(IChatRequestContext.of("What is new?"), "What is new?", Map.of("KEY", "value"),
				TOOL_CALLING);
	}

	@Test
	void anAgentAskingToSuspendEndsTheRunAsABlockHoldingItsTurnAndWhatWasNotDelivered() throws Exception {
		NetworkOfAgentsExecutionResult<String> result = run();

		assertEquals(NStatus.SUSPENDED, result.getExecutedStatus());
		assertNull(result.getResult());
		assertEquals(1, result.getWaitingConditions().size());
		assertEquals("which year?", result.getWaitingConditions().get(0).getWaitingConditionStatement());
		assertEquals(1, saved.size(), "the cut is written once, when the run is declared finished");
		GNetworkOfAgentsSuspension block = saved.get(0);
		assertEquals(block.getCode(), result.getSuspensionId(), "the result names the block");
		assertEquals("the-network", block.getNetworkCode());
		assertEquals("What is new?", block.getNetworkInput());
		assertEquals(1, block.getSuspendedAgents().size());
		assertEquals(ASKER, block.getSuspendedAgents().get(0).getParticipant().getNetworkAgentName());
		assertEquals("What is new?", block.getSuspendedAgents().get(0).getInputMessage().getPayload(),
				"the message the asker was executing when it asked");
		assertEquals(0, block.getSuspendedAgents().get(0).getContributionNr());
		assertEquals(1, block.getFrozenDeliveries().size());
		assertEquals(WRITER, block.getFrozenDeliveries().get(0).getMessages().get(0).getToAgent());
		assertEquals("found this about What is new?",
				block.getFrozenDeliveries().get(0).getMessages().get(0).getPayload());
		assertEquals(2, block.getAgentsRuntime().size(), "the runtime of every agent of the network");
		assertEquals(Map.of("KEY", "value"), block.getSharedState().getEnvironment());
		assertTrue(messagesSeenByTheWriter.isEmpty(), "the writer was never given what was frozen");
	}

	@Test
	void respawningTheBlockWithTheAnswerReExecutesTheAgentDeliversWhatWasFrozenAndEndsTheRun() throws Exception {
		run();
		GNetworkOfAgentsSuspension block = saved.get(0);
		// the answer is given on the condition as read back from the block, as whoever
		// loads the block and resolves it does
		@SuppressWarnings({ "unchecked", "rawtypes" })
		AgentSuspendedWaitingCondition answered = block.getSuspendedAgents().get(0).getWaitingCondition()
				.toCondition();
		answered.setInstanceValue("2024");
		// the respawn runs on a service created for the block, as the factory creates one
		service = newService();

		@SuppressWarnings("unchecked")
		NetworkOfAgentsExecutionResult<String> respawned = service.respawnNetworkExecution(
				IChatRequestContext.of("resume"), block, List.<AgentSuspendedWaitingCondition<?, ?>>of(answered),
				null, TOOL_CALLING);

		assertEquals(NStatus.EXECUTED, respawned.getExecutedStatus());
		assertEquals("wrote: found this about What is new?", respawned.getResult());
		assertEquals(List.of("2024"), answersSeenByTheAsker, "the asker was re-executed and read its answer");
		assertEquals(List.of("found this about What is new?"), messagesSeenByTheWriter,
				"the frozen message reached the writer");
		assertEquals(1, saved.size() - 1, "the consumed block is marked, no new block is written");
		assertNotNull(block.getRespawnedAt());
		assertNull(block.getRespawnedToSuspensionCode());
	}

	@Test
	void respawningTheBlockWithoutTheAnswerDeliversWhatWasFrozenAndSuspendsAgainIntoANewBlock() throws Exception {
		run();
		GNetworkOfAgentsSuspension block = saved.get(0);
		service = newService();

		NetworkOfAgentsExecutionResult<String> respawned = service.respawnNetworkExecution(
				IChatRequestContext.of("resume"), block, List.of(), null, TOOL_CALLING);

		assertEquals(NStatus.SUSPENDED, respawned.getExecutedStatus());
		assertTrue(answersSeenByTheAsker.isEmpty(), "the asker, still waiting, was not re-executed");
		assertEquals(List.of("found this about What is new?"), messagesSeenByTheWriter,
				"the frozen message reached the writer anyway");
		assertEquals(1, respawned.getWaitingConditions().size());
		assertEquals("which year?", respawned.getWaitingConditions().get(0).getWaitingConditionStatement());
		assertEquals(3, saved.size(), "the first block, the new block, the first block marked");
		GNetworkOfAgentsSuspension again = saved.get(1);
		assertNotEquals(block.getCode(), again.getCode());
		assertEquals(again.getCode(), respawned.getSuspensionId());
		assertEquals(block.getCode(), again.getRespawnedFromSuspensionCode());
		assertEquals(block.getCode(), saved.get(2).getCode());
		assertEquals(again.getCode(), block.getRespawnedToSuspensionCode());
		assertEquals(1, again.getSuspendedAgents().size());
		assertEquals("What is new?", again.getSuspendedAgents().get(0).getInputMessage().getPayload(),
				"the turn of the asker is carried over, for the next respawn");
		assertEquals(1, again.getSharedState().getContributions().size(), "the writer's answer is in the shared state");
	}

	@Test
	void aServiceWhoseAgentsAlreadyRanIsRefused() throws Exception {
		run();
		GNetworkOfAgentsSuspension block = saved.get(0);

		AgentException refusal = assertThrows(AgentException.class, () -> service.respawnNetworkExecution(
				IChatRequestContext.of("resume"), block, List.of(), null, TOOL_CALLING));

		assertTrue(refusal.getMessage().contains("created for the block"), refusal.getMessage());
		assertTrue(messagesSeenByTheWriter.isEmpty(), "nothing was delivered");
	}

	@Test
	void aBlockOfAnotherNetworkIsRefused() throws Exception {
		run();
		GNetworkOfAgentsSuspension block = saved.get(0);
		block.setNetworkCode("another-network");
		service = newService();

		assertThrows(AgentException.class, () -> service.respawnNetworkExecution(IChatRequestContext.of("resume"),
				block, List.of(), null, TOOL_CALLING));
	}
}
