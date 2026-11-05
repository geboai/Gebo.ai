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
import org.springframework.ai.util.json.JsonParser;

import ai.gebo.architecture.agents.model.AgentSuspendedWaitingCondition;
import ai.gebo.architecture.agents.services.GWaitExternalConditionSink;
import ai.gebo.architecture.agents.services.IGWaitExternalCondition;
import ai.gebo.architecture.agents.model.ClarificationAnswer;
import ai.gebo.architecture.agents.model.ClarificationOption;
import ai.gebo.architecture.agents.model.ClarificationOptions;
import ai.gebo.architecture.agents.tools.GAbstractAskUserChoiceQuestionTool.Param;
import ai.gebo.llms.abstraction.layer.services.ToolCallsListener;
import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCalling;

/**
 * Pins the single choice question tool: it ends the call it is part of, declares to the
 * loaded wait what the user is asked and among which options, and where nothing
 * is loaded it tells the model to decide by itself.
 */
class AskUserSingleChoiceQuestionToolTest {
	private final AskUserSingleChoiceQuestionTool tool = new AskUserSingleChoiceQuestionTool();
	private final GWaitExternalConditionSink wait = new GWaitExternalConditionSink();

	private ToolContext inACallWith(IGWaitExternalCondition loaded) {
		GeboToolCalling toolCalling = GeboToolCalling.builder().provide(IGWaitExternalCondition.class, loaded).build();
		return new ToolContext(toolCalling.toolsContext(Map.of()));
	}

	private static Param param(String question, List<String> options, Boolean custom) {
		return param(question, options, custom, null);
	}

	private static Param param(String question, List<String> options, Boolean custom, Boolean mandatory) {
		Param param = new Param();
		param.setMandatory(mandatory);
		param.setQuestion(question);
		param.setWhyNeeded("the report differs by year");
		param.setOptions(options != null ? options.stream().map(ClarificationOption::of).toList() : null);
		param.setCustomAnswerAllowed(custom);
		return param;
	}

	@Test
	void theToolEndsTheCallItIsPartOf() {
		ToolCallback callback = AskUserSingleChoiceQuestionTool.declare();

		assertEquals(AskUserSingleChoiceQuestionTool.TOOL_NAME, callback.getToolDefinition().name());
		assertTrue(callback.getToolMetadata().returnDirect(), "the loop stops at it: the work waits");
		assertTrue(callback.getToolDefinition().inputSchema().contains("customAnswerAllowed"));
		assertTrue(callback.getToolDefinition().inputSchema().contains("mandatory"));
		assertTrue(callback.getToolDefinition().inputSchema().contains("label"));
		assertTrue(callback.getToolDefinition().inputSchema().contains("description"));
	}

	@Test
	void theQuestionAndTheOptionsAreDeclaredAsTheConditionTheRunWaitsFor() {
		String told = tool.ask(param("Which year?", List.of("2023", "2024"), true), inACallWith(wait));

		assertEquals(AskUserSingleChoiceQuestionTool.WAITING, told);
		assertEquals(1, wait.waited().size());
		AgentSuspendedWaitingCondition<?, ?> condition = wait.waited().get(0);
		assertEquals("Which year?", condition.getWaitingConditionStatement());
		assertEquals("the report differs by year", condition.getWaitedConditionInstanceLLMExplanation());
		assertSame(ClarificationAnswer.class, condition.getType(), "what the user answers");
		assertNull(condition.getInstanceValue(), "not answered yet");
		assertSame(ClarificationOptions.class, condition.getOptionsType());
		ClarificationOptions options = (ClarificationOptions) condition.getLlmChosenOptions();
		assertEquals("Which year?", options.getQuestion());
		assertEquals(List.of("2023", "2024"), options.labels());
		assertTrue(options.isCustomAnswerAllowed());
		assertFalse(options.isMultipleChoice(), "the user picks one");
		assertTrue(condition.isMandatory(), "mandatory unless the model says otherwise");
	}

	@Test
	void theModelMaySayTheUserCanLeaveTheQuestionUnanswered() {
		tool.ask(param("Which year?", List.of("2023", "2024"), false, false), inACallWith(wait));

		assertFalse(wait.waited().get(0).isMandatory());
	}

	@Test
	void theToolIsCalledThroughItsCallbackAsTheModelCallsIt() {
		AskUserSingleChoiceQuestionTool.declare().call(
				"{\"question\":\"Which year?\",\"whyNeeded\":\"it matters\",\"options\":[{\"label\":\" 2023 \",\"description\":\" the closed year \"},{\"label\":\"2024\",\"description\":\" \"},{\"label\":\"\"}]}",
				inACallWith(wait));

		assertEquals(1, wait.waited().size());
		ClarificationOptions options = (ClarificationOptions) wait.waited().get(0).getLlmChosenOptions();
		assertEquals(List.of("2023", "2024"), options.labels(), "options are trimmed, blank ones dropped");
		assertEquals("the closed year", options.getOptions().get(0).getDescription());
		assertNull(options.getOptions().get(1).getDescription(), "a blank description is no description");
		assertFalse(options.isCustomAnswerAllowed(), "not allowed unless said");
	}

	@Test
	void whereNothingCanWaitTheModelIsToldToDecideByItself() {
		String told = tool.ask(param("Which year?", List.of("2023", "2024"), null), new ToolContext(Map.of()));

		assertEquals(AskUserSingleChoiceQuestionTool.NOT_AVAILABLE, told);
		assertFalse(wait.isWaiting());
	}

	@Test
	void aQuestionWithoutEnoughOptionsIsNotAsked() {
		assertEquals(AskUserSingleChoiceQuestionTool.NO_QUESTION, tool.ask(param(" ", List.of("a", "b"), null),
				inACallWith(wait)));
		assertEquals(AskUserSingleChoiceQuestionTool.TOO_FEW_OPTIONS, tool.ask(param("Which?", List.of("only one"), false),
				inACallWith(wait)));
		assertEquals(AskUserSingleChoiceQuestionTool.TOO_FEW_OPTIONS, tool.ask(param("Which?", null, true),
				inACallWith(wait)));
		assertFalse(wait.isWaiting());

		assertEquals(AskUserSingleChoiceQuestionTool.WAITING, tool.ask(param("Which?", List.of("only one"), true),
				inACallWith(wait)), "one option with an explanation of the user's own allowed is a choice");
	}

	@Test
	void theAnswerIsRenderedForTheModelOnARespawn() {
		tool.ask(param("Which year?", List.of("2023", "2024"), true), inACallWith(wait));
		@SuppressWarnings({ "unchecked", "rawtypes" })
		AgentSuspendedWaitingCondition resolved = wait.waited().get(0);

		resolved.setInstanceValue(ClarificationAnswer.option("2024"));
		assertEquals("You asked the user: Which year?\nThe user chose: 2024",
				AskUserSingleChoiceQuestionTool.answered(resolved));

		resolved.setInstanceValue(ClarificationAnswer.explanation("the fiscal year, not the calendar one"));
		assertEquals("You asked the user: Which year?\nThe user explained: the fiscal year, not the calendar one",
				AskUserSingleChoiceQuestionTool.answered(resolved));

		resolved.setInstanceValue("not a clarification answer");
		assertNull(AskUserSingleChoiceQuestionTool.answered(resolved));
	}

	@Test
	void whatTheModelNotesForLaterIsSavedOnTheCondition() {
		Param param = param("Which year?", List.of("2023", "2024"), false);
		param.setNotesForLater("  the report needs the closed year ");

		tool.ask(param, inACallWith(wait));

		assertEquals("the report needs the closed year", wait.waited().get(0).getNotesForLater());
	}

	@Test
	void blankNotesAreNoNotes() {
		Param param = param("Which year?", List.of("2023", "2024"), false);
		param.setNotesForLater("   ");

		tool.ask(param, inACallWith(wait));

		assertNull(wait.waited().get(0).getNotesForLater());
	}

	@Test
	@SuppressWarnings("unchecked")
	void theNotesAreAParameterTheModelMayLeaveOut() {
		Map<String, Object> schema = JsonParser
				.fromJson(AskUserSingleChoiceQuestionTool.declare().getToolDefinition().inputSchema(), Map.class);

		assertTrue(((Map<String, Object>) schema.get("properties")).containsKey(ToolCallsListener.NOTES_FOR_LATER),
				"the parameter is the one kept out of the call as the user sees it");
		assertFalse(((List<String>) schema.get("required")).contains("notesForLater"));
		assertTrue(((List<String>) schema.get("required")).contains("question"));
	}

	@Test
	void bothToolsTellTheModelTheyStopItsWorkAndWhereToKeepWhatItNeeds() {
		for (String description : List.of(AskUserSingleChoiceQuestionTool.DESCRIPTION,
				AskUserMultipleChoiceQuestionTool.DESCRIPTION)) {
			assertTrue(description.contains("You will not see what this tool returns"), description);
			assertTrue(description.contains("executed again from the start on the same message"), description);
			assertTrue(description.contains("notesForLater"), description);
			assertTrue(description.contains("the very values and results"), description);
		}
	}
}
