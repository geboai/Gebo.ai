package ai.gebo.microservices_cluster_setup_use.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import gebo.microservices.api.client.brain.model.GeboChatResponse;
import ai.gebo.microservices_cluster_setup_use.tests.model.RegisteredInteractionTestModel;

/**
 * Microservices-cluster setup-and-use integration test exercising the
 * <b>default network of agents</b> as the default pipeline entry
 * ({@code ai.gebo.agents.standard.enabled=true}).
 * <p>
 * Cluster counterpart of
 * {@code ai.gebo.full_setup_use.tests.FullSetupUseAndAgenticChatTest}. With
 * the standard agents enabled the default pipeline router stops taking
 * per-request decisions and delegates every request to the agents network
 * (controller/coordinator + searchers + report writer), so the routing decision
 * is always {@code DELEGATED_AGENT}.
 *
 * @see SetupUseMicroservicesClusterPipelineIT for the pipeline-router counterpart
 */
public class SetupUseMicroservicesClusterAgenticChatIT extends AbstractMicroservicesClusterSetupUseChatTest {
	private static final String registeredSession = "/registered-interaction-tests/agentic-chat-test-session.json";

	@Test
	public void setupCreateKnowledgeBaseAndRunAgenticChatTest() throws Exception {
		runFullSetupAndChatSession(registeredSession);
	}

	@Override
	protected void verifyRoutingDecision(RegisteredInteractionTestModel model, GeboChatResponse response) {
		assertEquals("DELEGATED_AGENT", String.valueOf(response.getPipelineRouterDecisionCode()),
				"With the default network of agents enabled every request must be delegated to the agents network");
	}
}
