/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.workflows.compute.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.IGMessageEmitter;
import ai.gebo.application.messaging.IMessageEnvelopeFactory;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.core.messages.GFinishedWorkflowPayload;

/**
 * AI generated comments Implementation of the IGMessageEmitter interface for
 * emitting messages related to project and knowledge base deletion operations
 * in the core module.
 */
@Component
@Scope("singleton")
public class GWorkflowsConcentratorMessagesEmitterImpl implements IGMessageEmitter {
	@Autowired
	IGMessageBroker broker; // Broker for handling message acceptance and dispatch.
	
	@Autowired
	IMessageEnvelopeFactory envelopeFactory;

	/**
	 * Constructor for GCoreMessagesEmitterImpl. Initializes the component.
	 */
	public GWorkflowsConcentratorMessagesEmitterImpl() {

	}

	/**
	 * Retrieves the messaging module ID.
	 * 
	 * @return the module ID for the core module.
	 */
	@Override
	public String getMessagingModuleId() {
		return GStandardModulesConstraints.CORE_MODULE;
	}

	/**
	 * Retrieves the messaging system ID.
	 * 
	 * @return the system ID for the settings controller component.
	 */
	@Override
	public String getMessagingSystemId() {
		return GStandardModulesConstraints.SYSTEM_SETTINGS_CONTROLLER_COMPONENT;
	}

	/**
	 * Retrieves the component type.
	 * 
	 * @return the system component type as an application component.
	 */
	@Override
	public SystemComponentType getComponentType() {
		return SystemComponentType.APPLICATION_COMPONENT;
	}

	/**
	 * Retrieves the list of payload types that this emitter can emit.
	 *
	 * @return a list of class names of payloads.
	 */
	@Override
	public List<String> getEmittedPayloadTypes() {
		return List.of( GFinishedWorkflowPayload.class.getName());
	}

	
}