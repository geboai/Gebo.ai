package ai.gebo.architecture.agents.tools;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.ToolCallback;

import ai.gebo.architecture.agents.model.AgentSuspendedWaitingCondition;
import ai.gebo.architecture.agents.services.GWaitExternalConditionSink;
import ai.gebo.architecture.agents.services.IGWaitExternalCondition;
import ai.gebo.architecture.agents.tools.AskUserClarificationTool.ClarificationAnswer;
import ai.gebo.architecture.agents.tools.AskUserClarificationTool.ClarificationOptions;
import ai.gebo.architecture.agents.tools.AskUserClarificationTool.Param;
import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCalling;

/**
 * Pins the clarification tool: it ends the call it is part of, declares to the
 * loaded wait what the user is asked and among which options, and where nothing
 * is loaded it tells the model to decide by itself.
 */
class AskUserClarificationToolTest {
	private final AskUserClarificationTool tool = new AskUserClarificationTool();
	private final GWaitExternalConditionSink wait = new GWaitExternalConditionSink();

	private ToolContext inACallWith(IGWaitExternalCondition loaded) {
		GeboToolCalling toolCalling = GeboToolCalling.builder().provide(IGWaitExternalCondition.class, loaded).build();
		return new ToolContext(toolCalling.toolsContext(Map.of()));
	}

	private static Param param(String question, List<String> options, Boolean custom) {
		Param param = new Param();
		param.setQuestion(question);
		param.setWhyNeeded("the report differs by year");
		param.setOptions(options);
		param.setCustomAnswerAllowed(custom);
		return param;
	}

	@Test
	void theToolEndsTheCallItIsPartOf() {
		ToolCallback callback = AskUserClarificationTool.declare();

		assertEquals(AskUserClarificationTool.TOOL_NAME, callback.getToolDefinition().name());
		assertTrue(callback.getToolMetadata().returnDirect(), "the loop stops at it: the work waits");
		assertTrue(callback.getToolDefinition().inputSchema().contains("customAnswerAllowed"));
	}

	@Test
	void theQuestionAndTheOptionsAreDeclaredAsTheConditionTheRunWaitsFor() {
		String told = tool.ask(param("Which year?", List.of("2023", "2024"), true), inACallWith(wait));

		assertEquals(AskUserClarificationTool.WAITING, told);
		assertEquals(1, wait.waited().size());
		AgentSuspendedWaitingCondition<?, ?> condition = wait.waited().get(0);
		assertEquals("Which year?", condition.getWaitingConditionStatement());
		assertEquals("the report differs by year", condition.getWaitedConditionInstanceLLMExplanation());
		assertSame(ClarificationAnswer.class, condition.getType(), "what the user answers");
		assertNull(condition.getInstanceValue(), "not answered yet");
		assertSame(ClarificationOptions.class, condition.getOptionsType());
		ClarificationOptions options = (ClarificationOptions) condition.getLlmChosenOptions();
		assertEquals("Which year?", options.getQuestion());
		assertEquals(List.of("2023", "2024"), options.getOptions());
		assertTrue(options.isCustomAnswerAllowed());
	}

	@Test
	void theToolIsCalledThroughItsCallbackAsTheModelCallsIt() {
		AskUserClarificationTool.declare().call(
				"{\"question\":\"Which year?\",\"whyNeeded\":\"it matters\",\"options\":[\" 2023 \",\"2024\",\"\"]}",
				inACallWith(wait));

		assertEquals(1, wait.waited().size());
		ClarificationOptions options = (ClarificationOptions) wait.waited().get(0).getLlmChosenOptions();
		assertEquals(List.of("2023", "2024"), options.getOptions(), "options are trimmed, blank ones dropped");
		assertFalse(options.isCustomAnswerAllowed(), "not allowed unless said");
	}

	@Test
	void whereNothingCanWaitTheModelIsToldToDecideByItself() {
		String told = tool.ask(param("Which year?", List.of("2023", "2024"), null), new ToolContext(Map.of()));

		assertEquals(AskUserClarificationTool.NOT_AVAILABLE, told);
		assertFalse(wait.isWaiting());
	}

	@Test
	void aQuestionWithoutEnoughOptionsIsNotAsked() {
		assertEquals(AskUserClarificationTool.NO_QUESTION, tool.ask(param(" ", List.of("a", "b"), null),
				inACallWith(wait)));
		assertEquals(AskUserClarificationTool.TOO_FEW_OPTIONS, tool.ask(param("Which?", List.of("only one"), false),
				inACallWith(wait)));
		assertEquals(AskUserClarificationTool.TOO_FEW_OPTIONS, tool.ask(param("Which?", null, true),
				inACallWith(wait)));
		assertFalse(wait.isWaiting());

		assertEquals(AskUserClarificationTool.WAITING, tool.ask(param("Which?", List.of("only one"), true),
				inACallWith(wait)), "one option with an explanation of the user's own allowed is a choice");
	}

	@Test
	void theAnswerIsRenderedForTheModelOnARespawn() {
		tool.ask(param("Which year?", List.of("2023", "2024"), true), inACallWith(wait));
		@SuppressWarnings({ "unchecked", "rawtypes" })
		AgentSuspendedWaitingCondition resolved = wait.waited().get(0);

		resolved.setInstanceValue(ClarificationAnswer.option("2024"));
		assertEquals("You asked the user: Which year?\nThe user chose: 2024",
				AskUserClarificationTool.answered(resolved));

		resolved.setInstanceValue(ClarificationAnswer.explanation("the fiscal year, not the calendar one"));
		assertEquals("You asked the user: Which year?\nThe user explained: the fiscal year, not the calendar one",
				AskUserClarificationTool.answered(resolved));

		resolved.setInstanceValue("not a clarification answer");
		assertNull(AskUserClarificationTool.answered(resolved));
	}
}
