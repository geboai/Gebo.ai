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
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
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
 */
@Aspect
@Component
@ConditionalOnBean(Tracer.class)
public class MessageBrokerObservabilityAspect {

	private final Tracer tracer;
	private final Propagator propagator;
	private final MeterRegistry meterRegistry;

	public MessageBrokerObservabilityAspect(Tracer tracer, Propagator propagator, MeterRegistry meterRegistry) {
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

		Map<String, String> traceContext = envelope.getTraceContext();
		Span span = (traceContext != null && !traceContext.isEmpty())
				? propagator.extract(traceContext, Map::get).name(spanName(envelope)).start()
				: tracer.nextSpan().name(spanName(envelope)).start();
		span.tag("payloadType", payloadType);
		span.tag("sourceModule", sourceModule);
		span.tag("targetModule", targetModule);
		if (envelope.getWorkflowId() != null) {
			span.tag("workflowId", envelope.getWorkflowId());
		}
		if (envelope.getWorkflowStepId() != null) {
			span.tag("workflowStepId", envelope.getWorkflowStepId());
		}

		long start = System.nanoTime();
		try (Tracer.SpanInScope scope = tracer.withSpan(span)) {
			return pjp.proceed();
		} finally {
			span.end();
			Timer.builder("gebo.messaging.broker.routing").tag("sourceModule", sourceModule)
					.tag("targetModule", targetModule).tag("payloadType", payloadType).register(meterRegistry)
					.record(System.nanoTime() - start, TimeUnit.NANOSECONDS);
		}
	}

	private String spanName(GMessageEnvelope<?> envelope) {
		return "gebo.messaging.route." + envelope.getSourceModule() + "." + envelope.getSourceComponent() + "->"
				+ envelope.getTargetModule() + "." + envelope.getTargetComponent();
	}
}
