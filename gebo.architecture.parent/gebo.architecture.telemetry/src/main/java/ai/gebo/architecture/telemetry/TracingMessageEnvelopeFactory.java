/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.telemetry;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.IGMessageEmitter;
import ai.gebo.application.messaging.IGMessagePayloadType;
import ai.gebo.application.messaging.IMessageEnvelopeFactory;
import ai.gebo.application.messaging.impl.DefaultMessageEnvelopeFactory;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.propagation.Propagator;

/**
 * Tracing-aware {@link IMessageEnvelopeFactory}: builds the envelope exactly
 * as {@link DefaultMessageEnvelopeFactory} does, then injects the current
 * trace context (if any) into it via Micrometer Tracing's {@link Propagator}.
 * Because {@code GMessageEnvelopeCodec} round-trips the whole envelope as
 * JSON, this trace context survives the RabbitMQ hop automatically - no AMQP
 * header plumbing is involved. Active only when a {@link Tracer} bean exists
 * (i.e. {@code gebo.architecture.telemetry} is on the classpath); takes
 * priority over {@link DefaultMessageEnvelopeFactory} via {@code @Primary}.
 */
@Component
@ConditionalOnBean(Tracer.class)
@Primary
public class TracingMessageEnvelopeFactory implements IMessageEnvelopeFactory {

	private final DefaultMessageEnvelopeFactory delegate;
	private final Tracer tracer;
	private final Propagator propagator;

	public TracingMessageEnvelopeFactory(DefaultMessageEnvelopeFactory delegate, Tracer tracer,
			Propagator propagator) {
		this.delegate = delegate;
		this.tracer = tracer;
		this.propagator = propagator;
	}

	@Override
	public <T extends IGMessagePayloadType> GMessageEnvelope<T> newMessageFrom(IGMessageEmitter system, T payload) {
		GMessageEnvelope<T> envelope = delegate.newMessageFrom(system, payload);
		injectTraceContext(envelope);
		return envelope;
	}

	@Override
	public <T extends IGMessagePayloadType> GMessageEnvelope<T> newMessageFrom(IGMessageEmitter system, T payload,
			String user) {
		GMessageEnvelope<T> envelope = delegate.newMessageFrom(system, payload, user);
		injectTraceContext(envelope);
		return envelope;
	}

	private void injectTraceContext(GMessageEnvelope<?> envelope) {
		Span currentSpan = tracer.currentSpan();
		if (currentSpan == null || currentSpan.isNoop()) {
			return;
		}
		propagator.inject(currentSpan.context(), envelope, GMessageEnvelope::putTraceContextField);
	}
}
