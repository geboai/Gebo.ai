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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import ai.gebo.model.GUserMessage.MsgServerity;
import ai.gebo.system.messages.services.IGSystemMessagesService;
import ai.gebo.system.messages.services.SystemMessagePublication;

/**
 * Pins how the owner of a suspended run is told it waits: a message for that
 * person only, one per kind and block, taken back when the block settles; and
 * that telling never breaks the run.
 */
class WaitingConditionsNotifierTest {
	private final IGSystemMessagesService messages = mock(IGSystemMessagesService.class);
	private final WaitingConditionsNotifier notifier = new WaitingConditionsNotifier(messages);

	private SystemMessagePublication published() {
		final ArgumentCaptor<SystemMessagePublication> told = ArgumentCaptor.forClass(SystemMessagePublication.class);
		verify(messages).publish(told.capture());
		return told.getValue();
	}

	@Test
	void anUnwatchedRunIsToldToItsOwnerOnly() {
		notifier.unwatched("block-1", "anna", "conv-1", List.of("Which year?", "Which country?"));

		final SystemMessagePublication message = published();
		assertEquals(WaitingConditionsNotifier.SOURCE, message.source());
		assertEquals("unwatched:block-1", message.key());
		assertEquals(MsgServerity.info, message.severity());
		assertEquals(List.of("anna"), message.recipients());
		assertTrue(message.dismissible());
		assertTrue(message.detail().contains("conv-1") && message.detail().contains("Which year?; Which country?"),
				message.detail());
	}

	@Test
	void anExpiredConditionThatKeepsWaitingIsAWarning() {
		notifier.expired("block-1", "anna", "conv-1", List.of());

		final SystemMessagePublication message = published();
		assertEquals("expired:block-1", message.key());
		assertEquals(MsgServerity.warn, message.severity());
		assertTrue(message.detail().contains("a condition"), message.detail());
	}

	@Test
	void bothMessagesAreTakenBackWhenTheBlockSettles() {
		notifier.settled("block-1");

		verify(messages).retract(WaitingConditionsNotifier.SOURCE, "unwatched:block-1");
		verify(messages).retract(WaitingConditionsNotifier.SOURCE, "expired:block-1");
	}

	@Test
	void nobodyIsToldWithoutAnOwnerOrAService() {
		notifier.unwatched("block-1", null, "conv-1", List.of("x"));
		notifier.expired("block-1", " ", "conv-1", List.of("x"));
		verify(messages, never()).publish(any());

		final WaitingConditionsNotifier silent = new WaitingConditionsNotifier(null);
		silent.unwatched("block-1", "anna", "conv-1", List.of("x"));
		silent.settled("block-1");
	}

	@Test
	void aFailureToTellNeverBreaksTheRun() {
		when(messages.publish(any())).thenThrow(new IllegalStateException("no mongo"));
		doThrow(new IllegalStateException("no mongo")).when(messages).retract(any(), any());

		notifier.unwatched("block-1", "anna", "conv-1", List.of("x"));
		notifier.settled("block-1");
	}
}
