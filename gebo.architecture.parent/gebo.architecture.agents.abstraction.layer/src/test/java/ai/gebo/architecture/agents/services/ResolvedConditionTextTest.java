package ai.gebo.architecture.agents.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import ai.gebo.architecture.agents.model.AgentSuspendedWaitingCondition;
import ai.gebo.architecture.agents.model.ClarificationAnswer;

/** Pins what a respawned agent tells its model: every condition it waited for, before its message. */
@SuppressWarnings("rawtypes")
class ResolvedConditionTextTest {
	private static AgentSuspendedWaitingCondition condition(String statement, ClarificationAnswer answer) {
		return new AgentSuspendedWaitingCondition<ClarificationAnswer, Object>(ClarificationAnswer.class, statement,
				"it matters", answer, false);
	}

	@Test
	void everyConditionIsToldBeforeTheMessageADeclinedOneToo() {
		assertEquals("You asked the user: Which year?\nThe user chose: 2024\n\n"
				+ "You were waiting for: Which country?\nThe user chose not to answer: go on without it, saying what you assumed."
				+ "\n\nWhat is new?",
				ResolvedConditionText.prepend(List.of(condition("Which year?", ClarificationAnswer.option("2024")),
						condition("Which country?", null)), "What is new?"));
	}

	@Test
	void withNoConditionTheMessageIsLeftAsItIs() {
		assertEquals("What is new?", ResolvedConditionText.prepend(List.of(), "What is new?"));
		assertEquals("What is new?", ResolvedConditionText.prepend(null, "What is new?"));
	}
}
