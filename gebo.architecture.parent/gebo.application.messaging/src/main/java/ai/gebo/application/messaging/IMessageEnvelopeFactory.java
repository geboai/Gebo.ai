/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.application.messaging;

import ai.gebo.application.messaging.model.GMessageEnvelope;

/**
 * Injectable factory for {@link GMessageEnvelope} creation, mirroring
 * {@link GMessageEnvelope#newMessageFrom}. Introduced so envelope creation has
 * a Spring-managed seam: a static factory method has no path to a
 * Spring-managed bean (e.g. a Micrometer {@code Tracer}), while an injectable
 * factory does. The default implementation ({@code DefaultMessageEnvelopeFactory})
 * simply delegates to the existing static logic; a tracing-aware override
 * lives in {@code gebo.architecture.telemetry}.
 */
public interface IMessageEnvelopeFactory {

	<T extends IGMessagePayloadType> GMessageEnvelope<T> newMessageFrom(IGMessageEmitter system, T payload);

	<T extends IGMessagePayloadType> GMessageEnvelope<T> newMessageFrom(IGMessageEmitter system, T payload,
			String user);
}
