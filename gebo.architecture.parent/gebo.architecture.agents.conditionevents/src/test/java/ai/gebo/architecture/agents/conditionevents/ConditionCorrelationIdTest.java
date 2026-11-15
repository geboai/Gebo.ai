package ai.gebo.architecture.agents.conditionevents;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Pins the two forms of the correlation of an event to its condition. */
class ConditionCorrelationIdTest {
	private static final String CONDITION = "3f2b8a52-7c1e-4d0b-9a6e-1b2c3d4e5f60";

	@Test
	void theMergedFormIsTheSessionCodeAndTheConditionId() {
		final ConditionCorrelationId id = new ConditionCorrelationId("session-1", CONDITION);

		assertEquals("session-1:" + CONDITION, id.composed());
		assertEquals(id, ConditionCorrelationId.parse(id.composed()));
	}

	@Test
	void theMergedFormIsSplitAtItsLastSeparatorSoASessionCodeMayHoldOne() {
		final ConditionCorrelationId id = ConditionCorrelationId.parse("tenant:session-1:" + CONDITION);

		assertEquals("tenant:session-1", id.sessionCode());
		assertEquals(CONDITION, id.conditionId());
	}

	@Test
	void eitherFormOrBothNameTheCondition() {
		final ConditionCorrelationId expected = new ConditionCorrelationId("session-1", CONDITION);

		assertEquals(expected, ConditionCorrelationId.of("session-1:" + CONDITION, null, null));
		assertEquals(expected, ConditionCorrelationId.of(null, "session-1", CONDITION));
		assertEquals(expected, ConditionCorrelationId.of("session-1:" + CONDITION, "session-1", CONDITION));
	}

	@Test
	void formsNamingDifferentConditionsOrNothingAreRefused() {
		final IllegalArgumentException disagree = assertThrows(IllegalArgumentException.class,
				() -> ConditionCorrelationId.of("session-1:" + CONDITION, "session-2", CONDITION));
		assertTrue(disagree.getMessage().startsWith(ConditionCorrelationId.FORMS_DISAGREE));
		assertThrows(IllegalArgumentException.class, () -> ConditionCorrelationId.of(null, "session-1", null));
		assertThrows(IllegalArgumentException.class, () -> ConditionCorrelationId.parse("no-separator"));
		assertThrows(IllegalArgumentException.class, () -> ConditionCorrelationId.parse("session-1:"));
		assertThrows(IllegalArgumentException.class, () -> ConditionCorrelationId.parse(":" + CONDITION));
	}
}
