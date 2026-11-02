/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.telemetry;

import org.springframework.beans.factory.ObjectProvider;
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
 * header plumbing is involved; inside the monolith it carries the trace from
 * the emitting thread to the receiver's delivery thread. Takes priority over
 * {@link DefaultMessageEnvelopeFactory} via {@code @Primary}.
 * <p>
 * The {@link Tracer} and {@link Propagator} are looked up when an envelope is
 * built, not when this bean is created. They come from Spring Boot's tracing
 * auto-configuration, which registers its beans after the component scan that
 * finds this class, so a {@code @ConditionalOnBean(Tracer.class)} here never
 * matched and the factory was silently left out. Without a tracer the envelope
 * is built exactly as the default factory builds it.
 * </p>
 */
@Component
@Primary
public class TracingMessageEnvelopeFactory implements IMessageEnvelopeFactory {

	private final DefaultMessageEnvelopeFactory delegate;
	private final ObjectProvider<Tracer> tracer;
	private final ObjectProvider<Propagator> propagator;

	public TracingMessageEnvelopeFactory(DefaultMessageEnvelopeFactory delegate, ObjectProvider<Tracer> tracer,
			ObjectProvider<Propagator> propagator) {
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
		Tracer currentTracer = tracer.getIfAvailable();
		Propagator currentPropagator = propagator.getIfAvailable();
		if (currentTracer == null || currentPropagator == null) {
			return;
		}
		Span currentSpan = currentTracer.currentSpan();
		if (currentSpan == null || currentSpan.isNoop()) {
			return;
		}
		currentPropagator.inject(currentSpan.context(), envelope, GMessageEnvelope::putTraceContextField);
	}
}
