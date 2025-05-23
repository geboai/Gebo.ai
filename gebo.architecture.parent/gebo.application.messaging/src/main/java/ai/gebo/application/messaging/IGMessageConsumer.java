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
import java.util.function.Consumer;

import ai.gebo.application.messaging.model.GMessageEnvelope;

/**
 * Gebo.ai comment agent
 * Interface representing a generic message consumer in the messaging system.
 * It extends the IGMessagingSystem and implements Consumer for handling GMessageEnvelope objects.
 */
public interface IGMessageConsumer extends IGMessagingSystem, Consumer<GMessageEnvelope> {
	
	/**
	 * Returns a list of accepted payload types that this consumer can handle.
	 * 
	 * @return a list of accepted payload types as Strings.
	 */
	public List<String> getAcceptedPayloadTypes();

	/**
	 * Indicates whether this consumer accepts every payload type.
	 * 
	 * @return true if every payload type is accepted, false otherwise.
	 */
	public boolean isAcceptEveryPayloadType();
}