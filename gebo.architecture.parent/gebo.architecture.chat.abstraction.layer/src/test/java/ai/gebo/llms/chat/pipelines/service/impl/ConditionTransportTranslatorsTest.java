package ai.gebo.llms.chat.pipelines.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import ai.gebo.architecture.agents.model.AgentSuspendedWaitingCondition;
import ai.gebo.architecture.agents.tools.AskUserClarificationTool.ClarificationAnswer;
import ai.gebo.architecture.agents.tools.AskUserClarificationTool.ClarificationOptions;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.AgentSuspendedWaitingConditionDto;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.AgentSuspendedWaitingConditionReactionDto;
import ai.gebo.llms.chat.pipelines.service.IAgentSuspendedWaitingConditionTransportTranslator;
import ai.gebo.llms.chat.pipelines.service.defaultsteps.impl.AgentSuspendedWaitingConditionTransportTranslatorRepositoryPatternImpl;

/**
 * Pins how a waiting condition travels to the client and how the reaction fills
 * the saved condition back: the clarification the agents ask, any other
 * condition through the default translator, and the repository preferring the
 * translator made for the kind of condition.
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
class ConditionTransportTranslatorsTest {
	private final ClarificationConditionTransportTranslator clarification = new ClarificationConditionTransportTranslator();
	private final DefaultConditionTransportTranslator fallback = new DefaultConditionTransportTranslator();
	/** As Spring orders them: the fallback first, so the one declared after it wins. */
	private final AgentSuspendedWaitingConditionTransportTranslatorRepositoryPatternImpl repository = new AgentSuspendedWaitingConditionTransportTranslatorRepositoryPatternImpl(
			List.of(fallback, clarification));

	private static AgentSuspendedWaitingCondition<ClarificationAnswer, ClarificationOptions> clarificationCondition(
			boolean customAllowed, boolean mandatory) {
		ClarificationOptions options = new ClarificationOptions();
		options.setQuestion("Which year?");
		options.getOptions().addAll(List.of("2023", "2024"));
		options.setCustomAnswerAllowed(customAllowed);
		return new AgentSuspendedWaitingCondition<ClarificationAnswer, ClarificationOptions>(ClarificationAnswer.class,
				"Which year?", "the report differs by year", null, mandatory, ClarificationOptions.class, options);
	}

	private static AgentSuspendedWaitingConditionReactionDto reaction(String type, String conditionId,
			Map<String, Object> attributes) {
		AgentSuspendedWaitingConditionReactionDto reaction = new AgentSuspendedWaitingConditionReactionDto();
		reaction.setConditionType(type);
		reaction.setConditionId(conditionId);
		reaction.setAttributes(new LinkedHashMap<String, Object>(attributes));
		return reaction;
	}

	@Test
	void theRepositoryPrefersTheTranslatorMadeForTheKindOfCondition() {
		AgentSuspendedWaitingCondition<String, Object> plain = new AgentSuspendedWaitingCondition<String, Object>(
				String.class, "the invoice number", "needed to look it up", null, true);

		assertSame(clarification, repository.findTranslator(clarificationCondition(true, true)));
		assertSame(fallback, repository.findTranslator(plain));
		assertSame(clarification, repository.findReactionTranslator(reaction("clarification", "c", Map.of())));
		assertSame(fallback, repository.findReactionTranslator(reaction("default", "c", Map.of())));
		assertNull(repository.findReactionTranslator(reaction("unknown", "c", Map.of())));
	}

	@Test
	void aClarificationGoesDownAsTheQuestionItsOptionsAndWhetherTheUserMayExplain() {
		AgentSuspendedWaitingCondition condition = clarificationCondition(true, false);

		AgentSuspendedWaitingConditionDto dto = clarification.translate(condition);

		assertEquals("clarification", dto.getConditionType());
		assertEquals(condition.getSuspendedConditionId(), dto.getConditionId());
		assertEquals("Which year?", dto.getAttributes().get("question"));
		assertEquals("Which year?", dto.getAttributes().get("statement"));
		assertEquals("the report differs by year", dto.getAttributes().get("explanation"));
		assertEquals(List.of("2023", "2024"), dto.getAttributes().get("options"));
		assertEquals(Boolean.TRUE, dto.getAttributes().get("customAnswerAllowed"));
		assertEquals(Boolean.FALSE, dto.getAttributes().get("mandatory"));
	}

	@Test
	void theReactionFillsTheSavedConditionWithTheOptionChosenOrTheExplanationGiven() {
		AgentSuspendedWaitingCondition saved = clarificationCondition(true, true);
		String id = saved.getSuspendedConditionId();

		AgentSuspendedWaitingCondition resolved = clarification.translateReaction(
				reaction("clarification", id, Map.of("chosenOption", " 2024 ")), saved);
		assertSame(saved, resolved, "the saved condition itself, its id untouched");
		assertEquals(id, resolved.getSuspendedConditionId());
		assertEquals("2024", ((ClarificationAnswer) resolved.getInstanceValue()).getChosenOption());
		assertNull(((ClarificationAnswer) resolved.getInstanceValue()).getCustomExplanation());

		clarification.translateReaction(
				reaction("clarification", id, Map.of("customExplanation", "the fiscal year")), saved);
		assertEquals("the fiscal year", ((ClarificationAnswer) saved.getInstanceValue()).getCustomExplanation());
		assertNull(((ClarificationAnswer) saved.getInstanceValue()).getChosenOption());
	}

	@Test
	void aReactionThatDoesNotAnswerTheQuestionAsAskedIsRefused() {
		AgentSuspendedWaitingCondition saved = clarificationCondition(false, true);
		String id = saved.getSuspendedConditionId();

		assertThrows(IllegalArgumentException.class,
				() -> clarification.translateReaction(reaction("clarification", id, Map.of()), saved), "no answer");
		assertThrows(IllegalArgumentException.class, () -> clarification.translateReaction(
				reaction("clarification", id, Map.of("chosenOption", "2025")), saved), "an option not offered");
		assertThrows(IllegalArgumentException.class, () -> clarification.translateReaction(
				reaction("clarification", id, Map.of("customExplanation", "whatever")), saved),
				"an explanation where none is allowed");
		assertThrows(IllegalArgumentException.class, () -> clarification.translateReaction(
				reaction("clarification", "another", Map.of("chosenOption", "2024")), saved),
				"a reaction to another condition");
		assertNull(saved.getInstanceValue(), "nothing was filled");
	}

	@Test
	void aConditionTheUserMayLeaveUnansweredIsDeclinedWithNoValueAMandatoryOneCannotBe() {
		AgentSuspendedWaitingCondition optional = clarificationCondition(true, false);
		AgentSuspendedWaitingCondition resolved = clarification.translateReaction(
				reaction("clarification", optional.getSuspendedConditionId(), Map.of("declined", true)), optional);
		assertSame(optional, resolved);
		assertNull(resolved.getInstanceValue(), "resolved with no value: the agent goes on without it");

		AgentSuspendedWaitingCondition mandatory = clarificationCondition(true, true);
		assertThrows(IllegalArgumentException.class, () -> clarification.translateReaction(
				reaction("clarification", mandatory.getSuspendedConditionId(), Map.of("declined", "true")), mandatory));
	}

	@Test
	void anyOtherConditionGoesDownByItsTypesAndComesBackAsTheValueConvertedToItsType() {
		AgentSuspendedWaitingCondition<Integer, List> saved = new AgentSuspendedWaitingCondition<Integer, List>(
				Integer.class, "how many copies?", "the print run", null, true, List.class, List.of(1, 2, 5));

		AgentSuspendedWaitingConditionDto dto = fallback.translate(saved);
		assertEquals("default", dto.getConditionType());
		assertEquals(Integer.class.getName(), dto.getAttributes().get("valueType"));
		assertEquals(List.class.getName(), dto.getAttributes().get("optionsType"));
		assertEquals(List.of(1, 2, 5), dto.getAttributes().get("llmChosenOptions"));
		assertEquals("how many copies?", dto.getAttributes().get("statement"));
		assertEquals(Boolean.TRUE, dto.getAttributes().get("mandatory"));

		fallback.translateReaction(reaction("default", saved.getSuspendedConditionId(), Map.of("value", "5")), saved);
		assertEquals(Integer.valueOf(5), saved.getInstanceValue(), "a text becomes the integer waited for");
		assertThrows(IllegalArgumentException.class, () -> fallback.translateReaction(
				reaction("default", saved.getSuspendedConditionId(), Map.of("value", "five")), saved));
		assertThrows(IllegalArgumentException.class,
				() -> fallback.translateReaction(reaction("default", saved.getSuspendedConditionId(), Map.of()), saved));
	}

	@Test
	void everyTranslatorAnswersForItsOwnKindOnly() {
		assertTrue(clarification.canTranslate(clarificationCondition(true, true)));
		assertFalse(clarification.canTranslate(new AgentSuspendedWaitingCondition<String, Object>(String.class, "x",
				"y", null, true)));
		assertTrue(fallback.canTranslate(clarificationCondition(true, true)), "the fallback takes anything");
		for (IAgentSuspendedWaitingConditionTransportTranslator translator : List.of(clarification, fallback)) {
			assertTrue(translator.canTranslateReaction(reaction(translator.getId(), "c", Map.of())));
			assertFalse(translator.canTranslateReaction(reaction("other", "c", Map.of())));
		}
	}
}
