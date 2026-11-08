/*
 * Copyright (c) 2025+ Gebo.ai
 * Licensed under the Mozilla Public License 2.0 (MPL 2.0) with Data Protection Clauses.
 * See LICENSE file in the project root for full license information.
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 */
package ai.gebo.architecture.agents.conditionevents.messaging;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.IGMessageEmitter;
import ai.gebo.application.messaging.IMessageEnvelopeFactory;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.architecture.agents.conditionevents.model.ConditionEvent;
import ai.gebo.architecture.agents.conditionevents.services.IConditionEventsPublisher;

/**
 * The platform's emitter of condition events: the broker routes a message only
 * from a registered emitter of its payload type, so the components that solve
 * conditions send through this one instead of registering their own. The message
 * goes to {@link ConditionEventsReceiverFactory}'s receiver, here or, in a
 * deployment that bridges it, in the application hosting it.
 */
@Component
@Scope("singleton")
public class ConditionEventsPublisher implements IConditionEventsPublisher, IGMessageEmitter {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConditionEventsPublisher.class);
	public static final String SYSTEM_ID = "CONDITION-EVENTS-PUBLISHER";
	static final String NO_EVENT = "No condition event to publish";
	static final String NO_RECEIVER = "No receiver of condition events is reachable from this application: "
			+ ConditionEventsReceiverFactory.MODULE_ID + "." + ConditionEventsReceiverFactory.SYSTEM_ID;
	/**
	 * Looked up on each publication: the broker is assembled from the emitters,
	 * this one included.
	 */
	private final ObjectProvider<IGMessageBroker> broker;
	private final ObjectProvider<IMessageEnvelopeFactory> envelopes;

	public ConditionEventsPublisher(ObjectProvider<IGMessageBroker> broker,
			ObjectProvider<IMessageEnvelopeFactory> envelopes) {
		this.broker = broker;
		this.envelopes = envelopes;
	}

	@Override
	public String publish(ConditionEvent event, String onBehalfOf) {
		if (event == null) {
			throw new IllegalArgumentException(NO_EVENT);
		}
		final IGMessageBroker theBroker = broker.getObject();
		if (!theBroker.checkReceivingComponentPresent(ConditionEventsReceiverFactory.MODULE_ID,
				ConditionEventsReceiverFactory.SYSTEM_ID)) {
			throw new IllegalStateException(NO_RECEIVER);
		}
		final GMessageEnvelope<ConditionEventPayload> envelope = envelopes.getObject().newMessageFrom(this,
				new ConditionEventPayload(event), onBehalfOf);
		envelope.setTargetModule(ConditionEventsReceiverFactory.MODULE_ID);
		envelope.setTargetComponent(ConditionEventsReceiverFactory.SYSTEM_ID);
		envelope.setTargetType(SystemComponentType.APPLICATION_COMPONENT);
		theBroker.accept(envelope);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Condition event published:" + envelope.getId() + " for session:" + event.getSessionCode()
					+ " condition:" + event.getConditionId() + " correlation:" + event.getCorrelationId()
					+ " on behalf of:" + onBehalfOf);
		}
		return envelope.getId();
	}

	@Override
	public List<String> getEmittedPayloadTypes() {
		return List.of(ConditionEventPayload.class.getName());
	}

	@Override
	public String getMessagingModuleId() {
		return ConditionEventsReceiverFactory.MODULE_ID;
	}

	@Override
	public String getMessagingSystemId() {
		return SYSTEM_ID;
	}

	@Override
	public SystemComponentType getComponentType() {
		return SystemComponentType.APPLICATION_COMPONENT;
	}
}
