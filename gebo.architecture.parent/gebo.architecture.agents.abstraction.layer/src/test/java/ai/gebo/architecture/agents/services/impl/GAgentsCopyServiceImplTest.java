package ai.gebo.architecture.agents.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.PartialOperationStatus;
import ai.gebo.architecture.agents.services.IAgentConfigDao;
import ai.gebo.architecture.agents.services.IAgentsNetworkDao;
import ai.gebo.architecture.agents.services.IGAgentsNetworkCrudService;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;
import ai.gebo.model.base.GBaseObject;

/**
 * Pins the copies of agents and networks of agents: editable also when the original
 * is a configuration bean, never the default configuration of an agent service,
 * under a unique description and code, and for a network on copies of its agents
 * with the participants and communication lists rewired to them.
 */
class GAgentsCopyServiceImplTest {
	/** Configuration beans: resolved before the stored entities, like the real DAOs do. */
	private final Map<String, GAgentConfig> beanAgents = new LinkedHashMap<>();
	private final Map<String, GAgentConfig> storedAgents = new LinkedHashMap<>();
	private final Map<String, GAgentsNetwork> beanNetworks = new LinkedHashMap<>();
	private final Map<String, GAgentsNetwork> storedNetworks = new LinkedHashMap<>();
	private Predicate<GAgentsNetwork> valid = x -> true;
	private GAgentsCopyServiceImpl service;

	/** The code the persistence layer generates from a description. */
	private static String slug(String description) {
		StringBuilder sb = new StringBuilder();
		for (char c : description.toLowerCase().toCharArray()) {
			if (Character.isLetterOrDigit(c) || c == '-' || c == '_' || c == '.') {
				sb.append(c);
			} else if (Character.isWhitespace(c)) {
				sb.append('-');
			}
		}
		return sb.toString();
	}

	/**
	 * Stores like the persistence layer: the code generated from the description goes
	 * through the code generator of the entity's type, then is made unique.
	 */
	private static <T extends GBaseObject> T insert(Map<String, T> stored, T value,
			BiFunction<T, String, String> codeGenerator) {
		String code = codeGenerator.apply(value, slug(value.getDescription()));
		while (stored.containsKey(code)) {
			code = code + "-x";
		}
		value.setCode(code);
		stored.put(code, value);
		return value;
	}

	private static <T> T find(Map<String, T> beans, Map<String, T> stored, String code) {
		return beans.containsKey(code) ? beans.get(code) : stored.get(code);
	}

	private static <T> List<T> all(Map<String, T> beans, Map<String, T> stored) {
		List<T> all = new ArrayList<>(beans.values());
		all.addAll(stored.values());
		return all;
	}

	private GAgentConfig beanAgent(String code, String description, String serviceId) {
		GAgentConfig agent = new GAgentConfig();
		agent.setCode(code);
		agent.setDescription(description);
		agent.setAgentServiceId(serviceId);
		agent.setReadOnly(true);
		beanAgents.put(code, agent);
		return agent;
	}

	private static AgentNetworkParticipant participant(String agentCode, String contextualName,
			String... communicationList) {
		AgentNetworkParticipant participant = new AgentNetworkParticipant();
		participant.setAgentConfigCode(agentCode);
		participant.setAgentContextualName(contextualName);
		participant.setCommunicationList(List.of(communicationList));
		return participant;
	}

	@BeforeEach
	void setUp() throws Exception {
		IGRuntimeBinder runtimeBinder = mock(IGRuntimeBinder.class);
		GAgentConfigCopyCodeGenerator agentCodes = new GAgentConfigCopyCodeGenerator(runtimeBinder);
		GAgentsNetworkCopyCodeGenerator networkCodes = new GAgentsNetworkCopyCodeGenerator(runtimeBinder);
		IAgentConfigDao agentDao = mock(IAgentConfigDao.class);
		when(agentDao.getConfigurations()).thenAnswer(x -> all(beanAgents, storedAgents));
		when(agentDao.findByCode(any())).thenAnswer(x -> find(beanAgents, storedAgents, x.getArgument(0)));
		when(agentDao.insert(any()))
				.thenAnswer(x -> insert(storedAgents, (GAgentConfig) x.getArgument(0), agentCodes::generateCode));
		doAnswer(x -> storedAgents.remove(((GAgentConfig) x.getArgument(0)).getCode())).when(agentDao).delete(any());
		IAgentsNetworkDao networkDao = mock(IAgentsNetworkDao.class);
		when(networkDao.getConfigurations()).thenAnswer(x -> all(beanNetworks, storedNetworks));
		when(networkDao.findByCode(any())).thenAnswer(x -> find(beanNetworks, storedNetworks, x.getArgument(0)));
		when(networkDao.insert(any()))
				.thenAnswer(x -> insert(storedNetworks, (GAgentsNetwork) x.getArgument(0), networkCodes::generateCode));
		doAnswer(x -> storedNetworks.remove(((GAgentsNetwork) x.getArgument(0)).getCode())).when(networkDao)
				.delete(any());
		IGAgentsNetworkCrudService crudService = mock(IGAgentsNetworkCrudService.class);
		when(crudService.validate(any())).thenAnswer(x -> {
			GAgentsNetwork network = x.getArgument(0);
			return valid.test(network) ? PartialOperationStatus.of(network)
					: PartialOperationStatus.ofError("Invalid network", "The network is not valid");
		});
		when(runtimeBinder.getImplementationOf(IAgentConfigDao.class)).thenReturn(agentDao);
		when(runtimeBinder.getImplementationOf(IAgentsNetworkDao.class)).thenReturn(networkDao);
		when(runtimeBinder.getImplementationOf(IGAgentsNetworkCrudService.class)).thenReturn(crudService);
		service = new GAgentsCopyServiceImpl(runtimeBinder);
	}

	@Test
	void theCopyOfABeanAgentIsEditableAndNeverTheDefaultConfiguration() {
		GAgentConfig original = beanAgent("controllerAgent", "Network controller", "controllerAgent");
		original.setDefaultConfiguration(true);
		original.setTemperature(0.7);

		OperationStatus<GAgentConfig> status = service.copyAgent("controllerAgent");

		GAgentConfig copy = status.getResult();
		assertNotNull(copy);
		assertFalse(status.isHasErrorMessages());
		assertEquals("controllerAgent-1", copy.getCode(), "named after the original, not its description");
		assertEquals("Network controller (copy)", copy.getDescription());
		assertEquals(Boolean.FALSE, copy.getReadOnly());
		assertNull(copy.getDefaultConfiguration());
		assertEquals("controllerAgent", copy.getAgentServiceId());
		assertEquals(0.7, copy.getTemperature());
		assertEquals(Boolean.TRUE, original.getReadOnly(), "the original is untouched");
		assertEquals(Boolean.TRUE, original.getDefaultConfiguration());
	}

	@Test
	void everyCopyGetsItsOwnDescriptionAndCode() {
		beanAgent("writer", "Writer", "ReportWriterNetworkAgentService");

		GAgentConfig first = service.copyAgent("writer").getResult();
		GAgentConfig second = service.copyAgent("writer").getResult();

		assertEquals("Writer (copy)", first.getDescription());
		assertEquals("Writer (copy 2)", second.getDescription());
		assertEquals("writer-1", first.getCode());
		assertEquals("writer-2", second.getCode());
		assertNotEquals(first.getCode(), second.getCode());
		assertEquals(2, storedAgents.size());
	}

	@Test
	void theCopyOfACopyTakesTheNextNumberOfItsRootOriginal() {
		beanAgent("writer", "Writer", "ReportWriterNetworkAgentService");
		GAgentConfig first = service.copyAgent("writer").getResult();

		GAgentConfig copyOfCopy = service.copyAgent(first.getCode()).getResult();

		assertEquals("Writer (copy 2)", copyOfCopy.getDescription(), "never Writer (copy) (copy)");
		assertEquals("writer-2", copyOfCopy.getCode());
	}

	@Test
	void theCopiesAreNamedAfterTheRootDescription() {
		assertEquals("Writer", GAgentsCopyServiceImpl.baseDescription(described("Writer (copy)")));
		assertEquals("Writer", GAgentsCopyServiceImpl.baseDescription(described(" Writer (Copy 3) ")));
		assertEquals("Writer", GAgentsCopyServiceImpl.baseDescription(described("Writer (copy) (copy 2)")),
				"the marks stacked by older copies are removed too");
		assertEquals("Writer (copyist)", GAgentsCopyServiceImpl.baseDescription(described("Writer (copyist)")));
		assertEquals("Writer (copy) notes", GAgentsCopyServiceImpl.baseDescription(described("Writer (copy) notes")));
		assertEquals("(copy)", GAgentsCopyServiceImpl.baseDescription(described("(copy)")),
				"nothing but a copy mark is kept as it is");
		assertEquals("writer", GAgentsCopyServiceImpl.baseDescription(described(" ")), "no description: the code");
	}

	private static GAgentConfig described(String description) {
		GAgentConfig agent = new GAgentConfig();
		agent.setCode("writer");
		agent.setDescription(description);
		return agent;
	}

	@Test
	void aCodeDeclaredByABeanIsNeverGivenToACopy() {
		beanAgent("writer", "Writer", "ReportWriterNetworkAgentService");
		// a bean already declares the code the first copy would be named with
		beanAgent("writer-1", "Another agent", "toolCallingAgentService");

		GAgentConfig copy = service.copyAgent("writer").getResult();

		assertEquals("writer-2", copy.getCode());
		assertEquals("Writer (copy)", copy.getDescription());
		assertEquals(List.of("writer-2"), List.copyOf(storedAgents.keySet()));
	}

	@Test
	void aMissingAgentIsNotCopied() {
		assertNull(service.copyAgent("missing").getResult());
		assertTrue(service.copyAgent("missing").isHasErrorMessages());
		assertTrue(service.copyAgent(" ").isHasErrorMessages());
		assertTrue(storedAgents.isEmpty());
	}

	private GAgentsNetwork beanNetwork() {
		beanAgent("chatRuntimeDataQueryAdapter", "Input adapter", "chatRuntimeDataQueryAdapter");
		beanAgent("controllerAgent", "Controller", "controllerAgent");
		beanAgent("searcher", "Searcher", "internalKnowledgeBaseSearcher");
		beanAgent("ReportWriterNetworkAgentService", "Writer", "ReportWriterNetworkAgentService");
		GAgentsNetwork network = new GAgentsNetwork();
		network.setCode("DEFAULT_AGENTS_NETWORK");
		network.setDescription("Full network");
		network.setReadOnly(true);
		network.setAgents(List.of(participant("chatRuntimeDataQueryAdapter", null, "controllerAgent"),
				participant("controllerAgent", null, "searcher-web", "searcher-kb", "ReportWriterNetworkAgentService"),
				// the same agent configuration taking part twice, under two contextual names
				participant("searcher", "web", "ReportWriterNetworkAgentService"),
				participant("searcher", "kb", "ReportWriterNetworkAgentService"),
				participant("ReportWriterNetworkAgentService", null, "controllerAgent")));
		beanNetworks.put(network.getCode(), network);
		return network;
	}

	@Test
	void theCopyOfANetworkRunsOnCopiesOfItsAgents() {
		GAgentsNetwork original = beanNetwork();

		OperationStatus<GAgentsNetwork> status = service.copyAgentsNetwork("DEFAULT_AGENTS_NETWORK");

		GAgentsNetwork copy = status.getResult();
		assertNotNull(copy);
		assertFalse(status.isHasErrorMessages());
		assertEquals("DEFAULT_AGENTS_NETWORK-1", copy.getCode());
		assertEquals("Full network (copy)", copy.getDescription());
		assertEquals(Boolean.FALSE, copy.getReadOnly());
		assertEquals(4, storedAgents.size(), "one copy per agent configuration, the shared one copied once");
		for (GAgentConfig agentCopy : storedAgents.values()) {
			assertEquals(Boolean.FALSE, agentCopy.getReadOnly());
		}
		// the agents are addressed by names made from the originals' codes
		List<String> participants = copy.getAgents().stream().map(AgentNetworkParticipant::getNetworkAgentName)
				.toList();
		assertEquals(List.of("chatRuntimeDataQueryAdapter-1", "controllerAgent-1", "searcher-1-web", "searcher-1-kb",
				"ReportWriterNetworkAgentService-1"), participants);
		assertEquals(List.of("controllerAgent-1"), copy.getAgents().get(0).getCommunicationList());
		assertEquals(List.of("searcher-1-web", "searcher-1-kb", "ReportWriterNetworkAgentService-1"),
				copy.getAgents().get(1).getCommunicationList());
		assertEquals(List.of("ReportWriterNetworkAgentService-1"), copy.getAgents().get(2).getCommunicationList());
		assertEquals(List.of("controllerAgent-1"), copy.getAgents().get(4).getCommunicationList());
		// the original network and its agents are untouched
		assertEquals(Boolean.TRUE, original.getReadOnly());
		assertEquals("controllerAgent", original.getAgents().get(1).getAgentConfigCode());
		assertEquals(List.of("searcher-web", "searcher-kb", "ReportWriterNetworkAgentService"),
				original.getAgents().get(1).getCommunicationList());
	}

	@Test
	void theCopyOfANetworkCopyTakesTheNextNumberOfItsRootOriginal() {
		beanNetwork();
		GAgentsNetwork first = service.copyAgentsNetwork("DEFAULT_AGENTS_NETWORK").getResult();

		GAgentsNetwork copyOfCopy = service.copyAgentsNetwork(first.getCode()).getResult();

		assertEquals("DEFAULT_AGENTS_NETWORK-2", copyOfCopy.getCode());
		assertEquals("Full network (copy 2)", copyOfCopy.getDescription());
		assertEquals("controllerAgent-2", copyOfCopy.getAgents().get(1).getAgentConfigCode());
		assertEquals("Controller (copy 2)", storedAgents.get("controllerAgent-2").getDescription());
	}

	@Test
	void anInvalidNetworkIsNotCopied() {
		beanNetwork();
		valid = x -> false;

		OperationStatus<GAgentsNetwork> status = service.copyAgentsNetwork("DEFAULT_AGENTS_NETWORK");

		assertNull(status.getResult());
		assertTrue(status.isHasErrorMessages());
		assertTrue(storedAgents.isEmpty(), "nothing is stored for an invalid network");
		assertTrue(storedNetworks.isEmpty());
	}

	@Test
	void anInvalidNetworkCopyIsRemovedWithItsAgents() {
		beanNetwork();
		// the original passes the validation, its copy does not
		valid = x -> x.getCode().equals("DEFAULT_AGENTS_NETWORK");

		OperationStatus<GAgentsNetwork> status = service.copyAgentsNetwork("DEFAULT_AGENTS_NETWORK");

		assertNull(status.getResult());
		assertTrue(status.getMessages().stream().anyMatch(x -> x.getSeverity() == GUserMessage.MsgServerity.error));
		assertTrue(storedAgents.isEmpty(), "the agent copies are removed");
		assertTrue(storedNetworks.isEmpty(), "the network copy is removed");
	}

	@Test
	void aMissingNetworkIsNotCopied() {
		assertNull(service.copyAgentsNetwork("missing").getResult());
		assertTrue(service.copyAgentsNetwork("missing").isHasErrorMessages());
		assertTrue(service.copyAgentsNetwork(null).isHasErrorMessages());
	}
}
