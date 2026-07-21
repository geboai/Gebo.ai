package ai.gebo.microservices_cluster_setup_use.tests;

import org.junit.jupiter.api.Test;

/**
 * Microservices-cluster setup-and-use integration test exercising the
 * <b>default pipeline entry</b> with the network of agents <b>disabled</b>
 * ({@code ai.gebo.agents.standard.enabled=false}).
 * <p>
 * This is the cluster counterpart of
 * {@code ai.gebo.full_setup_use.tests.FullSetupUseAndPipelineTest} (which runs
 * the same scenario against the gebo.ai.app MONOLITH booted in-process via
 * {@code @SpringBootTest}). Here the SUT is the running microservices cluster
 * (eureka + gateway + brain + vectorizator + graphicator + chunker + filesystem
 * + uploads + userspace + sharepoint + confluence + jira + aws-s3 + googledrive
 * + mcpclient + integration + fulltextor + heimdall + tyr) brought up by the
 * parent pom's docker-compose profile, so the test JVM starts NO Spring context:
 * it drives the cluster through the generated java stub clients pointed at the
 * published host ports.
 * <p>
 * With the standard agents network turned off, the default pipeline router
 * takes the routing decisions and dispatches the request to the matching worker
 * (RAG / deep-search / tools / pure-llm), so the registered session asserts the
 * decisions actually taken.
 *
 * @see SetupUseMicroservicesClusterAgenticChatIT for the network-of-agents
 *      counterpart
 */
public class SetupUseMicroservicesClusterPipelineIT extends AbstractMicroservicesClusterSetupUseChatTest {
	private static final String registeredSession = "/registered-interaction-tests/pipeline-test-session.json";

	@Test
	public void setupCreateKnowledgeBaseAndRunPipelineTest() throws Exception {
		runFullSetupAndChatSession(registeredSession);
	}
}
