package ai.gebo.architecture.agents.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import ai.gebo.architecture.agents.model.AgentSuspendedWaitingCondition;
import ai.gebo.architecture.agents.model.ClarificationAnswer;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension.WaitingCondition;

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

	private static AgentSuspendedWaitingCondition noted(AgentSuspendedWaitingCondition condition) {
		condition.setNotesForLater(" the report needs the closed year ");
		return condition;
	}

	@Test
	void theNotesFollowTheAnswerTheDeclinedConditionAndAnyOtherValue() {
		assertEquals("You asked the user: Which year?\nThe user chose: 2024"
				+ "\nYour notes from before you stopped: the report needs the closed year",
				ResolvedConditionText.render(noted(condition("Which year?", ClarificationAnswer.option("2024")))));
		assertEquals("You were waiting for: Which year?\nThe user chose not to answer: go on without it, saying what you assumed."
				+ "\nYour notes from before you stopped: the report needs the closed year",
				ResolvedConditionText.render(noted(condition("Which year?", null))));
		assertEquals("You were waiting for: The approval\nIt came: yes"
				+ "\nYour notes from before you stopped: the report needs the closed year",
				ResolvedConditionText.render(noted(new AgentSuspendedWaitingCondition<String, Object>(String.class,
						"The approval", "it matters", "yes", true))));
	}

	@Test
	void eachConditionKeepsItsOwnNotes() {
		AgentSuspendedWaitingCondition country = condition("Which country?", ClarificationAnswer.option("Italy"));
		country.setNotesForLater("only the EU figures");

		assertEquals("You asked the user: Which year?\nThe user chose: 2024"
				+ "\nYour notes from before you stopped: the report needs the closed year\n\n"
				+ "You asked the user: Which country?\nThe user chose: Italy"
				+ "\nYour notes from before you stopped: only the EU figures\n\nWhat is new?",
				ResolvedConditionText.prepend(
						List.of(noted(condition("Which year?", ClarificationAnswer.option("2024"))), country), "What is new?"));
	}

	@Test
	void theNotesComeBackFromTheBlock() {
		AgentSuspendedWaitingCondition saved = noted(condition("Which year?", null));

		assertEquals(saved.getNotesForLater(), WaitingCondition.of(saved).toCondition().getNotesForLater());
		assertNull(WaitingCondition.of(condition("Which year?", null)).toCondition().getNotesForLater());
	}

	@Test
	void theOtherAgentsReadWhoWaitedForWhatWhyAndHowItEndedNeverTheNotes() {
		assertEquals("Already settled, do not ask for it again. Agent the-asker waited for: Which year?"
				+ "\nWhy it was needed: it matters\nThe user chose: 2024",
				ResolvedConditionText.renderForPeers("the-asker",
						noted(condition("Which year?", ClarificationAnswer.option("2024")))));
		assertEquals("Already settled, do not ask for it again. Agent the-asker waited for: Which year?"
				+ "\nWhy it was needed: it matters"
				+ "\nThe user chose not to answer: go on without it, saying what you assumed.",
				ResolvedConditionText.renderForPeers("the-asker", noted(condition("Which year?", null))));
		assertEquals("Already settled, do not ask for it again. Agent the-asker waited for: The approval\nIt came: yes",
				ResolvedConditionText.renderForPeers("the-asker", new AgentSuspendedWaitingCondition<String, Object>(
						String.class, "The approval", " ", "yes", true)));
		assertNull(ResolvedConditionText.renderForPeers("the-asker", null));
	}
}
