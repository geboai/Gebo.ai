/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.abstraction.layer.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Pins the identity a chat's background jobs (its state shrunk, its minimal context
 * prepared) run with: the user the chat belongs to, as the user details service
 * resolves them, so their model calls are accounted to them, on a thread that carries no
 * identity.
 */
class SessionShrinkIdentityTest {

	private static final UserDetails PAOLO = User.withUsername("paolo@example.org").password("unused")
			.authorities("ADMIN_ROLE", "USER_ROLE").build();

	/** The users the service resolves: only paolo. */
	private static final UserDetailsService USERS = name -> {
		if (PAOLO.getUsername().equals(name)) {
			return PAOLO;
		}
		throw new UsernameNotFoundException(name);
	};

	@AfterEach
	void noIdentityLeft() {
		SecurityContextHolder.clearContext();
	}

	private static SessionShrinkRequestPayload job(String user) {
		final SessionShrinkRequestPayload payload = new SessionShrinkRequestPayload();
		payload.setUserChatSessionCode("chat-1");
		payload.setUsername(user);
		return payload;
	}

	@Test
	void aChatsJobRunsAsItsUserWithTheirOwnAuthoritiesAndLeavesNoIdentityBehind() throws Exception {
		final AtomicReference<Authentication> inside = new AtomicReference<>();

		SessionShrinkMessagesReceiver.asChatUser(job("paolo@example.org"), USERS,
				() -> inside.set(SecurityContextHolder.getContext().getAuthentication()));

		assertEquals("paolo@example.org", inside.get().getName());
		assertSame(PAOLO, inside.get().getPrincipal(), "the resolved user, as at login");
		assertTrue(inside.get().getAuthorities().stream().anyMatch(a -> "ADMIN_ROLE".equals(a.getAuthority())));
		assertNull(SecurityContextHolder.getContext().getAuthentication(), "the thread's own (none) restored");
	}

	@Test
	void aJobWithoutItsUserOrWithAnUnknownOneRunsWithoutIdentity() throws Exception {
		final List<Authentication> inside = new ArrayList<>();

		SessionShrinkMessagesReceiver.asChatUser(job(null), USERS,
				() -> inside.add(SecurityContextHolder.getContext().getAuthentication()));
		SessionShrinkMessagesReceiver.asChatUser(job("someone@example.org"), USERS,
				() -> inside.add(SecurityContextHolder.getContext().getAuthentication()));
		SessionShrinkMessagesReceiver.asChatUser(job("paolo@example.org"), null,
				() -> inside.add(SecurityContextHolder.getContext().getAuthentication()));

		assertEquals(3, inside.size(), "the job runs anyway");
		inside.forEach(a -> assertNull(a));
	}
}
