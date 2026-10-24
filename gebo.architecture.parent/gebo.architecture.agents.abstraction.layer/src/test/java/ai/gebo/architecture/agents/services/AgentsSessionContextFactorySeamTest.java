/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.agents.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentProducedSessionContribution;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.AgentsSessionCreationContext;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.RuntimeAgentInfos;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.security.services.ReactiveIdentityUtil;

/**
 * Pins the seam that lets a caller living longer than a single run own the
 * session that run collaborates in: that the session the agents are given is the
 * one that caller built, that the caller is told when the run is over and with
 * what, and — above all — that a run with nobody owning its session behaves
 * exactly as it always did.
 */
class AgentsSessionContextFactorySeamTest {

	private static final String INPUT = "the request";

	/** What a factory was asked and told, so a test can look at it afterwards. */
	private static final class RecordingSessionFactory implements IAgentsCollaborationSessionContextFactory {
		private final AgentsCollaborationSessionContext toBuild;
		private final RuntimeException failWith;
		private final List<AgentsSessionCreationContext> asked = new ArrayList<>();
		private final List<AgentsCollaborationSessionContext> closed = new ArrayList<>();
		private final List<Object> outputs = new ArrayList<>();
		private final List<Throwable> failures = new ArrayList<>();

		private RecordingSessionFactory(AgentsCollaborationSessionContext toBuild, RuntimeException failWith) {
			this.toBuild = toBuild;
			this.failWith = failWith;
		}

		@Override
		public AgentsCollaborationSessionContext createSessionContext(AgentsSessionCreationContext creation) {
			asked.add(creation);
			if (failWith != null) {
				throw failWith;
			}
			return toBuild;
		}

		@Override
		public void sessionCompleted(AgentsCollaborationSessionContext session, AgentsSessionCreationContext creation,
				Object output, Throwable failure) {
			closed.add(session);
			outputs.add(output);
			failures.add(failure);
		}
	}

	/** A network service of no behaviour: it exists to run the base's executeNetwork. */
	private static final class TestNetworkService extends GAbstractAgentsNetworkService<String, String> {
		private TestNetworkService(IGAgentsNetworkRuntimeDao agentsDao, GAgentsNetwork network,
				INotificationSink notificationSink, IAgentsCollaborationSessionContextFactory sessionFactory) {
			super(null, null, null, network, notificationSink, String.class, String.class, null, agentsDao,
					sessionFactory);
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
			return "A network service used to pin the session seam";
		}
	}

	private static GAgentsNetwork network() {
		AgentNetworkParticipant participant = new AgentNetworkParticipant();
		participant.setAgentConfigCode("agent");
		participant.setInputNode(true);
		participant.setOutputNode(true);
		GAgentsNetwork network = new GAgentsNetwork();
		network.setCode("a-network");
		network.setScenarioDescription("pinning the session seam");
		network.setAgents(List.of(participant));
		network.setMaxLoopIteration(2);
		return network;
	}

	/**
	 * The dao of a network whose only agent takes text and answers nothing, noting
	 * the session it was given.
	 */
	private static IGAgentsNetworkRuntimeDao daoOf(List<AgentsCollaborationSessionContext> seenByTheAgent)
			throws AgentException {
		IGNetworkAgentService agent = mock(IGNetworkAgentService.class);
		when(agent.getId()).thenReturn("an-agent");
		when(agent.getInputType()).thenReturn((Class) String.class);
		try {
			when(agent.onMessage(any(), any(), any(), org.mockito.ArgumentMatchers.anyInt(), any(), any(), any(), any(),
					any(), any(), any())).thenAnswer(invocation -> {
						seenByTheAgent.add(invocation.getArgument(7, AgentsCollaborationSessionContext.class));
						return List.of();
					});
		} catch (Exception impossible) {
			throw new IllegalStateException(impossible);
		}
		RuntimeAgentInfos runtime = mock(RuntimeAgentInfos.class);
		when(runtime.getService()).thenReturn(agent);
		when(runtime.getNetworkParticipantConfig()).thenReturn(network().getAgents().get(0));
		when(runtime.getInvocations()).thenReturn(new AtomicInteger());
		when(runtime.getAgentContext()).thenReturn(new AgentPrivateSessionContext());
		IGAgentsNetworkRuntimeDao dao = mock(IGAgentsNetworkRuntimeDao.class);
		when(dao.findAgentByCode(any())).thenReturn(runtime);
		return dao;
	}

	/** A dao of a network whose input node has no runtime at all: the run fails. */
	private static IGAgentsNetworkRuntimeDao daoWithNoRuntime() throws AgentException {
		IGAgentsNetworkRuntimeDao dao = mock(IGAgentsNetworkRuntimeDao.class);
		when(dao.findAgentByCode(any())).thenReturn(null);
		return dao;
	}

	private static INotificationSink sink() {
		return state -> {
		};
	}

	@Test
	void aRunWithNobodyOwningItsSessionOpensOneOfItsOwn() throws Exception {
		List<AgentsCollaborationSessionContext> seen = new ArrayList<>();
		GAgentsNetwork network = network();
		TestNetworkService service = new TestNetworkService(daoOf(seen), network, sink(), null);

		service.executeNetwork(IChatRequestContext.of(INPUT), INPUT, Map.of("KEY", "value"));
		service.executeNetwork(IChatRequestContext.of(INPUT), INPUT, Map.of("KEY", "value"));

		assertEquals(2, seen.size(), "the agent ran once per run");
		assertNotNull(seen.get(0).getId());
		assertNotEquals(seen.get(0).getId(), seen.get(1).getId(),
				"each run of a service owning its sessions collaborates in a session of its own");
		assertEquals("value", seen.get(0).getEnvironment().get("KEY"),
				"the environment still reaches the session, as it always did");
	}

	@Test
	void theSessionTheAgentsCollaborateInIsTheOneTheOwnerBuilt() throws Exception {
		List<AgentProducedSessionContribution> past = List
				.of(new AgentProducedSessionContribution(0, "writer", "what was said before"));
		AgentsCollaborationSessionContext owned = new AgentsCollaborationSessionContext("owned-session", past, 1);
		RecordingSessionFactory factory = new RecordingSessionFactory(owned, null);
		List<AgentsCollaborationSessionContext> seen = new ArrayList<>();
		TestNetworkService service = new TestNetworkService(daoOf(seen), network(), sink(), factory);

		service.executeNetwork(IChatRequestContext.of(INPUT), INPUT, Map.of());

		assertSame(owned, seen.get(0), "the agents were given the very session the owner built");
		assertEquals(1, seen.get(0).getSampledContributions().size(), "with the past that was replayed into it");
		assertEquals("a-network", factory.asked.get(0).getNetwork().getCode(),
				"and the owner was told what the run is about");
		assertEquals("TEST_NETWORK_SERVICE", factory.asked.get(0).getNetworkServiceId());
		assertEquals(String.class, factory.asked.get(0).getInputType());
	}

	@Test
	void theOwnerIsToldWhenTheRunIsOver() throws Exception {
		AgentsCollaborationSessionContext owned = new AgentsCollaborationSessionContext("owned-session");
		RecordingSessionFactory factory = new RecordingSessionFactory(owned, null);
		TestNetworkService service = new TestNetworkService(daoOf(new ArrayList<>()), network(), sink(), factory);

		service.executeNetwork(IChatRequestContext.of(INPUT), INPUT, Map.of());

		assertEquals(1, factory.closed.size(), "told exactly once");
		assertSame(owned, factory.closed.get(0));
		assertNull(factory.failures.get(0), "and told that nothing went wrong");
	}

	@Test
	void theOwnerIsToldWhatMadeTheRunFail() throws Exception {
		AgentsCollaborationSessionContext owned = new AgentsCollaborationSessionContext("owned-session");
		RecordingSessionFactory factory = new RecordingSessionFactory(owned, null);
		TestNetworkService service = new TestNetworkService(daoWithNoRuntime(), network(), sink(), factory);

		assertThrows(AgentException.class,
				() -> service.executeNetwork(IChatRequestContext.of(INPUT), INPUT, Map.of()));

		assertEquals(1, factory.closed.size(), "a failed run closes its session too");
		assertSame(owned, factory.closed.get(0));
		assertNull(factory.outputs.get(0), "with nothing produced");
		assertNotNull(factory.failures.get(0), "and with what made it fail");
		assertTrue(factory.failures.get(0) instanceof AgentException);
	}

	@Test
	void anOwnerThatBuildsNoSessionLeavesTheRunItsOwn() throws Exception {
		RecordingSessionFactory factory = new RecordingSessionFactory(null, null);
		List<AgentsCollaborationSessionContext> seen = new ArrayList<>();
		TestNetworkService service = new TestNetworkService(daoOf(seen), network(), sink(), factory);

		service.executeNetwork(IChatRequestContext.of(INPUT), INPUT, Map.of());

		assertEquals(1, seen.size(), "the run went ahead");
		assertNotNull(seen.get(0).getId(), "in a session of its own");
	}

	@Test
	void anOwnerThatFailsToBuildASessionDoesNotStopTheRun() throws Exception {
		RecordingSessionFactory factory = new RecordingSessionFactory(null,
				new IllegalStateException("the session store is down"));
		List<AgentsCollaborationSessionContext> seen = new ArrayList<>();
		TestNetworkService service = new TestNetworkService(daoOf(seen), network(), sink(), factory);

		service.executeNetwork(IChatRequestContext.of(INPUT), INPUT, Map.of());

		assertEquals(1, seen.size(), "a run is never lost because its session could not be built");
		assertNotNull(seen.get(0).getId());
	}

	@Test
	void theOwnerOfTheSessionsCanBeGivenAfterTheServiceWasBuilt() throws Exception {
		AgentsCollaborationSessionContext owned = new AgentsCollaborationSessionContext("owned-later");
		RecordingSessionFactory factory = new RecordingSessionFactory(owned, null);
		List<AgentsCollaborationSessionContext> seen = new ArrayList<>();
		TestNetworkService service = new TestNetworkService(daoOf(seen), network(), sink(), null);

		service.setSessionContextFactory(factory);
		service.executeNetwork(IChatRequestContext.of(INPUT), INPUT, Map.of());

		assertSame(owned, seen.get(0), "which is what the factory overload of the service factories relies on");
	}

	@Test
	void aServiceFactoryHandsTheSessionOwnerToTheServiceItBuilds() throws Exception {
		AgentsCollaborationSessionContext owned = new AgentsCollaborationSessionContext("owned-by-factory");
		RecordingSessionFactory sessionFactory = new RecordingSessionFactory(owned, null);
		List<AgentsCollaborationSessionContext> seen = new ArrayList<>();
		final TestNetworkService built = new TestNetworkService(daoOf(seen), network(), sink(), null);
		// A service factory of the plainest kind: it knows nothing of sessions, and
		// inherits the overload that passes one on.
		IGAgentsNetworkServiceFactory<String, String, TestNetworkService> serviceFactory = new IGAgentsNetworkServiceFactory<String, String, TestNetworkService>() {
			@Override
			public String getId() {
				return "TEST_FACTORY";
			}

			@Override
			public String getDescription() {
				return "builds the test network service";
			}

			@Override
			public boolean canHandle(Class<IGAgentsNetworkService> agentNetworkService) {
				return true;
			}

			@Override
			public Class<String> getOutputType() {
				return String.class;
			}

			@Override
			public Class<String> getInputType() {
				return String.class;
			}

			@Override
			public TestNetworkService create(GAgentsNetwork network, INotificationSink notificationSink,
					Class<String> inputType, Class<String> outputType, ReactiveIdentityUtil runAs) {
				return built;
			}
		};

		TestNetworkService service = serviceFactory.create(network(), sink(), String.class, String.class, null,
				sessionFactory);
		service.executeNetwork(IChatRequestContext.of(INPUT), INPUT, Map.of());

		assertSame(built, service, "the service is the one the factory builds, untouched otherwise");
		assertSame(owned, seen.get(0), "and its runs collaborate in the sessions that were asked for");
	}
}
