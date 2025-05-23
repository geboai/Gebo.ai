/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.application.messaging;

/**
 * Gebo.ai comment agent
 * This interface represents a message receiver within the messaging system.
 * It extends the IGMessageConsumer interface, which likely defines the basic 
 * message consuming capabilities that IGMessageReceiver inherits.
 */
public interface IGMessageReceiver extends IGMessageConsumer {

	/**
	 * Default method to determine if the message receiver handles the given message payload type.
	 *
	 * @param payloadType The type of the message payload to check against.
	 * @return true if the receiver handles the message payload, otherwise false. This default implementation always returns true.
	 * Note: Implementers may override this method to provide custom logic.
	 */
	public default boolean handlesMessagePayload(IGMessagePayloadType payloadType) {
		// This implementation always returns true, meaning by default, all types of payloads are handled.
		return true;
	}
}