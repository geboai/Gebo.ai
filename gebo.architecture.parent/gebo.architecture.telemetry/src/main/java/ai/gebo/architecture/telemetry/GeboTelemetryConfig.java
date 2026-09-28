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
 * <p>
 * Gebo does not declare its own {@link ObservationRegistry}: it uses the one Spring
 * Boot auto-configures, which every LLM model configuration support service injects
 * and hands to the Spring AI models it builds (chat, embedding, image). That registry
 * is meant to be extended, not replaced, by any module deployed alongside:
 * <ul>
 * <li>publish {@code ObservationHandler} beans (e.g. one typed on Spring AI's
 * {@code ChatModelObservationContext} or {@code EmbeddingModelObservationContext})
 * to receive every model call;</li>
 * <li>publish {@code ObservationRegistryCustomizer}, {@code ObservationPredicate},
 * {@code ObservationFilter} or {@code GlobalObservationConvention} beans to shape
 * what is observed and how it is tagged.</li>
 * </ul>
 * Spring Boot applies all of them to the registry whichever module publishes them.
 * Replacing the registry itself is also possible, since the auto-configured one
 * backs off when another {@link ObservationRegistry} bean exists, and the beans
 * above are applied to the replacement too.
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
