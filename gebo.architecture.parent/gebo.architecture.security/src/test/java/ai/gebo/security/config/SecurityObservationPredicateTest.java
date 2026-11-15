package ai.gebo.security.config;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;

/**
 * A security observation is recorded only inside another observation (the
 * request's): alone it would be a trace of its own with nothing in it.
 */
class SecurityObservationPredicateTest {

	private final ObservationRegistry registry = registry();

	private static ObservationRegistry registry() {
		ObservationRegistry registry = ObservationRegistry.create();
		// a registry with no handler is itself a no-op: every observation is
		registry.observationConfig().observationHandler(context -> true);
		registry.observationConfig().observationPredicate(new SecurityObservationPredicate());
		return registry;
	}

	@Test
	void aSecurityObservationWithoutAParentIsNotRecorded() {
		Observation chain = Observation.createNotStarted("spring.security.filterchains", registry);
		assertTrue(chain.isNoop(), "A security filter chain observation with no request observation around it");
		Observation authorization = Observation.createNotStarted("spring.security.authorizations", registry);
		assertTrue(authorization.isNoop(), "An authorization observation with no request observation around it");
	}

	@Test
	void aSecurityObservationInsideARequestObservationIsRecorded() {
		Observation request = Observation.createNotStarted("http.server.requests", registry).start();
		try (Observation.Scope scope = request.openScope()) {
			Observation chain = Observation.createNotStarted("spring.security.filterchains", registry);
			assertFalse(chain.isNoop(), "Inside the request's observation the security span belongs to its trace");
		} finally {
			request.stop();
		}
	}

	@Test
	void otherObservationsAreLeftAlone() {
		assertFalse(Observation.createNotStarted("http.server.requests", registry).isNoop());
		assertFalse(Observation.createNotStarted("task checkTimeouts", registry).isNoop());
		assertFalse(Observation.createNotStarted("gen_ai.client.operation", registry).isNoop());
	}
}
