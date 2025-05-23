/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.application.messaging.orchestration;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.IGMessageReceiver;
import ai.gebo.application.messaging.IGMessageReceiverFactory;
import ai.gebo.architecture.multithreading.IGRunnable;
import ai.gebo.architecture.multithreading.IGeboThreadManager;

/**
 * Gebo.ai comment agent
 * A singleton component that orchestrates message handling in a multi-threaded environment.
 * It implements ApplicationListener to handle the application's context closed events.
 */
@Component
@Scope("singleton")
public class MultiThreadedMessagesOrchestrator implements ApplicationListener<ContextClosedEvent> {
    // Logger for this class
    Logger LOGGER = LoggerFactory.getLogger(getClass());

    // Message broker for handling messaging operations
    @Autowired
    IGMessageBroker broker;

    // Thread manager for executing multithreaded tasks
    @Autowired()
    IGeboThreadManager taskExecutor;

    // List of factories for creating message receivers, may be optional
    @Autowired(required = false)
    List<IGMessageReceiverFactory> factories;

    // Handler for managing timeout callbacks
    @Autowired
    TimeoutCallbackHandler callbackHandler;

    // List of multiplexed message receivers
    protected List<ThreadMessageReceiverMultiplexer> multiplexingReceivers = new ArrayList<ThreadMessageReceiverMultiplexer>();

    /**
     * Default constructor for the orchestrator.
     */
    public MultiThreadedMessagesOrchestrator() {
    }

    /**
     * Retrieves a list of message receivers.
     * Initializes message receivers using the configured factories if not already done.
     * 
     * @return a list of initialized IGMessageReceiver instances
     */
    public synchronized List<IGMessageReceiver> getReceivers() {
        if (multiplexingReceivers.isEmpty()) {
            List<IGRunnable> allRunnables = new ArrayList<IGRunnable>();
            for (IGMessageReceiverFactory factory : factories) {
                int howToCreate = factory.getPoolCardinality();
                if (howToCreate < 0)
                    howToCreate = 0;
                // Log information about the number of instances being created
                LOGGER.info("Running thread to execute IGMessage receiver " + factory.getCompleteId() + " in "
                        + howToCreate + " instances");
                List<MessageReceiverRunner> receivers = new ArrayList<MessageReceiverRunner>();
                for (int i = 0; i < howToCreate; i++) {
                    MessageReceiverRunner receiver = new MessageReceiverRunner(factory);
                    receivers.add(receiver);
                }
                allRunnables.addAll(receivers);
                // Check if the factory should use a sender thread and create a backup receiver if needed
                IGMessageReceiver backupReceiver = factory.useSenderThread() ? factory.create() : null;
                // Create a multiplexer for handling multiple receivers
                ThreadMessageReceiverMultiplexer receiver = new ThreadMessageReceiverMultiplexer(factory, receivers,
                        backupReceiver);
                multiplexingReceivers.add(receiver);
                // Register the receiver with the callback handler
                callbackHandler.add(receiver);
            }
            for (IGRunnable igRunnable : allRunnables) {
                // Run each runnable using the task executor
                this.taskExecutor.run(igRunnable);
            }
        }
        // Return a copy of the list of multiplexing receivers
        return new ArrayList<IGMessageReceiver>(multiplexingReceivers);
    }

    /**
     * Handles the application context closed event.
     * Currently, this method does not perform any operations.
     * 
     * @param event the context closed event
     */
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        
    }
}