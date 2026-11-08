/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.conditionevents.messaging;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.GAbstractMessageReceiverFactory;
import ai.gebo.application.messaging.IGMessageReceiver;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.architecture.agents.conditionevents.model.ConditionEventAck;
import ai.gebo.architecture.agents.conditionevents.model.ConditionEventCaller;
import ai.gebo.architecture.agents.conditionevents.services.IConditionEventsService;

/**
 * The message broker's entry of the condition events: every message carrying a
 * {@link ConditionEventPayload}, from a component of the platform or from an
 * external messaging system bridged into the broker, is delivered as a channel
 * trusted as infrastructure; the sender the envelope names is kept as the actor,
 * its source component as the source when the event names none.
 */
@Component
@Scope("singleton")
public class ConditionEventsReceiverFactory extends GAbstractMessageReceiverFactory {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConditionEventsReceiverFactory.class);
	public static final String MODULE_ID = "AGENTS-CONDITION-EVENTS";
	public static final String SYSTEM_ID = "CONDITION-EVENTS-RECEIVER";
	static final MessageReceiverFactoryConfig FACTORY_CONFIG = new MessageReceiverFactoryConfig();
	static {
		FACTORY_CONFIG.setUseSenderThread(false);
		FACTORY_CONFIG.setPoolCardinality(1);
	}
	/**
	 * Resolved on the first message: the receiver factories are collected while
	 * the thread manager is created, and the service reaches the chat pipelines,
	 * which need that thread manager.
	 */
	private final ObjectProvider<IConditionEventsService> service;

	class ConditionEventsReceiver extends GNestedMessageReceiver {
		@Override
		public void accept(GMessageEnvelope envelope) {
			if (!(envelope.getPayload() instanceof ConditionEventPayload payload)) {
				throw new IllegalStateException("This receiver cannot handle payload type:" + envelope.getPayloadType());
			}
			final String source = envelope.getSourceModule() != null
					? envelope.getSourceModule()
							+ (envelope.getSourceComponent() != null ? "." + envelope.getSourceComponent() : "")
					: envelope.getSourceComponent();
			final ConditionEventAck ack = service.getObject().deliver(payload.getEvent(),
					ConditionEventCaller.trustedChannel(envelope.getUserId(), ConditionEventCaller.MESSAGE, source));
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Condition event message:" + envelope.getId() + " from:" + source + " -> " + ack.getOutcome()
						+ (ack.getReason() != null ? ": " + ack.getReason() : ""));
			}
		}
	}

	public ConditionEventsReceiverFactory(ObjectProvider<IConditionEventsService> service) {
		super(FACTORY_CONFIG);
		this.service = service;
	}

	@Override
	public List<String> getAcceptedPayloadTypes() {
		return List.of(ConditionEventPayload.class.getName());
	}

	@Override
	public boolean isAcceptEveryPayloadType() {
		return false;
	}

	@Override
	public IGMessageReceiver create() {
		return new ConditionEventsReceiver();
	}

	@Override
	public String getMessagingModuleId() {
		return MODULE_ID;
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
