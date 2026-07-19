/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.application.messaging.impl;

import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.IGMessageEmitter;
import ai.gebo.application.messaging.IGMessagePayloadType;
import ai.gebo.application.messaging.IMessageEnvelopeFactory;
import ai.gebo.application.messaging.model.GMessageEnvelope;

/**
 * Default {@link IMessageEnvelopeFactory}: delegates to
 * {@link GMessageEnvelope}'s static factory methods, unchanged. Always
 * registered (deliberately not {@code @ConditionalOnMissingBean}: plain
 * {@code @Component} beans across different modules have no guaranteed scan
 * order, and the tracing-aware override in {@code gebo.architecture.telemetry}
 * depends on this concrete bean existing to delegate to - a flipped condition
 * would break that dependency). Where both beans exist, {@code @Primary} on
 * the tracing-aware override picks it over this one.
 */
@Component
public class DefaultMessageEnvelopeFactory implements IMessageEnvelopeFactory {

	@Override
	public <T extends IGMessagePayloadType> GMessageEnvelope<T> newMessageFrom(IGMessageEmitter system, T payload) {
		return GMessageEnvelope.newMessageFrom(system, payload);
	}

	@Override
	public <T extends IGMessagePayloadType> GMessageEnvelope<T> newMessageFrom(IGMessageEmitter system, T payload,
			String user) {
		return GMessageEnvelope.newMessageFrom(system, payload, user);
	}
}
