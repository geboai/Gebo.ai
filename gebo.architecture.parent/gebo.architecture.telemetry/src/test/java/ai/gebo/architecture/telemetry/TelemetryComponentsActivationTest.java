/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.telemetry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import ai.gebo.application.messaging.GeboCurrentApplication;
import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.IGMessageEmitter;
import ai.gebo.application.messaging.IGMessagePayloadType;
import ai.gebo.application.messaging.IGMessagingSystem;
import ai.gebo.application.messaging.IMessageEnvelopeFactory;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.impl.DefaultMessageEnvelopeFactory;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.model.GModuleMetaInfo;
import ai.gebo.application.messaging.orchestration.IGMessageDeliveryObserver;
import ai.gebo.application.messaging.orchestration.NoOpMessageDeliveryObserver;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;

/**
 * Boots the telemetry components the way the applications do - found by a
 * component scan of a Gebo package, with Spring Boot's tracing
 * auto-configuration supplying the {@link Tracer} - and checks they are not
 * only present but working: the envelope factory and the delivery observer win
 * over the defaults, a message built inside a span carries that span's trace to
 * the delivery thread, and the broker aspect continues the trace and records
 * the routing timer.
 * <p>
 * Guards against the regression where {@code @ConditionalOnBean(Tracer.class)}
 * on these component-scanned classes was evaluated before the tracing
 * auto-configuration had registered the tracer, so none of them was ever
 * created and no trace crossed a message hop.
 * </p>
 */
@SpringBootTest(classes = TelemetryComponentsActivationTest.TestApplication.class, properties = {
		"spring.main.web-application-type=none", "management.tracing.sampling.probability=1.0" })
class TelemetryComponentsActivationTest {

	@SpringBootApplication
	@ComponentScan(basePackages = "ai.gebo.architecture.telemetry")
	@Import({ DefaultMessageEnvelopeFactory.class, NoOpMessageDeliveryObserver.class })
	static class TestApplication {
		@Bean
		RecordingBroker recordingBroker(Tracer tracer) {
			return new RecordingBroker(tracer);
		}
	}

	@Autowired
	Tracer tracer;
	@Autowired
	IMessageEnvelopeFactory envelopeFactory;
	@Autowired
	IGMessageDeliveryObserver deliveryObserver;
	@Autowired
	IGMessageBroker broker;
	@Autowired
	RecordingBroker recordingBroker;
	@Autowired
	MeterRegistry meterRegistry;

	@Test
	void theTracingComponentsReplaceTheDefaults() {
		assertInstanceOf(TracingMessageEnvelopeFactory.class, envelopeFactory);
		assertInstanceOf(TracingMessageDeliveryObserver.class, deliveryObserver);
	}

	@Test
	void anEnvelopeBuiltInsideASpanCarriesItsTraceToTheDelivery() throws Exception {
		Span span = tracer.nextSpan().name("emitter").start();
		GMessageEnvelope<TestPayload> envelope;
		try (Tracer.SpanInScope scope = tracer.withSpan(span)) {
			envelope = envelopeFactory.newMessageFrom(new TestEmitter(), new TestPayload());
		} finally {
			span.end();
		}
		String traceId = span.context().traceId();
		assertNotNull(envelope.getTraceContext(), "the envelope carries no trace context");
		assertTrue(envelope.getTraceContext().getOrDefault("traceparent", "").contains(traceId),
				"traceparent " + envelope.getTraceContext() + " does not name trace " + traceId);

		// the delivery runs on another thread, as the receivers' runners do
		AtomicReference<String> deliveredTraceId = new AtomicReference<>();
		Thread receiver = new Thread(() -> deliveryObserver.aroundDelivery(envelope,
				() -> deliveredTraceId.set(currentTraceId())));
		receiver.start();
		receiver.join();
		assertEquals(traceId, deliveredTraceId.get());
	}

	@Test
	void theBrokerAspectContinuesTheTraceAndTimesTheRouting() {
		Span span = tracer.nextSpan().name("emitter").start();
		GMessageEnvelope<TestPayload> envelope;
		try (Tracer.SpanInScope scope = tracer.withSpan(span)) {
			envelope = envelopeFactory.newMessageFrom(new TestEmitter(), new TestPayload());
		} finally {
			span.end();
		}
		broker.accept(envelope);
		// read through a method: the bean is the aspect's proxy, whose own fields are unset
		assertEquals(span.context().traceId(), recordingBroker.lastTraceId());
		assertNotNull(meterRegistry.find("gebo.messaging.broker.routing").timer(),
				"the routing timer was not registered");
	}

	private String currentTraceId() {
		Span current = tracer.currentSpan();
		return current != null ? current.context().traceId() : null;
	}

	static class TestPayload implements IGMessagePayloadType {
		private static final long serialVersionUID = 1L;

		@Override
		public String getPayloadId() {
			return "test-payload";
		}

		@Override
		public Date getTimestamp() {
			return new Date();
		}
	}

	static class TestEmitter implements IGMessageEmitter {
		@Override
		public List<String> getEmittedPayloadTypes() {
			return List.of(TestPayload.class.getName());
		}

		@Override
		public String getMessagingModuleId() {
			return "test-module";
		}

		@Override
		public String getMessagingSystemId() {
			return "test-emitter";
		}

		@Override
		public SystemComponentType getComponentType() {
			return SystemComponentType.USER;
		}
	}

	/** A broker that only remembers the trace it ran under. */
	static class RecordingBroker implements IGMessageBroker {
		private final AtomicReference<String> lastTraceId = new AtomicReference<>();
		private final Tracer tracer;

		RecordingBroker(Tracer tracer) {
			this.tracer = tracer;
		}

		public String lastTraceId() {
			return lastTraceId.get();
		}

		@Override
		public void accept(GMessageEnvelope envelope) {
			Span current = tracer.currentSpan();
			lastTraceId.set(current != null ? current.context().traceId() : null);
		}

		@Override
		public void broadcast(GMessageEnvelope envelope) {
			accept(envelope);
		}

		@Override
		public List<String> getAcceptedPayloadTypes() {
			return List.of();
		}

		@Override
		public boolean isAcceptEveryPayloadType() {
			return true;
		}

		@Override
		public String getMessagingModuleId() {
			return "test-module";
		}

		@Override
		public String getMessagingSystemId() {
			return "test-broker";
		}

		@Override
		public SystemComponentType getComponentType() {
			return SystemComponentType.USER;
		}

		@Override
		public void addSystemComponent(IGMessagingSystem system) {
		}

		@Override
		public boolean isRegistered(IGMessagingSystem system) {
			return false;
		}

		@Override
		public void removeSystemComponent(IGMessagingSystem system) {
		}

		@Override
		public List<GModuleMetaInfo> getSystemsInfo() {
			return List.of();
		}

		@Override
		public boolean checkReceivingComponentPresent(String module, String componentId) {
			return false;
		}

		@Override
		public GeboCurrentApplication getCurrentApplication() {
			return null;
		}
	}
}
