/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.telemetry;

import java.util.Map;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.orchestration.IGMessageDeliveryObserver;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.propagation.Propagator;

/**
 * Tracing-aware {@link IGMessageDeliveryObserver}: the actual receiver
 * execution point - {@code MessageReceiverRunner}'s dedicated-thread delivery
 * loop and {@code ThreadMessageReceiverMultiplexer}'s synchronous backup path,
 * neither of which a Spring AOP proxy can reach since both run on manually
 * managed threads outside the container. Extracts the envelope's trace
 * context (injected earlier by {@link TracingMessageEnvelopeFactory}) and
 * scopes a span around the real delivery call, so processing latency and span
 * nesting are correct regardless of which of the two delivery paths ran.
 */
@Component
@ConditionalOnBean(Tracer.class)
@Primary
public class TracingMessageDeliveryObserver implements IGMessageDeliveryObserver {

	private final Tracer tracer;
	private final Propagator propagator;

	public TracingMessageDeliveryObserver(Tracer tracer, Propagator propagator) {
		this.tracer = tracer;
		this.propagator = propagator;
	}

	@Override
	public void aroundDelivery(GMessageEnvelope<?> envelope, Runnable delivery) {
		Map<String, String> traceContext = envelope.getTraceContext();
		if (traceContext == null || traceContext.isEmpty()) {
			delivery.run();
			return;
		}
		Span span = propagator.extract(traceContext, Map::get)
				.name("gebo.messaging.delivery." + envelope.getTargetModule() + "." + envelope.getTargetComponent())
				.tag("payloadType", String.valueOf(envelope.getPayloadType()))
				.tag("sourceModule", String.valueOf(envelope.getSourceModule()))
				.tag("targetModule", String.valueOf(envelope.getTargetModule()))
				.start();
		try (Tracer.SpanInScope scope = tracer.withSpan(span)) {
			delivery.run();
		} finally {
			span.end();
		}
	}
}
