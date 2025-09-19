/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.application.messaging;

import java.util.List;

import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.architecture.multithreading.IGThreadPoolConsumer;

/**
 * AI generated comments Abstract factory class for creating message receivers
 * with a timeout mechanism.
 */
public abstract class GAbstractTimedOutMessageReceiverFactory extends GAbstractMessageReceiverFactory
		implements IGTimedOutMessageReceiverFactory, IGThreadPoolConsumer {

	// Timeout duration for message receiving operations.
	protected final Long timeout;

	// Configuration for the timed out message receiver factory.
	protected TimedOutMessageReceiverFactoryConfig factoryConfig;

	/**
	 * Constructs a factory with the specified configuration.
	 * 
	 * @param factoryConfig The configuration for the factory.
	 */
	protected GAbstractTimedOutMessageReceiverFactory(TimedOutMessageReceiverFactoryConfig factoryConfig) {
		super(factoryConfig);
		this.factoryConfig = factoryConfig;
		this.timeout = factoryConfig.timeout;
		if (this.timeout == null || this.timeout <= 0l)
			throw new RuntimeException("Timeout cannot be 0 or less");
	}

	

	/**
	 * Configuration class for the TimedOutMessageReceiverFactory.
	 */
	public static class TimedOutMessageReceiverFactoryConfig extends MessageReceiverFactoryConfig {

		// Timeout duration in milliseconds.
		public Long timeout = null;

		// Threshold for flushing messages.
		public Integer flushThreshold = 10;

		/**
		 * Returns the configured timeout.
		 * 
		 * @return The timeout in milliseconds.
		 */
		public Long getTimeout() {
			return timeout;
		}

		/**
		 * Sets the timeout duration.
		 * 
		 * @param timeout The timeout to be set.
		 */
		public void setTimeout(Long timeout) {
			this.timeout = timeout;
		}

		/**
		 * Returns the flush threshold.
		 * 
		 * @return The flush threshold.
		 */
		public Integer getFlushThreshold() {
			return flushThreshold;
		}

		/**
		 * Sets the flush threshold for messages.
		 * 
		 * @param flushThreshold The flush threshold to set.
		 */
		public void setFlushThreshold(Integer flushThreshold) {
			this.flushThreshold = flushThreshold;
		}
	}

	/**
	 * Abstract class for receivers that support timeout functionality.
	 */
	protected abstract class GAbstractNestedTimedOutMessageReceiver extends GNestedMessageReceiver
			implements IGTimedOutMessageReceiver {

		/**
		 * Returns the receiving timeout from the parent factory.
		 * 
		 * @return The timeout in milliseconds.
		 */
		@Override
		public long getReceivingTimeout() {
			return GAbstractTimedOutMessageReceiverFactory.this.getReceivingTimeout();
		}
	}

	/**
	 * Receiver class that aggregates messages in batches and supports nested
	 * receivers.
	 */
	protected class GNestedBatchAggregatorMessageReceiver extends GBatchAggregatorMessageReceiver {

		/**
		 * Constructs a batch aggregator message receiver.
		 * 
		 * @param nested         The nested messages receiver.
		 * @param flushThreshold The flush threshold.
		 */
		public GNestedBatchAggregatorMessageReceiver(IGBatchMessagesReceiver nested, int flushThreshold) {
			super(nested, flushThreshold);
		}

		/**
		 * Determines if immediate flushing of messages is required.
		 * 
		 * @param messages List of message envelopes.
		 * @return False as default behavior.
		 */
		@Override
		protected boolean isImmediateFlushRequired(List<GMessageEnvelope> messages) {
			return false;
		}

		/**
		 * Returns the receiving timeout from the parent factory.
		 * 
		 * @return The timeout in milliseconds.
		 */
		@Override
		public long getReceivingTimeout() {
			return GAbstractTimedOutMessageReceiverFactory.this.getReceivingTimeout();
		}

		/**
		 * Returns the list of accepted payload types.
		 * 
		 * @return List of accepted payload types.
		 */
		@Override
		public List<String> getAcceptedPayloadTypes() {
			return GAbstractTimedOutMessageReceiverFactory.this.getAcceptedPayloadTypes();
		}

		/**
		 * Checks if every payload type is accepted.
		 * 
		 * @return True if every payload type is accepted, false otherwise.
		 */
		@Override
		public boolean isAcceptEveryPayloadType() {
			return GAbstractTimedOutMessageReceiverFactory.this.isAcceptEveryPayloadType();
		}

		/**
		 * Returns the messaging module ID.
		 * 
		 * @return Messaging module ID.
		 */
		@Override
		public String getMessagingModuleId() {
			return GAbstractTimedOutMessageReceiverFactory.this.getMessagingModuleId();
		}

		/**
		 * Returns the messaging system ID.
		 * 
		 * @return Messaging system ID.
		 */
		@Override
		public String getMessagingSystemId() {
			return GAbstractTimedOutMessageReceiverFactory.this.getMessagingSystemId();
		}

		/**
		 * Returns the type of system component.
		 * 
		 * @return SystemComponentType of the messaging receiver.
		 */
		@Override
		public SystemComponentType getComponentType() {
			return GAbstractTimedOutMessageReceiverFactory.this.getComponentType();
		}

	}

	/**
	 * Returns the configured receiving timeout for the factory.
	 * 
	 * @return The receiving timeout in milliseconds.
	 */
	@Override
	public final long getReceivingTimeout() {
		return timeout;
	}
}