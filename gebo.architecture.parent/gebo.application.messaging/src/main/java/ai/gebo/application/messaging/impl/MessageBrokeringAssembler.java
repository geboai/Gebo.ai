/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.application.messaging.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.IGMessageEmitter;
import ai.gebo.application.messaging.IGMessageReceiver;
import ai.gebo.application.messaging.external.IGExternalMessageEmitter;
import ai.gebo.application.messaging.external.IGExternalMessageEmitterSource;
import ai.gebo.application.messaging.external.IGExternalMessageEmitterSourceRepositoryPattern;
import ai.gebo.application.messaging.external.IGExternalMessageReceiver;
import ai.gebo.application.messaging.external.IGExternalMessageReceiverSource;
import ai.gebo.application.messaging.external.IGExternalMessageReceiverSourceRepositoryPattern;
import ai.gebo.application.messaging.model.ComponentMetaInfo;
import ai.gebo.application.messaging.model.GModuleMetaInfo;
import ai.gebo.application.messaging.orchestration.MultiThreadedMessagesOrchestrator;

/**
 * AI generated comments
 * MessageBrokeringAssembler is responsible for assembling the message brokering components and initializing
 * them when the application context is refreshed. It integrates both internal and external message emitters and receivers.
 */
@Configuration
public class MessageBrokeringAssembler implements ApplicationListener<ContextRefreshedEvent> {
    
    // Logger for logging information and warnings
	private final static Logger LOGGER = LoggerFactory.getLogger(MessageBrokeringAssembler.class);
	
	// Message broker instance for handling message components
	IGMessageBroker broker;
	
	// List of message receivers
	List<IGMessageReceiver> receivers = null;
	
	// List of message emitters
	List<IGMessageEmitter> emitters = null;
	
	// Orchestrator for managing multi-threaded message operations
	MultiThreadedMessagesOrchestrator threadOrchestrator = null;
	
	// Repository pattern to manage external message emitter sources
	IGExternalMessageEmitterSourceRepositoryPattern externalMessageEmitterSourceRepositoryPattern = null;
	
	// Repository pattern to manage external message receiver sources
	IGExternalMessageReceiverSourceRepositoryPattern externalMessageReceiverSourceRepositoryPattern = null;

    /**
     * Constructs a new MessageBrokeringAssembler with the specified dependencies.
     *
     * @param broker the message broker to use
     * @param receivers the list of message receivers, may be null
     * @param emitters the list of message emitters, may be null
     * @param threadOrchestrator the orchestrator for threaded message operations
     * @param externalMessageEmitterSourceRepositoryPattern repository for external message emitter sources
     * @param externalMessageReceiverSourceRepositoryPattern repository for external message receiver sources
     */
	public MessageBrokeringAssembler(@Autowired IGMessageBroker broker,
			@Autowired(required = false) List<IGMessageReceiver> receivers,
			@Autowired(required = false) List<IGMessageEmitter> emitters,
			MultiThreadedMessagesOrchestrator threadOrchestrator,
			IGExternalMessageEmitterSourceRepositoryPattern externalMessageEmitterSourceRepositoryPattern,
			IGExternalMessageReceiverSourceRepositoryPattern externalMessageReceiverSourceRepositoryPattern) {
		this.broker = broker;
		this.receivers = receivers != null ? receivers : new ArrayList<IGMessageReceiver>();
		this.emitters = emitters != null ? emitters : new ArrayList<IGMessageEmitter>();
		this.threadOrchestrator = threadOrchestrator;
		this.externalMessageEmitterSourceRepositoryPattern = externalMessageEmitterSourceRepositoryPattern;
		this.externalMessageReceiverSourceRepositoryPattern = externalMessageReceiverSourceRepositoryPattern;
	}

    /**
     * Handles the ContextRefreshedEvent to initialize the message brokering process.
     * It adds both internal and external message emitters and receivers to the broker.
     *
     * @param event the context refreshed event
     */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Begin initializing message brokering");
		}
		
		// Retrieve threaded receivers and add them to the receivers list
		List<IGMessageReceiver> threadedReceivers = threadOrchestrator.getReceivers();
		if (threadedReceivers != null) {
			receivers.addAll(threadedReceivers);
		}
		
		// Warn if no receivers are available; otherwise, log and add each receiver to the broker
		if (receivers.isEmpty())
			LOGGER.warn("No receivers reachable");
		else {
			for (IGMessageReceiver recvr : receivers) {
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("IGMessageReceiver [" + recvr.getClass().getName() + "] " + recvr.getCompleteId()
							+ " accepts all messages: " + recvr.isAcceptEveryPayloadType() + " accepted payloads: "
							+ recvr.getAcceptedPayloadTypes());
				}
				broker.addSystemComponent(recvr);
			}
		}
		
		// Warn if no emitters are available; otherwise, log and add each emitter to the broker
		if (emitters.isEmpty())

			LOGGER.warn("No emitters reachable");
		else {
			for (IGMessageEmitter emit : emitters) {
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("IGMessageEmitter [" + emit.getClass().getName() + "] " + emit.getCompleteId()
							+ " sends payloads: " + emit.getEmittedPayloadTypes());
				}
				broker.addSystemComponent(emit);

			}
		}

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Begin Integrating remote emitters and receivers");
		}
		
		// Add external message emitters to the broker
		List<IGExternalMessageEmitterSource> emitterSources = externalMessageEmitterSourceRepositoryPattern
				.getImplementations();
		for (IGExternalMessageEmitterSource emitterSource : emitterSources) {
			List<IGExternalMessageEmitter> emittersList = emitterSource.getExternalEmitters();
			for (IGExternalMessageEmitter emitter : emittersList) {
				broker.addSystemComponent(emitter.getEmitter());
			}
		}
		
		// Add external message receivers to the broker
		List<IGExternalMessageReceiverSource> receiverSources = externalMessageReceiverSourceRepositoryPattern
				.getImplementations();
		for (IGExternalMessageReceiverSource receiverSource : receiverSources) {
			List<IGExternalMessageReceiver> receiversList = receiverSource.getExternalReceivers();
			for (IGExternalMessageReceiver receiver : receiversList) {
				broker.addSystemComponent(receiver.getReceiver());
			}
		}
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("End Integrating remote emitters and receivers");
		}
		
		// Log information regarding system components
		List<GModuleMetaInfo> systemInfos = broker.getSystemsInfo();
		if (LOGGER.isInfoEnabled()) {
			for (GModuleMetaInfo moduleMetaInfo : systemInfos) {

				LOGGER.info("-Module: " + moduleMetaInfo.getMessagingModuleId());
				LOGGER.info(" Components: ");

				List<ComponentMetaInfo> components = moduleMetaInfo.getComponents();
				for (ComponentMetaInfo component : components) {
					LOGGER.info("   - Component: " + component.getMessagingSystemId());
				}
			}
		}
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("End initializing message brokering");
		}
	}

}