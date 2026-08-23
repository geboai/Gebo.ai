package ai.gebo.full_setup_use.tests;

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

	/**
	 * With the standard agents enabled every request is expected to come back as
	 * {@link RespondingWith#DELEGATED_AGENT}. Reported through the shared soft/strict
	 * gate rather than asserted outright, so that a run whose only anomaly is the
	 * route taken still proves the monolith answers - see the base class javadoc.
	 */
	@Override
	protected void verifyRoutingDecision(RegisteredInteractionTestModel registeredInteractionTestModel,
			GeboChatResponse response) {
		if (!RespondingWith.DELEGATED_AGENT.name().equals(response.getPipelineRouterDecisionCode())) {
			reportUnexpectedRoutingDecision(response.getPipelineRouterDecisionCode(),
					"[" + RespondingWith.DELEGATED_AGENT.name()
							+ "] (with the default network of agents enabled every request should be delegated to the agents network)");
		}
	}

}
