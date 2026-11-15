/*
 * Copyright (c) 2025+ Gebo.ai
 * Licensed under the Mozilla Public License 2.0 (MPL 2.0) with Data Protection Clauses.
 * See LICENSE file in the project root for full license information.
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 */
package ai.gebo.architecture.agents.conditionevents.messaging;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.ObjectProvider;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.IMessageEnvelopeFactory;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.impl.DefaultMessageEnvelopeFactory;
import ai.gebo.architecture.agents.conditionevents.model.ConditionEvent;

/**
 * The platform's emitter sends each event to the events layer's receiver, on
 * behalf of the account given, and refuses when no receiver is reachable rather
 * than leaving the event to the broker's log.
 */
public class ConditionEventsPublisherTest {
	private final IGMessageBroker broker = mock(IGMessageBroker.class);
	private final ConditionEventsPublisher publisher = new ConditionEventsPublisher(providerOf(broker),
			providerOf(new DefaultMessageEnvelopeFactory()));

	@SuppressWarnings("unchecked")
	private static <T> ObjectProvider<T> providerOf(T bean) {
		final ObjectProvider<T> provider = mock(ObjectProvider.class);
		when(provider.getObject()).thenReturn(bean);
		return provider;
	}

	private static ConditionEvent anEvent() {
		final ConditionEvent event = new ConditionEvent();
		event.setSessionCode("session-1");
		event.setConditionId("condition-1");
		event.setConditionType("default");
		return event;
	}

	@Test
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void theEventGoesToTheReceiverOnBehalfOfTheAccount() {
		when(broker.checkReceivingComponentPresent(ConditionEventsReceiverFactory.MODULE_ID,
				ConditionEventsReceiverFactory.SYSTEM_ID)).thenReturn(true);
		final ConditionEvent event = anEvent();

		final String id = publisher.publish(event, "someone@example.com");

		final ArgumentCaptor<GMessageEnvelope> sent = ArgumentCaptor.forClass(GMessageEnvelope.class);
		verify(broker).accept(sent.capture());
		final GMessageEnvelope<ConditionEventPayload> envelope = sent.getValue();
		assertEquals(id, envelope.getId(), "the id of the message sent is returned");
		assertEquals(ConditionEventsReceiverFactory.MODULE_ID, envelope.getTargetModule());
		assertEquals(ConditionEventsReceiverFactory.SYSTEM_ID, envelope.getTargetComponent());
		assertEquals(SystemComponentType.APPLICATION_COMPONENT, envelope.getTargetType());
		assertEquals(ConditionEventsReceiverFactory.MODULE_ID, envelope.getSourceModule(), "sent by the publisher");
		assertEquals(ConditionEventsPublisher.SYSTEM_ID, envelope.getSourceComponent());
		assertEquals(ConditionEventPayload.class.getName(), envelope.getPayloadType());
		assertTrue(publisher.getEmittedPayloadTypes().contains(envelope.getPayloadType()),
				"the broker accepts the payload from this emitter");
		assertEquals("someone@example.com", envelope.getUserId(), "on behalf of the account given");
		assertSame(event, envelope.getPayload().getEvent());
	}

	@Test
	public void noReceiverReachableIsRefused() {
		when(broker.checkReceivingComponentPresent(any(), any())).thenReturn(false);

		final IllegalStateException refused = assertThrows(IllegalStateException.class,
				() -> publisher.publish(anEvent(), null));

		assertEquals(ConditionEventsPublisher.NO_RECEIVER, refused.getMessage());
		verify(broker, never()).accept(any());
	}

	@Test
	public void noEventIsRefused() {
		assertThrows(IllegalArgumentException.class, () -> publisher.publish(null, null));
		verify(broker, never()).accept(any());
	}
}
