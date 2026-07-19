/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.application.messaging.orchestration;

import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.model.GMessageEnvelope;

/**
 * Default {@link IGMessageDeliveryObserver}: runs the delivery with no
 * instrumentation. Always registered (see {@code DefaultMessageEnvelopeFactory}
 * for why this deliberately isn't {@code @ConditionalOnMissingBean}); where a
 * tracing-aware override is also present (see {@code gebo.architecture.telemetry}),
 * its {@code @Primary} annotation picks it over this one.
 */
@Component
public class NoOpMessageDeliveryObserver implements IGMessageDeliveryObserver {

	@Override
	public void aroundDelivery(GMessageEnvelope<?> envelope, Runnable delivery) {
		delivery.run();
	}
}
