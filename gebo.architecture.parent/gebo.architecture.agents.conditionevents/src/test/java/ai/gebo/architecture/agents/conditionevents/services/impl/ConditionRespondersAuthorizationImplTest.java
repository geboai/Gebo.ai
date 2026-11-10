/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.conditionevents.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;

import ai.gebo.architecture.agents.conditionevents.model.ConditionEventCaller;
import ai.gebo.architecture.agents.conditionevents.model.ConditionResponder;
import ai.gebo.architecture.agents.conditionevents.model.ConditionSources;
import ai.gebo.architecture.agents.model.ConditionSource;
import ai.gebo.architecture.agents.model.ConditionSource.Kind;
import ai.gebo.architecture.agents.model.ConditionTarget;
import ai.gebo.llms.chat.abstraction.layer.session.model.GUserChatSession;
import ai.gebo.security.model.UsersGroup;
import ai.gebo.security.services.IGSecurityDirectory;

class ConditionRespondersAuthorizationImplTest {
	private static final String OWNER = "arossi";
	private static final String APPLICATION = "APPLICATION";

	private final IGSecurityDirectory directory = mock(IGSecurityDirectory.class);
	@SuppressWarnings("unchecked")
	private final ObjectProvider<IGSecurityDirectory> provider = mock(ObjectProvider.class);
	private final ConditionRespondersAuthorizationImpl authorization = new ConditionRespondersAuthorizationImpl(
			provider);
	private GUserChatSession session;

	@BeforeEach
	void aConversation() {
		when(provider.getIfAvailable()).thenReturn(directory);
		when(directory.findGroupsOfUser(anyString())).thenReturn(List.of());
		when(directory.findGroupsOfUser("cneri")).thenReturn(List.of(group("finance")));
		session = new GUserChatSession();
		session.setCode("conversation-1");
		session.setUsername(OWNER);
	}

	// ---- a condition with no target: the usual answerers, and anyone with access to the conversation

	@Test
	void withNoTargetTheOwnerAnswers() {
		assertNull(authorization.refusal(null, session, ConditionResponder.person(OWNER, false)));
		assertNull(authorization.refusal(new ConditionTarget(), session, ConditionResponder.person(OWNER, false)),
				"an empty target is no target");
	}

	@Test
	void withNoTargetAnyoneWithAccessToTheConversationAnswers() {
		assertEquals(ConditionRespondersAuthorizationImpl.NO_ACCESS,
				authorization.refusal(null, session, ConditionResponder.person("bverdi", false)));

		session.setAccessibleUsers(List.of("bverdi"));
		assertNull(authorization.refusal(null, session, ConditionResponder.person("bverdi", false)), "shared with them");

		session.setAccessibleGroups(List.of("finance"));
		assertNull(authorization.refusal(null, session, ConditionResponder.person("cneri", false)),
				"shared with a group of theirs");
		assertEquals(ConditionRespondersAuthorizationImpl.NO_ACCESS,
				authorization.refusal(null, session, ConditionResponder.person("dbianchi", false)));

		session.setAccessibleToAll(true);
		assertNull(authorization.refusal(null, session, ConditionResponder.person("dbianchi", false)),
				"shared with everyone");
	}

	@Test
	void withNoTargetTheAccountsDeliveringForOthersAnswerAsBefore() {
		final ConditionEventCaller application = ConditionEventCaller.of("erp-integration", Set.of(APPLICATION),
				ConditionEventCaller.REST);
		assertNull(authorization.refusal(null, session, delivered(application, null, "erp")));

		final ConditionEventCaller channel = ConditionEventCaller.trustedChannel(null, ConditionEventCaller.MESSAGE,
				"erp");
		assertNull(authorization.refusal(null, session, delivered(channel, null, "erp")));

		final ConditionEventCaller stranger = ConditionEventCaller.of("bverdi", Set.of("ROLE_USER"),
				ConditionEventCaller.REST);
		assertEquals(ConditionRespondersAuthorizationImpl.NO_ACCESS,
				authorization.refusal(null, session, delivered(stranger, null, null)));
	}

	// ---- a condition with a target: only who or what it lists, or an administrator

	@Test
	void withATargetOnlyTheUsersListedAnswerNotEvenTheOwner() {
		final ConditionTarget target = ConditionTarget.of(List.of("bverdi"), null, null);

		assertNull(authorization.refusal(target, session, ConditionResponder.person("bverdi", false)));
		assertEquals("The condition is waited from users bverdi only",
				authorization.refusal(target, session, ConditionResponder.person(OWNER, false)));
	}

	@Test
	void withATargetTheMembersOfTheGroupsListedAnswer() {
		final ConditionTarget target = ConditionTarget.of(null, List.of("finance"), null);

		assertNull(authorization.refusal(target, session, ConditionResponder.person("cneri", false)));
		assertFalse(authorization.mayAnswer(target, session, ConditionResponder.person("bverdi", false)));
	}

	@Test
	void withATargetAccessToTheConversationIsNotEnough() {
		session.setAccessibleToAll(true);
		final ConditionTarget target = ConditionTarget.of(List.of("bverdi"), null, null);

		assertFalse(authorization.mayAnswer(target, session, ConditionResponder.person("dbianchi", false)));
	}

	@Test
	void withATargetASystemListedAnswersThroughAnAccountDeliveringForOthers() {
		final ConditionTarget target = ConditionTarget.of(null, null, List.of("erp"));
		final ConditionEventCaller application = ConditionEventCaller.of("erp-integration", Set.of(APPLICATION),
				ConditionEventCaller.REST);
		final ConditionEventCaller channel = ConditionEventCaller.trustedChannel("queue", ConditionEventCaller.MESSAGE,
				"erp");

		assertNull(authorization.refusal(target, session, delivered(application, null, "erp")));
		assertNull(authorization.refusal(target, session, delivered(channel, null, "erp")));
		assertFalse(authorization.mayAnswer(target, session, delivered(application, null, "crm")),
				"a system not listed");
	}

	@Test
	void aUserCannotSpeakForASystemListed() {
		final ConditionTarget target = ConditionTarget.of(null, null, List.of("erp"));
		final ConditionEventCaller user = ConditionEventCaller.of("bverdi", Set.of("ROLE_USER"),
				ConditionEventCaller.REST);

		assertFalse(authorization.mayAnswer(target, session, delivered(user, null, "erp")),
				"verified as themselves, not as the system");
	}

	@Test
	void anAccountDeliveringForOthersAnswersForAUserListed() {
		final ConditionTarget target = ConditionTarget.of(List.of("bverdi"), null, null);
		final ConditionEventCaller application = ConditionEventCaller.of("erp-integration", Set.of(APPLICATION),
				ConditionEventCaller.REST);
		final ConditionSource bverdi = ConditionSource.of(Kind.PERSON, "erp", "bverdi", null);

		assertNull(authorization.refusal(target, session, delivered(application, bverdi, "erp")));
	}

	@Test
	void anUnverifiedOrNonPersonSourceIsNoUserListed() {
		final ConditionTarget target = ConditionTarget.of(List.of("bverdi"), null, null);
		final ConditionEventCaller user = ConditionEventCaller.of("dbianchi", Set.of("ROLE_USER"),
				ConditionEventCaller.REST);
		final ConditionSource claimed = ConditionSource.of(Kind.PERSON, null, "bverdi", null);
		assertFalse(authorization.mayAnswer(target, session, delivered(user, claimed, null)),
				"claiming to be another user");

		final ConditionEventCaller application = ConditionEventCaller.of("erp-integration", Set.of(APPLICATION),
				ConditionEventCaller.REST);
		final ConditionSource agent = ConditionSource.of(Kind.AGENT, "erp", "bverdi", null);
		assertFalse(authorization.mayAnswer(target, session, delivered(application, agent, "erp")),
				"an agent named as the user");
	}

	// ---- whoever

	@Test
	void anAdministratorAlwaysAnswers() {
		final ConditionTarget target = ConditionTarget.of(List.of("bverdi"), List.of("finance"), List.of("erp"));

		assertNull(authorization.refusal(target, session, ConditionResponder.person("admin", true)));
		assertNull(authorization.refusal(null, session, ConditionResponder.person("admin", true)));
		final ConditionEventCaller administrator = ConditionEventCaller.of("admin", Set.of("ROLE_USER", "ROLE_ADMIN"),
				ConditionEventCaller.REST);
		assertTrue(authorization.mayAnswer(target, session, delivered(administrator, null, null)));
	}

	@Test
	void nobodyBehindTheAnswerIsRefused() {
		assertEquals(ConditionRespondersAuthorizationImpl.NOBODY, authorization.refusal(null, session, null));
		assertEquals(ConditionRespondersAuthorizationImpl.NOBODY,
				authorization.refusal(null, session, ConditionResponder.person(" ", false)));
	}

	@Test
	void groupsThatCannotBeReadCountForNothing() {
		when(directory.findGroupsOfUser("cneri")).thenThrow(new IllegalStateException("directory down"));
		final ConditionTarget target = ConditionTarget.of(null, List.of("finance"), null);

		assertFalse(authorization.mayAnswer(target, session, ConditionResponder.person("cneri", false)));
		when(provider.getIfAvailable()).thenReturn(null);
		assertFalse(authorization.mayAnswer(target, session, ConditionResponder.person("cneri", false)),
				"no directory");
	}

	private static ConditionResponder delivered(ConditionEventCaller caller, ConditionSource claimed,
			String sourceSystem) {
		return ConditionResponder.of(caller,
				ConditionSources.ofDelivered(claimed, sourceSystem, caller, APPLICATION), APPLICATION);
	}

	private static UsersGroup group(String code) {
		final UsersGroup group = new UsersGroup();
		group.setCode(code);
		return group;
	}
}
