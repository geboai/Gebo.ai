package ai.gebo.architecture.agents.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import ai.gebo.architecture.agents.model.AgentSuspendedWaitingCondition;
import ai.gebo.architecture.agents.model.ClarificationAnswer;
import ai.gebo.architecture.agents.model.ConditionSource;
import ai.gebo.architecture.agents.model.ConditionSource.Kind;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension.WaitingCondition;

/**
 * Pins what the agents are told of who solved a condition: today's words for
 * the conversation's own user or when nobody said, the source for any other.
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
class ResolvedConditionSourceTextTest {

	private static ConditionSource anna(boolean verified) {
		final ConditionSource source = ConditionSource.of(Kind.PERSON, "ERP", "arossi", "Anna Rossi");
		source.setChannel("REST");
		source.setVerified(verified);
		return source;
	}

	private static ConditionSource chatUser() {
		final ConditionSource source = ConditionSource.of(Kind.PERSON, "CHAT", "paolo", null);
		source.setChannel(ConditionSource.CHAT_CHANNEL);
		source.setVerified(true);
		return source;
	}

	private static AgentSuspendedWaitingCondition question(ClarificationAnswer answer, ConditionSource by) {
		final AgentSuspendedWaitingCondition condition = new AgentSuspendedWaitingCondition<ClarificationAnswer, Object>(
				ClarificationAnswer.class, "Which year?", "it matters", answer, false);
		condition.setSolvedBy(by);
		return condition;
	}

	private static AgentSuspendedWaitingCondition approval(Object value, ConditionSource by) {
		final AgentSuspendedWaitingCondition condition = new AgentSuspendedWaitingCondition<Object, Object>(Object.class,
				"The approval", "it matters", value, false);
		condition.setSolvedBy(by);
		return condition;
	}

	@Test
	void theConversationsUserAndNoSourceAreToldAsAlways() {
		for (ConditionSource by : new ConditionSource[] { null, chatUser() }) {
			assertEquals("You asked the user: Which year?\nThe user chose: 2024",
					ResolvedConditionText.render(question(ClarificationAnswer.option("2024"), by)));
			assertEquals("You were waiting for: The approval\nIt came: yes",
					ResolvedConditionText.render(approval("yes", by)));
			assertEquals("You were waiting for: The approval\nThe user chose not to answer: go on without it,"
					+ " saying what you assumed.", ResolvedConditionText.render(approval(null, by)));
		}
	}

	@Test
	void anotherSourceIsNamed() {
		assertEquals("You asked: Which year?\nAnna Rossi (ERP) chose: 2024",
				ResolvedConditionText.render(question(ClarificationAnswer.option("2024"), anna(true))));
		assertEquals("You were waiting for: The approval\nIt came from Anna Rossi (ERP): yes",
				ResolvedConditionText.render(approval("yes", anna(true))));
		assertEquals("You were waiting for: The approval\nAnna Rossi (ERP) chose not to answer: go on without it,"
				+ " saying what you assumed.", ResolvedConditionText.render(approval(null, anna(true))));
	}

	@Test
	void anUnverifiedSourceOrAnAgentIsSaidToBe() {
		assertEquals("You were waiting for: The approval\nIt came from Anna Rossi (ERP), unverified: yes",
				ResolvedConditionText.render(approval("yes", anna(false))));
		final ConditionSource agent = ConditionSource.of(Kind.AGENT, "ACCOUNTING", "invoice-checker", null);
		agent.setVerified(true);
		assertEquals("You asked: Which year?\nAgent invoice-checker (ACCOUNTING) chose: 2024",
				ResolvedConditionText.render(question(ClarificationAnswer.option("2024"), agent)));
	}

	@Test
	void anIdIsToldAsItIs() {
		final ConditionSource account = ConditionSource.of(Kind.UNKNOWN, "ERP", "testuser@gebo.ai", null);
		account.setVerified(true);
		assertEquals("You asked: Which year?\ntestuser@gebo.ai (ERP) chose: 2024",
				ResolvedConditionText.render(question(ClarificationAnswer.option("2024"), account)));
		assertEquals("You were waiting for: The approval\nAn unknown source, unverified chose not to answer: go on"
				+ " without it, saying what you assumed.",
				ResolvedConditionText.render(approval(null, new ConditionSource())));
	}

	@Test
	void theOtherAgentsReadTheSourceToo() {
		assertEquals("Already settled, do not ask for it again. Agent writer waited for: The approval"
				+ "\nWhy it was needed: it matters\nIt came from Anna Rossi (ERP): yes",
				ResolvedConditionText.renderForPeers("writer", approval("yes", anna(true))));
		assertEquals("Already settled, do not ask for it again. Agent writer waited for: Which year?"
				+ "\nWhy it was needed: it matters\nThe user chose: 2024",
				ResolvedConditionText.renderForPeers("writer", question(ClarificationAnswer.option("2024"), chatUser())));
	}

	@Test
	void theBlockKeepsWhoSolvedACondition() {
		final AgentSuspendedWaitingCondition<?, ?> back = WaitingCondition.of(approval("yes", anna(true))).toCondition();
		assertEquals("arossi", back.getSolvedBy().getId());
		assertEquals("It came from Anna Rossi (ERP): yes",
				ResolvedConditionText.render(back).substring("You were waiting for: The approval\n".length()));
		assertNull(WaitingCondition.of(approval("yes", null)).toCondition().getSolvedBy());
	}
}
