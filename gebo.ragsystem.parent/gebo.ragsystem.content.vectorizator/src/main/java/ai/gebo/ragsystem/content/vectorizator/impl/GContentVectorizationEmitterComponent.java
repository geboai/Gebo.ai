/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.ragsystem.content.vectorizator.impl;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.IGMessageEmitter;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.core.messages.GContentEmbeddingHandshakePayload;
import ai.gebo.core.messages.GContentsProcessingStatusUpdatePayload;
import ai.gebo.core.messages.GUserMessagePayload;

/**
 * AI generated comments
 * 
 * Component responsible for emitting messages related to content vectorization.
 * It acts as a message emitter within the system's messaging architecture,
 * sending vectorization-related messages to the broker.
 */
@Component
@Scope("singleton")
public class GContentVectorizationEmitterComponent implements IGMessageEmitter {

	/**
	 * The message broker used to dispatch messages
	 */
	protected IGMessageBroker broker;

	/**
	 * Constructor that initializes the component with a message broker.
	 * 
	 * @param broker The message broker to use for sending messages
	 */
	public GContentVectorizationEmitterComponent(IGMessageBroker broker) {
		this.broker = broker;

	}

	/**
	 * Returns the module identifier for this component.
	 * 
	 * @return The standard vectorizator module identifier
	 */
	@Override
	public String getMessagingModuleId() {
		return GStandardModulesConstraints.VECTORIZATOR_MODULE;
	}

	/**
	 * Returns the system identifier for this component.
	 * 
	 * @return The standard vectorization emitter component identifier
	 */
	@Override
	public String getMessagingSystemId() {
		return GStandardModulesConstraints.VECTORIZATION_EMITTER_COMPONENT;
	}

	/**
	 * Specifies the type of this component within the system.
	 * 
	 * @return The component type as APPLICATION_COMPONENT
	 */
	@Override
	public SystemComponentType getComponentType() {
		return SystemComponentType.APPLICATION_COMPONENT;
	}

	/**
	 * Provides a list of payload types that this emitter can send.
	 * 
	 * @return A list of fully qualified class names for payload types
	 */
	@Override
	public List<String> getEmittedPayloadTypes() {
		return List.of(GContentEmbeddingHandshakePayload.class.getName(), GUserMessagePayload.class.getName(),
				GContentsProcessingStatusUpdatePayload.class.getName());
	}

	/**
	 * Sends a message envelope to the broker.
	 * 
	 * @param msg The message envelope to be sent
	 */
	void send(GMessageEnvelope msg) {
		broker.accept(msg);
	}
}