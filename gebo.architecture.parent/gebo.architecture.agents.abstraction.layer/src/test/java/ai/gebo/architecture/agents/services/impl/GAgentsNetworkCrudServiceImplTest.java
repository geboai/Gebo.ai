package ai.gebo.architecture.agents.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.services.IAgentConfigDao;
import ai.gebo.architecture.agents.services.IAgentsNetworkDao;
import ai.gebo.architecture.agents.services.IGAgentServiceRuntimeDao;
import ai.gebo.architecture.agents.services.IGNetworkAgentService;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;

/**
 * Pins the insert of a new network: its code is generated from its description by
 * the persistence layer, so it is not required and one given in advance is refused,
 * while the update keeps requiring the code of the network to update.
 */
class GAgentsNetworkCrudServiceImplTest {
	private final Map<String, GAgentsNetwork> stored = new HashMap<>();
	private IAgentsNetworkDao networkDao;
	private GAgentsNetworkCrudServiceImpl service;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@BeforeEach
	void setUp() throws Exception {
		IGNetworkAgentService textAgent = mock(IGNetworkAgentService.class);
		when(textAgent.getId()).thenReturn("textAgent");
		when(textAgent.getInputType()).thenReturn(String.class);
		when(textAgent.getOutputType()).thenReturn(String.class);
		IGAgentServiceRuntimeDao serviceDao = mock(IGAgentServiceRuntimeDao.class);
		when(serviceDao.findByCode("textAgent")).thenReturn(textAgent);
		IAgentConfigDao agentDao = mock(IAgentConfigDao.class);
		when(agentDao.findByCode(any())).thenAnswer(x -> {
			GAgentConfig agent = new GAgentConfig();
			agent.setCode(x.getArgument(0));
			agent.setAgentServiceId("textAgent");
			return agent;
		});
		networkDao = mock(IAgentsNetworkDao.class);
		when(networkDao.findByCode(any())).thenAnswer(x -> stored.get((String) x.getArgument(0)));
		// like the persistence layer: a code only for a network that has none
		when(networkDao.insert(any())).thenAnswer(x -> {
			GAgentsNetwork network = x.getArgument(0);
			if (network.getCode() != null) {
				throw new IllegalStateException("inserting a network with code " + network.getCode());
			}
			network.setCode(network.getDescription().toLowerCase().replace(' ', '-'));
			stored.put(network.getCode(), network);
			return network;
		});
		IGRuntimeBinder runtimeBinder = mock(IGRuntimeBinder.class);
		when(runtimeBinder.getImplementationOf(IAgentsNetworkDao.class)).thenReturn(networkDao);
		when(runtimeBinder.getImplementationOf(IAgentConfigDao.class)).thenReturn(agentDao);
		when(runtimeBinder.getImplementationOf(IGAgentServiceRuntimeDao.class)).thenReturn(serviceDao);
		service = new GAgentsNetworkCrudServiceImpl(runtimeBinder);
	}

	/** A valid two agents network: an input node messaging an output node. */
	private static GAgentsNetwork network(String code, String description) {
		AgentNetworkParticipant input = new AgentNetworkParticipant();
		input.setAgentConfigCode("reader");
		input.setInputNode(true);
		input.setCommunicationList(List.of("writer"));
		AgentNetworkParticipant output = new AgentNetworkParticipant();
		output.setAgentConfigCode("writer");
		output.setOutputNode(true);
		GAgentsNetwork network = new GAgentsNetwork();
		network.setCode(code);
		network.setDescription(description);
		network.setScenarioDescription("Reads and writes");
		network.setAgentsNetworkServiceFactoryId("factory");
		network.setAgents(List.of(input, output));
		return network;
	}

	private static boolean hasError(OperationStatus<?> status, String summary) {
		return status.getMessages().stream()
				.anyMatch(x -> x.getSeverity() == GUserMessage.MsgServerity.error && summary.equals(x.getSummary()));
	}

	@Test
	void aNewNetworkIsInsertedWithoutACodeAndGetsOneFromItsDescription() {
		OperationStatus<GAgentsNetwork> status = service.insert(network(null, "My network"));

		assertFalse(status.isHasErrorMessages(), String.valueOf(status.getMessages()));
		assertNotNull(status.getResult());
		assertEquals("my-network", status.getResult().getCode());
	}

	@Test
	void anEmptyCodeIsNoCode() {
		OperationStatus<GAgentsNetwork> status = service.insert(network("  ", "My network"));

		assertFalse(status.isHasErrorMessages(), String.valueOf(status.getMessages()));
		assertEquals("my-network", status.getResult().getCode());
	}

	@Test
	void aCodeGivenInAdvanceIsRefused() throws Exception {
		OperationStatus<GAgentsNetwork> status = service.insert(network("my-code", "My network"));

		assertNull(status.getResult());
		assertTrue(hasError(status, "Network code is generated"));
		verify(networkDao, never()).insert(any());
	}

	@Test
	void theCodeOfAnExistingNetworkIsRefused() throws Exception {
		stored.put("taken", network("taken", "Taken"));

		OperationStatus<GAgentsNetwork> status = service.insert(network("taken", "My network"));

		assertNull(status.getResult());
		assertTrue(hasError(status, "Network already exists"));
		verify(networkDao, never()).insert(any());
	}

	@Test
	void aNewNetworkNeedsTheDescriptionItsCodeIsGeneratedFrom() throws Exception {
		OperationStatus<GAgentsNetwork> status = service.insert(network(null, " "));

		assertNull(status.getResult());
		assertTrue(hasError(status, "Missing network description"));
		verify(networkDao, never()).insert(any());
	}

	@Test
	void theUpdateStillNeedsTheCode() {
		OperationStatus<GAgentsNetwork> status = service.update(network(null, "My network"));

		assertNull(status.getResult());
		assertTrue(hasError(status, "Missing network code"));
	}
}
