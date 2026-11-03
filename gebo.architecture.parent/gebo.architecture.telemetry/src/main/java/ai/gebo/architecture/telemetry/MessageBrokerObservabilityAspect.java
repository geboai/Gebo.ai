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
import java.util.concurrent.TimeUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.model.GMessageEnvelope;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.propagation.Propagator;

/**
 * Instruments {@code IGMessageBroker.accept(..)}/{@code broadcast(..)} -
 * the single method every message hop passes through, whether it's a local
 * dispatch inside the monolith, a local dispatch inside a microservice, or
 * the entry point for a RabbitMQ-delivered envelope. {@code IGMessageBroker}
 * is a genuine Spring bean injected by interface everywhere it's used, so a
 * proxy-based AOP aspect reaches every one of those call sites.
 *
 * <p>
 * If the envelope already carries a trace context (populated by
 * {@link TracingMessageEnvelopeFactory} at creation time, or by a remote
 * sender before the RabbitMQ hop), this continues that trace; otherwise it
 * starts a new one. Either way it records a span plus a routing-latency timer
 * tagged by source/target module and payload type.
 * </p>
 * <p>
 * The {@link Tracer}, {@link Propagator} and {@link MeterRegistry} are looked
 * up per call rather than required at startup, for the reason given on
 * {@link TracingMessageEnvelopeFactory}: a {@code @ConditionalOnBean} on a
 * component-scanned class runs before Spring Boot's auto-configuration has
 * registered them. Without a tracer the call runs without a span; without a
 * meter registry it runs without the timer.
 * </p>
 */
@Aspect
@Component
public class MessageBrokerObservabilityAspect {

	private final ObjectProvider<Tracer> tracer;
	private final ObjectProvider<Propagator> propagator;
	private final ObjectProvider<MeterRegistry> meterRegistry;

	public MessageBrokerObservabilityAspect(ObjectProvider<Tracer> tracer, ObjectProvider<Propagator> propagator,
			ObjectProvider<MeterRegistry> meterRegistry) {
		this.tracer = tracer;
		this.propagator = propagator;
		this.meterRegistry = meterRegistry;
	}

	@Around("execution(* ai.gebo.application.messaging.IGMessageBroker.accept(..)) "
			+ "|| execution(* ai.gebo.application.messaging.IGMessageBroker.broadcast(..))")
	public Object aroundBrokerRouting(ProceedingJoinPoint pjp) throws Throwable {
		Object[] args = pjp.getArgs();
		if (args.length == 0 || !(args[0] instanceof GMessageEnvelope<?> envelope)) {
			return pjp.proceed();
		}

		String sourceModule = String.valueOf(envelope.getSourceModule());
		String targetModule = String.valueOf(envelope.getTargetModule());
		String payloadType = String.valueOf(envelope.getPayloadType());

		Tracer currentTracer = tracer.getIfAvailable();
		Propagator currentPropagator = propagator.getIfAvailable();
		Span span = null;
		if (currentTracer != null && currentPropagator != null) {
			Map<String, String> traceContext = envelope.getTraceContext();
			span = (traceContext != null && !traceContext.isEmpty())
					? currentPropagator.extract(traceContext, Map::get).name(spanName(envelope)).start()
					: currentTracer.nextSpan().name(spanName(envelope)).start();
			span.tag("payloadType", payloadType);
			span.tag("sourceModule", sourceModule);
			span.tag("targetModule", targetModule);
			if (envelope.getWorkflowId() != null) {
				span.tag("workflowId", envelope.getWorkflowId());
			}
			if (envelope.getWorkflowStepId() != null) {
				span.tag("workflowStepId", envelope.getWorkflowStepId());
			}
		}

		long start = System.nanoTime();
		try (Tracer.SpanInScope scope = span != null ? currentTracer.withSpan(span) : null) {
			return pjp.proceed();
		} finally {
			if (span != null) {
				span.end();
			}
			MeterRegistry registry = meterRegistry.getIfAvailable();
			if (registry != null) {
				Timer.builder("gebo.messaging.broker.routing").tag("sourceModule", sourceModule)
						.tag("targetModule", targetModule).tag("payloadType", payloadType).register(registry)
						.record(System.nanoTime() - start, TimeUnit.NANOSECONDS);
			}
		}
	}

	private String spanName(GMessageEnvelope<?> envelope) {
		return "gebo.messaging.route." + envelope.getSourceModule() + "." + envelope.getSourceComponent() + "->"
				+ envelope.getTargetModule() + "." + envelope.getTargetComponent();
	}
}
