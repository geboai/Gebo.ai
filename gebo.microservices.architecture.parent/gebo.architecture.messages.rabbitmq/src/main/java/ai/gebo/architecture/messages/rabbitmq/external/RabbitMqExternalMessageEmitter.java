/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.messages.rabbitmq.external;

import ai.gebo.application.messaging.external.ExternalEmitterIfaceData;
import ai.gebo.application.messaging.external.GAbstractExternalMessageEmitter;

/**
 * Inbound bridge (RabbitMQ -&gt; local broker), emitter side.
 *
 * <p>
 * Registered in the in-memory broker as an {@code IGMessageEmitter} so that
 * envelopes consumed from RabbitMQ and injected into the broker (see
 * {@code RabbitMqInboundBridge}) are recognized as coming from a known emitter
 * and get routed to their local target receiver.
 * </p>
 *
 * Gebo.ai comment agent
 */
public class RabbitMqExternalMessageEmitter extends GAbstractExternalMessageEmitter {

	/**
	 * @param config the messaging identity this emitter is registered with
	 */
	public RabbitMqExternalMessageEmitter(ExternalEmitterIfaceData config) {
		super(config);
	}
}
