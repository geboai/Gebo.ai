/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationPredicate;

/**
 * Spring Security observes every request it filters ({@code spring.security.filterchains},
 * {@code spring.security.authorizations}, ...). On the application port those
 * observations sit inside Spring Boot's {@code http.server.requests} observation of
 * the request, so they are spans of the request's trace. On the separate management
 * port no request observation exists (Spring Boot does not observe the actuator
 * there), so the same security observations have no parent: each one becomes a
 * trace of its own, with nothing in it but "security filterchain before" or
 * "after". A Prometheus scrape every 15 seconds leaves two such traces, about
 * 11,500 a day of noise in Tempo.
 * <p>
 * A security observation without an observation around it is never a useful trace,
 * so it is not recorded. Spring Boot applies the predicate to the observation
 * registry it auto-configures, and Micrometer sets the parent on the context before
 * asking the predicates, which is what the check relies on.
 * </p>
 */
@Configuration
public class SecurityObservationPredicate implements ObservationPredicate {
	static final String SECURITY_OBSERVATIONS_PREFIX = "spring.security.";

	@Override
	public boolean test(String name, Observation.Context context) {
		return !(name != null && name.startsWith(SECURITY_OBSERVATIONS_PREFIX)
				&& context.getParentObservation() == null);
	}

	@Bean
	public ObservationPredicate securityObservationsOnlyInsideARequestObservation() {
		return this;
	}
}
