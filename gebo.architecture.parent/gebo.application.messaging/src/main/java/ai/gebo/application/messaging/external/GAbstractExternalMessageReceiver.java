/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.application.messaging.external;

import java.util.List;

import ai.gebo.application.messaging.IGMessageReceiver;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.GMessageEnvelope;

/**
 * Gebo.ai comment agent
 * Abstract class that provides the basic structure for an external message receiver.
 * It implements IGExternalMessageReceiver interface and provides functionality for handling configuration data.
 */
public abstract class GAbstractExternalMessageReceiver implements IGExternalMessageReceiver {
	// Configuration for the external receiver interface
	private ExternalReceiverIfaceData config = null;

	/**
	 * Default constructor initializing the receiver with no configuration.
	 */
	public GAbstractExternalMessageReceiver() {
	}

	/**
	 * Constructor initializing the receiver with the specified configuration.
	 * 
	 * @param config the configuration for the external receiver interface
	 */
	public GAbstractExternalMessageReceiver(ExternalReceiverIfaceData config) {
		this.config = config;
	}

	/**
	 * Inner class that implements IGMessageReceiver to handle message receiving functionality
	 * based on the provided configuration.
	 */
	class NestedMessageReceiver implements IGMessageReceiver {

		/**
		 * Returns the list of accepted payload types. 
		 * If the configuration is null, returns null.
		 * 
		 * @return list of accepted payload types or null
		 */
		@Override
		public List<String> getAcceptedPayloadTypes() {
			return config != null ? config.getAcceptedPayloadTypes() : null;
		}

		/**
		 * Checks if all payload types are accepted.
		 * If the configuration is null, returns null.
		 * 
		 * @return true if all payload types are accepted, false otherwise
		 */
		@Override
		public boolean isAcceptEveryPayloadType() {
			return config != null ? config.isAcceptEveryPayloadType() : null;
		}

		/**
		 * Retrieves the messaging module identifier from the configuration.
		 * 
		 * @return the messaging module identifier or null
		 */
		@Override
		public String getMessagingModuleId() {
			return config != null ? config.getMessagingModuleId() : null;
		}

		/**
		 * Retrieves the messaging system identifier from the configuration.
		 * 
		 * @return the messaging system identifier or null
		 */
		@Override
		public String getMessagingSystemId() {
			return config != null ? config.getMessagingSystemId() : null;
		}

		/**
		 * Gets the component type from the configuration.
		 * 
		 * @return the component type or null
		 */
		@Override
		public SystemComponentType getComponentType() {
			return config != null ? config.getComponentType() : null;
		}

		/**
		 * Processes the given message by invoking the outer class's accept method.
		 * 
		 * @param message the message envelope to be processed
		 */
		@Override
		public void accept(GMessageEnvelope message) {
			GAbstractExternalMessageReceiver.this.accept(message);
		}

		/**
		 * Indicates that this is not a local system.
		 * 
		 * @return false always as this is not a local system
		 */
		@Override
		public boolean isLocalSystem() {
			return false;
		}

	}

	// Instance of NestedMessageReceiver to handle message receiving
	private final NestedMessageReceiver receiver = new NestedMessageReceiver();

	/**
	 * Returns the message receiver.
	 * 
	 * @return an instance of IGMessageReceiver
	 */
	@Override
	public IGMessageReceiver getReceiver() {
		return receiver;
	}

	/**
	 * Abstract method that must be implemented by subclasses to define 
	 * how incoming messages are processed.
	 * 
	 * @param message the message envelope to be processed
	 */
	public abstract void accept(GMessageEnvelope message);

	/**
	 * Retrieves the current configuration.
	 * 
	 * @return the current external receiver interface configuration
	 */
	public ExternalReceiverIfaceData getConfig() {
		return config;
	}

	/**
	 * Sets a new configuration for the external receiver interface.
	 * 
	 * @param config the new configuration to set
	 */
	public void setConfig(ExternalReceiverIfaceData config) {
		this.config = config;
	}
}