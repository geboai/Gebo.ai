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
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.application.messaging.IGMessageReceiver;
import ai.gebo.application.messaging.IGMessageReceiverFactory;
import ai.gebo.application.messaging.IGTimedOutMessageReceiver;
import ai.gebo.application.messaging.IGTimedOutMessageReceiverFactory;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.architecture.multithreading.IGRunnable;

/**
 * AI generated comments
 * The MessageReceiverRunner class is responsible for managing the reception
 * and processing of messages through a provided factory.
 * It implements the IGMessageReceiver and IGRunnable interfaces.
 */
class MessageReceiverRunner implements IGMessageReceiver, IGRunnable {
    static Logger LOGGER = LoggerFactory.getLogger(MessageReceiverRunner.class);
    
    // Factory to create message receivers
    private IGMessageReceiverFactory factory = null;
    
    // List to hold incoming messages
    private List<GMessageEnvelope> messages = new ArrayList<GMessageEnvelope>();
    
    // Flag to control the running state of the receiver
    private boolean running = true;
    
    // Flag to indicate if the factory supports timeout
    private boolean timeOutFactory = false;
    
    // Flag to indicate if the receiver is idle
    private boolean idle = false;
    
    // Timeout value for message reception
    private long timeout = Long.MAX_VALUE;
    
    // Timestamp when message reception was blocked
    private long blockedAt = 0l;
    
    // Single instance of the message receiver
    private IGMessageReceiver instance = null;

    /**
     * Constructor for MessageReceiverRunner.
     * Initializes the factory and assesses the timeout capabilities.
     * 
     * @param factory The message receiver factory to be used.
     */
    MessageReceiverRunner(IGMessageReceiverFactory factory) {
        this.factory = factory;
        this.timeOutFactory = this.factory instanceof IGTimedOutMessageReceiverFactory;
        this.timeout = (this.timeOutFactory) ? ((IGTimedOutMessageReceiverFactory) this.factory).getReceivingTimeout()
                : Long.MAX_VALUE;
        running = true;
    }

    /**
     * Retrieves the accepted payload types from the factory.
     * 
     * @return List of accepted payload types.
     */
    @Override
    public List<String> getAcceptedPayloadTypes() {
        return factory.getAcceptedPayloadTypes();
    }

    /**
     * Determines if every payload type is accepted by the factory.
     * 
     * @return Boolean indicating if all payload types are accepted.
     */
    @Override
    public boolean isAcceptEveryPayloadType() {
        return factory.isAcceptEveryPayloadType();
    }

    /**
     * Gets the messaging system identifier from the factory.
     * 
     * @return String representing the messaging system ID.
     */
    @Override
    public String getMessagingSystemId() {
        return factory.getMessagingSystemId();
    }

    /**
     * Obtains the component type from the factory.
     * 
     * @return SystemComponentType of the component.
     */
    @Override
    public SystemComponentType getComponentType() {
        return factory.getComponentType();
    }

    /**
     * Accepts a message and adds it to the processing queue.
     * Also notifies any waiting threads to process incoming messages.
     * 
     * @param msg The GMessageEnvelope to accept and process.
     */
    @Override
    public void accept(GMessageEnvelope msg) {
        synchronized (messages) {
            messages.add(msg);
            messages.notify();
        }
    }

    /**
     * The main run loop for processing messages.
     * Handles reception, processing, and potential timeout of messages.
     */
    @Override
    public void run() {
        try {
            boolean lastLoopManagesTimeout = false;
            boolean hasAlreadyReceivedMessages = false;
            while (running) {
                List<GMessageEnvelope> copiedMessages = new ArrayList<GMessageEnvelope>();
                boolean manageTimeOutOnly = false;
                synchronized (messages) {
                    if (messages.isEmpty()) {
                        if (LOGGER.isDebugEnabled()) {
                            LOGGER.debug("Handler:" + getCompleteId() + " wait for incoming messages");
                        }
                        blockedAt = System.currentTimeMillis();

                        if (this.timeOutFactory && !lastLoopManagesTimeout && hasAlreadyReceivedMessages) {
                            idle = true;
                            messages.wait(timeout);
                            manageTimeOutOnly = messages.isEmpty();
                        } else {
                            idle = true;
                            lastLoopManagesTimeout = false;
                            messages.wait();
                        }
                        if (!running)
                            return;
                        if (LOGGER.isDebugEnabled()) {
                            LOGGER.debug("Handler:" + getCompleteId() + " unlocked for incoming messages");
                        }
                    }
                    copiedMessages.addAll(messages);
                    messages.clear();
                    if (instance == null) {
                        instance = factory.create();
                    }
                    if (manageTimeOutOnly) {
                        IGTimedOutMessageReceiver timedOutReceiver = (IGTimedOutMessageReceiver) instance;
                        timedOutReceiver.onTimeoutEvent(new Date(blockedAt));
                        lastLoopManagesTimeout = true;
                        continue;
                    }
                    idle = false;
                }
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Handler:" + getCompleteId() + " going on reception loop with:" + copiedMessages.size()
                            + " messages");
                }
                for (GMessageEnvelope msg : copiedMessages) {
                    try {
                        instance.accept(msg);
                    } catch (Throwable th) {
                        LOGGER.error("Exception on message delivery:" + msg.toString(), th);
                    }
                }

                hasAlreadyReceivedMessages = true;
            }
        } catch (InterruptedException e) {

            e.printStackTrace();
        }

    }

    /**
     * Returns the length of the current message queue.
     * 
     * @return The number of messages currently in the queue.
     */
    public int getMessagesQueueLength() {
        return messages.size();
    }

    /**
     * Gets the messaging module ID from the factory.
     * 
     * @return String representing the messaging module ID.
     */
    @Override
    public String getMessagingModuleId() {
        return factory.getMessagingModuleId();
    }

    /**
     * Checks if the runner is currently active.
     * 
     * @return Boolean indicating if the runner is running.
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Checks if the runner is currently idle.
     * 
     * @return Boolean indicating if the runner is idle.
     */
    public boolean isIdle() {
        return idle;
    }

    /**
     * Initiates the shutdown process by stopping the running state
     * and notifying all waiting threads to end processing.
     */
    public void startShutdown() {
        running = false;
        synchronized (messages) {
            messages.notifyAll();
        }
    }

    /**
     * Method to perform shutdown procedures. Currently not implemented.
     */
    @Override
    public void shutdown() {
        // TODO Auto-generated method stub

    }
}