/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.application.messaging.orchestration;

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

/**
 * AI generated comments
 * Manages a collection of message receiver threads and delegates messages to them based on load and availability.
 * Provides fallback to a backup receiver if no idle threads are available.
 */
class ThreadMessageReceiverMultiplexer implements IGMessageReceiver {
	
	// Logger for the class
	private final static Logger LOGGER = LoggerFactory.getLogger(ThreadMessageReceiverMultiplexer.class);
	
	// Factory for creating message receivers
	protected IGMessageReceiverFactory factory;
	
	// List of message receiver thread runners
	protected List<MessageReceiverRunner> receivers = null;
	
	// Backup receiver in case all threads are busy
	protected IGMessageReceiver backupReceiver = null;
	
	// Monitor object for coordinating access to the backup receiver
	protected Object backupReceiverMonitor = new Object();
	
	// Timestamp of the last message received
	protected long lastReceviedTime = 0l;
	
	// Tracks whether the last interaction was a timeout event
	protected boolean lastInteractionWasTimeout = false;
	
	// Timeout period for receiving messages
	protected long timeout = 0l;
	
	// Indicates if the backup receiver is currently executing
	protected boolean backupReceiverMonitorExecuting = false;

	/**
	 * Constructor to initialize the multiplexer with the given factory, receivers, and backup receiver.
	 *
	 * @param factory The factory to create message receivers
	 * @param receivers The list of message receiver runners to manage
	 * @param backupReceiver The backup receiver used when all threads are busy
	 */
	ThreadMessageReceiverMultiplexer(IGMessageReceiverFactory factory, List<MessageReceiverRunner> receivers,
			IGMessageReceiver backupReceiver) {
		
		this.receivers = receivers;
		this.backupReceiver = backupReceiver;
		this.factory = factory;
		
		// Set the timeout if the factory supports timeouts
		if (factory instanceof IGTimedOutMessageReceiverFactory) {
			timeout = ((IGTimedOutMessageReceiverFactory) factory).getReceivingTimeout();
		}

		// Ensure the multiplexer has at least one available receiver or a backup receiver
		if (backupReceiver == null && (receivers == null || receivers.isEmpty()))
			throw new RuntimeException(
					"A ThreadMessageReceiverMultiplexer with no Thread receivers and no receiver in caller's thread will not do deliveries");
	}

	@Override
	public List<String> getAcceptedPayloadTypes() {
		// Delegate to factory's implementation of accepted payload types
		return factory.getAcceptedPayloadTypes();
	}

	@Override
	public boolean isAcceptEveryPayloadType() {
		// Delegate to factory's acceptance of all payload types
		return factory.isAcceptEveryPayloadType();
	}

	@Override
	public String getMessagingModuleId() {
		// Retrieve the messaging module ID from the factory
		return factory.getMessagingModuleId();
	}

	@Override
	public String getMessagingSystemId() {
		// Retrieve the messaging system ID from the factory
		return factory.getMessagingSystemId();
	}

	@Override
	public SystemComponentType getComponentType() {
		// Retrieve the component type from the factory
		return factory.getComponentType();
	}

	/**
	 * Accepts a message envelope and delegates it to the appropriate message receiver.
	 *
	 * @param msg The message envelope to process
	 */
	@Override
	public void accept(GMessageEnvelope msg) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin accept(...)");
		}
		try {
			boolean delivered = false;
			int lowerLoad = Integer.MAX_VALUE;
			int lowerLoadIndex = -1;
			synchronized (receivers) {
				int n = 0;

				// Find an idle receiver or the one with the lowest load
				for (MessageReceiverRunner threadMessageReceiver : receivers) {
					int loadFactor = threadMessageReceiver.getMessagesQueueLength();
					if (loadFactor < lowerLoad) {
						lowerLoadIndex = n;
						lowerLoad = loadFactor;
					}
					if (!delivered && threadMessageReceiver.isIdle()) {
						delivered = true;
						if (LOGGER.isDebugEnabled()) {
							LOGGER.debug("Unlocking idle thread nr:" + n);
						}
						threadMessageReceiver.accept(msg);
						break;
					}
					n++;
				}
			}
			
			// If no idle receiver, enqueue in the least loaded queue or use backup receiver
			if (!delivered && backupReceiver == null && lowerLoadIndex >= 0) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("No idle thread but enqueued in the nr:" + lowerLoadIndex);
				}
				receivers.get(lowerLoadIndex).accept(msg);
			} else if (!delivered && backupReceiver != null) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("No idle thread found, executing in caller thread to slow it down");
				}
				synchronized (backupReceiverMonitor) {
					try {
						backupReceiverMonitorExecuting = true;
						lastReceviedTime = System.currentTimeMillis();
						lastInteractionWasTimeout = false;
						backupReceiver.accept(msg);
						lastReceviedTime = System.currentTimeMillis();
					} finally {
						backupReceiverMonitorExecuting = false;
					}
				}
			}
		} catch (Throwable th) {
			LOGGER.error("Exception in accept(...) for thread multiplexing method", th);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End accept(...)");
		}
	}

	/**
	 * Periodic callback to check for timeout conditions and trigger timeout handling if needed.
	 */
	void callbackTick() {
		if (!lastInteractionWasTimeout && timeout > 0l && backupReceiver != null
				&& backupReceiver instanceof IGTimedOutMessageReceiver) {
			long now = System.currentTimeMillis();

			// Check if timeout threshold is reached
			if (now >= (timeout + lastReceviedTime) && !backupReceiverMonitorExecuting) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Begin managing timeout on caller thread receiver");
				}
				synchronized (backupReceiverMonitor) {
					try {
						lastInteractionWasTimeout = true;
						IGTimedOutMessageReceiver receover = (IGTimedOutMessageReceiver) backupReceiver;
						receover.onTimeoutEvent(new Date());
					} catch (Throwable th) {
						LOGGER.error("Exception in onTimeoutEvent(...)", th);
					}
				}
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("End managing timeout on caller thread receiver");
				}
			}
		}
	}
}