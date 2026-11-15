package ai.gebo.architecture.agents.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ai.gebo.architecture.agents.model.ConditionSource.Kind;

/** Pins how a source is named to people and agents, and when it is the conversation's own user. */
class ConditionSourceTest {

	private static ConditionSource verified(ConditionSource source) {
		source.setVerified(true);
		return source;
	}

	@Test
	void aSourceIsNamedByItsShownNameThenItsIdThenItsSystem() {
		assertEquals("Anna Rossi (ERP)", verified(ConditionSource.of(Kind.PERSON, "ERP", "arossi", "Anna Rossi")).label());
		assertEquals("arossi (ERP)", verified(ConditionSource.of(Kind.PERSON, "ERP", "arossi", " ")).label());
		assertEquals("WORKFLOW", verified(ConditionSource.of(Kind.SYSTEM, "WORKFLOW", null, null)).label(),
				"the system is not repeated");
	}

	@Test
	void anAgentIsSaidToBeOne() {
		assertEquals("agent invoice-checker (ACCOUNTING)",
				verified(ConditionSource.of(Kind.AGENT, "ACCOUNTING", "invoice-checker", null)).label());
	}

	@Test
	void anUnverifiedSourceIsSaidToBe() {
		assertEquals("Anna Rossi (ERP), unverified", ConditionSource.of(Kind.PERSON, "ERP", "arossi", "Anna Rossi").label());
		assertEquals("an unknown source, unverified", new ConditionSource().label());
	}

	@Test
	void onlyAVerifiedPersonInTheChatIsTheConversationsUser() {
		final ConditionSource chat = verified(ConditionSource.of(Kind.PERSON, "GEBO-CHAT", "paolo", null));
		chat.setChannel(ConditionSource.CHAT_CHANNEL);
		assertTrue(chat.isConversationUser());

		final ConditionSource rest = verified(ConditionSource.of(Kind.PERSON, "ERP", "paolo", null));
		rest.setChannel("REST");
		assertFalse(rest.isConversationUser());

		final ConditionSource claimed = ConditionSource.of(Kind.PERSON, "GEBO-CHAT", "paolo", null);
		claimed.setChannel(ConditionSource.CHAT_CHANNEL);
		assertFalse(claimed.isConversationUser(), "not verified");
	}
}
