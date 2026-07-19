/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.telemetry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.micrometer.metrics.autoconfigure.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import ai.gebo.architecture.environment.GeboApplicationArchitecture;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;

/**
 * Shared telemetry wiring for both the monolith and every microservice: tags
 * every metric with the running application's name and architecture mode
 * ({@code monolithic}/{@code microservices}), and registers the
 * {@link ObservedAspect} that makes the {@code @Observed} annotation active
 * anywhere in the codebase.
 */
@Configuration
public class GeboTelemetryConfig {

	@Bean
	public MeterRegistryCustomizer<MeterRegistry> geboCommonMetricsTags(Environment environment,
			@Autowired(required = false) GeboApplicationArchitecture architecture) {
		String applicationName = environment.getProperty("spring.application.name", "gebo-ai");
		String architectureTag = architecture != null ? architecture.getArchitecture().name().toLowerCase()
				: "unknown";
		return registry -> registry.config().commonTags("application", applicationName, "architecture",
				architectureTag);
	}

	@Bean
	public ObservedAspect observedAspect(ObservationRegistry observationRegistry) {
		return new ObservedAspect(observationRegistry);
	}
}
