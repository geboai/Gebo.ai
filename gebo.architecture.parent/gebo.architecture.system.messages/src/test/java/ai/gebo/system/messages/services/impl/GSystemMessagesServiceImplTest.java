/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.system.messages.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ai.gebo.model.GUserMessage.MsgServerity;
import ai.gebo.system.messages.model.GSystemMessage;
import ai.gebo.system.messages.model.GSystemMessageDismissal;
import ai.gebo.system.messages.model.SystemMessageAudience;
import ai.gebo.system.messages.repositories.SystemMessageDismissalRepository;
import ai.gebo.system.messages.repositories.SystemMessageRepository;
import ai.gebo.system.messages.services.SystemMessagePublication;

/**
 * Covers the role routing, expiry, dismissal and revision rules of
 * {@link GSystemMessagesServiceImpl}, over in-memory repositories.
 */
class GSystemMessagesServiceImplTest {
	private static final Instant NOW = Instant.parse("2026-09-27T10:00:00Z");

	private final Map<String, GSystemMessage> messages = new LinkedHashMap<>();
	private final Map<String, GSystemMessageDismissal> dismissals = new LinkedHashMap<>();
	private GSystemMessagesServiceImpl service;

	@SuppressWarnings("unchecked")
	@BeforeEach
	void setUp() {
		SystemMessageRepository messageRepository = mock(SystemMessageRepository.class);
		when(messageRepository.findById(anyString()))
				.thenAnswer(i -> Optional.ofNullable(messages.get(i.getArgument(0, String.class))));
		when(messageRepository.save(any(GSystemMessage.class))).thenAnswer(i -> {
			GSystemMessage m = i.getArgument(0);
			messages.put(m.getId(), m);
			return m;
		});
		when(messageRepository.findByAudienceIn(anyCollection())).thenAnswer(i -> {
			Collection<SystemMessageAudience> audiences = i.getArgument(0);
			return messages.values().stream().filter(m -> audiences.contains(m.getAudience())).toList();
		});
		when(messageRepository.findAll()).thenAnswer(i -> List.copyOf(messages.values()));
		doAnswer(i -> messages.remove(i.getArgument(0, String.class))).when(messageRepository)
				.deleteById(anyString());

		SystemMessageDismissalRepository dismissalRepository = mock(SystemMessageDismissalRepository.class);
		when(dismissalRepository.save(any(GSystemMessageDismissal.class))).thenAnswer(i -> {
			GSystemMessageDismissal d = i.getArgument(0);
			dismissals.put(d.getId(), d);
			return d;
		});
		when(dismissalRepository.findByUsername(anyString())).thenAnswer(i -> dismissals.values().stream()
				.filter(d -> d.getUsername().equals(i.getArgument(0))).toList());
		doAnswer(i -> dismissals.values().removeIf(d -> d.getMessageId().equals(i.getArgument(0))))
				.when(dismissalRepository).deleteByMessageId(anyString());

		service = new GSystemMessagesServiceImpl(messageRepository, dismissalRepository,
				Clock.fixed(NOW, ZoneOffset.UTC));
	}

	private static SystemMessagePublication publication(String key, SystemMessageAudience audience,
			MsgServerity severity, String summary, boolean dismissible, Date expiresAt) {
		return new SystemMessagePublication("test", key, severity, summary, null, audience, dismissible, expiresAt);
	}

	private static List<String> keys(List<GSystemMessage> list) {
		return list.stream().map(GSystemMessage::getKey).toList();
	}

	@Test
	void administratorsAndUsersSeeTheMessagesRoutedToTheirRole() {
		service.publish(publication("for-admins", SystemMessageAudience.ADMINS, MsgServerity.info, "a", true, null));
		service.publish(publication("for-users", SystemMessageAudience.USERS, MsgServerity.info, "u", true, null));
		service.publish(publication("for-all", SystemMessageAudience.ALL, MsgServerity.info, "e", true, null));

		assertEquals(List.of("for-admins", "for-all").stream().sorted().toList(),
				keys(service.findVisibleFor("admin", true)).stream().sorted().toList());
		assertEquals(List.of("for-all", "for-users").stream().sorted().toList(),
				keys(service.findVisibleFor("user", false)).stream().sorted().toList());
	}

	@Test
	void theMostSevereMessagesComeFirst() {
		service.publish(publication("info", SystemMessageAudience.ALL, MsgServerity.info, "i", true, null));
		service.publish(publication("error", SystemMessageAudience.ALL, MsgServerity.error, "e", true, null));
		service.publish(publication("warn", SystemMessageAudience.ALL, MsgServerity.warn, "w", true, null));

		assertEquals(List.of("error", "warn", "info"), keys(service.findVisibleFor("user", false)));
	}

	@Test
	void expiredMessagesAreNotShown() {
		service.publish(publication("expired", SystemMessageAudience.ALL, MsgServerity.info, "x", true,
				Date.from(NOW.minusSeconds(1))));
		service.publish(publication("current", SystemMessageAudience.ALL, MsgServerity.info, "c", true,
				Date.from(NOW.plusSeconds(3600))));

		assertEquals(List.of("current"), keys(service.findVisibleFor("user", false)));
	}

	@Test
	void republishingTheSameContentKeepsTheRevisionAndAChangeBumpsIt() {
		assertEquals(1, service.publish(publication("s", SystemMessageAudience.ALL, MsgServerity.warn, "10 days",
				true, null)).getRevision());
		assertEquals(1, service.publish(publication("s", SystemMessageAudience.ALL, MsgServerity.warn, "10 days",
				true, null)).getRevision());
		assertEquals(2, service.publish(publication("s", SystemMessageAudience.ALL, MsgServerity.warn, "9 days",
				true, null)).getRevision());
	}

	@Test
	void aDismissedMessageStaysHiddenUntilItsContentChanges() {
		GSystemMessage m = service.publish(publication("s", SystemMessageAudience.ALL, MsgServerity.warn, "10 days",
				true, null));

		assertTrue(service.dismiss("user", false, m.getId()));
		assertTrue(service.findVisibleFor("user", false).isEmpty());
		assertEquals(1, service.findVisibleFor("other", false).size());

		service.publish(publication("s", SystemMessageAudience.ALL, MsgServerity.warn, "10 days", true, null));
		assertTrue(service.findVisibleFor("user", false).isEmpty());

		service.publish(publication("s", SystemMessageAudience.ALL, MsgServerity.warn, "9 days", true, null));
		assertEquals(List.of("s"), keys(service.findVisibleFor("user", false)));
	}

	@Test
	void nonDismissibleMessagesAndMessagesForAnotherRoleCannotBeDismissed() {
		GSystemMessage sticky = service.publish(publication("sticky", SystemMessageAudience.ALL, MsgServerity.error,
				"stays", false, null));
		GSystemMessage forAdmins = service.publish(publication("admins", SystemMessageAudience.ADMINS,
				MsgServerity.info, "admins", true, null));

		assertFalse(service.dismiss("user", false, sticky.getId()));
		assertFalse(service.dismiss("user", false, forAdmins.getId()));
		assertFalse(service.dismiss("user", false, "test:missing"));
		assertEquals(List.of("sticky"), keys(service.findVisibleFor("user", false)));
	}

	@Test
	void retractingRemovesTheMessageAndItsDismissals() {
		GSystemMessage m = service.publish(publication("s", SystemMessageAudience.ALL, MsgServerity.info, "x", true,
				null));
		service.dismiss("user", false, m.getId());

		service.retract("test", "s");

		assertTrue(service.findAll().isEmpty());
		assertTrue(dismissals.isEmpty());
	}

	@Test
	void incompletePublicationsAreRefused() {
		assertThrows(IllegalArgumentException.class, () -> service
				.publish(new SystemMessagePublication("test", "k", MsgServerity.info, " ", null,
						SystemMessageAudience.ALL, true, null)));
		assertThrows(IllegalArgumentException.class, () -> service.publish(
				new SystemMessagePublication("test", "k", MsgServerity.info, "s", null, null, true, null)));
	}
}
