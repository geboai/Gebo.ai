package ai.gebo.architecture.agents.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ai.gebo.architecture.agents.model.AgentsExchangeMessage.MessageSemantic;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.services.GAbstractReactiveOutputAgentsNetworkServiceFactory;
import ai.gebo.architecture.agents.services.IAgentConfigDao;
import ai.gebo.architecture.agents.services.IAgentsNetworkDao;
import ai.gebo.architecture.agents.services.IGAgentConfigUsagesProvider;
import ai.gebo.architecture.agents.services.IGAgentServiceRuntimeDao;
import ai.gebo.architecture.agents.services.IGAgentsNetworkServiceFactory;
import ai.gebo.architecture.agents.services.IGAgentsNetworkServiceFactoryRepositoryPattern;
import ai.gebo.architecture.agents.services.IGNetworkAgentService;
import ai.gebo.architecture.agents.services.IGReactiveAgentService;
import ai.gebo.architecture.agents.services.IGReactiveToNetworkAgentAdapterFactory;
import ai.gebo.architecture.agents.services.IGReactiveToNetworkAgentAdapterFactory.AdapterWithFlux;
import ai.gebo.architecture.agents.services.IGReactiveToNetworkAgentAdapterFactoryRepositoryPattern;
import ai.gebo.architecture.agents.services.IGRoutingNetworkAgentService;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;

/**
 * Pins the validation of a network of agents against what the runtime needs to run
 * it, and the insert of a new network.
 * <p>
 * The agent services are chosen by the prefix of the agent config code: a
 * {@code router...} dispatches commands typed on each target's input, a
 * {@code reader...} forwards its String output, a {@code delivering...} forwards an
 * Integer, a {@code sharing...} answers a List with a RESPONSE, a {@code reactive...}
 * answers the user directly through its network adapter; any other answers a String
 * with a RESPONSE. The networks run on a String to String factory unless they
 * declare the reactive output one.
 */
class GAgentsNetworkCrudServiceImplTest {
	private static final String FACTORY = "factory";
	private static final String REACTIVE_FACTORY = "reactiveFactory";
	private final Map<String, GAgentsNetwork> stored = new HashMap<>();
	private final List<String> deletedAgents = new ArrayList<>();
	private IAgentsNetworkDao networkDao;
	private GAgentsNetworkCrudServiceImpl service;

	@SuppressWarnings({ "rawtypes" })
	private static IGNetworkAgentService agent(Class<? extends IGNetworkAgentService> type, String id,
			Class<?> input, Class<?> output, MessageSemantic semantic) {
		IGNetworkAgentService agent = mock(type);
		when(agent.getId()).thenReturn(id);
		when(agent.getInputType()).thenReturn((Class) input);
		when(agent.getOutputType()).thenReturn((Class) output);
		when(agent.getProducedMessageSemantic()).thenReturn(semantic);
		return agent;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static IGAgentsNetworkServiceFactory factory(Class<? extends IGAgentsNetworkServiceFactory> type,
			String id) {
		IGAgentsNetworkServiceFactory factory = mock(type);
		when(factory.getId()).thenReturn(id);
		when(factory.getInputType()).thenReturn(String.class);
		when(factory.getOutputType()).thenReturn(String.class);
		return factory;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@BeforeEach
	void setUp() throws Exception {
		Map<String, Object> services = new HashMap<>();
		services.put("routerAgent", agent(IGRoutingNetworkAgentService.class, "routerAgent", String.class, Void.class,
				MessageSemantic.EXECUTE_AND_SHARE_RESULT));
		services.put("readerAgent", agent(IGNetworkAgentService.class, "readerAgent", String.class, String.class,
				MessageSemantic.EXECUTE_AND_SHARE_RESULT));
		services.put("deliveringAgent", agent(IGNetworkAgentService.class, "deliveringAgent", String.class,
				Integer.class, MessageSemantic.EXECUTE_AND_SHARE_RESULT));
		services.put("sharingAgent", agent(IGNetworkAgentService.class, "sharingAgent", String.class, List.class,
				MessageSemantic.RESPONSE));
		services.put("textAgent", agent(IGNetworkAgentService.class, "textAgent", String.class, String.class,
				MessageSemantic.RESPONSE));
		// a reactive agent (answering the user directly) takes part through its network adapter
		IGReactiveAgentService reactiveAgent = mock(IGReactiveAgentService.class);
		when(reactiveAgent.getId()).thenReturn("reactiveAgent");
		services.put("reactiveAgent", reactiveAgent);
		IGNetworkAgentService reactiveAdapter = agent(IGNetworkAgentService.class, "reactiveAgent", String.class,
				String.class, MessageSemantic.RESPONSE);
		IGReactiveToNetworkAgentAdapterFactory adapterFactory = mock(IGReactiveToNetworkAgentAdapterFactory.class);
		when(adapterFactory.create(any())).thenReturn(new AdapterWithFlux(reactiveAdapter, null, null));
		IGReactiveToNetworkAgentAdapterFactoryRepositoryPattern adapterFactories = mock(
				IGReactiveToNetworkAgentAdapterFactoryRepositoryPattern.class);
		when(adapterFactories.getFactory(any())).thenReturn(adapterFactory);
		IGAgentServiceRuntimeDao serviceDao = mock(IGAgentServiceRuntimeDao.class);
		when(serviceDao.findByCode(any())).thenAnswer(x -> services.get((String) x.getArgument(0)));
		IAgentConfigDao agentDao = mock(IAgentConfigDao.class);
		when(agentDao.findByCode(any())).thenAnswer(x -> {
			GAgentConfig agent = new GAgentConfig();
			agent.setCode(x.getArgument(0));
			String kind = agent.getCode().replaceAll("[A-Z].*", "");
			agent.setAgentServiceId(services.containsKey(kind + "Agent") ? kind + "Agent" : "textAgent");
			// the agents declared by configuration beans are read-only
			agent.setReadOnly(agent.getCode().startsWith("system") ? true : null);
			return agent;
		});
		doAnswer(x -> deletedAgents.add(((GAgentConfig) x.getArgument(0)).getCode())).when(agentDao).delete(any());
		IGAgentsNetworkServiceFactoryRepositoryPattern networkFactories = mock(
				IGAgentsNetworkServiceFactoryRepositoryPattern.class);
		IGAgentsNetworkServiceFactory plainFactory = factory(IGAgentsNetworkServiceFactory.class, FACTORY);
		IGAgentsNetworkServiceFactory reactiveFactory = factory(GAbstractReactiveOutputAgentsNetworkServiceFactory.class,
				REACTIVE_FACTORY);
		when(networkFactories.findByCode(FACTORY)).thenReturn(plainFactory);
		when(networkFactories.findByCode(REACTIVE_FACTORY)).thenReturn(reactiveFactory);
		networkDao = mock(IAgentsNetworkDao.class);
		when(networkDao.findByCode(any())).thenAnswer(x -> stored.get((String) x.getArgument(0)));
		when(networkDao.getConfigurations()).thenAnswer(x -> List.copyOf(stored.values()));
		doAnswer(x -> stored.remove(((GAgentsNetwork) x.getArgument(0)).getCode())).when(networkDao).delete(any());
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
		when(runtimeBinder.getImplementationOf(IGReactiveToNetworkAgentAdapterFactoryRepositoryPattern.class))
				.thenReturn(adapterFactories);
		when(runtimeBinder.getImplementationOf(IGAgentsNetworkServiceFactoryRepositoryPattern.class))
				.thenReturn(networkFactories);
		// an agent exported elsewhere (e.g. over A2A) is not used solely by a network
		IGAgentConfigUsagesProvider usages = code -> code.startsWith("exported")
				? List.of("exported as A2A agent by the server 'srv'")
				: List.of();
		service = new GAgentsNetworkCrudServiceImpl(runtimeBinder, List.of(usages));
	}

	private static AgentNetworkParticipant participant(String agentCode, boolean input, boolean output,
			String... communicationList) {
		AgentNetworkParticipant participant = new AgentNetworkParticipant();
		participant.setAgentConfigCode(agentCode);
		participant.setInputNode(input);
		participant.setOutputNode(output);
		participant.setCommunicationList(List.of(communicationList));
		return participant;
	}

	private static GAgentsNetwork network(String code, String description, String factory,
			AgentNetworkParticipant... participants) {
		GAgentsNetwork network = new GAgentsNetwork();
		network.setCode(code);
		network.setDescription(description);
		network.setScenarioDescription("Reads and writes");
		network.setAgentsNetworkServiceFactoryId(factory);
		network.setAgents(List.of(participants));
		return network;
	}

	/** A valid network: an input node forwarding the request to an output node answering. */
	private static GAgentsNetwork network(String code, String description) {
		return network(code, description, FACTORY, participant("reader", true, false, "writer"),
				participant("writer", false, true));
	}

	private static boolean hasError(OperationStatus<?> status, String summary) {
		return status.getMessages().stream()
				.anyMatch(x -> x.getSeverity() == GUserMessage.MsgServerity.error && summary.equals(x.getSummary()));
	}

	private OperationStatus<GAgentsNetwork> validate(String factory, AgentNetworkParticipant... participants) {
		return service.validate(network("net", "Net", factory, participants));
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

	@Test
	void theAgentsUsedOnlyByADeletedNetworkGoWithIt() {
		GAgentsNetwork deleted = network("deleted", "Deleted", FACTORY,
				participant("readerOwn", true, false, "writerOwn", "sharedWriter", "exportedWriter", "systemWriter"),
				participant("writerOwn", false, true), participant("sharedWriter", false, false),
				participant("exportedWriter", false, false), participant("systemWriter", false, false));
		stored.put("deleted", deleted);
		stored.put("other", network("other", "Other", FACTORY, participant("readerOther", true, false, "sharedWriter"),
				participant("sharedWriter", false, true)));

		OperationStatus<GAgentsNetwork> status = service.delete(deleted);

		assertFalse(status.isHasErrorMessages(), String.valueOf(status.getMessages()));
		assertFalse(stored.containsKey("deleted"));
		assertEquals(List.of("readerOwn", "writerOwn"), deletedAgents,
				"kept: the one another network uses, the exported one and the read-only one");
		assertTrue(status.getMessages().stream().anyMatch(x -> "Agents kept".equals(x.getSummary())
				&& x.getDetail().contains("sharedWriter") && x.getDetail().contains("exportedWriter")
				&& !x.getDetail().contains("systemWriter")));
	}

	@Test
	void aRouterCommandsEachAgentInItsOwnInputType() {
		// the router's Void output is never what it delivers: no type to match, no warning
		OperationStatus<GAgentsNetwork> status = validate(FACTORY, participant("reader", true, false, "router"),
				participant("router", false, false, "sharing", "writer"), participant("sharing", false, false),
				participant("writer", false, true, "router"));

		assertFalse(status.isHasErrorMessages(), String.valueOf(status.getMessages()));
		assertFalse(status.isHasWarnMessages(), String.valueOf(status.getMessages()));
	}

	@Test
	void anAgentDeliveringItsMessagesNeedsAnAgentToTalkTo() {
		OperationStatus<GAgentsNetwork> status = validate(FACTORY, participant("reader", true, false),
				participant("router", false, false), participant("writer", false, true));

		assertNull(status.getResult());
		assertEquals(2, status.getMessages().stream().filter(x -> "No agent to talk to".equals(x.getSummary()))
				.count(), String.valueOf(status.getMessages()));
	}

	@Test
	void anOutputSharedInTheSessionContextNeedsNoMatchingInput() {
		OperationStatus<GAgentsNetwork> status = validate(FACTORY, participant("reader", true, false, "sharing"),
				participant("sharing", false, false, "writer"), participant("writer", false, true));

		assertFalse(status.isHasErrorMessages(), String.valueOf(status.getMessages()));
	}

	@Test
	void anOutputDeliveredAsInputMustMatchTheInputType() {
		OperationStatus<GAgentsNetwork> status = validate(FACTORY, participant("delivering", true, false, "writer"),
				participant("writer", false, true));

		assertNull(status.getResult());
		assertTrue(hasError(status, "Incompatible communication types"));
	}

	@Test
	void theNetworkFactoryMustBeDeclaredAndRegistered() {
		assertTrue(hasError(validate(null, participant("reader", true, false, "writer"),
				participant("writer", false, true)), "Missing network service factory"));
		assertTrue(hasError(validate("unknown", participant("reader", true, false, "writer"),
				participant("writer", false, true)), "Unknown network service factory"));
	}

	@Test
	void theNodesMustTakeAndGiveWhatTheFactoryRunsWith() {
		// the sharing agent produces a List, the factory's output is a String
		OperationStatus<GAgentsNetwork> status = validate(FACTORY, participant("reader", true, false, "sharing"),
				participant("sharing", false, true));

		assertTrue(hasError(status, "Output node not producing the network output"));
	}

	@Test
	void aReactiveAgentTakesPartAsTheOutputNodeOfAReactiveOutputFactory() {
		OperationStatus<GAgentsNetwork> status = validate(REACTIVE_FACTORY,
				participant("reader", true, false, "reactiveWriter"), participant("reactiveWriter", false, true));

		assertFalse(status.isHasErrorMessages(), String.valueOf(status.getMessages()));
		assertNotNull(status.getResult());
	}

	@Test
	void aReactiveAgentCannotBeAnInnerNode() {
		OperationStatus<GAgentsNetwork> status = validate(REACTIVE_FACTORY,
				participant("reactiveReader", true, false, "writer"), participant("writer", false, true));

		assertNull(status.getResult());
		assertTrue(hasError(status, "Reactive agent not on an output node"));
	}

	@Test
	void onlyAReactiveOutputFactoryRunsAReactiveAgent() {
		OperationStatus<GAgentsNetwork> status = validate(FACTORY,
				participant("reader", true, false, "reactiveWriter"), participant("reactiveWriter", false, true));

		assertTrue(hasError(status, "Reactive agent not run by the network factory"));
	}

	@Test
	void aReactiveOutputFactoryStreamsExactlyOneReactiveOutput() {
		assertTrue(hasError(validate(REACTIVE_FACTORY, participant("reader", true, false, "writer"),
				participant("writer", false, true)), "One reactive output node needed"));
		assertTrue(hasError(validate(REACTIVE_FACTORY, participant("reader", true, false, "reactiveOne", "reactiveTwo"),
				participant("reactiveOne", false, true), participant("reactiveTwo", false, true)),
				"One reactive output node needed"));
	}
}
