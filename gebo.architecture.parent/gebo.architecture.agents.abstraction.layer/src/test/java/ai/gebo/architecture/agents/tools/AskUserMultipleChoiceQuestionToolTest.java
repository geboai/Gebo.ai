package ai.gebo.architecture.agents.tools;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.ToolCallback;

import ai.gebo.architecture.agents.model.AgentSuspendedWaitingCondition;
import ai.gebo.architecture.agents.model.ClarificationAnswer;
import ai.gebo.architecture.agents.model.ClarificationOptions;
import ai.gebo.architecture.agents.services.GWaitExternalConditionSink;
import ai.gebo.architecture.agents.services.IGWaitExternalCondition;
import ai.gebo.architecture.ai.model.ToolReference;
import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCalling;

/**
 * Pins what the multiple choice question tool adds to what it shares with the
 * single choice one: the question is declared as a multiple choice, the answer
 * reaches the model option by option, and the source mounts both tools.
 */
class AskUserMultipleChoiceQuestionToolTest {
	private final GWaitExternalConditionSink wait = new GWaitExternalConditionSink();

	private ToolContext inACallWith(IGWaitExternalCondition loaded) {
		GeboToolCalling toolCalling = GeboToolCalling.builder().provide(IGWaitExternalCondition.class, loaded).build();
		return new ToolContext(toolCalling.toolsContext(Map.of()));
	}

	private void theModelAsks() {
		AskUserMultipleChoiceQuestionTool.declare().call(
				"{\"question\":\"Which years?\",\"whyNeeded\":\"it matters\",\"customAnswerAllowed\":true,"
						+ "\"options\":[{\"label\":\"2023\",\"description\":\"the closed year\"},{\"label\":\"2024\"}]}",
				inACallWith(wait));
	}

	@Test
	void theToolEndsTheCallItIsPartOf() {
		ToolCallback callback = AskUserMultipleChoiceQuestionTool.declare();

		assertEquals(AskUserMultipleChoiceQuestionTool.TOOL_NAME, callback.getToolDefinition().name());
		assertTrue(callback.getToolMetadata().returnDirect(), "the loop stops at it: the work waits");
	}

	@Test
	void theQuestionIsDeclaredAsAMultipleChoice() {
		theModelAsks();

		ClarificationOptions options = (ClarificationOptions) wait.waited().get(0).getLlmChosenOptions();
		assertTrue(options.isMultipleChoice(), "the user picks every option that applies");
		assertEquals(List.of("2023", "2024"), options.labels());
		assertEquals("the closed year", options.getOptions().get(0).getDescription());
		assertTrue(options.isCustomAnswerAllowed());
	}

	@Test
	void theOptionsChosenAndTheExplanationReachTheModelOnARespawn() {
		theModelAsks();
		@SuppressWarnings({ "unchecked", "rawtypes" })
		AgentSuspendedWaitingCondition resolved = wait.waited().get(0);
		ClarificationAnswer answer = ClarificationAnswer.options(List.of("2023", "2024"));
		answer.setCustomExplanation("and the forecast");
		resolved.setInstanceValue(answer);

		assertEquals("You asked the user: Which years?\nThe user chose:\n- 2023\n- 2024"
				+ "\nThe user explained: and the forecast", GAbstractAskUserChoiceQuestionTool.answered(resolved));
	}

	@Test
	void theSourceMountsBothToolsOnlyWhenAsked() {
		AskUserChoiceQuestionToolSource source = new AskUserChoiceQuestionToolSource();

		assertTrue(source.isMountedOnlyWhenAsked());
		assertEquals(List.of(AskUserSingleChoiceQuestionTool.TOOL_NAME, AskUserMultipleChoiceQuestionTool.TOOL_NAME),
				source.getFullToolReferences().stream().map(ToolReference::getName).toList());
		assertEquals(List.of(AskUserSingleChoiceQuestionTool.TOOL_NAME, AskUserMultipleChoiceQuestionTool.TOOL_NAME),
				source.getToolCallbacks().stream().map(callback -> callback.getToolDefinition().name()).toList());
		assertFalse(source.getToolCallbacks().isEmpty());
	}
}
