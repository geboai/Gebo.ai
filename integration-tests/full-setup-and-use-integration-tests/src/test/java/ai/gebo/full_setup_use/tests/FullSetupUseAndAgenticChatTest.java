package ai.gebo.full_setup_use.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.full_setup_use.tests.model.RegisteredInteractionTestModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.pipelines.service.defaultsteps.impl.model.RespondingWith;
import ai.gebo.monolithic.api.client.model.GeboChatResponse;
import ai.gebo.monolithic.app.Main;

/**
 * Full setup-and-use integration test exercising the <b>default network of
 * agents</b> as the default pipeline entry
 * ({@code ai.gebo.agents.standard.enabled=true}). With the standard agents
 * enabled the default pipeline router stops taking per-request decisions and
 * delegates every request to the agents network (controller/coordinator +
 * searchers + report writer), so the routing decision is always
 * {@link RespondingWith#DELEGATED_AGENT}.
 *
 * @see FullSetupUseAndPipelineTest for the pipeline-router counterpart
 */
@SpringBootTest(classes = Main.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "ai.gebo.agents.standard.enabled=true")
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class FullSetupUseAndAgenticChatTest extends AbstractFullSetupUseChatTest {
	private static final String registeredSession = "/registered-interaction-tests/agentic-chat-test-session.json";

	@Test
	public void setupCreateKnowledgeBaseAndRunAgenticChatTest() throws InterruptedException, IOException,
			InstantiationException, IllegalAccessException, GeboPersistenceException, LLMConfigException {
		runFullSetupAndChatSession(registeredSession);
	}

	@Override
	protected void verifyRoutingDecision(RegisteredInteractionTestModel registeredInteractionTestModel,
			GeboChatResponse response) {
		assertEquals(RespondingWith.DELEGATED_AGENT.name(), response.getPipelineRouterDecisionCode(),
				"With the default network of agents enabled every request must be delegated to the agents network");
	}

}
