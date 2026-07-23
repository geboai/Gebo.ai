/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.application.messaging.orchestration;

import ai.gebo.application.messaging.model.GMessageEnvelope;

/**
 * Wraps the point where a message is actually handed to a receiver's business
 * logic - {@link MessageReceiverRunner}'s dedicated-thread delivery loop and
 * {@link ThreadMessageReceiverMultiplexer}'s synchronous backup-receiver path.
 *
 * <p>
 * Both of those execution paths run on plain objects manually instantiated by
 * {@link MultiThreadedMessagesOrchestrator} and handed to
 * {@code IGeboThreadManager}, so they are never reached by a Spring AOP proxy.
 * This interface is the explicit collaborator that reaches them instead - the
 * delivery-side counterpart of {@code IMessageEnvelopeFactory} on the creation
 * side. The default implementation is a no-op; a tracing-aware override lives
 * in {@code gebo.architecture.telemetry}.
 * </p>
 */
public interface IGMessageDeliveryObserver {

	void aroundDelivery(GMessageEnvelope<?> envelope, Runnable delivery);
}
