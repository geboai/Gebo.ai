/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.application.messaging;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.model.GMessagesBatchPayload;

/**
 * AI generated comments
 * Abstract class responsible for receiving and aggregating messages into batches
 * for further processing by a nested message receiver.
 */
abstract class GBatchAggregatorMessageReceiver implements IGTimedOutMessageReceiver {
	// Logger instance for logging debug and error information
	private static Logger LOGGER = LoggerFactory.getLogger(GBatchAggregatorMessageReceiver.class);
	
	// The nested receiver to delegate batched message processing
	IGBatchMessagesReceiver nested = null;
	
	// List to store messages until they are batched and flushed
	List<GMessageEnvelope> messages = new ArrayList<GMessageEnvelope>();
	
	// Threshold for the number of messages after which they must be flushed
	int flushThreshold = 0;

	/**
	 * Constructor to initialize the nested receiver and flush threshold.
	 * 
	 * @param nested The nested message receiver that processes the batches.
	 * @param flushThreshold The maximum number of messages to hold before flushing.
	 */
	public GBatchAggregatorMessageReceiver(IGBatchMessagesReceiver nested, int flushThreshold) {
		this.nested = nested;
		this.flushThreshold = flushThreshold;
	}

	/**
	 * Determines whether the messages should be flushed immediately based on 
	 * specific criteria.
	 * 
	 * @param messages The list of messages to assess.
	 * @return boolean indicating if an immediate flush is required.
	 */
	protected abstract boolean isImmediateFlushRequired(List<GMessageEnvelope> messages);

	@Override
	public void onTimeoutEvent(Date waitStartDate) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin onTimeoutEvent(" + waitStartDate + ")");
		}
		this.flush();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End onTimeoutEvent(" + waitStartDate + ")");
		}
	}

	@Override
	public void accept(GMessageEnvelope msg) {
		boolean doFlush = false;
		synchronized (messages) {
			// Add the new message to the list
			messages.add(msg);
			// Check if immediate flush is needed
			boolean immedatedFlushRequired = isImmediateFlushRequired(messages);
			if (immedatedFlushRequired) {
				LOGGER.info("Queue for:" + getMessagingModuleId() + "." + getMessagingSystemId()
						+ " is to be immediately flushed");
			}
			// Check if the message list has reached the flush threshold
			boolean reachedMaximumLength = (messages.size() >= flushThreshold);
			if (reachedMaximumLength) {
				LOGGER.info("Queue for:" + getMessagingModuleId() + "." + getMessagingSystemId()
						+ " reached maximum length");
			}
			doFlush = immedatedFlushRequired || reachedMaximumLength;
		}
		// Perform flushing if needed
		if (doFlush) {
			flush();
		}
	}

	/**
	 * Flushes the collected messages, creating batches and passing them 
	 * to the nested message receiver for processing.
	 */
	protected void flush() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin flush() so invoke batch elaboration");
		}
		LOGGER.info("Flushing Queue:" + getMessagingModuleId() + "." + getMessagingSystemId());
		List<GMessageEnvelope> tosend = new ArrayList<GMessageEnvelope>();
		synchronized (messages) {
			// Collect all messages to a list for sending and clear the existing list
			tosend.addAll(messages);
			messages.clear();
		}
		if (!tosend.isEmpty()) {
			// Create batches from the messages and send them
			List<GMessageEnvelope<GMessagesBatchPayload>> batches = GMessagesBatchPayload.of(tosend);
			for (GMessageEnvelope<GMessagesBatchPayload> batch : batches) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Elaborating batch:" + batch.toString());
				}
				try {
					nested.acceptMessages(batch);
				} catch (Throwable th) {
					LOGGER.error("Exception while deliverying batched messages" + batch.toString(), th);
				}
			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End flush()");
		}
	}
}