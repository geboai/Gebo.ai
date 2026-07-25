package ai.gebo.full_setup_use.tests;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.monolithic.app.Main;

/**
 * Full setup-and-use integration test exercising the <b>default pipeline
 * entry</b> with the network of agents <b>disabled</b>
 * ({@code ai.gebo.agents.standard.enabled=false}). With the delegated agents
 * network turned off, the default pipeline router takes the routing decisions
 * and dispatches the request to the matching worker (RAG / deep-search / tools /
 * pure-llm), so the registered session asserts the decisions actually taken.
 *
 * @see FullSetupUseAndAgenticChatTest for the network-of-agents counterpart
 */
@SpringBootTest(classes = Main.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "ai.gebo.agents.standard.enabled=false")
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class FullSetupUseAndPipelineTest extends AbstractFullSetupUseChatTest {
	private static final String registeredSession = "/registered-interaction-tests/pipeline-test-session.json";

	@Test
	public void setupCreateKnowledgeBaseAndRunPipelineTest() throws InterruptedException, IOException,
			InstantiationException, IllegalAccessException, GeboPersistenceException, LLMConfigException {
		runFullSetupAndChatSession(registeredSession);
	}

}
