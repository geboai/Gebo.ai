package ai.gebo.architecture.agents.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.services.IAgentConfigDao;
import ai.gebo.architecture.agents.services.IAgentsNetworkDao;
import ai.gebo.architecture.patterns.IGRuntimeBinder;

/**
 * Pins the codes of the copies: {@code <original code>-<n>}, the first slot not taken
 * (by a stored entity or by a configuration bean alike) from 1 on, the copy of a copy
 * taking the next free slot of its root original; the other entities keep the code
 * from their description.
 */
class GAgentsCopyCodeGeneratorTest {
	/** The codes the DAOs find: stored entities and configuration beans. */
	private final Set<String> taken = new HashSet<>();
	private GAgentConfigCopyCodeGenerator agentCodes;
	private GAgentsNetworkCopyCodeGenerator networkCodes;

	@BeforeEach
	void setUp() {
		IAgentConfigDao agentDao = mock(IAgentConfigDao.class);
		when(agentDao.findByCode(any())).thenAnswer(x -> taken.contains(x.getArgument(0)) ? new GAgentConfig() : null);
		IAgentsNetworkDao networkDao = mock(IAgentsNetworkDao.class);
		when(networkDao.findByCode(any()))
				.thenAnswer(x -> taken.contains(x.getArgument(0)) ? new GAgentsNetwork() : null);
		IGRuntimeBinder runtimeBinder = mock(IGRuntimeBinder.class);
		when(runtimeBinder.getImplementationOf(IAgentConfigDao.class)).thenReturn(agentDao);
		when(runtimeBinder.getImplementationOf(IAgentsNetworkDao.class)).thenReturn(networkDao);
		agentCodes = new GAgentConfigCopyCodeGenerator(runtimeBinder);
		networkCodes = new GAgentsNetworkCopyCodeGenerator(runtimeBinder);
	}

	private static GAgentConfig copyOf(String code) {
		GAgentConfig copy = new GAgentConfig();
		copy.setCopiedFromCode(code);
		return copy;
	}

	@Test
	void anEntityThatIsNoCopyKeepsTheCodeFromItsDescription() {
		assertEquals("my-agent", agentCodes.generateCode(new GAgentConfig(), "my-agent"));
	}

	@Test
	void aCopyTakesTheFirstSlotOfItsOriginal() {
		assertEquals("controllerAgent-1", agentCodes.generateCode(copyOf("controllerAgent"), "coordinator-copy"));
		GAgentsNetwork network = new GAgentsNetwork();
		network.setCopiedFromCode("DEFAULT_AGENTS_NETWORK");
		assertEquals("DEFAULT_AGENTS_NETWORK-1", networkCodes.generateCode(network, "coordinator-with-copy"));
	}

	@Test
	void aTakenSlotIsSkipped() {
		taken.add("controllerAgent-1");
		assertEquals("controllerAgent-2", agentCodes.generateCode(copyOf("controllerAgent"), "x"));
		taken.add("controllerAgent-2");
		assertEquals("controllerAgent-3", agentCodes.generateCode(copyOf("controllerAgent"), "x"));
	}

	@Test
	void aFreedSlotIsTakenAgain() {
		taken.add("controllerAgent-2");
		assertEquals("controllerAgent-1", agentCodes.generateCode(copyOf("controllerAgent"), "x"));
	}

	@Test
	void theCopyOfACopyTakesTheNextSlotOfItsRootOriginal() {
		taken.add("controllerAgent-1");
		taken.add("controllerAgent-2");
		assertEquals("controllerAgent-3", agentCodes.generateCode(copyOf("controllerAgent-2"), "x"),
				"never controllerAgent-2-1");
		assertEquals("controllerAgent-3", agentCodes.generateCode(copyOf("controllerAgent-1"), "x"));
	}
}
